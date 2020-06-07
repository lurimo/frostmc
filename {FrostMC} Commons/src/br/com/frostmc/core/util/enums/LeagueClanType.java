package br.com.frostmc.core.util.enums;

public enum LeagueClanType {
	
	BRONZE("§e", 0, 7),
	PRATA("§7", 2000, 6),
	OURO("§6", 4000, 5),
	PLATINA("§f", 6000, 4),
	DIAMANTE("§b", 8000, 3),
	CRISTAL("§3", 10000, 2),
	MESTRE("§4", 15000, 1);

	private String color;
	private int xp;
	private int order;
	
	private LeagueClanType(String color, int xp, int order) {
		this.color = color;
		this.xp = xp;
		this.order = order;
	}
	
	public String getColor() { return color; }
	public int getXp() { return xp; }
	public int getOrder() { return order; }
	
	public String fullName() {
		return getColor() + name().toUpperCase();
	}
	
	public static LeagueClanType getRanked(int score) {
		if (MESTRE.getXp() <= score) {
			return MESTRE;
		} else if (CRISTAL.getXp() <= score) {
			return CRISTAL;
		} else if (DIAMANTE.getXp() <= score) {
			return DIAMANTE;
		} else if (PLATINA.getXp() <= score) {
			return PLATINA;
		} else if (OURO.getXp() <= score) {
			return OURO;
		} else if (PRATA.getXp() <= score) {
			return PRATA;
		} else {
			return BRONZE;
		}
	}
	
}
