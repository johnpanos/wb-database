package com.team3256.database.model.scouting.match;

import com.fasterxml.jackson.annotation.JsonView;
import com.team3256.database.model.View;

import javax.persistence.Embeddable;

@Embeddable
@JsonView(View.Public.class)
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
