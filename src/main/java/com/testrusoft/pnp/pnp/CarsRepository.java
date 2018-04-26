package com.testrusoft.pnp.pnp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by root on 25.04.2018.
 */
@Repository
public interface CarsRepository extends JpaRepository<Cars, Long> {
    ;
}