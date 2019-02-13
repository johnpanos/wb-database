package com.team3256.database.model.scouting.match;

import javax.persistence.Embeddable;

@Embeddable
public class Climb {
    double time;
    double cycleTime;
    int habLevel;
    boolean canSupport;
    boolean dropped;

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(double cycleTime) {
        this.cycleTime = cycleTime;
    }

    public int getHabLevel() {
        return habLevel;
    }

    public void setHabLevel(int habLevel) {
        this.habLevel = habLevel;
    }

    public boolean isCanSupport() {
        return canSupport;
    }

    public void setCanSupport(boolean canSupport) {
        this.canSupport = canSupport;
    }

    public boolean isDropped() {
        return dropped;
    }

    public void setDropped(boolean dropped) {
        this.dropped = dropped;
    }
}
