package br.com.frostmc.core.data.mysql.bukkit.doublexp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.enums.RunningType;

public class DoubleXP {
	
	public Player player;
	public OfflinePlayer offlinePlayer;
	public UUID uuid;
	public RunningType runningType;
	public int quantity;
	public long expire;
	
	public DoubleXP(Player player, RunningType runningType, int quantity, long expire) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.runningType = runningType;
		this.quantity = quantity;
		this.expire = expire;
	}
	
	public DoubleXP(OfflinePlayer offlinePlayer, RunningType runningType, int quantity, long expire) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.runningType = runningType;
		this.quantity = quantity;
		this.expire = expire;
	}
	
	public DoubleXP(UUID uuid, RunningType runningType, int quantity, long expire) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.runningType = runningType;
		this.quantity = quantity;
		this.expire = expire;
	}
	
	public DoubleXP(Player player) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.runningType = RunningType.DESACTIVED;
		this.quantity = 0;
		this.expire = 0L;
	}
	
	public DoubleXP(OfflinePlayer offlinePlayer) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.runningType = RunningType.DESACTIVED;
		this.quantity = 0;
		this.expire = 0L;
	}
	
	public DoubleXP(UUID uuid) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.runningType = RunningType.DESACTIVED;
		this.quantity = 0;
		this.expire = 0L;
	}
	
	public RunningType getRunningType() { return runningType; }
	public int getQuantity() { return quantity; }
	public long getExpire() { return expire; }
	
	public void setRunningType(RunningType runningType) { this.runningType = runningType; }
	public void addQuantity(int quantity) { this.quantity += quantity; }
	public void setExpire(long expire) { this.expire = expire; }
	
	public void removeQuantity(int quantity) {
		this.quantity -= quantity;
		if (this.quantity < 0) {
			this.quantity = 0;
		}
	}
	
	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_accounts_doublexp` WHERE `uuid` = '" + this.uuid.toString() + "';");
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
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts_doublexp` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("INSERT INTO `players_accounts_doublexp` (`uuid`, `nickname`, `running`, `quantity`, `expire`) VALUES ('" + this.uuid.toString() + "', '" + String.valueOf(this.player.getName() != null ? this.player.getName() : this.offlinePlayer.getName()) + "',  '" + getRunningType() + "', '" + getQuantity() + "', '" + getExpire() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts_doublexp` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("DELETE FROM `players_accounts_doublexp` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `players_accounts_doublexp` SET `running` = '" + getRunningType() + "', `quantity` = '" + getQuantity() + "', `expire` = '" + getExpire() + "' WHERE (`uuid` = '" + this.uuid.toString() + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}