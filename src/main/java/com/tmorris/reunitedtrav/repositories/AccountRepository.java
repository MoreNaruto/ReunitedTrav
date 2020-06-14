package com.tmorris.reunitedtrav.repositories;

import com.tmorris.reunitedtrav.models.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AccountRepository extends CrudRepository<Account, Integer> {
    @Query(value = "SELECT * FROM Account a WHERE a.username = :username",
            nativeQuery = true)
    Account findAccountByUsername(@Param("username") String userName);
}
