package org.bunuelolovers.sgmms.sgmms_proyect.model.incident;

import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;

public abstract class Incident implements Comparable<Incident>{
    private String id;
    private int gravity;

    private Vertex position;
    private IncidentType type;

    private StateIncident stateIncident;

    public Incident(String id, int gravity, StateIncident stateIncident, Vertex position,IncidentType type ) {
        this.id = id;
        this.gravity = gravity;
        this.position = position;
        this.stateIncident = stateIncident;
        this.type = type;
     }


    public IncidentType getType() {
        return type;
    }

    public void setType(IncidentType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGravity() {
        return gravity;
    }

    public Vertex getPosition() {
        return position;
    }

    public StateIncident getStateIncident() {
        return stateIncident;
    }

    public void setStateIncident(StateIncident stateIncident) {
        this.stateIncident = stateIncident;
    }

    @Override
    public int compareTo(Incident o) {
        if(this.gravity != o.getGravity()){
            return Integer.compare(this.gravity, o.getGravity());
        }else{
            return this.id.compareTo(o.getId());
        }
    }
}
