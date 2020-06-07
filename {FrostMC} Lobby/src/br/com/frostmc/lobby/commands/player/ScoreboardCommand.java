package br.com.frostmc.lobby.commands.player;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.lobby.FrostLobby;
import br.com.frostmc.lobby.commands.BaseCommand;
import br.com.frostmc.lobby.scoreboard.Scoreboarding;

public class ScoreboardCommand extends BaseCommand {
	
	public ScoreboardCommand() {
		super("scoreboard", "ative/desative a scoreboard", Arrays.asList("score", "scores"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (FrostLobby.scoreboard.contains(player.getUniqueId())) {
				FrostLobby.scoreboard.remove(player.getUniqueId());
				Scoreboarding.removeScoreboard(player);
				player.sendMessage("§8[§c§lSCOREBOARD§8] §fSua scoreboard foi desativada.");
			} else {
				FrostLobby.scoreboard.add(player.getUniqueId());
				Scoreboarding.setScoreboard(player);
				player.sendMessage("§8[§a§lSCOREBOARD§8] §fSua scoreboard foi ativada.");
			}
		} else {
			return true;
		}
		return false;
	}
}
