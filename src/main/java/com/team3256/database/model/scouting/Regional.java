package com.team3256.database.model.scouting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "regional")
public class Regional {
    @Id
    private String key;

    private String name;

    @Column(name = "short_name")
    private String shortName;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "regional", cascade = {CascadeType.ALL})
    private List<Match> matches;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "regional_teams",
            joinColumns = { @JoinColumn(name = "regional_key") },
            inverseJoinColumns = { @JoinColumn(name = "team_key") })
    private List<Team> teams;

    private String address;

    private String city;

    @Column(name = "location_name")
    private String locationName;

    private String timezone;

    @Column(name = "start_date")
    private Date startDate;

    private int year;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return String.format(
                "key: %s\n" +
                "name: %s\n" +
                "shortName: %s\n" +
                "address: %s\n" +
                "city: %s\n" +
                "locationName: %s",
                key,
                name,
                shortName,
                address,
                city,
                locationName
        );
    }
}
