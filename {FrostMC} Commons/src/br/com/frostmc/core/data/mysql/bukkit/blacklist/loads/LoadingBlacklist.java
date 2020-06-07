package br.com.frostmc.core.data.mysql.bukkit.blacklist.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.blacklist.Blacklist;
import br.com.frostmc.core.util.enums.TemporaryType;

public class LoadingBlacklist {

	public static Blacklist load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_event_blacklist` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String staffer = resultSet.getString("staffer");
				String ipAddrees = resultSet.getString("ipAddrees");
				TemporaryType temporaryType = TemporaryType.valueOf(resultSet.getString("temporary"));
				long expire = resultSet.getLong("expire");
				Blacklist blacklist = new Blacklist(player, staffer, ipAddrees, temporaryType, expire);
				resultSet.close();
				preparedStatement.close();
				return blacklist;
			} else {
				Blacklist blacklist = new Blacklist(player);
				resultSet.close();
				preparedStatement.close();
				return blacklist;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações da blacklist");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static Blacklist load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_event_blacklist` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String staffer = resultSet.getString("staffer");
				String ipAddrees = resultSet.getString("ipAddrees");
				TemporaryType temporaryType = TemporaryType.valueOf(resultSet.getString("temporary"));
				long expire = resultSet.getLong("expire");
				Blacklist blacklist = new Blacklist(offlinePlayer, staffer, ipAddrees, temporaryType, expire);
				resultSet.close();
				preparedStatement.close();
				return blacklist;
			} else {
				Blacklist blacklist = new Blacklist(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return blacklist;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações da blacklist.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static Blacklist load(UUID uuid) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_event_blacklist` WHERE (`uuid` = '" + uuid + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String staffer = resultSet.getString("staffer");
				String ipAddrees = resultSet.getString("ipAddrees");
				TemporaryType temporaryType = TemporaryType.valueOf(resultSet.getString("temporary"));
				long expire = resultSet.getLong("expire");
				Blacklist blacklist = new Blacklist(uuid, staffer, ipAddrees, temporaryType, expire);
				resultSet.close();
				preparedStatement.close();
				return blacklist;
			} else {
				Blacklist blacklist = new Blacklist(uuid);
				resultSet.close();
				preparedStatement.close();
				return blacklist;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações da blacklist.");
			exception.printStackTrace();
		}
		return null;
	}

}
