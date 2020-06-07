package br.com.frostmc.onevsone;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.onevsone.util.ClassGetter;
import br.com.frostmc.onevsone.util.TeleportServer;

public class Frost1v1 extends JavaPlugin {
	
	public static Frost1v1 instance;
	public static Frost1v1 getInstance() {
		return instance;
	}
	
	public static Plugin plugin;
	public static Plugin getPlugin() {
		return plugin;
	}
	
	//boolean's
	public static ArrayList<UUID> scoreboard = new ArrayList<>();

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		instance = this; plugin = this;
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		for (Player todos : Bukkit.getOnlinePlayers()) {
			TeleportServer.connectPlayer(todos, ServersType.LOBBY);
		}
		for (World dia : Bukkit.getServer().getWorlds()) {
			dia.setGameRuleValue("doDaylightCycle", "false");
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			for(Entity entity : player.getWorld().getEntities()) {
				if(entity instanceof Item) {
					entity.remove();
				}
			}
		}
		
		ClassGetter.loadCommandBukkit();
		ClassGetter.loadListenerBukkit();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		HandlerList.unregisterAll();
		instance = null; plugin = null;
	}

}
