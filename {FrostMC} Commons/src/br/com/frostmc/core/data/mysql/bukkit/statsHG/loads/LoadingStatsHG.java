package br.com.frostmc.core.data.mysql.bukkit.statsHG.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.statsHG.StatsHG;

public class LoadingStatsHG {
	
	public static StatsHG load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_hg` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int kills = resultSet.getInt("kills");
				int deaths = resultSet.getInt("deaths");
				int wins = resultSet.getInt("wins");
				StatsHG statsHG = new StatsHG(player, kills, deaths, wins);
				resultSet.close();
				preparedStatement.close();
				return statsHG;
			} else {
				StatsHG statsHG = new StatsHG(player);
				resultSet.close();
				preparedStatement.close();
				return statsHG;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status hg.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static StatsHG load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_hg` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int kills = resultSet.getInt("kills");
				int deaths = resultSet.getInt("deaths");
				int wins = resultSet.getInt("wins");
				StatsHG statsHG = new StatsHG(offlinePlayer, kills, deaths, wins);
				resultSet.close();
				preparedStatement.close();
				return statsHG;
			} else {
				StatsHG statsHG = new StatsHG(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return statsHG;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status hg.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static StatsHG load(UUID uuid) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_hg` WHERE (`uuid` = '" + uuid.toString() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int kills = resultSet.getInt("kills");
				int deaths = resultSet.getInt("deaths");
				int wins = resultSet.getInt("wins");
				StatsHG statsHG = new StatsHG(uuid, kills, deaths, wins);
				resultSet.close();
				preparedStatement.close();
				return statsHG;
			} else {
				StatsHG statsHG = new StatsHG(uuid);
				resultSet.close();
				preparedStatement.close();
				return statsHG;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status hg.");
			exception.printStackTrace();
		}
		return null;
	}

}
