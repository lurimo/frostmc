package br.com.frostmc.core.data.mysql.bukkit.diary.hg;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;

public class DiaryHG {
	
	public Player player;
	public OfflinePlayer offlinePlayer;
	public UUID uuid;
	
	public String kit;
	public long expire;
	
	public DiaryHG(Player player, String kit, long expire) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.kit = kit;
		this.expire = expire;
	}
	
	public DiaryHG(OfflinePlayer offlinePlayer , String kit, long expire) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.kit = kit;
		this.expire = expire;
	}
	
	public DiaryHG(UUID uuid , String kit, long expire) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.kit = kit;
		this.expire = expire;
	}
	
	public DiaryHG(Player player) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.kit = null;
		this.expire = 0L;
	}
	
	public DiaryHG(OfflinePlayer offlinePlayer ) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.kit = null;
		this.expire = 0L;
	}
	
	public DiaryHG(UUID uuid) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.kit = null;
		this.expire = 0L;
	}
	
	public String getKit() { return kit; }
	public long getExpire() { return expire; }
	
	public void setKit(String kit) { this.kit = kit; }
	public void setExpire(long expire) { this.expire = expire; }
	
	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `server_diary_hg` WHERE `uuid` = '" + this.uuid.toString() + "';");
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
	
	public boolean checkKit(String kit) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `server_diary_hg` WHERE (`uuid` = '" + this.uuid.toString() + "') AND (`kit` = '" + kit.toUpperCase() + "');");
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
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_diary_hg` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("INSERT INTO `server_diary_hg` (`uuid`, `nickname`, `kit`, `expire`) VALUES ('" + this.uuid.toString() + "', '" + String.valueOf(this.player.getName() != null ? this.player.getName() : this.offlinePlayer.getName()) + "',  '" + getKit() + "', '" + getExpire() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_diary_hg` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("DELETE FROM `server_diary_hg` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `server_diary_hg` SET `kit` = '" + getKit() + "', `expire` = '" + getExpire() + "' WHERE (`uuid` = '" + this.uuid.toString() + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
