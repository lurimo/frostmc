package br.com.frostmc.core.util.enums;

public enum StatsType {
	
	KILLS("Kills"),
	DEATHS("Deaths"),
	WINS("Wins"),
	XP("XP"),
	MOEDAS("Moedas"),
	FICHAS("Fichas"),
	VICTORY("Vitorias"),
	DEFEAT("Derrotas");
	
	private String name;
	
	private StatsType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
