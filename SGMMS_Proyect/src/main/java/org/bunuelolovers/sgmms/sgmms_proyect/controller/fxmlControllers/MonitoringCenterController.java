package org.bunuelolovers.sgmms.sgmms_proyect.controller.fxmlControllers;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers.EntitiesController;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SceneManager;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MonitoringCenterController implements Initializable {

    private EntitiesController entitiesController;

    @FXML
    private Label numFires;

    @FXML
    private Label numCrashes;

    @FXML
    private Label numHeist;

    private int numFiresCount;
    private int numCrashesCount;
    private int numHeistCount;



    @FXML
    private TableView<Incident> table;

    @FXML
    private TableColumn<Incident, String> id;

    @FXML
    private TableColumn<Incident, Integer> gravity;

    @FXML
    private TableColumn<Incident, String> state;

    @FXML
    private TableColumn<Incident, String> ubi;

    @FXML
    private TableColumn<Incident, String> vertex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entitiesController = EntitiesController.getInstance();

        // Columna ID
        id.setCellValueFactory(cellDataFeatures -> {
            Incident incident = cellDataFeatures.getValue();
            if (incident != null) {

                return new SimpleStringProperty(incident.getId());
            } else {
                 return new SimpleStringProperty("<Incidente Nulo>");
            }
        });

         gravity.setCellValueFactory(cellDataFeatures -> {
            Incident incident = cellDataFeatures.getValue();
            if (incident != null) {
                 return new SimpleIntegerProperty(incident.getGravity()).asObject();
            } else {

                return new ReadOnlyObjectWrapper<>(null);
            }
        });


        state.setCellValueFactory(cellDataFeatures -> {
            Incident incident = cellDataFeatures.getValue();
            if (incident != null) {
                StateIncident stateIncident = incident.getStateIncident();
                if (stateIncident != null) {
                    return new SimpleStringProperty(stateIncident.toString());
                } else {
                     return new SimpleStringProperty("<Estado Nulo>");
                }
            } else {
                 return new SimpleStringProperty("<Incidente Nulo>");
            }
        });

        ubi.setCellValueFactory(cellDataFeatures -> {
            Incident incident = cellDataFeatures.getValue();
            if (incident != null) {
                if (incident instanceof Fire) {
                    Fire fire = (Fire) incident;
                    return new SimpleStringProperty(fire.getZone().getName());
                }

                if(incident instanceof Heist){
                    Heist heist = (Heist) incident;
                    return new SimpleStringProperty(heist.getZone().getName());
                }

                if (incident instanceof Crash){
                    Crash crash = (Crash) incident;
                    return new SimpleStringProperty(crash.getPosition().getX()+ "," + crash.getPosition().getY());
                } else {
                    return new SimpleStringProperty("N/A");
                }
            }else {
                 return new SimpleStringProperty("<Incidente Nulo>");
            }
        });


        vertex.setCellValueFactory(cellDataFeatures -> {
            Incident incident = cellDataFeatures.getValue();
            if (incident != null) {
                if (incident instanceof Fire) {
                    Fire fire = (Fire) incident;
                    return new SimpleStringProperty(fire.getZone().getX() + "," + fire.getZone().getY());
                }

                if(incident instanceof Heist){
                    Heist heist = (Heist) incident;
                    return new SimpleStringProperty(heist.getZone().getX() + "," + heist.getZone().getY());
                }

                if (incident instanceof Crash){
                    Crash crash = (Crash) incident;
                    return new SimpleStringProperty(crash.getPosition().getX()+ "," + crash.getPosition().getY());
                } else {
                    return new SimpleStringProperty("N/A");
                }
            }else {
                 return new SimpleStringProperty("<Incidente Nulo>");
            }
        });
         setupDataUpdater();
    }


    private void setupDataUpdater() {
        new Thread(() -> {
            while (entitiesController.isRunning()) {
                 updateIncidentTable();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateIncidentTable() {
        if (entitiesController == null) return;

        ObservableList<Incident> currentIncidents = entitiesController.reportIncident();

        numFiresCount = 0;
        numCrashesCount = 0;
        numHeistCount = 0;

        for (Incident i:currentIncidents){
            if (i instanceof Fire && (i.getStateIncident() == StateIncident.UNSOLVED|| i.getStateIncident() == StateIncident.IN_PROGRESS)) {
                numFiresCount++;
            } else if (i instanceof Crash && (i.getStateIncident() == StateIncident.UNSOLVED|| i.getStateIncident() == StateIncident.IN_PROGRESS)) {
                numCrashesCount++;
            } else if (i instanceof Heist && (i.getStateIncident() == StateIncident.UNSOLVED|| i.getStateIncident() == StateIncident.IN_PROGRESS)) {
                numHeistCount++;
            }
        }

        Platform.runLater(() -> {
            if (table != null) {
                numFires.setText(String.valueOf(numFiresCount));
                numCrashes.setText(String.valueOf(numCrashesCount));
                numHeist.setText(String.valueOf(numHeistCount));
                table.setItems(currentIncidents);
            }
        });
    }





    @FXML
    protected void onclickExit() {

        SceneManager.getInstance().switchScene(SceneManager.SceneID.MAIN_MENU, "Menu");
     }

    @FXML
    protected void onClickHistory(){
        SceneManager.getInstance().switchScene(SceneManager.SceneID.HISTORY, "Historial");


    }
}