package com.tea.fmScout_api.controller;

import com.tea.fmScout_api.dto.FootballPlayerDto;
import com.tea.fmScout_api.model.Country;
import com.tea.fmScout_api.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService)
    {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        return countryService.getCountryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Country createCountry(@RequestBody Country country) {
        return countryService.createCountry(country);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country updatedCountry) {
//        try {
//            return ResponseEntity.ok(countryService.updateCountry(id, updatedCountry));
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
//        countryService.deleteCountry(id);
//        return ResponseEntity.noContent().build();
//    }

    @GetMapping("/{id}/players")
    public ResponseEntity<List<FootballPlayerDto>> getAllPlayers(@PathVariable Long id)
    {
        List<FootballPlayerDto> playerDtoList = countryService.findAllPlayers(id);
        return ResponseEntity.ok(playerDtoList);
    }
}
