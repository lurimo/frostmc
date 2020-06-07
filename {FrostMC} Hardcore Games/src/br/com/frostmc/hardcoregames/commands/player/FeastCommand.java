package br.com.frostmc.hardcoregames.commands.player;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.timer.feast.Feast;
import br.com.frostmc.hardcoregames.util.State;

public class FeastCommand extends BaseCommand {

	public FeastCommand() {
		super("feast");
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (FrostHG.state == State.JOGO) {
				if (!player.getInventory().contains(Material.COMPASS)) {
					player.sendMessage("§b§lFEAST §fVocê não possuí uma bússola no inventário.");
		    		return true;
		    	}
		    	if (Feast.feastLoc == null) {
		    		player.sendMessage("§b§lFEAST §fO feast ainda não spawnou!");
		    		return true;
		    	}
		    	player.setCompassTarget(Feast.feastLoc);
		    	player.sendMessage("§b§lFEAST §fBússola apontada para o feast.");
			} else {
				player.sendMessage("§b§lFEAST §fO jogo ainda não iniciou.");
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
