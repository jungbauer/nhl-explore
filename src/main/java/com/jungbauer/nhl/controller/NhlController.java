package com.jungbauer.nhl.controller;

import com.jungbauer.nhl.apidata.ClubSeasonSchedule;
import com.jungbauer.nhl.apidata.GameCenterLanding;
import com.jungbauer.nhl.apidata.GameCenterPlayByPlay;
import com.jungbauer.nhl.apidata.Standings;
import com.jungbauer.nhl.dto.DtoTeam;
import com.jungbauer.nhl.service.NhlApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class NhlController {

    private final NhlApiService nhlApiService;

    public NhlController(NhlApiService nhlApiService) {
        this.nhlApiService = nhlApiService;
    }

    @GetMapping("/standings")
    public Standings standings() {
        return nhlApiService.getStandingsNow();
    }

    @GetMapping("/teams")
    public List<DtoTeam> teams() {
        return nhlApiService.getDtoTeams();
    }

    @GetMapping("/club-schedule")
    public ClubSeasonSchedule clubSchedule(@RequestParam(name = "team") String teamCode, @RequestParam(name = "season") String season) {
        return nhlApiService.getClubSeasonSchedule(teamCode, season);
    }

    @GetMapping("/gamecenter-landing")
    public GameCenterLanding gamecenterLanding(@RequestParam(name = "gameId") String gameId) {
        return nhlApiService.getGameCenterLanding(gameId);
    }

    @GetMapping("/gamecenter-playbyplay")
    public GameCenterPlayByPlay gamecenterPlayByPlay(@RequestParam(name = "gameId") String gameId) {
        return nhlApiService.getGameCenterPlayByPlay(gameId);
    }

//    @GetMapping("/populateteams")
//    public String populateteams() {
//        return nhlApiService.populateTeamTable();
//    }
//
//    @GetMapping("/updateconfdiv")
//    public String updateconfdiv() {
//        return nhlApiService.updateconfdiv();
//    }

}
