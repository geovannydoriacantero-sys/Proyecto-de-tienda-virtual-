package controladorvista;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import tienda.ListaUsuario;
import tienda.Producto;
import tienda.Usuario;
import tienda.UsuarioAdministrador;

public class TarjetaCarritoControlador implements Initializable {

    private Usuario usuario;
    private ListaUsuario listaUsuarios;
    private UsuarioAdministrador usuarioAdmin;
    private Producto producto;

    private Runnable listener;

    private int cantidad;

    @FXML
    private ImageView imagenView;
    @FXML
    private Label labelCantidad;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelPrecio;
    @FXML
    private Label labelUnidad;

    public void setOnCantidadCambiada(Runnable r) {
        this.listener = r;
    }

    public void setUsuarioAdmin(UsuarioAdministrador usuarioAdmin) {
        this.usuarioAdmin = usuarioAdmin;
    }

    public void setListaUsuarios(ListaUsuario listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public void setUsuario(Usuario u) {
        this.usuario = u;
    }

    public int getCantidad() {
        try {
            return Integer.parseInt(labelUnidad.getText());
        } catch (Exception e) {
            return 1;
        }
    }

    public void setProducto(Producto p) {
        this.producto = p;

        // Inicializar cantidad con la cantidad real del carrito
        this.cantidad = p.getCantidadCarrito();
        labelUnidad.setText("" + this.cantidad);
        
        labelNombre.setText(p.getNombre());
        labelCantidad.setText("x" + p.getStock());
        labelPrecio.setText("$ " + p.getPrecio());

        Image image = new Image("file:" + p.getRutaImagen());
        imagenView.setImage(image);
    }

    public Producto getProducto() {
        return producto;
    }

    @FXML
    public void sumarProducto(ActionEvent e) {
        try {
            cantidad = Integer.parseInt(labelUnidad.getText());
            if (cantidad < producto.getStock()) {
                cantidad++;
                labelUnidad.setText("" + cantidad);
                producto.setCantidadCarrito(cantidad); // actualizar en el producto
                if (listener != null) {
                    listener.run();
                }
            }
        } catch (NumberFormatException err) {
            System.out.println("Error: " + err);
        }

    }

    @FXML
    public void restarProducto(ActionEvent e) {
        try {
            cantidad = Integer.parseInt(labelUnidad.getText());
            if (cantidad > 1) {
                cantidad--;
                labelUnidad.setText("" + cantidad);
                producto.setCantidadCarrito(cantidad); // actualizar en el producto
                if (listener != null) {
                    listener.run();
                }
            }
        } catch (NumberFormatException err) {
            System.out.println("Error: " + err);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        redondearImagenes();
    }

    public void redondearImagenes() {
        Rectangle clip = new Rectangle(140, 140);
        clip.setArcWidth(64);
        clip.setArcHeight(64);
        imagenView.setClip(clip);
    }

}
