package br.com.frostmc.hardcoregames.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Difficulty;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.inventorys.ManagerInventory;
import br.com.frostmc.hardcoregames.listeners.PrincipalsListener;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;
import br.com.frostmc.hardcoregames.timer.Timer;
import br.com.frostmc.hardcoregames.util.admin.AdminManager;
import br.com.frostmc.hardcoregames.util.boosbar.BarAPI;
import br.com.frostmc.hardcoregames.util.combatlog.Combat;
import br.com.frostmc.hardcoregames.util.kit.KitAPI;
import br.com.frostmc.hardcoregames.util.kit.Kits;
import br.com.frostmc.hardcoregames.util.kit.hability.Scout;
import br.com.frostmc.hardcoregames.util.kit.hability.Surprise;

public class Manager {

	private HashMap<UUID, String> deathMessage = new HashMap<>();
	private HashMap<UUID, String> firstBlood = new HashMap<>();
	
	private ArrayList<UUID> jogadores = new ArrayList<>();
	private ArrayList<UUID> espectadores = new ArrayList<>();
	private ArrayList<UUID> scoreboard = new ArrayList<>();
	private ArrayList<UUID> antiLogged = new ArrayList<>();
	private ArrayList<Block> borderBlock = new ArrayList<>();
	public HashMap<UUID, Integer> kills = new HashMap<>();
	public ArrayList<Location> locationsfirework = new ArrayList<>();
	public ArrayList<String> kitsDesativados = new ArrayList<>();
	public static Player winner;
	
	public static Player getWinner() {
		return winner;
	}
	
	public static void setWinner(Player player) {
		winner = player;
	}
	
	private KitAPI kitAPI;

	public boolean disablekit;
	public boolean arenaFinal;

	public Manager( ) {
		this.kitAPI = new KitAPI();
	}

	public void start() {
		Timer.iniciarTimer();
		
		Timer.spawnar("coliseu", new Location(Bukkit.getWorld("world"), 0, 120, 0));
		
		FrostHG.state = State.INICIANDO;
		World world = Bukkit.getWorlds().get(0);
		world.setTime(0);
		world.setStorm(false);
		world.setThundering(false);
		world.setDifficulty(Difficulty.PEACEFUL);
	}

