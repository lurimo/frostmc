package br.com.frostmc.common.command.moderators.basic;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.util.Strings;

public class UnblacklistCommand extends BaseCommand {
	
	public UnblacklistCommand() {
		super("unblacklist", "unblacklist de eventos", Arrays.asList("blacklisted"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isModPlus(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("§3§lUNBLACKLIST §fUtilize o comando: /blacklist (player)");
				return true;
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
					AccountBukkit accountOffline = new AccountBukkit(offlinePlayer);
					if (!accountOffline.getGroups().exists()) {
						player.sendMessage("§c§lBLACKLIST §fEsse jogador não foi encontrado em nosso banco de dados.");
						return true;
					}
					if (!accountOffline.getBlacklist().exists()) {
						player.sendMessage("§c§lBLACKLIST §fEsse jogador não está adicionado na blacklist.");
						return true;
					}
					accountOffline.getBlacklist().unBlacklist();
					sendWarning(player.getName() + " tirou o jogador " + offlinePlayer.getName() + " da blacklist");
					player.sendMessage("§a§lBLACKLIST §fVocê removeu o jogador " + offlinePlayer.getName() + " da blacklist.");
					return true;
				}
				AccountBukkit accountTarget = new AccountBukkit(target);
				if (!accountTarget.getGroups().exists()) {
					player.sendMessage("§c§lBLACKLIST §fEsse jogador não foi encontrado em nosso banco de dados.");
					return true;
				}
				if (!accountTarget.getBlacklist().exists()) {
					player.sendMessage("§c§lBLACKLIST §fEsse jogador não está adicionado na blacklist.");
					return true;
				}
				accountTarget.getBlacklist().unBlacklist();
				sendWarning(player.getName() + " tirou o jogador " + target.getName() + " da blacklist");
				target.sendMessage("§a§lBLACKLIST §fVocê foi removido da blacklist de eventos. Espero que não faça mais nenhuma besteira.");
				player.sendMessage("§a§lBLACKLIST §fVocê removeu o jogador " + target.getName() + " da blacklist.");
				return true;
			}
		} else {
			CommandSender player = commandSender;
			if (args.length == 0) {
				player.sendMessage("§3§lUNBLACKLIST §fUtilize o comando: /blacklist (player)");
				return true;
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
					AccountBukkit accountOffline = new AccountBukkit(offlinePlayer);
					if (!accountOffline.getGroups().exists()) {
						player.sendMessage("§c§lBLACKLIST §fEsse jogador não foi encontrado em nosso banco de dados.");
						return true;
					}
					if (!accountOffline.getBlacklist().exists()) {
						player.sendMessage("§c§lBLACKLIST §fEsse jogador não está adicionado na blacklist.");
						return true;
					}
					accountOffline.getBlacklist().unBlacklist();
					sendWarning(player.getName() + " tirou o jogador " + offlinePlayer.getName() + " da blacklist");
					player.sendMessage("§a§lBLACKLIST §fVocê removeu o jogador " + offlinePlayer.getName() + " da blacklist.");
					return true;
				}
				AccountBukkit accountTarget = new AccountBukkit(target);
				if (!accountTarget.getGroups().exists()) {
					player.sendMessage("§c§lBLACKLIST §fEsse jogador não foi encontrado em nosso banco de dados.");
					return true;
				}
				if (!accountTarget.getBlacklist().exists()) {
					player.sendMessage("§c§lBLACKLIST §fEsse jogador não está adicionado na blacklist.");
					return true;
				}
				accountTarget.getBlacklist().unBlacklist();
				sendWarning(player.getName() + " tirou o jogador " + target.getName() + " da blacklist");
				target.sendMessage("§a§lBLACKLIST §fVocê foi removido da blacklist de eventos. Espero que não faça mais nenhuma besteira.");
				player.sendMessage("§a§lBLACKLIST §fVocê removeu o jogador " + target.getName() + " da blacklist.");
				return true;
			}
		}
		return false;
	}

}
