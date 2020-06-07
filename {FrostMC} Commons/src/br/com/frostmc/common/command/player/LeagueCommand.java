package br.com.frostmc.common.command.player;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.core.util.enums.LeagueType;

public class LeagueCommand extends BaseCommand{

	public LeagueCommand() {
		super("league", "veja sua liga", Arrays.asList("liga", "ligas", "rank", "ranks"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
			if (args.length == 0) {
				player.sendMessage("");
				player.sendMessage("      §8[§6§lLEAGUE§8]");
				player.sendMessage("");
				player.sendMessage("§aO servidor possui um sistema de liga único, que garante aos jogadores uma competição e mais destaque.");
				player.sendMessage("§aA liga é ordenada de acordo com o seu XP! Porém, como faço ganhar xp?");
				player.sendMessage("§aMatando jogadores, completando desafios, vencendo duelos na 1v1 e Gladiator ou ganhando partidas de HG!");
				player.sendMessage("§aAo exceder uma dessas ocasiôes você irá receber uma quantidade de XP calculada por nossa rede para upar sua LIGA!");
				player.sendMessage("");
				ArrayList<String> list = new ArrayList<>();
				for (LeagueType ligasType : LeagueType.values()) {
					list.add(ligasType.name());
				}
				for (String ligasType : list) {
					player.sendMessage(String.valueOf(gamer.getLeagueType().ordinal() >= LeagueType.valueOf(ligasType).ordinal() ? "§a✔" : "§c✗") + " " + LeagueType.valueOf(ligasType).fullName());
				}
				player.sendMessage("");
				gamer.checkRank();
				player.sendMessage("§aLIGA Atual: " + gamer.getLeagueType().fullName());
				player.sendMessage("§aXP Atual: §f" + gamer.getXp() + " XP");
				player.sendMessage("§aXP necessário para próxima LIGA: §f" + (gamer.getLeagueType().equals(LeagueType.FROST) ? "0" : NumberFormat.getInstance().format(gamer.getNext().getXp() - gamer.getXp())) + " XP");
				player.sendMessage("§aProgresso: §f" + (gamer.getLeagueType().equals(LeagueType.FROST) ? "0" : gamer.percentage()) + "%");
				player.sendMessage("");
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
