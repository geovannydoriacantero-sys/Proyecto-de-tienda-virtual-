package tienda;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

public class Main extends Application {

    private GridPane gridPane; // grid de tarjetas
    private ProductoAdministrador producto_admin;
    private BorderPane root;
    private Scene scene;
    private Stage stage;

    public Main() {
        producto_admin = new ProductoAdministrador();
    }

    /*
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        root = new BorderPane();

        // Crear el menú
        MenuBar menuBar = new MenuBar();

        Menu menuOpciones = new Menu("Opciones");

        MenuItem itemCatalogo = new MenuItem("Catálogo");
        MenuItem itemAgregar = new MenuItem("Agregar producto");
        MenuItem itemSalir = new MenuItem("Salir");

        // Eventos
        itemCatalogo.setOnAction(e -> mostrarCatalogo());
        itemAgregar.setOnAction(e -> mostrarAgregarProducto());
        itemSalir.setOnAction(e -> Platform.exit());

        menuOpciones.getItems().addAll(itemCatalogo, itemAgregar, new SeparatorMenuItem(), itemSalir);

        menuBar.getMenus().add(menuOpciones);

        // MenuBar arriba
        root.setTop(menuBar);

        // Vista inicial
        mostrarCatalogo();

        scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Sistema de Productos");
        stage.show();
    }

    // 👇 Catálogo
    private void mostrarCatalogo() {
        CatalogoView catalogo = new CatalogoView();
        root.setCenter(catalogo.getView());
    }

    public class AgregarProductoView {

        private VBox root;

        public AgregarProductoView() {
            root = new VBox(10);
            root.setPadding(new Insets(20));

            TextField txtNombre = new TextField();
            txtNombre.setPromptText("Nombre del producto");

            TextField txtPrecio = new TextField();
            txtPrecio.setPromptText("Precio");

            TextField txtRutaImagen = new TextField();
            txtRutaImagen.setPromptText("Ruta de imagen");

            Button btnGuardar = new Button("Guardar");

            btnGuardar.setOnAction(e -> {

                // Mensaje
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Producto guardado");
                a.show();
            });

            root.getChildren().addAll(txtNombre, txtPrecio, txtRutaImagen, btnGuardar);
        }

        public VBox getView() {
            return root;
        }
    }

    // 👇 Formulario agregar producto
    private void mostrarAgregarProducto() {
        AgregarProductoView agregar = new AgregarProductoView();
        root.setCenter(agregar.getView());
    }

    public class CatalogoView {

        private ScrollPane scroll;
        private FlowPane flow;

        public CatalogoView() {
            flow = new FlowPane();
            flow.setPadding(new Insets(20));
            flow.setHgap(20);
            flow.setVgap(20);

            // Aquí cargas tus productos desde la lista
            ListaProducto productos = producto_admin.cargarProductos();
            NodoProducto p = productos.getCabeza();

            while (p != null) {
                Producto prod = (Producto) p.producto;
                VBox card = crearCard(prod);
                flow.getChildren().add(card);
                p = p.siguiente;
            }

            scroll = new ScrollPane(flow);
        }

        private VBox crearCard(Producto p) {
            VBox card = new VBox();
            card.setSpacing(5);
            card.setPadding(new Insets(10));
            card.setPrefWidth(150);
            card.setStyle("-fx-background-color: white; -fx-border-color: gray; -fx-border-radius: 8; -fx-background-radius: 8;");

            ImageView img = new ImageView(new Image("file:" + p.getRutaImagen()));
            img.setFitHeight(120);
            img.setFitWidth(120);
            img.setPreserveRatio(true);

            Label nombre = new Label(p.getNombre());
            Label precio = new Label("$" + p.getPrecio());

            card.getChildren().addAll(img, nombre, precio);
            return card;
        }

        public ScrollPane getView() {
            return scroll;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}*/
    @Override
    public void start(Stage stage) {
        // Campos de entrada
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre del producto");

        TextField descripcionField = new TextField();
        descripcionField.setPromptText("Descripcion");

        TextField categoriaField = new TextField();
        categoriaField.setPromptText("Categoria");

        TextField stockField = new TextField();
        stockField.setPromptText("Stock");

        TextField precioField = new TextField();
        precioField.setPromptText("Precio");

        Label imagenLabel = new Label("Ninguna imagen seleccionada");
        Button cargarImagenBtn = new Button("Cargar imagen");

        final String[] rutaImagen = {null}; // para almacenar la ruta elegida

        cargarImagenBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
            );
            File archivo = fileChooser.showOpenDialog(stage);
            if (archivo != null) {
                rutaImagen[0] = archivo.getAbsolutePath().replace("\\", "/");
                imagenLabel.setText("Imagen seleccionada: " + archivo.getName());
            }
        });

        Button agregarBtn = new Button("Agregar producto");
        agregarBtn.setOnAction(e -> {

            if (nombreField.getText().isEmpty() || rutaImagen[0] == null) {
                mostrarAlerta("Faltan datos", "Debe ingresar un nombre y seleccionar una imagen.");
                return;
            }
            try {

                producto_admin.agregarProducto(new Producto("", nombreField.getText(), descripcionField.getText(),
                        categoriaField.getText(), Integer.parseInt(stockField.getText()),
                        Double.parseDouble(precioField.getText()), ""), rutaImagen[0]);
                mostrarProductos();
                nombreField.clear();
                descripcionField.clear();
                categoriaField.clear();
                stockField.clear();
                precioField.clear();
                imagenLabel.setText("Ninguna imagen seleccionada");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        HBox form = new HBox(10, nombreField, descripcionField, categoriaField, stockField, precioField, cargarImagenBtn, imagenLabel, agregarBtn);
        form.setPadding(new Insets(10));
        form.setAlignment(Pos.CENTER);

        // GridPane
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(15);
        gridPane.setVgap(15);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        VBox root = new VBox(10, form, scrollPane);
        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Tienda GMShop");
        stage.setScene(scene);
        stage.show();

        // Cargar productos guardados
        mostrarProductos();
    }

    public void mostrarProductos() {
        // 1. Cargar la lista desde el archivo
        ListaProducto lista = producto_admin.cargarProductos();

        // 2. Limpiar la interfaz
        gridPane.getChildren().clear();

        // 3. Recorrer la lista enlazada
        NodoProducto actual = lista.getCabeza();

        int col = 0;
        int row = 0;
        int maxColumns = 4; // tarjetas por fila

        while (actual != null) {
            Producto p = actual.producto;

            VBox card = crearTarjetaProducto(p);

            gridPane.add(card, col, row);
            col++;

            if (col == maxColumns) {
                col = 0;
                row++;
            }

            actual = actual.siguiente;
        }
    }

    // Tarjeta tipo Temu
    private VBox crearTarjetaProducto(Producto p) {
        VBox card = new VBox();
        card.setSpacing(5);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: #ccc; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6,0,0,2);");
        card.setPrefSize(130, 180);

        ImageView img = new ImageView();
        try {
            img.setImage(new Image("file:" + p.getRutaImagen()));
        } catch (Exception e) {
        }
        img.setFitWidth(100);
        img.setFitHeight(100);
        img.setPreserveRatio(true);

        Label nombre = new Label(p.getNombre());
        nombre.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        Label descripcion = new Label(p.getDescripcion());
        descripcion.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        Label stock = new Label("x" + p.getStock());
        descripcion.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        Label precio = new Label("$" + p.getPrecio());
        precio.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60;");

        card.getChildren().addAll(img, nombre, descripcion, stock, precio);
        return card;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /*public static void main(String[] args) {
        launch();
    }*/

}
