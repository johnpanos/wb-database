package com.team3256.database.model.scouting.match;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.team3256.database.model.hr.User;
import com.team3256.database.model.View;
import com.team3256.database.model.scouting.Match;
import com.team3256.database.model.scouting.Team;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity(name="match_data")
@SqlResultSetMapping(
        name="matchComputed",
        classes=@ConstructorResult(
                targetClass=MatchComputed.class,
                columns={
                        @ColumnResult(name="team", type= String.class),
                        @ColumnResult(name="auto_hab_level_average", type=Double.class),
                        @ColumnResult(name="auto_crossed_count", type=Integer.class),
                        @ColumnResult(name="total_cargo_count", type=Integer.class),
                        @ColumnResult(name="total_hatch_count", type=Integer.class),
                        @ColumnResult(name="average_cargo_time", type=Double.class),
                        @ColumnResult(name="average_hatch_time", type=Double.class),
                        @ColumnResult(name="climb_hab_average", type=Double.class),
                        @ColumnResult(name="climb_average_cycle_time", type=Double.class),
                        @ColumnResult(name="climb_average_time", type=Double.class),
                        @ColumnResult(name="climb_support_count", type=Integer.class),
                        @ColumnResult(name="disconnect_count", type=Integer.class),
                        @ColumnResult(name="average_disconnect_duration", type=Double.class),
                        @ColumnResult(name="foul_count", type=Integer.class)
                }
        )
)

@NamedNativeQuery(
        name="match_data.computeMatches",
        resultClass = MatchComputed.class,
        query="SELECT DISTINCT match_data.team_key as team,\n" +
                "\tAVG(match_data.hab_level) as auto_hab_level_average,\n" +
                "\tCOUNT(CASE WHEN match_data.crossed THEN 1 END) as auto_crossed_count,\n" +
                "\t\n" +
                "\tSUM(match_data.cargo_count) as total_cargo_count,\n" +
                "\tSUM(match_data.hatch_count) as total_hatch_count,\n" +
                "\t\n" +
                "\tAVG(match_data.average_cargo) as average_cargo_time,\n" +
                "\tAVG(match_data.average_hatch) as average_hatch_time,\n" +
                "\t\n" +
                "\tAVG(climb.hab_level) as climb_hab_average,\n" +
                "\tAVG(climb.cycle_time) as climb_average_cycle_time,\n" +
                "\tAVG(climb.time) as climb_average_time,\n" +
                "\tCOUNT(CASE WHEN climb.can_support THEN 1 END) as climb_support_count,\n" +
                "\t\n" +
                "\tCOUNT(disconnect.duration) as disconnect_count,\n" +
                "\tAVG(disconnect.duration) as average_disconnect_duration,\n" +
                "\t\n" +
                "\tCOUNT(foul) as foul_count\n" +
                "\t\n" +
                "\tFROM match_data\n" +
                "\tJOIN climb ON match_data.id = climb.match_id\n" +
                "\tJOIN disconnect ON match_data.id = disconnect.match_id\n" +
                "\tJOIN foul ON match_data.id = foul.match_id\n" +
                "\tGROUP BY match_data.team_key;",
        resultSetMapping="matchComputed"
)

@JsonView(View.Public.class)
public class MatchData {
    @Id
    String id;

    public enum Alliance {
        Blue,
        Red
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="scouted_by_id")
    private User scoutedBy;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="match_id")
    @JsonBackReference
    private Match match;

    @NotNull
    @ManyToOne
    @JoinColumn(name="team_key", nullable=false)
    private Team team;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Alliance alliance;

    @NotNull
    @Column(name = "average_hatch")
    @JsonProperty("averageHatch")
    private Double averageHatch;

    @NotNull
    @Column(name = "average_cargo")
    @JsonProperty("averageCargo")
    private Double averageCargo;

    @NotNull
    @Column(name = "hatch_count")
    @JsonProperty("hatchCount")
    private Integer hatchCount;

    @NotNull
    @Column(name = "cargo_count")
    @JsonProperty("cargoCount")
    private Integer cargoCount;

    @NotNull
    @Embedded
    private Auto auto;

    @NotNull
    @ElementCollection
    @CollectionTable(
            name="hatch",
            joinColumns=@JoinColumn(name="match_id")
    )
    private List<Hatch> hatch;

    @NotNull
    @ElementCollection
    @CollectionTable(
            name="cargo",
            joinColumns=@JoinColumn(name="match_id")
    )
    private List<Cargo> cargo;

    @NotNull
    @ElementCollection
    @CollectionTable(
            name="climb",
            joinColumns=@JoinColumn(name="match_id")
    )
    private List<Climb> climb;

    @NotNull
    @ElementCollection
    @CollectionTable(
            name="disconnect",
            joinColumns=@JoinColumn(name="match_id")
    )
    private List<Disconnect> disconnect;

    @NotNull
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

    public User getScoutedBy() {
        return scoutedBy;
    }

    public void setScoutedBy(User scoutedBy) {
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
