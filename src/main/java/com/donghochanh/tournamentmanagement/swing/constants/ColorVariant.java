package com.donghochanh.tournamentmanagement.swing.constants;

public enum ColorVariant {
	PRIMARY("#00A76F"),
	SECONDARY("#8E33FF"),
	SUCCESS("#22C55E"),
	ERROR("#FF5630"),
	WARNING("#FFAB00"),
	INFO("#00B8D9");

	private final String color;

	ColorVariant(String color) {
		this.color = color;
	}

	public String getColor() {
		return this.color;
	}
}
