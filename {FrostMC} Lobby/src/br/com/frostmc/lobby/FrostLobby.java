package br.com.frostmc.lobby;

import java.util.ArrayList;
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

import br.com.frostmc.common.util.TeleportServer;
import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.lobby.util.ClassGetter;
import br.com.frostmc.lobby.util.crates.CrateManager;
import br.com.frostmc.lobby.util.holograms.HologramManager;

public class FrostLobby extends JavaPlugin {

	private static FrostLobby instance;
	public static FrostLobby getInstance() {
		return instance;
	}
	
	public static ArrayList<UUID> scoreboard = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		new BukkitRunnable() {
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					TeleportServer.connectPlayer(player, ServersType.LOGIN);
				}
			}
		}.runTaskLater(this, 40L);
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
		
		new CrateManager().initialize();
		
	}

	@Override
	public void onDisable() {
		instance = null;
		HandlerList.unregisterAll();
	}

}
