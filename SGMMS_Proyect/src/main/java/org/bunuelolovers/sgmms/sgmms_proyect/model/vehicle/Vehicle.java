package org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;

import java.util.List;

public abstract class Vehicle extends Thread  {

    protected double x;
    protected double y;
    protected double height;
    protected double width;
    protected int timer;

    protected VehicleType vehicleType;

    protected String id;

    protected GraphicsContext gc;

    private boolean isRunning;


     public Vehicle(String id,double x, double y, double height, double width, Canvas canvas,int timer, VehicleType vehicleType) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.timer = timer;
        this.vehicleType = vehicleType;
        gc = canvas.getGraphicsContext2D();
        this.isRunning = true;

    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public abstract void paint(GraphicsContext gc);

    public abstract void followRoute(List<Vertex> route);



    public String getid(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

}