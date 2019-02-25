package com.team3256.database.model.scouting.match;

import com.fasterxml.jackson.annotation.JsonView;
import com.team3256.database.model.View;

import javax.persistence.Embeddable;

@Embeddable
@JsonView(View.Public.class)
public class Hatch {
    String pickup;
    String dropOff;
    double pickupTime;
    double cycleTime;

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropOff() {
        return dropOff;
    }

    public void setDropOff(String dropOff) {
        this.dropOff = dropOff;
    }

    public double getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(double pickupTime) {
        this.pickupTime = pickupTime;
    }

    public double getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(double cycleTime) {
        this.cycleTime = cycleTime;
    }
}
