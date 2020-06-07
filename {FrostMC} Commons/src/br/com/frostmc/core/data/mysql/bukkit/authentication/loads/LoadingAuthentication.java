package br.com.frostmc.core.data.mysql.bukkit.authentication.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.authentication.Authentication;
import br.com.frostmc.core.util.enums.AuthType;

public class LoadingAuthentication {

	public static Authentication load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_authentication` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				AuthType authType = AuthType.valueOf(resultSet.getString("status"));
				String ipAddrees = resultSet.getString("ipAddrees");
				String password = resultSet.getString("password");
				String securityCode = resultSet.getString("securityCode");
				Authentication authentication = new Authentication(player, authType, ipAddrees, password, securityCode);
				resultSet.close();
				preparedStatement.close();
				return authentication;
			} else {
				Authentication authentication = new Authentication(player);
				resultSet.close();
				preparedStatement.close();
				return authentication;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações da blacklist");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static Authentication load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_authentication` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				AuthType authType = AuthType.valueOf(resultSet.getString("status"));
				String ipAddrees = resultSet.getString("ipAddrees");
				String password = resultSet.getString("password");
				String securityCode = resultSet.getString("securityCode");
				Authentication authentication = new Authentication(offlinePlayer, authType, ipAddrees, password, securityCode);
				resultSet.close();
				preparedStatement.close();
				return authentication;
			} else {
				Authentication authentication = new Authentication(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return authentication;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações da blacklist");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static Authentication load(UUID uuid) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_authentication` WHERE (`uuid` = '" + uuid + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				AuthType authType = AuthType.valueOf(resultSet.getString("status"));
				String ipAddrees = resultSet.getString("ipAddrees");
				String password = resultSet.getString("password");
				String securityCode = resultSet.getString("securityCode");
				Authentication authentication = new Authentication(uuid, authType, ipAddrees, password, securityCode);
				resultSet.close();
				preparedStatement.close();
				return authentication;
			} else {
				Authentication authentication = new Authentication(uuid);
				resultSet.close();
				preparedStatement.close();
				return authentication;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações da blacklist");
			exception.printStackTrace();
		}
		return null;
	}

}
