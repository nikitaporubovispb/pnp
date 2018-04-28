package com.testrusoft.pnp.pnp.repository;

import com.testrusoft.pnp.pnp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by andr on 26.04.2018.
 */
@Repository
public interface ClientsRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByNameAndYear(String name, Integer year);
}
