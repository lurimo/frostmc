package br.com.frostmc.common.command.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.command.player.TellCommand.TellModes;
import br.com.frostmc.common.listener.PlayerChatListener;

public class ReplyCommand extends BaseCommand {

	public ReplyCommand() {
		super("reply", "responder as mensagens privadas", Arrays.asList("responder", "r"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (args.length == 0) {
				player.sendMessage("�8[�e�lTELL�8] �fUtilize o comando: /r (message)");
				return true;
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(TellCommand.lastTell.get(player.getUniqueId()));
				if (target == null) {
					player.sendMessage("�8[�e�lTELL�8] �fNingu�m te enviou mensagens recentemente.");
					return true;
				}
				if (target.getName() == null) {
					player.sendMessage("�8[�c�lERRO�8] �fEste jogador n�o foi encontrado em nossa rede.");
					return true;
				}
				String message = "";
				for(Integer integer = Integer.valueOf(0); integer < args.length; integer++) {
					message = message + args[integer] + " ";
				}
				if (TellCommand.tellModes.get(target.getUniqueId()).equals(TellModes.OFF)) {
					player.sendMessage("�8[�c�lERRO�8] �fEste jogador desativou o recebimento de mensagens privadas.");
					return true;
				}
				if (TellCommand.tellModes.get(player.getUniqueId()).equals(TellModes.OFF)) {
					player.sendMessage("�8[�c�lERRO�8] �fVoc� est� com o tell desativado, ative-o para mandar mensagens.");
					return true;
				}
				for (String s : PlayerChatListener.tellBlocks) {
					if (message.toLowerCase().contains(s.toLowerCase())) {
						sendWarning("�8[G-TELL] �7Mensagem d� �f" + player.getName() + " �7para �f" + target.getName() + " �7" + message);
					}
				}
				if (TellCommand.lastTell.containsKey(player.getUniqueId()) || TellCommand.lastTell.containsKey(target.getUniqueId())) {
					TellCommand.lastTell.remove(player.getUniqueId());
					TellCommand.lastTell.remove(target.getUniqueId());
				}
				target.sendMessage("�8[�7Mensagem d� �f" + player.getName() + "�8] �7" + message);
				player.sendMessage("�8[�7Mensagem para �f" + target.getName() + "�8] �7" + message);
			}
		} else {
			return true;
		}
		return false;
	}

}
