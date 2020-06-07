package br.com.frostmc.login.util.npc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_7_R4.*;

public class NpcManager {

	public static void spawnZombie(Location loc, String name) {
		Zombie mob = loc.getWorld().spawn(loc, Zombie.class);
		mob.setCustomName(name);
		mob.setCustomNameVisible(true);
		mob.setPassenger(mob);
		mob.getEquipment().setItemInHand(new ItemStack(Material.COMPASS));
		EntityZombie entityMob = ((CraftZombie) mob).getHandle();
		entityMob.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}
	
}
