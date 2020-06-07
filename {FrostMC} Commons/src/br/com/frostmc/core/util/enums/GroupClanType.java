package br.com.frostmc.core.util.enums;

public enum GroupClanType {
	
	MEMBRO("§7"),
	LINEPRINCIPAL("§3"),
	RECRUTER("§d"),
	MODERADOR("§5"),
	ADMIN("§c"),
	OWNER("§4");
	
	public String color;
	
	private GroupClanType(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}
	
	public String getFullName() {
		return color + "§l" + name().toUpperCase();
	}
}
