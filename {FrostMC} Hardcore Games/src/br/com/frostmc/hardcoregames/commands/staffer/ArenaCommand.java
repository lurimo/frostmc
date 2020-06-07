package br.com.frostmc.hardcoregames.commands.staffer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.util.FinalBattle;

public class ArenaCommand extends BaseCommand {

	public ArenaCommand() {
		super("arena");
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = (Player) commandSender;
			if (!BukkitMain.getPermissions().isYoutuberPlus(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("");
				player.sendMessage("§3§lARENA-COMANDOS");
				player.sendMessage("");
				player.sendMessage("§f/arena (largura) (altura)§8- §7Criar uma arena a gosto.");
				player.sendMessage("§f/arena (limpar/final) §8- §7Limpar/Spawnar uma arena.");
				player.sendMessage("");
				return true;
			}
			if (args.length == 1) {
	    		if (args[0].equalsIgnoreCase("limpar") || args[0].equalsIgnoreCase("clear")) {
	    			limparArena(player);
	    		} else if (args[0].equalsIgnoreCase("final")) {
	    			player.sendMessage("");
	    			player.sendMessage("§3§lARENA §fTentando spawnar a arena final.");
	    			player.sendMessage("");
	    			if (!FrostHG.getManager().arenaFinal) {
						FrostHG.getManager().arenaFinal = true;
						FinalBattle.createArena();
					}
	    		}
			} else if (args.length == 2) {
	    		String largura = args[0], altura = args[1];
	    		if ((!isInteger(largura)) || (!isInteger(altura))) {
	    			sendNumber(player, "Digite apenas números.");
	    			return true;
	    		}
	    		player.sendMessage("");
    			player.sendMessage("§3§lARENA §fTentando criar uma arena (" + largura + ", " + altura + ", " + largura + ")");
    			player.sendMessage("");
	    		criarArena(player.getLocation(), Integer.valueOf(largura), Integer.valueOf(altura));
	    		player.sendMessage("§3§lARENA §fVocê criou uma arena §b(" + largura + ", " + altura + ", " + largura + ")");
	    		return true;
			}
		} else {
			return true;
		}
		return false;
	}
	
	private static Location ponto_baixo;
	private static Location ponto_alto;
	
	public static void criarArena(Location location, int largura, int altura) {
        List<Location> cuboid = new ArrayList<>();
        cuboid.clear();
    	for (int bX = -largura; bX <= largura; bX++) {
             for (int bZ = -largura; bZ <= largura; bZ++) {
                  for (int bY = -1; bY <= altura; bY++) {
                	   if (bY == -1) {
                           cuboid.add(location.clone().add(bX, bY, bZ));
                       } else if ((bX == -largura) || (bZ == -largura) || (bX == largura) || (bZ == largura)) {
                           cuboid.add(location.clone().add(bX, bY, bZ));
                       }
                  }
             }
    	 }
    	
         for (Location loc1 : cuboid)
              loc1.getBlock().setType(Material.BEDROCK);
   
         
         Location PB = location.clone().add(largura - 1, 0, largura - 1);
         ponto_baixo = PB;
         Location PA = location.clone().subtract(largura - 1, 0, largura - 1);
         PA.add(0, altura, 0);
         ponto_alto = PA;
	}
	
	public void limparArena(Player p) {
		if (ponto_alto == null) {
			p.sendMessage("§3§lARENA §fNão existe uma arena para limpar.");
			return;
		}
		
		for (Block b : getBlocks(ponto_baixo, ponto_alto))
			 b.setType(Material.AIR);
        
		p.sendMessage("§3§lARENA §fOs drops da arena foram removidos.");
	}
	
	public List<Location> getLocationsFromTwoPoints(Location location1, Location location2) {
		List<Location> locations = new ArrayList<>();
		int topBlockX = (location1.getBlockX() < location2.getBlockX() ? location2.getBlockX() : location1.getBlockX());
		int bottomBlockX = (location1.getBlockX() > location2.getBlockX() ? location2.getBlockX() : location1.getBlockX());
		int topBlockY = (location1.getBlockY() < location2.getBlockY() ? location2.getBlockY() : location1.getBlockY());
		int bottomBlockY = (location1.getBlockY() > location2.getBlockY() ? location2.getBlockY() : location1.getBlockY());
		int topBlockZ = (location1.getBlockZ() < location2.getBlockZ() ? location2.getBlockZ() : location1.getBlockZ());
		int bottomBlockZ = (location1.getBlockZ() > location2.getBlockZ() ? location2.getBlockZ() : location1.getBlockZ());
		for (int x = bottomBlockX; x <= topBlockX; x++)
		for (int z = bottomBlockZ; z <= topBlockZ; z++) 
		for (int y = bottomBlockY; y <= topBlockY; y++) 
		     locations.add(new Location(location1.getWorld(), x, y, z));
		return locations;
	}
	
	public List<Block> getBlocks(Location location1, Location location2) {
		List<Block> blocks = new ArrayList<>();
		for (Location loc : getLocationsFromTwoPoints(location1, location2)) {
			 Block b = loc.getBlock();
			 if (!b.getType().equals(Material.AIR))
			     blocks.add(b);
		}
		return blocks;
	}

}
