package br.com.frostmc.core.data.mysql.bukkit.statsPvP.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.statsPvP.StatsPvP;

public class LoadingStatsPvP {
	
	public static StatsPvP load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_pvp` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int kills = resultSet.getInt("kills");
				int deaths = resultSet.getInt("deaths");
				int killstreak = resultSet.getInt("killstreak");
				int lava_easy = resultSet.getInt("lava_easy");
				int lava_medium = resultSet.getInt("lava_medium");
				int lava_hard = resultSet.getInt("lava_hard");
				int lava_extreme = resultSet.getInt("lava_extreme");
				StatsPvP statsPvP = new StatsPvP(player, kills, deaths, killstreak, lava_easy, lava_medium, lava_hard, lava_extreme);
				resultSet.close();
				preparedStatement.close();
				return statsPvP;
			} else {
				StatsPvP statsPvP = new StatsPvP(player);
				resultSet.close();
				preparedStatement.close();
				return statsPvP;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status pvp.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static StatsPvP load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_pvp` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int kills = resultSet.getInt("kills");
				int deaths = resultSet.getInt("deaths");
				int killstreak = resultSet.getInt("killstreak");
				int lava_easy = resultSet.getInt("lava_easy");
				int lava_medium = resultSet.getInt("lava_medium");
				int lava_hard = resultSet.getInt("lava_hard");
				int lava_extreme = resultSet.getInt("lava_extreme");
				StatsPvP statsPvP = new StatsPvP(offlinePlayer, kills, deaths, killstreak, lava_easy, lava_medium, lava_hard, lava_extreme);
				resultSet.close();
				preparedStatement.close();
				return statsPvP;
			} else {
				StatsPvP statsPvP = new StatsPvP(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return statsPvP;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status pvp.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static StatsPvP load(UUID uuid) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_pvp` WHERE (`uuid` = '" + uuid.toString() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int kills = resultSet.getInt("kills");
				int deaths = resultSet.getInt("deaths");
				int killstreak = resultSet.getInt("killstreak");
				int lava_easy = resultSet.getInt("lava_easy");
				int lava_medium = resultSet.getInt("lava_medium");
				int lava_hard = resultSet.getInt("lava_hard");
				int lava_extreme = resultSet.getInt("lava_extreme");
				StatsPvP statsPvP = new StatsPvP(uuid, kills, deaths, killstreak, lava_easy, lava_medium, lava_hard, lava_extreme);
				resultSet.close();
				preparedStatement.close();
				return statsPvP;
			} else {
				StatsPvP statsPvP = new StatsPvP(uuid);
				resultSet.close();
				preparedStatement.close();
				return statsPvP;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status pvp.");
			exception.printStackTrace();
		}
		return null;
	}

}
