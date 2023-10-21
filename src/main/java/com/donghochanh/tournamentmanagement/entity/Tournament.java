package com.donghochanh.tournamentmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = Tournament.TABLE_NAME)
public class Tournament {
	protected static final String TABLE_NAME = "tournament";

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer prize;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@ManyToMany
	@JoinTable(
		name = "tournament_team",
		joinColumns = @JoinColumn(name = "tournament_id"),
		inverseJoinColumns = @JoinColumn(name = "team_id"),
		uniqueConstraints = @UniqueConstraint(columnNames = {"tournament_id", "team_id"})
	)
	private List<Team> teams;
}
