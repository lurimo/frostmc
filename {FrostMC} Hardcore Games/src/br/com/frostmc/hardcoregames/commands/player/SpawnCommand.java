package br.com.frostmc.hardcoregames.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.util.State;

public class SpawnCommand extends BaseCommand{

	public SpawnCommand() {
		super("spawn");
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (args.length == 0) {
				if (FrostHG.state!=State.INICIANDO) {
					return true;
				}
				player.teleport(FrostHG.getManager().getSpawn());
			}
		} else {
			return true;
		}
		return false;
	}

}
