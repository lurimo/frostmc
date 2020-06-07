package br.com.frostmc.onevsone.commands.player;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.onevsone.Frost1v1;
import br.com.frostmc.onevsone.commands.BaseCommand;
import br.com.frostmc.onevsone.scoreboard.Scoreboarding;

public class ScoreboardCommand extends BaseCommand {
	
	public ScoreboardCommand() {
		super("scoreboard", "ative/desative a scoreboard", Arrays.asList("score", "scores"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (Frost1v1.scoreboard.contains(player.getUniqueId())) {
				Frost1v1.scoreboard.remove(player.getUniqueId());
				Scoreboarding.removeScoreboard(player);
				player.sendMessage("�8[�cSCOREBOARD�8] �fSua scoreboard foi desativada.");
			} else {
				Frost1v1.scoreboard.add(player.getUniqueId());
				Scoreboarding.setScoreboard(player);
				player.sendMessage("�8[�aSCOREBOARD�8] �fSua scoreboard foi ativada.");
			}
		} else {
			return true;
		}
		return false;
	}
}