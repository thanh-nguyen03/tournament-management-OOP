package com.donghochanh.tournamentmanagement.entity;

import com.donghochanh.tournamentmanagement.constants.MatchResult;
import com.donghochanh.tournamentmanagement.constants.MatchStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = Match.TABLE_NAME)
public class Match {
	protected static final String TABLE_NAME = "matches";

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private Integer number;

	@Column(nullable = false)
	private Integer team1Score;

	@Column(nullable = false)
	private Integer team2Score;

	@Column(nullable = false)
	private MatchResult matchResult;

	@Column(nullable = false)
	private MatchStatus matchStatus;

	@Column(nullable = false)
	private String stadium;

	@ManyToOne
	@JoinColumn(name = "team1_id", nullable = false)
	private Team team1;

	@ManyToOne
	@JoinColumn(name = "team2_id", nullable = false)
	private Team team2;

	@ManyToOne
	@JoinColumn(name = "tournament_id", nullable = false)
	private Tournament tournament;
}
