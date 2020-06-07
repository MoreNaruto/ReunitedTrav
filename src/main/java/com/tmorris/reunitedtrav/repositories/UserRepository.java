package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
