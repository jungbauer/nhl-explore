package com.jungbauer.nhl.service;

import com.jungbauer.nhl.apidata.*;
import com.jungbauer.nhl.domain.Conference;
import com.jungbauer.nhl.domain.Division;
import com.jungbauer.nhl.dto.DtoTeam;
import com.jungbauer.nhl.repository.ConferenceRepository;
import com.jungbauer.nhl.repository.DivisionRepository;
import com.jungbauer.nhl.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NhlApiService {

    private final String API_BASE_URL = "https://api-web.nhle.com/v1";

    private final RestClient restClient;
    private final ConferenceRepository conferenceRepository;
    private final DivisionRepository divisionRepository;
    private final TeamRepository teamRepository;

    public NhlApiService(RestClient.Builder restClientBuilder, ConferenceRepository conferenceRepository,
                         DivisionRepository divisionRepository, TeamRepository teamRepository) {
        this.restClient = restClientBuilder.baseUrl(API_BASE_URL).build();
        this.conferenceRepository = conferenceRepository;
        this.divisionRepository = divisionRepository;
        this.teamRepository = teamRepository;
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

    public GameCenterLanding getGameCenterLanding(String gameId) {
        return restClient.get().uri("/gamecenter/{gameId}/landing", gameId).retrieve().body(GameCenterLanding.class);
    }

    public GameCenterPlayByPlay getGameCenterPlayByPlay(String gameId) {
        return restClient.get().uri("/gamecenter/{gameId}/play-by-play", gameId).retrieve().body(GameCenterPlayByPlay.class);
    }

    public String populateTeamTable() {
        List<ClubSeasonSchedule.Game> games = getClubSeasonSchedule("TOR", "20252026").getGames();
        int saveCounter = 0;
        for (ClubSeasonSchedule.Game game : games) {
            Optional<com.jungbauer.nhl.domain.Team> dbHomeTeam = teamRepository.findByNhlIdAndAbbrev(game.getHomeTeam().getId(), game.getHomeTeam().getAbbrev());
            if (dbHomeTeam.isEmpty()) {
                com.jungbauer.nhl.domain.Team homeTeam = new com.jungbauer.nhl.domain.Team();
                homeTeam.setNhlId(game.getHomeTeam().getId());
                homeTeam.setCommonName(game.getHomeTeam().getCommonName().get("default"));
                homeTeam.setPlaceName(game.getHomeTeam().getPlaceName().get("default"));
                homeTeam.setAbbrev(game.getHomeTeam().getAbbrev());
                homeTeam.setLogo(game.getHomeTeam().getLogo());
                homeTeam.setDarkLogo(game.getHomeTeam().getDarkLogo());
                teamRepository.save(homeTeam);
                saveCounter++;
            }

            Optional<com.jungbauer.nhl.domain.Team> dbAwayTeam = teamRepository.findByNhlIdAndAbbrev(game.getAwayTeam().getId(), game.getAwayTeam().getAbbrev());
            if (dbAwayTeam.isEmpty()) {
                com.jungbauer.nhl.domain.Team awayTeam = new com.jungbauer.nhl.domain.Team();
                awayTeam.setNhlId(game.getAwayTeam().getId());
                awayTeam.setCommonName(game.getAwayTeam().getCommonName().get("default"));
                awayTeam.setPlaceName(game.getAwayTeam().getPlaceName().get("default"));
                awayTeam.setAbbrev(game.getAwayTeam().getAbbrev());
                awayTeam.setLogo(game.getAwayTeam().getLogo());
                awayTeam.setDarkLogo(game.getAwayTeam().getDarkLogo());
                teamRepository.save(awayTeam);
                saveCounter++;
            }
        }

        return "populated team table: " + saveCounter;
    }

    public String updateconfdiv() {
        Standings standings = getStandingsNow();
        int updateCounter = 0;
        for (Team team : standings.getStandings()) {
            Optional<com.jungbauer.nhl.domain.Team> dbOptTeam = teamRepository.findByAbbrev(team.getTeamAbbrev().getDefault());
            if (dbOptTeam.isPresent()) {
                com.jungbauer.nhl.domain.Team dbTeam = dbOptTeam.get();
                dbTeam.setTeamName(team.getTeamName().getDefault());

                Optional<Conference> optConf = conferenceRepository.findByNameAndAbbrev(team.getConferenceName(), team.getConferenceAbbrev());
                optConf.ifPresent(dbTeam::setConference);

                Optional<Division> optDiv = divisionRepository.findByNameAndAbbrev(team.getDivisionName(), team.getDivisionAbbrev());
                optDiv.ifPresent(dbTeam::setDivision);

                teamRepository.save(dbTeam);
                updateCounter++;
            }
        }

        return "confdiv updated: " + updateCounter;
    }
}
