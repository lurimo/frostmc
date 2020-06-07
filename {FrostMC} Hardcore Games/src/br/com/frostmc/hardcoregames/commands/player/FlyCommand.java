package br.com.frostmc.hardcoregames.commands.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.util.State;

public class FlyCommand extends BaseCommand {

	public FlyCommand() {
		super("fly", "use flight", Arrays.asList("voar"));
	}
	
	public static ArrayList<UUID> usingFlight = new ArrayList<>();

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			
			if (!BukkitMain.getPermissions().isLight(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			
			if (FrostHG.state != State.INICIANDO) {
				player.sendMessage("§3§lFLY §fO modo fly não pode ser ativado pois a partida ja foi iniciada!");
				return true;
			}
			
			if (args.length == 0) {
				if (usingFlight.contains(player.getUniqueId())) {
					usingFlight.remove(player.getUniqueId());
					player.setAllowFlight(false);
					player.setFlying(false);
					player.sendMessage("§3§lFLY §fSeu modo fly foi §c§lDESATIVADO");
				} else {
					usingFlight.add(player.getUniqueId());
					player.setAllowFlight(true);
					player.setFlying(true);
					player.sendMessage("§3§lFLY §fSeu modo fly foi §a§lATIVADO");
				}
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
