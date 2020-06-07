package br.com.frostmc.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.frostmc.common.permission.PermissionManager;
import br.com.frostmc.common.permission.Permissions;
import br.com.frostmc.common.util.ClassGetter;
import br.com.frostmc.common.util.MessagesType;
import br.com.frostmc.common.util.ServerOptions;
import br.com.frostmc.common.util.event.UpdateScheduler;
import br.com.frostmc.common.util.gamer.GamerManager;
import br.com.frostmc.common.util.tag.Tags;
import br.com.frostmc.common.util.worldedit.BO2;
import br.com.frostmc.common.util.worldedit.WorldEdit;
import br.com.frostmc.core.CoreMain;
import br.com.frostmc.core.data.mysql.bukkit.server.Event;
import br.com.frostmc.core.util.enums.ServersType;

public class BukkitMain extends JavaPlugin {

	public String[] commandBlocks = new String[] { "minecraft:", "bukkit:", "pl", "plugins", "icanhasbukkit", "ver",
			"version", "?", "help", "tellraw", "me", "?", "plugins", "pl", "whisper", "about", ":" };

	public static BukkitMain instance;

	public static BukkitMain getInstance() {
		return instance;
	}

	public static Plugin plugin;

	public static Plugin getPlugin() {
		return plugin;
	}

	public static CoreMain core;

	public static CoreMain getCore() {
		return core;
	}

	public static GamerManager gamerManager;

	public static GamerManager getGamerManager() {
		return gamerManager;
	}

	public static PermissionManager permissionManager;

	public static PermissionManager getPermissionManager() {
		return permissionManager;
	}

	public static Permissions permissions;

	public static Permissions getPermissions() {
		return permissions;
	}

	public static ServersType serversType = ServersType.DEFAULT_SERVER;

	public static ServersType getServersType() {
		return serversType;
	}

	public static WorldEdit worldEdit;

	public static WorldEdit getWorldEdit() {
		return worldEdit;
	}

	public static BO2 bo2;

	public static BO2 getBO2() {
		return bo2;
	}
	
	public ServersType getServerOnly() {
		if (Bukkit.getServer().getPort() == ServersType.PVP.getPort()) {
			return ServersType.PVP;
		} else if (Bukkit.getServer().getPort() == ServersType.ONEVSONE.getPort()) {
			return ServersType.ONEVSONE;
		} else if (Bukkit.getServer().getPort() == ServersType.GLADIATOR.getPort()) {
			return ServersType.GLADIATOR;
		}
		return ServersType.DEFAULT_SERVER;
	}

	@Override
	public void onEnable() {
		plugin = this;
		instance = this;

		core = new CoreMain();
		core.mysql.start();
		core.mysql.setup();
		core.getUtil().changeServerOption(ServerOptions.CHAT, true);
		core.getUtil().changeServerOption(ServerOptions.BUILD, true);
		core.getUtil().changeServerOption(ServerOptions.DAMAGE, true);
		core.getUtil().changeServerOption(ServerOptions.PVP, true);
		if (new Event().exists()) {
			core.getUtil().changeServerOption(ServerOptions.EVENTO, true);
		} else {
			core.getUtil().changeServerOption(ServerOptions.EVENTO, false);
		}
		core.box.setBungee(false);

		Tags.changeTag = false;
		
		ClassGetter.loadCommandBukkit();
		ClassGetter.loadListenerBukkit();

		new UpdateScheduler().run();
		getServer().getScheduler().runTaskTimer(this, new UpdateScheduler(), 1, 1);

		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getScheduler().runTaskLater(this, () -> unregisterCommands(commandBlocks), 2L);

		gamerManager = new GamerManager();
		(permissionManager = new PermissionManager(this)).onEnable();
		permissions = new Permissions();
		
		worldEdit = new WorldEdit(this);

		bo2 = new BO2();
		bo2.startUpdate();
		
		serversType = ServersType.getServerType(getServerOnly());

		core.getBox().box("[FrostMC - Common]", "O plugin foi habilitado com sucesso.");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		for (Player players : Bukkit.getOnlinePlayers()) {
			MessagesType.clearTitle(players);
		}
		HandlerList.unregisterAll();
		plugin = null;
		instance = null;

		core.getMySQL().close();

		if (permissionManager != null) {
			permissionManager.onDisable();
		}
		
		core.getBox().box("[FrostMC - Common]", "O plugin foi desabilitado com sucesso.");
	}

	@SuppressWarnings("unchecked")
	public void unregisterCommands(String... commands) {
		try {
			Field f1 = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			f1.setAccessible(true);
			CommandMap commandMap = (CommandMap) f1.get(Bukkit.getServer());
			Field f2 = commandMap.getClass().getDeclaredField("knownCommands");
			f2.setAccessible(true);
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) f2.get(commandMap);
			for (String command : commands) {
				if (knownCommands.containsKey(command)) {
					knownCommands.remove(command);
					List<String> aliases = new ArrayList<>();
					for (String key : knownCommands.keySet()) {
						if (!key.contains(":"))
							continue;
						String substr = key.substring(key.indexOf(":") + 1);
						if (substr.equalsIgnoreCase(command)) {
							aliases.add(key);
						}
					}
					for (String alias : aliases) {
						knownCommands.remove(alias);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
