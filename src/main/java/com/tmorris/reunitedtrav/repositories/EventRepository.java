package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
}
