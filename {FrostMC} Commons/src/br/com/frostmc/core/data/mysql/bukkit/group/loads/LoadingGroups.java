package br.com.frostmc.core.data.mysql.bukkit.group.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.group.Groups;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.TemporaryType;

public class LoadingGroups {
	
	public static Groups load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String firstLogin = resultSet.getString("firstLogin");
				String lastLogin = resultSet.getString("lastLogin");
				String setBy = resultSet.getString("setBy");
				String ipAddrees = resultSet.getString("ipAddrees");
				GroupsType groupType = GroupsType.valueOf(resultSet.getString("group"));
				TemporaryType temporaryType = TemporaryType.valueOf(resultSet.getString("temporary"));
				long expire = resultSet.getLong("expire");
				Groups groups = new Groups(player, firstLogin, lastLogin, setBy, ipAddrees, groupType, temporaryType, expire);
				resultSet.close();
				preparedStatement.close();
				return groups;
			} else {
				Groups groups = new Groups(player);
				resultSet.close();
				preparedStatement.close();
				return groups;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do seu grupo.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static Groups load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String firstLogin = resultSet.getString("firstLogin");
				String lastLogin = resultSet.getString("lastLogin");
				String setBy = resultSet.getString("setBy");
				String ipAddrees = resultSet.getString("ipAddrees");
				GroupsType groupType = GroupsType.valueOf(resultSet.getString("group"));
				TemporaryType temporaryType = TemporaryType.valueOf(resultSet.getString("temporary"));
				long expire = resultSet.getLong("expire");
				Groups groups = new Groups(offlinePlayer, firstLogin, lastLogin, setBy, ipAddrees, groupType, temporaryType, expire);
				resultSet.close();
				preparedStatement.close();
				return groups;
			} else {
				Groups groups = new Groups(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return groups;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do seu grupo.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static Groups load(UUID uuid) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts` WHERE (`uuid` = '" + uuid + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String firstLogin = resultSet.getString("firstLogin");
				String lastLogin = resultSet.getString("lastLogin");
				String setBy = resultSet.getString("setBy");
				String ipAddrees = resultSet.getString("ipAddrees");
				GroupsType groupType = GroupsType.valueOf(resultSet.getString("group"));
				TemporaryType temporaryType = TemporaryType.valueOf(resultSet.getString("temporary"));
				long expire = resultSet.getLong("expire");
				Groups groups = new Groups(uuid, firstLogin, lastLogin, setBy, ipAddrees, groupType, temporaryType, expire);
				resultSet.close();
				preparedStatement.close();
				return groups;
			} else {
				Groups groups = new Groups(uuid);
				resultSet.close();
				preparedStatement.close();
				return groups;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do seu grupo.");
			exception.printStackTrace();
		}
		return null;
	}

}
