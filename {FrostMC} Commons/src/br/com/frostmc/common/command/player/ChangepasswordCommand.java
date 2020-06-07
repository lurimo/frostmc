package br.com.frostmc.common.command.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.util.Strings;

public class ChangepasswordCommand extends BaseCommand{

	public ChangepasswordCommand() {
		super("changepassword", "change password", Arrays.asList("trocarsenha", "unregister"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isDiretor(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length < 1) {
				player.sendMessage("§8[§b§lLOGIN§8] §fUtilize o comando: /changepassword (player) (nova senha)");
				return true;
			}
			if (args.length == 2) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					OfflinePlayer targetoff = Bukkit.getOfflinePlayer(args[0]);
					AccountBukkit account = new AccountBukkit(targetoff);
					if (!account.getAuthentication().exists()) {
						player.sendMessage("§8[§c§lERRO§8] §fEsTe jogador não foi encontrado em nosso banco de dados.");
						return true;
					}
					account.getAuthentication().setPassword(args[1]);
					account.getAuthentication().setSecurityCode();
					account.getAuthentication().save();
					player.sendMessage("");
					player.sendMessage("§8[§b§lLOGIN§8]");
					player.sendMessage("");
					player.sendMessage("§fVocê alterou a senha do jogador §a" + args[0] + " §fcom sucesso.");
					player.sendMessage("");
					player.sendMessage("§fNova senha: §a" + args[1]);
					player.sendMessage("§fNovo código de segurança: §a" + account.getAuthentication().getSecurityCode());
					player.sendMessage("");
					return true;
				}
				AccountBukkit account = new AccountBukkit(target);
				if (!account.getAuthentication().exists()) {
					player.sendMessage("3§lLOGIN §fEsse jogador não foi encontrado em nosso banco de dados.");
					return true;
				}
				account.getAuthentication().setPassword(args[1]);
				account.getAuthentication().setSecurityCode();
				account.getAuthentication().save();
				player.sendMessage("§3§lLOGIN §fVocê alterou a senha do jogador §a" + args[0] + " §fcom sucesso.");
				player.sendMessage("§3§lLOGIN §fNova senha: §a" + args[1]);
				player.sendMessage("§3§lLOGIN §fNovo codigo de segurança: §a" + account.getAuthentication().getSecurityCode());
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
