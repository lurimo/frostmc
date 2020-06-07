package br.com.frostmc.common.util.fake;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;

import com.google.common.collect.Iterables;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.WorldServer;
import net.minecraft.util.com.google.common.cache.CacheBuilder;
import net.minecraft.util.com.google.common.cache.CacheLoader;
import net.minecraft.util.com.google.common.cache.LoadingCache;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;

public class CustomPlayerAPI {
	
	public static final LoadingCache<GameProfile, Property> Textures = CacheBuilder.newBuilder().expireAfterWrite(30L, TimeUnit.MINUTES).build(new CacheLoader<GameProfile, Property>() {
		public Property load(GameProfile key) {
			return CustomPlayerAPI.loadTextures(key);
		}
	});
	
	private static MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();

	private static final Property loadTextures(GameProfile profile) {
		MinecraftServer.getServer().av().fillProfileProperties(profile, true);
		return (Property)Iterables.getFirst(profile.getProperties().get("textures"), null);
	}

	public static MinecraftServer getNmsServer() {
		return nmsServer;
	}

	public static WorldServer getNmsWorld(World world) {
		return ((CraftWorld) world).getHandle();
	}

	public static void setHeadYaw(Entity en, float yaw) {
		if (!(en instanceof EntityLiving)) {
			return;
		}
		EntityLiving handle = (EntityLiving) en;
		while (yaw < -180.0F) {
			yaw += 360.0F;
		}
		while (yaw >= 180.0F) {
			yaw -= 360.0F;
		}
		handle.aO = yaw;
		if (!(handle instanceof EntityHuman)) {
			handle.aM = yaw;
		}
		handle.aP = yaw;
	}
}
