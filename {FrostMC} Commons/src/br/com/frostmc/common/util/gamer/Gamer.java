package br.com.frostmc.common.util.gamer;

import java.text.NumberFormat;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.util.MessagesType;
import br.com.frostmc.common.util.tag.Tags;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.util.enums.GroupClanType;
import br.com.frostmc.core.util.enums.LeagueType;
import br.com.frostmc.core.util.enums.RunningType;

public class Gamer {
	
	public Player player;
	public OfflinePlayer offlinePlayer;
	public UUID uuid;
	public AccountBukkit account;
	
	public String firstLogin, lastLogin;
	public int box_iron, box_gold, box_diamond;
	public int keyBox_iron, keyBox_gold, keyBox_diamond;
	public int kills_pvp, deaths_pvp, killstreak_pvp, lava_easy_pvp, lava_medium_pvp, lava_hard_pvp, lava_extreme_pvp;
	public int wins_hg, kills_hg, deaths_hg;
	public int victory_gladiator, defeat_gladiator, winstreak_gladiator;
	public int victory_1v1, defeat_1v1, winstreak_1v1;
	public int xp, moedas, fichas;
	
	public boolean doublexp;
	
	public LeagueType leagueType;
	public GroupClanType clanType;
	
	public Gamer(Player player) {
		this.account = new AccountBukkit(player);
		
		this.player = player;
		this.offlinePlayer = null;
		this.uuid = player.getUniqueId();

		this.firstLogin = "00/00/0000 - 00:00:00"; this.lastLogin = "00/00/0000 - 00:00:00"; 
		this.box_iron = 0; this.box_gold = 0; this.box_diamond = 0;
		this.keyBox_iron = 0; this.keyBox_gold = 0; this.keyBox_diamond = 0;
		this.kills_pvp = 0; this.deaths_pvp = 0; this.killstreak_pvp = 0; this.lava_easy_pvp = 0; this.lava_medium_pvp = 0; this.lava_hard_pvp = 0; this.lava_extreme_pvp = 0;
		this.wins_hg = 0; this.kills_hg = 0; this.deaths_hg = 0;
		this.victory_gladiator = 0; this.defeat_gladiator = 0; this.winstreak_gladiator = 0;
		this.victory_1v1 = 0; this.defeat_1v1 = 0; this.winstreak_1v1 = 0;
		
		this.xp = 0;
		this.moedas = 0;
		this.fichas = 0;
		
		this.leagueType = LeagueType.PRIMARIO;
		this.clanType = GroupClanType.MEMBRO;
	}
	
	public Gamer(OfflinePlayer offlinePlayer) {
		this.account = new AccountBukkit(player);
		
		this.player = null;
		this.offlinePlayer = offlinePlayer;
		this.uuid = offlinePlayer.getUniqueId();
		
		this.firstLogin = "00/00/0000 - 00:00:00"; this.lastLogin = "00/00/0000 - 00:00:00"; 
		
		this.box_iron = 0; this.box_gold = 0; this.box_diamond = 0;
		this.keyBox_iron = 0; this.keyBox_gold = 0; this.keyBox_diamond = 0;
		this.kills_pvp = 0; this.deaths_pvp = 0; this.killstreak_pvp = 0; this.lava_easy_pvp = 0; this.lava_medium_pvp = 0; this.lava_hard_pvp = 0; this.lava_extreme_pvp = 0;
		this.wins_hg = 0; this.kills_hg = 0; this.deaths_hg = 0;
		this.victory_gladiator = 0; this.defeat_gladiator = 0; this.winstreak_gladiator = 0;
		this.victory_1v1 = 0; this.defeat_1v1 = 0; this.winstreak_1v1 = 0;
		
		this.xp = 0; this.moedas = 0; this.fichas = 0;
		
		this.leagueType = LeagueType.PRIMARIO;
	}
	
