package io.deki.unl0cked.boot;

import io.deki.unl0cked.browser.BrowserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Deki on 25.03.2019
 */
public class Bootstrap extends Application {

    private static Stage stage;
    private static BrowserController controller;
    private static Group group;
    private static Scene scene;

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
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static Scene getScene() {
        return scene;
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
