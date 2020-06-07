package br.com.frostmc.gladiator.commands.player;


import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.gladiator.FrostGladiator;
import br.com.frostmc.gladiator.commands.BaseCommand;
import br.com.frostmc.gladiator.scoreboard.Scoreboarding;

public class ScoreboardCommand extends BaseCommand {
	
	public ScoreboardCommand() {
		super("scoreboard", "ative/desative a scoreboard", Arrays.asList("score", "scores"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (FrostGladiator.scoreboard.contains(player.getUniqueId())) {
				FrostGladiator.scoreboard.remove(player.getUniqueId());
				Scoreboarding.removeScoreboard(player);
				player.sendMessage("§c§lSCOREBOARD §fA sua scoreboard foi desativada!");
			} else {
				FrostGladiator.scoreboard.add(player.getUniqueId());
				Scoreboarding.setScoreboard(player);
				player.sendMessage("§a§lSCOREBOARD §fA sua scoreboard foi ativada!");
			}
		} else {
			return true;
		}
		return false;
	}
}
