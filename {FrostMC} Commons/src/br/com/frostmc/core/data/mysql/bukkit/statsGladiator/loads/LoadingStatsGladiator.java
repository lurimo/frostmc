package br.com.frostmc.core.data.mysql.bukkit.statsGladiator.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.statsGladiator.StatsGladiator;

public class LoadingStatsGladiator {
	
	public static StatsGladiator load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_gladiator` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int victory = resultSet.getInt("victory");
				int defeat = resultSet.getInt("defeat");
				int winstreak = resultSet.getInt("winstreak");
				StatsGladiator statsGlobal = new StatsGladiator(player, victory, defeat, winstreak);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			} else {
				StatsGladiator statsGlobal = new StatsGladiator(player);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status gladiator.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static StatsGladiator load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_gladiator` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int victory = resultSet.getInt("victory");
				int defeat = resultSet.getInt("defeat");
				int winstreak = resultSet.getInt("winstreak");
				StatsGladiator statsGlobal = new StatsGladiator(offlinePlayer, victory, defeat, winstreak);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			} else {
				StatsGladiator statsGlobal = new StatsGladiator(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status gladiator.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static StatsGladiator load(UUID uuid) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_gladiator` WHERE (`uuid` = '" + uuid + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int victory = resultSet.getInt("victory");
				int defeat = resultSet.getInt("defeat");
				int winstreak = resultSet.getInt("winstreak");
				StatsGladiator statsGlobal = new StatsGladiator(uuid, victory, defeat, winstreak);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			} else {
				StatsGladiator statsGlobal = new StatsGladiator(uuid);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status gladiator.");
			exception.printStackTrace();
		}
		return null;
	}

}
