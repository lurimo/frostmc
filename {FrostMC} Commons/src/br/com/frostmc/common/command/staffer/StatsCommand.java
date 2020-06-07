package br.com.frostmc.common.command.staffer;

import java.util.Arrays;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.StatsType;

public class StatsCommand extends BaseCommand {

	public StatsCommand() {
		super("stats", "altere o status", Arrays.asList("status"));
	}
	
	public enum StatsModes {
		ADD, SET, REMOVE;
	}
	
	public enum StatsServer {
		KITPVP, HG, GLADIATOR, ONEVSONE, GLOBAL, RESET;
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			AccountBukkit account = new AccountBukkit(player);
			if (!BukkitMain.getPermissions().isAdmin(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("§3§lSTATS §fUtilize o comando: /stats (player) (server/reset) (mode) (add/set/remove) (quantity)");
				return true;
			}
			if (args.length == 1) {
				Player target = getPlayer(args[0]);
				if (!isOnline(target)) {
					OfflinePlayer targetOffline = getOfflinePlayer(args[0]);
					AccountBukkit accountOff = new AccountBukkit(targetOffline);
					if (!accountOff.getStatsGlobal().exists()) {
						sendError(commandSender, "Esse jogador não foi encontrado em nosso banco de dados.");
						return true;
					}
					if (accountOff.getGroups().hasGroupPermission(account.getGroups().getGroupsType()) ) {
						sendError(commandSender, "Você não pode alterar o status desse jogador.");
						return true;
					}
					player.sendMessage("§3§lSTATS §fUtilize o comando: /stats (player) (server/reset) (mode) (add/set/remove) (quantity)");
					return true;
				}
				AccountBukkit accountOn = new AccountBukkit(target);
				if (!accountOn.getStatsGlobal().exists()) {
					sendError(commandSender, "Esse jogador não foi encontrado em nosso banco de dados.");
					return true;
				}
				if (accountOn.getGroups().hasGroupPermission(account.getGroups().getGroupsType()) ) {
					sendError(commandSender, "Você não pode alterar o status desse jogador.");
					return true;
				}
				player.sendMessage("§3§lSTATS §fUtilize o comando: /stats (player) (server/reset) (mode) (add/set/remove) (quantity)");
				return true;
			}
			if (args.length == 2) {
				Player target = getPlayer(args[0]);
				if (!isOnline(target)) {
					OfflinePlayer targetOffline = getOfflinePlayer(args[0]);
					AccountBukkit accountOff = new AccountBukkit(targetOffline);
					accountOff.getBuy_kit_pvp().delete();
					accountOff.getBuy_kit_hg().delete();
					accountOff.getDiaryHG().delete();
					accountOff.getDiaryPvP().delete();
					accountOff.getDoubleXP().delete();
					accountOff.getBlacklist().delete();
					accountOff.getPunishments().delete();
					accountOff.getStatsGlobal().delete();
					accountOff.getStatsPvP().delete();
					accountOff.getStatsHG().delete();
					accountOff.getStatsGladiator().delete();
					accountOff.getStats1v1().delete();
					accountOff.getGroups().delete();
					player.sendMessage("§a§lSTATS §fVocê resetou a conta do jogador " + targetOffline.getName());
					return true;
				} else {
					AccountBukkit accountOn = new AccountBukkit(target);
					accountOn.getBuy_kit_pvp().delete();
					accountOn.getBuy_kit_hg().delete();
					accountOn.getDiaryHG().delete();
					accountOn.getDiaryPvP().delete();
					accountOn.getDoubleXP().delete();
					accountOn.getBlacklist().delete();
					accountOn.getPunishments().delete();
					accountOn.getStatsGlobal().delete();
					accountOn.getStatsPvP().delete();
					accountOn.getStatsHG().delete();
					accountOn.getStatsGladiator().delete();
					accountOn.getStats1v1().delete();
					accountOn.getGroups().delete();
					accountOn.getPlayer().kickPlayer("§3§lRESET" + "\n" + "\n" + "§fVocê teve sua conta resetada!" + "\n" + "\n" + "§3" + Strings.getWebsite());
					player.sendMessage("§a§lSTATS §fVocê resetou a conta do jogador " + target.getName());
					return true;
				}
			}
			if (args.length == 3) {
				player.sendMessage("§3§lSTATS §fUtilize o comando: /stats (player) (server/reset) (mode) (add/set/remove) (quantity)");
				return true;
			}
			if (args.length == 4) {
				player.sendMessage("§3§lSTATS §fUtilize o comando: /stats (player) (server/reset) (mode) (add/set/remove) (quantity)");
				return true;
			}
			Player target = getPlayer(args[0]);
			if (!isOnline(target)) {
				OfflinePlayer targetOffline = getOfflinePlayer(args[0]);
				AccountBukkit accountOff = new AccountBukkit(targetOffline);
				StatsServer stats = StatsServer.KITPVP;
				try {
					stats = StatsServer.valueOf(args[1].toUpperCase().toString());
				} catch (Exception e) {
					sendError(commandSender, "O servidor " + args[1] + " não existe.");
					return true;
				}
				StatsType type = StatsType.KILLS;
				try {
					type = StatsType.valueOf(args[2].toUpperCase().toString());
				} catch (Exception e) {
					sendError(commandSender, "O tipo " + args[2] + " não existe.");
					return true;
				}
				StatsModes modes = StatsModes.ADD;
				try {
					modes = StatsModes.valueOf(args[3].toUpperCase().toString());
				} catch (Exception e) {
					sendError(commandSender, "O modo " + args[3] + " não existe.");
					return true;
				}
				if (!isInteger(args[4])) {
					sendError(commandSender, "A quantia precisa ser coloca com números!");
					return true;
				}
				Integer value = Integer.valueOf(args[4]);
				if (value <= 0) {
					sendError(commandSender, "A quantia precisa ser maior que zero!");
					return true;
				}
				metodoStats(accountOff, modes, stats, type, value);
				Gamer gamer = BukkitMain.getGamerManager().getGamer(targetOffline);
				gamer.loadProfile();
				return true;
			}
			AccountBukkit accountOn = new AccountBukkit(target);
			StatsServer stats = StatsServer.KITPVP;
			try {
				stats = StatsServer.valueOf(args[1].toUpperCase().toString());
			} catch (Exception e) {
				e.printStackTrace();
				sendError(commandSender, "O servidor " + args[1] + " não existe.");
				return true;
			}
			StatsType type = StatsType.KILLS;
			try {
				type = StatsType.valueOf(args[2].toUpperCase().toString());
			} catch (Exception e) {
				sendError(commandSender, "O tipo " + args[2] + " não existe.");
				return true;
			}
			StatsModes modes = StatsModes.ADD;
			try {
				modes = StatsModes.valueOf(args[3].toUpperCase().toString());
			} catch (Exception e) {
				sendError(commandSender, "O modo " + args[3] + " não existe.");
				return true;
			}
			if (!isInteger(args[4])) {
				sendError(commandSender, "A quantia precisa ser coloca com números!");
				return true;
			}
			Integer value = Integer.valueOf(args[4]);
			if (value <= 0) {
				sendError(commandSender, "A quantia precisa ser maior que zero!");
				return true;
			}
			metodoStats(accountOn, modes, stats, type, value);
			Gamer gamer = BukkitMain.getGamerManager().getGamer(target);
			gamer.loadProfile();
			return true;
		} else {
			CommandSender player = commandSender;
			if (args.length == 0) {
				player.sendMessage("§3§lSTATS §fUtilize o comando: /stats (player) (server/reset) (mode) (add/set/remove) (quantity)");
				return true;
			}
			if (args.length == 1) {
				Player target = getPlayer(args[0]);
				if (!isOnline(target)) {
					OfflinePlayer targetOffline = getOfflinePlayer(args[0]);
					AccountBukkit accountOff = new AccountBukkit(targetOffline);
					if (!accountOff.getStatsGlobal().exists()) {
						sendError(commandSender, "Esse jogador não foi encontrado em nosso banco de dados.");
						return true;
					}
					player.sendMessage("§3§lSTATS §fUtilize o comando: /stats (player) (server/reset) (mode) (add/set/remove) (quantity)");
					return true;
				}
				AccountBukkit accountOn = new AccountBukkit(target);
				if (!accountOn.getStatsGlobal().exists()) {
					sendError(commandSender, "Esse jogador não foi encontrado em nosso banco de dados.");
					return true;
				}
				player.sendMessage("§3§lSTATS §fUtilize o comando: /stats (player) (server/reset) (mode) (add/set/remove) (quantity)");
				return true;
			}
			if (args.length == 2) {
				Player target = getPlayer(args[0]);
				if (!isOnline(target)) {
					OfflinePlayer targetOffline = getOfflinePlayer(args[0]);
					AccountBukkit accountOff = new AccountBukkit(targetOffline);
					accountOff.getBuy_kit_pvp().delete();
					accountOff.getBuy_kit_hg().delete();
					accountOff.getDiaryHG().delete();
					accountOff.getDiaryPvP().delete();
					accountOff.getDoubleXP().delete();
					accountOff.getBlacklist().delete();
					accountOff.getPunishments().delete();
					accountOff.getStatsGlobal().delete();
					accountOff.getStatsPvP().delete();
					accountOff.getStatsHG().delete();
					accountOff.getStatsGladiator().delete();
					accountOff.getStats1v1().delete();
					accountOff.getGroups().delete();
					player.sendMessage("§a§lSTATS §fVocê resetou a conta do jogador " + targetOffline.getName());
					return true;
				} else {
					AccountBukkit accountOn = new AccountBukkit(target);
					accountOn.getBuy_kit_pvp().delete();
					accountOn.getBuy_kit_hg().delete();
					accountOn.getDiaryHG().delete();
					accountOn.getDiaryPvP().delete();
					accountOn.getDoubleXP().delete();
					accountOn.getBlacklist().delete();
					accountOn.getPunishments().delete();
					accountOn.getStatsGlobal().delete();
					accountOn.getStatsPvP().delete();
					accountOn.getStatsHG().delete();
					accountOn.getStatsGladiator().delete();
					accountOn.getStats1v1().delete();
					accountOn.getGroups().delete();
					accountOn.getPlayer().kickPlayer("§3§lRESET" + "\n" + "\n" + "§fVocê teve sua conta resetada!" + "\n" + "\n" + "§3" + Strings.getWebsite());
					player.sendMessage("§a§lSTATS §fVocê resetou a conta do jogador " + target.getName());
					return true;
				}
			}
			if (args.length == 3) {
				player.sendMessage("§3§lSTATS §fUtilize o comando: /stats (player) (server/reset) (mode) (add/set/remove) (quantity)");
				return true;
			}
			if (args.length == 4) {
				player.sendMessage("§3§lSTATS §fUtilize o comando: /stats (player) (server/reset) (mode) (add/set/remove) (quantity)");
				return true;
			}
			Player target = getPlayer(args[0]);
			if (!isOnline(target)) {
				OfflinePlayer targetOffline = getOfflinePlayer(args[0]);
				AccountBukkit accountOff = new AccountBukkit(targetOffline);
				StatsServer stats = StatsServer.KITPVP;
				try {
					stats = StatsServer.valueOf(args[1].toUpperCase().toString());
				} catch (Exception e) {
					sendError(commandSender, "O servidor " + args[1] + " não existe.");
					return true;
				}
				StatsType type = StatsType.KILLS;
				try {
					type = StatsType.valueOf(args[2].toUpperCase().toString());
				} catch (Exception e) {
					sendError(commandSender, "O tipo " + args[2] + " não existe.");
					return true;
				}
				StatsModes modes = StatsModes.ADD;
				try {
					modes = StatsModes.valueOf(args[3].toUpperCase().toString());
				} catch (Exception e) {
					sendError(commandSender, "O modo " + args[3] + " não existe.");
					return true;
				}
				if (!isInteger(args[4])) {
					sendError(commandSender, "A quantia precisa ser coloca com números!");
					return true;
				}
				Integer value = Integer.valueOf(args[4]);
				if (value <= 0) {
					sendError(commandSender, "A quantia precisa ser maior que zero!");
					return true;
				}
				metodoStats(accountOff, modes, stats, type, value);
				return true;
			}
			AccountBukkit accountOn = new AccountBukkit(target);
			StatsServer stats = StatsServer.KITPVP;
			try {
				stats = StatsServer.valueOf(args[1].toUpperCase().toString());
			} catch (Exception e) {
				e.printStackTrace();
				sendError(commandSender, "O servidor " + args[1] + " não existe.");
				return true;
			}
			StatsType type = StatsType.KILLS;
			try {
				type = StatsType.valueOf(args[2].toUpperCase().toString());
			} catch (Exception e) {
				sendError(commandSender, "O tipo " + args[2] + " não existe.");
				return true;
			}
			StatsModes modes = StatsModes.ADD;
			try {
				modes = StatsModes.valueOf(args[3].toUpperCase().toString());
			} catch (Exception e) {
				sendError(commandSender, "O modo " + args[3] + " não existe.");
				return true;
			}
			if (!isInteger(args[4])) {
				sendError(commandSender, "A quantia precisa ser coloca com números!");
				return true;
			}
			Integer value = Integer.valueOf(args[4]);
			if (value <= 0) {
				sendError(commandSender, "A quantia precisa ser maior que zero!");
				return true;
			}
			metodoStats(accountOn, modes, stats, type, value);
		}
		return false;
	}
	
