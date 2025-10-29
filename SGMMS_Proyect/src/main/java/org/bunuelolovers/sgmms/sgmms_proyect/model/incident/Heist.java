package org.bunuelolovers.sgmms.sgmms_proyect.model.incident;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;
import org.bunuelolovers.sgmms.sgmms_proyect.model.map.Zone;
import org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle.FireTruck;
import org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle.Patrol;

import java.util.List;

public class Heist extends Incident {
     private Image activeThief;
    private Image caughtThief;

    private Patrol PoliceCar;
    private Zone Zone;

    private double x,y;


    public Heist(String id, int gravity, StateIncident stateIncident, double x, double y , Vertex position, Zone zone) {
        super(id, gravity, stateIncident, position, IncidentType.HEIST);
        this.x = x-20;
        this.y = y-10;
        this.PoliceCar = null;
        this.Zone = zone;


        activeThief =new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/incidents/heistAssets/thief.png"),
                60, 60, true, false);

        caughtThief = new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/incidents/heistAssets/thiefCaught.png"),60,60, true, false);



    }

    public void paint(GraphicsContext gc)  {

        if (getStateIncident() == StateIncident.UNSOLVED || getStateIncident() == StateIncident.IN_PROGRESS) {
            gc.drawImage(activeThief, x,y);
        } else if (getStateIncident() == StateIncident.SOLVED) {
            gc.drawImage(caughtThief,x,y);
        }
    }

    public Zone getZone() {
        return Zone;
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

    public Patrol getPoliceCar() {
        return PoliceCar;
    }

    public void setPoliceCar(Patrol policeCar) {
        PoliceCar = policeCar;
    }
}