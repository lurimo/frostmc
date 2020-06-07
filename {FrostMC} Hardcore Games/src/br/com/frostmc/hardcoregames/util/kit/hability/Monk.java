package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Monk implements Listener {

	public String kitName = "Kit " + Kits.MONK.getName();
	
	@EventHandler
	public void asd(PlayerInteractEntityEvent e) {
		final Player p = e.getPlayer();

		if ((e.getRightClicked() instanceof Player)) {
			Player jogadorClicado = (Player) e.getRightClicked();
			if ((p.getItemInHand().getType() == Material.BLAZE_ROD) && (FrostHG.getManager().getKitAPI().hasKit(p, Kits.MONK))) {
				e.setCancelled(true);
				if (CooldownAPI.hasCooldown(p, kitName)) {
					p.sendMessage(CooldownAPI.getMessage(p));
					return;
				}
				int random = new Random().nextInt(jogadorClicado.getInventory().getSize() - 10 + 1 + 10);
				ItemStack ItemSelecionado = jogadorClicado.getInventory().getItem(random);
				ItemStack ItemMudado = jogadorClicado.getItemInHand();

				jogadorClicado.setItemInHand(ItemSelecionado);
				jogadorClicado.getInventory().setItem(random, ItemMudado);

				CooldownAPI.addCooldown(p, new Cooldown(kitName, 30L));
			}
		}
	}
}
