package br.com.frostmc.hardcoregames;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.hardcoregames.listeners.DanoListener;
import br.com.frostmc.hardcoregames.timer.feast.FeastConfig;
import br.com.frostmc.hardcoregames.util.ClassGetter;
import br.com.frostmc.hardcoregames.util.Manager;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.TeleportServer;

public class FrostHG extends JavaPlugin {
	
	private static FrostHG instance;
	public static FrostHG getInstance() {
		return instance;
	}
	
	public static State state;
	
	private static Manager manager;
	public static Manager getManager() {
		return manager;
	}
	
	private void delete(File delete) {
		if (delete.isDirectory()) {
			String[] lista = delete.list();
			for (int i = 0; i < lista.length; i++) {
				delete(new File(delete, lista[i]));
			}
		}
		delete.delete();
	}
	
	@Override
	public void onLoad() {
		delete(new File("world"));
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		manager = new Manager();
		manager.start();
		manager.setupShematics();
		manager.loadRecepients();
		manager.loadChunks();
		manager.disablekit = false;
		
		new ClassGetter().loadCommand();
		new ClassGetter().loadListener();
		
		DanoListener.setup();
		Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
		FeastConfig.loadConfig();
		
		for(Entity entity : Bukkit.getWorld("world").getEntities()) {
			if(entity instanceof Item) {
				entity.remove();
			}
		}
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		BukkitMain.getCore().getBox().box("[FrostMC - HG]", "O plugin foi habilitado com sucesso.");
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		instance = null;
		HandlerList.unregisterAll();
		
		for (Player todos : Bukkit.getOnlinePlayers()) {
			TeleportServer.connectPlayer(todos, ServersType.LOBBY);
		}
		
		BukkitMain.getCore().getBox().box("[FrostMC - HG]", "O plugin foi desabilitado com sucesso.");
	}

}
