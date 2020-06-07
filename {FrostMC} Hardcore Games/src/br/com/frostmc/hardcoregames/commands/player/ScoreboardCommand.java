package br.com.frostmc.hardcoregames.commands.player;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;

public class ScoreboardCommand extends BaseCommand {
	
	public ScoreboardCommand() {
		super("scoreboard", "active/desactive to scoreboard", Arrays.asList("score", "scores"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
				FrostHG.getManager().getScoreboard().remove(player.getUniqueId());
				Scoreboarding.removeScoreboard(player);
				player.sendMessage("§c§lSCOREBOARD §fA sua scoreboard foi desativada!");
			} else {
				FrostHG.getManager().getScoreboard().add(player.getUniqueId());
				Scoreboarding.setScoreboard(player);
				player.sendMessage("§a§lSCOREBOARD §fA sua scoreboard foi ativada!");
			}
		} else {
			return true;
		}
		return false;
	}

}
