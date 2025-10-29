package org.bunuelolovers.sgmms.sgmms_proyect.controller.fxmlControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers.EntitiesController;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.AudioManager;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SceneManager;

public class MenuController {

    @FXML
    private Button mapButton;
    @FXML
    private Button mCButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button toggleMusicButton;

    @FXML
    protected void onClickActionMap() {
        AudioManager.stopMenuMusic();
        EntitiesController.getInstance().resumeGame();
        SceneManager.getInstance().switchScene(SceneManager.SceneID.GAME_VIEW, "Map");
    }

    @FXML
    protected void onClickActionMonitoring() {
        SceneManager.getInstance().switchScene(SceneManager.SceneID.MONITORING, "Centro de Monitoreo");
    }

    @FXML
    protected void onClickExit() {
        SceneManager.getInstance().switchScene(SceneManager.SceneID.EXIT, "Salir");
    }

    @FXML
    protected void onClickExitToRoutes(){
        SceneManager.getInstance().switchScene(SceneManager.SceneID.ROUTES, "Routes");
    }

    @FXML
    private void initialize() {
        mCButton.setOnAction(e -> onClickActionMonitoring());
        if (!AudioManager.isPlaying()) {
            AudioManager.playMenuMusic();
        }
        updateMusicButton();
    }

    @FXML
    private void onToggleMusic() {
        if (AudioManager.isPlaying()) {
            AudioManager.stopMenuMusic();
            toggleMusicButton.setText("🔊");
        } else {
            AudioManager.playMenuMusic();
            toggleMusicButton.setText("🔇");
        }
    }

    private void updateMusicButton() {
        if (toggleMusicButton != null) {
            if (org.bunuelolovers.sgmms.sgmms_proyect.manager.AudioManager.isPlaying()) {
                toggleMusicButton.setText("🔇");
            } else {
                toggleMusicButton.setText("🔊");
            }
        }
    }
}