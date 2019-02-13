package com.team3256.database.controller.scouting;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.scouting.Regional;
import com.team3256.database.model.scouting.RegionalRepository;
import com.team3256.database.model.scouting.Team;
import com.team3256.database.model.scouting.TeamRepository;
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
public class RegionalController {
    @Autowired
    private RegionalRepository regionalRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ScoutingService scoutingService;

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

    @GetMapping("/{key}/teams")
    public List<Team> getRegionalTeams(@PathVariable("key") String key) {
        return regionalRepository.findById(key).map(Regional::getTeams).orElseThrow(DatabaseNotFoundException::new);
    }

}