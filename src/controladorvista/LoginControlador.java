package controladorvista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Main;
import tienda.ListaUsuario;
import tienda.ProductoAdministrador;
import tienda.Usuario;
import tienda.UsuarioAdministrador;

public class LoginControlador implements Initializable {

    private ProductoAdministrador producto_admin;
    private ListaUsuario listaUsuarios;
    private UsuarioAdministrador user_admin;

    // Setter para recibir las referencias desde Main
    public void setListaUsuarios(ListaUsuario lista) {
        this.listaUsuarios = lista;
    }

    public void setProductoAdmin(ProductoAdministrador producto) {
        this.producto_admin = producto;
    }

    public void setUsuarioAdmin(UsuarioAdministrador userAdmin) {
        this.user_admin = userAdmin;
    }

    @FXML
    private Button botonInicioSesion1;
    @FXML
    private Button botonInicioSesion2;
    @FXML
    private Button botonRegistro;
    @FXML
    private Pane panelInicioSesion;
    @FXML
    private Pane panelRegistro;
    @FXML
    private Label label1;
    @FXML
    private TextField fieldCorreo;
    @FXML
    private PasswordField fieldContrasena;
    @FXML
    private TextField fieldNombreRegistro;
    @FXML
    private TextField fieldCorreoRegistro;
    @FXML
    private PasswordField fieldContrasenaRegistro;

    @FXML
    public void mostrarPanelInicioSesion(ActionEvent event) {
        label1.setVisible(false);
        botonRegistro.setVisible(false);
        botonInicioSesion1.setVisible(false);
        panelInicioSesion.setVisible(true);
    }

    @FXML
    public void mostrarPanelRegistro(ActionEvent event) {
        label1.setVisible(false);
        botonRegistro.setVisible(false);
        botonInicioSesion1.setVisible(false);
        panelRegistro.setVisible(true);
    }

    @FXML
    public void regresarInicioSesion(ActionEvent event) {
        label1.setVisible(true);
        botonRegistro.setVisible(true);
        botonInicioSesion1.setVisible(true);
        panelInicioSesion.setVisible(false);
        fieldCorreo.setText("");
        fieldContrasena.setText("");
    }

    @FXML
    public void regresarRegistro(ActionEvent event) {
        label1.setVisible(true);
        botonRegistro.setVisible(true);
        botonInicioSesion1.setVisible(true);
        panelRegistro.setVisible(false);
        fieldNombreRegistro.setText("");
        fieldCorreoRegistro.setText("");
        fieldContrasenaRegistro.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    public void iniciarSesion() throws IOException {
        if (fieldCorreo.getText().isEmpty() || fieldContrasena.getText().isEmpty()) {
            Main.mostrarAlerta("Informacion", "Por favor, rellene todos los campos vacíos.", new Alert(Alert.AlertType.INFORMATION));
            return;
        }

        Usuario user = listaUsuarios.buscarUsuario(fieldCorreo.getText());
        if (user == null) {
            Main.mostrarAlerta("Error", "El correo no está registrado.", new Alert(Alert.AlertType.ERROR));
            return;
        }

        if (!user.getContrasena().equals(fieldContrasena.getText())) {
            Main.mostrarAlerta("Informacion", "La contraseña es incorrecta.", new Alert(Alert.AlertType.INFORMATION));
            return;
        }

        Stage stage = (Stage) botonInicioSesion2.getScene().getWindow();
        stage.close();
        FXMLLoader loader = Main.abrirVentana("/vista/Menu.fxml");
        MenuControlador menu_c = loader.getController();
        menu_c.setListaUsuarios(listaUsuarios);
        menu_c.setProductoAdmin(producto_admin);
        menu_c.setUsuarioAdmin(user_admin);
        menu_c.setUsuario(user);

    }

    @FXML
    public void registrarse() {
        if (fieldNombreRegistro.getText().isEmpty() || fieldCorreoRegistro.getText().isEmpty() || fieldContrasenaRegistro.getText().isEmpty()) {
            Main.mostrarAlerta("Informacion", "Por favor, rellene todos los campos vacíos.", new Alert(Alert.AlertType.INFORMATION));
            return;
        }
        if(fieldNombreRegistro.getText().length() < 2) {
            Main.mostrarAlerta("Informacion", "Por favor, utilize una nombre mínimo de 3 digitos.", new Alert(Alert.AlertType.INFORMATION));
            fieldCorreoRegistro.requestFocus();
            return;
        }
        
        if(!validarCorreo(fieldCorreoRegistro.getText())) {
            Main.mostrarAlerta("Informacion", "Por favor, utilize un correo válido.", new Alert(Alert.AlertType.INFORMATION));
            fieldCorreoRegistro.requestFocus();
            return;
        }
        if(!validarContrasena(fieldContrasenaRegistro.getText())) {
            Main.mostrarAlerta("Informacion", "Por favor, utilize una contraseña mínimo de 6 digitos.", new Alert(Alert.AlertType.INFORMATION));
            fieldContrasenaRegistro.setText("");
            fieldContrasenaRegistro.requestFocus();
            return;
        }

        Usuario nuevoUsuario = new Usuario(fieldNombreRegistro.getText(), fieldCorreoRegistro.getText(), fieldContrasenaRegistro.getText());

        if (listaUsuarios.agregarUsuario(nuevoUsuario)) {
            // Guardar automáticamente en usuarios
            user_admin.agregarUsuario(nuevoUsuario);
            Main.mostrarAlerta("Registro exitoso", "Usuario registrado correctamente.", new Alert(Alert.AlertType.INFORMATION));
            label1.setVisible(true);
            botonRegistro.setVisible(true);
            botonInicioSesion1.setVisible(true);
            panelRegistro.setVisible(false);
            fieldNombreRegistro.setText("");
            fieldCorreoRegistro.setText("");
            fieldContrasenaRegistro.setText("");
        } else {
            Main.mostrarAlerta("Error", "No se puede utilizar este correo porque ya está registrado.", new Alert(Alert.AlertType.ERROR));
        }
    }
    
    private boolean validarCorreo(String correo) {
        return correo.matches("[\\w.%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
    }  
    private boolean validarContrasena(String contrasena) {
        return contrasena.matches(".{6,}");
    }
}
