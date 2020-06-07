package br.com.frostmc.hardcoregames.commands.staffer;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.timer.Timer;

public class TimerCommand extends BaseCommand {

	public TimerCommand() {
		super("timer", "alternate the time for game", Arrays.asList("tempo"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			
		} else {
			return true;
		}
		Player player = getPlayer(commandSender);
		if (!BukkitMain.getPermissions().isYoutuberPlus(player)) {
			player.sendMessage(Strings.getPermission());
			return true;
		}
		if (args.length == 0 || args.length == 1) {
			player.sendMessage("§3§lTEMPO §fUtilize o comando: /timer (tempo) (s/m)");
			return true;
		}
		Integer tempo = null;
		try {
			tempo = Integer.valueOf(args[0]);
		} catch (Exception exception) {
			sendNumber(player, "Digite somente números.");
			return true;
		}
		if (tempo < 0) {
			player.sendMessage("§3§lTEMPO §fVocê não pode setar o tempo menor que 0.");
			return true;
		}
		if (args[1].equalsIgnoreCase("s") || args[1].equalsIgnoreCase("segundos")) {
			Timer.tempo = tempo;
			return true;
		}
		if (args[1].equalsIgnoreCase("m") || args[1].equalsIgnoreCase("minutos")) {
			Timer.tempo = tempo*60;
			return true;
		}
		return false;
	}

}
