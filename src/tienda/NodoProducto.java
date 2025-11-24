
package tienda;

public class NodoProducto {
    
    public NodoProducto siguiente;
    public Producto producto;
    
    public NodoProducto(Producto producto) {
        this.producto = producto;
        siguiente = null;
    }
}
