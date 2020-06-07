 package br.com.frostmc.core.data.mysql.bukkit;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.core.data.mysql.bukkit.authentication.Authentication;
import br.com.frostmc.core.data.mysql.bukkit.authentication.loads.LoadingAuthentication;
import br.com.frostmc.core.data.mysql.bukkit.blacklist.Blacklist;
import br.com.frostmc.core.data.mysql.bukkit.blacklist.loads.LoadingBlacklist;
import br.com.frostmc.core.data.mysql.bukkit.buy_kit.hg.BuyKitHG;
import br.com.frostmc.core.data.mysql.bukkit.buy_kit.loads.LoadingBuyKitHG;
import br.com.frostmc.core.data.mysql.bukkit.buy_kit.loads.LoadingBuyKitPvP;
import br.com.frostmc.core.data.mysql.bukkit.buy_kit.pvp.BuyKitPvP;
import br.com.frostmc.core.data.mysql.bukkit.buy_quests.Quests;
import br.com.frostmc.core.data.mysql.bukkit.buy_quests.loads.LoadingQuests;
import br.com.frostmc.core.data.mysql.bukkit.clan.Clan;
import br.com.frostmc.core.data.mysql.bukkit.diary.hg.DiaryHG;
import br.com.frostmc.core.data.mysql.bukkit.diary.loads.LoadingDiaryHG;
import br.com.frostmc.core.data.mysql.bukkit.diary.loads.LoadingDiaryPvP;
import br.com.frostmc.core.data.mysql.bukkit.diary.pvp.DiaryPvP;
import br.com.frostmc.core.data.mysql.bukkit.doublexp.DoubleXP;
import br.com.frostmc.core.data.mysql.bukkit.doublexp.loads.LoadingDoubleXP;
import br.com.frostmc.core.data.mysql.bukkit.group.Groups;
import br.com.frostmc.core.data.mysql.bukkit.group.loads.LoadingGroups;
import br.com.frostmc.core.data.mysql.bukkit.punishments.Punishments;
import br.com.frostmc.core.data.mysql.bukkit.punishments.loads.LoadingPunish;
import br.com.frostmc.core.data.mysql.bukkit.stats1v1.Stats1v1;
import br.com.frostmc.core.data.mysql.bukkit.stats1v1.loads.LoadingStats1v1;
import br.com.frostmc.core.data.mysql.bukkit.statsGladiator.StatsGladiator;
import br.com.frostmc.core.data.mysql.bukkit.statsGladiator.loads.LoadingStatsGladiator;
import br.com.frostmc.core.data.mysql.bukkit.statsGlobal.StatsGlobal;
import br.com.frostmc.core.data.mysql.bukkit.statsGlobal.loads.LoadingStatsGlobal;
import br.com.frostmc.core.data.mysql.bukkit.statsHG.StatsHG;
import br.com.frostmc.core.data.mysql.bukkit.statsHG.loads.LoadingStatsHG;
import br.com.frostmc.core.data.mysql.bukkit.statsPvP.StatsPvP;
import br.com.frostmc.core.data.mysql.bukkit.statsPvP.loads.LoadingStatsPvP;

public class AccountBukkit {
	
	public Authentication authentication;
	public Groups groups;
	public Punishments punishments;
	public Clan clanManager;
	public DoubleXP doubleXP;
	public StatsGlobal statsGlobal;
	public StatsPvP statsPvP;
	public StatsHG statsHG;
	public StatsGladiator statsGladiator;
	public Stats1v1 stats1v1;
	public Blacklist blacklist;
	public BuyKitPvP buy_kit_pvp;
	public BuyKitHG buy_kit_hg;
	public DiaryPvP diaryPvP;
	public DiaryHG diaryHG;
	public Quests quests;
	
	private Player player;
	
