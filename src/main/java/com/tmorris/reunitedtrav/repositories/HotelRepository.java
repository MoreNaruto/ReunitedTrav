package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
}
