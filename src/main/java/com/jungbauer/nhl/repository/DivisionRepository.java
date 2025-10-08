package com.jungbauer.nhl.repository;

import com.jungbauer.nhl.domain.Division;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DivisionRepository extends CrudRepository<Division, Integer> {
    Optional<Division> findByName(String name);
    Optional<Division> findByNameAndAbbrev(String name, String abbrev);
}
