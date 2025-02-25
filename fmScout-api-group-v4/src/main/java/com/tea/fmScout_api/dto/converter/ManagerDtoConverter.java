package com.tea.fmScout_api.dto.converter;

import com.tea.fmScout_api.dto.ManagerDto;
import com.tea.fmScout_api.model.Manager;
import com.tea.fmScout_api.repository.ClubRepository;
import org.springframework.stereotype.Component;

@Component
public class ManagerDtoConverter {
    private final ClubRepository clubRepository;

    public ManagerDtoConverter(ClubRepository clubRepository)
    {
        this.clubRepository = clubRepository;
    }

    public ManagerDto convert(Manager from)
    {
        String clubName = (from.getClub() != null) ? from.getClub().getClubName() : "Unknown Club";

        return new ManagerDto(from.getManagerName(),
                from.getAttacking(),
                from.getDefending(),
                from.getFitness(),
                from.getMental(),
                from.getTactical(),
                from.getTechnical(),
                from.getWorkingWithYoungStars(),
                from.getBirthDate(),
                clubName);
    }
}