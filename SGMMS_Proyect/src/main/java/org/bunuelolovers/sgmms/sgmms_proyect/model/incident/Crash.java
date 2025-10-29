package org.bunuelolovers.sgmms.sgmms_proyect.model.incident;

import org.bunuelolovers.sgmms.sgmms_proyect.graph.Vertex;
import org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle.Ambulance;
import org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle.NpcVehicle;
import org.bunuelolovers.sgmms.sgmms_proyect.model.vehicle.Vehicle;

public class Crash extends Incident {

    private Vehicle v1,v2, ambulance;

    public Crash(String id, int gravity, StateIncident stateIncident, Vertex position,
                 Vehicle v1, Vehicle v2) {
        super(id, gravity, stateIncident, position, IncidentType.CRASH);
        this.v1 = v1;
        this.v2 = v2;
        this.ambulance = null;
    }

    public Vehicle getV1() {
        return v1;
    }

    public Vehicle getV2() {
        return v2;
    }

    public Ambulance getAmbulance() {
        return (Ambulance) ambulance;
    }

    public void setAmbulance(Vehicle ambulance) {
        this.ambulance = ambulance;
    }
}
