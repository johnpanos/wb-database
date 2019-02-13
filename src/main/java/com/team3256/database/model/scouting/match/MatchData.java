package com.team3256.database.model.scouting.match;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.team3256.database.model.scouting.Match;
import com.team3256.database.model.scouting.Team;

import javax.persistence.*;
import java.util.List;

@Entity(name="match_data")
public class MatchData {
    @Id
    String id;

    public enum Alliance {
        Blue,
        Red
    }

    private String scoutedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="match_id")
    @JsonBackReference
    private Match match;

    @ManyToOne
    @JoinColumn(name="team_key", nullable=false)
    private Team team;

    @Enumerated(EnumType.STRING)
    private Alliance alliance;

    @Column(name = "average_hatch")
    @JsonProperty("averageHatch")
    private Double averageHatch;

    @Column(name = "average_cargo")
    @JsonProperty("averageCargo")
    private Double averageCargo;

    @Column(name = "hatch_count")
    @JsonProperty("hatchCount")
    private Integer hatchCount;

    @Column(name = "cargo_count")
    @JsonProperty("cargoCount")
    private Integer cargoCount;

    @Embedded
    private Auto auto;

    @ElementCollection
    @CollectionTable(
            name="hatch",
            joinColumns=@JoinColumn(name="match_id")
    )
    private List<Hatch> hatch;

    @ElementCollection
    @CollectionTable(
            name="cargo",
            joinColumns=@JoinColumn(name="match_id")
    )
    private List<Cargo> cargo;

    @ElementCollection
    @CollectionTable(
            name="climb",
            joinColumns=@JoinColumn(name="match_id")
    )
    private List<Climb> climb;

    @ElementCollection
    @CollectionTable(
            name="disconnect",
            joinColumns=@JoinColumn(name="match_id")
    )
    private List<Disconnect> disconnect;

    @ElementCollection
    @CollectionTable(
            name="foul",
            joinColumns=@JoinColumn(name="match_id")
    )
    private List<Foul> foul;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScoutedBy() {
        return scoutedBy;
    }

    public void setScoutedBy(String scoutedBy) {
        this.scoutedBy = scoutedBy;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
    }

    public Double getAverageHatch() {
        return averageHatch;
    }

    public void setAverageHatch(Double averageHatch) {
        this.averageHatch = averageHatch;
    }

    public Double getAverageCargo() {
        return averageCargo;
    }

    public void setAverageCargo(Double averageCargo) {
        this.averageCargo = averageCargo;
    }

    public Integer getHatchCount() {
        return hatchCount;
    }

    public void setHatchCount(Integer hatchCount) {
        this.hatchCount = hatchCount;
    }

    public Integer getCargoCount() {
        return cargoCount;
    }

    public void setCargoCount(Integer cargoCount) {
        this.cargoCount = cargoCount;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public List<Hatch> getHatch() {
        return hatch;
    }

    public void setHatch(List<Hatch> hatch) {
        this.hatch = hatch;
    }

    public List<Cargo> getCargo() {
        return cargo;
    }

    public void setCargo(List<Cargo> cargo) {
        this.cargo = cargo;
    }

    public List<Climb> getClimb() {
        return climb;
    }

    public void setClimb(List<Climb> climb) {
        this.climb = climb;
    }

    public List<Disconnect> getDisconnect() {
        return disconnect;
    }

    public void setDisconnect(List<Disconnect> disconnect) {
        this.disconnect = disconnect;
    }

    public List<Foul> getFoul() {
        return foul;
    }

    public void setFoul(List<Foul> foul) {
        this.foul = foul;
    }
}
