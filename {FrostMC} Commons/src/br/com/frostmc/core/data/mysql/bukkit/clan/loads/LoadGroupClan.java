package br.com.frostmc.core.data.mysql.bukkit.clan.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.clan.group.GroupClan;
import br.com.frostmc.core.util.enums.GroupClanType;

public class LoadGroupClan {
	
	public static GroupClan load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `clan_group` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				GroupClanType clanType = GroupClanType.valueOf(resultSet.getString("group"));
				String clan = resultSet.getString("nameClan");
				GroupClan participantesClan = new GroupClan(player, clanType, clan);
				resultSet.close();
				preparedStatement.close();
				return participantesClan;
			} else {
				GroupClan participantesClan = new GroupClan(player);
				resultSet.close();
				preparedStatement.close();
				return participantesClan;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	public static GroupClan load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `clan_group` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				GroupClanType clanType = GroupClanType.valueOf(resultSet.getString("group"));
				String clan = resultSet.getString("nameClan");
				GroupClan participantesClan = new GroupClan(offlinePlayer, clanType, clan);
				resultSet.close();
				preparedStatement.close();
				return participantesClan;
			} else {
				GroupClan participantesClan = new GroupClan(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return participantesClan;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

}
