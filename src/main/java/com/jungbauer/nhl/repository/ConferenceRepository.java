package com.jungbauer.nhl.repository;

import com.jungbauer.nhl.domain.Conference;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConferenceRepository extends CrudRepository<Conference, Integer> {
    Optional<Conference> findByName(String name);
    Optional<Conference> findByNameAndAbbrev(String name, String abbrev);
}
