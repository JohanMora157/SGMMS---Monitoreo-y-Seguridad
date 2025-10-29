package org.bunuelolovers.sgmms.sgmms_proyect.model.map;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static java.lang.Thread.sleep;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SceneManager;


public class Score extends Thread{
    private int score;
    private List<Image> images;
    private int frame;
    private GraphicsContext gc;
    private int tiempo;
    private Image iconoTiempo;

    private boolean isRunning;

    public Score(Canvas canvas) {

        isRunning = true;
        images = new ArrayList<>();
        frame = 1;
        this.gc = canvas.getGraphicsContext2D();
        this.score = 0;
        for(int i = 0; i < 13; i++){
            images.add(new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/score/coin_"+ (i+1) + ".png")));
        }
         try {
            iconoTiempo = new Image(getClass().getResourceAsStream("/org/bunuelolovers/sgmms/sgmms_proyect/images/clock.png"));
        } catch (Exception e) {
         }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                frame = (frame + 1) % images.size();

                Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
    public void paint() {
        // Dibujar puntos
        gc.drawImage(images.get(frame), 10, 14, 32, 32);
        gc.setFont(Font.font("Consolas", FontWeight.BOLD, 24));
        gc.setFill(Color.WHITE);
        String scoreText = "x " + score;
        gc.fillText(scoreText, 50, 38);
        gc.setStroke(Color.BLACK);
        gc.strokeText(scoreText, 50, 38);

        // Dibujar tiempo
        int minutos = tiempo / 60;
        int segundos = tiempo % 60;
        String tiempoText = String.format("%02d:%02d", minutos, segundos);

        if (iconoTiempo != null) {
            gc.drawImage(iconoTiempo, 10, 60, 32, 32);
        }

        gc.setFill(Color.WHITE);
        gc.fillText(tiempoText, 50, 84);
        gc.setStroke(Color.BLACK);
        gc.strokeText(tiempoText, 50, 84);
    }

    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
    }
}