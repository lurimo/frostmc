package br.com.frostmc.core.data.mysql.bukkit.statsHG;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;

public class StatsHG {
	
	public Player player;
	public OfflinePlayer offlinePlayer;
	public UUID uuid;
	public int kills, deaths, wins;
	
	public StatsHG(Player player, int kills, int deaths, int wins) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.kills = kills;
		this.deaths = deaths;
		this.wins = wins;
	}
	
	public StatsHG(OfflinePlayer offlinePlayer, int kills, int deaths, int wins) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.kills = kills;
		this.deaths = deaths;
		this.wins = wins;
	}
	
	public StatsHG(UUID uuid, int kills, int deaths, int wins) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.kills = kills;
		this.deaths = deaths;
		this.wins = wins;
	}
	
	public StatsHG(Player player) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.kills = 0;
		this.deaths = 0;
		this.wins = 0;
	}
	
	public StatsHG(OfflinePlayer offlinePlayer) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.kills = 0;
		this.deaths = 0;
		this.wins = 0;
	}
	
	public StatsHG(UUID uuid) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.kills = 0;
		this.deaths = 0;
		this.wins = 0;
	}
	
	public int getKills() { return kills; }
	public int getDeaths() { return deaths; }
	public int getWins() { return wins; }

	public void setKills(int kills) { this.kills = kills; }
	public void setDeaths(int deaths) { this.deaths = deaths; }
	public void setWins(int wins) { this.wins = wins; }
	
	public void addKills(int kills) { this.kills += kills; }
	public void addDeaths(int deaths) { this.deaths += deaths; }
	public void addWins(int wins) { this.wins += wins; }
	
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
	
	public void removeWins(int streak) {
		this.wins -= wins;
		if (this.wins < 0) {
			this.wins = 0;
		}
	}
	
	public void resetKills() { this.kills = 0; }
	public void resetDeaths() { this.deaths = 0; }
	public void resetWins() { this.wins = 0; }
	
	public void resetAll() {
		resetKills();
		resetDeaths();
		resetWins();
		save();
	}
	
	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_hg` WHERE `uuid` = '" + this.uuid.toString() + "';");
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
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_hg` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("INSERT INTO `players_stats_hg` (`uuid`, `nickname`, `kills`, `deaths`, `wins`) VALUES ('" + this.uuid.toString() + "', '" + String.valueOf(this.player.getName() != null ? this.player.getName() : this.offlinePlayer.getName()) + "', '" + getKills() + "', '" + getDeaths() + "', '" + getWins() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_hg` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("DELETE FROM `players_stats_hg` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `players_stats_hg` SET `kills` = '" + getKills() + "', `deaths` = '" + getDeaths() + "', `wins` = '" + getWins() + "' WHERE (`uuid` = '" + this.uuid.toString() + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}