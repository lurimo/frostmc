package br.com.frostmc.core.data.mysql.bukkit.stats1v1.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.stats1v1.Stats1v1;

public class LoadingStats1v1 {
	
	public static Stats1v1 load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_1v1` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int victory = resultSet.getInt("victory");
				int defeat = resultSet.getInt("defeat");
				int winstreak = resultSet.getInt("winstreak");
				Stats1v1 statsGlobal = new Stats1v1(player, victory, defeat, winstreak);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			} else {
				Stats1v1 statsGlobal = new Stats1v1(player);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status 1v1.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static Stats1v1 load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_1v1` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int victory = resultSet.getInt("victory");
				int defeat = resultSet.getInt("defeat");
				int winstreak = resultSet.getInt("winstreak");
				Stats1v1 statsGlobal = new Stats1v1(offlinePlayer, victory, defeat, winstreak);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			} else {
				Stats1v1 statsGlobal = new Stats1v1(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status 1v1.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static Stats1v1 load(UUID uuid) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_1v1` WHERE (`uuid` = '" + uuid + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int victory = resultSet.getInt("victory");
				int defeat = resultSet.getInt("defeat");
				int winstreak = resultSet.getInt("winstreak");
				Stats1v1 statsGlobal = new Stats1v1(uuid, victory, defeat, winstreak);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			} else {
				Stats1v1 statsGlobal = new Stats1v1(uuid);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status 1v1.");
			exception.printStackTrace();
		}
		return null;
	}

}
