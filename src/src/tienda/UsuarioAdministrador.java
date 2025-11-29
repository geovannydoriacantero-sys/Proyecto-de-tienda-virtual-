package tienda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UsuarioAdministrador {

    private final String ruta = "usuarios.txt";

    public UsuarioAdministrador() {
    }

    public ListaUsuario cargarUsuarios(ProductoAdministrador producto_admin) {
        ListaUsuario lista = new ListaUsuario();

        File archivo = new File(ruta);
        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(";");
                if (partes.length < 2) {
                    continue;
                }

                String nombre = partes[0].trim();
                String correo = partes[1].trim();
                String contraseña = partes[2].trim();

                Usuario u = new Usuario(nombre, correo, contraseña);

                if (partes.length >= 4 && !partes[3].trim().isEmpty()) {
                    String[] ids = partes[3].split(",");
                    for (String id : ids) {
                        Producto p = producto_admin.buscarProductoPorID(id.trim());
                        if (p != null) {
                            u.getListaDeseados().agregarProducto(p);
                        }
                    }
                }

                if (partes.length >= 5 && !partes[4].trim().isEmpty()) {
                    String[] ids = partes[4].split(",");
                    for (String id : ids) {
                        Producto p = producto_admin.buscarProductoPorID(id.trim());
                        if (p != null) {
                            u.getListaCarrito().agregarProducto(p);
                        }
                    }
                }
                
                if (partes.length >= 6 && !partes[5].trim().isEmpty()) {
                    String[] ids = partes[5].split(",");
                    for (String id : ids) {
                        Producto p = producto_admin.buscarProductoPorID(id.trim());
                        if (p != null) {
                            u.getListaHistorial().agregarProducto(p);
                        }
                    }
                }

                lista.agregarUsuario(u);
            }
        } catch (Exception e) {
        }
        return lista;
    }

    private String convertirListaAID(ListaProducto lista) {
        if (lista == null || lista.getCabeza() == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        NodoProducto auxiliar = lista.getCabeza();

        while (auxiliar != null) {
            sb.append(auxiliar.producto.getId());

            if (auxiliar.siguiente != null) {
                sb.append(",");
            }
            auxiliar = auxiliar.siguiente;
        }
        return sb.toString();
    }

    public void agregarUsuario(Usuario usuario) {

        try {
            File archivo = new File(ruta);
            boolean archivoVacio = (!archivo.exists()) || (archivo.length() == 0);

            FileWriter fw = new FileWriter(ruta, true);

            try (BufferedWriter bw = new BufferedWriter(fw)) {

                if (!archivoVacio) {
                    bw.newLine();
                }
                String lineaUsuario = usuario.getUserName() + ";"
                        + usuario.getUserCorreo() + ";"
                        + usuario.getContrasena() + ";"
                        + convertirListaAID(usuario.getListaDeseados()) + ";"
                        + convertirListaAID(usuario.getListaCarrito()) + ";"
                        + convertirListaAID(usuario.getListaHistorial());
                bw.write(lineaUsuario);
            }

        } catch (IOException err) {
            System.out.println("Error: " + err);
        }
    }

    public void guardarUsuarios(ListaUsuario listaUsuarios) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, false))) {

        NodoUsuario aux = listaUsuarios.getCabeza();

        while (aux != null) {
            Usuario u = aux.usuario;

            String linea = 
                u.getUserName() + ";" +
                u.getUserCorreo() + ";" +
                u.getContrasena() + ";" +
                convertirListaAID(u.getListaDeseados()) + ";" +
                convertirListaAID(u.getListaCarrito()) + ";" +
                convertirListaAID(u.getListaHistorial());

            bw.write(linea);
            bw.newLine();

            aux = aux.siguiente;
        }

    } catch (IOException e) {
        System.out.println("Error guardando usuarios: " + e);
    }
}
    
    
}
