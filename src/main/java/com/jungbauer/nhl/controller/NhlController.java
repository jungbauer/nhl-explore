package com.jungbauer.nhl.controller;

import com.jungbauer.nhl.apidata.ClubSeasonSchedule;
import com.jungbauer.nhl.apidata.Standings;
import com.jungbauer.nhl.apidata.Team;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class NhlController {

    private final RestClient restClient;

    public NhlController(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("https://api-web.nhle.com/v1").build();
    }

    @GetMapping("/standings")
    public Standings standings() {
        return restClient.get().uri("/standings/now").retrieve().body(Standings.class);
    }

    @GetMapping("/teams")
    public List<DtoTeam> teams() {
        Standings standings = restClient.get().uri("/standings/now").retrieve().body(Standings.class);

        List<DtoTeam> teams = new ArrayList<>();

        assert standings != null;
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

    @GetMapping("/club-schedule")
    public ClubSeasonSchedule clubSchedule(@RequestParam(name = "team") String teamCode, @RequestParam(name = "season") String season) {
        return restClient.get().uri("/club-schedule-season/{teamCode}/{season}", teamCode, season).retrieve().body(ClubSeasonSchedule.class);
    }


}
