package modelo;

import controladorvista.LoginControlador;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tienda.ListaProducto;
import tienda.ListaUsuario;
import tienda.ProductoAdministrador;
import tienda.UsuarioAdministrador;

public class Main extends Application {

    private static ListaUsuario listaUsuarios = new ListaUsuario();
    private static ListaProducto catalogo = new ListaProducto();
    private static ProductoAdministrador productoAdministrador = new ProductoAdministrador();
    private static UsuarioAdministrador usuarioAdministrador = new UsuarioAdministrador();

    private static Stage mainStage;
    private static Image logo;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        logo = new Image(getClass().getResourceAsStream("/images/logo.png"));

        catalogo = productoAdministrador.cargarProductos();
        listaUsuarios = usuarioAdministrador.cargarUsuarios(productoAdministrador);

        FXMLLoader loader = abrirVentana("/vista/Login.fxml");

        LoginControlador controller = loader.getController();
        controller.setListaUsuarios(listaUsuarios);
        controller.setProductoAdmin(productoAdministrador);
        controller.setUsuarioAdmin(usuarioAdministrador);
    }

    public static Stage getMainStage() {
        return mainStage;
    }
    
    public static void main(String args[]) {
        launch();
    }
    
    public static FXMLLoader abrirVentana(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));

        Parent root = loader.load();
        mainStage.setTitle("ShopGM");
        mainStage.getIcons().add(logo);
        mainStage.setScene(new Scene(root));
        mainStage.show();
        
        return loader;
    }
    
    public static void mostrarAlerta(String titulo, String mensaje, Alert tipo) {
        Alert alert = tipo;
        alert.setTitle(titulo);
        alert.setHeaderText(null);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/images/logo.png"));

        Label label = new Label(mensaje);
        label.setWrapText(true);
        label.setMaxWidth(350);
        label.setStyle("-fx-font-size: 16px; -fx-font-family: 'Segoe UI Bold';");

        alert.getDialogPane().setContent(label);
        DialogPane dialogPane = alert.getDialogPane();

        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.setStyle(
                "-fx-background-color: #FCF181;"
                + "-fx-font-size: 16px;"
                + "-fx-font-family: 'Segoe UI Bold';"
                + "-fx-background-radius: 24px;"
                + "-fx-border-radius: 24px;"
        );

        dialogPane.setStyle(
                "-fx-background-color: #FAF6EB;"
                + "-fx-border-radius: 24px;"
                + "-fx-background-radius: 24px;"
        );

        alert.showAndWait();
    }

}
