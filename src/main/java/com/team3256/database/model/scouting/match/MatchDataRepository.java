package com.team3256.database.model.scouting.match;

import com.team3256.database.model.scouting.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MatchDataRepository extends CrudRepository<MatchData, String> {
    Optional<List<MatchData>> findByTeam(Team team);
}