	public Gamer(UUID uuid) {
		this.account = new AccountBukkit(uuid);
		
		this.player = null;
		this.offlinePlayer = null;
		this.uuid = uuid;
		
		this.firstLogin = "00/00/0000 - 00:00:00"; this.lastLogin = "00/00/0000 - 00:00:00"; 
		
		this.box_iron = 0; this.box_gold = 0; this.box_diamond = 0;
		this.keyBox_iron = 0; this.keyBox_gold = 0; this.keyBox_diamond = 0;
		this.kills_pvp = 0; this.deaths_pvp = 0; this.killstreak_pvp = 0; this.lava_easy_pvp = 0; this.lava_medium_pvp = 0; this.lava_hard_pvp = 0; this.lava_extreme_pvp = 0;
		this.wins_hg = 0; this.kills_hg = 0; this.deaths_hg = 0;
		this.victory_gladiator = 0; this.defeat_gladiator = 0; this.winstreak_gladiator = 0;
		this.victory_1v1 = 0; this.defeat_1v1 = 0; this.winstreak_1v1 = 0;
		
		this.xp = 0;
		this.moedas = 0;
		this.fichas = 0;
		
		this.leagueType = LeagueType.PRIMARIO;
	}
	
	public UUID getUuid() { return uuid; }
	public AccountBukkit getAccount() { return account; }
	
	public int getKills_pvp() { return kills_pvp; }
	public int getDeaths_pvp() { return deaths_pvp; }
	public int getKillstreak_pvp() { return killstreak_pvp; }
	public int getLava_easy_pvp() { return lava_easy_pvp; }
	public int getLava_medium_pvp() { return lava_medium_pvp; }
	public int getLava_hard_pvp() { return lava_hard_pvp; }
	public int getLava_extreme_pvp() { return lava_extreme_pvp; }
	
	public int getWins_hg() { return wins_hg; }
	public int getKills_hg() { return kills_hg; }
	public int getDeaths_hg() { return deaths_hg; }
	
	public int getVictory_gladiator() { return victory_gladiator; }
	public int getDefeat_gladiator() { return defeat_gladiator; }
	public int getWinstreak_gladiator() { return winstreak_gladiator; }
	
	public int getVictory_1v1() { return victory_1v1; }
	public int getDefeat_1v1() { return defeat_1v1; }
	public int getWinstreak_1v1() { return winstreak_1v1; }
	
	public String getFirstLogin() { return firstLogin; }
	public String getLastLogin() { return lastLogin; }
	public int getBox_diamond() { return box_diamond; }
	public int getBox_gold() { return box_gold; }
	public int getBox_iron() { return box_iron; }
	public int getKeyBox_diamond() { return keyBox_diamond; }
	public int getKeyBox_gold() { return keyBox_gold; }
	public int getKeyBox_iron() { return keyBox_iron; }
	public int getXp() { return xp; }
	public int getMoedas() { return moedas; }
	public int getFichas() { return fichas; }
	
	public void setDoublexp(boolean doublexp) {
		this.doublexp = doublexp;
	}
	public boolean isDoublexp() {
		return doublexp;
	}
	
	public LeagueType getLeagueType() { return leagueType; }
	public void setLeagueType(LeagueType leagueType) { this.leagueType = leagueType; }
	
	public void addBox_diamond(int box_diamond) { this.box_diamond += box_diamond; }
	public void addBox_gold(int box_gold) { this.box_gold += box_gold; }
	public void addBox_iron(int box_iron) { this.box_iron += box_iron; }
	public void addKeyBox_diamond(int keyBox_diamond) { this.keyBox_diamond += keyBox_diamond; }
	public void addKeyBox_gold(int keyBox_gold) { this.keyBox_gold += keyBox_gold; }
	public void addKeyBox_iron(int keyBox_iron) { this.keyBox_iron += keyBox_iron; }
	public void addKills_pvp(int kills_pvp) { this.kills_pvp += kills_pvp; }
	public void addDeaths_pvp(int deaths_pvp) { this.deaths_pvp += deaths_pvp;  }
	public void addKillstreak_pvp(int killstreak_pvp) { 
		this.killstreak_pvp += killstreak_pvp; 
		Player player = Bukkit.getPlayer(this.uuid);
		if (this.killstreak_pvp % 10 == 0) {
			Bukkit.broadcastMessage("�4�lKILLSTREAK �fO jogador �a" + player.getName() + " �fconseguiu um �1�lKILLSTREAK �fde �a" + this.killstreak_pvp);
		}
	}
	public void addLava_easy_pvp(int lava_easy_pvp) {  this.lava_easy_pvp += lava_easy_pvp; }
	public void addLava_medium_pvp(int lava_medium_pvp) { this.lava_medium_pvp += lava_medium_pvp; }
	public void addLava_hard_pvp(int lava_hard_pvp) { this.lava_hard_pvp += lava_hard_pvp; }
	public void addLava_extreme_pvp(int lava_extreme_pvp) { this.lava_extreme_pvp += lava_extreme_pvp; }
	public void addWins_hg(int wins_hg) { this.wins_hg += wins_hg; }
	public void addKills_hg(int kills_hg) { this.kills_hg += kills_hg; }
	public void addDeaths_hg(int deaths_hg) { this.deaths_hg += deaths_hg; }
	public void addXp(int xp) {
		this.xp += xp; 
		checkRank();
	}
	
