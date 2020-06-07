package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Ninja implements Listener {

	public String kitName = "Kit " + Kits.NINJA.getName();
	public HashMap<UUID, UUID> tp = new HashMap<>();
	
	 @EventHandler
	 private void setTarget(EntityDamageByEntityEvent e) {
		if (e.isCancelled())
			return;
		if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player)) {
	         Player d = (Player)e.getDamager();
		     if (FrostHG.state != State.JOGO)
		    	 return;
	 	     if (FrostHG.getManager().getKitAPI().hasKit(d, Kits.NINJA)) {
		         Player p = (Player)e.getEntity();
	             tp.put(d.getUniqueId(), p.getUniqueId());
	 	     }
		}
	 }

	 @EventHandler
	 private void teleport(PlayerToggleSneakEvent e) {
	     Player p = e.getPlayer();
	     if (FrostHG.state != State.JOGO)
	    	 return;
	     if ((p.isSneaking()) && (FrostHG.getManager().getKitAPI().hasKit(p, Kits.NINJA))) {
	     	  if (tp.containsKey(p.getUniqueId())) {
	     		 if (!CooldownAPI.hasCooldown(p, kitName)) {
	                  Player req = Bukkit.getPlayer(tp.get(p.getUniqueId()));
	                  if (req != null) {
	                 	  if (!FrostHG.getManager().getJogadores().contains(req.getUniqueId())) {
	                 		  tp.remove(p.getUniqueId());
	                 	      return;
	                 	  }
						  if (p.getLocation().distance(req.getLocation()) <= 30) {
	                 	      p.teleport(req);
	                 	      CooldownAPI.addCooldown(p, new Cooldown(kitName, 6L));
						  } else {
							  p.sendMessage("§a§lNINJA §fO jogador está muito longe.");
						  }  
	                 }
	     		  } else {
	     			  p.sendMessage(CooldownAPI.getMessage(p));
	     		  }
	     	  }
	     }
	 }

}
