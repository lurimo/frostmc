package br.com.frostmc.core.data.mysql.bukkit.authentication;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.enums.AuthType;

public class Authentication {
	
	public Player player;
	public OfflinePlayer offlinePlayer;
	public UUID uuid;
	public AuthType authType;
	public String ipAddrees, password, securityCode;
	
	public Authentication(Player player, AuthType authType, String ipAddrees, String password, String securityCode) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.authType = authType;
		this.ipAddrees = ipAddrees;
		this.password = password;
		this.securityCode = securityCode;
	}
	
	public Authentication(OfflinePlayer offlinePlayer, AuthType authType, String ipAddrees, String password, String securityCode) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.authType = authType;
		this.ipAddrees = ipAddrees;
		this.password = password;
		this.securityCode = securityCode;
	}
	
	public Authentication(UUID uuid, AuthType authType, String ipAddrees, String password, String securityCode) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.authType = authType;
		this.ipAddrees = ipAddrees;
		this.password = password;
		this.securityCode = securityCode;
	}
	
	public Authentication(Player player) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.authType = AuthType.NONREGISTERED;
		this.ipAddrees = "0.0.0.0";
		this.password = null;
		this.securityCode = "00000";
	}
	
	public Authentication(OfflinePlayer offlinePlayer) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.authType = AuthType.NONREGISTERED;
		this.ipAddrees = "0.0.0.0";
		this.password = null;
		this.securityCode = "00000";
	}
	
	public Authentication(UUID uuid) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.authType = AuthType.NONREGISTERED;
		this.ipAddrees = "0.0.0.0";
		this.password = null;
		this.securityCode = "00000";
	}
	
	public void setAuthType(AuthType authType) { this.authType = authType; }
	public void setIpAddrees(String ipAddrees) { this.ipAddrees = ipAddrees; }
	public void setPassword(String password) { this.password = password; }
	public void setSecurityCode() { 
		Integer integer = Integer.valueOf((int) (10000 + Math.random() * 90000));
		this.securityCode = "#" + integer; 
	}
	
	public AuthType getAuthType() { return authType; }
	public String getIpAddrees() { return ipAddrees; }
	public String getPassword() { return password; }
	public String getSecurityCode() { return securityCode; }
	
	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_authentication` WHERE `uuid` = '" + this.uuid.toString() + "';");
			if (resultSet.next()) {
				return resultSet.getString("uuid") != null;
			}
			resultSet.close();
			statement.close();
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}
	
	public void create() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_authentication` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("INSERT INTO `players_authentication` (`uuid`, `nickname`, `status`, `ipAddrees`, `password`, `securityCode`) VALUES ('" + this.uuid.toString() + "', '" + String.valueOf(this.player.getName() != null ? this.player.getName() : this.offlinePlayer.getName()) + "', '" + getAuthType() + "', '" + getIpAddrees() + "', '" + getPassword() + "', '" + getSecurityCode() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_authentication` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("DELETE FROM `players_authentication` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `players_authentication` SET `status` = '" + getAuthType() + "', `ipAddrees` = '" + getIpAddrees() + "', `password` = '" + getPassword() + "', `securityCode` = '" + getSecurityCode() + "' WHERE (`uuid` = '" + this.uuid.toString() + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	
}