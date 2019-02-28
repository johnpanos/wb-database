package com.team3256.database.model.scouting.match;

import com.team3256.database.model.hr.User;
import com.team3256.database.model.scouting.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MatchDataRepository extends JpaRepository<MatchData, String> {
    Optional<List<MatchData>> findByTeam(Team team);
    Optional<List<MatchData>> findByScoutedBy(User user);

    @Query(
        name="match_data.computeMatches",
        nativeQuery = true
    )
    List<MatchComputed> computeMatches();
}
