package org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.Crash;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.Incident;
import org.bunuelolovers.sgmms.sgmms_proyect.model.incident.StateIncident;

import java.util.ArrayList;
import java.util.List;

public class Ambulance extends Vehicle {

    private int frame;
    private List<Image> imagesUP;
    private List<Image> imagesDOWN;
    private List<Image> imagesLEFT;
    private List<Image> imagesRIGHT;

    private Crash incident;

    private Image actualImage;

    private boolean isOnTheCrash;


    public Ambulance(String id, double x, double y, double height, double weight, Canvas canvas, int timer) {
        super(id, x, y, height, weight, canvas, timer, VehicleType.AMBULANCE);
        this.frame = 0;
        isOnTheCrash = false;

        incident = null;


        imagesUP = new ArrayList<Image>();
        imagesDOWN = new ArrayList<Image>();
        imagesLEFT = new ArrayList<Image>();
        imagesRIGHT = new ArrayList<Image>();

        try {

            for (int i = 0; i < 10; i++) {
                imagesRIGHT.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/ambulance/EAST/SEPARATED/CAR_AMBULANCE_0" + i + ".png"),
                        weight, height, false, false));
            }
            for (int i = 0; i < 10; i++) {

                imagesUP.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/ambulance/NORTH/SEPARATED/CAR_AMBULANCE_0" + i + ".png"),
                        weight, height, false, false));
            }
            for (int i = 0; i < 10; i++) {

                imagesLEFT.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/ambulance/WEST/SEPARATED/CAR_AMBULANCE_0" + i + ".png"),
                        weight, height, false, false));
            }
            for (int i = 0; i < 10; i++) {

                imagesDOWN.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/ambulance/SOUTH/SEPARATED/CAR_AMBULANCE_0" + i + ".png"),
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

                resolveCrash(incident);

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
                isOnTheCrash = true;
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
    public boolean resolveCrash(Incident incident) {
        if (incident == null || !(incident instanceof Crash)) {
            // Si no hay incidente o no es un Crash, no hacer nada o resetear estado local
            // isOnTheCrash = false; // Podría resetearse aquí si el incidente se limpia externamente
            return false;
        }

        boolean result = false;
        // Asegúrate que 'this.incident' (el incidente asignado a la ambulancia) es el que se está evaluando
        // y que la ambulancia ha llegado al sitio.
        if (isOnTheCrash && this.incident == incident && incident.getStateIncident() == StateIncident.IN_PROGRESS) {
            incident.setStateIncident(StateIncident.SOLVED);
            // No es necesario limpiar this.incident o isOnTheCrash aquí,
            // EntitiesController lo hará cuando la ambulancia regrese a la base.
            result = true;
        }
        return result;
    }

    public Crash getIncident() {
        return incident;
    }

    public void setIncident(Crash incident) {
        this.incident = incident;
    }

    public void setOnTheCrash(boolean onTheCrash) {
        isOnTheCrash = onTheCrash;
    }
}
