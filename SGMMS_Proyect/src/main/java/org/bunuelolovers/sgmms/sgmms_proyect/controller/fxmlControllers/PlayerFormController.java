package org.bunuelolovers.sgmms.sgmms_proyect.controller.fxmlControllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers.EntitiesController;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.AudioManager;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SceneManager;
import org.bunuelolovers.sgmms.sgmms_proyect.manager.SoundEffectsManager;
import org.bunuelolovers.sgmms.sgmms_proyect.model.map.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerFormController implements Initializable {

    private EntitiesController entitiesController;

    @FXML
    private TextField usernameField;

    @FXML
    private Label textWelcome;

    @FXML
    private RadioButton radioFemale, radioMale;

    @FXML
    private TextField minutes;

    @FXML
    private ToggleGroup genre;

    @FXML
    private Canvas fxmlCanvas;

    private GraphicsContext gc;
    private Image carImage;

    private Rectangle[] coordinates;
    private int currentFrame;
    private long lastFrameTime;

    private double carX;
    private double carY;

    private AnimationTimer animationTimer;

    private final double SPEED = 3;
    private final double SCALE = 4;


   @FXML
   protected void OnClickManual() {
       TextArea textArea = new TextArea(mensajeControles);
       textArea.setEditable(false);
       textArea.setWrapText(true);
       textArea.setPrefWidth(500);
       textArea.setPrefHeight(600);
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Manual");
       alert.setHeaderText("¿Como jugar?");
       alert.getDialogPane().setContent(textArea);
       alert.getDialogPane().getStylesheets().add(
           getClass().getResource("/org/bunuelolovers/sgmms/sgmms_proyect/css/playerForm_style.css").toExternalForm()
       );
       alert.showAndWait();
   }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entitiesController = EntitiesController.getInstance();
        AudioManager.playMenuMusic();
        genre = new ToggleGroup();
        radioFemale.setToggleGroup(genre);
        radioMale.setToggleGroup(genre);

        gc = fxmlCanvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);



        carImage = new Image(
                getClass().getResource("/org/bunuelolovers/sgmms/sgmms_proyect/menuCar/RC_Car_Sprite Sheet.png").toExternalForm(),
                false
        );

        initializeAnimations();
        startGameLoop();
    }

    private void initializeAnimations() {
        carX = -50;
        carY = 20;

        coordinates = new Rectangle[]{
                new Rectangle(8, 79, 16, 17),
                new Rectangle(40, 79, 16, 17),
                new Rectangle(72, 79, 16, 17),
                new Rectangle(104, 79, 16, 17),
                new Rectangle(136, 79, 16, 17),

                new Rectangle(8, 143, 16, 17),
                new Rectangle(40, 143, 16, 17),
                new Rectangle(72, 143, 17, 17),
                new Rectangle(104, 143, 17, 17),
                new Rectangle(136, 143, 16, 17)
        };
        currentFrame = 0;
        lastFrameTime = 0;
    }

    private void startGameLoop() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
                render();
            }
        };
        animationTimer.start();
    }

    private void update(long now) {
        carX += SPEED;
        if (carX > fxmlCanvas.getWidth()) {
            carX = -100;
        }

        if (now - lastFrameTime > 150_000_000) {
            currentFrame = (currentFrame + 1) % coordinates.length;
            lastFrameTime = now;
        }
    }

    private void render() {
        gc.clearRect(0, 0, fxmlCanvas.getWidth(), fxmlCanvas.getHeight());

        Rectangle frame = coordinates[currentFrame];
        double drawWidth = frame.getWidth() * SCALE;
        double drawHeight = frame.getHeight() * SCALE;

        gc.drawImage(
                carImage,
                frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight(),
                carX, carY, drawWidth, drawHeight
        );
    }

    @FXML
    protected void onActionStart() {
        String username = usernameField.getText();
        String genreSelected = isFemale();
        int gameMinutes = 1000;
        entitiesController.setGameTimer(gameMinutes);

        try {
            if (!minutes.getText().isEmpty()) {
                gameMinutes = Integer.parseInt(minutes.getText());
            }
            entitiesController.setGameTimer(gameMinutes);
        } catch (NumberFormatException e) {
            minutes.clear();
            minutes.setPromptText("Por favor digite un número entero válido");
            minutes.setStyle("-fx-prompt-text-fill: red;");
            return;
        }


        if (username != null && !username.isEmpty() && genreSelected != null&& minutes.getText() != null && !minutes.getText().isEmpty()){
            Player player = new Player(username, genreSelected);
            textWelcome.setText("Bienvenido/a, " + player.getName());
            animationTimer.stop();
            AudioManager.stopMenuMusic();
            SoundEffectsManager.playAmbience();
            SceneManager.getInstance().switchScene(SceneManager.SceneID.GAME_VIEW, "Map");
        } else {
            usernameField.setPromptText("Por favor escribe algo...");
            minutes.setPromptText("Por favor digita el número de segundos");
            usernameField.setStyle("-fx-prompt-text-fill: red;");
            minutes.setStyle("-fx-prompt-text-fill: red;");
        }
    }

    public String isFemale() {
        radioMale.setUserData("Masculino");
        radioFemale.setUserData("Femenino");
        if (genre.getSelectedToggle() != null && genre.getSelectedToggle().getUserData() != null) {
            return genre.getSelectedToggle().getUserData().toString();
        }
        return null;
    }


    private String mensajeControles =
            "Manual de Controles 🎮\n\n" +
                    "- Avanzar/Arriba 🔼 \n" +
                    "  W\n\n" +
                    "- Atrás/Abajo 🔽\n" +
                    "  S\n\n" +
                    "- Derecha ⏩\n" +
                    "  D\n\n" +
                    "- Izquierda ⏪ \n" +
                    "  A\n\n" +
                    "- Pitar 🔔\n" +
                    "  P\n\n" +
                    "- Radio 📻\n" +
                    "  R\n\n" +
                    "- Resolver el incidente 🖱\n" +
                    "  Clic\n\n" +

                    "Mecánicas ℹ\n\n" +
                    "- El jugador inicia en la esquina superior izquierda del mapa y se puede mover libremente,\n" +
                    "  pero tiene colisiones, así que si colisiona con otro vehículo \"morirá\" y reaparecerá en\n" +
                    "  el mismo punto de aparición. El juego cuenta con un minuto de duración base (editable desde el menú).\n" +
                    "  Además, el jugador tiene una puntuación que sube o baja con cada decisión al resolver un incidente.\n" +
                    "  Cada incidente puede ser resuelto por medio de los 3 botones en la esquina superior izquierda.\n\n" +

                    "- Botones para resolver incidentes de 3 tipos y sus valores en puntos (Correcto e Incorrecto):\n\n" +
                    "  🔥 Incendio       👍 +3 puntos   ❌ -1 punto\n" +
                    "  👮‍♂ Robo           👍 +2 puntos   ❌ -2 puntos\n" +
                    "  🚘 Choques        👍 +4 puntos   ❌ -2 puntos\n\n" +

                    "- El número de autómatas generado es aleatorio entre 5 y 10 🎲\n" +
                    "  (3 Patrullas, 3 Bomberos y 3 Ambulancias)\n\n" +

                    "- Cada 5 segundos se puede generar un incidente 🔔\n\n" +

                    "Si el jugador se choca con otro vehículo pierde 10 puntos y viene una ambulancia 🚑 para ayudarle a reaparecer\n\n" +

                    "NOTAS:\n" +
                    "- Los incidentes ocurren de forma aleatoria y no se pueden tener 2 incidentes en el mismo lugar al mismo tiempo.\n" +
                    "  Si el mapa se llena de incidentes, no se generarán más durante ese periodo.\n" +
                    "- Ver historial de incidentes resueltos (opción en el menú) 🗞\n" +
                    "- Ver historial de incidentes sin resolver en tiempo real (opción en el menú) 📄\n" +
                    "- Cuando hay un incidente y el jugador está cerca, no se puede mover hasta que lo resuelva.\n";

}
