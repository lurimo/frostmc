package br.com.frostmc.core.data.mysql.bukkit.punishments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Longs;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.BanType;
import br.com.frostmc.core.util.enums.TemporaryType;

public class Punishments {
	
	public String player;
	public OfflinePlayer offlinePlayer;
	public String ipAddrees;
	public String date;
	public BanType banType;
	public TemporaryType temporaryType;
	public String staffer;
	public String reason;
	public long expire;
	
	public Punishments(String player, String ipAddrees, String date, BanType banType, TemporaryType temporaryType, String staffer, String reason, long expire) {
		this.player = player;
		this.offlinePlayer = null;
		this.ipAddrees = ipAddrees;
		this.date = date;
		this.banType = banType;
		this.temporaryType = temporaryType;
		this.staffer = staffer;
		this.reason = reason;
		this.expire = expire;
	}
	
	public Punishments(OfflinePlayer offlinePlayer, String ipAddrees, String date, BanType banType, TemporaryType temporaryType, String staffer, String reason, long expire) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.ipAddrees = ipAddrees;
		this.date = date;
		this.banType = banType;
		this.temporaryType = temporaryType;
		this.staffer = staffer;
		this.reason = reason;
		this.expire = expire;
	}
	
	public Punishments(UUID uuid, String ipAddrees, String date, BanType banType, TemporaryType temporaryType, String staffer, String reason, long expire) {
		this.player = null;
		this.offlinePlayer = null;
		this.ipAddrees = ipAddrees;
		this.date = date;
		this.banType = banType;
		this.temporaryType = temporaryType;
		this.staffer = staffer;
		this.reason = reason;
		this.expire = expire;
	}
	
	public Punishments(String player) {
		this.player = player;
		this.offlinePlayer = null;
		this.ipAddrees = "0.0.0.0";
		this.date = "00/00/0000 - 00:00";
		this.banType = BanType.BANNED;
		this.temporaryType = TemporaryType.PERMANENT;
		this.staffer = null;
		this.reason = null;
		this.expire = 0L;
	}
	
	public Punishments(OfflinePlayer offlinePlayer) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.ipAddrees = "0.0.0.0";
		this.date = "00/00/0000 - 00:00";
		this.banType = BanType.BANNED;
		this.temporaryType = TemporaryType.PERMANENT;
		this.staffer = null;
		this.reason = null;
		this.expire = 0L;
	}
	
	public Punishments(UUID uuid) {
		this.player = null;
		this.offlinePlayer = null;
		this.ipAddrees = "0.0.0.0";
		this.date = "00/00/0000 - 00:00";
		this.banType = BanType.BANNED;
		this.temporaryType = TemporaryType.PERMANENT;
		this.staffer = null;
		this.reason = null;
		this.expire = 0L;
	}
	
	public String getIpAddrees() { return ipAddrees; }
	public String getDate() { return date; }
	public BanType getBanType() { return banType; }
	public TemporaryType getTemporaryType() { return temporaryType; }
	public String getStaffer() { return staffer; }
	public String getReason() { return reason; }
	public long getExpire() { return expire; }
	
	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_accounts_punish` WHERE `nickname` = '" + this.player + "';");
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
	
	public void create() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts_punish` WHERE (`nickname` = '" + this.player + "');");
			preparedStatement.execute("INSERT INTO `players_accounts_punish` (`nickname`, `ipAddrees`, `date`, `type`, `temporary`, `staffer`, `reason`, `expire`) VALUES ('" + String.valueOf(this.player != null ? this.player : this.offlinePlayer) + "', '" + getIpAddrees() + "', '" + getDate() + "', '" + getBanType() + "', '" + getTemporaryType() + "', '" + getStaffer() + "', '" + getReason() + "', '" + getExpire() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_accounts_punish` WHERE (`nickname` = '" + this.player + "');");
			preparedStatement.execute("DELETE FROM `players_accounts_punish` WHERE (`nickname` = '" + this.player + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `players_accounts_punish` SET `ipAddrees` = '" + getIpAddrees() + "', `date` = '" + getDate() + "', `type` = '" + getBanType() + ", `temporary` = '" + getTemporaryType() + "', `staffer` = '" + getStaffer() + "', `reason` = '" + getReason() + "', `expire` = '" + getExpire() + "' WHERE (`nickname` = '" + this.player + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void chatMessage() {
		for (Player players : BukkitMain.getPlugin().getServer().getOnlinePlayers()) {
			if (getBanType().equals(BanType.BANNED)) {
				if (BukkitMain.getPermissions().isModPlus(players)) {
					if (getTemporaryType().equals(TemporaryType.PERMANENT)) {
						players.sendMessage("§e§l[BAN] §a(" + this.player + ") foi banido permanentemente por " + getStaffer());
					} else {
						players.sendMessage("§e§l[BAN] §a(" + this.player + ") foi banido temporariamente por " + getStaffer());
					}
				}
				if (getTemporaryType().equals(TemporaryType.PERMANENT)) {
					players.sendMessage("§c" + this.player + " foi banido permanentemente do servidor.");
				}
			} else if (getBanType().equals(BanType.MUTATED)) {
				if (BukkitMain.getPermissions().isModPlus(players)) {
					if (getTemporaryType().equals(TemporaryType.PERMANENT)) {
						players.sendMessage("§e§l[MUTE] §a(" + this.player + ") foi mutado permanentemente por " + getStaffer());
					} else {
						players.sendMessage("§e§l[MUTE] §a(" + this.player + ") foi mutado temporariamente por " + getStaffer());
					}
				}
			}
		}
	}
	
	public String messageOnEntry() {
		if (getTemporaryType().equals(TemporaryType.PERMANENT)) {
			return "§cSua conta foi suspensa permanentemente!" + "\n" + "§cO seu banimento irá expirar em Nunca." + "\n" + "\n" + "§cStaffer " + getStaffer() + " pelo Motivo " + getReason() + "\n" + "\n" + "§cO banimento foi injusto? preencha a appeal em nosso site!" + "\n" + "§cCaso contrario compre seu unban em: " + Strings.getWebsite() + "/loja";
		} else {
			return "§cSua conta foi suspensa permanentemente!" + "\n" + "§cO seu banimento irá expirar em " + Longs.messageSmall(getExpire()) + "." + "\n" + "\n" + "§cStaffer " + getStaffer() + " pelo Motivo " + getReason() + "\n" + "\n" + "§cO banimento foi injusto? preencha a appeal em nosso site!" + "\n" + "§cCaso contrario compre seu unban em: " + Strings.getWebsite() + "/loja";
		}
	}
	
}