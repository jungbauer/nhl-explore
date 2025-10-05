package com.jungbauer.nhl.service;

import com.jungbauer.nhl.apidata.ClubSeasonSchedule;
import com.jungbauer.nhl.apidata.Standings;
import com.jungbauer.nhl.apidata.Team;
import com.jungbauer.nhl.dto.DtoTeam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class NhlApiService {

    private final String API_BASE_URL = "https://api-web.nhle.com/v1";

    private final RestClient restClient;

    public NhlApiService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl(API_BASE_URL).build();
    }

    public Standings getStandingsNow() {
        return restClient.get().uri("/standings/now").retrieve().body(Standings.class);
    }

    public ClubSeasonSchedule getClubSeasonSchedule(String teamCode, String season) {
        return restClient.get().uri("/club-schedule-season/{teamCode}/{season}", teamCode, season).retrieve().body(ClubSeasonSchedule.class);
    }

    public List<DtoTeam> getDtoTeams() {
        Standings standings = getStandingsNow();
        List<DtoTeam> teams = new ArrayList<>();

        for (Team team : standings.getStandings()) {
            DtoTeam dtoTeam = new DtoTeam();
            dtoTeam.setConferenceAbbrev(team.getConferenceAbbrev());
            dtoTeam.setConferenceName(team.getConferenceName());
            dtoTeam.setDivisionAbbrev(team.getDivisionAbbrev());
            dtoTeam.setDivisionName(team.getDivisionName());
            dtoTeam.setPlaceName(team.getPlaceName().getDefault());
            dtoTeam.setTeamName(team.getTeamName().getDefault());
            dtoTeam.setTeamCommonName(team.getTeamCommonName().getDefault());
            dtoTeam.setTeamAbbrev(team.getTeamAbbrev().getDefault());
            dtoTeam.setTeamLogo(team.getTeamLogo());

            teams.add(dtoTeam);
        }
        return teams;
    }
}
