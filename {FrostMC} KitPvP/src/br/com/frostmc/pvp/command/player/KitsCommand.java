package br.com.frostmc.pvp.command.player;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.json.JSONChatClickEventType;
import br.com.frostmc.common.json.JSONChatExtra;
import br.com.frostmc.common.json.JSONChatHoverEventType;
import br.com.frostmc.common.json.JSONChatMessage;
import br.com.frostmc.pvp.command.BaseCommand;
import br.com.frostmc.pvp.util.admin.AdminManager;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class KitsCommand extends BaseCommand {
	
	public KitsCommand() {
		super("kit", "selecione uma habilidade", Arrays.asList("kits", "habilidade"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if(isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (args.length == 0) {
				sendKits(player);
				return true;
			}
			if (!KitAPI.hasKit(player, Kits.NENHUM.getName())) {
				player.sendMessage("§8[§c§lERRO§8] §fVocê já está utilizando um kit, volte ao spawn e tente novamente.");
				return true;
			}
			if (!WarpsAPI.isInWarp(player, Warps.SPAWN)) {
				player.sendMessage("§8[§c§lERRO§8] §fVocê não pode trocar de kit neste local, volte ao §a§lSPAWN§f e tente novamente.");
				return true;
			}
			if(args.length == 1) {
				String kitName = args[0].toUpperCase();
				Kits kit = Kits.PVP;
				try {
					kit = Kits.valueOf(kitName);
				} catch(Exception exception) {
					kit = Kits.PVP;
					player.sendMessage("§8[§c§lERRO§8] §fEste kit não existe no nosso servidor.");
					return true;
				}
				if (AdminManager.isAdmin(player))
					return true;
				if(!KitAPI.hasKitPermission(player, kit)) {
					player.sendMessage("§8[§c§lERRO§8] §fVocê não possui este kit, você pode comprá-lo na loja.");
					return true;
				}
				KitAPI.setKit(player, kit);
				KitAPI.giveItens(player);
				KitAPI.giveKit(player, kit);
				player.sendMessage("§8[§6§lKIT-PVP§8] §fVocê selecionou o kit §a§l" + kit.getName() + "§f e foi teleportado a arena. Boa sorte!");
				return true;
			}
		} else {
			return true;
		}
		return false;
	}
	
	public JSONChatExtra create(Player player, Kits kit) {
		JSONChatExtra extra = new JSONChatExtra("§f, §a§l" + kit.getName());
		extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "§a§l" + kit.getName() + "\n" + String.valueOf(kit.getLore()).replace(",", "\n").replace("[", "").replace("]", ""));
		extra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/kit " + kit.getName());
		return extra;
	}

	
	public void sendKits(Player player) {
		
		int q = 0;
		for (Kits kits : Kits.values()) {
			if (!kits.equals(Kits.NENHUM) && KitAPI.hasKitPermission(player, kits)) {
				q++;
			}
		}
		
		JSONChatMessage message = new JSONChatMessage("§A§lKIT §fVocê possui §a" + q + " §fhabilidades: ", null, null);
		JSONChatExtra kitPrimary = new JSONChatExtra("§a§l" + Kits.PVP.getName());
		kitPrimary.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "§a§l" + Kits.PVP.getName() + "\n" + String.valueOf(Kits.PVP.getLore()).replace(",", "\n").replace("[", "").replace("]", ""));
		kitPrimary.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/kit pvp");
		message.addExtra(kitPrimary);
		Kits[] arrayOfKits;
		int a = (arrayOfKits = Kits.values()).length;
		int b = (arrayOfKits = Kits.values()).length;
		for (a = 0; a < b; a++) {
			Kits kits = arrayOfKits[a];
			if (!kits.equals(Kits.PVP) && KitAPI.hasKitPermission(player, kits)) {
				JSONChatExtra extra = create(player, kits);
				message.addExtra(extra);
			}
		}
		JSONChatExtra ponto = new JSONChatExtra("§f.");
		message.addExtra(ponto);
		message.sendToPlayer(player);
		player.sendMessage("§a§lDICA: §7Clique em cima do kit desejado para seleciona-lo.");
	}
	
}
