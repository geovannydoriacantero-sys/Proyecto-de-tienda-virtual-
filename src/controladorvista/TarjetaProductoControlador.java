package controladorvista;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import tienda.ListaUsuario;
import tienda.Producto;
import tienda.Usuario;
import tienda.UsuarioAdministrador;

public class TarjetaProductoControlador implements Initializable {

    private Usuario usuario;
    private ListaUsuario listaUsuarios;
    private UsuarioAdministrador usuarioAdmin;
    private Producto producto;

    private MenuControlador menuC;

    @FXML
    private ImageView imgProducto;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblPrecio;

    @FXML
    private Button botonDeseado;
    @FXML
    private Button botonCarrito;

    @FXML
    private HBox botonesTarjeta;

    public Producto getProducto() {
        return producto;
    }

    public void setMenuC(MenuControlador menuC) {
        this.menuC = menuC;
    }

    public void mostrarModeloDeseado() {
        botonesTarjeta.getChildren().remove(botonCarrito);
        botonesTarjeta.setAlignment(Pos.CENTER);
        botonDeseado.setGraphic(new ImageView(new Image(getClass().getResource("/images/hearth_red.png").toExternalForm(), 20, 20, true, true)));
    }

    public void ocultarBotonesTarjeta() {
        botonesTarjeta.setVisible(false);
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

    public void setProducto(Producto p) {
        this.producto = p;

        lblNombre.setText(p.getNombre());
        lblPrecio.setText("$ " + p.getPrecio());

        Image image = new Image("file:" + p.getRutaImagen());
        imgProducto.setImage(image);
    }

    public void ponerEnDeseado() {
        botonDeseado.setGraphic(new ImageView(new Image(getClass().getResource("/images/hearth_red.png").toExternalForm(), 20, 20, true, true)));
    }

    public void ponerEnNoDeseado() {
        botonDeseado.setGraphic(new ImageView(new Image(getClass().getResource("/images/hearth_black.png").toExternalForm(), 20, 20, true, true)));
    }
    
    public void ponerEnCarrito() {
        botonCarrito.setGraphic(new ImageView(new Image(getClass().getResource("/images/shoppingcar_red.png").toExternalForm(), 20, 20, true, true)));
    }

    public void ponerEnNoCarrito() {
        botonCarrito.setGraphic(new ImageView(new Image(getClass().getResource("/images/shoppingcar_black.png").toExternalForm(), 20, 20, true, true)));
    }
    
    @FXML
    protected void agregarADeseados(ActionEvent event) {
        if (usuario.agregarADeseados(producto)) {
            usuarioAdmin.guardarUsuarios(listaUsuarios);
            botonDeseado.setGraphic(new ImageView(new Image(getClass().getResource("/images/hearth_red.png").toExternalForm(), 20, 20, true, true)));
        } else {
            botonDeseado.setGraphic(new ImageView(new Image(getClass().getResource("/images/hearth_black.png").toExternalForm(), 20, 20, true, true)));
            usuarioAdmin.guardarUsuarios(listaUsuarios);

            if (menuC != null) {
                menuC.mostrarProductosDeseados(menuC.getContenedorDeseados());
                menuC.cargarDeseados();
            }
        }
    }

    @FXML
    protected void agregarACarrito(ActionEvent event) {
        if (usuario.agregarACarrito(producto)) {
            usuarioAdmin.guardarUsuarios(listaUsuarios);
            botonCarrito.setGraphic(new ImageView(new Image(getClass().getResource("/images/shoppingcar_red.png").toExternalForm(), 20, 20, true, true)));
        } else {
            botonCarrito.setGraphic(new ImageView(new Image(getClass().getResource("/images/shoppingcar_black.png").toExternalForm(), 20, 20, true, true)));
            usuarioAdmin.guardarUsuarios(listaUsuarios);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        redondearImagenes();
    }

    public void redondearImagenes() {
        Rectangle clip = new Rectangle(116, 120);
        clip.setArcWidth(64);
        clip.setArcHeight(64);
        imgProducto.setClip(clip);
    }
}
