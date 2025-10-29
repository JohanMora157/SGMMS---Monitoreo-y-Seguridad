package org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;

import java.util.ArrayList;
import java.util.List;

public class NpcVehicle extends Vehicle implements Runnable{

    private List<Image> crash;
    private int frame;
    private Image actualImage;

    private boolean stop;

    private boolean isCrashed;

    private List<Image> imagesUP;
    private List<Image> imagesDOWN;
    private List<Image> imagesLEFT;
    private List<Image> imagesRIGHT;
    private int typeCar; // esto es porque hay variedad de carros de npc

    private boolean finishRoot;


    public NpcVehicle(String id, double x, double y, double height, double weight, Canvas canvas, int timer) {
        super(id, x, y, height, weight, canvas, timer, VehicleType.NPC);
        this.frame = 0;
        isCrashed = false;

        typeCar= (int) (Math.random() * 5 + 1);
        stop = false;


        crash = new ArrayList<Image>();
        imagesUP = new ArrayList<Image>();
        imagesDOWN = new ArrayList<Image>();
        imagesLEFT = new ArrayList<Image>();
        imagesRIGHT = new ArrayList<Image>();

        finishRoot = false;

        try{
            for(int i = 0; i <18 ; i++){
                 crash.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/crashCar/crashFirecar"+i+".png"),
                        weight, height, false, false));
            }
            for(int i = 0; i < 10; i++){
                 imagesRIGHT.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/npc"+typeCar+"/EAST/SEPARATED/CAR_NPC"+typeCar+"_0" + i + ".png"),
                        weight, height, false, false));
            }
            for(int i = 0; i < 10; i++){

                imagesUP.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/npc"+typeCar+"/NORTH/SEPARATED/CAR_NPC"+typeCar+"_0" + i + ".png"),
                        weight, height, false, false));
            }
            for(int i = 0; i < 10; i++){

                imagesLEFT.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/npc"+typeCar+"/WEST/SEPARATED/CAR_NPC"+typeCar+"_0" + i + ".png"),
                        weight, height, false, false));
            }
            for(int i = 0; i < 10; i++){

                imagesDOWN.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/npcCar/npc"+typeCar+"/SOUTH/SEPARATED/CAR_NPC"+typeCar+"_0" + i + ".png"),
                        weight, height, false, false));
            }

            this.actualImage = imagesRIGHT.get(0);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void paint(GraphicsContext gc) {

        if(!isCrashed) {
            if(frame == 8){
                frame = 0;
            }
            gc.drawImage(actualImage, this.x, this.y, actualImage.getWidth(), actualImage.getHeight());
        }else{

            gc.drawImage(crash.get(frame), x, y, 70, 45);
        }

}
@Override
    public void run() {
    try {
        while (isRunning()) {
            if (!isCrashed) {
                frame++;
                if (frame > 8) {
                    frame = 0;
                }
            } else {
                frame++;
                if (frame == 17) {
                    frame = 0;
                }
            }
            sleep(100);

        }
    } catch (Exception e) {
        e.printStackTrace();
    }

}


    public void mover(double dx, double dy) {

        if(isCrashed) {
            actualImage = crash.get(frame);
            return;
        }

        if (stop) {

            return;
        }
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
        if(isCrashed){
            return;
        }
        if(stop) {
            return;
        }
        new Thread(() -> {
            try {
                for (Vertex destino : ruta) {
                    moveTo(destino.getX(), destino.getY());
                }
                finishRoot =true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }

    private void moveTo(double targetX, double targetY) throws InterruptedException {
        if(isCrashed || stop) {
            return;
        } else {
            while ((int)x != (int)targetX) {
                if(isCrashed || stop){
                    break;
                }
                if(x<targetX) {
                    mover(2, 0);
                    x+=1;
                }else{
                    mover(-2, 0);
                    x-=1;
                }
                Thread.sleep(timer);
            }
            while ((int)y != (int)targetY) {
                if(isCrashed || stop){
                    break;
                }
                if(y<targetY) {
                    mover(0, 2);
                    y+=1;
                }else{
                    mover(0, -2);
                    y-=1;
                }
                Thread.sleep(timer);
            }
        }
    }

    public boolean isFinishRoot() {
        return finishRoot;
    }

    public void setFinishRoot(boolean finishRoot) {
        this.finishRoot = finishRoot;
    }

    public boolean isCrashed() {
        return isCrashed;
    }

    public void setCrashed(boolean crashed) {
        frame = 0;
        isCrashed = crashed;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {

        if(stop) {
            finishRoot = true;
        }
        this.stop = stop;
    }
}
