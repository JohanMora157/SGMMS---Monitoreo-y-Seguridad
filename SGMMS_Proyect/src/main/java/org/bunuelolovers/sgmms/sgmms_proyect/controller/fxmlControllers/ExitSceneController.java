package org.bunuelolovers.sgmms.sgmms_proyect.controller.fxmlControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers.EntitiesController;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class ExitSceneController implements Initializable {

    private EntitiesController entitiesController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entitiesController = EntitiesController.getInstance();
    }

    @FXML
    protected void onYes() {
        System.exit(0);
    }

    @FXML
    protected void onNo() {
        if (entitiesController.getScore() != null && entitiesController.getScore().getTiempo() <= 0) {
            SceneManager.getInstance().switchScene(SceneManager.SceneID.FINAL_STATS, "Game Over");
        } else {
            SceneManager.getInstance().switchScene(SceneManager.SceneID.MAIN_MENU, "Menu");
        }
    }
}