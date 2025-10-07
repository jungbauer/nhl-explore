package com.jungbauer.nhl.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PROTECTED)
    private Integer id;

    private Integer nhlId;
    private String teamName;
    private String commonName;
    private String placeName;
    private String abbrev;
    private String logo;
    private String darkLogo;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant updatedOn;
}
