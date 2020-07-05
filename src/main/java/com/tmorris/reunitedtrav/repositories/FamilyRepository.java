package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Integer> {
}
