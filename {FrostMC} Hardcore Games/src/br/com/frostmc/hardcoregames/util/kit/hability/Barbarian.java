package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PacketPlayOutSetSlot;

public class Barbarian implements Listener {

	private Material[] updates = {Material.STONE_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD};

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (FrostHG.state==State.INICIANDO)
			return;
		Player player = e.getPlayer();
		if (FrostHG.getManager().getKitAPI().hasKit(player, Kits.BARBARIAN)) {
			ItemStack item = player.getInventory().getItemInHand();
			if (FrostHG.getManager().getKitAPI().isItem(player, item, "§bKit " + Kits.BARBARIAN.getName())) {
				player.getItemInHand().setDurability((short) 0);
				EntityPlayer eP = ((CraftPlayer) player).getHandle();
				PacketPlayOutSetSlot packet = new PacketPlayOutSetSlot(eP.activeContainer.windowId, 44 - Math.abs(player.getInventory().getHeldItemSlot() - 8), CraftItemStack.asNMSCopy(player.getItemInHand()));
				eP.playerConnection.sendPacket(packet);
			}
		}
	}

	@EventHandler
	public void onBlockBreak(EntityDamageByEntityEvent e) {
		if (FrostHG.state!=State.JOGO)
			return;
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			if (FrostHG.getManager().getKitAPI().hasKit(player, Kits.BARBARIAN)) {
				ItemStack item = player.getInventory().getItemInHand();
				if (FrostHG.getManager().getKitAPI().isItem(player, item, "§bKit " + Kits.BARBARIAN.getName())) {
					player.getItemInHand().setDurability((short) 0);
					EntityPlayer eP = ((CraftPlayer) player).getHandle();
					PacketPlayOutSetSlot packet = new PacketPlayOutSetSlot(eP.activeContainer.windowId, 44 - Math.abs(player.getInventory().getHeldItemSlot() - 8), CraftItemStack.asNMSCopy(player.getItemInHand()));
					eP.playerConnection.sendPacket(packet);
				}
			}
		}
	}

	@EventHandler
	public void onDeathEvent(PlayerDeathEvent e) {
		if (FrostHG.state!=State.JOGO)
			return;
		if (e.getEntity().getKiller() instanceof Player) {
			if (e.getEntity() instanceof Player) {
				Player player = e.getEntity().getKiller();
				ItemStack item = player.getInventory().getItemInHand();
				if (FrostHG.getManager().getKitAPI().hasKit(player, Kits.BARBARIAN)) {
					if (FrostHG.getManager().getKitAPI().isItem(player, item, "§bKit " + Kits.BARBARIAN.getName())) {
						if (!FrostHG.getManager().kills.containsKey(player.getUniqueId())) {
							FrostHG.getManager().kills.put(player.getUniqueId(), 0);
						}
						if (FrostHG.getManager().kills.get(player.getUniqueId()) % 3 == 0) {
							int l = FrostHG.getManager().kills.get(player.getUniqueId()) / 3 - 1;
							if (l < updates.length) {
								item.setType(updates[l]);
							}
						}
					}
				}
			}
		}
	}

}
 