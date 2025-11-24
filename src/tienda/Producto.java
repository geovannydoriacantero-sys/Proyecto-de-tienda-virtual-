package tienda;

public class Producto {

    private String id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private int stock;
    private double precio;
    private String rutaImagen;
    
    private int cantidadCarrito = 1; // Por defecto 1

    public Producto(String id, String nombre, String descripcion, String categoria, int stock, double precio, String rutaImagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
        this.rutaImagen = rutaImagen;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getStock() {
        return stock;
    }

    public double getPrecio() {
        return precio;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public int getCantidadCarrito() {
        return cantidadCarrito;
    }

    public void setCantidadCarrito(int cantidadCarrito) {
        this.cantidadCarrito = cantidadCarrito;
    }
}
