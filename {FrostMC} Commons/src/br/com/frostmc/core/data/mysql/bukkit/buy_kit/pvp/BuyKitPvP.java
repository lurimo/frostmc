package br.com.frostmc.core.data.mysql.bukkit.buy_kit.pvp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;

public class BuyKitPvP {
	
	public Player player;
	public OfflinePlayer offlinePlayer;
	public UUID uuid;
	
	public String kit;
	
	public BuyKitPvP(Player player, String kit) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.kit = kit;
	}
	
	public BuyKitPvP(OfflinePlayer offlinePlayer, String kit) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.kit = kit;
	}
	
	public BuyKitPvP(UUID uuid, String kit) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.kit = kit;
	}
	
	public BuyKitPvP(Player player) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.kit = null;
	}
	
	public BuyKitPvP(OfflinePlayer offlinePlayer) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.kit = null;
	}
	
	public BuyKitPvP(UUID uuid) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.kit = null;
	}
	
	public void setKit(String kit) { this.kit = kit; }
	
	public String getKit() { return kit; }
	
	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `server_buy_kits_pvp` WHERE `nickname` = '" + this.player.getName() + "';");
			if (resultSet.next()) {
				return resultSet.getString("nickname") != null;
			}
			resultSet.close();
			statement.close();
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}
	
	public boolean checkKit(String kit) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `server_buy_kits_pvp` WHERE (`nickname` = '" + this.player.getName() + "') AND (`kit` = '" + kit.toUpperCase() + "');");
			if(resultSet.next()) { 
				return true; 
			}
			resultSet.close();
			statement.close();
			return false;
		} catch(Exception exception) {
			exception.printStackTrace();
			return true;
		}
	}
	
	public void create() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_buy_kits_pvp` WHERE (`nickname` = '" + this.player.getName() + "');");
			preparedStatement.execute("INSERT INTO `server_buy_kits_pvp` (`nickname`, `kit`) VALUES ('" + String.valueOf(this.player.getName() != null ? this.player.getName() : this.offlinePlayer.getName()) + "', '" + getKit() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	} 
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_buy_kits_pvp` WHERE (`nickname` = '" + this.player.getName() + "');");
			preparedStatement.execute("DELETE FROM `server_buy_kits_pvp` WHERE (`nickname` = '" + this.player.getName() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `server_buy_kits_pvp` SET `kit` = '" + getKit() + "' WHERE (`nickname` = '" + this.player.getName() + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
