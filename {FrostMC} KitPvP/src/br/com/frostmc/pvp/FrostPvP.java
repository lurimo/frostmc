package br.com.frostmc.pvp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.pvp.listener.DanoListener;
import br.com.frostmc.pvp.util.ClassGetter;
import br.com.frostmc.pvp.util.TeleportServer;
import br.com.frostmc.pvp.util.feast.ConfigManager;
import br.com.frostmc.pvp.util.feast.Feast;
import br.com.frostmc.pvp.util.hability.kits.Gladiator;
import br.com.frostmc.pvp.util.hologram.top.TopDeaths;
import br.com.frostmc.pvp.util.hologram.top.TopKills;
import br.com.frostmc.pvp.util.hologram.top.TopLavaEasy;
import br.com.frostmc.pvp.util.hologram.top.TopLavaExtreme;
import br.com.frostmc.pvp.util.hologram.top.TopLavaHard;
import br.com.frostmc.pvp.util.hologram.top.TopLavaMedium;
import br.com.frostmc.pvp.util.hologram.top.TopStreak;
import br.com.frostmc.pvp.util.kit_rotation.KitRotation;

public class FrostPvP extends JavaPlugin {
	
	public static FrostPvP instance;
	public static FrostPvP getInstance() {
		return instance;
	}
	
	public static Plugin plugin;
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static ConfigManager configManager;
	public static ConfigManager getConfigManager() {
		return configManager;
	}
	
	public static Feast feast;
	public static Feast getFeast() {
		return feast;
	}
	
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
		
		configManager = new ConfigManager();
		feast = new Feast();
		
		new BukkitRunnable() {
			public void run() {
				new TopKills().remove();
				new TopDeaths().remove();
				new TopStreak().remove();
				new TopLavaEasy().remove();
				new TopLavaMedium().remove();
				new TopLavaHard().remove();
				new TopLavaExtreme().remove();
				new TopKills().set();
				new TopDeaths().set();
				new TopStreak().set();
				new TopLavaEasy().set();
				new TopLavaMedium().set();
				new TopLavaHard().set();
				new TopLavaExtreme().set();
				new KitRotation().apply();
			}
		}.runTaskLater(this, 100L);
		
		new BukkitRunnable() {
			public void run() {
				if (Bukkit.getOnlinePlayers().length <= 0)
					return;
				new TopKills().remove();
				new TopDeaths().remove();
				new TopStreak().remove();
				new TopLavaEasy().remove();
				new TopLavaMedium().remove();
				new TopLavaHard().remove();
				new TopLavaExtreme().remove();
				new TopKills().set();
				new TopDeaths().set();
				new TopStreak().set();
				new TopLavaEasy().set();
				new TopLavaMedium().set();
				new TopLavaHard().set();
				new TopLavaExtreme().set();
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (!BukkitMain.getPermissions().isModPlus(player)) {
						player.sendMessage("�8[�b�lADMINISTRA��O�8] �fOs hologramas do servidor foram atualizados.");
						return;
					}
				}
			}
		}.runTaskTimer(this, 0L, 20 * 60);
		
		DanoListener.setup();
		saveDefaultConfig();
		ClassGetter.loadCommand();
		ClassGetter.loadListener();
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		for (Player todos : Bukkit.getOnlinePlayers()) {
			if (Gladiator.gladArena.containsKey(todos.getUniqueId())) {
				Gladiator.removerBlocos(todos.getLocation());
			}
		}
		HandlerList.unregisterAll();
		instance = null; plugin = null;
		new TopKills().remove();
	}
	
	public List<Player> getOnlinePlayers() {
		List<Player> list = new ArrayList<>();
		for (World world : FrostPvP.getInstance().getServer().getWorlds()) {
			for (Player player : world.getPlayers()) {
				list.add(player);
			}
		}
		return list;
	}

}
