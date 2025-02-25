package com.tea.fmScout_api.repository;

import com.tea.fmScout_api.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("SELECT c FROM Country c WHERE c.countryName = :countryName")
    Optional<Country> findByCountryName(String countryName);

    boolean existsByCountryName(String countryName);
}
