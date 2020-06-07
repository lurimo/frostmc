package br.com.frostmc.core.util.enums;

public enum LeagueType {
	
	PRIMARIO("§f", "⚌", 0, 12),
	APRENDIZ("§a", "☰", 1000, 11),
	AVANÇADO("§e", "☷", 2000, 10),
	BRONZE("§6", "✶", 5000, 9),
	PRATA("§7", "✷", 7500, 8),
	OURO("§6", "✸", 10000, 7),
	DIAMANTE("§b", "✹", 15000, 6),
	CRISTAL("§3", "✺", 20000, 5),
	ESMERALDA("§2", "✻", 25000, 4),
	LENDA("§4", "✾", 30000, 3),
	MESTRE("§c", "✿", 35000, 2),
	FROST("§b", "❄", 40000, 1);

	private String color;
	private String symbol;
	private int xp;
	private int order;
	
	private LeagueType(String color, String symbol, int xp, int order) {
		this.color = color;
		this.symbol = symbol;
		this.xp = xp;
		this.order = order;
	}
	
	public String getColor() { return color; }
	public String getSymbol() { return symbol; }
	public int getXp() { return xp; }
	public int getOrder() { return order; }
	
	public String fullName() {
		return getColor() + getSymbol() + " " + name().toUpperCase();
	}
	
	public static LeagueType getRanked(int score) {
		if (FROST.getXp() <= score) {
			return FROST;
		} else if (MESTRE.getXp() <= score) {
			return MESTRE;
		} else if (LENDA.getXp() <= score) {
			return LENDA;
		} else if (ESMERALDA.getXp() <= score) {
			return ESMERALDA;
		} else if (CRISTAL.getXp() <= score) {
			return CRISTAL;
		} else if (DIAMANTE.getXp() <= score) {
			return DIAMANTE;
		} else if (OURO.getXp() <= score) {
			return OURO;
		} else if (PRATA.getXp() <= score) {
			return PRATA;
		} else if (BRONZE.getXp() <= score) {
			return BRONZE;
		} else if (AVANÇADO.getXp() <= score) {
			return AVANÇADO;
		} else if (APRENDIZ.getXp() <= score) {
			return APRENDIZ;
		} else {
			return PRIMARIO;
		}
	}
	
}
