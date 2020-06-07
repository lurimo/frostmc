package br.com.frostmc.core.data.mysql.bukkit.punishments.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.punishments.Punishments;
import br.com.frostmc.core.util.enums.BanType;
import br.com.frostmc.core.util.enums.TemporaryType;

public class LoadingPunish {
	
	public static Punishments load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts_punish` WHERE (`nickname` = '" + player.getName() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String ipAddrees = resultSet.getString("ipAddrees");
				String date = resultSet.getString("date");
				BanType banType = BanType.valueOf(resultSet.getString("type"));
				TemporaryType temporaryType = TemporaryType.valueOf(resultSet.getString("temporary"));
				String staffer = resultSet.getString("staffer");
				String reason = resultSet.getString("reason");
				long expire = resultSet.getLong("expire");
				Punishments punishments = new Punishments(player, ipAddrees, date, banType, temporaryType, staffer, reason, expire);
				resultSet.close();
				preparedStatement.close();
				return punishments;
			} else {
				Punishments punishments = new Punishments(player);
				resultSet.close();
				preparedStatement.close();
				return punishments;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do seu banimento.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static Punishments load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts_punish` WHERE (`nickname` = '" + offlinePlayer.getName() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String ipAddrees = resultSet.getString("ipAddrees");
				String date = resultSet.getString("date");
				BanType banType = BanType.valueOf(resultSet.getString("type"));
				TemporaryType temporaryType = TemporaryType.valueOf(resultSet.getString("temporary"));
				String staffer = resultSet.getString("staffer");
				String reason = resultSet.getString("reason");
				long expire = resultSet.getLong("expire");
				Punishments punishments = new Punishments(offlinePlayer, ipAddrees, date, banType, temporaryType, staffer, reason, expire);
				resultSet.close();
				preparedStatement.close();
				return punishments;
			} else {
				Punishments punishments = new Punishments(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return punishments;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do seu banimento.");
			exception.printStackTrace();
		}
		return null;
	}

}
