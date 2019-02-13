package com.team3256.database.model.scouting.match;

import javax.persistence.Embeddable;

@Embeddable
public class Disconnect {
    double startTime;
    double duration;

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
