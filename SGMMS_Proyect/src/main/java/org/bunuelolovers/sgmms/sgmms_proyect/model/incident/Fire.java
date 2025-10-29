package org.bunuelolovers.sgmms.sgmms_proyect.model.incident;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;
import org.bunuelolovers.sgmms.sgmms_proyect.model.map.Zone;
import org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle.FireTruck;

import java.util.ArrayList;
import java.util.List;

public class Fire extends Incident implements Runnable{
    private  FireTruck fireTruck;
    private int frame;
    private Image currentImage;
    private List<Image> fireFrames;
    private double x,y;
    private Zone zone;

    public Fire(String id, int gravity, StateIncident stateIncident,double x, double y, Vertex position, Zone zone) {
        super(id, gravity, stateIncident, position, IncidentType.FIRE);
        fireFrames = new ArrayList<Image>();
        this.x = x-10;
        this.y = y-30;
        this.zone = zone;
        frame = 0;


        for (int i = 0; i < 8; i++) {
            fireFrames.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/incidents/fireAssets/fire0" + i + ".png"),
                    60, 60, true, false));
        }

        currentImage = fireFrames.get(frame);

    }

    public Zone getZone() {
        return zone;
    }

    public void paint(GraphicsContext gc) {
        if (currentImage != null) {
            gc.drawImage(currentImage, x, y);
            gc.drawImage(currentImage, x-20,y);
            gc.drawImage(currentImage, x-40,y);
        }
    }


    @Override
    public void run() {
        while(getStateIncident() == StateIncident.UNSOLVED || getStateIncident() == StateIncident.IN_PROGRESS) {
            try {

                frame++;
                if(frame == 8){
                    frame = 0;
                }
                currentImage = fireFrames.get(frame);

                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public FireTruck getFireTruck() {
        return fireTruck;
    }

    public void setFireTruck(FireTruck fireTruck) {
        this.fireTruck = fireTruck;
    }
}