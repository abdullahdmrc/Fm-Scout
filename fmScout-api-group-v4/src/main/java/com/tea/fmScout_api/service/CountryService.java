package com.tea.fmScout_api.service;

import com.tea.fmScout_api.dto.FootballPlayerDto;
import com.tea.fmScout_api.dto.converter.FootballPlayerDtoConverter;
import com.tea.fmScout_api.model.Country;
import com.tea.fmScout_api.repository.CountryRepository;
import com.tea.fmScout_api.repository.FootballPlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final FootballPlayerRepository footballPlayerRepository;
    private final FootballPlayerDtoConverter footballPlayerDtoConverter;


    public CountryService(CountryRepository countryRepository, FootballPlayerRepository footballPlayerRepository, FootballPlayerDtoConverter footballPlayerDtoConverter)
    {
        this.countryRepository = countryRepository;
        this.footballPlayerRepository = footballPlayerRepository;
        this.footballPlayerDtoConverter = footballPlayerDtoConverter;
    }


    public List<Country> getAllCountries() {
        return countryRepository.findAll(Sort.by(Sort.Direction.ASC, "countryId"));
    }


    public Optional<Country> getCountryById(Long countryId) {
        return countryRepository.findById(countryId);
    }

    public List<FootballPlayerDto> findAllPlayers(Long countryId)
    {
        return footballPlayerRepository.findFootballPlayerByCountry_CountryId(countryId).stream()
                .map(footballPlayerDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public Country createCountry(Country country)
    {
        if (countryRepository.existsByCountryName(country.getCountryName())) {
            throw new RuntimeException("The Country is already exists");
        }
        return countryRepository.save(country);
    }

//    @Transactional
//    public Country updateCountry(Long countryId, Country updatedCountry) {
//        Optional<Country> countryOptional = countryRepository.findById(countryId);
//        if (countryOptional.isPresent()) {
//            Country country = countryOptional.get();
//            country.setCountryName(updatedCountry.getCountryName());
//            country.setFederation(updatedCountry.getFederation());
//            return countryRepository.save(country);
//        } else {
//            throw new RuntimeException("Country not found with id: " + countryId);
//        }
//    }
//
//    @Transactional
//    public void deleteCountry(Long countryId) {
//        countryRepository.deleteById(countryId);
//    }


}

