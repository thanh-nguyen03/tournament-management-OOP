package com.donghochanh.tournamentmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = TournamentTeamResult.TABLE_NAME)
public class TournamentTeamResult {
	protected static final String TABLE_NAME = "tournament_team_result";

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private Integer points;

	@Column(nullable = false)
	private Integer matchesPlayed;

	@Column(nullable = false)
	private Integer wins;

	@Column(nullable = false)
	private Integer draws;

	@Column(nullable = false)
	private Integer losses;

	@Column(nullable = false)
	private Integer goalsFor;

	@Column(nullable = false)
	private Integer goalsAgainst;

	@ManyToOne
	@JoinColumn(name = "tournament_id", nullable = false)
	private Tournament tournament;

	@ManyToOne
	@JoinColumn(name = "team_id", nullable = false)
	private Team team;
}
