package org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SoundEffectsManager;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.*;
import org.bunuelolovers.sgmms.sgmms_proyect.model.map.MapClass;
import org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class VehiclesController {

    private VehiclePlayer player;

    private Map<VehicleType, List<Vehicle>> vehiclesByType;
    private Map<VehicleType, List<Vehicle>> assignedVehicles;
    private MapClass map;

    private static final int PLAYER_CRASH_PENALTY = 10;

    private static final String[] EXTREME_VERTICES = {
            "B0", "C0", "D0", "E0", "F0",
            "A2", "A3",
            "G1", "G2", "G3",
            "B6", "C6", "D6", "E6", "F6"
    };


    public VehiclesController(MapClass map) {
        this.map = map;
        vehiclesByType = new EnumMap<>(VehicleType.class);
        assignedVehicles = new EnumMap<>(VehicleType.class);

        for (VehicleType type : VehicleType.values()) {
            vehiclesByType.put(type, new ArrayList<>());
            assignedVehicles.put(type, new ArrayList<>());
        }
    }

    public void initVehicles(Canvas canvas) {
        player = new VehiclePlayer("Player", map.getCorners().getVertex("A2").getX(), map.getCorners().getVertex("A2").getY(), 60, 60, canvas, 80);
        if (map != null && map.getBlocks() != null) {
            player.setCollisions(map.getBlocks());
        }

        new Thread(player).start();

        int numNpcs = (int) (Math.random() * 6) + 5;
        String[] startRoutes = {"B6", "F6", "F0", "E6", "G1", "G3", "D6", "B0","A1","A3", "C6"};
        for (int i = 0; i < numNpcs; i++) {
            Vertex v = map.getCorners().getVertex(startRoutes[i]);
            Vehicle npc = new NpcVehicle("npc" + (i + 1), v.getX(), v.getY(), 60, 60, canvas, 10);
            npc.followRoute(getRandomRouteFrom(map.getCorners().getVertex(startRoutes[i])));
            new Thread(npc).start();
            vehiclesByType.get(VehicleType.NPC).add(npc);

        }

        for (int i = 1; i <= 3; i++) {
            Vertex v = map.getCorners().getVertex("Hospital");
            Vehicle ambulance = new Ambulance("ambulance" + i, v.getX(), v.getY(), 60, 60, canvas, 5);
            vehiclesByType.get(VehicleType.AMBULANCE).add(ambulance);
            ambulance.start();
        }

        for (int i = 1; i <= 3; i++) {
            Vertex v = map.getCorners().getVertex("Bomberos");
            Vehicle firetruck = new FireTruck("firetruck" + i, v.getX(), v.getY(), 60, 60, canvas, 5);
            vehiclesByType.get(VehicleType.FIRE_TRUCK).add(firetruck);
            firetruck.start();
        }

        for (int i = 1; i <= 3; i++) {
            Vertex v = map.getCorners().getVertex("Policia");
            Vehicle policeCar = new Patrol("police" + i, v.getX(), v.getY(), 60, 60, canvas, 6);
            vehiclesByType.get(VehicleType.POLICE).add(policeCar);
            policeCar.start();
        }
    }

    private List<Vertex> getRandomRouteFrom(Vertex origen) {
        List<Vertex> vertices = new ArrayList<>(map.getCorners().getTodosLosVertices());
        Vertex destino;
        do {
            destino = vertices.get((int) (Math.random() * vertices.size()));
        } while (destino == origen);
        return map.getCorners().getRutaMasCorta(origen.getId(), destino.getId());
    }

    public void assignRoots(boolean isRunning) {
        if (!isRunning) {
            return;
        }
        List<Vehicle> npcs = vehiclesByType.get(VehicleType.NPC);
        if (npcs != null) {
            for (Vehicle npc : npcs) {
                if (npc instanceof NpcVehicle) {
                    NpcVehicle npcActual = (NpcVehicle) npc;
                    if (npcActual.isFinishRoot()) {
                        Vertex actual = EntitiesController.getInstance().getNearestVertex(npcActual.getX(), npcActual.getY());
                        List<Vertex> vertices = new ArrayList<>(map.getCorners().getTodosLosVertices());
                        Vertex destino;
                        do {
                            destino = vertices.get((int) (Math.random() * vertices.size()));
                        } while (destino == actual);
                        List<Vertex> nuevaRuta = map.getCorners().getRutaMasCorta(actual.getId(), destino.getId());
                        npcActual.setStop(false);
                        npcActual.followRoute(nuevaRuta);
                        npcActual.setFinishRoot(false);
                    }
                }
            }
        }
    }

    public void renderVehicles(GraphicsContext gc){
        if (player != null) player.paint(gc);


        List<Vehicle> npcs = vehiclesByType.get(VehicleType.NPC);
        if (npcs != null) {
            for (Vehicle npc : npcs) {
                npc.paint(gc);
            }
        }

        for (VehicleType type : VehicleType.values()) {
            if (type == VehicleType.NPC) continue;
            List<Vehicle> assignedList = assignedVehicles.get(type);
            if (assignedList != null) {
                for (Vehicle vehicle : assignedList) {
                    vehicle.paint(gc);
                }
            }
        }
    }

    public void stopVehiclesAndClearLists(){
        for (VehicleType type : VehicleType.values()) {
            List<Vehicle> vehicles = vehiclesByType.get(type);
            if (vehicles != null) {
                for (Vehicle vehicle : vehicles) {
                    vehicle.setRunning(false);
                }
                vehicles.clear();
            }
            assignedVehicles.get(type).clear();
        }
    }

    public void checkCollisions(boolean isRunning) {
        if (!isRunning) {
            return;
        }
        List<Vehicle> npcs = vehiclesByType.get(VehicleType.NPC);
        if (npcs != null) {
            for (int i = 0; i < npcs.size(); i++) {
                if (!(npcs.get(i) instanceof NpcVehicle)) continue;
                NpcVehicle v1 = (NpcVehicle) npcs.get(i);
                for (int j = i + 1; j < npcs.size(); j++) {
                    if (!(npcs.get(j) instanceof NpcVehicle)) continue;
                    NpcVehicle v2 = (NpcVehicle) npcs.get(j);
                    if (isColliding(v1, v2) && !v1.isCrashed() && !v2.isCrashed()) {
                        v1.setCrashed(true);
                        v2.setCrashed(true);
                        SoundEffectsManager.playChoque();
                        Vertex collisionPosition = EntitiesController.getInstance().getNearestVertex(v1.getX(), v1.getY());
                        if (collisionPosition != null) {
                            int gravity = (int) (Math.random() * 5) + 2;
                            Incident crash = new Crash("crash between " + v1.getid() + " and " + v2.getid(), gravity, StateIncident.UNSOLVED, collisionPosition, v1, v2);
                            EntitiesController.getInstance().getIncidents().insert(crash);
                        }
                        return;
                    }

                    if (isColliding(player, v1) && !v1.isCrashed() && !player.isCrashed()) {
                        v1.setCrashed(true);
                        player.setCrashed(true);
                        SoundEffectsManager.playChoque();
                        Vertex collisionPosition = EntitiesController.getInstance().getNearestVertex(v1.getX(), v1.getY());
                        if (collisionPosition != null) {
                            int gravity = (int) (Math.random() * 5) + 2;
                            Incident crash = new Crash("Player crash with " + v1.getid(), gravity, StateIncident.UNSOLVED, collisionPosition, player, v1);
                            EntitiesController.getInstance().getIncidents().insert(crash);
                            playerCrash((Crash) crash);
                        }
                        return;
                    }

                    if (isColliding(player, v2) && !v2.isCrashed() && !player.isCrashed()) {
                        v2.setCrashed(true);
                        player.setCrashed(true);
                        SoundEffectsManager.playChoque();
                        Vertex collisionPosition = EntitiesController.getInstance().getNearestVertex(v2.getX(), v2.getY());
                        if (collisionPosition != null) {
                            int gravity = (int) (Math.random() * 5) + 2;
                            Incident crash = new Crash("Player crash with " + v2.getid(), gravity, StateIncident.UNSOLVED, collisionPosition, player, v2);
                            EntitiesController.getInstance().getIncidents().insert(crash);
                            playerCrash((Crash) crash);
                        }
                        return;
                    }
                    if (isColliding(v1, v2) && !v1.isCrashed() && v2.isCrashed()) {
                        v1.setStop(true);
                        return;
                    }
                    if (isColliding(v1, v2) && v1.isCrashed() && !v2.isCrashed()) {
                        v2.setStop(true);
                        return;
                    }

                    if (isColliding(player, v1) && v1.isCrashed()) {
                        player.setStop(true);
                        return;
                    }
                    if (isColliding(player, v2) && v2.isCrashed()) {
                        player.setStop(true);
                        return;
                    }


                    player.setStop(false);
                }
            }
        }
    }


    private void playerCrash(Crash crashIncident){
        if(crashIncident != null && crashIncident.getStateIncident() == StateIncident.UNSOLVED){
            List<Vehicle> availableAmbulances = vehiclesByType.get(VehicleType.AMBULANCE);
            if (availableAmbulances != null && !availableAmbulances.isEmpty()) {
                Ambulance ambulance = (Ambulance) availableAmbulances.remove(0);
                assignedVehicles.get(VehicleType.AMBULANCE).add(ambulance);
                ambulance.setIncident(crashIncident);

                crashIncident.setAmbulance(ambulance);
                List<Vertex> routeToCrash = map.getCorners().getRutaMasCorta("Hospital", crashIncident.getPosition().getId());
                ambulance.followRoute(routeToCrash);
                crashIncident.setStateIncident(StateIncident.IN_PROGRESS);
            }
        }

        EntitiesController.getInstance().getScore().setScore(Math.max(0, EntitiesController.getInstance().getScore().getScore() - PLAYER_CRASH_PENALTY));
    }

    public boolean isColliding(Vehicle v1, Vehicle v2) {
        double x1 = v1.getX();
        double y1 = v1.getY();
        double w1 = v1.getWidth();
        double h1 = v1.getHeight();

        double x2 = v2.getX();
        double y2 = v2.getY();
        double w2 = v2.getWidth();
        double h2 = v2.getHeight();

        // Determinar si los vehículos están chocados
        boolean v1IsCrashed = false;
        if (v1 instanceof NpcVehicle && ((NpcVehicle) v1).isCrashed()) {
            v1IsCrashed = true;
        } else if (v1 instanceof VehiclePlayer && ((VehiclePlayer) v1).isCrashed()) {
            v1IsCrashed = true;
        }

        boolean v2IsCrashed = false;
        if (v2 instanceof NpcVehicle && ((NpcVehicle) v2).isCrashed()) {
            v2IsCrashed = true;
        } else if (v2 instanceof VehiclePlayer && ((VehiclePlayer) v2).isCrashed()) {
            v2IsCrashed = true;
        }

        // Factor de expansión para vehículos chocados (10% de aumento en cada dimensión)
        final double crashExpansionFactor = 1.1;

        if (v1IsCrashed) {
            double widthIncrease1 = w1 * (crashExpansionFactor - 1.0);
            double heightIncrease1 = h1 * (crashExpansionFactor - 1.0);
            x1 -= widthIncrease1 / 2.0; // Ajustar x para expandir desde el centro
            y1 -= heightIncrease1 / 2.0; // Ajustar y para expandir desde el centro
            w1 *= crashExpansionFactor;
            h1 *= crashExpansionFactor;
        }

        if (v2IsCrashed) {
            double widthIncrease2 = w2 * (crashExpansionFactor - 1.0);
            double heightIncrease2 = h2 * (crashExpansionFactor - 1.0);
            x2 -= widthIncrease2 / 2.0; // Ajustar x para expandir desde el centro
            y2 -= heightIncrease2 / 2.0; // Ajustar y para expandir desde el centro
            w2 *= crashExpansionFactor;
            h2 *= crashExpansionFactor;
        }

        // Comprobación de colisión AABB Axis-Aligned Bounding Box)
        boolean collision = x1 < x2 + w2 &&
                x1 + w1 > x2 &&
                y1 < y2 + h2 &&
                y1 + h1 > y2;

        return collision;
    }

    public void checkAssignedVehicleProximity() {
        double awarenessDistance = 100.0;

        List<Vehicle> npcs = vehiclesByType.get(VehicleType.NPC);
        if (npcs == null) return;

        for (VehicleType type : new VehicleType[]{VehicleType.AMBULANCE, VehicleType.FIRE_TRUCK, VehicleType.POLICE}) {
            List<Vehicle> assigned = assignedVehicles.get(type);
            if (assigned == null) continue;

            for (Vehicle assignedVehicle : assigned) {
                for (Vehicle npc : npcs) {
                    if (!(npc instanceof NpcVehicle)) continue;
                    NpcVehicle npcVehicle = (NpcVehicle) npc;
                    if (npcVehicle.isCrashed()) continue;

                    double dx = npcVehicle.getX() - assignedVehicle.getX();
                    double dy = npcVehicle.getY() - assignedVehicle.getY();
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    if (distance < awarenessDistance) {
                        npcVehicle.setStop(true);
                    }
                }
            }
        }
    }

    public VehiclePlayer getPlayer() {
        return player;
    }

    public Map<VehicleType, List<Vehicle>> getVehiclesByType() {
        return vehiclesByType;
    }

    public Map<VehicleType, List<Vehicle>> getAssignedVehicles() {
        return assignedVehicles;
    }

    public void setMap(MapClass map) { // Para actualizar el mapa si cambia dinámicamente
        this.map = map;
    }

    public void updatePlayerMovement() {
        if (player != null && player.isRunning() && !player.isCrashed() && !player.isStop()) {
            player.onMove();
        }
    }

    public Vertex getRandomExtremeVertex() { // Movido desde EntitiesController
        if (map == null || map.getCorners() == null || EXTREME_VERTICES.length == 0) return null;
        String id = EXTREME_VERTICES[(int) (Math.random() * EXTREME_VERTICES.length)];
        return map.getCorners().getVertex(id);
    }

    public boolean assignAmbulanceToCrash(Crash crashIncident) {
        if (crashIncident.getStateIncident() == StateIncident.UNSOLVED) {
            List<Vehicle> availableAmbulances = vehiclesByType.get(VehicleType.AMBULANCE);
            if (availableAmbulances != null && !availableAmbulances.isEmpty()) {
                Ambulance ambulance = (Ambulance) availableAmbulances.remove(0);
                assignedVehicles.get(VehicleType.AMBULANCE).add(ambulance);
                ambulance.setIncident(crashIncident);
                crashIncident.setAmbulance(ambulance);

                if (this.map != null && this.map.getCorners() != null && crashIncident.getPosition() != null) {
                    List<Vertex> routeToCrash = this.map.getCorners().getRutaMasCorta("Hospital", crashIncident.getPosition().getId());
                    ambulance.followRoute(routeToCrash);
                }
                crashIncident.setStateIncident(StateIncident.IN_PROGRESS);
                return true;
            }
        }
        return false;
    }

    public boolean assignFireTruckToFire(Fire fireIncident, Vertex fireLocation) {
        if (fireIncident.getStateIncident() == StateIncident.UNSOLVED) {
            List<Vehicle> availableFireTrucks = vehiclesByType.get(VehicleType.FIRE_TRUCK);
            if (availableFireTrucks != null && !availableFireTrucks.isEmpty()) {
                FireTruck firetruck = (FireTruck) availableFireTrucks.remove(0);
                assignedVehicles.get(VehicleType.FIRE_TRUCK).add(firetruck);
                firetruck.setIncident(fireIncident);
                fireIncident.setFireTruck(firetruck);

                if (fireLocation != null && this.map != null && this.map.getCorners() != null) {
                    List<Vertex> ruta = this.map.getCorners().getRutaMasCorta("Bomberos", fireLocation.getId());
                    firetruck.followRoute(ruta);
                }
                fireIncident.setStateIncident(StateIncident.IN_PROGRESS);
                return true;
            }
        }
        return false;
    }

    public boolean assignPatrolToHeist(Heist heistIncident, Vertex heistLocation) {
        if (heistIncident.getStateIncident() == StateIncident.UNSOLVED) {
            List<Vehicle> availablePoliceCars = vehiclesByType.get(VehicleType.POLICE);
            if (availablePoliceCars != null && !availablePoliceCars.isEmpty()) {
                Patrol policeCar = (Patrol) availablePoliceCars.remove(0);
                assignedVehicles.get(VehicleType.POLICE).add(policeCar);
                policeCar.setIncident(heistIncident);
                heistIncident.setPoliceCar(policeCar);

                if (heistLocation != null && this.map != null && this.map.getCorners() != null) {
                    List<Vertex> ruta = this.map.getCorners().getRutaMasCorta("Policia", heistLocation.getId());
                    policeCar.followRoute(ruta);
                }
                heistIncident.setStateIncident(StateIncident.IN_PROGRESS);
                return true;
            }
        }
        return false;
    }

    public void handleResolvedCrash(Crash resolvedCrash) {
        Ambulance amb = resolvedCrash.getAmbulance();
        Vertex playerSpawnPoint = (map != null && map.getCorners() != null) ? map.getCorners().getVertex("A1") : null;

        if (amb != null) {
            Vertex hospitalVertex = (map != null && map.getCorners() != null) ? map.getCorners().getVertex("Hospital") : null;
            if (hospitalVertex != null) {
                amb.setX(hospitalVertex.getX());
                amb.setY(hospitalVertex.getY());
            }
            amb.setIncident(null);
            amb.setOnTheCrash(false);

            assignedVehicles.get(VehicleType.AMBULANCE).remove(amb);
            if (!vehiclesByType.get(VehicleType.AMBULANCE).contains(amb)) {
                vehiclesByType.get(VehicleType.AMBULANCE).add(amb);
            }
            resolvedCrash.setAmbulance(null);
        }

        resetVehicleAfterCrash(resolvedCrash.getV1(), playerSpawnPoint);
        resetVehicleAfterCrash(resolvedCrash.getV2(), playerSpawnPoint);
    }

    private void resetVehicleAfterCrash(Vehicle vehicle, Vertex playerSpawnLocation) {
        if (vehicle == null) return;
        if (vehicle instanceof NpcVehicle npc) {
            Vertex newNpcPos = getRandomExtremeVertex();
            if (newNpcPos != null) {
                npc.setX(newNpcPos.getX());
                npc.setY(newNpcPos.getY());
            }
            npc.setCrashed(false);
            npc.setStop(false);
            npc.setFinishRoot(true); // Para que busque nueva ruta
        } else if (vehicle instanceof VehiclePlayer vp) {
            if (playerSpawnLocation != null) {
                vp.resetStateAfterCrash(playerSpawnLocation.getX(), playerSpawnLocation.getY());
            } else { // Fallback si no hay punto de spawn definido
                vp.resetStateAfterCrash(vp.getX() + 10, vp.getY() + 10); // Moverlo un poco
                vp.setCrashed(false); // Asegurar que no esté crasheado
            }
        }
    }

    public void handleResolvedFire(Fire resolvedFire) {
        if (resolvedFire.getZone() != null) resolvedFire.getZone().setIncident(null);

        FireTruck firetruck = resolvedFire.getFireTruck();
        if (firetruck != null) {
            Vertex fireStationLocation = (map != null && map.getCorners() != null) ? map.getCorners().getVertex("Bomberos") : null;
            if (fireStationLocation != null) {
                firetruck.setX(fireStationLocation.getX());
                firetruck.setY(fireStationLocation.getY());
            }
            firetruck.setIncident(null);
            firetruck.setOnTheFire(false);

            assignedVehicles.get(VehicleType.FIRE_TRUCK).remove(firetruck);
            if (!vehiclesByType.get(VehicleType.FIRE_TRUCK).contains(firetruck)) {
                vehiclesByType.get(VehicleType.FIRE_TRUCK).add(firetruck);
            }
            resolvedFire.setFireTruck(null);
        }
    }

    public void handleResolvedHeist(Heist resolvedHeist) {
        if (resolvedHeist.getZone() != null) resolvedHeist.getZone().setIncident(null);

        Patrol policeCar = resolvedHeist.getPoliceCar();
        if (policeCar != null) {
            Vertex policeStationLocation = (map != null && map.getCorners() != null) ? map.getCorners().getVertex("Policia") : null;
            if (policeStationLocation != null) {
                policeCar.setX(policeStationLocation.getX());
                policeCar.setY(policeStationLocation.getY());
            }
            policeCar.setIncident(null);
            policeCar.setOnTheHeist(false);

            assignedVehicles.get(VehicleType.POLICE).remove(policeCar);
            if (!vehiclesByType.get(VehicleType.POLICE).contains(policeCar)) {
                vehiclesByType.get(VehicleType.POLICE).add(policeCar);
            }
            resolvedHeist.setPoliceCar(null);
        }
    }
}

