package com.tea.fmScout_api.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class AddClubRequest {
    @NotBlank
    private Long id;
    @NotBlank
    private String clubName;
    @NotBlank
    private Integer foundationDate;
    @NotBlank
    private String leagueName;
    @NotBlank
    private Integer reputation;
    private String presidentName;
    private String managerName;
}
