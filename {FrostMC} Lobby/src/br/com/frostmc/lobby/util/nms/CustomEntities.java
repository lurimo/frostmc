package br.com.frostmc.lobby.util.nms;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_7_R4.Entity;

@SuppressWarnings("deprecation")
public enum CustomEntities {

	CUSTOM_ITEM("CustomItem", EntityType.DROPPED_ITEM.getTypeId(), CustomItemEntity.class);

	public static void spawnEntity(Entity entity, Location loc) {
		entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		entity.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld) loc.getWorld()).getHandle().addEntity(entity, SpawnReason.SPAWNER);
	}

	CustomEntities(String name, int id, Class<? extends Entity> custom) {
		addToMaps(custom, name, id);
	}

	@SuppressWarnings("unchecked")
	private static void addToMaps(Class<? extends Entity> clazz, String name, int id) {
		((Map<String, Class<? extends Entity>>) getValue("c", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(name, clazz);
		((Map<Class<? extends Entity>, String>) getValue("d", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(clazz, name);
		((Map<Class<? extends Entity>, Integer>) getValue("f", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
	}
	
	public static Object getValue(String field, Class<?> clazz, Object instance) {
		try {
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			return f.get(instance);
		} catch (Exception exception) {

		}
		return null;
	}

}
