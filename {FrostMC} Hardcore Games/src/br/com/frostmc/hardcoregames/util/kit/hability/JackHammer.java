package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class JackHammer implements Listener {

	public String kitName = "Kit " + Kits.JACKHAMMER.getName();
	public HashMap<UUID, Integer> blocos = new HashMap<>();
	
	@EventHandler
	private void Habilidade(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if ((e.getPlayer().getItemInHand().getType().equals(Material.STONE_AXE)) && (FrostHG.getManager().getKitAPI().hasKit(e.getPlayer(), Kits.JACKHAMMER))) {
			if (CooldownAPI.hasCooldown(player, kitName)) {
				player.sendMessage(CooldownAPI.getMessage(player)); 
				return;
			 }
			 blocos.put(e.getPlayer().getUniqueId(), blocos.containsKey(e.getPlayer().getUniqueId()) ? blocos.get(e.getPlayer().getUniqueId()) + 1 : 1);
			  if (blocos.get(e.getPlayer().getUniqueId()).equals(6)) {
				  CooldownAPI.addCooldown(player, new Cooldown(kitName, 5L));
				  blocos.remove(e.getPlayer().getUniqueId());
				  return;
			  }
			  if (e.getBlock().getRelative(BlockFace.UP).getType() != Material.AIR) {
				  quebrar(e.getBlock(), BlockFace.UP);
			  } else {
				  quebrar(e.getBlock(), BlockFace.DOWN);
			  }
		}
	}
	
	private void quebrar(final Block b, final BlockFace face) {
		new BukkitRunnable() {
		Block block = b;
		@SuppressWarnings("deprecation")
		public void run() {
			if (block.getType() != Material.BEDROCK && block.getType() != Material.ENDER_PORTAL_FRAME && block.getY() <= 128) {
				block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType().getId(), 30);
				block.setType(Material.AIR);
				block = block.getRelative(face);
			} else {
				cancel();
			}
		}
		}.runTaskTimer(FrostHG.getInstance(), 2L, 2L);
	}

}
