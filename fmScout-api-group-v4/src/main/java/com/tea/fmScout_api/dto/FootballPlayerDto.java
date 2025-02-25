package com.tea.fmScout_api.dto;

import com.tea.fmScout_api.model.Position;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class FootballPlayerDto {
    private Long playerId;
    private String name;
    private int age;
    private String countryName;
    private Long clubId;
    private String clubName;
    private List<String> positions;
    private Integer ca;
    private Integer pa;

    private Integer corners;
    private Integer crossing;
    private Integer dribbling;
    private Integer finishing;
    private Integer firstTouch;
    private Integer freeKickTaking;
    private Integer heading;
    private Integer longShots;
    private Integer longThrows;
    private Integer marking;
    private Integer passing;
    private Integer penaltyTaking;
    private Integer tackling;
    private Integer technique;
    private Integer aggression;
    private Integer anticipation;
    private Integer bravery;
    private Integer composure;
    private Integer concentration;
    private Integer vision;
    private Integer decision;
    private Integer determination;
    private Integer flair;
    private Integer leadership;
    private Integer offTheBall;
    private Integer positioning;
    private Integer teamWork;
    private Integer workRate;
    private Integer acceleration;
    private Integer agility;
    private Integer balance;
    private Integer jumpingReach;
    private Integer naturalFitness;
    private Integer pace;
    private Integer stamina;
    private Integer strength;
    private Integer stability;
    private Integer height;
    private Integer leftFoot;
    private Integer rightFoot;

    private Double marketValue;
    private double salary;
    private String rentalClubName;
    private Integer addToFavourites;
}

