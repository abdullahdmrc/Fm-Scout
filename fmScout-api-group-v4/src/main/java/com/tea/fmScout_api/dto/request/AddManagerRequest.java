package com.tea.fmScout_api.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
public class AddManagerRequest {
    @NotEmpty
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