	public void addVictory_1v1(int victory_1v1) { this.victory_1v1 += victory_1v1; }
	public void addDefeat_1v1(int defeat_1v1) { this.defeat_1v1 += defeat_1v1; }
	public void addWinstreak_1v1(int winstreak_1v1) {
		this.winstreak_1v1 += winstreak_1v1;
		Player player = Bukkit.getPlayer(this.uuid);
		if (this.winstreak_1v1 % 10 == 0) {
			Bukkit.broadcastMessage("�4�lWINSTREAK �fO jogador �a" + player.getName() + " �fconseguiu um �1�lWINSTREAK �fde �a" + this.winstreak_1v1);
		}
	}
	
	public void addVictory_gladiator(int victory_gladiator) { this.victory_gladiator += victory_gladiator; }
	public void addDefeat_gladiator(int defeat_gladiator) { this.defeat_gladiator += defeat_gladiator; }
	public void addWinstreak_gladiator(int winstreak_gladiator) { 
		this.winstreak_gladiator += winstreak_gladiator;
		Player player = Bukkit.getPlayer(this.uuid);
		if (this.winstreak_gladiator % 10 == 0) {
			Bukkit.broadcastMessage("�4�lWINSTREAK �fO jogador �a" + player.getName() + " �fconseguiu um �1�lWINSTREAK �fde �a" + this.winstreak_gladiator);
		}
	}
	
	public void addMoedas(int moedas) { this.moedas += moedas; }
	public void addFichas(int fichas) { this.fichas += fichas; }
	
	public void removeKills_pvp(int kills_pvp) { this.kills_pvp -= kills_pvp; if (this.kills_pvp < 0) { this.kills_pvp = 0; } }
	public void removeDeaths_pvp(int deaths_pvp) { this.deaths_pvp -= deaths_pvp; if (this.deaths_pvp < 0) { this.deaths_pvp = 0; } }
	public void removeKillstreak_pvp(int killstreak_pvp) { this.killstreak_pvp -= killstreak_pvp; if (this.killstreak_pvp < 0) { this.killstreak_pvp = 0; } }
	
	public void removeWins_hg(int wins_hg) { this.wins_hg -= wins_hg; if (this.wins_hg < 0) { this.wins_hg = 0; } }
	public void removeKills_hg(int kills_hg) { this.kills_hg -= kills_hg; if (this.kills_hg < 0) { this.kills_hg = 0; } }
	public void removeDeaths_hg(int deaths_hg) { this.deaths_hg -= deaths_hg; if (this.deaths_hg < 0) { this.deaths_hg = 0; } }
	
	public void removeVictory_gladiator(int victory_gladiator) { this.victory_gladiator -= victory_gladiator; if (this.victory_gladiator < 0) { this.victory_gladiator = 0; } }
	public void removeDefeat_gladiator(int defeat_gladiator) { this.defeat_gladiator -= defeat_gladiator; if (this.defeat_gladiator < 0) { this.defeat_gladiator = 0; } }

	public void removeVictory_1v1(int victory_1v1) { this.victory_1v1 -= victory_1v1; if (this.victory_1v1 < 0) { this.victory_1v1 = 0; } }
	public void removeDefeat_1v1(int defeat_1v1) { this.defeat_1v1 -= defeat_1v1; if (this.defeat_1v1 < 0) { this.defeat_1v1 = 0; } }
	
