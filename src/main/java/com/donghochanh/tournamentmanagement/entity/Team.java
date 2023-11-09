package com.donghochanh.tournamentmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = Team.TABLE_NAME)
public class Team {
	protected static final String TABLE_NAME = "team";

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String stadium;

	@Column(nullable = false)
	private String country;

	@ManyToMany(mappedBy = "teams")
	private List<Tournament> tournaments;

	@OneToMany(mappedBy = "team1")
	private List<Match> home;

	@OneToMany(mappedBy = "team2")
	private List<Match> away;

	@OneToMany(mappedBy = "team")
	private List<TournamentTeamResult> tournamentTeamResults;
}
