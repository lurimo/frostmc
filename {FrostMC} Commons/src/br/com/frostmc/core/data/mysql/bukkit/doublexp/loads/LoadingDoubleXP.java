package br.com.frostmc.core.data.mysql.bukkit.doublexp.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.doublexp.DoubleXP;
import br.com.frostmc.core.util.enums.RunningType;

public class LoadingDoubleXP {

	public static DoubleXP load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts_doublexp` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				RunningType runningType = RunningType.valueOf(resultSet.getString("running"));
				int quantity = resultSet.getInt("quantity");
				long expire = resultSet.getLong("expire");
				DoubleXP doubleXP = new DoubleXP(player, runningType, quantity, expire);
				resultSet.close();
				preparedStatement.close();
				return doubleXP;
			} else {
				DoubleXP doubleXP = new DoubleXP(player);
				resultSet.close();
				preparedStatement.close();
				return doubleXP;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do seu doublexp.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static DoubleXP load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts_doublexp` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				RunningType runningType = RunningType.valueOf(resultSet.getString("running"));
				int quantity = resultSet.getInt("quantity");
				long expire = resultSet.getLong("expire");
				DoubleXP doubleXP = new DoubleXP(offlinePlayer, runningType, quantity, expire);
				resultSet.close();
				preparedStatement.close();
				return doubleXP;
			} else {
				DoubleXP doubleXP = new DoubleXP(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return doubleXP;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do seu doublexp.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static DoubleXP load(UUID uuid) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts_doublexp` WHERE (`uuid` = '" + uuid + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				RunningType runningType = RunningType.valueOf(resultSet.getString("running"));
				int quantity = resultSet.getInt("quantity");
				long expire = resultSet.getLong("expire");
				DoubleXP doubleXP = new DoubleXP(uuid, runningType, quantity, expire);
				resultSet.close();
				preparedStatement.close();
				return doubleXP;
			} else {
				DoubleXP doubleXP = new DoubleXP(uuid);
				resultSet.close();
				preparedStatement.close();
				return doubleXP;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do seu doublexp.");
			exception.printStackTrace();
		}
		return null;
	}

}
