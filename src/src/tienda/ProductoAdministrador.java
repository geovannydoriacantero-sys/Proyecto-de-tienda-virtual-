package tienda;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ProductoAdministrador {

    private static final String DATA_FILE = "productos.txt";
    private static final String IMAGES_DIR = "images";

    public void comprobarRutaImagen() throws IOException {
        Path images = Paths.get(IMAGES_DIR);
        if (!Files.exists(images)) {
            Files.createDirectories(images);
        }
    }

    public String obtenerSiguienteID() throws IOException {

        try {
            Path ruta = Paths.get(DATA_FILE);
            if (!Files.exists(ruta)) {
                return "P0001";
            } else {
                List<String> lineas = Files.readAllLines(ruta);
                for (int i = lineas.size() - 1; i >= 0; i--) {
                    String linea = lineas.get(i);
                    if (linea.isEmpty()) {
                        continue;
                    }

                    String[] partes = linea.split(";");
                    String anteriorID = partes[0].substring(1);
                    int numero = Integer.parseInt(anteriorID);
                    numero++;
                    return String.format("P%04d", numero);
                }
            }
        } catch (IOException | NumberFormatException err) {
            System.out.println("ERROR: " + err);
        }
        return "P0001";
    }

    public Producto agregarProducto(Producto producto, String rutaImagen) throws IOException {
        String id = obtenerSiguienteID();
        comprobarRutaImagen();

        File imagen_original = new File(rutaImagen);
        if (!imagen_original.exists()) {
            throw new FileNotFoundException("La imagen no existe: " + producto.getRutaImagen());
        }

        String extension = "";
        int ext = imagen_original.getName().lastIndexOf('.');
        if (ext >= 0) {
            extension = imagen_original.getName().substring(ext);
        }
        String nuevoNombreImagen = id + extension;
        Path targetPath = Paths.get(IMAGES_DIR, nuevoNombreImagen);

        Files.copy(imagen_original.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        String ruta = IMAGES_DIR + "/" + nuevoNombreImagen;

        // Guardar registro
        String registro = String.format("%s;%s;%s;%s;%d;%.2f;%s", id, producto.getNombre(), producto.getDescripcion(), producto.getCategoria(),
                producto.getStock(), producto.getPrecio(), ruta);
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(DATA_FILE),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            bw.write(registro);
            bw.newLine();
        }

        return producto;
    }

    public ListaProducto cargarProductos() {
        ListaProducto lista = new ListaProducto();
        Path archivo = Paths.get(DATA_FILE);
        if (!Files.exists(archivo)) {
            return lista;
        }
        try {
            List<String> lineas = Files.readAllLines(archivo);
            for (String line : lineas) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] partes = line.split(";");
                if (partes.length >= 7) {
                    String id = partes[0];
                    String nombre = partes[1];
                    String descripcion = partes[2];
                    String categoria = partes[3];
                    int stock = Integer.parseInt(partes[4].replace(",", "."));
                    double precio = Double.parseDouble(partes[5].replace(",", "."));
                    String rutaImagen = partes[6];
                    lista.agregarProducto(new Producto(id, nombre, descripcion, categoria, stock, precio, rutaImagen));
                }
            }
        } catch (IOException | NumberFormatException err) {
            System.out.println("Error: " + err);
        }
        return lista;
    }

    public Producto buscarProductoPorID(String id) {
        ListaProducto lista = cargarProductos();
        NodoProducto aux = lista.getCabeza();

        while (aux != null) {
            if (aux.producto.getId().equalsIgnoreCase(id)) {
                return aux.producto;
            }
            aux = aux.siguiente;
        }

        return null;
    }
}
