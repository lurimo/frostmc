package br.com.frostmc.hardcoregames.commands.player;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.json.JSONChatClickEventType;
import br.com.frostmc.common.json.JSONChatExtra;
import br.com.frostmc.common.json.JSONChatHoverEventType;
import br.com.frostmc.common.json.JSONChatMessage;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class KitsCommand extends BaseCommand{

	public KitsCommand() {
		super("kits", "select hability", Arrays.asList("kit"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (args.length == 0) {
				if (FrostHG.state != State.INICIANDO) {
					if (!BukkitMain.getPermissions().isLight(player)) {
						player.sendMessage("§3§lKIT §fO torneio já foi iniciado.");
						return true;
					}
					if (!FrostHG.getManager().getKitAPI().hasKit(player, Kits.NENHUM)) {
						player.sendMessage("§3§lKIT §fVocê já selecionou um kit!");
						return true;
					}
				}
				if (FrostHG.getManager().disablekit == true) {
					player.sendMessage("§3§lKIT §fNenhum kit disponivel no momento.");
					return true;
				}
				sendKits(player);
				return true;
			}
			if (args.length == 1) {
				Kits kits = Kits.NENHUM;
				try {
					kits = Kits.valueOf(args[0].toUpperCase());
				} catch(Exception exception) {
					kits = Kits.NENHUM;
					player.sendMessage("§4§lERRO §fNão foi póssivel encontrar o kit citado.");
					return true;
				}
				if (!FrostHG.getManager().getKitAPI().hasKitPermission(player, kits)) {
					player.sendMessage("§4§lPERMISSÃO §fVocê não póssui permissão para selecionar esse kit.");
					return true;
				}
				if (FrostHG.getManager().kitsDesativados.contains(kits.name().toLowerCase())) {
    				player.sendMessage("§3§lKIT §fO kit §c" + kits.getName() + " §festa §c§lDESATIVADO");
    			    return true ;
    			}
				if (!FrostHG.getManager().getKitAPI().Kit.containsKey(player.getUniqueId())) {
					FrostHG.getManager().getKitAPI().setKit(player, Kits.NENHUM);
				}
				FrostHG.getManager().getKitAPI().kitSet(player, kits);
				Scoreboarding.updateKit(player, player.getScoreboard());
				return true;
			}
		} else {
			return true;
		}
		return false;
	}
	
	public JSONChatExtra create(Player player, Kits kit) {
		JSONChatExtra extra = new JSONChatExtra("§f, §a" + kit.getName());
		extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "§a" + kit.getName() + "\n" + String.valueOf(kit.getLore()).replace(",", "\n").replace("[", "").replace("]", ""));
		extra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/kit " + kit.getName());
		return extra;
	}

	
	public void sendKits(Player player) {
		
		int q = 0;
		for (Kits kits : Kits.values()) {
			if (FrostHG.getManager().getKitAPI().hasKitPermission(player, kits)) {
				q++;
			} else if (FrostHG.getManager().disablekit == true) {
				q = 0;
			}
		}
		
		JSONChatMessage message;
		message = new JSONChatMessage("§b§lKIT §fVocê póssui §a" + q + " §fhabilidades: ", null, null);
		JSONChatExtra membro = new JSONChatExtra("§a" + Kits.NENHUM.getName());
		membro.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "§a" + Kits.NENHUM.getName() + "\n" + String.valueOf(Kits.NENHUM.getLore()).replace(",", "\n").replace("[", "").replace("]", ""));
		membro.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/kit nenhum");
		message.addExtra(membro);
		Kits[] arrayOfKits;
		int a = (arrayOfKits = Kits.values()).length;
		int b = (arrayOfKits = Kits.values()).length;
		for (a = 0; a < b; a++) {
			Kits kits = arrayOfKits[a];
			if (!kits.equals(Kits.NENHUM) && FrostHG.getManager().disablekit != true && FrostHG.getManager().getKitAPI().hasKitPermission(player, kits)) {
				JSONChatExtra extra = create(player, kits);
				message.addExtra(extra);
			}
		}
		JSONChatExtra ponto = new JSONChatExtra("§f.");
		message.addExtra(ponto);
		message.sendToPlayer(player);
		player.sendMessage("§7§nDICA§7: Clique em cima do kit desejado para seleciona-lo.");
	}

}
