package org.bunuelolovers.sgmms.sgmms_proyect.model.map;

import org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle.Vehicle;

import java.util.List;

public class Player {
    private String name;
    private Genre genre;

    private int score;

    public enum Genre {
        FEMALE,
        MALE
    }


    private List<Vehicle> assignedVehicles;

    public Player(String name, String genre) {
        this.name = name;
        switch (genre){
            case "Female":
                this.genre = Genre.FEMALE;
                break;
            case "Male":
                this.genre = Genre.MALE;
                break;
        }

        score = 0;
    }


    public String getName() {
        return name;
    }

}
