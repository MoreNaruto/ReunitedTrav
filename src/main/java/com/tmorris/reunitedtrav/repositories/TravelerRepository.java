package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.Traveler;
import org.springframework.data.repository.CrudRepository;

public interface TravelerRepository extends CrudRepository<Traveler, Integer> {
}
