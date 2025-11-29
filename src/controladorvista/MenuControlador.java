package controladorvista;

import java.io.File;
import java.io.IOException;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Main;
import tienda.ListaProducto;
import tienda.ListaUsuario;
import tienda.NodoProducto;
import tienda.Producto;
import tienda.ProductoAdministrador;
import tienda.Usuario;
import tienda.UsuarioAdministrador;

public class MenuControlador implements Initializable {

    ProductoAdministrador productoAdmin;
    UsuarioAdministrador usuarioAdmin;
    private Map<String, Label> mapaCategorias;
    private Usuario usuario;
    private ListaUsuario listaUsuario;

    private List<TarjetaCarritoControlador> tarjetas = new ArrayList<>();
    private List<TarjetaProductoControlador> tarjetasProducto = new ArrayList<>();

    private final double costoEnvio = 10000;
    final String[] rutaImagen = {null}; // para almacenar la ruta elegida

    public MenuControlador() {
        mapaCategorias = new HashMap<>();
    }

    public VBox getContenedorDeseados() {
        return contenedorDeseados;
    }

    private void modoAdmin() {
        try {
            mostrarProductosPorCategoria(contenedorAdministrador);
        } catch (IOException ex) {
        }
        panelCatalogo.setVisible(false);
        menuAsistencia.setVisible(false);
        botonCarrito.setVisible(false);
        boton3_mlateral.setVisible(true);

    }

    private void modoUsuario() {
        try {
            mostrarProductosPorCategoria(contenedorCatalogo);
            mostrarProductosCarrito();
            cargarDeseados();
        } catch (IOException ex) {
        }
        panelCatalogo.setVisible(true);
        menuAsistencia.setVisible(true);
        botonCarrito.setVisible(true);
        boton3_mlateral.setVisible(false);
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        cargarNombreUsuario();
        
        if (usuario.getUserCorreo().equals("admin10@gmail.com") && usuario.getContrasena().equals("admin2025")) {
            modoAdmin();
        } else {
            modoUsuario();
        }
    }

    public void setListaUsuarios(ListaUsuario listaUsuarios) {
        this.listaUsuario = listaUsuarios;
    }

    public void setProductoAdmin(ProductoAdministrador producto_admin) {
        this.productoAdmin = producto_admin;
    }

    public void setUsuarioAdmin(UsuarioAdministrador user_admin) {
        this.usuarioAdmin = user_admin;
    }

    private boolean menuAbierto = false;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Pane panelCatalogo;
    @FXML
    private Pane panelDeseados;
    @FXML
    private Pane panelCarrito;
    @FXML
    private Pane panelHistorial;
    @FXML
    private Pane panelAdministrador;

    @FXML
    private Button botonHome;
    @FXML
    private Button botonDeseados;
    @FXML
    private Button botonHistorial;
    @FXML
    private Button botonCarrito;
    @FXML
    private Button botonPagar;

    @FXML
    private VBox menuDesplegable;
    @FXML
    private HBox menuAsistencia;

    @FXML
    private Button boton1_mlateral;
    @FXML
    private Button boton2_mlateral;
    @FXML
    private Button boton3_mlateral;
    @FXML
    private Button botonMenu;
    @FXML
    private Button botonMenu2;
    @FXML
    private Rectangle fondoOscuro;

    @FXML
    private Button botonHombre;
    @FXML
    private Button botonMujer;
    @FXML
    private Button botonAccesorios;

    @FXML
    private Label sinDeseados;
    @FXML
    private Label sinCarrito;
    @FXML
    private Label sinHistorial;

    @FXML
    private Label labelSubTotal;
    @FXML
    private Label labelEnvio;
    @FXML
    private Label labelTotal;

    @FXML
    private Label labelUsuario;

    @FXML
    private TextField fieldBuscarImagen;

    @FXML
    private TextField fieldDescripcion;

    @FXML
    private TextField fieldImagen;

    @FXML
    private TextField fieldNombre;

    @FXML
    private TextField fieldPrecio;

    @FXML
    private TextField fieldStock;