	public void removeBox_diamond(int box_diamond) { this.box_diamond -= box_diamond; if (this.box_diamond < 0) { this.box_diamond = 0; } }
	public void removeBox_gold(int box_gold) { this.box_gold -= box_gold; if (this.box_gold < 0) { this.box_gold = 0; } }
	public void removeBox_iron(int box_iron) { this.box_iron -= box_iron; if (this.box_iron < 0)  { this.box_iron = 0; } }
	public void removeKeyBox_diamond(int keyBox_diamond) { this.keyBox_diamond -= keyBox_diamond; if (this.keyBox_diamond < 0) { this.keyBox_diamond = 0; } }
	public void removeKeyBox_gold(int keyBox_gold) { this.keyBox_gold -= keyBox_gold; if (this.keyBox_gold < 0) { this.keyBox_gold = 0; } }
	public void removeKeyBox_iron(int keyBox_iron) { this.keyBox_iron -= keyBox_iron; if (this.keyBox_iron < 0) { this.keyBox_iron = 0; } }
	public void removeXp(int xp) { this.xp -= xp; if (this.xp < 0) { this.xp = 0; } }
	public void removeMoedas(int moedas) {
		this.moedas -= moedas;
		if (this.moedas < 0) {
			this.moedas = 0;
		}
		Player player = Bukkit.getPlayer(this.uuid);
		player.sendMessage("�6�lMOEDAS �fVoc� perdeu �6�l" + NumberFormat.getInstance().format(moedas) + " MOEDA" + (moedas < 1 ? "" : "S"));
	}
	public void removeFichas(int fichas) {
		this.fichas -= fichas;
		if (this.fichas < 0) {
			this.fichas = 0;
		}
		Player player = Bukkit.getPlayer(this.uuid);
		player.sendMessage("�3�lFICHAS �fVoc� perdeu �3�l" + NumberFormat.getInstance().format(fichas) + " FICHA" + (fichas < 1 ? "" : "S"));
	}
	
	public void resetXp() {
		this.xp = 0;
		Player player = Bukkit.getPlayer(this.uuid);
		Tags.updateTag(player);
	}
	
	public void resetKillstreak_pvp() { this.killstreak_pvp = 0; }
	public void resetWinstreak_gladiator() { this.winstreak_gladiator = 0; }
	public void resetWinstreak_1v1() { this.winstreak_1v1 = 0; }
	
	public void resetKillstreakAdversary_pvp(Player adversary) { 
		if (getKillstreak_pvp() != 0 && getKillstreak_pvp() > 10)
			Bukkit.broadcastMessage("�4�lKILLSTREAK �fO jogador �a" + adversary.getName() + " �facabou com o �1�lKILLSTREAK �fde �c" + getKillstreak_pvp() + " �fdo jogador �c" + this.player.getName());
		this.killstreak_pvp = 0; 
	}
	
	public void resetWinstreakAdversary_gladiator(Player adversary) { 
		if (getWinstreak_gladiator() != 0 && getWinstreak_gladiator() > 10)
			Bukkit.broadcastMessage("�4�lWINSTREAK �fO jogador �a" + adversary.getName() + " �facabou com o �1�lWINSTREAK �fde �c" + getWinstreak_gladiator() + " �fdo jogador �c" + this.player.getName());
		this.winstreak_gladiator = 0; 
	}
	
	public void resetWinstreakAdversary_1v1(Player adversary) { 
		if (getWinstreak_1v1() != 0 && getWinstreak_1v1() > 10)
			Bukkit.broadcastMessage("�4�lWINSTREAK �fO jogador �a" + adversary.getName() + " �facabou com o �1�lWINSTREAK �fde �c" + getWinstreak_1v1() + " �fdo jogador �c" + this.player.getName());
		this.winstreak_1v1 = 0; 
	}
	
	public void loadProfile() {
		this.firstLogin = account.getGroups().getFirstLogin();
		this.lastLogin = account.getGroups().getLastLogin();
		this.box_iron = account.getQuests().getIron();
		this.box_gold = account.getQuests().getGold();
		this.box_diamond = account.getQuests().getDiamond();
		this.keyBox_iron = account.getQuests().getKey_iron();
		this.keyBox_gold = account.getQuests().getKey_gold();
		this.keyBox_diamond = account.getQuests().getKey_diamond();
		
		this.kills_pvp = account.getStatsPvP().getKills();
		this.deaths_pvp = account.getStatsPvP().getDeaths();
		this.killstreak_pvp = account.getStatsPvP().getKillstreak();
		this.lava_easy_pvp = account.getStatsPvP().getLava_easy();
		this.lava_medium_pvp = account.getStatsPvP().getLava_medium();
		this.lava_hard_pvp = account.getStatsPvP().getLava_hard();
		this.lava_extreme_pvp = account.getStatsPvP().getLava_extreme();
		
		this.wins_hg = account.getStatsHG().getWins();
		this.kills_hg = account.getStatsHG().getKills();
		this.deaths_hg = account.getStatsHG().getDeaths();
		
		this.victory_gladiator = account.getStatsGladiator().getVictory();
		this.defeat_gladiator = account.getStatsGladiator().getDefeat();
		this.winstreak_gladiator = account.getStatsGladiator().getWinstreak();
		
		this.victory_1v1 = account.getStats1v1().getVictory();
		this.defeat_1v1 = account.getStats1v1().getDefeat();
		this.winstreak_1v1 = account.getStats1v1().getWinstreak();
		
		this.xp = account.getStatsGlobal().getXp();
		this.moedas = account.getStatsGlobal().getMoedas();
		this.fichas = account.getStatsGlobal().getFichas();
		
		if (account.getDoubleXP().exists()) {
			if (account.getDoubleXP().getRunningType().equals(RunningType.ACTIVED)) {
				setDoublexp(true);
			} else {
				setDoublexp(false);
			}
		}
		
		this.leagueType = account.getStatsGlobal().getLeagueType();
	}
	
