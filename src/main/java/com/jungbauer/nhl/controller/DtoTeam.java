package com.jungbauer.nhl.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoTeam {
    private String conferenceAbbrev;
    private String conferenceName;
    private String divisionAbbrev;
    private String divisionName;
    private String placeName;
    private String teamName;
    private String teamCommonName;
    private String teamAbbrev;
    private String teamLogo;
}
