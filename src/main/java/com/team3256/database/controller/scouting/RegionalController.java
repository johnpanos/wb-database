package com.team3256.database.controller.scouting;

import com.fasterxml.jackson.annotation.JsonView;
import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.error.RegionalNotFoundException;
import com.team3256.database.model.View;
import com.team3256.database.model.scouting.*;
import com.team3256.database.service.ScoutingService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/scouting/regional")
@JsonView(View.Public.class)
public class RegionalController {
    @Autowired
    private RegionalRepository regionalRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ScoutingService scoutingService;

    @GetMapping("/")
    public Iterable<Regional> getAllRegionals() {
        return regionalRepository.findAll();
    }

    @Transactional
    @GetMapping("/{key}")
    public Regional getRegional(@PathVariable("key") String key) {
        Regional regional = regionalRepository.findById(key).orElseGet(() -> {
            // Fetch regional if not in database
            Regional tempRegional = scoutingService.fetchRegionalBA(key);
            List<Team> tempTeams = scoutingService.fetchTeamsForRegional(tempRegional);
            tempRegional.setTeams(new ArrayList<>());
            tempRegional.setMatches(new ArrayList<>());

            for (Team team : tempTeams) {
                System.out.println(
                        String.format(
                                "Key: %s, City: %s, Nickname: %s, Country: %s, State/Prov: %s, TeamNum: %s",
                                team.getKey(),
                                team.getCity(),
                                team.getNickname(),
                                team.getCountry(),
                                team.getStateProv(),
                                team.getTeamNumber()

                        )
                );

                Team dbTeam = teamRepository.findById(team.getKey()).orElseGet(() -> teamRepository.save(team));

                tempRegional.getTeams().add(dbTeam);
            }

            return regionalRepository.save(tempRegional);
        });

        return regional;
    }

    @GetMapping("/{key}/matches")
    public List<Match> getMatchesForRegional(@PathVariable("key") String key) {
        return regionalRepository.findById(key).orElseThrow(DatabaseNotFoundException::new).getMatches();
    }

    @GetMapping("/{key}/teams")
    public List<Team> getRegionalTeams(@PathVariable("key") String key) {
        return regionalRepository.findById(key).map(Regional::getTeams).orElseThrow(RegionalNotFoundException::new);
    }

}
