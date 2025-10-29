package org.bunuelolovers.sgmms.sgmms_proyect.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SceneManager;

import java.io.IOException;
public class MainApp extends Application {

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.getInstance().setPrimaryStage(stage);
        SceneManager.getInstance().switchScene(SceneManager.SceneID.PLAYER_FORM, "Registro Operador Municipal");

        stage.setOnCloseRequest(event -> {
            event.consume();

            Platform.exit();
            System.exit(0);
        });
    }
}