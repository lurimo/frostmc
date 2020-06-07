package br.com.frostmc.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.AuthType;
import br.com.frostmc.login.util.ClassGetter;
import br.com.frostmc.login.util.holograms.HologramManager;

public class FrostLogin extends JavaPlugin {
	
	public static FrostLogin instance;
	public static FrostLogin getInstance() {
		return instance;
	}
	
	public static ArrayList<UUID> scoreboard = new ArrayList<>();
	public static HashMap<UUID, AuthType> checkToLogin = new HashMap<UUID, AuthType>();
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer("§b§lFROST§f§lMC" + "\n" + "\n" + "§fO servidor foi reiniciado" + "\n" + "\n" + "§3" + Strings.getWebsite());
		}
		for (World dia : Bukkit.getServer().getWorlds()) {
			dia.setTime(0);
			dia.setGameRuleValue("doDaylightCycle", "false");
		}
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			for(Entity entity : player.getWorld().getEntities()) {
				if(entity instanceof Item) {
					entity.remove();
				}
			}
		}
		World world = Bukkit.getWorld("world");
		world.setDifficulty(Difficulty.PEACEFUL);
		for (LivingEntity mob : world.getEntitiesByClass(LivingEntity.class)) {
            for (Entity entity : mob.getLocation().getChunk().getEntities()) {
                 if (((entity instanceof LivingEntity)) && (!(entity instanceof Player))) {
                	 entity.remove();
                 }
            }
		}
		
		ClassGetter.loadCommandBukkit();
		ClassGetter.loadListenerBukkit();
		
		new BukkitRunnable() {
			public void run() {
				new HologramManager().initialize();
			}
		}.runTaskLater(this, 100L);
		
	}
	
	@Override
	public void onDisable() {
		instance = null;
		HandlerList.unregisterAll();
		scoreboard.clear();
		checkToLogin.clear();
	}
	
}