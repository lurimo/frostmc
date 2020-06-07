package br.com.frostmc.core.data.mysql.bukkit.statsGlobal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.tag.Tags;
import br.com.frostmc.core.util.enums.LeagueType;

public class StatsGlobal {
	
	public Player player;
	public OfflinePlayer offlinePlayer;
	public UUID uuid;
	public LeagueType leagueType;
	public int xp, moedas, fichas;
	
	public StatsGlobal(Player player, LeagueType leagueType, int xp, int moedas, int fichas) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.leagueType = leagueType;
		this.xp = xp;
		this.moedas = moedas;
		this.fichas = fichas;
	}
	
	public StatsGlobal(OfflinePlayer offlinePlayer, LeagueType leagueType, int xp, int moedas, int fichas) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.leagueType = leagueType;
		this.xp = xp;
		this.moedas = moedas;
		this.fichas = fichas;
	}
	
	public StatsGlobal(UUID uuid, LeagueType leagueType, int xp, int moedas, int fichas) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.leagueType = leagueType;
		this.xp = xp;
		this.moedas = moedas;
		this.fichas = fichas;
	}
	
	public StatsGlobal(Player player) {
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();
		this.leagueType = LeagueType.PRIMARIO;
		this.xp = 0;
		this.moedas = 0;
		this.fichas = 0;
	}
	
	public StatsGlobal(OfflinePlayer offlinePlayer) {
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		this.leagueType = LeagueType.PRIMARIO;
		this.xp = 0;
		this.moedas = 0;
		this.fichas = 0;
	}
	
	public StatsGlobal(UUID uuid) {
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		this.leagueType = LeagueType.PRIMARIO;
		this.xp = 0;
		this.moedas = 0;
		this.fichas = 0;
	}
	
	public LeagueType getLeagueType() { return leagueType; }
	
	public void setLeagueType(LeagueType leagueType) { this.leagueType = leagueType; }
	
	public void setXp(int xp) { this.xp = xp; checkRank(); }
	public void setMoedas(int moedas) { this.moedas = moedas; }
	public void setFichas(int fichas) { this.fichas = fichas; }
	
	public int getXp() { return xp; }
	public int getMoedas() { return moedas; }
	public int getFichas() { return fichas; }
	
	public void addXp(int xp) {
		this.xp += xp;
	}
	
	public void addMoedas(int moedas) { this.moedas += moedas; }
	public void addFichas(int fichas) { this.fichas += fichas; }
	
	public void removeXp(int xp) {
		this.xp -= xp;
		if (this.xp < 0) {
			this.xp = 0;
		}
		Tags.updateTag(this.player);
	}
	
	public void removeMoedas(int moedas) {
		this.moedas -= moedas;
		if (this.moedas < 0) {
			this.moedas = 0;
		}
		this.player.sendMessage("§6§lMOEDAS §fVocê perdeu §6§l" + NumberFormat.getInstance().format(moedas) + "MOEDA" + (moedas < 1 ? "" : "S"));
	}
	
	public void removeFichas(int fichas) {
		this.fichas -= fichas;
		if (this.fichas < 0) {
			this.fichas = 0;
		}
		this.player.sendMessage("§3§lFICHAS §fVocê perdeu §3§l" + NumberFormat.getInstance().format(fichas) + " FICHA" + (fichas < 1 ? "" : "S"));
	}
	
	public void resetXp() {
		this.xp = 0;
		Tags.updateTag(this.player);
	}
	
	public void resetMoedas() { this.moedas = 0; }
	public void resetFichas() { this.fichas = 0; }
	
	public void resetAll() {
		setLeagueType(LeagueType.PRIMARIO);
		resetXp();
		resetMoedas();
		resetFichas();
		save();
	}
	
	public int percentage() {
		return getXp() * 100 / getNext().getXp();
	}
	
	public boolean exists() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_global` WHERE `uuid` = '" + this.uuid.toString() + "';");
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
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_global` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("INSERT INTO `players_stats_global` (`uuid`, `nickname`, `league`, `xp`, `moedas`, `fichas`) VALUES ('" + this.uuid.toString() + "', '" + String.valueOf(this.player.getName() != null ? this.player.getName() : this.offlinePlayer.getName()) + "', '" + getLeagueType() + "', '" + getXp() + "', '" + getMoedas() + "', '" + getFichas() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `players_stats_global` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.execute("DELETE FROM `players_stats_global` WHERE (`uuid` = '" + this.uuid.toString() + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void save() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			statement.execute("UPDATE `players_stats_global` SET `league` = '" + getLeagueType() + "', `xp` = '" + getXp() + "', `moedas` = '" + getMoedas() + "', `fichas` = '" + getFichas() + "' WHERE (`uuid` = '" + this.uuid.toString() + "');");
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public LeagueType getNext() {
		if (getLeagueType().equals(LeagueType.PRIMARIO)) {
			return LeagueType.APRENDIZ;
		} else if (getLeagueType().equals(LeagueType.APRENDIZ)) {
			return LeagueType.AVANÇADO;
		} else if (getLeagueType().equals(LeagueType.AVANÇADO)) {
			return LeagueType.BRONZE;
		} else if (getLeagueType().equals(LeagueType.BRONZE)) {
			return LeagueType.PRATA;
		} else if (getLeagueType().equals(LeagueType.PRATA)) {
			return LeagueType.OURO;
		} else if (getLeagueType().equals(LeagueType.OURO)) {
			return LeagueType.DIAMANTE;
		} else if (getLeagueType().equals(LeagueType.DIAMANTE)) {
			return LeagueType.CRISTAL;
		} else if (getLeagueType().equals(LeagueType.CRISTAL)) {
			return LeagueType.ESMERALDA;
		} else if (getLeagueType().equals(LeagueType.ESMERALDA)) {
			return LeagueType.LENDA;
		} else if (getLeagueType().equals(LeagueType.LENDA)) {
			return LeagueType.MESTRE;
		} else if (getLeagueType().equals(LeagueType.MESTRE)) {
			return LeagueType.FROST;
		} else if (getLeagueType().equals(LeagueType.FROST)) {
			return LeagueType.PRIMARIO;
		} else {
			return LeagueType.PRIMARIO;
		}
	}

	public boolean allowNextRank() {
		if (getLeagueType().equals(LeagueType.PRIMARIO)) {
			if (getXp() >= LeagueType.APRENDIZ.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueType.APRENDIZ)) {
			if (getXp() >= LeagueType.AVANÇADO.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueType.AVANÇADO)) {
			if (getXp() >= LeagueType.BRONZE.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueType.BRONZE)) {
			if (getXp() >= LeagueType.PRATA.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueType.PRATA)) {
			if (getXp() >= LeagueType.OURO.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueType.OURO)) {
			if (getXp() >= LeagueType.DIAMANTE.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueType.DIAMANTE)) {
			if (getXp() >= LeagueType.CRISTAL.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueType.CRISTAL)) {
			if (getXp() >= LeagueType.ESMERALDA.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueType.ESMERALDA)) {
			if (getXp() >= LeagueType.LENDA.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueType.LENDA)) {
			if (getXp() >= LeagueType.MESTRE.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueType.MESTRE)) {
			if (getXp() >= LeagueType.FROST.getXp()) {
				return true;
			}
		}
		return false;
	}
	
	public void checkRank() {
		if (getLeagueType().equals(LeagueType.FROST))
			return;
		if (allowNextRank()) {
			setLeagueType(getNext());
			save();
			Tags.updateTag(this.player);
		}
	}
	
}