package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Surprise {
	
	public Kits getSurprise() {
		List<Kits> kits = new ArrayList<Kits>();
		for (Kits kit : Kits.values()) {
			kits.add(kit);
		}
		if (kits.size() > 0) {
			return (Kits) kits.get(new Random().nextInt(kits.size()));
		}
		return null;
	}
	
	public void setSurprise(Player player) {
		Kits kit = getSurprise();
		player.sendMessage("§b§lKIT §fO seu kit surpresa é §a" + kit.getName());
		FrostHG.getManager().getKitAPI().setKit(player, kit);
		FrostHG.getManager().getKitAPI().giveKit(player, Kits.valueOf(FrostHG.getManager().getKitAPI().getKit(player).toUpperCase()));
		player.updateInventory();
		Scoreboarding.updateKit(player, player.getScoreboard());
	}
	
}
