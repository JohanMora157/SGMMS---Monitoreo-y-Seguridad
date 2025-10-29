package org.bunuelolovers.sgmms.sgmms_proyect.model.map;

import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.Incident;

public class Zone {

    private ZoneType zoneType;
    private String name;
    private Incident incident;
    private double x,y;


    public Zone(ZoneType zoneType, String name, double x, double y) {
        this.zoneType = zoneType;
        this.name = name;
        this.x = x;
        this.y = y;

    }


    public String getName() {
        return name;
    }

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
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
}
