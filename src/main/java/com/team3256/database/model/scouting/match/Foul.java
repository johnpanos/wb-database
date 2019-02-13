package com.team3256.database.model.scouting.match;

import javax.persistence.Embeddable;

@Embeddable
public class Foul {
    double time;
    String reason;

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
