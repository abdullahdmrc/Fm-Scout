package com.tea.fmScout_api.service;

import com.tea.fmScout_api.dto.converter.FootballPlayerDtoConverter;
import com.tea.fmScout_api.dto.FootballPlayerDto;
import com.tea.fmScout_api.dto.request.AddFootballPlayerRequest;
import com.tea.fmScout_api.model.Club;
import com.tea.fmScout_api.model.Country;
import com.tea.fmScout_api.model.FootballPlayer;
import com.tea.fmScout_api.model.PlayerRole;
import com.tea.fmScout_api.model.Specification.SpecificationUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import com.tea.fmScout_api.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FootballPlayerService {

    private final FootballPlayerRepository footballPlayerRepository;
    private final FootballPlayerDtoConverter footballPlayerDtoConverter;
    private final PlayerRoleRepository playerRoleRepository;
    private final CountryRepository countryRepository;
    private final ClubRepository clubRepository;
    private final PositionRepository positionRepository;
    private final FreeTransferRepository freeTransferRepository;
    private final WonderkidRepository wonderkidRepository;

    public FootballPlayerService(FootballPlayerRepository footballPlayerRepository,
                                 FootballPlayerDtoConverter footballPlayerDtoConverter,
                                 PlayerRoleRepository playerRoleRepository,
                                 CountryRepository countryRepository,
                                 ClubRepository clubRepository,
                                 PositionRepository positionRepository,
                                 FreeTransferRepository freeTransferRepository,
                                 WonderkidRepository wonderkidRepository) {
        this.footballPlayerRepository = footballPlayerRepository;
        this.footballPlayerDtoConverter = footballPlayerDtoConverter;
        this.playerRoleRepository = playerRoleRepository;
        this.countryRepository = countryRepository;
        this.clubRepository = clubRepository;
        this.positionRepository = positionRepository;
        this.freeTransferRepository = freeTransferRepository;
        this.wonderkidRepository = wonderkidRepository;
    }

    public FootballPlayerDto getFootballPlayerById(Long id) {
        FootballPlayer player = footballPlayerRepository.findPlayerById(id)
                .orElseThrow(() -> new RuntimeException("Football player not found with id: " + id));
        return footballPlayerDtoConverter.convert(player);
    }


    public List<FootballPlayerDto> getAllFootballPlayers() {
        return footballPlayerRepository.findAll().stream()
                .map(footballPlayerDtoConverter::convert)
                .toList();
    }

    public List<FootballPlayerDto> getAllFootballPlayers(String sortBy, String sortOrder) {
        Sort sort;
        if (sortOrder.equalsIgnoreCase("asc")){
            sort = Sort.by(Sort.Direction.ASC, sortBy);
        }else {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        }

        return footballPlayerRepository.findAll(sort).stream()
                .map(footballPlayerDtoConverter::convert)
                .toList();
    }

    public List<FootballPlayerDto> getSortedPlayers(String attribute, String direction) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Sort sort = Sort.by(sortDirection, attribute);

        List<FootballPlayer> players = footballPlayerRepository.findAll(sort);

        return players.stream()
                .map(footballPlayerDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public List<FootballPlayerDto> filterPlayers(List<Specification<FootballPlayer>> specs) {
        Specification<FootballPlayer> combinedSpec = SpecificationUtils.combine(specs);
        List<FootballPlayer> players =  footballPlayerRepository.findAll(combinedSpec);

        return players.stream()
                .map(footballPlayerDtoConverter::convert)
                .collect(Collectors.toList());
    }


    // Wonderkids
    public List<FootballPlayerDto> getWonderkids() {
        return wonderkidRepository.findAll().stream()
                .map(footballPlayerDtoConverter::convert)
                .collect(Collectors.toList());
    }
    // Free Transfers
    public List<FootballPlayerDto> getFreeTransfers() {
        return freeTransferRepository.findAll().stream()
                .map(footballPlayerDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public FootballPlayerDto createFootballPlayer(AddFootballPlayerRequest createRequest)
    {
        // Check if a player with the same name and DateOfBirth already exists
        if (footballPlayerRepository.existsFootballPlayerByName(createRequest.getName())) {
            throw new RuntimeException("Football player with name: " + createRequest.getName() + " and same age already exists");
        }


        FootballPlayer savedPlayer = footballPlayerRepository.save(handleRequest(new FootballPlayer(), createRequest));
        handlePositions(savedPlayer, createRequest);
        FootballPlayer updatedPlayer = footballPlayerRepository.save(savedPlayer);
        return footballPlayerDtoConverter.convert(updatedPlayer);
    }

    @Transactional
    public FootballPlayerDto updateFootballPlayer(Long id, AddFootballPlayerRequest updateRequest)
    {
        FootballPlayer existingPlayer = footballPlayerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Football player not found with id: " + id));

        FootballPlayer updatedPlayer = handleRequest(existingPlayer, updateRequest);
        handlePositions(updatedPlayer, updateRequest);
        footballPlayerRepository.save(updatedPlayer);
        return footballPlayerDtoConverter.convert(updatedPlayer);
    }
    @Transactional
    public void deletePlayer(Long playerId)
    {
        footballPlayerRepository.deleteById(playerId);
    }

    private FootballPlayer handleRequest(FootballPlayer player, AddFootballPlayerRequest createRequest)
    {
        player.setName(createRequest.getName());
        player.setAge(createRequest.getAge());
        player.setCa(createRequest.getCa());
        player.setPa(createRequest.getPa());

        player.setCorners(createRequest.getCorners());
        player.setCrossing(createRequest.getCrossing());
        player.setDribbling(createRequest.getDribbling());
        player.setFinishing(createRequest.getFinishing());
        player.setFirstTouch(createRequest.getFirstTouch());
        player.setFreeKickTaking(createRequest.getFreeKickTaking());
        player.setHeading(createRequest.getHeading());
        player.setLongShots(createRequest.getLongShots());
        player.setLongThrows(createRequest.getLongThrows());
        player.setMarking(createRequest.getMarking());
        player.setPassing(createRequest.getPassing());
        player.setPenaltyTaking(createRequest.getPenaltyTaking());
        player.setTackling(createRequest.getTackling());
        player.setTechnique(createRequest.getTechnique());
        player.setAggression(createRequest.getAggression());
        player.setAnticipation(createRequest.getAnticipation());
        player.setBravery(createRequest.getBravery());
        player.setComposure(createRequest.getComposure());
        player.setConcentration(createRequest.getConcentration());
        player.setVision(createRequest.getVision());
        player.setDecision(createRequest.getDecision());
        player.setDetermination(createRequest.getDetermination());
        player.setFlair(createRequest.getFlair());
        player.setLeadership(createRequest.getLeadership());
        player.setOffTheBall(createRequest.getOffTheBall());
        player.setPositioning(createRequest.getPositioning());
        player.setTeamWork(createRequest.getTeamWork());
        player.setWorkRate(createRequest.getWorkRate());
        player.setAcceleration(createRequest.getAcceleration());
        player.setAgility(createRequest.getAgility());
        player.setBalance(createRequest.getBalance());
        player.setJumpingReach(createRequest.getJumpingReach());
        player.setNaturalFitness(createRequest.getNaturalFitness());
        player.setPace(createRequest.getPace());
        player.setStamina(createRequest.getStamina());
        player.setStrength(createRequest.getStrength());
        player.setStability(createRequest.getStability());
        player.setHeight(createRequest.getHeight());
        player.setLeftFoot(createRequest.getLeftFoot());
        player.setRightFoot(createRequest.getRightFoot());

        player.setSalary(createRequest.getSalary());
        player.setMarketValue(createRequest.getMarketValue());


        Country country = countryRepository.findByCountryName(createRequest.getCountryName())
                .orElseThrow(() -> new RuntimeException("Country not found"));
        player.setCountry(country);

        if (createRequest.getClubName() == null || createRequest.getClubName().trim().isEmpty() || "No Club".equals(createRequest.getClubName())) {
            player.setClub(null);
        } else {
            Club club = clubRepository.findByClubName(createRequest.getClubName())
                    .orElseThrow(() -> new RuntimeException("Club not found"));
            player.setClub(club);
        }

        if (createRequest.getRentalClubName() == null || createRequest.getRentalClubName().trim().isEmpty() || "No Rental Club".equals(createRequest.getRentalClubName())) {
            player.setRentalClub(null);
        } else {
            Club rentalclub = clubRepository.findByClubName(createRequest.getRentalClubName())
                    .orElseThrow(() -> new RuntimeException("Club not found"));
            player.setRentalClub(rentalclub);
        }

        player.setDateOfBirth(LocalDate.now().minusYears(createRequest.getAge()));

        return player;
    }

    private void handlePositions(FootballPlayer player, AddFootballPlayerRequest createRequest)
    {
        List<String> positions = createRequest.getPositions();
        if (positions != null && !positions.isEmpty()) {
            playerRoleRepository.deleteByPlayer_PlayerId(player.getPlayerId());

            List<PlayerRole> playerRoles = positions.stream()
                    .map(positionName -> positionRepository.findByPositionName(positionName)
                            .map(position -> new PlayerRole(player, position))
                            .orElseThrow(() -> new RuntimeException("Invalid position: " + positionName)))
                    .toList();

            playerRoleRepository.saveAll(playerRoles);
        }

    }
}
