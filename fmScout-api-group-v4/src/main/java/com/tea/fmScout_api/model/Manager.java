package com.tea.fmScout_api.model;

import jakarta.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "managers")
@Data
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    @Column(name = "manager_name", nullable = false)
    private String managerName;

    @Column(name = "attacking", nullable = false, columnDefinition = "INTEGER CHECK (attacking >= 0 AND attacking <= 20)")
    @Min(0)
    @Max(20)
    private Integer attacking;

    @Column(name = "defending", nullable = false, columnDefinition = "INTEGER CHECK (defending >= 0 AND defending <= 20)")
    @Min(0)
    @Max(20)
    private Integer defending;

    @Column(name = "fitness", nullable = false, columnDefinition = "INTEGER CHECK (fitness >= 0 AND fitness <= 20)")
    @Min(0)
    @Max(20)
    private Integer fitness;

    @Column(name = "mental", nullable = false, columnDefinition = "INTEGER CHECK (mental >= 0 AND mental <= 20)")
    @Min(0)
    @Max(20)
    private Integer mental;

    @Column(name = "tactical", nullable = false, columnDefinition = "INTEGER CHECK (tactical >= 0 AND tactical <= 20)")
    @Min(0)
    @Max(20)
    private Integer tactical;

    @Column(name = "technical", nullable = false, columnDefinition = "INTEGER CHECK (technical >= 0 AND technical <= 20)")
    @Min(0)
    @Max(20)
    private Integer technical;

    @Column(name = "working_with_young_stars", nullable = false, columnDefinition = "INTEGER CHECK (working_with_young_stars >= 0 AND working_with_young_stars <= 20)")
    @Min(0)
    @Max(20)
    private Integer workingWithYoungStars;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "club_id")
    private Club club;
}
