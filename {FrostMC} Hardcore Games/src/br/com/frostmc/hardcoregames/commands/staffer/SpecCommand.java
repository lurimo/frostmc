package br.com.frostmc.hardcoregames.commands.staffer;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.util.State;

public class SpecCommand extends BaseCommand {

	public SpecCommand() {
		super("spec", "activated spectate", Arrays.asList("spectate", "espec", "espectador"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isLight(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("§3§lSPEC §fUtilize o comando: /spec (on/off)");
				return true;
			}
			if (args.length == 1) {
				if (FrostHG.state != State.JOGO) {
					player.sendMessage("§3§lSPEC §fA partida precisa está no estágio jogo.");
					return true;
				}
				if (args[0].equalsIgnoreCase("on")) {
					if (FrostHG.getManager().getEspectadores().size() != 0) {
						for (UUID uuid : FrostHG.getManager().getEspectadores()) {
							Player target = Bukkit.getPlayer(uuid);
							if (target != null && target.isOnline()) {
								player.showPlayer(target);
						     }
						 }
						 player.sendMessage("§3§lSPEC §fEspectadores §a§lATIVADOS");
					} else {
						player.sendMessage("§3§lSPEC §fNenhum espectador encontrado.");
					}
				} else if (args[0].equalsIgnoreCase("off")) {
					if (FrostHG.getManager().getEspectadores().size() != 0) {
					    for (UUID uuid : FrostHG.getManager().getEspectadores()) {
						     Player target = Bukkit.getPlayer(uuid);
						     if (target != null && target.isOnline()) {
						    	 player.hidePlayer(target);
						     }
					    }
					    player.sendMessage("§3§lSPEC §fEspectadores §c§lDESATIVADOS");
					} else {
						player.sendMessage("§3§lSPEC §fNenhum espectador encontrado.");
					}
				}
			}
		} else {
			return true;
		}
		return false;
	}

}
