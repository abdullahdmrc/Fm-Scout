package com.tea.fmScout_api.dto.converter;

import com.tea.fmScout_api.dto.LeagueDto;
import com.tea.fmScout_api.model.League;
import com.tea.fmScout_api.repository.CountryRepository;
import org.springframework.stereotype.Component;

@Component
public class LeagueDtoConverter {
    private final CountryRepository countryRepository;

    public LeagueDtoConverter(CountryRepository countryRepository)
    {
        this.countryRepository = countryRepository;
    }
    public LeagueDto convert(League from)
    {
        String countryName = (from.getCountry() != null) ? from.getCountry().getCountryName() : "Unknown Country";

        return new LeagueDto(from.getLeagueId(), from.getLeagueName(), from.getLeagueTier(), countryName);
    }

}
