package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelerRepository extends JpaRepository<Traveler, Integer> {
}
