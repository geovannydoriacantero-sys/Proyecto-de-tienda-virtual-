package tienda;

public class ListaUsuario {
    private NodoUsuario cabeza;

    public ListaUsuario() {
        cabeza = null;
    }

    public Usuario buscarUsuario(String correo) {
        NodoUsuario aux = cabeza;

        while (aux != null) {
            if (aux.usuario.getUserCorreo().equalsIgnoreCase(correo)) {
                return aux.usuario;
            }
            aux = aux.siguiente;
        }

        return null;
    }

    public boolean agregarUsuario(Usuario usuario) {
        if (buscarUsuario(usuario.getUserCorreo()) != null) {      
            return false;
        }

        NodoUsuario nuevo = new NodoUsuario(usuario);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoUsuario aux = cabeza;
            while (aux.siguiente != null) {
                aux = aux.siguiente;
            }
            aux.siguiente = nuevo;
        }

        return true;
    }

    public NodoUsuario getCabeza() {
        return cabeza;
    }
}
