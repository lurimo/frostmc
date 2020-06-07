package br.com.frostmc.core.data.mysql.bukkit.clan.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.clan.stats.StatusClan;
import br.com.frostmc.core.util.enums.LeagueClanType;

public class LoadStatusClan {
	
	public static StatusClan load(String clan) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `clan_status` WHERE (`nameClan` = '" + clan + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String owner = resultSet.getString("owner");
				String clanName = resultSet.getString("nameClan");
				String clanTag = resultSet.getString("tagClan");
				int xp = resultSet.getInt("xp");
				LeagueClanType leagueClanType = LeagueClanType.valueOf(resultSet.getString("league"));
				int vitorias = resultSet.getInt("vitorias");
				int derrotas = resultSet.getInt("derrotas");
				StatusClan statusClan = new StatusClan(owner, clanName, clanTag, leagueClanType, xp, vitorias, derrotas);
				resultSet.close();
				preparedStatement.close();
				return statusClan;
			} else {
				StatusClan statusClan = new StatusClan();
				resultSet.close();
				preparedStatement.close();
				return statusClan;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

}
