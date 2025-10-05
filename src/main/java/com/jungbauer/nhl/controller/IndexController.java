package com.jungbauer.nhl.controller;

import com.jungbauer.nhl.apidata.ClubSeasonSchedule;
import com.jungbauer.nhl.dto.DtoTeam;
import com.jungbauer.nhl.service.NhlApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    private final NhlApiService nhlApiService;

    public IndexController(NhlApiService nhlApiService) {
        this.nhlApiService = nhlApiService;
    }

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/teams")
    public String teams(Model model) {
        List<DtoTeam> teams = nhlApiService.getDtoTeams();

        model.addAttribute("teams", teams);

        return "teams";
    }

    @GetMapping("/schedule")
    public String schedule(Model model, @RequestParam(name = "team") String teamCode, @RequestParam(name = "season") String season) {
        List<ClubSeasonSchedule.Game> games = nhlApiService.getClubSeasonSchedule(teamCode, season).getGames();

        model.addAttribute("games", games);

        return "schedule";
    }
}
