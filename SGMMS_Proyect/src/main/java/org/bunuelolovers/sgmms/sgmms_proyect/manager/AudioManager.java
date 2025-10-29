package org.bunuelolovers.sgmms.sgmms_proyect.manager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {

    // Reproductor de música
    private static MediaPlayer mediaPlayer;
    // Controlar si la música se está reproduciendo
    private static boolean isPlaying = false;

    // Reproducir música en el menú principal en bucle, antes verifica si ya está sonando
    public static void playMenuMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        try {
            Media media = new Media(AudioManager.class.getResource("/org/bunuelolovers/sgmms/sgmms_proyect/audio/songs/racingMenu.mp3").toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            // Hacer que sea un ciclo infinito de la música (ponerla en bucle infinito)
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            // Regulador de volumen (20%)
            mediaPlayer.setVolume(0.1);
            // Reproducir la canción
            mediaPlayer.play();
            isPlaying = true; // Marcar que ya se comenzó a reproducir
        } catch (Exception e) {
            // Error si no carga el audio
            e.printStackTrace();
        }
    }

    // Método para poder poner o quitar la música a elección (usar a futuro con el botón en menú)
    public static void toggleMusicPlaying() {
        if (mediaPlayer != null) {
            if (isPlaying) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
            isPlaying = !isPlaying;
        }
    }

    // Método para verificar si está sonando/reproduciendo
    public static boolean isPlaying() {
        return isPlaying;
    }

    // Parar la música
    public static void stopMenuMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPlaying = false;
        }
    }
}