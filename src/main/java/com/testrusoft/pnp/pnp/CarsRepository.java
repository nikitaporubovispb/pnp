package com.testrusoft.pnp.pnp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by root on 25.04.2018.
 */
@Repository
public interface CarsRepository extends JpaRepository<Car, Long> {
    ;
}