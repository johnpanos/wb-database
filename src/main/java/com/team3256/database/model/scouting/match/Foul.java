package com.team3256.database.model.scouting.match;

import com.fasterxml.jackson.annotation.JsonView;
import com.team3256.database.model.View;

import javax.persistence.Embeddable;

@Embeddable
@JsonView(View.Public.class)
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
