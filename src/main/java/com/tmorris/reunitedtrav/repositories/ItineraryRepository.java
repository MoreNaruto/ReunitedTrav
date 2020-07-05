package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
}
