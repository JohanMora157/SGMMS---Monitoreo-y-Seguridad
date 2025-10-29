package org.bunuelolovers.sgmms.sgmms_proyect.controller.fxmlControllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers.EntitiesController;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SceneManager;
import org.bunuelolovers.sgmms.sgmms_proyect.model.map.Route;
import org.bunuelolovers.sgmms.sgmms_proyect.model.map.ZoneType;

import java.net.URL;
import java.util.ResourceBundle;

public class ActiveRoutesViewController implements Initializable {

    private EntitiesController entitiesController;

    @FXML
    private TableView<Route> table;

    @FXML
    private TableColumn<Route, String> name;

    @FXML
    private TableColumn<Route, String> type;

    @FXML
    private TableColumn<Route, String> time;

    @FXML
    private TableColumn<Route, String> start;

    @FXML
    private TableColumn<Route, String> end;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entitiesController = EntitiesController.getInstance();

         name.setCellValueFactory(cellDataFeatures -> {
            Route route = cellDataFeatures.getValue();
            if (route != null) {

                return new SimpleStringProperty(route.getName());
            } else {
                return new SimpleStringProperty("<Incidente Nulo>");
            }
        });

        type.setCellValueFactory(cellDataFeatures -> {
            Route route = cellDataFeatures.getValue();
            if (route != null) {
                ZoneType routeType = route.getZoneType();
                if (routeType != null) {
                    return new SimpleStringProperty(routeType.toString());
                } else {
                    return new SimpleStringProperty("<Estado Nulo>");
                }
            } else {
                return new SimpleStringProperty("<Incidente Nulo>");
            }
        });

        time.setCellValueFactory(cellDataFeatures -> {
            Route route = cellDataFeatures.getValue();
            if (route != null) {

                return new SimpleStringProperty(((int)route.getTime())+ " minutos");
            } else {
                return new SimpleStringProperty("<Incidente Nulo>");
            }
        });



        start.setCellValueFactory(cellDataFeatures -> {
            Route route = cellDataFeatures.getValue();
            if (route != null) {

                return new SimpleStringProperty("x: "+route.getStart().getX()+ ", y: " + route.getStart().getY());

            }else {
                return new SimpleStringProperty("<Incidente Nulo>");
            }
        });

        end.setCellValueFactory(cellDataFeatures -> {
            Route route = cellDataFeatures.getValue();
            if (route != null) {

                return new SimpleStringProperty("x: "+route.getEnd().getX()+ ", y: " + route.getEnd().getY());

            }else {
                return new SimpleStringProperty("<Incidente Nulo>");
            }
        });
        updateIncidentTable();
     }


    private void updateIncidentTable() {
        if (entitiesController == null) return;

        ObservableList<Route> routes = entitiesController.reportRoutes();

        Platform.runLater(() -> {
            if (table != null) {
                table.setItems(routes);
            }
        });
    }

    @FXML
    protected void onClickExit() {

        SceneManager.getInstance().switchScene(SceneManager.SceneID.MAIN_MENU, "Salir");
    }

}
