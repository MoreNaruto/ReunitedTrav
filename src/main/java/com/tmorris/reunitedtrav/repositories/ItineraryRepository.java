package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.Itinerary;
import org.springframework.data.repository.CrudRepository;

public interface ItineraryRepository extends CrudRepository<Itinerary, Integer> {
}
