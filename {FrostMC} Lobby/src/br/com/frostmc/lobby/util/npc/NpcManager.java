package br.com.frostmc.lobby.util.npc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_7_R4.*;

public class NpcManager {

	public static void spawnPigZombie(Location loc, String name) {
		Villager mob = loc.getWorld().spawn(loc, Villager.class);
		mob.setCustomNameVisible(true);
		mob.setCustomName(name);
		mob.setPassenger(mob);
		mob.getEquipment().setItemInHand(new ItemStack(Material.BLAZE_ROD));
		EntityVillager entityMob = ((CraftVillager) mob).getHandle();
		entityMob.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}

	public static void spawnSkeleton(Location loc, String name) {
		Skeleton mob = loc.getWorld().spawn(loc, Skeleton.class);
		mob.setCustomNameVisible(true);
		mob.setCustomName(name);
		mob.setPassenger(mob);
		mob.getEquipment().setItemInHand(new ItemStack(Material.MUSHROOM_SOUP));
		EntitySkeleton entityMob = ((CraftSkeleton) mob).getHandle();
		entityMob.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}

	@SuppressWarnings("deprecation")
	public static void spawnIron_golem(Location loc, String name) {
		IronGolem mob = loc.getWorld().spawn(loc, IronGolem.class);
		mob.setCustomNameVisible(true);
		mob.setCustomName(name);
		mob.setPassenger(mob);
		mob.getEquipment().setItemInHand(new ItemStack(Material.getMaterial(101)));
		EntityIronGolem entityMob = ((CraftIronGolem) mob).getHandle();
		entityMob.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}

	public static void spawnZombie(Location loc, String name) {
		Zombie mob = loc.getWorld().spawn(loc, Zombie.class);
		mob.setCustomName(name);
		mob.setCustomNameVisible(true);
		mob.setPassenger(mob);
		mob.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
		EntityZombie entityMob = ((CraftZombie) mob).getHandle();
		entityMob.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}
	
}
