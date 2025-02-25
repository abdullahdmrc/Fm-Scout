package com.tea.fmScout_api.model;

import jakarta.persistence.*;
import lombok.Data;
import javax.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "leagues")
@Data
public class League{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leagueId;

    @Column(name = "league_name", nullable = false)
    private String leagueName;

    @Column(name = "league_tier", nullable = false, columnDefinition = "INTEGER CHECK (league_tier >= 1)")
    @Min(1)
    private Integer leagueTier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id",nullable = false)
    private Country country;

    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    private List<Club> clubs;
}