	public AccountBukkit(Player player) {
		this.player = player;
		this.authentication = LoadingAuthentication.load(player);
		this.groups = LoadingGroups.load(player);
		this.punishments = LoadingPunish.load(player);
		this.clanManager = new Clan(player);
		this.doubleXP = LoadingDoubleXP.load(player);
		this.statsGlobal = LoadingStatsGlobal.load(player);
		this.statsPvP = LoadingStatsPvP.load(player);
		this.statsHG = LoadingStatsHG.load(player);
		this.statsGladiator = LoadingStatsGladiator.load(player);
		this.stats1v1 = LoadingStats1v1.load(player);
		this.blacklist = LoadingBlacklist.load(player);
		this.buy_kit_pvp = LoadingBuyKitPvP.load(player);
		this.buy_kit_hg = LoadingBuyKitHG.load(player);
		this.diaryPvP = LoadingDiaryPvP.load(player);
		this.diaryHG = LoadingDiaryHG.load(player);
		this.quests = LoadingQuests.load(player);
	}
	
	public AccountBukkit(OfflinePlayer offlinePlayer) {
		this.authentication = LoadingAuthentication.load(offlinePlayer);
		this.groups = LoadingGroups.load(offlinePlayer);
		this.punishments = LoadingPunish.load(offlinePlayer);
		this.clanManager = new Clan(offlinePlayer);
		this.doubleXP = LoadingDoubleXP.load(offlinePlayer);
		this.statsGlobal = LoadingStatsGlobal.load(offlinePlayer);
		this.statsPvP = LoadingStatsPvP.load(offlinePlayer);
		this.statsHG = LoadingStatsHG.load(offlinePlayer);
		this.statsGladiator = LoadingStatsGladiator.load(offlinePlayer);
		this.stats1v1 = LoadingStats1v1.load(offlinePlayer);
		this.blacklist = LoadingBlacklist.load(offlinePlayer);
		this.buy_kit_pvp = LoadingBuyKitPvP.load(offlinePlayer);
		this.buy_kit_hg = LoadingBuyKitHG.load(offlinePlayer);
		this.diaryPvP = LoadingDiaryPvP.load(offlinePlayer);
		this.diaryHG = LoadingDiaryHG.load(offlinePlayer);
		this.quests = LoadingQuests.load(offlinePlayer);
	}
	
	public AccountBukkit(UUID uuid) {
		this.authentication = LoadingAuthentication.load(uuid);
		this.groups = LoadingGroups.load(uuid);
		this.doubleXP = LoadingDoubleXP.load(uuid);
		this.statsGlobal = LoadingStatsGlobal.load(uuid);
		this.statsPvP = LoadingStatsPvP.load(uuid);
		this.statsHG = LoadingStatsHG.load(uuid);
		this.statsGladiator = LoadingStatsGladiator.load(uuid);
		this.stats1v1 = LoadingStats1v1.load(uuid);
		this.blacklist = LoadingBlacklist.load(uuid);
		this.diaryPvP = LoadingDiaryPvP.load(uuid);
		this.diaryHG = LoadingDiaryHG.load(uuid);
		this.quests = LoadingQuests.load(uuid);
	}
	
	public Player getPlayer() { return player; }
	public Authentication getAuthentication() { return authentication; }
	public Groups getGroups() { return groups; }
	public Clan getClan() { return clanManager; }
	public Punishments getPunishments() { return punishments; }
	public DoubleXP getDoubleXP() { return doubleXP; }
	public StatsGlobal getStatsGlobal() { return statsGlobal; }
	public StatsPvP getStatsPvP() { return statsPvP; }
	public StatsHG getStatsHG() { return statsHG; }
	public StatsGladiator getStatsGladiator() { return statsGladiator; }
	public Stats1v1 getStats1v1() { return stats1v1; }
	public Blacklist getBlacklist() { return blacklist; }
	public BuyKitPvP getBuy_kit_pvp() { return buy_kit_pvp; }
	public BuyKitHG getBuy_kit_hg() { return buy_kit_hg; }
	public DiaryPvP getDiaryPvP() { return diaryPvP; }
	public DiaryHG getDiaryHG() { return diaryHG; }
	public Quests getQuests() { return quests; }
	
}
