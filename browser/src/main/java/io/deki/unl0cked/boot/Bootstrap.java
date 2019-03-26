package io.deki.unl0cked.boot;

import io.deki.unl0cked.browser.BrowserController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * Created by Deki on 25.03.2019
 */
public class Bootstrap extends Application {

    private final int F7_KEY_CODE = 118;

    private static Stage stage;
    private static BrowserController controller;
    private static Group group;
    private static Scene scene;

    private static JFrame frame = new JFrame("");

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        System.out.println("Launching unl0cked browser");
        setStage(stage);
        Group group = new Group();
        Scene scene = new Scene(group, 600, 400);
        setGroup(group);
        setScene(scene);
        stage.setScene(scene);
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/fxml/Browser.fxml"));
        setController(loader.getController());
        group.getChildren().add(root);
        stage.setTitle("Unl0cked Browser v" + getClass().getPackage().getImplementationVersion());
        stage.setAlwaysOnTop(true);
        Platform.setImplicitExit(false);
        initKeyboardShortcuts();
        initStealthFrame();
        stage.show();
    }

    /**
     * Add keyboard shortcuts for easy hide/show of the browser (F7 key by default).
     * We need 2 different event handlers for swing/jfx.
     */
    private void initKeyboardShortcuts() {
        getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            KeyCode keyCode = e.getCode();
            if (keyCode == KeyCode.F7) {
                getFrame().setVisible(true);
                getFrame().toFront();
                getStage().hide();
            }
        });
        getFrame().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == F7_KEY_CODE) {
                    Platform.runLater(() -> getStage().show());
                    getFrame().setVisible(false);
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {

            }
        });
    }

    /**
     * Init the frame we need to reopen the browser.
     */
    private void initStealthFrame() {
        getFrame().setAlwaysOnTop(true);
        getFrame().setUndecorated(true);
        getFrame().setSize(20, 50);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        System.out.println("Screen dimensions: " + rect.getWidth() + ", " + rect.getHeight());
        int x = (int) rect.getMaxX() - 15 /* - getFrame().getWidth() */;
        int y = (int) rect.getMaxY() - getFrame().getHeight();
        getFrame().setLocation(x, y);
    }

    public static Stage getStage() {
        return stage;
    }

    public static Scene getScene() {
        return scene;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static BrowserController getController() {
        return controller;
    }

    public static Group getGroup() {
        return group;
    }

    public static void setScene(Scene scene) {
        Bootstrap.scene = scene;
    }

    public static void setGroup(Group group) {
        Bootstrap.group = group;
    }

    public static void setController(BrowserController controller) {
        Bootstrap.controller = controller;
    }

    public static void setStage(Stage stage) {
        Bootstrap.stage = stage;
    }
}
