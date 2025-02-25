package com.tea.fmScout_api.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.*;

@Entity
@Table(name = "clubs")
@Data
public class Club {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "club_seq")
    @SequenceGenerator(name = "club_seq", sequenceName = "club_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "club_name", nullable = false)
    @NotBlank
    private String clubName;

    @Column(name = "foundation_date", nullable = false, columnDefinition = "INTEGER CHECK (foundation_date >= 1800 AND foundation_date <= 2023)")
    @Min(1800)
    @Max(2023)
    private Integer foundationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "league_id")
    private League league;

    @Column(name = "reputation", nullable = false, columnDefinition = "INTEGER CHECK (reputation >= 0 AND reputation <= 100)")
    @Min(0)
    @Max(100)
    private Integer reputation;

    @Column(name = "president_name")
    private String presidentName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Manager manager;
}
