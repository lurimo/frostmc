package br.com.frostmc.onevsone.util.protection;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Protection implements Listener {

	public static ArrayList<Player> invencible = new ArrayList<>();

	public static boolean hasProtection(Player player) {
		return invencible.contains(player);
	}

	public static void setProtection(Player player) {
		if (!hasProtection(player)) {
			invencible.add(player);
			player.sendMessage("�8�lPROTE��O �fVoc� ganhou a �a�lPROTE��O �fdo �a�lSPAWN�f!");
		}
	}

	public static void removeProtection(Player player) {
		if (hasProtection(player)) {
			invencible.remove(player);
			player.sendMessage("�8�lPROTE��O �fVoc� perdeu a �c�lPROTE��O �fdo �a�lSPAWN�f!");
		}
	}

	@EventHandler
	public void asd(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (hasProtection(player)) {
				event.setCancelled(true);
			}
		}
	}

}
