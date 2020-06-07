package br.com.frostmc.core.data.mysql.bungeecord.group.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.frostmc.bungeecord.BungeeMain;
import br.com.frostmc.core.data.mysql.bungeecord.group.Groups;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.TemporaryType;

public class LoadingGroups {
	
	public static Groups load(String player) {
		try {
			PreparedStatement preparedStatement = BungeeMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts` WHERE (`nickname` = '" + player + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String playerString = resultSet.getString("nickname");
				String firstLogin = resultSet.getString("firstLogin");
				String lastLogin = resultSet.getString("lastLogin");
				String setBy = resultSet.getString("setBy");
				String ipAddrees = resultSet.getString("ipAddrees");
				Groups groups = null;
				if (playerString.equals(player)) {
					GroupsType groupType = GroupsType.valueOf(resultSet.getString("group"));
					TemporaryType temporaryType = TemporaryType.valueOf(resultSet.getString("temporary"));
					long expire = resultSet.getLong("expire");
					groups = new Groups(player, firstLogin, lastLogin, setBy, ipAddrees, groupType, temporaryType, expire);
				} else {
					groups = new Groups(player);
				}
				return groups;
			} else {
				Groups groups = new Groups(player);
				resultSet.close();
				preparedStatement.close();
				return groups;
			}
		} catch (Exception exception) {
			BungeeMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do seu grupo.");
			exception.printStackTrace();
		}
		return null;
	}
	
}