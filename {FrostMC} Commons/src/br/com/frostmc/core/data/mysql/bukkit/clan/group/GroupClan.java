package br.com.frostmc.core.data.mysql.bukkit.clan.group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.enums.GroupClanType;

public class GroupClan {
	
	private Player player;
	private OfflinePlayer offlinePlayer;
	private UUID uuid;
	public ArrayList<String> membro;
	private GroupClanType clanType;
	private String clan;
	
	public GroupClan(Player player, GroupClanType clanType, String clan) {
		this.player = player;
		this.uuid = player.getUniqueId();
		this.offlinePlayer = null;
		this.clanType = clanType;
		this.membro = new ArrayList<String>();
		this.clan = clan;
	}

	public GroupClan(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId();
		this.offlinePlayer = null;
		this.clanType = GroupClanType.MEMBRO;
		this.membro = null;
		this.clan = null;
	}
	
	public GroupClan(OfflinePlayer offlinePlayer, GroupClanType clanType, String clan) {
		this.player = null;
		this.uuid = offlinePlayer.getUniqueId();
		this.offlinePlayer = offlinePlayer;
		this.clanType = GroupClanType.MEMBRO;
		this.membro = new ArrayList<String>();
		this.clan = clan;
	}

	public GroupClan(OfflinePlayer offlinePlayer) {
		this.player = null;
		this.uuid = offlinePlayer.getUniqueId();
		this.offlinePlayer = offlinePlayer;
		this.clanType = GroupClanType.MEMBRO;
		this.membro = null;
		this.clan = null;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public OfflinePlayer getOfflinePlayer() {
		return offlinePlayer;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public String getClan() {
		return clan;
	}
	
	public void setClan(String clan) {
		this.clan = clan;
	}
	
	public GroupClanType getClanType() {
		return clanType;
	}
	
	public void setClanType(GroupClanType clanType) {
		this.clanType = clanType;
	}
	
	public boolean isMembro() {
		return membro.contains(player.getName());
	}
	
	public void setMembro() {
		this.membro.add(player.getName());
	}
	
	public void addMembro() {
		if (this.membro == null) {
			this.membro = new ArrayList<String>();
		}
		if (!isMembro()) {
			this.membro.add(player.getName());
		}
	}

	public void removeMembro() {
		if (this.membro == null) {
			this.membro = new ArrayList<String>();
		}
		if (isMembro()) {
			this.membro.remove(player.getName());
		}
	}
	
	public int getTotalMembros() {
		if (this.membro == null) {
			return 0;
		}
		return this.membro.size();
	}
	
	public boolean hasClanTypePermission(GroupClanType clanType) {
		return getClanType().ordinal() >= clanType.ordinal();
	}
	
	public boolean hasClanType(GroupClanType clanType) {
		return getClanType().equals(clanType);
	}
	
	public void put(GroupClanType group, String clan) {
		setClanType(group);
		setClan(clan);
		create();
	}
	
	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `clan_group` WHERE (`uuid` = '" + getUuid().toString() + "');");
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
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `clan_group` WHERE (`uuid` = '" + getUuid().toString() + "');");
			preparedStatement.execute("INSERT INTO `clan_group` (`uuid`, `nickname`, `nameClan`, `group`) VALUES ('" + getUuid().toString() + "', '" + getPlayer().getName() + "', '" + getClan() + "', '" + getClanType() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `clan_group` WHERE (`uuid` = '" + getUuid().toString() + "');");
			preparedStatement.execute("DELETE FROM `clan_group` WHERE (`uuid` = '" + getUuid().toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `clan_group` WHERE (`uuid` = '" + getUuid().toString() + "');");
			preparedStatement.execute("UPDATE `clan_group` SET `nameClan` = '" + getClan() + "', `group` = '" + getClanType() + "' WHERE (`uuid` = '" + getUuid().toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void load() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `clan_group` ORDER BY `nameClan` DESC;");
			while (resultSet.next()) {
				if (!membro.contains(resultSet.getString("nickname"))) {
					membro.add(resultSet.getString("nickname"));
				} else {
					membro.clear();
				}
			}
			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
