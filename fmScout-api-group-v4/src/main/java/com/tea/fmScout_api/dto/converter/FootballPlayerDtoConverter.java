package com.tea.fmScout_api.dto.converter;

import com.tea.fmScout_api.dto.FootballPlayerDto;
import com.tea.fmScout_api.model.FootballPlayer;
import com.tea.fmScout_api.model.FreeTransfersView;
import com.tea.fmScout_api.model.WonderkidsView;
import com.tea.fmScout_api.repository.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
public class FootballPlayerDtoConverter {

    private final ClubRepository clubRepository;
    private final CountryRepository countryRepository;
    private final PlayerRoleRepository playerRoleRepository;
    private final PositionRepository positionRepository;

    public FootballPlayerDtoConverter(ClubRepository clubRepository, CountryRepository countryRepository, PlayerRoleRepository playerRoleRepository, PositionRepository positionRepository)
    {
        this.clubRepository = clubRepository;
        this.countryRepository = countryRepository;
        this.playerRoleRepository = playerRoleRepository;
        this.positionRepository = positionRepository;
    }

    public FootballPlayerDto convert(FootballPlayer from) {

        String countryName = from.getCountry().getCountryName();
        String clubName = (from.getClub() != null) ? from.getClub().getClubName() : "No Club";
        String rentalClubName = (from.getRentalClub() != null) ? from.getRentalClub().getClubName() : "No Rental Club";
        Long clubId=(from.getClub()!=null) ? from.getClub().getId():0;
        List<String> positions = playerRoleRepository.findPosition_PositionNamesByPlayer_PlayerId(from.getPlayerId());

        return new FootballPlayerDto(
                from.getPlayerId(),
                from.getName(),
                from.getAge(),
                countryName,
                clubId,
                clubName,
                positions,
                from.getCa(),
                from.getPa(),

                from.getCorners(),
                from.getCrossing(),
                from.getDribbling(),
                from.getFinishing(),
                from.getFirstTouch(),
                from.getFreeKickTaking(),
                from.getHeading(),
                from.getLongShots(),
                from.getLongThrows(),
                from.getMarking(),
                from.getPassing(),
                from.getPenaltyTaking(),
                from.getTackling(),
                from.getTechnique(),
                from.getAggression(),
                from.getAnticipation(),
                from.getBravery(),
                from.getComposure(),
                from.getConcentration(),
                from.getVision(),
                from.getDecision(),
                from.getDetermination(),
                from.getFlair(),
                from.getLeadership(),
                from.getOffTheBall(),
                from.getPositioning(),
                from.getTeamWork(),
                from.getWorkRate(),
                from.getAcceleration(),
                from.getAgility(),
                from.getBalance(),
                from.getJumpingReach(),
                from.getNaturalFitness(),
                from.getPace(),
                from.getStamina(),
                from.getStrength(),
                from.getStability(),
                from.getHeight(),
                from.getLeftFoot(),
                from.getRightFoot(),

                from.getMarketValue(),
                from.getSalary(),
                rentalClubName,
                from.getAddToFavourites()
        );
    }
    public FootballPlayerDto convert(WonderkidsView from)
    {
        String countryName = from.getCountry().getCountryName();
        String clubName = (from.getClub() != null) ? from.getClub().getClubName() : "No Club";
        String rentalClubName = (from.getRentalClub() != null) ? from.getRentalClub().getClubName() : "No Rental Club";
        Long clubId=(from.getClub()!=null) ? from.getClub().getId():0;
        List<String> positions = playerRoleRepository.findPosition_PositionNamesByPlayer_PlayerId(from.getPlayerId());

        return new FootballPlayerDto(
                from.getPlayerId(),
                from.getName(),
                from.getAge(),
                countryName,
                clubId,
                clubName,
                positions,
                from.getCa(),
                from.getPa(),

                from.getCorners(),
                from.getCrossing(),
                from.getDribbling(),
                from.getFinishing(),
                from.getFirstTouch(),
                from.getFreeKickTaking(),
                from.getHeading(),
                from.getLongShots(),
                from.getLongThrows(),
                from.getMarking(),
                from.getPassing(),
                from.getPenaltyTaking(),
                from.getTackling(),
                from.getTechnique(),
                from.getAggression(),
                from.getAnticipation(),
                from.getBravery(),
                from.getComposure(),
                from.getConcentration(),
                from.getVision(),
                from.getDecision(),
                from.getDetermination(),
                from.getFlair(),
                from.getLeadership(),
                from.getOffTheBall(),
                from.getPositioning(),
                from.getTeamWork(),
                from.getWorkRate(),
                from.getAcceleration(),
                from.getAgility(),
                from.getBalance(),
                from.getJumpingReach(),
                from.getNaturalFitness(),
                from.getPace(),
                from.getStamina(),
                from.getStrength(),
                from.getStability(),
                from.getHeight(),
                from.getLeftFoot(),
                from.getRightFoot(),

                from.getMarketValue(),
                from.getSalary(),
                rentalClubName,
                from.getAddToFavourites()
        );
    }

    public FootballPlayerDto convert(FreeTransfersView from)
    {
        String countryName = from.getCountry().getCountryName();
        String clubName = (from.getClub() != null) ? from.getClub().getClubName() : "No Club";
        String rentalClubName = (from.getRentalClub() != null) ? from.getRentalClub().getClubName() : "No Rental Club";
        Long clubId=(from.getClub()!=null) ? from.getClub().getId():0;
        List<String> positions = playerRoleRepository.findPosition_PositionNamesByPlayer_PlayerId(from.getPlayerId());

        return new FootballPlayerDto(
                from.getPlayerId(),
                from.getName(),
                from.getAge(),
                countryName,
                clubId,
                clubName,
                positions,
                from.getCa(),
                from.getPa(),

                from.getCorners(),
                from.getCrossing(),
                from.getDribbling(),
                from.getFinishing(),
                from.getFirstTouch(),
                from.getFreeKickTaking(),
                from.getHeading(),
                from.getLongShots(),
                from.getLongThrows(),
                from.getMarking(),
                from.getPassing(),
                from.getPenaltyTaking(),
                from.getTackling(),
                from.getTechnique(),
                from.getAggression(),
                from.getAnticipation(),
                from.getBravery(),
                from.getComposure(),
                from.getConcentration(),
                from.getVision(),
                from.getDecision(),
                from.getDetermination(),
                from.getFlair(),
                from.getLeadership(),
                from.getOffTheBall(),
                from.getPositioning(),
                from.getTeamWork(),
                from.getWorkRate(),
                from.getAcceleration(),
                from.getAgility(),
                from.getBalance(),
                from.getJumpingReach(),
                from.getNaturalFitness(),
                from.getPace(),
                from.getStamina(),
                from.getStrength(),
                from.getStability(),
                from.getHeight(),
                from.getLeftFoot(),
                from.getRightFoot(),

                from.getMarketValue(),
                from.getSalary(),
                rentalClubName,
                from.getAddToFavourites()
        );
    }
}