	public void startInvencivel() {
		for (Iterator<?> iterator = Schematic.Coliseu.iterator(); iterator.hasNext();) {
			Block block = (Block) iterator.next();
			if (block.getType().equals(Material.PISTON_BASE)) {
				block.setType(Material.AIR);
			}
		}
		FrostHG.state = State.INVENCIVEL;
		Timer.tempo = 120;
		World world = Bukkit.getWorlds().get(0);
		world.setTime(0);
		world.setStorm(false);
		world.setThundering(false);
		world.setDifficulty(Difficulty.PEACEFUL);
		for (Player player : FrostHG.getManager().getOnlinePlayers()) {
			this.kills.put(player.getUniqueId(), 0);
			Scoreboarding.setScoreboard(player);
			Bukkit.getWorld("world").setDifficulty(Difficulty.NORMAL);
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 3F, 3F);
			if (!AdminManager.isAdmin(player)) {
				FrostHG.getManager().setupPlayer(player);
				if (this.kitAPI.hasKit(player, Kits.SURPRISE)) {
					new Surprise().setSurprise(player);
				} else {
					this.kitAPI.giveKit(player, Kits.valueOf(this.kitAPI.getKit(player).toUpperCase()));
				}
			}
			player.getInventory().addItem(new ItemBuilder().setNome("§aBússola").setMaterial(Material.COMPASS).setQuantia(1).finalizar());
			player.updateInventory();
		}
		world.playSound(world.getSpawnLocation(), Sound.AMBIENCE_THUNDER, 4.0F, 4.0F);
		Bukkit.broadcastMessage("§6§lPARTIDA §fTodos estão invenciveis por 2 minutos!");
		Bukkit.broadcastMessage("§6§lPARTIDA §fQue a sorte esteja sempre ao seu favor!");
	}

	public void startGame() {
		//for (Iterator<?> iterator = Schematic.Coliseu.iterator(); iterator.hasNext();) {
			//Block block = (Block) iterator.next();
			//block.setType(Material.AIR);
		//}
		FrostHG.state = State.JOGO;
		World world = Bukkit.getWorlds().get(0);
		world.setTime(0);
		world.setStorm(false);
		world.setThundering(false);
		world.setDifficulty(Difficulty.HARD);
		for (Player player : FrostHG.getManager().getOnlinePlayers()) {
			BarAPI.removeBar(player);
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 3F, 3F);
			if (!AdminManager.isAdmin(player)) {
				Scoreboarding.setScoreboard(player);
				Scout.checkPotion(player);
			} else {
				if (BukkitMain.getPermissions().isTrial(player)) {
					AdminManager.setAdmin(player);
				} else {
					setEspectador(player);
				}
				if (FrostHG.getManager().getJogadores().size() >= 1) {
					for (Player players : FrostHG.getManager().getOnlinePlayers()) {
						Scoreboarding.updateJogadores(players, players.getScoreboard());
					}
				}
			}
		}
	}
	
	public static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static void clearEffects(Player p) {
		for (PotionEffect effect : p.getActivePotionEffects())
		     p.removePotionEffect(effect.getType());
	}
	
	public void setEspectador(Player player) {
		setupPlayer(player);
		if (getJogadores().contains(player.getUniqueId()))
			getJogadores().remove(player.getUniqueId());
		if (!getEspectadores().contains(player.getUniqueId()))
			getEspectadores().add(player.getUniqueId());
		
		player.setHealth(20.0);
		player.closeInventory();
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[4]);
		player.setFoodLevel(20);
		player.setAllowFlight(true);
		player.setFlying(true);
		player.setVelocity(new Vector(0, 1, 0));
		
		for (Player ons : FrostHG.getManager().getOnlinePlayers()) {
			if (FrostHG.getManager().getJogadores().contains(ons.getUniqueId())) {
			 	ons.hidePlayer(player);
		 	} else if (FrostHG.getManager().getEspectadores().contains(ons.getUniqueId())) {
			 	ons.showPlayer(player);
		 	}
		}
		
		if (!kills.containsKey(player.getUniqueId()))
			kills.put(player.getUniqueId(), 0);
		Scoreboarding.setScoreboard(player);
		player.sendMessage("§3§lSPEC §fVocê agora é um espectador!");
		player.getInventory().setItem(0, new ItemBuilder().setNome(ManagerInventory.spectatorSelector).setMaterial(Material.BOOK).setLore(Arrays.asList("§fVeja os jogadores vivos.")).finalizar());
		player.updateInventory();
	}

	public String getTimerFormat(int timer) {
		int a = timer / 60, b = timer % 60;
		String c = null;
		String d = null;
		if (a >= 10) {
			c = "" + a;
		} else {
			c = "0" + a;
		}
		if (b >= 10) {
			d = "" + b;
		} else {
			d = "0" + b;
		}
		return c + ":" + d;
	}
	
	public static boolean checkItem(ItemStack item, String display) {
		return (item != null && item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
		&& item.getItemMeta().getDisplayName().startsWith(display));
	}
	
	public static boolean isFull(Inventory p) {
		if (p.firstEmpty() == -1)
			return true;
		return false;
	}

	public String getTimerChat(int timer) {
		int minutos = timer / 60, segundos = timer % 60;
		String mensagem = "";
		String mMsg = "";
		String sMsg = "";
		if (minutos > 0 && segundos == 0) {
			mMsg = minutos == 1 ? " minuto" : " minutos";
			mensagem = minutos + mMsg;
		} else if (minutos == 0 && segundos > 0) {
			sMsg = segundos == 1 ? " segundo" : " segundos";
			mensagem = segundos + sMsg;
		} else if (minutos > 0 && segundos > 0) {
			mMsg = minutos == 1 ? " minuto" : " minutos";
			sMsg = segundos == 1 ? " segundo" : " segundos";
			mensagem = minutos + mMsg + " e " + segundos + sMsg;
		}
		return mensagem;
	}
	
	public void checkWinner() {
		if (getJogadores().size() == 0) {
			Bukkit.shutdown();
			return;
		}
		if (getJogadores().size() > 1)
			return;
		if (getJogadores().size() == 1) {
			FrostHG.state = State.FINAL;
			for (Player todos : FrostHG.getManager().getOnlinePlayers()) {
				for(Entity entity : todos.getWorld().getEntities()) {
					if(entity instanceof Item) {
						entity.remove();
					}
				}
			}
			Player winner = Bukkit.getPlayer(FrostHG.getManager().getJogadores().get(0));
			setWinner(winner);
			Scoreboarding.setScoreboard(winner);
			Gamer gamer = BukkitMain.getGamerManager().getGamer(winner);
			gamer.addWins_hg(1);
			sendFireworks();
			winner.getInventory().clear();
			winner.getInventory().setArmorContents(null);
			winner.updateInventory();
			new BukkitRunnable() {
				int segundos = 25;
				public void run() {
					if (segundos == 0) {
						cancel();
						stop();
						return;
					}
					if (segundos % 2 == 0) {
						Bukkit.broadcastMessage("§a§l" + winner.getName() + " §7ganhou o jogo!");
					}
					if (!winner.isOnline()) {
						cancel();
						stop();
						return;
					}
					segundos--;
				}
			}.runTaskTimer(FrostHG.getInstance(), 20, 20);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void stop() {
		Player winner = getWinner();
		TeleportServer.connectPlayer(winner, ServersType.LOBBY);
		
		for (Player on : Bukkit.getOnlinePlayers()) {
			Gamer gamer = BukkitMain.getGamerManager().getGamer(on);
			gamer.addWins_hg(1);
			TeleportServer.connectPlayer(on, ServersType.LOBBY);
		}
		
		new BukkitRunnable() {
			public void run() {
				Bukkit.shutdown();
			}
		}.runTaskLater(FrostHG.getInstance(), 40L);
	}

	public String getCausa(DamageCause deathCause) {
		String cause = "";
		if (deathCause.equals(DamageCause.ENTITY_ATTACK)) {
			cause = "atacado por um monstro";
		} else if (deathCause.equals(DamageCause.CUSTOM)) {
			cause = "de uma forma não conhecida";
		} else if (deathCause.equals(DamageCause.BLOCK_EXPLOSION)) {
			cause = "explodido em mil pedaços";
		} else if (deathCause.equals(DamageCause.ENTITY_EXPLOSION)) {
			cause = "explodido por um monstro";
		} else if (deathCause.equals(DamageCause.CONTACT)) {
			cause = "espetado por um cacto";
		} else if (deathCause.equals(DamageCause.FALL)) {
			cause = "de queda";
		} else if (deathCause.equals(DamageCause.FALLING_BLOCK)) {
			cause = "stompado por um bloco";
		} else if (deathCause.equals(DamageCause.FIRE_TICK)) {
			cause = "pegando fogo";
		} else if (deathCause.equals(DamageCause.LAVA)) {
			cause = "nadando na lava";
		} else if (deathCause.equals(DamageCause.LIGHTNING)) {
			cause = "atingido por um raio";
		} else if (deathCause.equals(DamageCause.MAGIC)) {
			cause = "atingido por uma magia";
		} else if (deathCause.equals(DamageCause.MELTING)) {
			cause = "atingido por um boneco de neve";
		} else if (deathCause.equals(DamageCause.POISON)) {
			cause = "envenenado";
		} else if (deathCause.equals(DamageCause.PROJECTILE)) {
			cause = "atingido por um projétil";
		} else if (deathCause.equals(DamageCause.STARVATION)) {
			cause = "de fome";
		} else if (deathCause.equals(DamageCause.SUFFOCATION)) {
			cause = "sufocado";
		} else if (deathCause.equals(DamageCause.SUICIDE)) {
			cause = "se suicidando";
		} else if (deathCause.equals(DamageCause.THORNS)) {
			cause = "encostando em alguns espinhos";
		} else if (deathCause.equals(DamageCause.VOID)) {
			cause = "pelo void";
		} else if (deathCause.equals(DamageCause.WITHER)) {
			cause = "pelo efeito do whiter";
		} else {
			cause = "por uma causa desconhecida";
		}
		return cause;
	}

	public void setupPlayer(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setHealth(20.0D);
		player.setFoodLevel(20);
		player.setFireTicks(0);
	}
	
	public Location getSpawn() {
		World world = (World) Bukkit.getServer().getWorlds().get(0);
		Location location = new Location(Bukkit.getWorld("world"), 0.500, (world.getHighestBlockYAt(0, 0) + 1.200), 0.500);
		return location;
	}

	public String getSala() {
		Server server = Bukkit.getServer();
		if (server.getPort() == ServersType.HG_A1.getPort()) {
			return "1";
		} else if (server.getPort() == ServersType.HG_A2.getPort()) {
			return "2";
		} else if (server.getPort() == ServersType.HG_A3.getPort()) {
			return "3";
		} else if (server.getPort() == ServersType.HG_A4.getPort()) {
			return "4";
		} else if (server.getPort() == ServersType.HG_A5.getPort()) {
			return "5";
		} else if (server.getPort() == ServersType.HG_A6.getPort()) {
			return "6";
		} else if (server.getPort() == ServersType.HG_A7.getPort()) {
			return "7";
		}
		return "?";
	}
	
	public static void spawnRandomFirework(Location loc) {
		Firework fw = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		Random r = new Random();    
		int rt = r.nextInt(4) + 3;
		FireworkEffect.Type type = FireworkEffect.Type.BALL;
		if (rt == 1) {
			type = FireworkEffect.Type.BALL;
		} else if (rt == 2) {
		    type = FireworkEffect.Type.BALL_LARGE;
		} else if (rt == 3) {
		    type = FireworkEffect.Type.BURST;
		} else if (rt == 4) {
		    type = FireworkEffect.Type.CREEPER;
		} else if (rt == 5) {
			type = FireworkEffect.Type.STAR;
		} else if (rt > 5) {
			type = FireworkEffect.Type.BALL_LARGE;
		}
		Color c1 = Color.RED;
		Color c2 = Color.LIME;
		Color c3 = Color.SILVER;		    
		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withColor(c2).withFade(c3).with(type).trail(r.nextBoolean()).build();
		fwm.addEffect(effect);			    
		int rp = r.nextInt(2) + 1;
		fwm.setPower(rp);
		fw.setFireworkMeta(fwm);
	}
	
	public static void sendFireworks() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(FrostHG.getInstance(), new Runnable() {
		int vezes = 0;
		public void run() {
			if (vezes == 7) {
				return;
			}
			vezes++;
			Random r = new Random();
	        spawnRandomFirework(((World)Bukkit.getServer().getWorlds().get(0)).getHighestBlockAt(getWinner().getLocation().add(0.0D, 0.0D, r.nextInt(5) + 5).add(0.0D, 5.0D, 0.0D)).getLocation());
	        spawnRandomFirework(((World)Bukkit.getServer().getWorlds().get(0)).getHighestBlockAt(getWinner().getLocation().add(r.nextInt(5) + 5, 0.0D, 0.0D).add(0.0D, 5.0D, 0.0D)).getLocation());
	        spawnRandomFirework(((World)Bukkit.getServer().getWorlds().get(0)).getHighestBlockAt(getWinner().getLocation().add(r.nextInt(5) + 5, 0.0D, r.nextInt(5) + 5).add(0.0D, 5.0D, 0.0D)).getLocation());
	        spawnRandomFirework(((World)Bukkit.getServer().getWorlds().get(0)).getHighestBlockAt(getWinner().getLocation().add(-r.nextInt(5) - 5, 0.0D, 0.0D).add(0.0D, 5.0D, 0.0D)).getLocation());
	        spawnRandomFirework(((World)Bukkit.getServer().getWorlds().get(0)).getHighestBlockAt(getWinner().getLocation().add(0.0D, 0.0D, -r.nextInt(5) - 5).add(0.0D, 5.0D, 0.0D)).getLocation());
	        spawnRandomFirework(((World)Bukkit.getServer().getWorlds().get(0)).getHighestBlockAt(getWinner().getLocation().add(-r.nextInt(5) - 5, 0.0D, -r.nextInt(5) - 5).add(0.0D, 5.0D, 0.0D)).getLocation());
	        spawnRandomFirework(((World)Bukkit.getServer().getWorlds().get(0)).getHighestBlockAt(getWinner().getLocation().add(-r.nextInt(5) - 5, 0.0D, r.nextInt(5) + 5).add(0.0D, 5.0D, 0.0D)).getLocation());
	        spawnRandomFirework(((World)Bukkit.getServer().getWorlds().get(0)).getHighestBlockAt(getWinner().getLocation().add(r.nextInt(5) + 5, 0.0D, -r.nextInt(5) - 5).add(0.0D, 5.0D, 0.0D)).getLocation());
		}
		}, 0, 20);
	}

	@SuppressWarnings("deprecation")
	public void loadRecepients() {
		ItemStack a = new ItemStack(Material.MUSHROOM_SOUP);
		ItemStack b = new ItemStack(Material.MUSHROOM_SOUP);
		ItemStack c = new ItemStack(Material.MUSHROOM_SOUP);
		ItemStack d = new ItemStack(Material.MUSHROOM_SOUP);
		ItemStack e = new ItemStack(Material.MUSHROOM_SOUP);
		ItemStack f = new ItemStack(Material.MUSHROOM_SOUP);
		
		ShapelessRecipe a1 = new ShapelessRecipe(a);
		a1.addIngredient(1, Material.INK_SACK, 3);
		a1.addIngredient(1, Material.BOWL);
		
		ShapelessRecipe a2 = new ShapelessRecipe(b);
		a2.addIngredient(1, Material.CACTUS);
		a2.addIngredient(1, Material.BOWL);
		
		ShapelessRecipe a3 = new ShapelessRecipe(c);
		a3.addIngredient(1, Material.RED_ROSE);
		a3.addIngredient(1, Material.YELLOW_FLOWER);
		a3.addIngredient(1, Material.BOWL);
		
		ShapelessRecipe a4 = new ShapelessRecipe(d);
		a4.addIngredient(1, Material.PUMPKIN_SEEDS);
		a4.addIngredient(1, Material.BOWL);
		
		ShapelessRecipe a5 = new ShapelessRecipe(e);
		a5.addIngredient(1, Material.MELON_SEEDS);
		a5.addIngredient(1, Material.BOWL);
		
		ShapelessRecipe a6 = new ShapelessRecipe(f);
		a6.addIngredient(1, Material.NETHER_STALK);
		a6.addIngredient(1, Material.BOWL);
		
		Bukkit.getServer().addRecipe(a1);
		Bukkit.getServer().addRecipe(a2);
		Bukkit.getServer().addRecipe(a3);
		Bukkit.getServer().addRecipe(a4);
		Bukkit.getServer().addRecipe(a5);
		Bukkit.getServer().addRecipe(a6);
	}

	public void loadChunks() {
		Bukkit.getConsoleSender().sendMessage("§3§lCHUNK §fCarregando chunks...");
		World world = Bukkit.getWorld("world");

		for (int x = -520; x <= 520; x += 16) {
			for (int z = -520; z <= 520; z += 16) {
				Location location = new Location(world, x, world.getHighestBlockYAt(x, z), z);
				world.getChunkAt(location).load(true);
			}
		}
		Bukkit.getConsoleSender().sendMessage("§3§lCHUNK §fOs chunks foram carregados com sucesso.");
	}
	
	public void checkBorder(Player p, int raio) {
		if (p.getLocation().getBlockX() >= raio || p.getLocation().getBlockX() <= -raio ||  p.getLocation().getBlockZ() >= raio || p.getLocation().getBlockZ() <= -raio) {
			if ((FrostHG.getManager().getEspectadores().contains(p.getUniqueId())) || (AdminManager.isAdmin(p))) {
				return;
			}
			if (FrostHG.state==State.JOGO) {
				p.setFireTicks(100);
			    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20, 2));
			    p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20, 2));
			    p.damage(3.0D);
			    p.sendMessage("§4§lBORDA §fSaia de perto da barreira!");
			} else if (FrostHG.state==State.INVENCIVEL) {
				 p.sendMessage("§4§lBORDA §fSaia de perto da barreira!");
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void respawn(Player p) {
		new BukkitRunnable() {
			public void run() {
				p.setHealth(20.0D);
			    p.setNoDamageTicks(100);
			    p.getPlayer().setItemOnCursor(new ItemStack(0));
			    p.closeInventory();
			    p.getPlayer().getInventory().setArmorContents(new ItemStack[4]);
			    p.getPlayer().getInventory().clear();
			    p.setFoodLevel(20);
			    p.setExp(0);
			    p.setLevel(0);
			    p.setFireTicks(0);
		    
	        	for (PotionEffect pe : p.getActivePotionEffects())
	        		p.removePotionEffect(pe.getType());
		    
		    	Location loc = getRespawnLocation(230);
		    	p.teleport(loc);
		    
		    	new BukkitRunnable() {
					public void run() {
						p.getInventory().addItem(new ItemBuilder().setNome("§aBússola").setMaterial(Material.COMPASS).setQuantia(1).finalizar());
						FrostHG.getManager().getKitAPI().giveKit(p, Kits.valueOf(FrostHG.getManager().getKitAPI().getKit(p).toUpperCase()));
						p.updateInventory();
					}
				}.runTaskLater(FrostHG.getInstance(), 20L);
		    
				if (Combat.inCombat(p.getUniqueId())) {
					if (Combat.combatLogs.containsKey(p.getUniqueId())) {
						Combat.combatLogs.remove(p.getUniqueId());
				    }
				}
				p.sendMessage("§a§lRESPAWN §fVocê renasceu antes dos " + (BukkitMain.getPermissions().isLight(p) ? "5" : "6") + " de andamento.");
			}
		}.runTaskLater(FrostHG.getInstance(), 4L);
	}
	
	public static Location getRespawnLocation(int maximo) {
		Random r = new Random();
		int x = r.nextInt(maximo) + maximo;
		int z = r.nextInt(maximo) + maximo;
		
		if (r.nextBoolean())
			x = -x;
		if (r.nextBoolean())
			z = -z;
		
		World world = (World) Bukkit.getServer().getWorlds().get(0);
		Location loc = new Location(world, x + 0.500, (world.getHighestBlockYAt(x, z) + 1.200), z + 0.500);
		return loc;
	}

	public List<Player> getOnlinePlayers() {
		List<Player> list = new ArrayList<>();
		for (World world : FrostHG.getInstance().getServer().getWorlds()) {
			for (Player player : world.getPlayers()) {
				list.add(player);
			}
		}
		return list;
	}
	
	public boolean isJogador(Player p) {
		return getJogadores().contains(p.getUniqueId());
	}
	
	public List<Player> getAliveGamers() {
		List<Player> gamers = new ArrayList<>();
		for (Player player : getOnlinePlayers())
			 if (isJogador(player)) {
				 gamers.add(player);
			 }
		return gamers;
	}

	public void setupShematics() {
		File file = new File(FrostHG.getInstance().getDataFolder(), "minifeast.schematic");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			copiarConfig(FrostHG.getInstance().getResource("minifeast.schematic"), file);
		}
		file = new File(FrostHG.getInstance().getDataFolder(), "coliseu.schematic");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			copiarConfig(FrostHG.getInstance().getResource("coliseu.schematic"), file);
		}
	}
	
	public void deathMessage(Player player, String cause) {
		this.deathMessage.put(player.getUniqueId(), cause);
		Bukkit.broadcastMessage("§bO jogador " + player.getName() + "(" + FrostHG.getManager().getKitAPI().getKit(player) + ") " + cause);
		if (!PrincipalsListener.respawn) {
			if (this.jogadores.size() > 1) {
				Bukkit.broadcastMessage("§e" + this.jogadores.size() + " jogadores restantes.");
			} else {
				Bukkit.broadcastMessage("§e" + this.jogadores.size() + " jogador restante.");
			}
			Bukkit.broadcastMessage("§7" + player.getName() + " saiu do servidor.");
		}
	}

	protected void copiarConfig(InputStream i, File config) {
		try {
			OutputStream out = new FileOutputStream(config);
			byte[] buf = new byte[710];
			int len;
			while ((len = i.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			i.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createArena(Location location, int raio, Material material, int alturaY) {
		int cx = location.getBlockX();
		int cy = location.getBlockY();
		int cz = location.getBlockZ();
		World w = location.getWorld();
		int rSquared = raio * raio;
		for (int x = cx - raio; x <= cx + raio; x++)
			for (int z = cz - raio; z <= cz + raio; z++)
				for (int y = cy + 1; y <= cy + alturaY; y++)
					if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared)
						w.getBlockAt(x, y, z).setType(material);
	}

	public String getItem(Player player) {
		ItemStack i = player.getInventory().getItemInHand();
		String nome = null;
		if (i.getType() == Material.AIR) {
			nome = "O Soco";
		} else if (i.getType() == Material.WOOD_SWORD) {
			nome = "Uma Espada de Madeira";
		} else if (i.getType() == Material.STONE_SWORD) {
			nome = "Uma Espada de Pedra";
		} else if (i.getType() == Material.IRON_SWORD) {
			nome = "Uma Espada de Ferro";
		} else if (i.getType() == Material.DIAMOND_SWORD) {
			nome = "Uma Espada de Diamante";
		} else if (i.getType() == Material.GOLD_SWORD) {
			nome = "Uma Espada de Ouro";
		} else if (i.getType() == Material.WOOD_AXE) {
			nome = "Um Machado de Madeira";
		} else if (i.getType() == Material.STONE_AXE) {
			nome = "Um Machado de Pedra";
		} else if (i.getType() == Material.IRON_AXE) {
			nome = "Um Machado de Ferro";
		} else if (i.getType() == Material.DIAMOND_AXE) {
			nome = "Um Machado de Diamante";
		} else if (i.getType() == Material.GOLD_AXE) {
			nome = "Um Machado de Ouro";
		} else if (i.getType() == Material.WOOD_SPADE) {
			nome = "Uma Pa de Madeira";
		} else if (i.getType() == Material.STONE_SPADE) {
			nome = "Uma Pa de Pedra";
		} else if (i.getType() == Material.IRON_SPADE) {
			nome = "Uma Pa de Ferro";
		} else if (i.getType() == Material.DIAMOND_SPADE) {
			nome = "Uma Pa de Diamante";
		} else if (i.getType() == Material.GOLD_SPADE) {
			nome = "Uma Pa de Ouro";
		} else if (i.getType() == Material.WOOD_PICKAXE) {
			nome = "Uma Picareta de Madeira";
		} else if (i.getType() == Material.STONE_PICKAXE) {
			nome = "Uma Picareta de Pedra";
		} else if (i.getType() == Material.IRON_PICKAXE) {
			nome = "Uma Picareta de Ferro";
		} else if (i.getType() == Material.DIAMOND_PICKAXE) {
			nome = "Uma Picareta de Diamante";
		} else if (i.getType() == Material.GOLD_PICKAXE) {
			nome = "Uma Picareta de Ouro";
		} else if (i.getType() == Material.STICK) {
			nome = "Um Graveto";
		} else if (i.getType() == Material.MAP) {
			nome = "Um Mapa";
		} else if (i.getType() == Material.MUSHROOM_SOUP) {
			nome = "Uma Sopa";
		} else if (i.getType() == Material.RED_MUSHROOM) {
			nome = "Um Cogumelo";
		} else if (i.getType() == Material.BROWN_MUSHROOM) {
			nome = "Um Cogumelo";
		} else if (i.getType() == Material.BOWL) {
			nome = "Uma Tigela";
		} else if (i.getType() == Material.COMPASS) {
			nome = "Uma Bússola";
		} else {
			nome = i.getType().toString();
		}
		return nome.toLowerCase();
	}
	
	public KitAPI getKitAPI() {
		return kitAPI;
	}
	
	public HashMap<UUID, String> getDeathMessage() {
		return deathMessage;
	}
	
	public HashMap<UUID, String> getFirstBlood() {
		return firstBlood;
	}

	public ArrayList<UUID> getScoreboard() {
		return scoreboard;
	}

	public ArrayList<UUID> getJogadores() {
		return jogadores;
	}

	public ArrayList<UUID> getEspectadores() {
		return espectadores;
	}

	public ArrayList<UUID> getAntiLogged() {
		return antiLogged;
	}
	
	public ArrayList<Block> getBorderBlock() {
		return borderBlock;
	}
	
}
