package com.team3256.database.model.scouting;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.team3256.database.model.View;
import com.team3256.database.model.scouting.match.MatchData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "match")
@JsonView(View.Public.class)
public class Match {
    public Match() {}

    public Match(Regional regional, int matchNumber) {
        this.setId(regional.getKey() + "-" + matchNumber);
        this.setRegional(regional);
        this.setMatchData(new ArrayList<>());
    }

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="regional_key")
    @JsonBackReference
    private Regional regional;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<MatchData> matchData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Regional getRegional() {
        return this.regional;
    }

    public void setRegional(Regional regional) {
        this.regional = regional;
    }

    public List<MatchData> getMatchData() {
        return this.matchData;
    }

    public void setMatchData(List<MatchData> matchData) {
        this.matchData = matchData;
    }
}
