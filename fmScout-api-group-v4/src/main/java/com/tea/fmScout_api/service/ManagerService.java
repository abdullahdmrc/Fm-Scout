package com.tea.fmScout_api.service;

import com.tea.fmScout_api.dto.ClubDto;
import com.tea.fmScout_api.dto.ManagerDto;
import com.tea.fmScout_api.dto.converter.ClubDtoConverter;
import com.tea.fmScout_api.dto.converter.ManagerDtoConverter;
import com.tea.fmScout_api.dto.request.AddClubRequest;
import com.tea.fmScout_api.dto.request.AddManagerRequest;
import com.tea.fmScout_api.model.Club;
import com.tea.fmScout_api.model.Manager;
import com.tea.fmScout_api.repository.ClubRepository;
import com.tea.fmScout_api.repository.ManagerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerDtoConverter managerDtoConverter;
    private final ClubDtoConverter clubDtoConverter;
    private final ClubRepository clubRepository;

    public ManagerService(ManagerRepository managerRepository, ManagerDtoConverter managerDtoConverter, ClubDtoConverter clubDtoConverter, ClubRepository clubRepository)
    {
        this.managerRepository = managerRepository;
        this.managerDtoConverter = managerDtoConverter;
        this.clubDtoConverter = clubDtoConverter;
        this.clubRepository = clubRepository;
    }

    public List<ManagerDto> getAllManagers() {

        return managerRepository.findAll(Sort.by(Sort.Direction.ASC, "managerId"))
                .stream()
                .map(managerDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public Optional<ManagerDto> getManagerById(Long managerId) {

        return managerRepository.findById(managerId)
                .map(managerDtoConverter::convert);
    }

    @Transactional
    public ManagerDto createManager(AddManagerRequest createRequest)
    {
        if (managerRepository.existsByManagerName(createRequest.getManagerName())) {
            throw new RuntimeException("The manager is already exists");
        }

        Manager manager = managerRepository.save(handleRequest(new Manager(), createRequest));

        return managerDtoConverter.convert(manager);
    }

    @Transactional
    public ManagerDto updateManager(Long managerId, AddManagerRequest updateRequest) {
        Manager existingManager = managerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        existingManager = managerRepository.save(handleRequest(existingManager, updateRequest));

        return managerDtoConverter.convert(existingManager);
    }

    @Transactional
    public void deleteManager(Long managerId)
    {
        managerRepository.deleteById(managerId);
    }

    private Manager handleRequest(Manager manager, AddManagerRequest addRequest)
    {
        manager.setManagerName(addRequest.getManagerName());
        manager.setBirthDate(addRequest.getBirthDate());
        manager.setAttacking(addRequest.getAttacking());
        manager.setDefending(addRequest.getDefending());
        manager.setFitness(addRequest.getFitness());
        manager.setMental(addRequest.getMental());
        manager.setTactical(addRequest.getTactical());
        manager.setTechnical(addRequest.getTechnical());
        manager.setWorkingWithYoungStars(addRequest.getWorkingWithYoungStars());
        manager.setBirthDate(addRequest.getBirthDate());

        if(addRequest.getClubName() != null) {
            Club club = clubRepository.findByClubName(addRequest.getClubName())
                    .orElseThrow(() -> new RuntimeException("Club not found"));
            manager.setClub(club);
        }
        return manager;
    }
}
