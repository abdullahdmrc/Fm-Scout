package com.tea.fmScout_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LeagueDto {
    private Long leagueId;
    private String leagueName;
    private Integer leagueTier;
    private String countryName;
}
