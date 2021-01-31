package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(value = "SELECT * FROM Role r WHERE r.name = :name",
            nativeQuery = true)
    Role findRoleByName(@Param("name") String name);
}