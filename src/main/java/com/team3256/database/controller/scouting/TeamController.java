package com.team3256.database.controller.scouting;

import com.fasterxml.jackson.annotation.JsonView;
import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.error.MatchDataNotFoundException;
import com.team3256.database.error.TeamNotFoundException;
import com.team3256.database.model.View;
import com.team3256.database.model.scouting.Team;
import com.team3256.database.model.scouting.TeamRepository;
import com.team3256.database.model.scouting.match.MatchData;
import com.team3256.database.model.scouting.match.MatchDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scouting/team")
public class TeamController {
    @Autowired
    private MatchDataRepository matchDataRepository;

    @Autowired
    private TeamRepository teamRepository;

    @JsonView(View.Public.class)
    @GetMapping("/{teamKey}/matches")
    public List<MatchData> getAllMatchDataForTeam(@PathVariable("teamKey") String teamKey) {
        Team team = teamRepository.findById(teamKey).orElseThrow(TeamNotFoundException::new);
        System.out.println(team.getCity());
        return matchDataRepository.findByTeam(teamRepository.findById(teamKey).orElseThrow(TeamNotFoundException::new)).orElseThrow(MatchDataNotFoundException::new);
    }

    @JsonView(View.Public.class)
    @GetMapping("/{teamKey}/{regionalMatchKey}")
    public MatchData getMatchDataForTeam(@PathVariable("teamKey") String teamKey, @PathVariable("regionalMatchKey") String regionalMatchKey) {
        return matchDataRepository.findById(regionalMatchKey + "-" + teamKey).orElseThrow(MatchDataNotFoundException::new);
    }
}
