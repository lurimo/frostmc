package br.com.frostmc.core.data.mysql.bungeecord.group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import br.com.frostmc.bungeecord.BungeeMain;
import br.com.frostmc.core.data.mysql.bungeecord.AccountBungee;
import br.com.frostmc.core.util.Longs;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.TemporaryType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Groups {
	
	public String player, firstLogin, lastLogin, setBy, ipAddrees;
	public GroupsType groupsType;
	public TemporaryType temporaryType;
	public long expire;
	
	public Groups(String player, String firstLogin, String lastLogin, String setBy, String ipAddrees, GroupsType groupsType, TemporaryType temporaryType, long expire) {
		this.player = player;
		this.firstLogin = firstLogin;
		this.lastLogin = lastLogin;
		this.setBy = setBy;
		this.ipAddrees = ipAddrees;
		this.groupsType = groupsType;
		this.temporaryType = temporaryType;
		this.expire = expire;
	}
	
	public Groups(String player) {
		this.player = player;
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

	@SuppressWarnings({ "deprecation" })
	public void setGroupServer(AccountBungee playerBungee, AccountBungee stafferBungee, GroupsType groupType, TemporaryType temporaryType, long expire) {
		ProxiedPlayer stafferPlayer = ProxyServer.getInstance().getPlayer(stafferBungee.getPlayer());
		ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(playerBungee.getPlayer());
		if (!exists()) {
			stafferPlayer.sendMessage("�3�lGROUP �fEsse jogador n�o foi encontrado em nosso banco de dados.");
			return;
		}
		if (playerBungee == stafferBungee) {
			stafferPlayer.sendMessage("�3�lGROUP �fVoc� n�o pode setar grupo para si mesmo.");
			return;
		}
		if (playerBungee.getGroups().hasGroupPermission(stafferBungee.getGroups().getGroupsType())) {
			stafferPlayer.sendMessage("�3�lGROUP �fVoc� n�o pode manajear o group desse jogador.");
			return;
		}
		
		stafferPlayer.sendMessage("�a�lGROUP �fVoc� alterou o cargo do jogador �a" + playerBungee.getGroups().player + " �fpara " + groupType.fullName() + (temporaryType.equals(TemporaryType.PERMANENT) ? "�fsem data de expira��o" : "�fir� expirar em " + Longs.messageSmall(expire)));
		
		this.player = playerBungee.getGroups().player;
		this.setBy = stafferPlayer.getName();
		this.ipAddrees = playerBungee.getGroups().getIpAddrees();
		this.groupsType = groupType;
		this.temporaryType = temporaryType;
		this.expire = expire;
		
		if (proxiedPlayer != null) {
			proxiedPlayer.disconnect(messageOnKick());
		}
		
		save();
	}
	
	@SuppressWarnings({ "deprecation" })
	public void setGroupConsole(AccountBungee playerBungee, CommandSender staffer, GroupsType groupType, TemporaryType temporaryType, long expire) {
		ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(playerBungee.getPlayer());
		
		if (!exists()) {
			ProxyServer.getInstance().getConsole().sendMessage("�3�lGROUP �fEsse jogador n�o foi encontrado em nosso banco de dados.");
			return;
		}
		
		ProxyServer.getInstance().getConsole().sendMessage("�a�lGROUP �fVoc� alterou o cargo do jogador �a" + playerBungee.getGroups().player + " �fpara " + groupType.fullName() + (temporaryType.equals(TemporaryType.PERMANENT) ? "�fsem data de expira��o" : "�fir� expirar em " + Longs.messageSmall(expire)));
		
		this.player = playerBungee.getGroups().player;
		this.setBy = "CONSOLE";
		this.ipAddrees = playerBungee.getGroups().getIpAddrees();
		this.groupsType = groupType;
		this.temporaryType = temporaryType;
		this.expire = expire;
		
		if (proxiedPlayer != null) {
			proxiedPlayer.disconnect(messageOnKick());
		}
		
		save();
	}
	
	public boolean exists() {
		try {
			Statement statement = BungeeMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_accounts` WHERE (`nickname` = '" + this.player + "');");
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
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BungeeMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts` WHERE (`nickname` = '" + this.player + "');");
			preparedStatement.execute("DELETE FROM `players_accounts` WHERE (`nickname` = '" + this.player + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BungeeMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `players_accounts` SET `firstLogin` = '" + getFirstLogin() + "', `lastLogin` = '" + getLastLogin() + "', `setBy` = '" + getSetBy() + "', `ipAddrees` = '" + getIpAddrees() + "', `group` = '" + getGroupsType() + "', `temporary` = '" + getTemporaryType() + "' ,`expire` = '" + getExpire() + "' WHERE (`nickname` = '" + this.player + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public String messageOnKick() {
		return "�3�lGROUP" + "\n" + "\n" + "�fO seu grupo foi atualizado." + "\n" + "�fAtualizado para " + getGroupsType().fullName() + "�fpor �a" + getSetBy() + "\n" + "\n" + "�fIr� expirar em �e" + (getTemporaryType().equals(TemporaryType.PERMANENT) ? "Nunca" : Longs.messageSmall(getExpire())) + "�f." + "\n" + "\n" + "�3" + Strings.getWebsite();
	}
	
}