package com.tea.fmScout_api.service;

import com.tea.fmScout_api.dto.ClubDto;
import com.tea.fmScout_api.dto.FootballPlayerDto;
import com.tea.fmScout_api.dto.LeagueDto;
import com.tea.fmScout_api.dto.converter.ClubDtoConverter;
import com.tea.fmScout_api.dto.converter.LeagueDtoConverter;
import com.tea.fmScout_api.dto.request.AddLeagueRequest;
import com.tea.fmScout_api.model.Club;
import com.tea.fmScout_api.model.Country;
import com.tea.fmScout_api.model.FootballPlayer;
import com.tea.fmScout_api.model.League;
import com.tea.fmScout_api.repository.ClubRepository;
import com.tea.fmScout_api.repository.CountryRepository;
import com.tea.fmScout_api.repository.LeagueRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeagueService {

    private final CountryRepository countryRepository;
    private final ClubDtoConverter clubDtoConverter;
    private final LeagueRepository leagueRepository;
    private final LeagueDtoConverter leagueDtoConverter;
    private final ClubRepository clubRepository;

    public LeagueService(CountryRepository countryRepository, ClubDtoConverter clubDtoConverter, LeagueRepository leagueRepository, LeagueDtoConverter leagueDtoConverter, ClubRepository clubRepository)
    {
        this.countryRepository = countryRepository;
        this.clubDtoConverter = clubDtoConverter;
        this.leagueRepository = leagueRepository;
        this.leagueDtoConverter = leagueDtoConverter;
        this.clubRepository = clubRepository;
    }

    public List<LeagueDto> getAllLeagues()
    {
        return leagueRepository.findAll(Sort.by(Sort.Direction.ASC, "leagueId"))
                .stream()
                .map(leagueDtoConverter::convert)
                .collect(Collectors.toList());
    }


    public Optional<LeagueDto> getLeagueById(Long leagueId)
    {
        return leagueRepository.findById(leagueId)
                .map(leagueDtoConverter::convert);
    }

    @Transactional
    public LeagueDto createLeague(AddLeagueRequest createRequest)
    {
        if (leagueRepository.existsByLeagueName(createRequest.getLeagueName())) {
            throw new RuntimeException("The league is already exists");
        }
        League league = new League();
        league.setLeagueName(createRequest.getLeagueName());
        league.setLeagueTier(createRequest.getLeagueTier());

        Country country = countryRepository.findByCountryName(createRequest.getCountryName())
                        .orElseThrow(() -> new RuntimeException("Country not found"));
        league.setCountry(country);

        leagueRepository.save(league);

        return leagueDtoConverter.convert(league);
    }

    @Transactional
    public LeagueDto updateLeague(Long leagueId, AddLeagueRequest updateRequest)
    {

        League existingLeague = leagueRepository.findById(leagueId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        existingLeague.setLeagueName(updateRequest.getLeagueName());
        existingLeague.setLeagueTier(updateRequest.getLeagueTier());

        Country country = countryRepository.findByCountryName(updateRequest.getCountryName())
                .orElseThrow(() -> new RuntimeException("Country not found"));
        existingLeague.setCountry(country);

        existingLeague = leagueRepository.save(existingLeague);

        return leagueDtoConverter.convert(existingLeague);
    }
//
//    @Transactional
//    public void deleteLeague(Long leagueId) {
//        leagueRepository.deleteById(leagueId);
//    }

    public List<ClubDto> findAllClubs(Long leagueId)
    {
        return clubRepository.findClubsByLeague_LeagueId(leagueId).stream()
                .map(clubDtoConverter::convert)
                .collect(Collectors.toList());
    }
}
