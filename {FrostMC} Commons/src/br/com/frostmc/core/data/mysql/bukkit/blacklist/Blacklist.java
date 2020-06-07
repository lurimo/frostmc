package br.com.frostmc.core.data.mysql.bukkit.blacklist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.TemporaryType;

public class Blacklist {
	
	public Player player;
	public OfflinePlayer offlinePlayer;
	public UUID uuid;
	public String staffer, ipAddrees;
	public TemporaryType temporaryType;
	public long expire;
	
	public Blacklist(Player player, String staffer, String ipAddrees, TemporaryType temporaryType, long expire) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.staffer = staffer;
		this.ipAddrees = ipAddrees;
		this.temporaryType = temporaryType;
		this.expire = expire;
	}
	
	public Blacklist(OfflinePlayer offlinePlayer, String staffer, String ipAddrees, TemporaryType temporaryType, long expire) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.staffer = staffer;
		this.ipAddrees = ipAddrees;
		this.temporaryType = temporaryType;
		this.expire = expire;
	}
	
	public Blacklist(UUID uuid, String staffer, String ipAddrees, TemporaryType temporaryType, long expire) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.staffer = staffer;
		this.ipAddrees = ipAddrees;
		this.temporaryType = temporaryType;
		this.expire = expire;
	}
	
	public Blacklist(Player player) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.staffer = null;
		this.ipAddrees = "0.0.0.0";
		this.temporaryType = TemporaryType.PERMANENT;
		this.expire = 0L;
	}
	
	public Blacklist(OfflinePlayer offlinePlayer) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.staffer = null;
		this.ipAddrees = "0.0.0.0";
		this.temporaryType = TemporaryType.PERMANENT;
		this.expire = 0L;
	}
	
	public Blacklist(UUID uuid) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.staffer = null;
		this.ipAddrees = "0.0.0.0";
		this.temporaryType = TemporaryType.PERMANENT;
		this.expire = 0L;
	}
	
	public String getStaffer() { return staffer; }
	public String getIpAddrees() { return ipAddrees; }
	public TemporaryType getTemporaryType() { return temporaryType; }
	public long getExpire() { return expire; }
	
	public void addBlacklistPermanent(String staffer, String ipAddrees) {
		this.staffer = staffer;
		this.ipAddrees = ipAddrees;
		this.temporaryType = TemporaryType.PERMANENT;
		this.expire = 0L;
		create();
	}
	
	public void addBlacklistTemporary(String staffer, String ipAddrees, long expire) {
		this.staffer = staffer;
		this.ipAddrees = ipAddrees;
		this.temporaryType = TemporaryType.TEMPORARY;
		this.expire = expire;
		create(); 
	}

	public void unBlacklist() {
		delete();
	}
	
	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_event_blacklist` WHERE `uuid` = '" + this.uuid.toString() + "';");
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
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_event_blacklist` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("INSERT INTO `players_event_blacklist` (`uuid`, `nickname`, `staffer`, `ipAddrees`, `temporary`, `expire`) VALUES ('" + this.uuid.toString() + "', '" + String.valueOf(this.player.getName() != null ? this.player.getName() : this.offlinePlayer.getName()) + "', '" + getStaffer() + "', '" + getIpAddrees() + "', '" + getTemporaryType() + "', '" + getExpire() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_event_blacklist` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("DELETE FROM `players_event_blacklist` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `players_event_blacklist` SET `staffer` = '" + getStaffer() + "', `ipAddrees` = '" + getIpAddrees() + "', `temporary` = '" + getTemporaryType() + "', `expire` = '" + getExpire() + "' WHERE (`uuid` = '" + this.uuid.toString() + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public String messageOnEntry() {
		if (getTemporaryType().equals(TemporaryType.PERMANENT)) {
			return "§4§lBLACKLIST §fVocê está adicionado na lista negra de eventos permanentemente. compre seu un-blacklist em §3" + Strings.getWebstore();
		} else {
			return "§4§lBLACKLIST §fVocê está adicionado na lista negra de eventos temporariamente. compre seu un-blacklist em §3" + Strings.getWebstore();
		}
	}
}
