package br.com.frostmc.core.data.mysql.bukkit.buy_quests;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;

public class Quests {
	
	public Player player;
	public OfflinePlayer offlinePlayer;
	public UUID uuid;
	
	public int iron, gold, diamond;
	public int key_iron, key_gold, key_diamond;
	
	public Quests(Player player, int iron, int gold, int diamond, int key_iron, int key_gold, int key_diamond) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.iron = iron; this.gold = gold; this.diamond = diamond;
		this.key_iron = key_iron; this.key_gold = key_gold; this.key_diamond = key_diamond;
	}
	
	public Quests(OfflinePlayer offlinePlayer, int iron, int gold, int diamond, int key_iron, int key_gold, int key_diamond) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.iron = iron; this.gold = gold; this.diamond = diamond;
		this.key_iron = key_iron; this.key_gold = key_gold; this.key_diamond = key_diamond;
	}
	
	public Quests(UUID uuid, int iron, int gold, int diamond, int key_iron, int key_gold, int key_diamond) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.iron = iron; this.gold = gold; this.diamond = diamond;
		this.key_iron = key_iron; this.key_gold = key_gold; this.key_diamond = key_diamond;
	}
	
	public Quests(Player player) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.iron = 0; this.gold = 0; this.diamond = 0;
		this.key_iron = 0; this.key_gold = 0; this.key_diamond = 0;
	}
	
	public Quests(OfflinePlayer offlinePlayer) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.iron = 0; this.gold = 0; this.diamond = 0;
		this.key_iron = 0; this.key_gold = 0; this.key_diamond = 0;
	}
	
	public Quests(UUID uuid) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.iron = 0; this.gold = 0; this.diamond = 0;
		this.key_iron = 0; this.key_gold = 0; this.key_diamond = 0;
	}
	
	public int getIron() { return iron; }
	public int getGold() { return gold; }
	public int getDiamond() { return diamond; }
	
	public int getKey_iron() { return key_iron; }
	public int getKey_gold() { return key_gold; }
	public int getKey_diamond() { return key_diamond; }

	public void setIron(int iron) { this.iron = iron; }
	public void setGold(int gold) { this.gold = gold; }
	public void setDiamond(int diamond) { this.diamond = diamond; }
	
	public void setKey_iron(int key_iron) { this.key_iron = key_iron; }
	public void setKey_gold(int key_gold) { this.key_gold = key_gold; }
	public void setKey_diamond(int key_diamond) { this.key_diamond = key_diamond; }
	
	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `server_quests` WHERE `uuid` = '" + this.uuid.toString() + "';");
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
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_quests` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("INSERT INTO `server_quests` (`uuid`, `nickname`, `iron`, `gold`, `diamond`, `key_iron`, `key_gold`, `key_diamond`) VALUES ('" + this.uuid.toString() + "', '" + String.valueOf(this.player.getName() != null ? this.player.getName() : this.offlinePlayer.getName()) + "', '" + getIron() + "', '" + getGold() + "', '" + getDiamond() + "', '" + getKey_iron() + "', '" + getKey_gold() + "', '" + getKey_diamond() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_quests` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("DELETE FROM `server_quests` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `server_quests` SET `iron` = '" + getIron() + "', `gold` = '" + getGold() + "', `diamond` = '" + getDiamond() + "', `key_iron` = '" + getKey_iron() + "', `key_gold` = '" + getKey_gold() + "', `key_diamond` = '" + getKey_diamond() + "' WHERE (`uuid` = '" + this.uuid.toString() + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}