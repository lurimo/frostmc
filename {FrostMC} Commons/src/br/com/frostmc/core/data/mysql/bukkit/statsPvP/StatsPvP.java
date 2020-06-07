package br.com.frostmc.core.data.mysql.bukkit.statsPvP;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;

public class StatsPvP {
	
	public Player player;
	public OfflinePlayer offlinePlayer;
	public UUID uuid;
	public int kills, deaths, killstreak, lava_easy, lava_medium, lava_hard, lava_extreme;
	
	public StatsPvP(Player player, int kills, int deaths, int killstreak, int lava_easy, int lava_medium, int lava_hard, int lava_extreme) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.kills = kills;
		this.deaths = deaths;
		this.killstreak = killstreak;
		this.lava_easy = lava_easy;
		this.lava_medium = lava_medium;
		this.lava_hard = lava_hard;
		this.lava_extreme = lava_extreme;
	}
	
	public StatsPvP(OfflinePlayer offlinePlayer, int kills, int deaths, int killstreak, int lava_easy, int lava_medium, int lava_hard, int lava_extreme) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.kills = kills;
		this.deaths = deaths;
		this.killstreak = killstreak;
	}
	
	public StatsPvP(UUID uuid, int kills, int deaths, int killstreak, int lava_easy, int lava_medium, int lava_hard, int lava_extreme) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.kills = kills;
		this.deaths = deaths;
		this.killstreak = killstreak;
		this.lava_easy = lava_easy;
		this.lava_medium = lava_medium;
		this.lava_hard = lava_hard;
		this.lava_extreme = lava_extreme;
	}
	
	public StatsPvP(Player player) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.kills = 0;
		this.deaths = 0;
		this.killstreak = 0;
		this.lava_easy = 0;
		this.lava_medium = 0;
		this.lava_hard = 0;
		this.lava_extreme = 0;
	}
	
	public StatsPvP(OfflinePlayer offlinePlayer) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.kills = 0;
		this.deaths = 0;
		this.killstreak = 0;
		this.lava_easy = 0;
		this.lava_medium = 0;
		this.lava_hard = 0;
		this.lava_extreme = 0;
	}
	
	public StatsPvP(UUID uuid) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.kills = 0;
		this.deaths = 0;
		this.killstreak = 0;
		this.lava_easy = 0;
		this.lava_medium = 0;
		this.lava_hard = 0;
		this.lava_extreme = 0;
	}
	
	public int getKills() { return kills; }
	public int getDeaths() { return deaths; }
	public int getKillstreak() { return killstreak; }
	public int getLava_easy() { return lava_easy; }
	public int getLava_medium() { return lava_medium; }
	public int getLava_hard() { return lava_hard; }
	public int getLava_extreme() { return lava_extreme; }

	public void setKills(int kills) { this.kills = kills; }
	public void setDeaths(int deaths) { this.deaths = deaths; }
	public void setKillstreak(int killstreak) { this.killstreak = killstreak; }
	public void setLava_easy(int lava_easy) { this.lava_easy = lava_easy; }
	public void setLava_medium(int lava_medium) { this.lava_medium = lava_medium; }
	public void setLava_hard(int lava_hard) { this.lava_hard = lava_hard; }
	public void setLava_extreme(int lava_extreme) { this.lava_extreme = lava_extreme; }
	
	public void addKills(int kills) { this.kills += kills; }
	public void addDeaths(int deaths) { this.deaths += deaths; }
	public void addLava_easy(int lava_easy) { this.lava_easy += lava_easy; }
	public void addLava_medium(int lava_medium) { this.lava_medium += lava_medium; }
	public void addLava_hard(int lava_hard) { this.lava_hard += lava_hard; }
	public void addLava_extreme(int lava_extreme) { this.lava_extreme += lava_extreme; }
	public void addKillstreak(int killstreak) { 
		this.killstreak += killstreak; 
		if (this.killstreak % 10 == 0) {
			Bukkit.broadcastMessage("§4§lKILLSTREAK §fO jogador §a" + this.player.getName() + " §fconseguiu um §9§lKILLSTREAK §fde §a" + this.killstreak);
		}
	}
	
	public void removeKills(int kills) {
		this.kills -= kills;
		if (this.kills < 0) {
			this.kills = 0;
		}
	}
	
	public void removeDeaths(int deaths) {
		this.deaths -= deaths;
		if (this.deaths < 0) {
			this.deaths = 0;
		}
	}
	
	public void removeKillstreak(int streak) {
		this.killstreak -= streak;
		if (this.killstreak < 0) {
			this.killstreak = 0;
		}
	}
	
	public void resetKills() { this.kills = 0; }
	public void resetDeaths() { this.deaths = 0; }
	public void resetKillstreak() { this.killstreak = 0; }
	public void resetLava_easy() { this.lava_easy = 0; }
	public void resetLava_medium() { this.lava_medium = 0; }
	public void resetLava_hard() { this.lava_hard = 0; }
	public void resetLava_extreme() { this.lava_extreme = 0; }
	public void resetKillstreakAdversary(Player player) { 
		if (getKillstreak() != 0 && getKillstreak() > 10)
			Bukkit.broadcastMessage("§4§lKILLSTREAK §fO jogador §a" + player.getName() + " §facabou com o §9§lKILLSTREAK §fde §c" + getKillstreak() + " §fdo jogador §c" + this.player.getName());
		this.killstreak = 0; 
	}
	
	public void resetAll() {
		resetKills();
		resetDeaths();
		resetKillstreak();
		resetLava_easy();
		resetLava_medium();
		resetLava_hard();
		resetLava_extreme();
		save();
	}
	
	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_pvp` WHERE `uuid` = '" + this.uuid.toString() + "';");
			if (resultSet.next()) {
				return resultSet.getString("uuid") != null;
			}
			resultSet.close();
			statement.close();
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}
	
	public void create() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_pvp` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("INSERT INTO `players_stats_pvp` (`uuid`, `nickname`, `kills`, `deaths`, `killstreak`, `lava_easy`, `lava_medium`, `lava_hard`, `lava_extreme`) VALUES ('" + this.uuid.toString() + "', '" + String.valueOf(this.player.getName() != null ? this.player.getName() : this.offlinePlayer.getName()) + "', '" + getKills() + "', '" + getDeaths() + "', '" + getKillstreak() + "', '" + getLava_easy() + "', '" + getLava_medium() + "', '" + getLava_hard() + "', '" + getLava_extreme() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_pvp` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("DELETE FROM `players_stats_pvp` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `players_stats_pvp` SET `kills` = '" + getKills() + "', `deaths` = '" + getDeaths() + "', `killstreak` = '" + getKillstreak() + "', `lava_easy` = '" + getLava_easy() + "', `lava_medium` = '" + getLava_medium() + "', `lava_hard` = '" + getLava_hard() + "', `lava_extreme` = '" + getLava_extreme() + "' WHERE (`uuid` = '" + this.uuid.toString() + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}