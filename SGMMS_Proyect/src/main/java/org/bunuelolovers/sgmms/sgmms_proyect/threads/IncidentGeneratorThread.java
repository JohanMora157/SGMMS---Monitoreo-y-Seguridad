package org.bunuelolovers.sgmms.sgmms_proyect.threads;

import org.bunuelolovers.sgmms.sgmms_proyect.manager.SoundEffectsManager;
import org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers.EntitiesController;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.Fire;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.Heist;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.Incident;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.StateIncident;
import org.bunuelolovers.sgmms.sgmms_proyect.model.map.MapClass;
import org.bunuelolovers.sgmms.sgmms_proyect.model.map.Zone;

import java.util.List;
import java.util.Random;

public class IncidentGeneratorThread implements Runnable {

    private EntitiesController entitiesController;
    private Random random;
    private volatile boolean isRunning = true;

    public IncidentGeneratorThread(   ) {
        entitiesController =  EntitiesController.getInstance();

        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                Thread.sleep(random.nextInt(10000) + 5000);
                 MapClass map = entitiesController.getMap();
                if (map == null || map.getZones().isEmpty()) {
                    continue;
                }

                List<Zone> zones = map.getZones();
                Zone randomZone = zones.get(random.nextInt(zones.size()));

                if(randomZone.getIncident() != null) {
                    continue;
                }

                Incident newIncident = null;
                int incidentType = random.nextInt(2);

                if (incidentType == 0) {
                    String fireId = "fire in "+ randomZone.getName();
                    SoundEffectsManager.playIncendio();                    int gravity = random.nextInt(3) + 1;
                    newIncident = new Fire(fireId, gravity, StateIncident.UNSOLVED, randomZone.getX(),randomZone.getY(),null,randomZone);
                    randomZone.setIncident(newIncident);
                    new Thread((Fire)newIncident).start();

                } else {
                    String heistId = "heist in "+ randomZone.getName();
                    SoundEffectsManager.playRobo();                    int gravity = random.nextInt(3) + 1;
                    randomZone.setIncident(newIncident);
                    newIncident = new Heist(heistId, gravity, StateIncident.UNSOLVED,  randomZone.getX(),randomZone.getY(),null,randomZone);
                }

                if (newIncident != null) {
                    entitiesController.getIncidents().insert(newIncident);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isRunning = false;
    }
}