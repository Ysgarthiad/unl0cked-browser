package io.deki.unl0cked.browser;

import io.deki.unl0cked.boot.Bootstrap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.WindowEvent;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created by Deki on 25.03.2019
 */
public class BrowserController {

    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0";
    private final File LOCAL_STORAGE = new File("data");

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public BorderPane rootPane;

    @FXML
    public MenuBar menuBar;

    @FXML
    public WebView webView;

    @FXML
    public TextField urlBar;

    @FXML
    public Button backButton, forwardButton;

    @FXML
    public void initialize() {
        System.out.println("Initializing controller");
        getEngine().setOnStatusChanged(e -> getUrlBar().setText(getEngine().getLocation()));
        enableLocalStorage();
        installTrustingCert();
        bindDimensionProperties();
        navigationButtons();
        initMenu();
        getEngine().setUserAgent(USER_AGENT);
        getEngine().load("https://google.com");
    }

    /**
     * Add action listeners to the navigation buttons.
     */
    private void navigationButtons() {
        backButton.setOnAction(e -> {
            WebHistory history = getEngine().getHistory();
            int size = history.getEntries().size();
            int index = history.getCurrentIndex();
            Platform.runLater(() -> history.go((size > 1 && index > 0) ? -1 : 0));
        });
        forwardButton.setOnAction(e -> {
            WebHistory history = getEngine().getHistory();
            int size = history.getEntries().size();
            int index = history.getCurrentIndex();
            Platform.runLater(() -> history.go((size > 1 && index < size - 1) ? 1 : 0));
        });
    }

    /**
     * Add elements to the top menu.
     */
    private void initMenu() {
        Menu fileMenu = new Menu("File");
        Menu navigationMenu = new Menu("Navigate");

        MenuItem close = new MenuItem("Close");
        close.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(close);

        MenuItem googleNav = new MenuItem("Google");
        googleNav.setOnAction(e -> getEngine().load("https://google.com"));
        navigationMenu.getItems().add(googleNav);

        getMenuBar().getMenus().add(fileMenu);
        getMenuBar().getMenus().add(navigationMenu);
    }

    /**
     * Makes the components resize according to window size
     */
    private void bindDimensionProperties() {
        Bootstrap.getStage().addEventHandler(WindowEvent.WINDOW_SHOWING, e -> {
            getAnchorPane().prefHeightProperty().bind(Bootstrap.getScene().heightProperty());
            getAnchorPane().prefWidthProperty().bind(Bootstrap.getScene().widthProperty());
            getMenuBar().prefWidthProperty().bind(Bootstrap.getScene().widthProperty());
        });
    }

    /**
     * Tells the webview to store data, enables cookies ect.
     */
    private void enableLocalStorage() {
        getWebView().setCache(true);
        getWebView().setCacheHint(CacheHint.SPEED);
        getEngine().setUserDataDirectory(LOCAL_STORAGE);
    }

    /**
     * Create a trust manager that accepts all issuers and installs it.
     * WARNING: this leaves SSL connections vulnerable to MITM attacks.
     */
    private void installTrustingCert() {
        TrustManager[] trustManager = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustManager, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public BorderPane getRootPane() {
        return rootPane;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public TextField getUrlBar() {
        return urlBar;
    }

    public WebView getWebView() {
        return webView;
    }

    public WebEngine getEngine() {
        return webView.getEngine();
    }
}
