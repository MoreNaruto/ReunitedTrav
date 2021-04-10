package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Integer> {
}
