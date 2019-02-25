package com.team3256.database.controller.scouting;

import com.fasterxml.jackson.annotation.JsonView;
import com.team3256.database.error.*;
import com.team3256.database.model.hr.User;
import com.team3256.database.model.hr.UserRepository;
import com.team3256.database.model.View;
import com.team3256.database.model.scouting.*;
import com.team3256.database.model.scouting.match.MatchData;
import com.team3256.database.model.scouting.match.MatchDataRepository;
import com.team3256.database.service.ScoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.TransactionScoped;
import java.security.Principal;

@RestController
@RequestMapping("/api/scouting/match")
@JsonView(View.Public.class)
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

    @PostMapping("/")
    @JsonView(View.Public.class)
    public MatchData addMatch(@AuthenticationPrincipal Principal principal, @RequestBody MatchDTO match) {
        System.out.println(match.getRegionalKey() + "-" + match.getMatchNumber() + "-frc" + match.getTeamKey());

        User user = userRepository.findByEmail(principal.getName()).orElseThrow(DatabaseNotFoundException::new);

        if (matchDataRepository.existsById(match.getRegionalKey() + "-" + match.getMatchNumber() + "-frc" + match.getTeamKey())) {
            throw new DatabaseAlreadyExistsException("Match Data for team already exists");
        } else {
            match.getMatchData().setTeam(teamRepository.findById("frc" + match.getTeamKey()).orElseThrow(TeamNotFoundException::new));
            match.getMatchData().setAlliance(match.getAlliance());
            match.getMatchData().setScoutedBy(user);

            MatchData dbMatch = scoutingService.addMatchDataForRegional(match.getRegionalKey(), match.getMatchNumber(), match.getMatchData());

            return dbMatch;
        }
    }

    @DeleteMapping("/{matchKey}")
    public String deleteMatch(@PathVariable("matchKey") String matchKey) {
        matchRepository.delete(matchRepository.findById(matchKey).orElseThrow(MatchNotFoundException::new));
        return matchKey;
    }

    @GetMapping("/{matchKey}")
    @JsonView(View.Public.class)
    public Match getMatch(@PathVariable("matchKey") String matchKey) {
        return matchRepository.findById(matchKey).orElseThrow(MatchNotFoundException::new);
    }

    @GetMapping("/user/{userId}")
    @JsonView(View.Public.class)
    public Iterable<MatchData> getAllMatchesForUser(@PathVariable int userId) {
        User user = userRepository.findById(userId).orElseThrow(DatabaseNotFoundException::new);
        return matchDataRepository.findByScoutedBy(user).orElseThrow(MatchDataNotFoundException::new);
    }

}
