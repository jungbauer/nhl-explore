package com.jungbauer.nhl.controller;

import com.jungbauer.nhl.apidata.Standings;
import com.jungbauer.nhl.apidata.Team;
import com.jungbauer.nhl.dto.DtoTeam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    private final RestClient restClient;

    public IndexController(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("https://api-web.nhle.com/v1").build();
    }

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/teams")
    public String teams(Model model) {

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

        model.addAttribute("teams", teams);

        return "teams";
    }
}
