package com.tea.fmScout_api.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "wonderkids")
public class WonderkidsView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    @Column(name = "player_name", nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (age >= 13 AND age <= 60)")
    @Min(13)
    @Max(60)
    private Integer age;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (ca >= 0 AND ca <= 200)")
    @Min(0)
    @Max(200)
    private Integer ca;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (pa >= 0 AND pa <= 200)")
    @Min(0)
    @Max(200)
    private Integer pa;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (corners >= 0 AND corners <= 20)")
    @Min(0)
    @Max(20)
    private Integer corners;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (crossing >= 0 AND crossing <= 20)")
    @Min(0)
    @Max(20)
    private Integer crossing;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (dribbling >= 0 AND dribbling <= 20)")
    @Min(0)
    @Max(20)
    private Integer dribbling;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (finishing >= 0 AND finishing <= 20)")
    @Min(0)
    @Max(20)
    private Integer finishing;

    @Column(name = "first_touch", nullable = false, columnDefinition = "INTEGER CHECK (first_touch >= 0 AND first_touch <= 20)")
    @Min(0)
    @Max(20)
    private Integer firstTouch;

    @Column(name = "free_kick_taking", nullable = false, columnDefinition = "INTEGER CHECK (free_kick_taking >= 0 AND free_kick_taking <= 20)")
    @Min(0)
    @Max(20)
    private Integer freeKickTaking;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (heading >= 0 AND heading <= 20)")
    @Min(0)
    @Max(20)
    private Integer heading;

    @Column(name = "long_shots", nullable = false, columnDefinition = "INTEGER CHECK (long_shots >= 0 AND long_shots <= 20)")
    @Min(0)
    @Max(20)
    private Integer longShots;

    @Column(name = "long_throws", nullable = false, columnDefinition = "INTEGER CHECK (long_throws >= 0 AND long_throws <= 20)")
    @Min(0)
    @Max(20)
    private Integer longThrows;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (marking >= 0 AND marking <= 20)")
    @Min(0)
    @Max(20)
    private Integer marking;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (passing >= 0 AND passing <= 20)")
    @Min(0)
    @Max(20)
    private Integer passing;

    @Column(name = "penalty_taking", nullable = false, columnDefinition = "INTEGER CHECK (penalty_taking >= 0 AND penalty_taking <= 20)")
    @Min(0)
    @Max(20)
    private Integer penaltyTaking;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (tackling >= 0 AND tackling <= 20)")
    @Min(0)
    @Max(20)
    private Integer tackling;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (technique >= 0 AND technique <= 20)")
    @Min(0)
    @Max(20)
    private Integer technique;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (aggression >= 0 AND aggression <= 20)")
    @Min(0)
    @Max(20)
    private Integer aggression;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (anticipation >= 0 AND anticipation <= 20)")
    @Min(0)
    @Max(20)
    private Integer anticipation;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (bravery >= 0 AND bravery <= 20)")
    @Min(0)
    @Max(20)
    private Integer bravery;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (composure >= 0 AND composure <= 20)")
    @Min(0)
    @Max(20)
    private Integer composure;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (concentration >= 0 AND concentration <= 20)")
    @Min(0)
    @Max(20)
    private Integer concentration;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (vision >= 0 AND vision <= 20)")
    @Min(0)
    @Max(20)
    private Integer vision;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (decision >= 0 AND decision <= 20)")
    @Min(0)
    @Max(20)
    private Integer decision;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (determination >= 0 AND determination <= 20)")
    @Min(0)
    @Max(20)
    private Integer determination;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (flair >= 0 AND flair <= 20)")
    @Min(0)
    @Max(20)
    private Integer flair;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (leadership >= 0 AND leadership <= 20)")
    @Min(0)
    @Max(20)
    private Integer leadership;

    @Column(name = "off_the_ball", nullable = false, columnDefinition = "INTEGER CHECK (off_the_ball >= 0 AND off_the_ball <= 20)")
    @Min(0)
    @Max(20)
    private Integer offTheBall;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (positioning >= 0 AND positioning <= 20)")
    @Min(0)
    @Max(20)
    private Integer positioning;

    @Column(name = "team_work", nullable = false, columnDefinition = "INTEGER CHECK (team_work >= 0 AND team_work <= 20)")
    @Min(0)
    @Max(20)
    private Integer teamWork;

    @Column(name = "work_rate", nullable = false, columnDefinition = "INTEGER CHECK (work_rate >= 0 AND work_rate <= 20)")
    @Min(0)
    @Max(20)
    private Integer workRate;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (acceleration >= 0 AND acceleration <= 20)")
    @Min(0)
    @Max(20)
    private Integer acceleration;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (agility >= 0 AND agility <= 20)")
    @Min(0)
    @Max(20)
    private Integer agility;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (balance >= 0 AND balance <= 20)")
    @Min(0)
    @Max(20)
    private Integer balance;

    @Column(name = "jumping_reach", nullable = false, columnDefinition = "INTEGER CHECK (jumping_reach >= 0 AND jumping_reach <= 20)")
    @Min(0)
    @Max(20)
    private Integer jumpingReach;

    @Column(name = "natural_fitness", nullable = false, columnDefinition = "INTEGER CHECK (natural_fitness >= 0 AND natural_fitness <= 20)")
    @Min(0)
    @Max(20)
    private Integer naturalFitness;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (pace >= 0 AND pace <= 20)")
    @Min(0)
    @Max(20)
    private Integer pace;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (stamina >= 0 AND stamina <= 20)")
    @Min(0)
    @Max(20)
    private Integer stamina;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (strength >= 0 AND strength <= 20)")
    @Min(0)
    @Max(20)
    private Integer strength;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (stability >= 0 AND stability <= 20)")
    @Min(0)
    @Max(20)
    private Integer stability;

    @Column(nullable = false, columnDefinition = "INTEGER CHECK (height >= 100 AND height <= 220)")
    @Min(100)
    @Max(220)
    private Integer height;

    @Column(name = "left_foot", nullable = false, columnDefinition = "INTEGER CHECK (left_foot >= 0 AND left_foot <= 20)")
    @Min(0)
    @Max(20)
    private Integer leftFoot;

    @Column(name = "right_foot", nullable = false, columnDefinition = "INTEGER CHECK (right_foot >= 0 AND right_foot <= 20)")
    @Min(0)
    @Max(20)
    private Integer rightFoot;

    @Column(name = "market_value", nullable = false, columnDefinition = "DOUBLE PRECISION CHECK (market_value >= 0)")
    @DecimalMin("0.0")
    private Double marketValue;

    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION CHECK (salary >= 0)")
    @DecimalMin("0.0")
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "rental_club_id")
    private Club rentalClub;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @ManyToMany
    @JoinTable(
            name = "players_roles",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    private List<Position> positions;


    @Column(name="add_to_favourites",nullable = false, columnDefinition ="INTEGER DEFAULT 0" )
    private Integer addToFavourites=0;
}