	public void update() {
		account.getGroups().setFirstLogin(getFirstLogin());
		account.getGroups().setLastLogin(getLastLogin());
		account.getQuests().setIron(getBox_iron());
		account.getQuests().setGold(getBox_gold());
		account.getQuests().setDiamond(getBox_diamond());
		account.getQuests().setIron(getKeyBox_iron());
		account.getQuests().setGold(getKeyBox_gold());
		account.getQuests().setDiamond(getKeyBox_diamond());
		
		account.getStatsPvP().setKills(getKills_pvp());
		account.getStatsPvP().setDeaths(getDeaths_pvp());
		account.getStatsPvP().setKillstreak(getKillstreak_pvp());
		account.getStatsPvP().setLava_easy(getLava_easy_pvp());
		account.getStatsPvP().setLava_medium(getLava_medium_pvp());
		account.getStatsPvP().setLava_hard(getLava_hard_pvp());
		account.getStatsPvP().setLava_extreme(getLava_extreme_pvp());
		
		account.getStatsHG().setWins(getWins_hg());
		account.getStatsHG().setKills(getKills_hg());
		account.getStatsHG().setDeaths(getDeaths_hg());
		
		account.getStatsGladiator().setVictory(getVictory_gladiator());
		account.getStatsGladiator().setDefeat(getDefeat_gladiator());
		account.getStatsGladiator().setWinstreak(getWinstreak_gladiator());
		
		account.getStats1v1().setVictory(getVictory_1v1());
		account.getStats1v1().setDefeat(getDefeat_1v1());
		account.getStats1v1().setWinstreak(getWinstreak_1v1());
		
		account.getStatsGlobal().setXp(getXp());
		account.getStatsGlobal().setMoedas(getMoedas());
		account.getStatsGlobal().setFichas(getFichas());
		
		account.getStatsGlobal().setLeagueType(getLeagueType());
		
		account.getStatsPvP().save();
		account.getStatsHG().save();
		account.getStats1v1().save();
		account.getStatsGladiator().save();
		account.getStatsGlobal().save();
	}
	
	public LeagueType getNext() {
		if (getLeagueType().equals(LeagueType.PRIMARIO)) {
			return LeagueType.APRENDIZ;
		} else if (getLeagueType().equals(LeagueType.APRENDIZ)) {
			return LeagueType.AVAN�ADO;
		} else if (getLeagueType().equals(LeagueType.AVAN�ADO)) {
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
			if (getXp() >= LeagueType.AVAN�ADO.getXp()) {
				return true;
			}
		} else if (getLeagueType().equals(LeagueType.AVAN�ADO)) {
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
	
	public int percentage() {
		return getXp() * 100 / getNext().getXp();
	}
	
	public void checkRank() {
		if (getLeagueType().equals(LeagueType.FROST))
			return;
		if (allowNextRank()) {
			setLeagueType(getNext());
			update();
			Player player = Bukkit.getPlayer(this.uuid);
			player.sendMessage("�6�lLEAGUE �fParab�ns, voc� �a�lUPOU �fsua �6�lLIGA! �fAgora voc� � um: " + getLeagueType().fullName());
			MessagesType.sendTitleMessage(player, "�6�lLeague", "�fVoc� upou para " + getLeagueType().fullName());
			Tags.updateTag(player);
		}
	}
	
}