package com.jungbauer.nhl.controller;

import com.jungbauer.nhl.apidata.ClubSeasonSchedule;
import com.jungbauer.nhl.domain.Team;
import com.jungbauer.nhl.dto.DtoTeam;
import com.jungbauer.nhl.repository.TeamRepository;
import com.jungbauer.nhl.service.NhlApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    private final NhlApiService nhlApiService;
    private final TeamRepository teamRepository;

    public IndexController(NhlApiService nhlApiService, TeamRepository teamRepository) {
        this.nhlApiService = nhlApiService;
        this.teamRepository = teamRepository;
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

    @GetMapping("/teamsdb")
    public String teamsdb(Model model) {
        List<Team> dbTeams = (List<Team>) teamRepository.findAll();

        List<DtoTeam> teams = new ArrayList<>();

        for (Team team : dbTeams) {
            DtoTeam dtoTeam = new DtoTeam();
            dtoTeam.setConferenceAbbrev(team.getConference().getAbbrev());
            dtoTeam.setConferenceName(team.getConference().getName());
            dtoTeam.setDivisionAbbrev(team.getDivision().getAbbrev());
            dtoTeam.setDivisionName(team.getDivision().getName());
            dtoTeam.setPlaceName(team.getPlaceName());
            dtoTeam.setTeamName(team.getTeamName());
            dtoTeam.setTeamCommonName(team.getCommonName());
            dtoTeam.setTeamAbbrev(team.getAbbrev());
            dtoTeam.setTeamLogo(team.getLogo());

            teams.add(dtoTeam);
        }


        model.addAttribute("teams", teams);

        return "teams";
    }
}
