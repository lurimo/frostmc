package br.com.frostmc.lobby.util.holograms;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.holograms.Hologram;
import br.com.frostmc.core.util.enums.LeagueType;
import br.com.frostmc.lobby.util.npc.NpcManager;

public class HologramManager {
	
	public static int integer = 0;
	
	@SuppressWarnings("deprecation")
	public void next() {
		integer++;
		if (integer == 30) {
			setHolograms();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!BukkitMain.getPermissions().isModPlus(player)) {
					player.sendMessage("§a§lHOLOGRAMA §fOs hologramas foram atualizados!");
					return;
				}
			}
			integer = 0;
		}
	}

	private static final List<PlayerTop> topScore = new ArrayList<>();
	public Hologram leagueHologram, mob_zombie_Hologram, mob_skeleton_Hologram, mob_villager_Hologram, mob_iron_golem_Hologram, onEntryHologram;

	public boolean initialize() {
		return updateHolograms();
	}

	public boolean updateHolograms() {
		topScore.clear();
		loadList(topScore);
		return setHolograms();
	}
	
	public void loadList(List<PlayerTop> list) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_global` ORDER BY `xp` DESC LIMIT 10;");
			int x = 0;
			while (resultSet.next()) {
				x++;
				list.add(new PlayerTop(x, resultSet.getString("nickname"), resultSet.getInt("xp")));
			}
			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean setHolograms() {
		topScore.clear();
		loadList(topScore);

		if (leagueHologram != null) {
			leagueHologram.remove();
			leagueHologram = null;
		}
		Location leagueLocation = new Location(Bukkit.getServer().getWorld("world"), -5.5, 174.5, 12.5);
		leagueHologram = new Hologram("§b§lTOP 10 RANKING", leagueLocation, true);
		
		leagueHologram.addLine("§fO rank é organizado de acordo");
		leagueHologram.addLine("§fcom o jogador com maior XP");
		leagueHologram.addLine(" ");

		int i = 1;

		for (PlayerTop scoreTop : topScore) {
			if (scoreTop != null) {
				LeagueType league = LeagueType.getRanked(scoreTop.getTop());
				String color = i == 1 ? "§a" : i == 2 ? "§e" : i == 3 ? "§c" : "§7";
				String name = (color.equals("§7") ? "§f" : color) + scoreTop.getName();

				String line = color + scoreTop.getId() + "º " + name + " §f| " + league.fullName() + " §3- §b" + NumberFormat.getInstance().format(scoreTop.getTop()) + " XP";

				leagueHologram.addLine(line);
				i++;
			}
		}
		

		for (Player player : Bukkit.getOnlinePlayers()) {
			leagueHologram.hide(player);
			leagueHologram.show(player);

		}

		/////////////////////////////////
		
		if (mob_zombie_Hologram != null) {
			mob_zombie_Hologram.remove();
			mob_zombie_Hologram = null;
		}
		
		if (mob_skeleton_Hologram != null) {
			mob_skeleton_Hologram.remove();
			mob_skeleton_Hologram = null;
		}
		
		if (mob_villager_Hologram != null) {
			mob_villager_Hologram.remove();
			mob_villager_Hologram = null;
		}
		
		if (mob_iron_golem_Hologram != null) {
			mob_iron_golem_Hologram.remove();
			mob_iron_golem_Hologram = null;
		}
		
		
		Location mob_zombie_Location = new Location(Bukkit.getServer().getWorld("world"), -0.5, 172.6, -8.5);
		mob_zombie_Hologram = new Hologram("§bKitPvP", mob_zombie_Location, true);
		mob_zombie_Hologram.addLine("§fClique para entrar.");
		mob_zombie_Location = new Location(Bukkit.getServer().getWorld("world"), -0.5, 171.5, -8.5);
		NpcManager.spawnZombie(mob_zombie_Location, "§1");
		
		Location mob_skeleton_Location = new Location(Bukkit.getServer().getWorld("world"), -3.5, 172.6, -8.5);
		mob_skeleton_Hologram = new Hologram("§bHardcoreGames", mob_skeleton_Location, true);
		mob_skeleton_Hologram.addLine("§fClique para entrar.");
		mob_skeleton_Location = new Location(Bukkit.getServer().getWorld("world"), -3.5, 171.5, -8.5);
		NpcManager.spawnSkeleton(mob_skeleton_Location, "§2");
		
		Location mob_villager_Location = new Location(Bukkit.getServer().getWorld("world"), -6.5, 172.6, -8.5);
		mob_villager_Hologram = new Hologram("§b1 vs 1", mob_villager_Location, true);
		mob_villager_Hologram.addLine("§fClique para entrar.");
		mob_villager_Location = new Location(Bukkit.getServer().getWorld("world"), -6.5, 171.5, -8.5);
		NpcManager.spawnPigZombie(mob_villager_Location, "§3");
	
		Location mob_iron_golem__Location = new Location(Bukkit.getServer().getWorld("world"), -9.5, 173.3, -8.5);
		mob_iron_golem_Hologram = new Hologram("§bGladiator", mob_iron_golem__Location, true);
		mob_iron_golem_Hologram.addLine("§fClique para entrar.");
		mob_iron_golem__Location = new Location(Bukkit.getServer().getWorld("world"), -9.5, 171.5, -8.5);
		NpcManager.spawnIron_golem(mob_iron_golem__Location, "§4");
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			mob_zombie_Hologram.hide(player);
			mob_zombie_Hologram.show(player);
			
			mob_skeleton_Hologram.hide(player);
			mob_skeleton_Hologram.show(player);
			
			mob_villager_Hologram.hide(player);
			mob_villager_Hologram.show(player);
			
			mob_iron_golem_Hologram.hide(player);
			mob_iron_golem_Hologram.show(player);
		}
		
		/////////////////////////////////
		
		if (onEntryHologram != null) {
			onEntryHologram.remove();
			onEntryHologram = null;
		}
		Location onEntryLocation = new Location(Bukkit.getServer().getWorld("world"), -2.5, 171.6, 0.5);
		onEntryHologram = new Hologram("§b§lFrost§f§lNetwork", onEntryLocation, true);
		onEntryHologram.addLine(" ");
		onEntryHologram.addLine("§fSeja bem-vindo ao §3§lLobby§7!");
		onEntryHologram.addLine("§fUse a bússola ou clique em um");
		onEntryHologram.addLine("§fmob para entrar em algum servidor.");


		for (Player player : Bukkit.getOnlinePlayers()) {
			onEntryHologram.hide(player);
			onEntryHologram.show(player);
		}

		return true;

	}
}