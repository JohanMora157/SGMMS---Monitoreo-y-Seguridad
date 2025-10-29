package org.bunuelolovers.sgmms.sgmms_proyect.manager;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class SoundEffectsManager {


    private static AudioClip loadSound(String path) {
        URL url = SoundEffectsManager.class.getResource(path);
        if (url != null) {
            return new AudioClip(url.toString());
        } else {
            return null;
        }
    }

    private static final AudioClip AmbienceSound = loadSound("/org/bunuelolovers/sgmms/sgmms_proyect/audio/soundEffects/motor.mp3");
    private static final AudioClip radio3 = loadSound("/org/bunuelolovers/sgmms/sgmms_proyect/audio/songs/radio3.mp3");
    private static final AudioClip pito1 = loadSound("/org/bunuelolovers/sgmms/sgmms_proyect/audio/soundEffects/pito1.mp3");
    private static final AudioClip robo = loadSound("/org/bunuelolovers/sgmms/sgmms_proyect/audio/soundEffects/robo.mp3");
    private static final AudioClip incendio = loadSound("/org/bunuelolovers/sgmms/sgmms_proyect/audio/soundEffects/incendio.mp3");
    private static final AudioClip choque = loadSound("/org/bunuelolovers/sgmms/sgmms_proyect/audio/soundEffects/crash.mp3");

    public static void playAmbience() {
        if (AmbienceSound != null) {
            AmbienceSound.setVolume(0.12);
            AmbienceSound.setCycleCount(MediaPlayer.INDEFINITE); // Repetir bucle
            if (!AmbienceSound.isPlaying()) {
                AmbienceSound.play();
            }
        }
    }

    public static void stopAmbience() {

        if (AmbienceSound != null) AmbienceSound.stop();
    }


    public static void stopAllRadios() {

        if (radio3 != null) radio3.stop();
        if(pito1 != null) pito1.stop();
    }

    public static void playRadio3() {
        stopAllRadios();
        if (radio3 != null) {
            radio3.setVolume(0.1);
            radio3.play();
        }
    }

    public static void playPito1() {
        if (pito1 != null) {
            pito1.setVolume(0.1);
            if (!pito1.isPlaying()) {
                pito1.play();
            }
        }
    }

    public static void stopPito1() {
        if (pito1 != null && pito1.isPlaying()) {
            pito1.stop();
        }
    }

    public static void playRobo() {
        if (robo != null) robo.play();
    }


    public static void playIncendio() {
        if (incendio != null) {
            incendio.setVolume(0.2);
            incendio.play();
        }
    }

    public static void playChoque() {
        if (choque != null) {
            choque.setVolume(0.2);
            choque.play();
        }
    }
}
