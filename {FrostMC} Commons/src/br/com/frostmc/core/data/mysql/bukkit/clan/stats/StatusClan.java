package br.com.frostmc.core.data.mysql.bukkit.clan.stats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.MessagesType;
import br.com.frostmc.core.util.enums.LeagueClanType;

public class StatusClan {
	
	public String owner, clanName, clanTag;
	public LeagueClanType leagueClanType;
	public int vitorias, derrotas, xp;
	
	public StatusClan(String owner, String clanName, String clanTag, LeagueClanType leagueClanType, int xp, int vitorias, int derrotas) {
		this.clanName = clanName;
		this.clanTag = clanTag;
		this.leagueClanType = leagueClanType;
		this.xp = xp;
		this.vitorias = vitorias;
		this.derrotas = derrotas;
	}
	
	public StatusClan() {
		this.owner = null;
		this.clanName = null;
		this.clanTag = null;
		this.leagueClanType = LeagueClanType.BRONZE;
		this.vitorias = 0;
		this.derrotas = 0;
		this.xp = 0;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String getClanName() {
		return clanName;
	}
	
	public String getClanTag() {
		return clanTag;
	}
	
	public LeagueClanType getLeagueType() {
		return leagueClanType;
	}
	
	public int getXp() {
		return xp;
	}
	
	public int getVitorias() {
		return vitorias;
	}
	
	public int getDerrotas() {
		return derrotas;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public void setClanName(String clanName) {
		this.clanName = clanName;
	}
	
	public void setClanTag(String clanTag) {
		this.clanTag = clanTag;
	}
	
	public void setLeagueType(LeagueClanType leagueClanType) {
		this.leagueClanType = leagueClanType;
	}
	
	public void setXp(int xp) {
		this.xp = xp;
		checkRank();
	}
	
	public void setVitorias(int vitorias) {
		this.vitorias = vitorias;
	}
	
	public void setDerrotas(int derrotas) {
		this.derrotas = derrotas;
	}
	
	public void addXp(int xp) {
		this.xp += xp;
		checkRank();
	}
	
	public void addVitorias(int vitorias) {
		this.vitorias += vitorias;
	}
	
	public void addDerrotas(int derrotas) {
		this.derrotas += derrotas;
	}
	
	public void removeXp(int xp) {
		this.xp -= xp;
		if (this.xp < 0) { 
			this.xp = 0; 
		}
	}
	
	public void removeVitorias(int vitorias) {
		this.vitorias -= vitorias;
		if (this.vitorias < 0) { 
			this.vitorias = 0; 
		}
	}
	
	public void removeDerrotas(int derrotas) {
		this.derrotas -= derrotas;
		if (this.derrotas < 0) { 
			this.derrotas = 0; 
		}
	}
	
	public void put(String clanName, String clanTag) {
		setClanName(clanName);
		setClanTag(clanTag);
		create();
	}
	
	public boolean exists(String column, String argument) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `clan_status` WHERE (`" + column + "` = '" + argument + "');");
			if (resultSet.next()) {
				return resultSet.getString(argument) != null;
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
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `clan_status` WHERE (`nameClan` = '" + getClanName() + "');");
			preparedStatement.execute("DELETE FROM `clan_status` WHERE (`nameClan` = '" + getClanName() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void create() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `clan_status` WHERE (`nameClan` = '" + getClanName() + "');");
			preparedStatement.execute("INSERT INTO `clan_status` (`owner`, `nameClan`, `tagClan`, `league`, `xp`, `vitorias`, `derrotas`) VALUES ('" + getOwner() + "', '" + getClanName() + "', '" + getClanTag() + "', '" + getLeagueType() + "', '" + getXp() + "', '" + getVitorias() + "', '" + getDerrotas() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `clan_status` WHERE (`nameClan` = '" + getClanName() + "');");
			preparedStatement.execute("UPDATE `clan_status` SET `owner` = '" + getOwner() + "'`league` = '" + getLeagueType() + "', `xp` = '" + getXp() + "', `vitorias` = '" + getVitorias() + "', `derrotas` = '" + getDerrotas() + "' WHERE (`nameClan` = '" + getClanName() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public LeagueClanType getNext() {
		if (getLeagueType().equals(LeagueClanType.BRONZE)) {
			return LeagueClanType.PRATA;
		} else if (getLeagueType().equals(LeagueClanType.PRATA)) {
			return LeagueClanType.OURO;
		} else if (getLeagueType().equals(LeagueClanType.OURO)) {
			return LeagueClanType.PLATINA;
		} else if (getLeagueType().equals(LeagueClanType.PLATINA)) {
			return LeagueClanType.DIAMANTE;
		} else if (getLeagueType().equals(LeagueClanType.DIAMANTE)) {
			return LeagueClanType.CRISTAL;
		} else if (getLeagueType().equals(LeagueClanType.CRISTAL)) {
			return LeagueClanType.DIAMANTE;
		} else if (getLeagueType().equals(LeagueClanType.MESTRE)) {
			return LeagueClanType.BRONZE;
		} else {
			return LeagueClanType.BRONZE;
		}
	}

	public boolean allowNextRank() {
		if (getLeagueType().equals(LeagueClanType.BRONZE)) {
			if (getXp() >= LeagueClanType.PRATA.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueClanType.PRATA)) {
			if (getXp() >= LeagueClanType.OURO.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueClanType.OURO)) {
			if (getXp() >= LeagueClanType.PLATINA.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueClanType.PLATINA)) {
			if (getXp() >= LeagueClanType.DIAMANTE.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueClanType.DIAMANTE)) {
			if (getXp() >= LeagueClanType.CRISTAL.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueClanType.CRISTAL)) {
			if (getXp() >= LeagueClanType.MESTRE.getXp()) {
				return true;
			}
		}
		return false;
	}
	
	public int percentage() {
		return getXp() * 100 / getNext().getXp();
	}
	
	public String getRankPosition() {
		if (percentage() < 20) {
			return "I";
		} else if (percentage() >= 20) {
			return "II";
		} else if (percentage() >= 50) {
			return "III";
		} else if (percentage() >= 80) {
			return "IV";
		} else if (percentage() <= 100) {
			return "V";
		}
		return "I";
	}
	
	@SuppressWarnings("deprecation")
	public void checkRank() {
		if (getLeagueType().equals(LeagueClanType.MESTRE))
			return;
		if (allowNextRank()) {
			setLeagueType(getNext());
			for (Player players : Bukkit.getOnlinePlayers()) {
				MessagesType.sendTitleMessage(players, "§fClan: §a" + getClanName(), "§fUpou para " + getLeagueType().fullName() + getRankPosition());
			}
		}
	}

}
