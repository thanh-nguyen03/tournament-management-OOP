package com.donghochanh.tournamentmanagement.exceptions;

public class TeamAlreadyAddedException extends RuntimeException {
	public TeamAlreadyAddedException(String message) {
		super(message);
	}
}
