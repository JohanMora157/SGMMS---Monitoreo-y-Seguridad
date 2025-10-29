package org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SoundEffectsManager;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.*;
import org.bunuelolovers.sgmms.sgmms_proyect.model.map.MapClass;
import org.bunuelolovers.sgmms.sgmms_proyect.model.map.Route;
import org.bunuelolovers.sgmms.sgmms_proyect.model.map.Score;
import org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle.*;
import org.bunuelolovers.sgmms.sgmms_proyect.structures.BinarySearchTree;
import org.bunuelolovers.sgmms.sgmms_proyect.threads.IncidentGeneratorThread;
import javafx.application.Platform;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SceneManager;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.IncidentType;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


public class EntitiesController {

    private static volatile EntitiesController instance;

    private BinarySearchTree<Incident> incidents;

    public volatile boolean isRunning = false;

    private List<Incident> resolvedIncident;

    private VehiclesController vehiclesController;
     private Score score;
    private MapClass map;

    private int gameTimer;

    private IncidentGeneratorThread incidentGeneratorThread;
    private Thread incidentThread;
    private Thread mainGameThread;

    public static EntitiesController getInstance() {
        if (instance == null) {
            synchronized (EntitiesController.class) {
                if (instance == null) {
                    instance = new EntitiesController();
                }
            }
        }
        return instance;
    }



    private EntitiesController() {
        incidents = new BinarySearchTree<>();
        resolvedIncident = new ArrayList<>();
    }

    public void setGameTimer(int gameTimer) {
        this.gameTimer = gameTimer;
    }

