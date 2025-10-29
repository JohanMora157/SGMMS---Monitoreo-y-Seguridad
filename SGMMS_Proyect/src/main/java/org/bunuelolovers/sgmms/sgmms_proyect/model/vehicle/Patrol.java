package org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.Heist;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.StateIncident;

import java.util.ArrayList;
import java.util.List;

public class Patrol extends Vehicle {

    private int frame;
    private List<Image> imagesUP, imagesDOWN, imagesLEFT, imagesRIGHT;
    private Heist incident;
    private Image actualImage;
    private boolean isOnTheHeist;


    public Patrol(String id, double x, double y, double height, double weight, Canvas canvas, int timer) {
        super(id, x, y, height, weight, canvas, timer, VehicleType.POLICE);
        frame = 0;
        isOnTheHeist = false;
        incident = null;
        imagesUP = new ArrayList<>();
        imagesDOWN = new ArrayList<>();
        imagesLEFT = new ArrayList<>();
        imagesRIGHT = new ArrayList<>();

        try {
            for (int i = 0; i < 10; i++) {
                imagesRIGHT.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/police/EAST/SEPARATED/CAR_POLICE_0" + i + ".png"),
                        weight, height, false, false));
            }
            for (int i = 0; i < 10; i++) {

                imagesUP.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/police/NORTH/SEPARATED/CAR_POLICE_0" + i + ".png"),
                        weight, height, false, false));
            }
            for (int i = 0; i < 10; i++) {

                imagesLEFT.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/police/WEST/SEPARATED/CAR_POLICE_0" + i + ".png"),
                        weight, height, false, false));
            }
            for (int i = 0; i < 10; i++) {

                imagesDOWN.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/police/SOUTH/SEPARATED/CAR_POLICE_0" + i + ".png"),
                        weight, height, false, false));
            }

            actualImage = imagesRIGHT.get(0);
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
                resolveHeist(incident);
                sleep(timer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean resolveHeist(Heist incident) {
        if (incident == null) {
            isOnTheHeist = false;
            return false;
        }
        if (isOnTheHeist && incident.getStateIncident() == StateIncident.IN_PROGRESS) {
            incident.setStateIncident(StateIncident.SOLVED);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.incident = null;
            isOnTheHeist = false;
            return true;
        }
        return false;
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
                isOnTheHeist = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void moveTo(double targetX, double targetY) throws InterruptedException {
        while ((int)x != (int)targetX) {
            if (x < targetX) { mover(2, 0); x += 1; }
            else { mover(-2, 0); x -= 1; }
            Thread.sleep(timer);
        }
        while ((int)y != (int)targetY) {
            if (y < targetY) { mover(0, 2); y += 1; }
            else { mover(0, -2); y -= 1; }
            Thread.sleep(timer);
        }
    }


    public Heist getIncident() {
        return incident;
    }

    public void setIncident(Heist incident) {
        this.incident = incident;
    }

    public void setOnTheHeist(boolean onTheHeist) {
        isOnTheHeist = onTheHeist;
    }
}