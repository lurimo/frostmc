package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Specialist implements Listener {
	
	@EventHandler
	private void giveLevel(PlayerDeathEvent e) {
		if ((e.getEntity() instanceof Player) && (e.getEntity().getKiller() instanceof Player)) {
			Player player = e.getEntity().getKiller();
			if (FrostHG.getManager().getKitAPI().hasKit(player, Kits.SPECIALIST))
				player.setLevel(player.getLevel() + 1);
		}
	}

	@EventHandler
	private void openEnchant(PlayerInteractEvent e) {
		if (FrostHG.state != State.JOGO)
			return;
		
		Player player = e.getPlayer();
		if ((player.getItemInHand().getType().equals(Material.BOOK)) && FrostHG.getManager().getKitAPI().hasKit(player, Kits.SPECIALIST))
			player.openEnchanting(player.getLocation(), true);
	}

}