    public void gameTime( ) {
        if (score != null) {
            score.setTiempo(gameTimer);
        }

       new Thread(()->{
           while(true){
               try {
                   score.setTiempo(score.getTiempo()-1);
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
               if(score.getTiempo()<=0){
                   incidentThread.interrupt();

                   Platform.runLater(() -> {
                       SceneManager.getInstance().switchScene(SceneManager.SceneID.FINAL_STATS, "Game Over");
                   });
                   break;
               }
           }
       }).start();
    }

    public void initVehiclesController(){
        this.vehiclesController = new VehiclesController(map);
    }

    public void startGame(Canvas canvas) {
        stopGame();

        incidents = new BinarySearchTree<>();
        resolvedIncident = new ArrayList<>();

        initMap(canvas);
        initVehiclesController();
        vehiclesController.initVehicles(canvas);
        initScore(canvas);

        isRunning = true;

        incidentGeneratorThread = new IncidentGeneratorThread();
        incidentThread = new Thread(incidentGeneratorThread);
        incidentThread.setDaemon(true);
        incidentThread.start();

        mainGameThread = new Thread(() -> {
            while (isRunning) {
                try {
                    vehiclesController.assignRoots(isRunning);
                    vehiclesController.checkCollisions(isRunning);
                    checkResolvedIncidents();
                    vehiclesController.checkAssignedVehicleProximity();
                    vehiclesController.updatePlayerMovement();

                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        mainGameThread.start();

    }


    public void stopGame() {
        isRunning = false;

        if (vehiclesController != null) {
            vehiclesController.stopVehiclesAndClearLists();
        }

        // Detener hilos
        if (mainGameThread != null) {
            mainGameThread.interrupt();
        }

        if (incidentThread != null) {
            incidentThread.interrupt();
        }

        if (incidentGeneratorThread != null) {
            incidentGeneratorThread.stop();
        }

         if (score != null) {
            score.setRunning(false);
            score.setScore(0);
        }

        incidents.clear();
        resolvedIncident.clear();
        SoundEffectsManager.stopAmbience();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void initScore(Canvas canvas) {
        score = new Score(canvas);
        score.start();
    }

    public void initMap(Canvas canvas) {
        Image imageMap = new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/map/Map.png"), 0, 0, false, false);
        map = new MapClass(imageMap, canvas);
    }

    public void renderVehicles(GraphicsContext gc){
        vehiclesController.renderVehicles(gc);
    }

    public void renderGame(GraphicsContext gc) {
        if (map != null) map.paint(gc);
        List<Incident> incidentsList = incidents.inOrder();
        for (Incident inc : incidentsList) {
            if (inc instanceof Fire fire) {
                fire.paint(gc);
            } else if (inc instanceof Heist heist) {
                heist.paint(gc);
            }
        }

    }

    public Vertex getNearestVertex(double x, double y) {
        double minDist = Double.MAX_VALUE;
        Vertex nearest = null;
        if (map != null && map.getCorners() != null) {
            for (Vertex v : map.getCorners().getTodosLosVertices()) {
                double dx = v.getX() - x;
                double dy = v.getY() - y;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if (dist < minDist) {
                    minDist = dist;
                    nearest = v;
                }
            }
        }
        return nearest;
    }


    public void resolveIncidents(IncidentType type) {
        if (!isRunning || vehiclesController == null) {
            return;
        }

        if (incidents != null && !incidents.isEmpty()) {
            List<Incident> currentIncidents = incidents.inOrder();
            //List<Incident> incidentsToRemoveFromBst = new ArrayList<>();

           int fireIncidents = 0;
           int heistIncidents = 0;
           int crashIncidents = 0;

             for (Incident incident : currentIncidents) {
                if (incident instanceof Fire) {
                    fireIncidents++;
                } else if (incident instanceof Heist) {
                    heistIncidents++;
                } else if (incident instanceof Crash) {
                    crashIncidents++;
                }
            }

            if(type == IncidentType.CRASH && crashIncidents == 0) {
                score.setScore(score.getScore() - 2);
                return;
            } else if (type == IncidentType.FIRE && fireIncidents == 0) {
                score.setScore(score.getScore() - 1);
                return;
            } else if (type == IncidentType.HEIST && heistIncidents == 0) {
                score.setScore(score.getScore() - 2);
                return;
            }

            if (type == IncidentType.CRASH) {
                boolean playedCrashResolved = false;
                for (Incident incident : currentIncidents) {
                    if (incident instanceof Crash crash && incident.getStateIncident().equals(StateIncident.UNSOLVED)) {
                        if(crash.getV1() == getPlayer() || crash.getV2() == getPlayer()){
                            resolveCrashIncident(crash);
                            playedCrashResolved = true;
                            break;
                        }
                    }
                }
                // Si no se resolvio un crash del jugador o no habia uno, se resuelve cualquier otro
                if (!playedCrashResolved) {
                    for(Incident incident : currentIncidents){
                        if(incident instanceof Crash crash && incident.getStateIncident().equals(StateIncident.UNSOLVED)){
                            resolveCrashIncident(crash);
                            break;
                        }
                    }
                }
            }

            else if (type == IncidentType.FIRE) {
                for (Incident incident : currentIncidents) {
                    if (incident instanceof Fire fire && incident.getStateIncident().equals(StateIncident.UNSOLVED)) {
                        resolveFireIncident(fire);
                        break;
                    }
                }
            } else if (type == IncidentType.HEIST) {
                for (Incident incident : currentIncidents) {
                    if (incident instanceof Heist heist && incident.getStateIncident().equals(StateIncident.UNSOLVED)) {
                        resolveHeistIncident(heist);
                        break;
                    }
                }
            }
        }
    }

    private void checkResolvedIncidents() {
        if (incidents != null && !incidents.isEmpty()) {
            List<Incident> incidentsToRemove = new ArrayList<>();

            for (Incident incident : incidents.inOrder()) {
                if (incident.getStateIncident() == StateIncident.SOLVED) {
                    boolean processed = false;
                    if (incident instanceof Crash resolvedCrash) {
                        vehiclesController.handleResolvedCrash(resolvedCrash);
                        processed = true;
                    } else if (incident instanceof Fire resolvedFire) {
                        vehiclesController.handleResolvedFire(resolvedFire);
                        processed = true;
                    } else if (incident instanceof Heist) {
                        vehiclesController.handleResolvedHeist((Heist) incident);
                        processed = true;
                    }

                    if (processed) {
                        incidentsToRemove.add(incident);
                        resolvedIncident.add(incident);
                    }
                }
            }
            for(Incident toRemove : incidentsToRemove){
                incidents.remove(toRemove);
            }
        }
    }


    private void resolveCrashIncident(Crash crash) {
        if (crash.getStateIncident() == StateIncident.UNSOLVED) {
            if (vehiclesController.assignAmbulanceToCrash(crash)) { // Ya actualiza estado a IN_PROGRESS
                if(score != null) score.setScore(score.getScore() + 4);
            }
        }
    }


    private void resolveFireIncident(Fire fire) {
        if (fire.getStateIncident() == StateIncident.UNSOLVED) {
            Vertex place = getNearestVertex(fire.getX(), fire.getY());
            if (vehiclesController.assignFireTruckToFire(fire, place)) { // Ya actualiza estado a IN_PROGRESS
                if(score != null) score.setScore(score.getScore() + 3);
            }
        }
    }

    private void resolveHeistIncident(Heist heist) {
        if (heist.getStateIncident() == StateIncident.UNSOLVED) {
            Vertex destino = getNearestVertex(heist.getX(), heist.getY());
            if (vehiclesController.assignPatrolToHeist(heist, destino)) { // Ya actualiza estado a IN_PROGRESS
                if(score != null) score.setScore(score.getScore() + 2);
            }
        }
    }

    public ObservableList<Incident> reportIncident() {
        List<Incident> incidentList = incidents.inOrder();
        return FXCollections.observableArrayList(incidentList);
    }

    public ObservableList<Route> reportRoutes() {
        List<Route> routes = map.getRoutes().inOrder();
        return FXCollections.observableArrayList(routes);
    }

    public ObservableList<Incident> reportResolvedIncident() {
        return FXCollections.observableArrayList(resolvedIncident);
    }



    public VehiclePlayer getPlayer() {
        return vehiclesController.getPlayer();
    }


    public BinarySearchTree<Incident> getIncidents() {
        return incidents;
    }


    public MapClass getMap() {
        return map;
    }

    public void setMap(MapClass map) {
        this.map = map;
    }


    public Score getScore() {
        return score;
    }


   public void resetGameTimer() {
        if (score != null) {
            score.setScore(0);
            score.setTiempo(gameTimer);
        }
    }

    public void pauseGame() {

        SoundEffectsManager.stopAmbience();
    }


    public void resumeGame() {
        SoundEffectsManager.playAmbience();
    }

    public VehiclesController getVehiclesController() {
        return vehiclesController;
    }
}








