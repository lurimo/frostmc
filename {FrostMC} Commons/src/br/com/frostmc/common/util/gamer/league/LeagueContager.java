package br.com.frostmc.common.util.gamer.league;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.core.util.enums.LeagueType;
import br.com.frostmc.core.util.enums.ServersType;

public class LeagueContager {
	
	private Gamer account, adversary;

	private int minimalValue, winValue, lostValue;
	
	private LeagueType leagueFirst, leagueSecondary;
	
	public LeagueContager(Gamer account, Gamer adversary) {
		this.account = account;
		this.adversary = adversary;

		this.leagueFirst = account.getLeagueType();
		this.leagueSecondary = adversary.getLeagueType();

		this.minimalValue = 0;
		this.winValue = 0;
		this.lostValue = 0;
	}

	public void prizeLeague() {
		
		Player adversaryPlayer = Bukkit.getPlayer(adversary.getUuid());
		Player accountPlayer = Bukkit.getPlayer(account.getUuid());
		
		int finalDebit = minimalValue, finalValue = minimalValue, accountKillstreak = 0, adversaryKillstreak = 0;
		
		if (account == adversary)
			return;
		
		if (account == null)
			return;
		
		if (adversary == null)
			return;

		if (BukkitMain.getServersType().equals(ServersType.PVP)) {
			accountKillstreak = account.getKillstreak_pvp();
			adversaryKillstreak = adversary.getKillstreak_pvp();
		} else if (BukkitMain.getServersType().equals(ServersType.GLADIATOR)) {
			accountKillstreak = account.getWinstreak_gladiator();
			adversaryKillstreak = adversary.getWinstreak_gladiator();
		} else if (BukkitMain.getServersType().equals(ServersType.ONEVSONE)) {
			accountKillstreak = account.getWinstreak_1v1();
			adversaryKillstreak = adversary.getWinstreak_1v1();
		}

		boolean continueProcess = false;

		if (adversary != null && account != null) {
			continueProcess = true;
		} else {
			BukkitMain.getCore().getBox().box("One of the user accounts ( " + adversaryPlayer.getName() + "/"  + accountPlayer.getName() + ") is invalid to continue the leagues procedure.");
			return;
		}

		if (continueProcess) {
			
			if (adversaryPlayer == null || accountPlayer == null)
				return;
			
			if (leagueFirst == null || leagueSecondary == null)
				return;
			
			if (leagueFirst.getOrder() <= leagueSecondary.getOrder()) {
				finalValue = finalValue + 2;

				if (accountKillstreak > adversaryKillstreak) {
					finalValue = finalValue + ((accountKillstreak / 3) >= 0 ? (accountKillstreak / 3) : 3);
				} else if (adversaryKillstreak > accountKillstreak) {
					finalValue = finalValue + ((adversaryKillstreak / 2) >= 0 ? (adversaryKillstreak / 2) : 5);
				} else {
					finalValue = finalValue + 2 + (int) new Random().nextInt(8);
				}

			} else {
				finalValue = finalValue + 1;

				if (accountKillstreak > adversaryKillstreak) {
					finalValue = finalValue + ((accountKillstreak / 3) >= 0 ? (accountKillstreak / 3) : 2);
				} else if (adversaryKillstreak > accountKillstreak) {
					finalValue = finalValue + ((adversaryKillstreak / 2) >= 0 ? (adversaryKillstreak / 2) : 3);
				} else {
					finalValue = finalValue + 1 + (int) new Random().nextInt(4);
				}
			}

			if (BukkitMain.getServersType().equals(ServersType.PVP)) {
				finalValue = finalValue + (int) new Random().nextInt(4);
			} else if (BukkitMain.getServersType().equals(ServersType.ONEVSONE)) {
				finalValue = finalValue + (int) new Random().nextInt(5);
			} else if (BukkitMain.getServersType().equals(ServersType.GLADIATOR)) {
				finalValue = finalValue + (int) new Random().nextInt(6);
			} else {
				finalValue = finalValue + (int) new Random().nextInt(10);
			}

			if (account.isDoublexp()) {
				finalValue = finalValue * 2;
			}

			this.winValue = finalValue;
			accountPlayer.sendMessage("§9§lXP §fVocê ganhou §9§l" + this.winValue + " XP " + (account.isDoublexp() ? "§7(DoubleXP)" : ""));
			account.addXp(this.winValue);
			
			finalDebit = finalValue / 2;

			this.lostValue = finalDebit;
			adversaryPlayer.sendMessage("§9§lXP §fVocê perdeu §c§l" + this.lostValue + " XP");

			if (adversary.getXp() > 0) {
				adversary.removeXp(this.lostValue);
			}
			int random = 0;
			if (((CraftPlayer) accountPlayer.getPlayer()).getHandle().playerConnection.networkManager.getVersion() > 46) {
				random = new Random().nextInt(100);
				accountPlayer.sendMessage("§6§lMOEDAS §fVocê ganhou §e§l" + (this.winValue * 6) + " MOEDAS §7(1.8)");
				account.addMoedas(random + (this.winValue * 6));
			} else {
				random = new Random().nextInt(50);
				accountPlayer.sendMessage("§6§lMOEDAS §fVocê ganhou §e§l" + (this.winValue * 3) + " MOEDAS");
				account.addMoedas(random + (this.winValue * 3));
			}
		}
	}
	
}