    @FXML
    private ComboBox<?> comboCategoria;

    @FXML
    private ScrollPane scrollCatalogo;
    @FXML
    private ScrollPane scrollDeseados;
    @FXML
    private ScrollPane scrollHistorial;
    @FXML
    private ScrollPane scrollCarrito;
    @FXML
    private VBox contenedorCatalogo;
    @FXML
    private VBox contenedorDeseados;
    @FXML
    private VBox contenedorCarrito;
    @FXML
    private VBox contenedorHistorial;
    @FXML
    private VBox contenedorAdministrador;

    @FXML
    private TextField fieldBuscar;

    @FXML
    public void cerrarSesion(ActionEvent event) throws IOException {
        Stage stage = (Stage) boton1_mlateral.getScene().getWindow();
        stage.close();

        FXMLLoader loader = Main.abrirVentana("/vista/Login.fxml");
        LoginControlador login_c = loader.getController();
        login_c.setListaUsuarios(listaUsuario);
        login_c.setProductoAdmin(productoAdmin);
        login_c.setUsuarioAdmin(usuarioAdmin);
    }

    @FXML
    public void buscarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );
        File archivo = fileChooser.showOpenDialog(Main.getMainStage());
        if (archivo != null) {
            rutaImagen[0] = archivo.getAbsolutePath().replace("\\", "/");
            fieldImagen.setText("" + archivo.getName());
        }
    }

    @FXML
    public void agregarProducto() {

        if (fieldNombre.getText().isEmpty() || fieldNombre.getText().isEmpty() || fieldNombre.getText().isEmpty()
                || fieldNombre.getText().isEmpty() || fieldNombre.getText().isEmpty() || rutaImagen[0] == null) {
            Main.mostrarAlerta("Rellene los campos", "Por favor, rellene todos los campos, agregue una imagen e intente nuevamente.", new Alert(Alert.AlertType.ERROR));
        } else {
            try {

                productoAdmin.agregarProducto(new Producto("", fieldNombre.getText(), fieldDescripcion.getText(),
                        comboCategoria.getSelectionModel().getSelectedItem().toString(), Integer.parseInt(fieldStock.getText()),
                        Double.parseDouble(fieldPrecio.getText()), ""), rutaImagen[0]);
                mostrarProductosPorCategoria(contenedorAdministrador);
                fieldNombre.clear();
                fieldDescripcion.clear();
                comboCategoria.getSelectionModel().clearSelection();
                comboCategoria.setValue(null);
                fieldStock.clear();
                fieldPrecio.clear();
                fieldImagen.clear();
                Main.mostrarAlerta("Éxito", "Producto guardado correctamente.", new Alert(Alert.AlertType.CONFIRMATION));
            } catch (IOException ex) {
                Logger.getLogger(tienda.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void abrirAdministrador(ActionEvent event) throws IOException {
        moverMenu();
        FadeTransition animFondo = new FadeTransition(Duration.millis(300), fondoOscuro);

        animFondo.setFromValue(0.0);
        animFondo.setToValue(0.6);
        animFondo.setOnFinished(evento -> {
            fondoOscuro.setVisible(true);
        });
        animFondo.play();
        animarAbrirVentana(panelAdministrador);
    }

    @FXML
    public void salirAdministrador(ActionEvent event) throws IOException {
        FadeTransition animFondo = new FadeTransition(Duration.millis(300), fondoOscuro);
        animFondo.setToValue(0);
        animFondo.setOnFinished(e -> fondoOscuro.setVisible(false));
        animFondo.play();
        animarCerrarVentana(panelAdministrador);

    }

    @FXML
    public void pagarProductos(ActionEvent event) {

        for (TarjetaCarritoControlador t : tarjetas) {
            if (usuario.agregarAHistorial(t.getProducto())) {
                Main.mostrarAlerta("Pago Éxitoso", "Su pago se ha realizado con éxito.", new Alert(Alert.AlertType.INFORMATION));
            }
        }
        usuario.getListaCarrito().limpiar();
        tarjetas.clear();
        usuarioAdmin.guardarUsuarios(listaUsuario);
        mostrarProductosCarrito();
        actualizarTotal();
    }

    @FXML
    public void mostrarHome(ActionEvent event) {
        cambiarColorBoton(botonHome);
        animarCerrarVentana(panelDeseados);
        animarCerrarVentana(panelHistorial);
        animarCerrarVentana(panelCarrito);
        animarAbrirVentana(panelCatalogo);
    }

    @FXML
    public void mostrarDeseados(ActionEvent event) {
        cambiarColorBoton(botonDeseados);
        mostrarProductosDeseados(contenedorDeseados);
        animarCerrarVentana(panelHistorial);
        animarCerrarVentana(panelCatalogo);
        animarCerrarVentana(panelCarrito);
        animarAbrirVentana(panelDeseados);
    }

    @FXML
    public void mostrarHistorial(ActionEvent event) {
        cambiarColorBoton(botonHistorial);
        mostrarProductosHistorial();
        animarCerrarVentana(panelCatalogo);
        animarCerrarVentana(panelDeseados);
        animarCerrarVentana(panelCarrito);
        animarAbrirVentana(panelHistorial);
    }

    @FXML
    public void mostrarCarrito(ActionEvent event) {
        cambiarColorBoton(botonCarrito);
        mostrarProductosCarrito();
        actualizarTotal();
        animarCerrarVentana(panelCatalogo);
        animarCerrarVentana(panelDeseados);
        animarCerrarVentana(panelHistorial);
        animarAbrirVentana(panelCarrito);
    }

    public void cambiarColorBoton(Button boton) {

        if (panelCatalogo.isVisible()) {
            botonHome.setGraphic(new ImageView(new Image(getClass().getResource("/images/home_black.png").toExternalForm(), 30, 30, true, true)));
            aplicarColorBoton(boton);
            return;
        }
        if (panelDeseados.isVisible()) {
            botonDeseados.setGraphic(new ImageView(new Image(getClass().getResource("/images/hearth_black.png").toExternalForm(), 30, 30, true, true)));
            aplicarColorBoton(boton);
            return;
        }
        if (panelHistorial.isVisible()) {
            botonHistorial.setGraphic(new ImageView(new Image(getClass().getResource("/images/reciente_black.png").toExternalForm(), 30, 30, true, true)));
            aplicarColorBoton(boton);
            return;
        }
        if (panelCarrito.isVisible()) {
            botonCarrito.setGraphic(new ImageView(new Image(getClass().getResource("/images/shoppingcar_black.png").toExternalForm(), 30, 30, true, true)));
            aplicarColorBoton(boton);
        }
    }

    public void aplicarColorBoton(Button boton) {
        if (boton == botonHome) {
            boton.setGraphic(new ImageView(new Image(getClass().getResource("/images/home_red.png").toExternalForm(), 30, 30, true, true)));
        }
        if (boton == botonDeseados) {
            boton.setGraphic(new ImageView(new Image(getClass().getResource("/images/hearth_red.png").toExternalForm(), 30, 30, true, true)));
        }
        if (boton == botonHistorial) {
            boton.setGraphic(new ImageView(new Image(getClass().getResource("/images/reciente_red.png").toExternalForm(), 30, 30, true, true)));
        }
        if (boton == botonCarrito) {
            boton.setGraphic(new ImageView(new Image(getClass().getResource("/images/shoppingcar_red.png").toExternalForm(), 30, 30, true, true)));
        }
    }

    public void animarAbrirVentana(Pane ventana) {
        FadeTransition animacion = new FadeTransition(Duration.millis(300), ventana);
        ventana.setVisible(true);

        animacion.setFromValue(0.0);
        animacion.setToValue(1.0);
        animacion.setCycleCount(1);
        animacion.play();
    }

    public void animarCerrarVentana(Pane ventana) {
        FadeTransition animacion = new FadeTransition(Duration.millis(300), ventana);

        animacion.setFromValue(1.0);
        animacion.setToValue(0.0);
        animacion.setCycleCount(1);
        animacion.setOnFinished(e -> {
            ventana.setVisible(false);
        });
        animacion.play();

    }

    @FXML
    public void seleccionarCategoriaHombre(ActionEvent event) {
        Label destino = mapaCategorias.get("hombre");
        scrollToNode(scrollCatalogo, destino);
        aplicarSeleccion(botonHombre);
    }

    @FXML
    public void seleccionarCategoriaMujer(ActionEvent event) {
        Label destino = mapaCategorias.get("mujer");
        scrollToNode(scrollCatalogo, destino);
        aplicarSeleccion(botonMujer);
    }

    @FXML
    public void seleccionarCategoriaAccesorios(ActionEvent event) {
        Label destino = mapaCategorias.get("accesorios");
        scrollToNode(scrollCatalogo, destino);
        aplicarSeleccion(botonAccesorios);
    }

    public void scrollToNode(ScrollPane scrollPane, Node node) {
        if (node == null || scrollPane.getContent() == null) {
            return;
        }

        // Posición del nodo relativa al contenido
        double contentHeight = scrollPane.getContent().getBoundsInLocal().getHeight();
        double viewportHeight = scrollPane.getViewportBounds().getHeight();
        double nodeY = node.getBoundsInParent().getMinY();

        // Normalizar posición al rango [0,1]
        double vValue = nodeY / (contentHeight - viewportHeight);
        vValue = Math.min(Math.max(vValue, 0), 1); // Limitar entre 0 y 1

        // Animar suavemente el scroll
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(scrollPane.vvalueProperty(), vValue);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv); // 300ms de duración
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    private void aplicarSeleccion(Button botonSeleccionado) {

        Button[] botones = {botonHombre, botonMujer, botonAccesorios};
        for (Button btn : botones) {
            if (btn == botonSeleccionado) {
                btn.setStyle("-fx-border-color: transparent transparent #EB7763 transparent;" + "-fx-border-width: 0 0 4 0;"
                        + "-fx-background-color: transparent;" + "-fx-padding: 5 10 5 10;" + "-fx-text-fill: #EB7763;");
            } else {
                btn.setStyle("-fx-background-color: transparent");
            }
        }
    }

    @FXML
    public void abrirMenuDesplegable(ActionEvent event) {
        moverMenu();
    }

    @FXML
    public void ocultarMenu(ActionEvent event) {
        moverMenu();
    }

    public void moverMenu() {
        double destinoX = menuAbierto ? -270 : 0;

        // Animación del menú
        TranslateTransition animMenu = new TranslateTransition(Duration.millis(300), menuDesplegable);
        animMenu.setToX(destinoX);
        animMenu.setInterpolator(Interpolator.EASE_BOTH);

        // Animación del fondo oscuro
        FadeTransition animFondo = new FadeTransition(Duration.millis(300), fondoOscuro);
        if (menuAbierto) {
            animFondo.setToValue(0);
            animFondo.setOnFinished(e -> fondoOscuro.setVisible(false));;
        } else {
            fondoOscuro.setVisible(true);
            animFondo.setToValue(0.6);
        }

        animMenu.play();
        animFondo.play();

        menuAbierto = !menuAbierto;
    }

    @FXML
    public void salirCatalogo(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fieldBuscar.setOnKeyReleased(e -> {
            try {
                buscarProductos(productoAdmin.cargarProductos(), fieldBuscar.getText());
            } catch (IOException ex) {
                Logger.getLogger(MenuControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        scrollCatalogo.vvalueProperty().addListener((obs, oldVal, newVal) -> {
            Button botonVisible = getBotonCategoriaVisiblePreciso();
            if (botonVisible != null) {
                aplicarSeleccion(botonVisible);
            }
        });
    }

    private Button getBotonCategoriaVisiblePreciso() {
        double viewportTop = scrollCatalogo.getVvalue() * (scrollCatalogo.getContent().getBoundsInLocal().getHeight() - scrollCatalogo.getViewportBounds().getHeight());
        double viewportCenter = viewportTop + scrollCatalogo.getViewportBounds().getHeight() / 2;

        String categoriaVisible = null;
        double minDistance = Double.MAX_VALUE;

        for (Map.Entry<String, Label> entry : mapaCategorias.entrySet()) {
            Label label = entry.getValue();
            double labelCenter = label.getBoundsInParent().getMinY() + label.getHeight() / 2;
            double distance = Math.abs(labelCenter - viewportCenter);

            if (distance < minDistance) {
                minDistance = distance;
                categoriaVisible = entry.getKey();
            }
        }

        if (categoriaVisible != null) {
            switch (categoriaVisible.toLowerCase()) {
                case "hombre":
                    return botonHombre;
                case "mujer":
                    return botonMujer;
                case "accesorios":
                    return botonAccesorios;
            }
        }

        return null;
    }

    private void cargarNombreUsuario() {
        labelUsuario.setText(usuario.getUserName());
    }

    public void mostrarProductosDeseados(VBox contendorDeseados) {

        contenedorDeseados.getChildren().clear();
        FlowPane contenedorTarjetas = new FlowPane();

        contenedorTarjetas.setHgap(15);
        contenedorTarjetas.setVgap(15);
        contenedorTarjetas.setPrefWrapLength(900);
        contenedorTarjetas.setPadding(new Insets(50, 0, 0, 50));

        contenedorTarjetas.setCache(true);
        contenedorTarjetas.setCacheHint(CacheHint.SPEED);

        NodoProducto nodo = usuario.getListaDeseados().getCabeza();

        // Mostrar o ocultar mensaje
        sinDeseados.setVisible(nodo == null);

        // Recorrer lista enlazada
        for (; nodo != null; nodo = nodo.siguiente) {
            AnchorPane tarjeta = crearTarjetaProducto(nodo.producto, contendorDeseados);
            tarjeta.setCache(true);
            tarjeta.setCacheHint(CacheHint.SPEED);
            if (tarjeta != null) {
                contenedorTarjetas.getChildren().add(tarjeta);
            }
        }

        contenedorDeseados.getChildren().add(contenedorTarjetas);

    }

    private void mostrarProductosHistorial() {

        contenedorHistorial.getChildren().clear();

        FlowPane contenedorTarjetas = new FlowPane();
        contenedorTarjetas.setHgap(15);
        contenedorTarjetas.setVgap(15);
        contenedorTarjetas.setPrefWrapLength(900);
        contenedorTarjetas.setPadding(new Insets(50, 0, 0, 50));

        contenedorTarjetas.setCache(true);
        contenedorTarjetas.setCacheHint(CacheHint.SPEED);

        NodoProducto nodo = usuario.getListaHistorial().getCabeza();

        // Mostrar o ocultar mensaje
        sinHistorial.setVisible(nodo == null);

        // Recorrer lista enlazada
        for (; nodo != null; nodo = nodo.siguiente) {
            AnchorPane tarjeta = crearTarjetaProductoHistorial(nodo.producto);
            tarjeta.setCache(true);
            tarjeta.setCacheHint(CacheHint.SPEED);
            if (tarjeta != null) {
                contenedorTarjetas.getChildren().add(tarjeta);
            }
        }

        contenedorHistorial.getChildren().add(contenedorTarjetas);
    }

    private void mostrarProductosCarrito() {
        tarjetas.clear();

        contenedorCarrito.getChildren().clear();
        contenedorCarrito.setPadding(new Insets(20, 0, 0, 20));

        NodoProducto nodo = usuario.getListaCarrito().getCabeza();

        sinCarrito.setVisible(nodo == null);

        for (; nodo != null; nodo = nodo.siguiente) {
            AnchorPane tarjetaCarrito = crearTarjetaProductoCarrito(nodo.producto);
            tarjetaCarrito.setCache(true);
            tarjetaCarrito.setCacheHint(CacheHint.SPEED);
            if (tarjetaCarrito != null) {
                contenedorCarrito.getChildren().add(tarjetaCarrito);
            }
        }
    }

    private void mostrarProductosPorCategoria(VBox contenedorCatalogo) throws IOException {
        tarjetasProducto.clear();
        ListaProducto lista = productoAdmin.cargarProductos();
        String titulo = "";

        if (lista == null || lista.getCabeza() == null) {
            return;
        }

        contenedorCatalogo.getChildren().clear();

        NodoProducto actual = lista.getCabeza();

        while (actual != null) {

            String categoria = actual.producto.getCategoria();
            if (categoria == null) {
                categoria = "Sin categoría";
            }

            boolean yaMostrada = false;
            NodoProducto busc = lista.getCabeza();

            while (busc != actual) {
                if (busc.producto != null
                        && busc.producto.getCategoria() != null
                        && busc.producto.getCategoria().equalsIgnoreCase(categoria)) {

                    yaMostrada = true;
                    break;
                }
                busc = busc.siguiente;
            }

            if (yaMostrada) {
                actual = actual.siguiente;
                continue;
            }

            titulo = categoria.substring(0, 1).toUpperCase() + categoria.substring(1);

            Label tituloCategoria = new Label(titulo);
            tituloCategoria.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
            tituloCategoria.setPadding(new Insets(50, 0, 50, 0));

            mapaCategorias.put(categoria, tituloCategoria);

            FlowPane contenedorTarjetas = new FlowPane();
            contenedorTarjetas.setHgap(15);
            contenedorTarjetas.setVgap(15);
            contenedorTarjetas.setPrefWrapLength(900);

            contenedorTarjetas.setCache(true);
            contenedorTarjetas.setCacheHint(CacheHint.SPEED);

            NodoProducto aux = lista.getCabeza();
            while (aux != null) {

                String catAux = aux.producto.getCategoria();
                if (catAux == null) {
                    catAux = "Sin categoría";
                }

                if (categoria.equalsIgnoreCase(catAux)) {

                    AnchorPane tarjeta = crearTarjetaProducto(aux.producto, contenedorCatalogo);
                    tarjeta.setCache(true);
                    tarjeta.setCacheHint(CacheHint.SPEED);
                    if (tarjeta != null) {
                        contenedorTarjetas.getChildren().add(tarjeta);
                    }
                }

                aux = aux.siguiente;
            }

            contenedorCatalogo.getChildren().addAll(tituloCategoria, contenedorTarjetas);

            actual = actual.siguiente;
        }
        scrollCatalogo.layout();
    }

    public void buscarProductos(ListaProducto lista, String filtro) throws IOException {
        filtro = filtro.toLowerCase().trim();

        if (filtro.isEmpty()) {
            mostrarProductosPorCategoria(contenedorCatalogo);
            return;
        }

        // 1. Limpiar el VBox donde van los resultados
        contenedorCatalogo.getChildren().clear();

        // 2. Crear UN solo FlowPane para las tarjetas encontradas
        FlowPane contenedorTarjetas = new FlowPane();
        contenedorTarjetas.setHgap(15);
        contenedorTarjetas.setVgap(15);
        contenedorTarjetas.setPrefWrapLength(900);
        contenedorTarjetas.setPadding(new Insets(50, 0, 0, 0));

        contenedorTarjetas.setCache(true);
        contenedorTarjetas.setCacheHint(CacheHint.SPEED);

        // 3. Recorrer la lista enlazada
        NodoProducto actual = lista.getCabeza();

        while (actual != null) {
            Producto p = actual.producto;

            String nombre = p.getNombre().toLowerCase();
            String categoria = p.getCategoria().toLowerCase();

            // 4. Si coincide agrega tarjeta
            if (nombre.contains(filtro)
                    || categoria.contains(filtro)) {

                Node tarjeta = crearTarjetaProducto(p, null);
                tarjeta.setCache(true);
                tarjeta.setCacheHint(CacheHint.SPEED);
                contenedorTarjetas.getChildren().add(tarjeta);
                animarAparicion(tarjeta);
            }

            actual = actual.siguiente;
        }

        // 5. Agregar solo un contenedor con todas las tarjetas
        contenedorCatalogo.getChildren().add(contenedorTarjetas);
    }

    private AnchorPane crearTarjetaProducto(Producto producto, VBox vbox) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/tarjeta_producto.fxml"));
            AnchorPane tarjeta = loader.load();
            tarjeta.setCache(true);
            tarjeta.setCacheHint(CacheHint.SPEED);

            TarjetaProductoControlador controller = loader.getController();
            controller.setUsuarioAdmin(usuarioAdmin);
            controller.setListaUsuarios(listaUsuario);
            controller.setUsuario(usuario);
            controller.setProducto(producto);

            if (vbox == contenedorAdministrador) {
                controller.ocultarBotonesTarjeta();
            }
            if (vbox == contenedorDeseados) {
                controller.mostrarModeloDeseado();
                controller.setMenuC(this);
            }
            if (vbox == contenedorCatalogo) {
                tarjetasProducto.add(controller);
            }

            tarjeta.setPrefWidth(160);   // igual que en tu código actual
            tarjeta.setPrefHeight(240);

            return tarjeta;

        } catch (IOException e) {
            return null;
        }
    }

    private AnchorPane crearTarjetaProductoHistorial(Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/tarjeta_producto.fxml"));
            AnchorPane tarjeta = loader.load();
            tarjeta.setCache(true);
            tarjeta.setCacheHint(CacheHint.SPEED);

            TarjetaProductoControlador controller = loader.getController();
            controller.setUsuarioAdmin(usuarioAdmin);
            controller.setListaUsuarios(listaUsuario);
            controller.setUsuario(usuario);
            controller.setProducto(producto);
            controller.ocultarBotonesTarjeta();

            tarjeta.setPrefWidth(160);
            tarjeta.setPrefHeight(240);

            return tarjeta;

        } catch (IOException e) {
            return null;
        }
    }

    private AnchorPane crearTarjetaProductoCarrito(Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/tarjeta_carrito.fxml"));
            AnchorPane tarjeta = loader.load();
            tarjeta.setCache(true);
            tarjeta.setCacheHint(CacheHint.SPEED);

            TarjetaCarritoControlador controller = loader.getController();
            controller.setUsuarioAdmin(usuarioAdmin);
            controller.setListaUsuarios(listaUsuario);
            controller.setUsuario(usuario);
            controller.setProducto(producto);

            // IMPORTANTE: registrar esta tarjeta
            tarjetas.add(controller);
            controller.setOnCantidadCambiada(() -> {
                actualizarTotal();
            });

            return tarjeta;

        } catch (IOException e) {
            return null;
        }
    }

    private void actualizarTotal() {
        double total = calcularTotal();

        if (total <= 0) {
            labelSubTotal.setText("Subtotal");
            labelEnvio.setText("Envío");
            labelTotal.setText("Total");
        } else {
            labelSubTotal.setText("Subtotal     " + total);
            labelEnvio.setText("Envío          " + costoEnvio);
            labelTotal.setText("Total           " + (total + costoEnvio));
        }
    }

    private double calcularTotal() {
        double total = 0;

        for (TarjetaCarritoControlador t : tarjetas) {
            total += t.getCantidad() * t.getProducto().getPrecio();
        }

        return total;
    }

    public void cargarDeseados() {
        for (TarjetaProductoControlador tp : tarjetasProducto) {
            if (usuario.getListaDeseados().existeProducto(tp.getProducto().getId())) {
                tp.ponerEnDeseado();
            } else {
                tp.ponerEnNoDeseado();
            }
        }
    }

    private void animarAparicion(Node node) {
        // Fade de 0 → 1
        FadeTransition fade = new FadeTransition(Duration.millis(300), node);
        fade.setFromValue(0);
        fade.setToValue(1);

        // Desplazamiento suave desde 15 px abajo
        TranslateTransition move = new TranslateTransition(Duration.millis(300), node);
        move.setFromY(15);
        move.setToY(0);

        ParallelTransition animacion = new ParallelTransition(fade, move);
        animacion.play();
    }
}
