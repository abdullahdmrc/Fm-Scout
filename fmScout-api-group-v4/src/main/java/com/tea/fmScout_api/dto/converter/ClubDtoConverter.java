package com.tea.fmScout_api.dto.converter;

import com.tea.fmScout_api.dto.ClubDto;
import com.tea.fmScout_api.model.Club;
import com.tea.fmScout_api.repository.LeagueRepository;
import com.tea.fmScout_api.repository.ManagerRepository;
import org.springframework.stereotype.Component;

@Component
public class ClubDtoConverter {

    private final LeagueRepository leagueRepository;
    private final ManagerRepository managerRepository;

    public ClubDtoConverter(LeagueRepository leagueRepository, ManagerRepository managerRepository) {
        this.leagueRepository = leagueRepository;
        this.managerRepository = managerRepository;
    }

    public ClubDto convert(Club from) {
        String leagueName = (from.getLeague() != null) ? from.getLeague().getLeagueName() : "Unknown League";
        String managerName = (from.getManager() != null) ? from.getManager().getManagerName() : "Unknown Manager";
        String presidentName = (from.getPresidentName() != null) ? from.getPresidentName() : "Unknown President";

        return new ClubDto(
                from.getId(),
                from.getClubName(),
                from.getFoundationDate(),
                leagueName,
                from.getReputation(),
                presidentName,
                managerName
        );
    }
}
