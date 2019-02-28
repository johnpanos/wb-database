package com.team3256.database.model.scouting.match;

public class MatchComputed {
    String team;
    double auto_hab_level_average;
    int auto_crossed_count;
    int total_cargo_count;
    int total_hatch_count;
    double average_cargo_time;
    double average_hatch_time;
    double climb_hab_average;
    double climb_average_cycle_time;
    double climb_average_time;
    int climb_support_count;
    int disconnect_count;
    double average_disconnect_duration;
    int foul_count;

    public MatchComputed(String team, double auto_hab_level_average, int auto_crossed_count, int total_cargo_count, int total_hatch_count, double average_cargo_time, double average_hatch_time, double climb_hab_average, double climb_average_cycle_time, double climb_average_time, int climb_support_count, int disconnect_count, double average_disconnect_duration, int foul_count) {
        this.team = team;
        this.auto_hab_level_average = auto_hab_level_average;
        this.auto_crossed_count = auto_crossed_count;
        this.total_cargo_count = total_cargo_count;
        this.total_hatch_count = total_hatch_count;
        this.average_cargo_time = average_cargo_time;
        this.average_hatch_time = average_hatch_time;
        this.climb_hab_average = climb_hab_average;
        this.climb_average_cycle_time = climb_average_cycle_time;
        this.climb_average_time = climb_average_time;
        this.climb_support_count = climb_support_count;
        this.disconnect_count = disconnect_count;
        this.average_disconnect_duration = average_disconnect_duration;
        this.foul_count = foul_count;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public double getAuto_hab_level_average() {
        return auto_hab_level_average;
    }

    public void setAuto_hab_level_average(double auto_hab_level_average) {
        this.auto_hab_level_average = auto_hab_level_average;
    }

    public int getAuto_crossed_count() {
        return auto_crossed_count;
    }

    public void setAuto_crossed_count(int auto_crossed_count) {
        this.auto_crossed_count = auto_crossed_count;
    }

    public int getTotal_cargo_count() {
        return total_cargo_count;
    }

    public void setTotal_cargo_count(int total_cargo_count) {
        this.total_cargo_count = total_cargo_count;
    }

    public int getTotal_hatch_count() {
        return total_hatch_count;
    }

    public void setTotal_hatch_count(int total_hatch_count) {
        this.total_hatch_count = total_hatch_count;
    }

    public double getAverage_cargo_time() {
        return average_cargo_time;
    }

    public void setAverage_cargo_time(double average_cargo_time) {
        this.average_cargo_time = average_cargo_time;
    }

    public double getAverage_hatch_time() {
        return average_hatch_time;
    }

    public void setAverage_hatch_time(double average_hatch_time) {
        this.average_hatch_time = average_hatch_time;
    }

    public double getClimb_hab_average() {
        return climb_hab_average;
    }

    public void setClimb_hab_average(double climb_hab_average) {
        this.climb_hab_average = climb_hab_average;
    }

    public double getClimb_average_cycle_time() {
        return climb_average_cycle_time;
    }

    public void setClimb_average_cycle_time(double climb_average_cycle_time) {
        this.climb_average_cycle_time = climb_average_cycle_time;
    }

    public double getClimb_average_time() {
        return climb_average_time;
    }

    public void setClimb_average_time(double climb_average_time) {
        this.climb_average_time = climb_average_time;
    }

    public int getClimb_support_count() {
        return climb_support_count;
    }

    public void setClimb_support_count(int climb_support_count) {
        this.climb_support_count = climb_support_count;
    }

    public int getDisconnect_count() {
        return disconnect_count;
    }

    public void setDisconnect_count(int disconnect_count) {
        this.disconnect_count = disconnect_count;
    }

    public double getAverage_disconnect_duration() {
        return average_disconnect_duration;
    }

    public void setAverage_disconnect_duration(double average_disconnect_duration) {
        this.average_disconnect_duration = average_disconnect_duration;
    }

    public int getFoul_count() {
        return foul_count;
    }

    public void setFoul_count(int foul_count) {
        this.foul_count = foul_count;
    }
}
