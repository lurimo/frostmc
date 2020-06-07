package br.com.frostmc.common.command.player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.listener.PlayerChatListener;

public class TellCommand extends BaseCommand {

	public TellCommand() {
		super("tell", "mandar mensagens privadas", Arrays.asList("susurro"));
	}

	public static HashMap<UUID, UUID> lastTell = new HashMap<>();
	public static HashMap<UUID, TellModes> tellModes = new HashMap<>();
	
	public enum TellModes {
		ON, OFF;
	}
	
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (args.length == 0) {
				player.sendMessage("�8[�e�lTELL�8] �fUtilize o comando: /tell (jogador) (mensagem)");
				return true;
			}
			if (args.length == 1) {
				TellModes modes = TellModes.OFF;
				try {
					modes = TellModes.valueOf(args[0].toUpperCase());
				} catch (Exception exception) {
					player.sendMessage("�8[�c�lERRO�8] �fEste modo n�o foi identificado.");
					return true;
				}
				if (modes.equals(TellModes.ON)) {
					if (tellModes.get(player.getUniqueId()).equals(TellModes.ON)) {
						player.sendMessage("�8[�e�lTELL�8] �fO seu tell j� esta habilitado.");
						return true;
					}
					tellModes.put(player.getUniqueId(), TellModes.ON);
					player.sendMessage("�8[�e�lTELL�8] �fAs suas mensagems privadas foram habilitadas.");
					return true;
				} else if (modes.equals(TellModes.OFF)) {
					if (tellModes.get(player.getUniqueId()).equals(TellModes.OFF)) {
						player.sendMessage("�8[�e�lTELL�8] �fO seu tell j� esta desabilitado.");
						return true;
					}
					tellModes.put(player.getUniqueId(), TellModes.OFF);
					player.sendMessage("�8[�e�lTELL�8] �fAs suas mensagems privadas foram desabilitadas.");
					return true;
				} else {
					player.sendMessage("�8[�e�lTELL�8] �fUtilize o comando: /tell (jogador) (mensagem)");
					return true;
				}
			}
			String message = "";
			for(Integer integer = Integer.valueOf(1); integer < args.length; integer++) {
				message = message + args[integer] + " ";
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				player.sendMessage("�8[�c�lERRO�8] �fEste jogador encontra-se offline.");
				return true;
			}
			if (tellModes.get(player.getUniqueId()).equals(TellModes.OFF)) {
				player.sendMessage("�8[�c�lERRO�8] �fVoc� est� com o tell desativado, ative-o para mandar mensagens.");
				return true;
			}
			if (tellModes.get(target.getUniqueId()).equals(TellModes.OFF)) {
				player.sendMessage("�8[�c�lERRO�8] �fEsse jogador est� com o tell desabilitado.");
				return true;
			}
			for (String s : PlayerChatListener.tellBlocks) {
				if (message.toLowerCase().contains(s.toLowerCase())) {
					sendWarning("�8[G-TELL] �7Mensagem d� �f" + player.getName() + " �7para �f" + target.getName() + "�8] �7" + message);
				}
			}
			if (!lastTell.containsKey(player.getUniqueId()) || !lastTell.containsKey(target.getUniqueId())) {
				lastTell.put(player.getUniqueId(), target.getUniqueId());
				lastTell.put(target.getUniqueId(), player.getUniqueId());
			}
			target.sendMessage("�8[�7Mensagem d� �f" + player.getName() + "�8] �7" + message);
			player.sendMessage("�8[�7Mensagem para �f" + target.getName() + "�8] �7" + message);
		} else {
			return true;
		}
		return false;
	}

}