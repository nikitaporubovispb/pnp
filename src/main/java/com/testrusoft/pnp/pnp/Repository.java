package com.testrusoft.pnp.pnp;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by root on 25.04.2018.
 */
@RepositoryRestResource(collectionResourceRel = "cars", path = "cars")
public interface Repository extends PagingAndSortingRepository<Cars, Long> {
    List<Cars> findBybrandName(@Param("brandName") String brandName);
}