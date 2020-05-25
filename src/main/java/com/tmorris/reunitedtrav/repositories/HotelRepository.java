package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.Hotel;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends CrudRepository<Hotel, Integer> {
}
