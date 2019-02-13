package com.team3256.database.model.scouting.match;

import javax.persistence.Embeddable;

@Embeddable
public class Auto {
    int habLevel;
    double time;
    boolean crossed;

    public int getHabLevel() {
        return habLevel;
    }

    public void setHabLevel(int habLevel) {
        this.habLevel = habLevel;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public boolean isCrossed() {
        return crossed;
    }

    public void setCrossed(boolean crossed) {
        this.crossed = crossed;
    }
}