	private static void metodoStats(AccountBukkit account, StatsModes modes, StatsServer stats, StatsType type, int value) {
		if (stats.equals(StatsServer.KITPVP)) {
			if (type.equals(StatsType.KILLS)) {
				if (modes.equals(StatsModes.SET)) {
					account.getStatsPvP().setKills(value);
				} else if (modes.equals(StatsModes.ADD)) {
					account.getStatsPvP().addKills(value);
				} else if (modes.equals(StatsModes.REMOVE)) {
					account.getStatsPvP().removeKills(value);
				}
			} else if (type.equals(StatsType.DEATHS)) {
				if (modes.equals(StatsModes.SET)) {
					account.getStatsPvP().setDeaths(value);
				} else if (modes.equals(StatsModes.ADD)) {
					account.getStatsPvP().addDeaths(value);
				} else if (modes.equals(StatsModes.REMOVE)) {
					account.getStatsPvP().removeDeaths(value);
				}
			} else {
				account.getPlayer().sendMessage("§c§lSTATS §fEsse método não está disponível para o servidor de Kit PvP.");
			}
			account.getStatsPvP().save();
			if (modes.equals(StatsModes.SET)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi setado §e" + value + " §f" + type.getName() + ".");
			} else if (modes.equals(StatsModes.ADD)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi adicionado §e" + value + " §f" + type.getName() + ".");
			} else if (modes.equals(StatsModes.REMOVE)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi removido §e" + value + " §f" + type.getName() + ".");
			}
			return;
		}
		if (stats.equals(StatsServer.HG)) {
			if (type.equals(StatsType.KILLS)) {
				if (modes.equals(StatsModes.SET)) {
					account.getStatsHG().setKills(value);
				} else if (modes.equals(StatsModes.ADD)) {
					account.getStatsHG().addKills(value);
				} else if (modes.equals(StatsModes.REMOVE)) {
					account.getStatsHG().removeKills(value);
				}
			} else if (type.equals(StatsType.DEATHS)) {
				if (modes.equals(StatsModes.SET)) {
					account.getStatsHG().setDeaths(value);
				} else if (modes.equals(StatsModes.ADD)) {
					account.getStatsHG().addDeaths(value);
				} else if (modes.equals(StatsModes.REMOVE)) {
					account.getStatsHG().removeDeaths(value);
				}
			} else if (type.equals(StatsType.WINS)) {
				if (modes.equals(StatsModes.SET)) {
					account.getStatsHG().setWins(value);
				} else if (modes.equals(StatsModes.ADD)) {
					account.getStatsHG().addWins(value);
				} else if (modes.equals(StatsModes.REMOVE)) {
					account.getStatsHG().removeWins(value);
				}
			} else {
				account.getPlayer().sendMessage("§c§lSTATS §fEsse método não está disponível para o servidor de Hardcore Games.");
			}
			account.getStatsHG().save();
			if (modes.equals(StatsModes.ADD)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi setado §e" + value + " §f" + type.getName() + ".");
			} else if (modes.equals(StatsModes.ADD)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi adicionado §e" + value + " §f" + type.getName() + ".");
			} else if (modes.equals(StatsModes.REMOVE)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi removido §e" + value + " §f" + type.getName() + ".");
			}
			return;
		}
		if (stats.equals(StatsServer.GLADIATOR)) {
			if (type.equals(StatsType.VICTORY)) {
				if (modes.equals(StatsModes.SET)) {
					account.getStatsGladiator().setVictory(value);
				} else if (modes.equals(StatsModes.ADD)) {
					account.getStatsGladiator().addVictory(value);
				} else if (modes.equals(StatsModes.REMOVE)) {
					account.getStatsGladiator().removeVictory(value);
				}
			} else if (type.equals(StatsType.DEFEAT)) {
				if (modes.equals(StatsModes.SET)) {
					account.getStatsGladiator().setDefeat(value);
				} else if (modes.equals(StatsModes.ADD)) {
					account.getStatsGladiator().addDefeat(value);
				} else if (modes.equals(StatsModes.REMOVE)) {
					account.getStatsGladiator().removeDefeat(value);
				}
			} else {
				account.getPlayer().sendMessage("§c§lSTATS §fEsse método não está disponível para o servidor de Gladiator.");
			}
			account.getStatsHG().save();
			if (modes.equals(StatsModes.ADD)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi setado §e" + value + " §f" + type.getName() + ".");
			} else if (modes.equals(StatsModes.ADD)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi adicionado §e" + value + " §f" + type.getName() + ".");
			} else if (modes.equals(StatsModes.REMOVE)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi removido §e" + value + " §f" + type.getName() + ".");
			}
			return;
		}
		if (stats.equals(StatsServer.ONEVSONE)) {
			if (type.equals(StatsType.VICTORY)) {
				if (modes.equals(StatsModes.SET)) {
					account.getStats1v1().setVictory(value);
				} else if (modes.equals(StatsModes.ADD)) {
					account.getStats1v1().addVictory(value);
				} else if (modes.equals(StatsModes.REMOVE)) {
					account.getStats1v1().removeVictory(value);
				}
			} else if (type.equals(StatsType.DEFEAT)) {
				if (modes.equals(StatsModes.SET)) {
					account.getStats1v1().setDefeat(value);
				} else if (modes.equals(StatsModes.ADD)) {
					account.getStats1v1().addDefeat(value);
				} else if (modes.equals(StatsModes.REMOVE)) {
					account.getStats1v1().removeDefeat(value);
				}
			} else {
				account.getPlayer().sendMessage("§c§lSTATS §fEsse método não está disponível para o servidor de 1v1.");
			}
			account.getStatsHG().save();
			if (modes.equals(StatsModes.ADD)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi setado §e" + value + " §f" + type.getName() + ".");
			} else if (modes.equals(StatsModes.ADD)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi adicionado §e" + value + " §f" + type.getName() + ".");
			} else if (modes.equals(StatsModes.REMOVE)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi removido §e" + value + " §f" + type.getName() + ".");
			}
			return;
		}
		if (stats.equals(StatsServer.GLOBAL)) {
			if (type.equals(StatsType.FICHAS)) {
				if (modes.equals(StatsModes.SET)) {
					account.getStatsGlobal().setFichas(value);
				} else if (modes.equals(StatsModes.ADD)) {
					account.getStatsGlobal().addFichas(value);
				} else if (modes.equals(StatsModes.REMOVE)) {
					account.getStatsGlobal().removeFichas(value);
				}
			} else if (type.equals(StatsType.MOEDAS)) {
				if (modes.equals(StatsModes.ADD)) {
					account.getStatsGlobal().setMoedas(value);
				} else if (modes.equals(StatsModes.ADD)) {
					account.getStatsGlobal().addMoedas(value);
				} else if (modes.equals(StatsModes.REMOVE)) {
					account.getStatsGlobal().removeMoedas(value);
				}
			} else if (type.equals(StatsType.XP)) {
				if (modes.equals(StatsModes.SET)) {
					account.getStatsGlobal().setXp(value);
				} else if (modes.equals(StatsModes.ADD)) {
					account.getStatsGlobal().addXp(value);
				} else if (modes.equals(StatsModes.REMOVE)) {
					account.getStatsGlobal().removeXp(value);
				}
			} else {
				account.getPlayer().sendMessage("§c§lSTATS §fEsse método não está disponível para o servidor ao todo.");
			}
			if (modes.equals(StatsModes.SET)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi setado §e" + value + " §f" + type.getName() + ".");
			} else if (modes.equals(StatsModes.ADD)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi adicionado §e" + value + " §f" + type.getName() + ".");
			} else if (modes.equals(StatsModes.REMOVE)) {
				account.getPlayer().sendMessage("§a§lSTATS §fEm sua conta foi removido §e" + value + " §f" + type.getName() + ".");
			}
			account.getStatsGlobal().save();
			return;
		}
	}

}