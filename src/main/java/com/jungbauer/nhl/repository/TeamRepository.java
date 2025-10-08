package com.jungbauer.nhl.repository;

import com.jungbauer.nhl.domain.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    Optional<Team> findByNhlIdAndAbbrev(Integer nhlId, String abbrev);
    Optional<Team> findByAbbrev(String abbrev);
}
