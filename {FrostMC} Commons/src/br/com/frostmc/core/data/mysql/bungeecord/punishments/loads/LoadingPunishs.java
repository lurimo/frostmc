package br.com.frostmc.core.data.mysql.bungeecord.punishments.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.frostmc.bungeecord.BungeeMain;
import br.com.frostmc.core.data.mysql.bungeecord.punishments.Punishs;
import br.com.frostmc.core.util.enums.BanType;
import br.com.frostmc.core.util.enums.TemporaryType;

public class LoadingPunishs {
	
	public static Punishs load(String player) {
		try {
			PreparedStatement preparedStatement = BungeeMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts_punish` WHERE (`nickname` = '" + player + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String ipAddrees = resultSet.getString("ipAddrees");
				String date = resultSet.getString("date");
				BanType banType = BanType.valueOf(resultSet.getString("type"));
				TemporaryType temporaryType = TemporaryType.valueOf(resultSet.getString("temporary"));
				String staffer = resultSet.getString("staffer");
				String reason = resultSet.getString("reason");
				long expire = resultSet.getLong("expire");
				Punishs punishments = new Punishs(player, ipAddrees, date, banType, temporaryType, staffer, reason, expire);
				resultSet.close();
				preparedStatement.close();
				return punishments;
			} else {
				Punishs punishments = new Punishs(player);
				resultSet.close();
				preparedStatement.close();
				return punishments;
			}
		} catch (Exception exception) {
			BungeeMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do seu banimento.");
			exception.printStackTrace();
		}
		return null;
	}

}
