package com.team3256.database.controller.scouting;

import com.team3256.database.error.DatabaseAlreadyExistsException;
import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.hr.User;
import com.team3256.database.model.hr.UserRepository;
import com.team3256.database.model.scouting.*;
import com.team3256.database.model.scouting.match.MatchData;
import com.team3256.database.model.scouting.match.MatchDataRepository;
import com.team3256.database.service.ScoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/scouting/match")
public class MatchController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegionalRepository regionalRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ScoutingService scoutingService;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchDataRepository matchDataRepository;

    @Transactional
    @PostMapping("/")
    public MatchData addMatch(@AuthenticationPrincipal Principal principal, @RequestBody MatchDTO match) {
        System.out.println(match.getRegionalKey() + "-" + match.getMatchNumber() + "-frc" + match.getTeamKey());

        User user = userRepository.findByEmail(principal.getName()).orElseThrow(DatabaseNotFoundException::new);

        if (matchDataRepository.existsById(match.getRegionalKey() + "-" + match.getMatchNumber() + "-frc" + match.getTeamKey())) {
            System.out.println("ALREADY EXISTS");
            throw new DatabaseAlreadyExistsException("Match Data for team already exists");
        } else {
            match.getMatchData().setTeam(teamRepository.findById("frc" + match.getTeamKey()).orElseThrow(DatabaseNotFoundException::new));
            match.getMatchData().setAlliance(match.getAlliance());
            match.getMatchData().setScoutedBy(user.getFirstName() + " " + user.getLastName());

            MatchData dbMatch = scoutingService.addMatchDataForRegional(match.getRegionalKey(), match.getMatchNumber(), match.getMatchData());

            return dbMatch;
        }
    }

    @DeleteMapping("/{matchKey}")
    public String deleteMatch(@PathVariable("matchKey") String matchKey) {
        matchRepository.delete(matchRepository.findById(matchKey).orElseThrow(DatabaseNotFoundException::new));
        return matchKey;
    }

    @GetMapping("/{matchKey}")
    public Match getMatch(@PathVariable("matchKey") String matchKey) {
        return matchRepository.findById(matchKey).orElseThrow(DatabaseNotFoundException::new);
    }

    @GetMapping("/team/{teamKey}")
    public List<MatchData> getMatchDataForTeam(@PathVariable("teamKey") String teamKey) {
        return matchDataRepository.findByTeam(teamRepository.findById(teamKey).orElseThrow(DatabaseNotFoundException::new)).orElseThrow(DatabaseNotFoundException::new);
    }
}
