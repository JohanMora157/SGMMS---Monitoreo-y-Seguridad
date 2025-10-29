package org.bunuelolovers.sgmms.sgmms_proyect.controller.fxmlControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers.EntitiesController;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class FinalStatsController implements Initializable {

    @FXML
    private Label finalScoreLabel;

    @FXML
    private Button playAgainButton;

    @FXML
    private Button exitButton;

    private EntitiesController entitiesController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        entitiesController = EntitiesController.getInstance();


        if (entitiesController != null && entitiesController.getScore() != null) {
            finalScoreLabel.setText(String.valueOf(entitiesController.getScore().getScore()));
        } else {
            finalScoreLabel.setText("0");
        }
    }

    @FXML
    protected void onClickPlayAgain() {
        if (entitiesController != null) {
            entitiesController.resetGameTimer();
            entitiesController.stopGame();

        }
        SceneManager.getInstance().switchScene(SceneManager.SceneID.PLAYER_FORM, "Map");
    }

    @FXML
    protected void onClickExit() {
        SceneManager.getInstance().switchScene(SceneManager.SceneID.EXIT, "Salir");
    }
}