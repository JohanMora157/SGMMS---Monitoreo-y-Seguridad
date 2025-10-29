package org.bunuelolovers.sgmms.sgmms_proyect.model.map;

import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;

public class Route implements Comparable<Route> {

    private ZoneType zoneType;
    private String name;
    private int time;
    private Vertex start;
    private Vertex end;

    public Route(ZoneType zoneType, String name, Vertex start, Vertex end) {
        this.zoneType = zoneType;
        this.name = name;
        this.start = start;
        this.end = end;
        this.time = (int) (Math.random() * 90 + 1);

    }

    public ZoneType getZoneType() {
        return zoneType;
    }


    public String getName() {
        return name;
    }


    public double getTime() {
        return time;
    }


    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Vertex getEnd() {
        return end;
    }

    @Override
    public int compareTo(Route o) {
         if (this.time < o.time) {
            return -1;
        } else if (this.time > o.time) {
            return 1;
        }
        return 0;
    }
}
