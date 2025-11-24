package tienda;

public class ListaProducto {

    private NodoProducto cabeza;

    public ListaProducto() {
        cabeza = null;
    }

    public void agregarProducto(Producto producto) {
        NodoProducto nuevo = new NodoProducto(producto);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoProducto auxiliar = cabeza;
            while (auxiliar.siguiente != null) {
                auxiliar = auxiliar.siguiente;
            }
            auxiliar.siguiente = nuevo;
        }
    }
    
    public boolean eliminarProducto(String id) {
    if (cabeza == null) {
        return false; // Lista vacía
    }

    // Caso 1: el que debe eliminarse es la cabeza
    if (cabeza.producto.getId().equals(id)) {
        cabeza = cabeza.siguiente; // mover la cabeza
        return true;
    }

    NodoProducto actual = cabeza;
    NodoProducto anterior = null;

    // Buscar el nodo a eliminar
    while (actual != null && !actual.producto.getId().equals(id)) {
        anterior = actual;
        actual = actual.siguiente;
    }

    // Si no se encontró
    if (actual == null) {
        return false;
    }

    // Caso 2: está en medio o al final
    anterior.siguiente = actual.siguiente;
    return true;
}

    public void limpiar() {
        NodoProducto actual = cabeza;
        while (actual != null) {
            NodoProducto siguiente = actual.siguiente;
            actual.siguiente = null; // romper enlace
            actual = siguiente;
        }
        cabeza = null;
    }

    public NodoProducto getCabeza() {
        return cabeza;
    }

    public boolean existeProducto(String id) {
        NodoProducto aux = cabeza;
        while (aux != null) {
            if (aux.producto.getId().equalsIgnoreCase(id)) {
                return true;
            }
            aux = aux.siguiente;
        }
        return false;
    }

    public Producto buscarProducto(String id) {
        NodoProducto aux = cabeza;
        while (aux != null) {
            if (aux.producto.getId().equalsIgnoreCase(id)) {
                return aux.producto;
            }
            aux = aux.siguiente;
        }
        return null;
    }
}
