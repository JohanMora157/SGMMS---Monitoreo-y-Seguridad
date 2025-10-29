package org.bunuelolovers.sgmms.sgmms_proyect.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers.EntitiesController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    public enum SceneID {
        MAIN_MENU, GAME_VIEW, MONITORING, EXIT, ROUTES, HISTORY, PLAYER_FORM, FINAL_STATS
    }

    private static SceneManager instance;
    private Stage primaryStage;

    private EntitiesController entitiesController;

    private final Map<SceneID, String> fxmlPaths = new HashMap<>();

    private SceneManager(){
        fxmlPaths.put(SceneID.PLAYER_FORM, "/GraphicScenes/player-form.fxml");
        fxmlPaths.put(SceneID.MAIN_MENU, "/GraphicScenes/menu-scene.fxml");
        fxmlPaths.put(SceneID.GAME_VIEW, "/org/bunuelolovers/sgmms/sgmms_proyect/scene-game.fxml");
        fxmlPaths.put(SceneID.MONITORING, "/GraphicScenes/monitoring-center-view.fxml");
        fxmlPaths.put(SceneID.HISTORY, "/GraphicScenes/incident-history-view.fxml");
        fxmlPaths.put(SceneID.ROUTES, "/GraphicScenes/active-routes-view.fxml");
        fxmlPaths.put(SceneID.EXIT, "/GraphicScenes/exit-scene.fxml");
        fxmlPaths.put(SceneID.FINAL_STATS, ("/GraphicScenes/final-stats.fxml"));
    }

    public static SceneManager getInstance(){
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void switchScene(SceneID id, String title){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPaths.get(id)));
            Parent root = fxmlLoader.load();
            Scene scene;
            if(id.equals(SceneID.GAME_VIEW)){
                scene = new Scene(root, 1400, 800);

            }else{
                scene = new Scene(root, 800, 800);

            }
            String css = getClass().getResource("/org/bunuelolovers/sgmms/sgmms_proyect/css/SceneStyles.css").toExternalForm();
            scene.getStylesheets().add(css);

            primaryStage.setTitle(title);
            primaryStage.setScene(scene);


            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}