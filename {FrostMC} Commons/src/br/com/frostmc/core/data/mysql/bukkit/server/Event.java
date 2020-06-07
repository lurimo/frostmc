package br.com.frostmc.core.data.mysql.bukkit.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.enums.BeneficyType;
import br.com.frostmc.core.util.enums.RunningType;

public class Event {

	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `server_event` WHERE (`running` = '" + RunningType.ACTIVED + "');");
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
	
	public boolean get(String beneficyType) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `server_event` WHERE (`running` = '" + RunningType.ACTIVED + "');");
			if (resultSet.next()) {
				return BeneficyType.valueOf(resultSet.getString(beneficyType).toUpperCase()) != null;
			}
			resultSet.close();
			statement.close();
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}
	
	public void create(String promotor, RunningType runningType, BeneficyType beneficyType) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_event` WHERE (`running` = '" + RunningType.ACTIVED + "');");
			preparedStatement.execute("INSERT INTO `server_event` (`promotor`, `type`, `running`) VALUES ('" + promotor + "', '" + runningType + "', '" + beneficyType + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_event` WHERE (`running` = '" + RunningType.ACTIVED + "');");
			preparedStatement.execute("DELETE FROM `server_event` WHERE (`running` = '" + RunningType.ACTIVED + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save(String promotor, String eventsType, RunningType runningType) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `server_event` SET `promotor` = '" + promotor + "', `type` = '" + eventsType + "', `running` = '" + runningType + "' WHERE (`running` = '" + RunningType.ACTIVED + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}