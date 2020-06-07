package br.com.frostmc.pvp.command.player;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.pvp.command.BaseCommand;
import br.com.frostmc.pvp.util.warp.WarpManager;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class WarpCommand extends BaseCommand {
	
	public WarpCommand() {
		super("warp", "selecione uma warp", Arrays.asList("warps", "warpselector"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if(isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if(args.length == 0) {
				player.sendMessage("�8[�6�lWARP�8] �fNosso servidor possui diversos tipos de arenas, para se teleportar a alguma utilize o comando: /warp [warp]");
				return true;
			}
			if (args.length == 1) {
				Warps warp = Warps.FPS;
				try {
					warp = Warps.valueOf(args[0].toUpperCase());
				} catch(Exception exception) {
					if(args[0].toLowerCase().equals("1v1")) {
						warp = Warps.ONEVSONE;
						WarpManager.send(player, Warps.ONEVSONE);
						return true;
					} else if(args[0].toLowerCase().equals("arena")) {
						sendError(player, "A warp citada n�o existe.");
						return true;
					} else {
						sendError(player, "A warp citada n�o existe.");
						return true;
					}
				}
				if(WarpsAPI.isInWarp(player, warp)) {
					player.sendMessage("�8[�c�lERRO�8] �fVoc� j� est� nessa �aWARP�7.");
					return true;
				}
				WarpManager.send(player, warp);
				player.sendMessage(" ");
				player.sendMessage("�8[�e�lWARP�8] �fVoc� est� sendo enviado para warp �a" +  Warps.valueOf(warp.name().toLowerCase()) + "�f.");
				player.sendMessage(" ");
			}
			return true;
		} else {
			return true;
		}
	}

}