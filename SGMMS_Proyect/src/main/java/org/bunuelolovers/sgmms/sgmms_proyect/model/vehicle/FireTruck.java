package org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.Fire;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.StateIncident;

import java.util.ArrayList;
import java.util.List;

public class FireTruck extends Vehicle {

    private int frame;
    private List<Image> imagesUP;
    private List<Image> imagesDOWN;
    private List<Image> imagesLEFT;
    private List<Image> imagesRIGHT;

    private Fire incident;
    private Image actualImage;
    private boolean isOnTheFire;


    public FireTruck(String id, double x, double y, double height, double weight, Canvas canvas, int timer) {
        super(id, x, y, height, weight, canvas, timer, VehicleType.FIRE_TRUCK);
        this.frame = 0;
        isOnTheFire = false;
        incident = null;

        imagesUP = new ArrayList<>();
        imagesDOWN = new ArrayList<>();
        imagesLEFT = new ArrayList<>();
        imagesRIGHT = new ArrayList<>();



        try {
            for (int i = 0; i < 10; i++) {
                imagesRIGHT.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/fireTruck/EAST/SEPARATED/CAR_BOMBER_0" + i + ".png"),
                        weight, height, false, false));
            }
            for (int i = 0; i < 10; i++) {
                imagesUP.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/fireTruck/NORTH/SEPARATED/CAR_BOMBER_0" + i + ".png"),
                        weight, height, false, false));
            }
            for (int i = 0; i < 10; i++) {
                imagesLEFT.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/fireTruck/WEST/SEPARATED/CAR_BOMBER_0" + i + ".png"),
                        weight, height, false, false));
            }
            for (int i = 0; i < 10; i++) {
                imagesDOWN.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/fireTruck/SOUTH/SEPARATED/CAR_BOMBER_0" + i + ".png"),
                        weight, height, false, false));
            }
            this.actualImage = imagesRIGHT.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.drawImage(actualImage, this.x, this.y, actualImage.getWidth(), actualImage.getHeight());
    }

    @Override
    public void run() {
        try {
            while (isRunning()) {
                frame++;
                if (frame == 9) {
                    frame = 0;
                }
                resolveFire(incident);
                sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mover(double dx, double dy) {
        if (dx > 0) {
            actualImage = imagesRIGHT.get(frame);
            setWidth(48);
            setHeight(26);
        } else if (dx < 0) {
            actualImage = imagesLEFT.get(frame);
            setWidth(48);
            setHeight(26);
        } else if (dy > 0) {
            actualImage = imagesDOWN.get(frame);
            setWidth(26);
            setHeight(48);
        } else if (dy < 0) {
            actualImage = imagesUP.get(frame);
            setWidth(26);
            setHeight(48);
        }
    }

    @Override
    public void followRoute(List<Vertex> ruta) {
        new Thread(() -> {
            try {
                for (Vertex destino : ruta) {
                    moveTo(destino.getX(), destino.getY());
                }
                isOnTheFire = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }




    private void moveTo(double targetX, double targetY) throws InterruptedException {
        while ((int) x != (int) targetX) {
            if (x < targetX) {
                mover(2, 0);
                x += 1;
            } else {
                mover(-2, 0);
                x -= 1;
            }
            Thread.sleep(timer);
        }
        while ((int) y != (int) targetY) {
            if (y < targetY) {
                mover(0, 2);
                y += 1;
            } else {
                mover(0, -2);
                y -= 1;
            }
            Thread.sleep(timer);
        }
    }

    public boolean resolveFire(Fire incident) {
        if (incident == null) {
            isOnTheFire = false;
            return false;
        }
        boolean result = false;
        if (isOnTheFire) {
            if (incident.getStateIncident() == StateIncident.IN_PROGRESS) {
                incident.setStateIncident(StateIncident.SOLVED);
                this.incident = null;
                isOnTheFire = false;
                result = true;
            }
        }
        return result;
    }

    public Fire getIncident() {
        return incident;
    }

    public void setIncident(Fire incident) {
        this.incident = incident;
    }

    public void setOnTheFire(boolean onTheFire) {
        isOnTheFire = onTheFire;
    }


}