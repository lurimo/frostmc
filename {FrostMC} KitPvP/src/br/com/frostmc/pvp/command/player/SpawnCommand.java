package br.com.frostmc.pvp.command.player;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.pvp.command.BaseCommand;
import br.com.frostmc.pvp.util.warp.WarpManager;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class SpawnCommand extends BaseCommand{

	public SpawnCommand() {
		super("spawn", "volte para o spawn", Arrays.asList("kill", "spawnar"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (args.length == 0) {
				if (WarpsAPI.isInWarp(player, Warps.SPAWN)) {
					player.sendMessage("§8[§6§lWARP§8] §fVocê já está na warp spawn.");
					return true;
				}
				WarpManager.send(player, Warps.SPAWN);
				player.sendMessage("§8[§6§lWARP§8] §fVocê foi enviado ao §aspawn§f.");
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
