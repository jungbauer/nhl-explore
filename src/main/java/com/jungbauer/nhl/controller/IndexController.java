package com.jungbauer.nhl.controller;

import com.jungbauer.nhl.dto.DtoTeam;
import com.jungbauer.nhl.service.NhlApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
