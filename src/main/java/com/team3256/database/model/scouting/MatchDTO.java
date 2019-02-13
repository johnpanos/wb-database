package com.team3256.database.model.scouting;

import com.team3256.database.model.scouting.match.MatchData;

public class MatchDTO {
    int teamKey;
    int matchNumber;
    String regionalKey;
    MatchData.Alliance alliance;
    MatchData matchData;

    public int getTeamKey() {
        return teamKey;
    }

    public void setTeamKey(int teamKey) {
        this.teamKey = teamKey;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getRegionalKey() {
        return regionalKey;
    }

    public void setRegionalKey(String regionalKey) {
        this.regionalKey = regionalKey;
    }

    public MatchData.Alliance getAlliance() {
        return alliance;
    }

    public void setAlliance(MatchData.Alliance alliance) {
        this.alliance = alliance;
    }

    public MatchData getMatchData() {
        return matchData;
    }

    public void setMatchData(MatchData matchData) {
        this.matchData = matchData;
    }
}
