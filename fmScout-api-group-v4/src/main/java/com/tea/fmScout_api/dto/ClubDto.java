package com.tea.fmScout_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ClubDto {
    private Long clubId;
    private String clubName;
    private Integer foundationDate;
    private String leagueName;
    private Integer reputation;
    private String presidentName;
    private String managerName;
}
