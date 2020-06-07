package br.com.frostmc.core.data.mysql.bukkit.stats1v1;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;

public class Stats1v1 {
	
	public Player player;
	public OfflinePlayer offlinePlayer;
	public UUID uuid;
	public int victory, defeat, winstreak;
	
	public Stats1v1(Player player, int victory, int defeat, int winstreak) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.victory = victory;
		this.defeat = defeat;
		this.winstreak = winstreak;
	}
	
	public Stats1v1(OfflinePlayer offlinePlayer, int victory, int defeat, int winstreak) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.victory = victory;
		this.defeat = defeat;
		this.winstreak = winstreak;
	}
	
	public Stats1v1(UUID uuid, int victory, int defeat, int winstreak) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.victory = victory;
		this.defeat = defeat;
		this.winstreak = winstreak;
	}
	
	public Stats1v1(Player player) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.victory = 0;
		this.defeat = 0;
		this.winstreak = 0;
	}
	
	public Stats1v1(OfflinePlayer offlinePlayer) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.victory = 0;
		this.defeat = 0;
		this.winstreak = 0;
	}
	
	public Stats1v1(UUID uuid) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.victory = 0;
		this.defeat = 0;
		this.winstreak = 0;
	}
	
	public int getVictory() { return victory; }
	public int getDefeat() { return defeat; }
	public int getWinstreak() { return winstreak; }
	
	public void setVictory(int victory) { this.victory = victory; }
	public void setDefeat(int defeat) { this.defeat = defeat; }
	public void setWinstreak(int winstreak) { this.winstreak = winstreak; }
	
	public void addVictory(int victory) { this.victory += victory; }
	public void addDefeat(int defeat) { this.defeat += defeat; }
	public void addWinstreak(int winstreak) { this.winstreak += winstreak; }
	
	public void removeVictory(int victory) {
		this.victory -= victory;
		if (this.victory < 0) {
			this.victory = 0;
		}
	}
	
	public void removeDefeat(int defeat) {
		this.defeat -= defeat;
		if (this.defeat < 0) {
			this.defeat = 0;
		}
	}
	
	public void removeWinstreak(int winstreak) {
		this.winstreak -= winstreak;
		if (this.winstreak < 0) {
			this.winstreak = 0;
		}
	}

	
	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_1v1` WHERE `uuid` = '" + this.uuid.toString() + "';");
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
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_1v1` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("INSERT INTO `players_stats_1v1` (`uuid`, `nickname`, `victory`, `defeat`, `winstreak`) VALUES ('" + this.uuid.toString() + "', '" + String.valueOf(this.player.getName() != null ? this.player.getName() : this.offlinePlayer.getName()) + "', '" + getVictory() + "', '" + getDefeat() + "', '" + getWinstreak() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_1v1` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("DELETE FROM `players_stats_1v1` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `players_stats_1v1` SET `victory` = '" + getVictory() + "', `defeat` = '" + getDefeat() + "', `winstreak` = '" + getWinstreak() + "' WHERE (`uuid` = '" + this.uuid.toString() + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}