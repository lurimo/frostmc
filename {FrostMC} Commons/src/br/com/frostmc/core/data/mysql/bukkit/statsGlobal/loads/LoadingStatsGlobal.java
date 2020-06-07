package br.com.frostmc.core.data.mysql.bukkit.statsGlobal.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.statsGlobal.StatsGlobal;
import br.com.frostmc.core.util.enums.LeagueType;

public class LoadingStatsGlobal {
	
	public static StatsGlobal load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_global` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				LeagueType ligasType = LeagueType.valueOf(resultSet.getString("league"));
				int xp = resultSet.getInt("xp");
				int moedas = resultSet.getInt("moedas");
				int fichas = resultSet.getInt("fichas");
				StatsGlobal statsGlobal = new StatsGlobal(player, ligasType, xp, moedas, fichas);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			} else {
				StatsGlobal statsGlobal = new StatsGlobal(player);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status global.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static StatsGlobal load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_global` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				LeagueType ligasType = LeagueType.valueOf(resultSet.getString("league"));
				int xp = resultSet.getInt("xp");
				int moedas = resultSet.getInt("moedas");
				int fichas = resultSet.getInt("fichas");
				StatsGlobal statsGlobal = new StatsGlobal(offlinePlayer, ligasType, xp, moedas, fichas);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			} else {
				StatsGlobal statsGlobal = new StatsGlobal(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status global.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static StatsGlobal load(UUID uuid) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_global` WHERE (`uuid` = '" + uuid + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				LeagueType ligasType = LeagueType.valueOf(resultSet.getString("league"));
				int xp = resultSet.getInt("xp");
				int moedas = resultSet.getInt("moedas");
				int fichas = resultSet.getInt("fichas");
				StatsGlobal statsGlobal = new StatsGlobal(uuid, ligasType, xp, moedas, fichas);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			} else {
				StatsGlobal statsGlobal = new StatsGlobal(uuid);
				resultSet.close();
				preparedStatement.close();
				return statsGlobal;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do status global.");
			exception.printStackTrace();
		}
		return null;
	}

}
