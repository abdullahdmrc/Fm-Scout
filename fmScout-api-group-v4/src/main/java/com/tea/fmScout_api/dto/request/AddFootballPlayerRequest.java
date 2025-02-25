package com.tea.fmScout_api.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Getter
public class AddFootballPlayerRequest {
    @NotBlank
    private String name;
    @Min(0)
    private int age;
    private String countryName;
    private String clubName;
    private List<String> positions;
    @Min(0)
    private int ca;
    @Min(0)
    private int pa;

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

    @Min(0)
    private double marketValue;
    private double salary;
    private String rentalClubName;
}
