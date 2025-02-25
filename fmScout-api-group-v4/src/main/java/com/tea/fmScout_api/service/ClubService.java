package com.tea.fmScout_api.service;

import com.tea.fmScout_api.dto.FootballPlayerDto;
import com.tea.fmScout_api.dto.converter.FootballPlayerDtoConverter;
import com.tea.fmScout_api.model.Club;
import com.tea.fmScout_api.model.League;
import com.tea.fmScout_api.model.Manager;
import com.tea.fmScout_api.repository.ClubRepository;
import com.tea.fmScout_api.repository.FootballPlayerRepository;
import com.tea.fmScout_api.repository.ManagerRepository;
import com.tea.fmScout_api.repository.LeagueRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.tea.fmScout_api.dto.ClubDto;
import com.tea.fmScout_api.dto.converter.ClubDtoConverter;
import com.tea.fmScout_api.dto.request.AddClubRequest;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubDtoConverter clubDtoConverter;
    private final ManagerRepository managerRepository;
    private final LeagueRepository leagueRepository;
    private final FootballPlayerRepository footballPlayerRepository;
    private final FootballPlayerDtoConverter footballPlayerDtoConverter;

    @PersistenceContext
    private EntityManager entityManager;

    public ClubService(ClubRepository clubRepository, ClubDtoConverter clubDtoConverter, ManagerRepository managerRepository, LeagueRepository leagueRepository, FootballPlayerRepository footballPlayerRepository, FootballPlayerDtoConverter footballPlayerDtoConverter)
    {
        this.clubRepository = clubRepository;
        this.clubDtoConverter = clubDtoConverter;
        this.managerRepository = managerRepository;
        this.leagueRepository = leagueRepository;
        this.footballPlayerRepository = footballPlayerRepository;
        this.footballPlayerDtoConverter = footballPlayerDtoConverter;
    }

    public List<ClubDto> getAllClubs()
    {
        return clubRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(clubDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public Optional<ClubDto> getClubById(Long clubId)
    {
        return clubRepository.findClubById(clubId)
                .map(clubDtoConverter::convert);
    }

    public List<FootballPlayerDto> findAllPlayers(Long clubId)
    {
        return footballPlayerRepository.findFootballPlayersByClub_Id(clubId).stream()
                .map(footballPlayerDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClubDto createClub(AddClubRequest createRequest)
    {
        if (clubRepository.existsByClubName(createRequest.getClubName())) {
            throw new RuntimeException("The Club is already exists");
        }

        Club club = new Club();
        club.setClubName(createRequest.getClubName());
        club.setFoundationDate(createRequest.getFoundationDate());
        League league = leagueRepository.findByLeagueName(createRequest.getLeagueName())
                .orElseThrow(() -> new RuntimeException("League not found"));
        club.setLeague(league);
        club.setReputation(createRequest.getReputation());
        if (createRequest.getPresidentName() == null || createRequest.getPresidentName().trim().isEmpty() || "Unknown President".equals(createRequest.getPresidentName())) {
            club.setPresidentName(null);
        } else {
            club.setPresidentName(createRequest.getPresidentName());
        }

        if (createRequest.getManagerName() == null || createRequest.getManagerName().trim().isEmpty() || "Unknown Manager".equals(createRequest.getManagerName())){
            club.setManager(null);
        }
        else {
            Manager manager = managerRepository.findByManagerName(createRequest.getManagerName())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
            if(manager.getClub()!=null){
                throw new RuntimeException("Club already exists");
            }
            else{
                club.setManager(manager);
                clubRepository.save(club);
                manager.setClub(club);
                managerRepository.save(manager);
            }
        }



        club = clubRepository.save(club);

        return clubDtoConverter.convert(club);
    }

    @Transactional
    public ClubDto updateClub(Long clubId, AddClubRequest updateRequest) {
        Club existingClub = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        existingClub.setClubName(updateRequest.getClubName());
        existingClub.setFoundationDate(updateRequest.getFoundationDate());
        existingClub.setReputation(updateRequest.getReputation());

        // Handle President Name
        if (updateRequest.getPresidentName() == null || updateRequest.getPresidentName().trim().isEmpty() || "Unknown President".equals(updateRequest.getPresidentName())) {
            existingClub.setPresidentName(null);
        } else {
            existingClub.setPresidentName(updateRequest.getPresidentName());
        }

        // Handle Manager Name
        if (updateRequest.getManagerName() == null || updateRequest.getManagerName().trim().isEmpty() || "Unknown Manager".equals(updateRequest.getManagerName())) {
            if (existingClub.getManager() != null) {
                // Disassociate the current manager
                Manager currentManager = existingClub.getManager();
                currentManager.setClub(null);  // Nullify the current club
                managerRepository.save(currentManager); // Persist the manager changes
                existingClub.setManager(null); // Remove manager from the club
            }
        } else {
            Manager newManager = managerRepository.findByManagerName(updateRequest.getManagerName())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));

            if (!newManager.equals(existingClub.getManager())) { // Only update if manager is different
                if (existingClub.getManager() != null) {
                    // Remove old manager from current club
                    existingClub.getManager().setClub(null);
                    managerRepository.save(existingClub.getManager());
                    existingClub.setManager(null);
                }

                // Ensure the new manager is disassociated from any previous club
                Club oldClub = newManager.getClub();
                if (oldClub != null) {
                    oldClub.setManager(null);
                    clubRepository.save(oldClub); // Persist the changes to the old club
                    newManager.setClub(null); // Nullify the club from the manager
                    managerRepository.save(newManager); // Persist the changes to the manager
                }

                // Now associate the new manager with the current club
                newManager.setClub(existingClub);
                existingClub.setManager(newManager);
                managerRepository.save(newManager); // Persist the new manager changes
            }
        }

        // Update the League association
        League league = leagueRepository.findByLeagueName(updateRequest.getLeagueName())
                .orElseThrow(() -> new RuntimeException("League not found"));
        existingClub.setLeague(league);

        // Save the club with updated manager and league
        existingClub = clubRepository.save(existingClub);

        return clubDtoConverter.convert(existingClub);
    }

    @Transactional
    public void deleteClub(Long clubId) {
        clubRepository.resetPlayerValues(clubId);
        clubRepository.deleteClubById(clubId);
        entityManager.clear();
    }
}
