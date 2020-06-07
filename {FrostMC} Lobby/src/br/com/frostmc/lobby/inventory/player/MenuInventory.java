package br.com.frostmc.lobby.inventory.player;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;

public class MenuInventory {
	
	public static final String titleMain = "§8§nVeja suas informações:";
	public static final String titleStats = "§8§nSeu status:";
	public static final String titlePerfil = "§8§nSeu perfil:";
	public static final String titlePreferencies = "§8§nSuas preferências:";
	
	public static void open(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 27, titleMain);

		inventory.setItem(12, new ItemBuilder().setNome("§aStatus").setMaterial(Material.BOOK).setLore(Arrays.asList("", "§fVeja seu status", "§fnos minigames.")).finalizar());
		inventory.setItem(13, new ItemBuilder().setNome("§aPerfil").setMaterial(Material.SKULL_ITEM).setCabeçaNome(player.getName()).setDurabilidade(3).setLore(Arrays.asList("", "§fVeja suas informações", "§fprivadas. Como senha,", "§fcodigo, etc.")).finalizar());
		inventory.setItem(14, new ItemBuilder().setNome("§aPreferências").setMaterial(Material.TRIPWIRE_HOOK).setLore(Arrays.asList("", "§fVejas suas preferencias.", "")).finalizar());
		
		player.openInventory(inventory);
	}
	
	@SuppressWarnings("deprecation")
	public static void openPreferencies(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 27, titlePreferencies);

		inventory.setItem(10, new ItemBuilder().setNome("§aConfigurações").setMaterial(Material.COMPASS).setLore(Arrays.asList("", "§fConfigure suas preferências", "§fdo seu jeito!", "")).finalizar());
		inventory.setItem(12, new ItemBuilder().setNome("§aParticulas").setMaterial(Material.GOLDEN_APPLE).setLore(Arrays.asList("", "§fSelecione um efeito", "§fpara colocar!", "")).finalizar());
		inventory.setItem(14, new ItemBuilder().setNome("§aCabeças").setMaterial(Material.getMaterial(58)).setLore(Arrays.asList("", "§fSelecione uma cabeça", "§fpara colocar!", "")).finalizar());
		inventory.setItem(17, new ItemBuilder().setNome("§cVoltar").setMaterial(Material.ARROW).glow().finalizar());
		
		player.openInventory(inventory);
	}
	
	@SuppressWarnings("deprecation")
	public static void openStatus(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 27, titleStats);

		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		
		inventory.setItem(11, new ItemBuilder().setNome("§aKit PvP").setMaterial(Material.DIAMOND_SWORD).setLore(Arrays.asList("", "§aKills: §f" + gamer.getKills_pvp(), "§aDeaths: §f" + gamer.getDeaths_pvp(), "§aKillStreak: §f" + gamer.getKillstreak_pvp())).finalizar());
		inventory.setItem(12, new ItemBuilder().setNome("§aHardcore Games").setMaterial(Material.MUSHROOM_SOUP).setLore(Arrays.asList("", "§aWins: §f" + gamer.getWins_hg(), "§aKills: §f" + gamer.getKills_pvp(), "§aDeaths: §f" + gamer.getDeaths_pvp())).finalizar());
		inventory.setItem(13, new ItemBuilder().setNome("§a1 vs. 1").setMaterial(Material.BLAZE_ROD).setLore(Arrays.asList("", "§aVitórias: §f" + gamer.getVictory_1v1(), "§aDerrotas: §f" + gamer.getDefeat_1v1(), "§aWinStreak: §f" + gamer.getWinstreak_1v1())).finalizar());
		inventory.setItem(14, new ItemBuilder().setNome("§aGladiator").setMaterial(Material.getMaterial(101)).setLore(Arrays.asList("", "§aVitórias: §f" + gamer.getVictory_gladiator(), "§aDerrotas: §f" + gamer.getDefeat_gladiator(), "§aWinStreak: §f" + gamer.getWinstreak_gladiator())).finalizar());
		inventory.setItem(17, new ItemBuilder().setNome("§cVoltar").setMaterial(Material.ARROW).glow().finalizar());
		
		player.openInventory(inventory);
	}
	
	public static void openPerfil(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 27, titlePerfil);

		AccountBukkit account = new AccountBukkit(player);
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		if (account.getClan().getGroupClan().getClan() != null) {
			List<String> desc = new ArrayList<>();
			desc.add("§f§l§m---------------------------");
			desc.add("§aClan: §f" + account.getClan().getGroupClan().getClan() + " §3- §f" + account.getClan().getStatusClan().getClanTag());
			desc.add("§aGrupo: " + account.getClan().getGroupClan().getClanType().getFullName());
			desc.add("");
			desc.add("§aLiga: " + account.getClan().getStatusClan().getLeagueType().fullName() + " " + account.getClan().getStatusClan().getRankPosition());
			desc.add("§aXP: §f" + NumberFormat.getInstance().format(account.getClan().getStatusClan().getXp()));
			desc.add("");
			desc.add("§aVitorias: §f" + NumberFormat.getInstance().format(account.getClan().getStatusClan().getVitorias()));
			desc.add("§aDerrotas: §f" + NumberFormat.getInstance().format(account.getClan().getStatusClan().getDerrotas()));
			desc.add("");
			desc.add("§aMembros:");
			account.getClan().getGroupClan().load();
			for (String membros : account.getClan().getGroupClan().membro) {
				desc.add("§8» §f" + membros);
			}
			desc.add("§f§l§m---------------------------");
			inventory.setItem(12, new ItemBuilder().setNome(account.getClan().getStatusClan().getLeagueType().getColor() + account.getClan().getGroupClan().getClan()).setMaterial(Material.PAPER).setLore(desc).finalizar());
		}
		
		inventory.setItem(13, new ItemBuilder().setNome("§7" + player.getName()).setMaterial(Material.SKULL_ITEM).setCabeçaNome(player.getName()).setDurabilidade(3).setLore(Arrays.asList(
				"",
				"§aGrupo: " + BukkitMain.getPermissionManager().getPlayerGroup(player).fullName(),
				"§aRegistro:",
				" §f- Senha: §7" + account.getAuthentication().getPassword() + (BukkitMain.getPermissions().isTrial(player) ? " §8- §7" + account.getAuthentication().getSecurityCode() : ""),
				" §f- Primeiro Login: §7" + gamer.getFirstLogin(),
				" §f- Ultimo Login: §7" + gamer.getLastLogin(),
				"")).finalizar());
		
		inventory.setItem(17, new ItemBuilder().setNome("§cVoltar").setMaterial(Material.ARROW).glow().finalizar());
		
		player.openInventory(inventory);
	}
	
}
