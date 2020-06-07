package br.com.frostmc.common.command.player;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.core.util.Strings;

public class AplicarCommand extends BaseCommand {

	public AplicarCommand() {
		super("aplicar", "aplique-se para equipe", Arrays.asList("application", "app", "form", "integraraequipe"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = (Player) commandSender;
			if (args.length == 0) {
				player.sendMessage("");
				player.sendMessage("§8[§b§lFROST§8] §fTem interesse em fazer parte de nossa §eEQUIPE§f? §fAcesse: §b" + Strings.getWebsite() + "comunidade/aplicacoes");
				player.sendMessage("");
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
