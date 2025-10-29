package org.bunuelolovers.sgmms.sgmms_proyect.graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private final String id;
    private final double x;
    private final double y;
    private final List<Vertex> vecinos = new ArrayList<>();

    public Vertex(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public List<Vertex> getneighboor() {
        return vecinos;
    }

    public void addNeighboor(Vertex v) {
        if (!vecinos.contains(v)) {
            vecinos.add(v);
        }
        if (!v.vecinos.contains(this)) {
            v.vecinos.add(this);
        }
    }


    @Override
    public String toString() {
        return id + " (" + x + ", " + y + ")";
    }
}
