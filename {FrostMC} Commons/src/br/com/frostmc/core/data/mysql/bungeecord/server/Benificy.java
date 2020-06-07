package br.com.frostmc.core.data.mysql.bungeecord.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import br.com.frostmc.bungeecord.BungeeMain;
import br.com.frostmc.core.util.enums.BeneficyType;
import br.com.frostmc.core.util.enums.RunningType;

public class Benificy {

	public static boolean exists() {
		try {
			Statement statement = BungeeMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `server_beneficy` WHERE `running` = '" + RunningType.ACTIVED + "';");
			if (resultSet.next()) {
				return resultSet.getString("running") != null;
			}
			resultSet.close();
			statement.close();
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}
	
	public static boolean get(String beneficyType) {
		try {
			Statement statement = BungeeMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `server_beneficy` WHERE `running` = '" + RunningType.ACTIVED + "';");
			if (resultSet.next()) {
				return BeneficyType.valueOf(resultSet.getString(beneficyType	).toUpperCase()) != null;
			}
			resultSet.close();
			statement.close();
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}
	
	public static void create(RunningType runningType, BeneficyType beneficyType) {
		try {
			PreparedStatement preparedStatement = BungeeMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_beneficy` WHERE `running` = '" + RunningType.ACTIVED + "';");
			preparedStatement.execute("INSERT INTO `server_beneficy` (`running`, `beneficy`) VALUES ('" + runningType + "', '" + beneficyType + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BungeeMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_beneficy` WHERE `running` = '" + RunningType.ACTIVED + "';");
			preparedStatement.execute("DELETE FROM `server_beneficy` WHERE (`running` = '" + RunningType.ACTIVED + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public static void save(RunningType runningType, BeneficyType beneficyType) {
		try {
			Statement statement = BungeeMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `server_beneficy` SET `running` = '" + runningType + "', `beneficy` = '" + beneficyType + "' WHERE (`running` = '" + RunningType.ACTIVED + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
