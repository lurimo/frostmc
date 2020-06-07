package br.com.frostmc.gladiator.commands.staffer;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.gladiator.commands.BaseCommand;
import br.com.frostmc.gladiator.scoreboard.Scoreboarding;
import br.com.frostmc.gladiator.util.admin.AdminManager;

public class AdminCommand extends BaseCommand {

	public AdminCommand() {
		super("admin", "ative/desative o modo admin", Arrays.asList("vanish"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (!isPlayer(commandSender)) {
			return false;
		}
		Player player = (Player) commandSender;
		if (!BukkitMain.getPermissions().isTrial(player)) {
			player.sendMessage(Strings.getPermission());
			return true;
		}

		boolean enter = !AdminManager.isAdmin(player);

		AdminManager.setAdmin(player, enter);
		sendWarning("O staffer " + player.getName() + " " + (enter ? "entrou no" : "saiu do") + " modo ADMIN");
		
		Scoreboarding.setScoreboard(player);
		return true;
	}

}

