package br.com.frostmc.common.command.moderators.basic;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.util.Longs;
import br.com.frostmc.core.util.Strings;

public class BlacklistCommand extends BaseCommand {

	public BlacklistCommand() {
		super("blacklist", "blacklist de eventos", Arrays.asList("blacklisted"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			AccountBukkit account = new AccountBukkit(player);
			if (!BukkitMain.getPermissions().isMod(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("§3§lBLACKLIST §fUtilize o comando: /blacklist (player) (time)");
				return true;
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (!isOnline(target)) {
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
					AccountBukkit accountOffline = new AccountBukkit(offlinePlayer);
					if (!accountOffline.getGroups().exists()) {
						player.sendMessage("§c§lBLACKLIST §fEsse jogador não foi encontrado em nosso banco de dados.");
						return true;
					}
					if (accountOffline.getGroups().hasGroupPermission(account.getGroups().getGroupsType()) ) {
						sendError(commandSender, "Você não pode alterar o status desse jogador.");
						return true;
					}
					if (accountOffline.getBlacklist().exists()) {
						player.sendMessage("§c§lBLACKLIST §fEsse jogador já está adicionado na blacklist.");
						return true;
					}
					sendWarning(player.getName() + " adicionou o jogador " + offlinePlayer.getName() + " na blacklist de eventos.");
					accountOffline.getBlacklist().addBlacklistPermanent(player.getName(), accountOffline.getGroups().getIpAddrees());
					player.sendMessage("§a§lBLACKLIST §fVocê adicionou o jogador " + offlinePlayer.getName() + " §fna blacklist sem data de expiração.");
					return true;
				}
				AccountBukkit accountTarget = new AccountBukkit(target);
				if (!accountTarget.getGroups().exists()) {
					player.sendMessage("§c§lBLACKLIST §fEsse jogador não foi encontrado em nosso banco de dados.");
					return true;
				}
				if (accountTarget.getGroups().hasGroupPermission(account.getGroups().getGroupsType()) ) {
					sendError(commandSender, "Você não pode alterar o status desse jogador.");
					return true;
				}
				if (accountTarget.getBlacklist().exists()) {
					player.sendMessage("§c§lBLACKLIST §fEsse jogador já está adicionado na blacklist.");
					return true;
				}
				sendWarning(player.getName() + " adicionou o jogador " + target.getName() + " na blacklist de eventos.");
				accountTarget.getBlacklist().addBlacklistPermanent(player.getName(), accountTarget.getGroups().getIpAddrees());
				target.sendMessage("§a§lBLACKLIST §fVocê foi adicionado na blacklist de eventos. Foi injusto? faça unblacklist em nosso discord.");
				player.sendMessage("§a§lBLACKLIST §fVocê adicionou o jogador " + target.getName() + " §fna blacklist sem data de expiração.");
				return true;
			}
			if (args.length == 2) {
				String timeString = args[1];
				long time = 0L;
				try {
					time = Longs.fromString(timeString);
				} catch(Exception exception) {
					sendError(player, "Exemplo: 1d, 1h, 1m, 1s.");
					time = 0L;
					return true;
				}
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
					AccountBukkit accountOffline = new AccountBukkit(offlinePlayer);
					if (!accountOffline.getGroups().exists()) {
						player.sendMessage("§c§lBLACKLIST §fEsse jogador não foi encontrado em nosso banco de dados.");
						return true;
					}
					if (accountOffline.getGroups().hasGroupPermission(account.getGroups().getGroupsType()) ) {
						sendError(commandSender, "Você não pode alterar o status desse jogador.");
						return true;
					}
					if (accountOffline.getBlacklist().exists()) {
						player.sendMessage("§c§lBLACKLIST §fEsse jogador já está adicionado na blacklist.");
						return true;
					}
					sendWarning(player.getName() + " adicionou o jogador " + offlinePlayer.getName() + " na blacklist de eventos.");
					accountOffline.getBlacklist().addBlacklistTemporary(player.getName(), accountOffline.getGroups().getIpAddrees(), time);
					player.sendMessage("§a§lBLACKLIST §fVocê adicionou o jogador " + offlinePlayer.getName() + " §fna blacklist irá expirar em §e" + Longs.messageSmall(time) + "§f.");
					return true;
				}
				AccountBukkit accountTarget = new AccountBukkit(target);
				if (!accountTarget.getGroups().exists()) {
					player.sendMessage("§c§lBLACKLIST §fEsse jogador não foi encontrado em nosso banco de dados.");
					return true;
				}
				if (accountTarget.getGroups().hasGroupPermission(account.getGroups().getGroupsType()) ) {
					sendError(commandSender, "Você não pode alterar o status desse jogador.");
					return true;
				}
				if (accountTarget.getBlacklist().exists()) {
					player.sendMessage("§c§lBLACKLIST §fEsse jogador já está adicionado na blacklist.");
					return true;
				}
				sendWarning(player.getName() + " adicionou o jogador " + target.getName() + " na blacklist de eventos.");
				accountTarget.getBlacklist().addBlacklistTemporary(player.getName(), accountTarget.getGroups().getIpAddrees(), time);
				target.sendMessage("§a§lBLACKLIST §fVocê foi adicionado na blacklist de eventos. Foi injusto? faça unblacklist em nosso discord.");
				player.sendMessage("§a§lBLACKLIST §fVocê adicionou o jogador " + target.getName() + " §fna blacklist irá expirar em §e" + Longs.messageSmall(time) + "§f.");
				return true;
			}
		} else {
			CommandSender player = commandSender;
			if (args.length == 0) {
				player.sendMessage("§3§lBLACKLIST §fUtilize o comando: /blacklist (player) (time)");
				return true;
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (!isOnline(target)) {
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
					AccountBukkit accountOffline = new AccountBukkit(offlinePlayer);
					if (!accountOffline.getGroups().exists()) {
						player.sendMessage("§c§lBLACKLIST §fEsse jogador não foi encontrado em nosso banco de dados.");
						return true;
					}
					if (accountOffline.getBlacklist().exists()) {
						player.sendMessage("§c§lBLACKLIST §fEsse jogador já está adicionado na blacklist.");
						return true;
					}
					sendWarning(player.getName() + " adicionou o jogador " + offlinePlayer.getName() + " na blacklist de eventos.");
					accountOffline.getBlacklist().addBlacklistPermanent(player.getName(), accountOffline.getGroups().getIpAddrees());
					player.sendMessage("§a§lBLACKLIST §fVocê adicionou o jogador " + offlinePlayer.getName() + " §fna blacklist sem data de expiração.");
					return true;
				}
				AccountBukkit accountTarget = new AccountBukkit(target);
				if (!accountTarget.getGroups().exists()) {
					player.sendMessage("§c§lBLACKLIST §fEsse jogador não foi encontrado em nosso banco de dados.");
					return true;
				}
				if (accountTarget.getBlacklist().exists()) {
					player.sendMessage("§c§lBLACKLIST §fEsse jogador já está adicionado na blacklist.");
					return true;
				}
				sendWarning(player.getName() + " adicionou o jogador " + target.getName() + " na blacklist de eventos.");
				accountTarget.getBlacklist().addBlacklistPermanent(player.getName(), accountTarget.getGroups().getIpAddrees());
				target.sendMessage("§a§lBLACKLIST §fVocê foi adicionado na blacklist de eventos. Foi injusto? faça unblacklist em nosso discord.");
				player.sendMessage("§a§lBLACKLIST §fVocê adicionou o jogador " + target.getName() + " §fna blacklist sem data de expiração.");
				return true;
			}
			if (args.length == 2) {
				String timeString = args[2];
				long time = 0L;
				try {
					time = Longs.fromString(timeString);
				} catch(Exception exception) {
					sendError(player, "Exemplo: 1d, 1h, 1m, 1s.");
					time = 0L;
					return true;
				}
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
					AccountBukkit accountOffline = new AccountBukkit(offlinePlayer);
					if (!accountOffline.getGroups().exists()) {
						player.sendMessage("§c§lBLACKLIST §fEsse jogador não foi encontrado em nosso banco de dados.");
						return true;
					}
					if (accountOffline.getBlacklist().exists()) {
						player.sendMessage("§c§lBLACKLIST §fEsse jogador já está adicionado na blacklist.");
						return true;
					}
					sendWarning(player.getName() + " adicionou o jogador " + offlinePlayer.getName() + " na blacklist de eventos.");
					accountOffline.getBlacklist().addBlacklistTemporary(player.getName(), accountOffline.getGroups().getIpAddrees(), time);
					player.sendMessage("§a§lBLACKLIST §fVocê adicionou o jogador " + offlinePlayer.getName() + " §fna blacklist irá expirar em §e" + Longs.messageSmall(time) + "§f.");
					return true;
				}
				AccountBukkit accountTarget = new AccountBukkit(target);
				if (!accountTarget.getGroups().exists()) {
					player.sendMessage("§c§lBLACKLIST §fEsse jogador não foi encontrado em nosso banco de dados.");
					return true;
				}
				if (accountTarget.getBlacklist().exists()) {
					player.sendMessage("§c§lBLACKLIST §fEsse jogador já está adicionado na blacklist.");
					return true;
				}
				sendWarning(player.getName() + " adicionou o jogador " + target.getName() + " na blacklist de eventos.");
				accountTarget.getBlacklist().addBlacklistTemporary(player.getName(), accountTarget.getGroups().getIpAddrees(), time);
				target.sendMessage("§a§lBLACKLIST §fVocê foi adicionado na blacklist de eventos. Foi injusto? faça unblacklist em nosso discord.");
				player.sendMessage("§a§lBLACKLIST §fVocê adicionou o jogador " + target.getName() + " §fna blacklist irá expirar em §e" + Longs.messageSmall(time) + "§f.");
				return true;
			}
		}
		return false;
	}

}
