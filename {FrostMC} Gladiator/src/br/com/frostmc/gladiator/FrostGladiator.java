package br.com.frostmc.gladiator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.gladiator.util.ClassGetter;
import br.com.frostmc.gladiator.util.GladiatorChunk;
import br.com.frostmc.gladiator.util.TeleportServer;
import br.com.frostmc.gladiator.util.jnbt.Schematic;

public class FrostGladiator extends JavaPlugin {
	
	public static ArrayList<UUID> scoreboard = new ArrayList<>();
	
	public static FrostGladiator instance;
	public static FrostGladiator getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		Schematic.setupShematics();
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		for (World dia : Bukkit.getServer().getWorlds()) {
			dia.setGameRuleValue("doDaylightCycle", "false");
		}
		
		for (Player player : getOnlinePlayers()) {
			for(Entity entity : player.getWorld().getEntities()) {
				if(entity instanceof Item) {
					entity.remove();
				}
			}
		}
		
		makeWorld();
		loadRecepients();
		
		ClassGetter.loadCommandBukkit();
		ClassGetter.loadListenerBukkit();
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		for (Player todos : Bukkit.getOnlinePlayers()) {
			TeleportServer.connectPlayer(todos, ServersType.LOBBY);
		}
		instance = null;
	}
	
	@SuppressWarnings("deprecation")
	public void loadRecepients() {
		ItemStack cacau = new ItemStack(Material.MUSHROOM_SOUP);
		ItemMeta cacaum = cacau.getItemMeta();
		cacau.setItemMeta(cacaum);
		
		ShapelessRecipe a1 = new ShapelessRecipe(cacau);
		a1.addIngredient(1, Material.INK_SACK, 3);
		a1.addIngredient(1, Material.BOWL);
		
		Bukkit.getServer().addRecipe(a1);
	}
	
	public static List<Player> getOnlinePlayers() {
		final List<Player> list = new ArrayList<>();
		
		for (World world : FrostGladiator.getInstance().getServer().getWorlds()) {
			for (Player player : world.getPlayers()) {
				list.add(player);
			}
		}
		
		return list;
	}
	
	private void makeWorld() {
		WorldCreator wc = new WorldCreator("gladiatores");
		wc.generateStructures(false);
		wc.generator(new GladiatorChunk());
		Bukkit.getServer().createWorld(wc);
	}
	
}
