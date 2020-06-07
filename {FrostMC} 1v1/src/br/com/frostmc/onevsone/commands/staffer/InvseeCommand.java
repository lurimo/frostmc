package br.com.frostmc.onevsone.commands.staffer;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.onevsone.commands.BaseCommand;

public class InvseeCommand extends BaseCommand {

	public InvseeCommand() {
		super("invsee", "entre/saia do modo invisivel", Arrays.asList("inv", "playerinv", "invplayer"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isMod(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("�8[�c�lINVSEE�8] �fUtilize o comando: �7/invsee (player)");
				return true;
			}
			if (args.length == 1) {
				Player targeton = Bukkit.getPlayer(args[0]);
				if (targeton == null) {
					player.sendMessage("�8[�c�lINVSEE�8] �fEste jogador n�o foi encontrado no servidor.");
					return true;
				}
				if (targeton.equals(player)) {
					sendError(player, "�fVoc� n�o pode interagir com si mesmo.");
					return true;
				}
				player.openInventory(targeton.getInventory());
				player.sendMessage("�8[�c�lINVSEE�8] �fVoc� abriu o inventario do jogador " + player.getName());
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
