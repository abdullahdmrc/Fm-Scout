package com.tea.fmScout_api.dto.request;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
public class AddLeagueRequest {
    @NotBlank
    private String leagueName;
    @Min(0)
    private Integer leagueTier;
    private String countryName;
}
