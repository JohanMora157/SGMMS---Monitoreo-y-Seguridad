module org.bunuelolovers.sgmms.sgmms_proyect {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires javafx.media; //para el sonido

    opens org.bunuelolovers.sgmms.sgmms_proyect to javafx.fxml;
    exports org.bunuelolovers.sgmms.sgmms_proyect.app;
    opens org.bunuelolovers.sgmms.sgmms_proyect.app to javafx.fxml;
    exports org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers;
    opens org.bunuelolovers.sgmms.sgmms_proyect.controller.modelControllers to javafx.fxml;
    exports org.bunuelolovers.sgmms.sgmms_proyect.controller.fxmlControllers;
    opens org.bunuelolovers.sgmms.sgmms_proyect.controller.fxmlControllers to javafx.fxml;
}