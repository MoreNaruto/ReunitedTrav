package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findByUuid(UUID uuid);
}
