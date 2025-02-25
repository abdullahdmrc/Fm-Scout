package com.tea.fmScout_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ManagerDto {
    private String managerName;
    private Integer attacking;
    private Integer defending;
    private Integer fitness;
    private Integer mental;
    private Integer tactical;
    private Integer technical;
    private Integer workingWithYoungStars;
    private LocalDate birthDate;
    private String clubName;
}
