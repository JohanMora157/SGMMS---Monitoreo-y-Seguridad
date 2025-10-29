package org.bunuelolovers.sgmms.sgmms_proyect.controller.fxmlControllers;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers.EntitiesController;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SceneManager;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SoundEffectsManager;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.IncidentType;
import org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle.VehicleType;


import java.net.URL;

import java.util.ResourceBundle;

public class MapController implements Initializable {

    @FXML
    private Canvas canvas;

    private GraphicsContext gc;
    private EntitiesController entitiesController;
    private AnimationTimer timer;


    @FXML
    private Button exitButton;

    @FXML
    private Label numAutomatas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        entitiesController = EntitiesController.getInstance();
        gc = canvas.getGraphicsContext2D();

        canvas.setFocusTraversable(true);

        SoundEffectsManager.playAmbience();



        if (!entitiesController.isRunning()) {
            entitiesController.startGame(canvas);
            entitiesController.gameTime();

        } else {
            if (entitiesController.getScore() != null) {
                entitiesController.getScore().setGraphicsContext(canvas.getGraphicsContext2D());
            }

        }



        initEvents();
        startAnimation();

        Platform.runLater(() -> canvas.requestFocus());
    }



    public void startAnimation() {

        if (timer == null) {
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {


                    gc.setTransform(1, 0, 0, 1, 0, 0);
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


                    if (entitiesController.getPlayer() != null && entitiesController.getMap() != null && entitiesController.getMap().getImage() != null) {
                        double playerX = entitiesController.getPlayer().getX();
                        double playerY = entitiesController.getPlayer().getY();

                        double mapWidth = entitiesController.getMap().getImage().getWidth();
                        double mapHeight = entitiesController.getMap().getImage().getHeight();

                        double offsetX = canvas.getWidth() / 2 - playerX;
                        double offsetY = canvas.getHeight() / 2 - playerY;


                        offsetX = Math.min(offsetX, 0);
                        offsetX = Math.max(offsetX, canvas.getWidth() - mapWidth);

                        offsetY = Math.min(offsetY, 0);
                        offsetY = Math.max(offsetY, canvas.getHeight() - mapHeight);
                        if (mapWidth < canvas.getWidth()) {
                            offsetX = (canvas.getWidth() - mapWidth) / 2;
                        }
                        if (mapHeight < canvas.getHeight()) {
                            offsetY = (canvas.getHeight() - mapHeight) / 2;
                        }
                        gc.translate(offsetX, offsetY);

                        numAutomatas.setText(entitiesController.getVehiclesController().getVehiclesByType().get(VehicleType.NPC).size()+"");

                    }


                    entitiesController.renderGame(gc);
                    entitiesController.renderVehicles(gc);
                    gc.setTransform(1, 0, 0, 1, 0, 0);
                    entitiesController.getScore().paint();


                }
            };
            timer.start();
        }
    }

    public void initEvents() {
        if (entitiesController != null && entitiesController.getPlayer() != null) {
            canvas.setOnKeyPressed(event -> {
                if (entitiesController.getPlayer() != null) {
                    entitiesController.getPlayer().setOnKeyPressed(event);
                }
            });

            canvas.setOnKeyReleased(event -> {
                if (entitiesController.getPlayer() != null) {
                    entitiesController.getPlayer().setOnKeyReleased(event);
                }
            });
        }
    }

    @FXML
    protected void onClickResolveFire(){
        entitiesController.resolveIncidents(IncidentType.FIRE);
        canvas.requestFocus();
    }

    @FXML
    protected void onClickResolveHeist(){
        entitiesController.resolveIncidents(IncidentType.HEIST);
        canvas.requestFocus();
    }

    @FXML
    protected void onClickResolveCrash(){
        entitiesController.resolveIncidents(IncidentType.CRASH);
        canvas.requestFocus();
    }

    @FXML
    protected void onClickButtonExit() {
        entitiesController.pauseGame();
        SceneManager.getInstance().switchScene(SceneManager.SceneID.MAIN_MENU, "Menu");
    }
}