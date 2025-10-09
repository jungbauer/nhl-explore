package com.jungbauer.nhl.apidata;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class GameCenterLanding {
    private long id;
    private long season;
    private int gameType;
    private boolean limitedScoring;
    private String gameDate;
    private Venue venue;
    private Venue venueLocation;
    private String startTimeUTC;
    private String easternUTCOffset;
    private String venueUTCOffset;
    private String venueTimezone;
    private PeriodDescriptor periodDescriptor;
    private List<TvBroadcast> tvBroadcasts;
    private String gameState;
    private String gameScheduleState;
    private Team awayTeam;
    private Team homeTeam;
    private boolean shootoutInUse;
    private int maxPeriods;
    private int regPeriods;
    private boolean otInUse;
    private boolean tiesInUse;
    private Summary summary;
    private Clock clock;

    @Data
    public static class Venue {
        private String _default;
        public String getDefault() { return _default; }
        public void setDefault(String _default) { this._default = _default; }
    }

    @Data
    public static class PeriodDescriptor {
        private int number;
        private String periodType;
        private int maxRegulationPeriods;
    }

    @Data
    public static class TvBroadcast {
        private int id;
        private String market;
        private String countryCode;
        private String network;
        private int sequenceNumber;
    }

    @Data
    public static class Team {
        private int id;
        private Map<String, String> commonName;
        private String abbrev;
        private Map<String, String> placeName;
        private Map<String, String> placeNameWithPreposition;
        private Integer score;
        private Integer sog;
        private String logo;
        private String darkLogo;
    }

    @Data
    public static class Summary {
        private List<ScoringPeriod> scoring;
        private List<ShootoutAttempt> shootout;
        private List<Star> threeStars;
        private List<PenaltyPeriod> penalties;
    }

    @Data
    public static class ScoringPeriod {
        private PeriodDescriptor periodDescriptor;
        private List<Goal> goals;
    }

    @Data
    public static class Goal {
        private String situationCode;
        private int eventId;
        private String strength;
        private long playerId;
        private Map<String, String> firstName;
        private Map<String, String> lastName;
        private Map<String, String> name;
        private Map<String, String> teamAbbrev;
        private String headshot;
        private String highlightClipSharingUrl;
        private String highlightClipSharingUrlFr;
        private Long highlightClip;
        private Long highlightClipFr;
        private Long discreteClip;
        private Long discreteClipFr;
        private int goalsToDate;
        private int awayScore;
        private int homeScore;
        private Map<String, String> leadingTeamAbbrev;
        private String timeInPeriod;
        private String shotType;
        private String goalModifier;
        private List<Assist> assists;
        private String pptReplayUrl;
        private String homeTeamDefendingSide;
        private Boolean isHome;
    }

    @Data
    public static class Assist {
        private long playerId;
        private Map<String, String> firstName;
        private Map<String, String> lastName;
        private Map<String, String> name;
        private int assistsToDate;
        private int sweaterNumber;
    }

    @Data
    public static class ShootoutAttempt {
        private int sequence;
        private long playerId;
        private Map<String, String> teamAbbrev;
        private Map<String, String> firstName;
        private Map<String, String> lastName;
        private String shotType;
        private String result;
        private String headshot;
        private boolean gameWinner;
    }

    @Data
    public static class Star {
        private int star;
        private long playerId;
        private String teamAbbrev;
        private String headshot;
        private Map<String, String> name;
        private int sweaterNo;
        private String position;
        private int goals;
        private int assists;
        private int points;
    }

    @Data
    public static class PenaltyPeriod {
        private PeriodDescriptor periodDescriptor;
        private List<Penalty> penalties;
    }

    @Data
    public static class Penalty {
        private String timeInPeriod;
        private String type;
        private int duration;
        private Map<String, String> committedByPlayer;
        private Map<String, String> teamAbbrev;
        private Map<String, String> drawnBy;
        private String descKey;
        private Map<String, String> servedBy;
    }

    @Data
    public static class Clock {
        private String timeRemaining;
        private int secondsRemaining;
        private boolean running;
        private boolean inIntermission;
    }
}
