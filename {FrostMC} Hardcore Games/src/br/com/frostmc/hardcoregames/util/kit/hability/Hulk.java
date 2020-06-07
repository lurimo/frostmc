package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Hulk implements Listener {

	public String kitName = "Kit " + Kits.HULK.getName();
	
	@EventHandler
	public void onClicando(PlayerInteractEntityEvent event) {
		if ((event.getRightClicked() instanceof Player)) {
			Player p = event.getPlayer();
			Player clicado = (Player) event.getRightClicked();
			if (FrostHG.state != State.JOGO) {
				return;
			}

			if ((!event.getRightClicked().isInsideVehicle()) && (event.getPlayer().getVehicle() == null) && (FrostHG.getManager().getKitAPI().hasKit(p, Kits.HULK)) && (p.getItemInHand() != null) && (p.getItemInHand().getType() == Material.AIR)) {
				if (CooldownAPI.hasCooldown(p, kitName)) {
					p.sendMessage(CooldownAPI.getMessage(p));
					return;
				}
				CooldownAPI.addCooldown(p, new Cooldown(kitName, 15L));
				p.setPassenger(event.getRightClicked());
				clicado.sendMessage("§2§lHULK §fUm hulk te §c§lPEGOU§f!");
				p.sendMessage("§2§lHULK §fVocê §a§lPEGOU§f algum jogador com o kit hulk.");
			}
		}
	}

	@EventHandler
	public void onPlaierInteraquetiEvente(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Action a = event.getAction();
		if (FrostHG.state!=State.JOGO) {
			return;
		}
		if ((a == Action.LEFT_CLICK_AIR) || (a == Action.LEFT_CLICK_BLOCK)) {
			Entity passenger = p.getPassenger();
			if ((FrostHG.getManager().getKitAPI().hasKit(p, Kits.HULK)) && (passenger != null) && ((passenger instanceof Player))) {
				Player pass = (Player) passenger;
				Vector vector = p.getEyeLocation().getDirection();
				vector.multiply(1.5F);
				pass.leaveVehicle();
				pass.setVelocity(vector);
				pass.sendMessage("§c§lCUIDADO §fVocê foi jogado de alguma altura.");
			}
		}
	}
}