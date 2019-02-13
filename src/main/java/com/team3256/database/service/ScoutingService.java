package com.team3256.database.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.scouting.*;
import com.team3256.database.model.scouting.match.MatchData;
import com.team3256.database.model.scouting.match.MatchDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScoutingService {
    private final String apiUrl = "https://thebluealliance.com/api/v3";

    private RestTemplate rest;
    private HttpHeaders headers;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RegionalRepository regionalRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchDataRepository matchDataRepository;

    @PostConstruct
    public void init() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
        String apiKey = "dRcnTWXfVTbTL87pNEWHSMQM70uSm9b4YC2C5yI5NJLwDZFNI3PiTJWHnpMTMqOE";
        headers.add("X-TBA-Auth-Key", apiKey);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
    }

    public List<Regional> fetchRegionalsBA(List<String> regionalKeys) {
        return null;
    }

    public Regional fetchRegionalBA(String regionalKey) {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = null;

        try {
            responseEntity = rest.exchange(apiUrl + "/event/" + regionalKey, HttpMethod.GET, requestEntity, String.class);
            RegionalBA regionalBA = mapper.readValue(responseEntity.getBody(), RegionalBA.class);
            System.out.println(String.format("Got %s regional, which starts %s", regionalBA.name, regionalBA.start_date.toString()));
            Regional regional = new Regional();
            regional.setKey(regionalBA.key);
            regional.setName(regionalBA.name);
            regional.setShortName(regionalBA.short_name);
            regional.setAddress(regionalBA.address);
            regional.setCity(regionalBA.city);
            regional.setLocationName(regionalBA.location_name);
            regional.setTimezone(regionalBA.timezone);
            regional.setStartDate(regionalBA.start_date);
            regional.setYear(regionalBA.year);
            return regional;
        } catch (IOException e) {
            System.out.println("This ain't it chief.");
            e.printStackTrace();
        }
        return null;
    }

    public List<Team> fetchTeamsForRegional(Regional regional) {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = null;
        List<Team> teams = new ArrayList<Team>();

        try {
            responseEntity = rest.exchange(apiUrl + "/event/" + regional.getKey() + "/teams/simple", HttpMethod.GET, requestEntity, String.class);

            teams = mapper.readValue(responseEntity.getBody(), new TypeReference<List<Team>>(){});
        } catch(IOException e) {
            System.out.println(e);
        }

        return teams;
    }

    public Match createMatchForRegional(String regionalKey, Match match) {
        return regionalRepository.findById(regionalKey).map(regional -> {
            match.setId(regionalKey + "-" + match.getId());
            regional.getMatches().add(match);
            Match newMatch = matchRepository.save(match);
            regionalRepository.save(regional);
            return newMatch;
        }).orElseThrow(DatabaseNotFoundException::new);
    }

    public MatchData addMatchDataForRegional(String regionalKey, int matchNumber, MatchData matchData) {
        Regional regional = regionalRepository.findById(regionalKey).orElseThrow(DatabaseNotFoundException::new);
        Match match = matchRepository.findById(regionalKey + "-" + matchNumber).orElseGet(() -> new Match(regional, matchNumber));

        matchData.setMatch(match);
        matchData.setId(regionalKey + "-" + matchNumber + "-" + matchData.getTeam().getKey());

        match.getMatchData().add(matchData);
        matchRepository.save(match);

        regional.getMatches().add(match);
        regionalRepository.save(regional);

        return matchData;
    }
}