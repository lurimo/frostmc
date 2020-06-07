package br.com.frostmc.core.data.mysql.bukkit.group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Longs;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.TemporaryType;

public class Groups {

	public Player player;
	public OfflinePlayer offlinePlayer;
	public UUID uuid;
	public String firstLogin, lastLogin, setBy, ipAddrees, password;
	public int securityCode;
	public GroupsType groupsType;
	public TemporaryType temporaryType;
	public long expire;
	
	public Groups(Player player, String password, int securityCode, String firstLogin, String lastLogin, String setBy, String ipAddrees, GroupsType groupsType, TemporaryType temporaryType, long expire) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.firstLogin = firstLogin;
		this.lastLogin = lastLogin;
		this.setBy = setBy;
		this.ipAddrees = ipAddrees;
		this.groupsType = groupsType;
		this.temporaryType = temporaryType;
		this.expire = expire;
	}
	
	public Groups(OfflinePlayer offlinePlayer, String firstLogin, String lastLogin, String setBy, String ipAddrees, GroupsType groupsType, TemporaryType temporaryType, long expire) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.firstLogin = firstLogin;
		this.lastLogin = lastLogin;
		this.setBy = setBy;
		this.ipAddrees = ipAddrees;
		this.groupsType = groupsType;
		this.temporaryType = temporaryType;
		this.expire = expire;
	}
	
	public Groups(UUID uuid, String setBy, String firstLogin, String lastLogin, String ipAddrees, GroupsType groupsType, TemporaryType temporaryType, long expire) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.firstLogin = firstLogin;
		this.lastLogin = lastLogin;
		this.setBy = setBy;
		this.ipAddrees = ipAddrees;
		this.groupsType = groupsType;
		this.temporaryType = temporaryType;
		this.expire = expire;
	}
	
	public Groups(Player player) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.firstLogin = "00/00/0000 - 00:00:00";
		this.lastLogin = "00/00/0000 - 00:00:00";
		this.setBy = null;
		this.ipAddrees = "0.0.0.0";
		this.groupsType = GroupsType.MEMBRO;
		this.temporaryType = TemporaryType.PERMANENT;
		this.expire = 0L;
	}
	
	public Groups(OfflinePlayer offlinePlayer) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.firstLogin = "00/00/0000 - 00:00:00";
		this.lastLogin = "00/00/0000 - 00:00:00";
		this.setBy = null;
		this.ipAddrees = "0.0.0.0";
		this.groupsType = GroupsType.MEMBRO;
		this.temporaryType = TemporaryType.PERMANENT;
		this.expire = 0L;
	}
	
	public Groups(UUID uuid) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.firstLogin = "00/00/0000 - 00:00:00";
		this.lastLogin = "00/00/0000 - 00:00:00";
		this.setBy = null;
		this.ipAddrees = "0.0.0.0";
		this.groupsType = GroupsType.MEMBRO;
		this.temporaryType = TemporaryType.PERMANENT;
		this.expire = 0L;
	}
	
	public String getFirstLogin() { return firstLogin; }
	public String getLastLogin() { return lastLogin; }
	public String getSetBy() { return setBy; }
	public String getIpAddrees() { return ipAddrees; }
	public GroupsType getGroupsType() { return groupsType; }
	public TemporaryType getTemporaryType() { return temporaryType; }
	public long getExpire() { return expire; }

	public void setFirstLogin(String firstLogin) { this.firstLogin = firstLogin; }
	public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }
	public void setSetBy(String setBy) { this.setBy = setBy; }
	public void setIpAddrees(String ipAddrees) { this.ipAddrees = ipAddrees; }
	public void setGroupsType(GroupsType groupsType) { this.groupsType = groupsType; }
	public void setTemporaryType(TemporaryType temporaryType) { this.temporaryType = temporaryType; }
	public void setExpire(long expire) { this.expire = expire; }
	
	public boolean hasGroupPermission(GroupsType group) { return getGroupsType().ordinal() >= group.ordinal(); }

	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_accounts` WHERE `uuid` = '" + this.uuid.toString() + "';");
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
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("INSERT INTO `players_accounts` (`uuid`, `nickname`, `firstLogin`, `lastLogin`, `setBy`, `ipAddrees`, `group`, `temporary`, `expire`) VALUES ('" + this.uuid.toString() + "', '" + String.valueOf(this.player.getName() != null ? this.player.getName() : this.offlinePlayer.getName()) + "', '" + getFirstLogin() + "', '" + getLastLogin() + "', '" + getSetBy() + "', '" + getIpAddrees() + "', '" + GroupsType.MEMBRO + "', '" + TemporaryType.PERMANENT + "', '" + 0L + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("DELETE FROM `players_accounts` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `players_accounts` SET `firstLogin` = '" + getFirstLogin() + "', `lastLogin` = '" + getLastLogin() + "', `setBy` = '" + getSetBy() + "', `ipAddrees` = '" + getIpAddrees() + "', `group` = '" + getGroupsType() + "', `temporary` = '" + getTemporaryType() + "' ,`expire` = '" + getExpire() + "' WHERE (`uuid` = '" + this.uuid.toString() + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public String messageOnKick() {
		return "§3§lGROUP" + "\n" + "\n" + "§fO seu grupo foi atualizado." + "\n" + "§fAtualizado para " + getGroupsType().fullName() + "§fpor §a" + getSetBy() + "\n" + "\n" + "§fIrá expirar em §e" + (getTemporaryType().equals(TemporaryType.PERMANENT) ? "Nunca" : Longs.messageSmall(getExpire())) + "§f." + "\n" + "\n" + "§3" + Strings.getWebsite();
	}

}