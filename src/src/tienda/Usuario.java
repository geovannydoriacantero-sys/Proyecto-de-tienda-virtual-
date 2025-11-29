package tienda;

public class Usuario {

    private String userName;
    private String userCorreo;
    private String contrasena;

    private ListaProducto listaDeseados;
    private ListaProducto listaHistorial;
    private ListaProducto listaCarrito;

    public Usuario(String userName, String userCorreo, String contrasena) {
        this.userName = userName;
        this.userCorreo = userCorreo;
        this.contrasena = contrasena;

        this.listaDeseados = new ListaProducto();
        this.listaHistorial = new ListaProducto();
        this.listaCarrito = new ListaProducto();
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCorreo() {
        return userCorreo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public ListaProducto getListaDeseados() {
        return listaDeseados;
    }

    public ListaProducto getListaHistorial() {
        return listaHistorial;
    }

    public ListaProducto getListaCarrito() {
        return listaCarrito;
    }

    public boolean agregarADeseados(Producto p) {
        if (listaDeseados.existeProducto(p.getId())) {
            listaDeseados.eliminarProducto(p.getId());
            return false;
        }
        listaDeseados.agregarProducto(p);
        return true;
    }

    public boolean agregarACarrito(Producto p) {
        if (listaCarrito.existeProducto(p.getId())) {
            listaCarrito.eliminarProducto(p.getId());
            return false;
        }
        listaCarrito.agregarProducto(p);
        return true;
    }
    
    public boolean agregarAHistorial(Producto p) {
        listaHistorial.agregarProducto(p);
        return true;
    }
}
