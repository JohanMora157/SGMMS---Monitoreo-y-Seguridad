package org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SoundEffectsManager;
import org.bunuelolovers.sgmms.sgmms_proyect.model.map.Block;

import java.util.ArrayList;
import java.util.List;

public class VehiclePlayer  extends Vehicle implements Runnable {
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    private boolean crashed = false;

    private boolean stop = false;

    private List<Block> collisions;

    private int frame;
    private int crashFrame;

    private List<Image> crash;

    private List<Image> imagesUP;
    private List<Image> imagesDOWN;
    private List<Image> imagesLEFT;
    private List<Image> imagesRIGHT;

    private Image actualImage;
    private Image defaultImage;

    @Override
    public void followRoute(List<Vertex> route) {

    }

    public VehiclePlayer(String id, double x, double y, double height, double width, Canvas canvas, int timer) {
        super(id, x, y, height, width, canvas, timer, VehicleType.PLAYER);

        crash = new ArrayList<>();
        collisions = new ArrayList<Block>();
        imagesUP = new ArrayList<Image>();
        imagesDOWN = new ArrayList<Image>();
        imagesLEFT = new ArrayList<Image>();
        imagesRIGHT = new ArrayList<Image>();


        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;

        try {
            for(int i = 0; i < 18; i++){
                crash.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/crashCar/crashFirecar"+i+".png"),
                        width, height, false, false));
            }
            for (int i = 0; i < 10; i++) {
                imagesRIGHT.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/playerCar/EAST/SEPARATED/Black_MUSCLECAR_CLEAN_EAST_00" + i + ".png"),
                        width, height, true, false));
            }
            for (int i = 0; i < 10; i++) {
                imagesUP.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/playerCar/NORTH/SEPARATED/Black_MUSCLECAR_CLEAN_NORTH_00" + i + ".png"),
                        width, height, true, false));
            }
            for (int i = 0; i < 10; i++) {
                imagesLEFT.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/playerCar/WEST/SEPARATED/Black_MUSCLECAR_CLEAN_WEST_00" + i + ".png"),
                        width, height, true, false));
            }
            for (int i = 0; i < 10; i++) {
                imagesDOWN.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/playerCar/SOUTH/SEPARATED/Black_MUSCLECAR_CLEAN_SOUTH_00" + i + ".png"),
                        width, height, true, false));
            }
            this.defaultImage = imagesRIGHT.get(0);
            this.actualImage = defaultImage;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            while (isRunning()) {
                if(isCrashed()){
                    crashFrame++;
                    if(crashFrame >= crash.size()){
                        crashFrame = 0;
                    }
                }
                frame++;
                if (frame == 9) {
                    frame = 0;
                }
                sleep(100);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void paint(GraphicsContext gc) {

        if(crashed){
            gc.drawImage(crash.get(frame), x, y, 70, 45);

        }else {
            gc.drawImage(actualImage, x, y, height, width);
        }
        }


    public void onMove() {
        if(isCrashed()){
            actualImage = crash.get(crashFrame % crash.size());
            return;
        }
        if(stop){
            return;
        }
        if(!crashed || !isRunning()){
            if (up) {
                if (y <= -20) {
                    y = 1270;
                } else if (canMoveV(-4)) {
                    y -= 1;

                }
                actualImage = imagesUP.get(frame);
            }

            if (down) {
                if (y >= 1270) {
                    y = -30;
                } else if (canMoveV(4)) {
                    y += 1;

                }
                actualImage = imagesDOWN.get(frame);
            }

            if (left) {
                if (x <= -20) {
                    x = 1280;
                } else if (canMoveH(-4)) {
                    x -= 1;

                    actualImage = imagesLEFT.get(frame);
                }
            }
            if (right) {
                if (x >= 1270) {
                    x = -20;
                } else if (canMoveH(4)) {
                    x += 1;
                    actualImage = imagesRIGHT.get(frame);
                }
            }

        }
    }

    public boolean canMoveH(double dx) {

        double futureX = x + dx;
        for (Block block : collisions) {
            Rectangle rectangle = block.getBounds();

            double rectX = rectangle.getX();
            double rectY = rectangle.getY();
            double rectW = rectangle.getWidth();
            double rectH = rectangle.getHeight();

            if (futureX < rectX + rectW &&
                    futureX + width > rectX &&
                    y < rectY + rectH &&
                    y + height > rectY) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveV ( double dy){
                double futureY = y + dy;
                for (Block block : collisions) {
                    Rectangle rectangle = block.getBounds();

                    double rectX = rectangle.getX();
                    double rectY = rectangle.getY();
                    double rectW = rectangle.getWidth();
                    double rectH = rectangle.getHeight();

                    if (x < rectX + rectW &&
                            x + width > rectX &&
                            futureY < rectY + rectH &&
                            futureY + height > rectY) {
                        return false;
                    }
                }
                return true;

            }

    public void setOnKeyPressed (KeyEvent e){
                switch (e.getCode()) {
                    case W:
                        this.up = true;

                        break;
                    case S:
                        this.down = true;

                        break;
                    case A:
                        this.left = true;

                        break;
                    case D:
                        this.right = true;

                        break;

                    case R:
                        SoundEffectsManager.playRadio3();
                        break;

                    case L:
                        SoundEffectsManager.stopAllRadios();
                        break;

                    case P:
                        SoundEffectsManager.playPito1();
                        break;

                    default:
                        break;
                }
            }

    public void setOnKeyReleased (KeyEvent e){
        switch (e.getCode()) {
            case W -> {
                up = false;
            }
            case S -> {
                down = false;
            }
            case A -> {
                left = false;
            }
            case D -> {
                right = false;
            }

            case P -> {
                SoundEffectsManager.stopPito1();
            }
        }
    }

    public void resetStateAfterCrash(double newX, double newY){
        setX(newX);
        setY(newY);
        setCrashed(false);
        if(this.defaultImage != null){
            this.actualImage = this.defaultImage;
        }
        this.frame = 0;
        this.crashFrame = 0;
    }


    public void setCollisions (List < Block > collisions) {
        this.collisions = collisions;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
        if(crashed){
            SoundEffectsManager.playChoque();

        }
    }

    public boolean isCrashed() {
        return crashed;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;

    }
}





