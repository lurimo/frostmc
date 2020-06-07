package br.com.frostmc.hardcoregames.commands.staffer;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.util.InventoryStore;
import br.com.frostmc.hardcoregames.util.Manager;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class SkitCommand extends BaseCommand {
	
	public SkitCommand() {
		super("skit");
	}

	public HashMap<String, InventoryStore> Skits = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isYoutuberPlus(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("");
				player.sendMessage("§3§lSKIT-COMANDOS");
				player.sendMessage("");
				player.sendMessage("§f/skit criar (nome) §8- §7crie um nome para o kit.");
				player.sendMessage("§f/skit aplicar (kit) (jogador/todos/distancia) §8- §7coloque um nome de um kit criado.");
				player.sendMessage("§f/skit list §8- §7Veja todos os kits criados.");
				player.sendMessage("");
				return true;
			}
			if (args.length == 1) {
	    		if (args[0].equalsIgnoreCase("list")) {
	    			if (Skits.size() == 0) {
	    				player.sendMessage("§b§lSKIT §fNenhum kit foi criado.");
	    				return true;
	    			}
	    			String skits = "";
	    			for (String kits : Skits.keySet()) {
	    				 if (skits.equals("")) {
	    					 if (Skits.size() == 1) {
	    						 skits = kits;
	    						 break;
	    					 } else {
	    						 skits = kits;
	    					 }
	    				 } else {
	    					 skits = skits + " ," + kits;
	    				 }
	    			}
	    			player.sendMessage("§b§lSKIT §fKits criados: §7" + skits);
	    		} else {
	    			player.sendMessage("");
	    			player.sendMessage("§3§lSKIT-COMANDOS");
	    			player.sendMessage("");
	    			player.sendMessage("§f/skit criar (nome) §8- §7crie um nome para o kit.");
	    			player.sendMessage("§f/skit aplicar (kit) (jogador/todos/distancia) §8- §7coloque um nome de um kit criado.");
	    			player.sendMessage("§f/skit list §8- §7Veja todos os kits criados.");
	    			player.sendMessage("");
	    		}
	    	} else if (args.length == 2) {
	    		if (!args[0].equalsIgnoreCase("criar")) {
	    			player.sendMessage("");
	    			player.sendMessage("§3§lSKIT-COMANDOS");
	    			player.sendMessage("");
	    			player.sendMessage("§f/skit criar (nome) §8- §7crie um nome para o kit.");
	    			player.sendMessage("§f/skit aplicar (kit) (jogador/todos/distancia) §8- §7coloque um nome de um kit criado.");
	    			player.sendMessage("§f/skit list §8- §7Veja todos os kits criados.");
	    			player.sendMessage("");
	    			return true;
	    		}
	    		String kit = args[1];
	    		if (Skits.containsKey(kit)) {
	    			player.sendMessage("§3§lSKIT §fEste Skit já existe.");
	    			return true;
	    		}
	    		Skits.put(kit, new InventoryStore(kit, player.getInventory().getArmorContents(), player.getInventory().getContents(), (List<PotionEffect>) player.getActivePotionEffects()));
	    		player.sendMessage("§aVocê criou o skit: §7" + kit + "§a.");
	    	} else if (args.length == 3) {
	    		if (!args[0].equalsIgnoreCase("aplicar")) {
	    			player.sendMessage("");
	    			player.sendMessage("§3§lSKIT-COMANDOS");
	    			player.sendMessage("");
	    			player.sendMessage("§f/skit criar (nome) §8- §7crie um nome para o kit.");
	    			player.sendMessage("§f/skit aplicar (kit) (jogador/todos/distancia) §8- §7coloque um nome de um kit criado.");
	    			player.sendMessage("§f/skit list §8- §7Veja todos os kits criados.");
	    			player.sendMessage("");
	    			return true;
	    		}
	    		String kit = args[1];
	    		if (!Skits.containsKey(kit)) {
	    			player.sendMessage("§4§lERRO §fNão foi póssivel encontrar o modo citado.");
	    			return true;
	    		}
				InventoryStore inv = Skits.get(kit);
	    		if ((args[2].equalsIgnoreCase("todos")) || (args[2].equalsIgnoreCase("*"))) {
	    			for (Player ons : Bukkit.getOnlinePlayers()) {
	    				if (FrostHG.getManager().isJogador(ons)) {
	    					 ons.getPlayer().setItemOnCursor(new ItemStack(0));
	    					 ons.getInventory().setArmorContents(inv.getArmor());
	    					 ons.getInventory().setContents(inv.getInv());
	    					 ons.addPotionEffects(inv.getPotions());
	    					 ons.sendMessage("§3§lSKIT §fVocê recebeu o skit: §a" + inv.getNome() + "§f.");
	    					 if (ons.getInventory().contains(Material.WOOL)) {
	    						 ons.getInventory().setItem(ons.getInventory().first(Material.WOOL), null);
	    						FrostHG.getManager().getKitAPI().giveKit(ons, Kits.valueOf(FrostHG.getManager().getKitAPI().getKit(ons).toUpperCase()));
	        					 if (!Manager.isFull(ons.getInventory()))
	        						 ons.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
	    					 }
	    				 }
	    			}
	    			player.sendMessage("§3§lSKIT §fVocê aplicou o skit: §a" + kit + " §fpara todos os jogadores vivos.");
	    		} else if (FrostHG.getManager().isInteger(args[2])) {
	    			int distancia = Integer.valueOf(args[2]);
	    			if (distancia >= 501) {
	    				player.sendMessage("§4§lERRO §fDistancia muito grande.");
	    				return true;
	    			}
					for (Entity ent : player.getNearbyEntities(distancia, 130, distancia)) {
						 if (!(ent instanceof Player))
							 continue;
						 
					  	 Player ons = (Player) ent;
						 if (FrostHG.getManager().isJogador(ons)) {
	    					 ons.getPlayer().setItemOnCursor(new ItemStack(0));
	    					 ons.getInventory().setArmorContents(inv.getArmor());
	    					 ons.getInventory().setContents(inv.getInv());
	    					 ons.addPotionEffects(inv.getPotions());
	    					 ons.sendMessage("§3§lSKIT §fVocê aplicou o skit: §a" + inv.getNome() + "§f.");
	    					 
	    					 if (ons.getInventory().contains(Material.WOOL)) {
	    						 ons.getInventory().setItem(ons.getInventory().first(Material.WOOL), null);
	    						 FrostHG.getManager().getKitAPI().giveKit(ons, Kits.valueOf(FrostHG.getManager().getKitAPI().getKit(ons)));
	    					 }
						 }
					}
	    			player.sendMessage("§3§lSKIT §fVocê aplicou o skit: §a" + kit + " §fpara todos os jogadores vivos no raio de §a" + distancia + " blocos");
	    		} else {
	    			Player t = Bukkit.getPlayer(args[2]);
	    			if (t == null) {
	    				player.sendMessage("§4§lERRO §fEsse jogador encontra-se offline.");
	    				return true;
	    			}
					t.getPlayer().setItemOnCursor(new ItemStack(0));
					t.getInventory().setArmorContents(inv.getArmor());
					t.getInventory().setContents(inv.getInv());
					t.addPotionEffects(inv.getPotions());
					if (t.getInventory().contains(Material.WOOL)) {
						t.getInventory().setItem(t.getInventory().first(Material.WOOL), null);
						FrostHG.getManager().getKitAPI().giveKit(t, Kits.valueOf(FrostHG.getManager().getKitAPI().getKit(t).toUpperCase()));
					}
					
					t.sendMessage("§3§lSKIT §fVocê recebeu o skit: §a" + inv.getNome() + "§f.");
					player.sendMessage("§3§lSKIT §fVocê aplicou o skit: §a" + kit + " §fpara o jogador §e" + t.getName() + "§f.");
	    		}
	    	}
		} else {
			return true;
		}
		return false;
	}

}
