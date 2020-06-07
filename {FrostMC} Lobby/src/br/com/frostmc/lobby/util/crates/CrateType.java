package br.com.frostmc.lobby.util.crates;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;

public enum CrateType {

	IRON("Prata", Arrays.asList("§6§l5.000 MOEDAS", "§6§l10.000 MOEDAS", "§6§l15.000 MOEDAS", "§9§l1 DOUBLE XP", "§9§l2 DOUBLE XP", "§9§l3 DOUBLE XP", "§3§l1 FICHA", "§fVip §a§lLIGHT §8(§fUm dia§8)"), ChatColor.WHITE),
	GOLD("Ouro", Arrays.asList("§6§l10.000 MOEDAS", "§6§l20.000 MOEDAS", "§6§l30.000 MOEDAS", "§9§l2 DOUBLE XP", "§9§l4 DOUBLE XP", "§9§l6 DOUBLE XP", "§3§l2 FICHAS", "§fVip §3§lPRIME §8(§fQuatro dias§8)"), ChatColor.GOLD),
	DIAMOND("Diamante", Arrays.asList("§6§l15.000 MOEDAS", "§6§l30.000 MOEDAS", "§6§l45.000 MOEDAS", "§9§l3 DOUBLE XP", "§9§l6 DOUBLE XP", "§9§l9 DOUBLE XP", "§3§l3 FICHAS", "§fVip §b§lFROST §8(§fUma semana§8)"), ChatColor.AQUA);

	private String name;
	private ChatColor color;
	private List<String> description;

	CrateType(String name, List<String> description, ChatColor color) {
		this.name = name;
		this.color = color;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public ChatColor getColor() {
		return color;
	}

	public List<String> getDescription() {
		return description;
	}

	public static CrateType getType(String name) {
		return Arrays.asList(values()).stream().filter(crate -> crate.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

}
