package br.com.frostmc.hardcoregames.timer.minifeast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.timer.Timer;
import br.com.frostmc.hardcoregames.timer.feast.FeastConfig;
import br.com.frostmc.hardcoregames.timer.feast.ItemChance;

public class MiniFeast {

	public static Location MinifeastLocation = null;
	public static ArrayList<Block> MinifeastBlock = new ArrayList<Block>();
	public static ArrayList<ItemChance> itens = new ArrayList<>();
	public static ArrayList<ItemChance> itensMinifeast = new ArrayList<>();
	public static boolean bausSpawned = false;
	
	@SuppressWarnings("deprecation")
	public static void addChest() {
		Location locationFeast = MiniFeast.MinifeastLocation;
		
		Location blockSpawn = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY(), locationFeast.getZ());
		Location blockSpawn1 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY(), locationFeast.getZ());
		Location blockSpawn2 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY(), locationFeast.getZ());
		Location blockSpawn3 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY(), locationFeast.getZ() + 1);
		Location blockSpawn4 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY(), locationFeast.getZ() - 1);
		Location blockSpawn5 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY(), locationFeast.getZ() - 1);
		Location blockSpawn6 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY(), locationFeast.getZ() + 1);
		Location blockSpawn7 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY(), locationFeast.getZ() + 1);
		Location blockSpawn8 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY(), locationFeast.getZ() - 1);
		Location blockSpawn9 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY(), locationFeast.getZ());
		Location blockSpawn10 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY(), locationFeast.getZ());
		Location blockSpawn11 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY(), locationFeast.getZ() + 2);
		Location blockSpawn12 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY(), locationFeast.getZ() - 2);
		Location blockSpawn13 = new Location(locationFeast.getWorld(), locationFeast.getX() + 3, locationFeast.getY(), locationFeast.getZ());
		Location blockSpawn14 = new Location(locationFeast.getWorld(), locationFeast.getX() - 3, locationFeast.getY(), locationFeast.getZ());
		Location blockSpawn15 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY(), locationFeast.getZ() + 3);
		Location blockSpawn16 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY(), locationFeast.getZ() - 3);
		
		Location blockSpawn17 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY(), locationFeast.getZ() - 2);
		Location blockSpawn18 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY(), locationFeast.getZ() + 2);
		Location blockSpawn19 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY(), locationFeast.getZ() - 1);
		Location blockSpawn20 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY(), locationFeast.getZ() + 1);
		
		Location blockSpawn21 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY(), locationFeast.getZ() + 2);
		Location blockSpawn22 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY(), locationFeast.getZ() - 2);
		Location blockSpawn23 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY(), locationFeast.getZ() + 1);
		Location blockSpawn24 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY(), locationFeast.getZ() - 1);
		
		Location blockSpawn25 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY(), locationFeast.getZ() + 2);
		Location blockSpawn26 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY(), locationFeast.getZ() - 2);
		Location blockSpawn27 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY(), locationFeast.getZ() + 2);
		Location blockSpawn28 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY(), locationFeast.getZ() - 2);
		
		Location blockSpawn29 = new Location(locationFeast.getWorld(), locationFeast.getX() + 3, locationFeast.getY(), locationFeast.getZ() + 1);
		Location blockSpawn30 = new Location(locationFeast.getWorld(), locationFeast.getX() + 3, locationFeast.getY(), locationFeast.getZ() - 1);
		Location blockSpawn31 = new Location(locationFeast.getWorld(), locationFeast.getX() - 3, locationFeast.getY(), locationFeast.getZ() + 1);
		Location blockSpawn32 = new Location(locationFeast.getWorld(), locationFeast.getX() - 3, locationFeast.getY(), locationFeast.getZ() - 1);
		Location blockSpawn33 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY(), locationFeast.getZ() + 3);
		Location blockSpawn34 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY(), locationFeast.getZ() - 3);
		Location blockSpawn35 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY(), locationFeast.getZ() + 3);
		Location blockSpawn36 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY(), locationFeast.getZ() - 3);
		
		Location blockSpawn37 = new Location(locationFeast.getWorld(), locationFeast.getX() + 3, locationFeast.getY(), locationFeast.getZ() + 2);
		Location blockSpawn38 = new Location(locationFeast.getWorld(), locationFeast.getX() + 3, locationFeast.getY(), locationFeast.getZ() - 2);
		Location blockSpawn39 = new Location(locationFeast.getWorld(), locationFeast.getX() - 3, locationFeast.getY(), locationFeast.getZ() + 2);
		Location blockSpawn40 = new Location(locationFeast.getWorld(), locationFeast.getX() - 3, locationFeast.getY(), locationFeast.getZ() - 2);
		Location blockSpawn41 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY(), locationFeast.getZ() + 3);
		Location blockSpawn42 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY(), locationFeast.getZ() - 3);
		Location blockSpawn43 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY(), locationFeast.getZ() + 3);
		Location blockSpawn44 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY(), locationFeast.getZ() - 3);
		
		Location blockSpawn45 = new Location(locationFeast.getWorld(), locationFeast.getX() + 4, locationFeast.getY(), locationFeast.getZ());
		Location blockSpawn46 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4, locationFeast.getY(), locationFeast.getZ());
		Location blockSpawn47 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY(), locationFeast.getZ() + 4);
		Location blockSpawn48 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY(), locationFeast.getZ() - 4);
		
		Location blockSpawn49 = new Location(locationFeast.getWorld(), locationFeast.getX() + 3, locationFeast.getY(), locationFeast.getZ() + 4);
		Location blockSpawn50 = new Location(locationFeast.getWorld(), locationFeast.getX() + 3, locationFeast.getY(), locationFeast.getZ() - 4);
		Location blockSpawn51 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4, locationFeast.getY(), locationFeast.getZ() + 3);
		Location blockSpawn52 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4, locationFeast.getY(), locationFeast.getZ() - 3);
		
		Location blockSpawn53 = new Location(locationFeast.getWorld(), locationFeast.getX() + 4, locationFeast.getY(), locationFeast.getZ() + 1);
		Location blockSpawn54 = new Location(locationFeast.getWorld(), locationFeast.getX() + 4 , locationFeast.getY(), locationFeast.getZ() + 2);
		Location blockSpawn55 = new Location(locationFeast.getWorld(), locationFeast.getX() + 4, locationFeast.getY(), locationFeast.getZ() + 3);
		Location blockSpawn56 = new Location(locationFeast.getWorld(), locationFeast.getX() + 4, locationFeast.getY(), locationFeast.getZ() + 4);
		Location blockSpawn57 = new Location(locationFeast.getWorld(), locationFeast.getX() + 4, locationFeast.getY(), locationFeast.getZ() - 1);
		Location blockSpawn58 = new Location(locationFeast.getWorld(), locationFeast.getX() + 4 , locationFeast.getY(), locationFeast.getZ() - 2);
		Location blockSpawn59 = new Location(locationFeast.getWorld(), locationFeast.getX() + 4, locationFeast.getY(), locationFeast.getZ() - 3);
		Location blockSpawn60 = new Location(locationFeast.getWorld(), locationFeast.getX() + 4, locationFeast.getY(), locationFeast.getZ() - 4);
		
		Location blockSpawn61 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4, locationFeast.getY(), locationFeast.getZ() - 1);
		Location blockSpawn62 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4 , locationFeast.getY(), locationFeast.getZ() - 2);
		Location blockSpawn63 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4, locationFeast.getY(), locationFeast.getZ() - 3);
		Location blockSpawn64 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4, locationFeast.getY(), locationFeast.getZ() - 4);
		Location blockSpawn65 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4, locationFeast.getY(), locationFeast.getZ() + 1);
		Location blockSpawn66 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4 , locationFeast.getY(), locationFeast.getZ() + 2);
		Location blockSpawn67 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4, locationFeast.getY(), locationFeast.getZ() + 3);
		Location blockSpawn68 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4, locationFeast.getY(), locationFeast.getZ() + 4);
		
		Location blockSpawn69 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY(), locationFeast.getZ() + 4);
		Location blockSpawn70 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2 , locationFeast.getY(), locationFeast.getZ() + 4);
		Location blockSpawn71 = new Location(locationFeast.getWorld(), locationFeast.getX() + 3, locationFeast.getY(), locationFeast.getZ() + 4);
		Location blockSpawn72 = new Location(locationFeast.getWorld(), locationFeast.getX() + 4, locationFeast.getY(), locationFeast.getZ() + 4);
		Location blockSpawn73 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY(), locationFeast.getZ() - 4);
		Location blockSpawn74 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2 , locationFeast.getY(), locationFeast.getZ() - 4);
		Location blockSpawn75 = new Location(locationFeast.getWorld(), locationFeast.getX() + 3, locationFeast.getY(), locationFeast.getZ() - 4);
		Location blockSpawn76 = new Location(locationFeast.getWorld(), locationFeast.getX() + 4, locationFeast.getY(), locationFeast.getZ() - 4);
		
		Location blockSpawn77 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY(), locationFeast.getZ() - 4);
		Location blockSpawn78 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2 , locationFeast.getY(), locationFeast.getZ() - 4);
		Location blockSpawn79 = new Location(locationFeast.getWorld(), locationFeast.getX() - 3, locationFeast.getY(), locationFeast.getZ() - 4);
		Location blockSpawn80 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4, locationFeast.getY(), locationFeast.getZ() - 4);
		Location blockSpawn81 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY(), locationFeast.getZ() + 4);
		Location blockSpawn82 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2 , locationFeast.getY(), locationFeast.getZ() + 4);
		Location blockSpawn83 = new Location(locationFeast.getWorld(), locationFeast.getX() - 3, locationFeast.getY(), locationFeast.getZ() + 4);
		Location blockSpawn84 = new Location(locationFeast.getWorld(), locationFeast.getX() - 4, locationFeast.getY(), locationFeast.getZ() + 4);
		
		Location blockSpawn85 = new Location(locationFeast.getWorld(), locationFeast.getX() + 3, locationFeast.getY(), locationFeast.getZ() + 3);
		Location blockSpawn86 = new Location(locationFeast.getWorld(), locationFeast.getX() + 3 , locationFeast.getY(), locationFeast.getZ() - 3);
		Location blockSpawn87 = new Location(locationFeast.getWorld(), locationFeast.getX() - 3, locationFeast.getY(), locationFeast.getZ() + 3);
		Location blockSpawn88 = new Location(locationFeast.getWorld(), locationFeast.getX() - 3, locationFeast.getY(), locationFeast.getZ() - 3);
		
		Location enchantSpawn = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 1, locationFeast.getZ());
		Location chestSpawn1 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY() + 1, locationFeast.getZ());
		Location chestSpawn2 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY() + 1, locationFeast.getZ());
		Location chestSpawn3 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 1, locationFeast.getZ() + 1);
		Location chestSpawn4 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 1, locationFeast.getZ() - 1);
		
		Block block = blockSpawn.getBlock();
		Block block1 = blockSpawn1.getBlock();
		Block block2 = blockSpawn2.getBlock();
		Block block3 = blockSpawn3.getBlock();
		Block block4 = blockSpawn4.getBlock();
		Block block5 = blockSpawn5.getBlock();
		Block block6 = blockSpawn6.getBlock();
		Block block7 = blockSpawn7.getBlock();
		Block block8 = blockSpawn8.getBlock();
		Block block9 = blockSpawn9.getBlock();
		Block block10 = blockSpawn10.getBlock();
		Block block11 = blockSpawn11.getBlock();
		Block block12 = blockSpawn12.getBlock();
		Block block13 = blockSpawn13.getBlock();
		Block block14 = blockSpawn14.getBlock();
		Block block15 = blockSpawn15.getBlock();
		Block block16 = blockSpawn16.getBlock();
		Block block17 = blockSpawn17.getBlock();
		Block block18 = blockSpawn18.getBlock();
		Block block19 = blockSpawn19.getBlock();
		Block block20 = blockSpawn20.getBlock();
		Block block21 = blockSpawn21.getBlock();
		Block block22 = blockSpawn22.getBlock();
		Block block23 = blockSpawn23.getBlock();
		Block block24 = blockSpawn24.getBlock();
		Block block25 = blockSpawn25.getBlock();
		Block block26 = blockSpawn26.getBlock();
		Block block27 = blockSpawn27.getBlock();
		Block block28 = blockSpawn28.getBlock();
		Block block29 = blockSpawn29.getBlock();
		Block block30 = blockSpawn30.getBlock();
		Block block31 = blockSpawn31.getBlock();
		Block block32 = blockSpawn32.getBlock();
		Block block33 = blockSpawn33.getBlock();
		Block block34 = blockSpawn34.getBlock();
		Block block35 = blockSpawn35.getBlock();
		Block block36 = blockSpawn36.getBlock();
		Block block37 = blockSpawn37.getBlock();
		Block block38 = blockSpawn38.getBlock();
		Block block39 = blockSpawn39.getBlock();
		Block block40 = blockSpawn40.getBlock();
		Block block41 = blockSpawn41.getBlock();
		Block block42 = blockSpawn42.getBlock();
		Block block43 = blockSpawn43.getBlock();
		Block block44 = blockSpawn44.getBlock();
		Block block45 = blockSpawn45.getBlock();
		Block block46 = blockSpawn46.getBlock();
		Block block47 = blockSpawn47.getBlock();
		Block block48 = blockSpawn48.getBlock();
		Block block49 = blockSpawn49.getBlock();
		Block block50 = blockSpawn50.getBlock();
		Block block51 = blockSpawn51.getBlock();
		Block block52 = blockSpawn52.getBlock();
		Block block53 = blockSpawn53.getBlock();
		Block block54 = blockSpawn54.getBlock();
		Block block55 = blockSpawn55.getBlock();
		Block block56 = blockSpawn56.getBlock();
		Block block57 = blockSpawn57.getBlock();
		Block block58 = blockSpawn58.getBlock();
		Block block59 = blockSpawn59.getBlock();
		Block block60 = blockSpawn60.getBlock();
		Block block61 = blockSpawn61.getBlock();
		Block block62 = blockSpawn62.getBlock();
		Block block63 = blockSpawn63.getBlock();
		Block block64 = blockSpawn64.getBlock();
		Block block65 = blockSpawn65.getBlock();
		Block block66 = blockSpawn66.getBlock();
		Block block67 = blockSpawn67.getBlock();
		Block block68 = blockSpawn68.getBlock();
		Block block69 = blockSpawn69.getBlock();
		Block block70 = blockSpawn70.getBlock();
		Block block71 = blockSpawn71.getBlock();
		Block block72 = blockSpawn72.getBlock();
		Block block73 = blockSpawn73.getBlock();
		Block block74 = blockSpawn74.getBlock();
		Block block75 = blockSpawn75.getBlock();
		Block block76 = blockSpawn76.getBlock();
		Block block77 = blockSpawn77.getBlock();
		Block block78 = blockSpawn78.getBlock();
		Block block79 = blockSpawn79.getBlock();
		Block block80 = blockSpawn80.getBlock();
		Block block81 = blockSpawn81.getBlock();
		Block block82 = blockSpawn82.getBlock();
		Block block83 = blockSpawn83.getBlock();
		Block block84 = blockSpawn84.getBlock();
		Block block85 = blockSpawn85.getBlock();
		Block block86 = blockSpawn86.getBlock();
		Block block87 = blockSpawn87.getBlock();
		Block block88 = blockSpawn88.getBlock();
		
		Block enchant = enchantSpawn.getBlock();
		Block chest1 = chestSpawn1.getBlock();
		Block chest2 = chestSpawn2.getBlock();
		Block chest3 = chestSpawn3.getBlock();
		Block chest4 = chestSpawn4.getBlock();
		
		block.setType(Material.getMaterial(98));
		block1.setType(Material.getMaterial(98));
		block2.setType(Material.getMaterial(98));
		block3.setType(Material.getMaterial(98));
		block4.setType(Material.getMaterial(98));
		block5.setType(Material.getMaterial(98));
		block6.setType(Material.getMaterial(98));
		block7.setType(Material.getMaterial(98));
		block8.setType(Material.getMaterial(98));
		block9.setType(Material.getMaterial(98));
		block10.setType(Material.getMaterial(98));
		block11.setType(Material.getMaterial(98));
		block12.setType(Material.getMaterial(98));
		block13.setType(Material.getMaterial(98));
		block14.setType(Material.getMaterial(98));
		block15.setType(Material.getMaterial(98));
		block16.setType(Material.getMaterial(98));
		
		block17.setType(Material.getMaterial(4));
		block18.setType(Material.getMaterial(4));
		block19.setType(Material.getMaterial(4));
		block20.setType(Material.getMaterial(4));
		block21.setType(Material.getMaterial(4));
		block22.setType(Material.getMaterial(4));
		block23.setType(Material.getMaterial(4));
		block24.setType(Material.getMaterial(4));
		block25.setType(Material.getMaterial(4));
		block26.setType(Material.getMaterial(4));
		block27.setType(Material.getMaterial(4));
		block28.setType(Material.getMaterial(4));
		
		block29.setType(Material.getMaterial(44));
		block30.setType(Material.getMaterial(44));
		block31.setType(Material.getMaterial(44));
		block32.setType(Material.getMaterial(44));
		block33.setType(Material.getMaterial(44));
		block34.setType(Material.getMaterial(44));
		block35.setType(Material.getMaterial(44));
		block36.setType(Material.getMaterial(44));
		block37.setType(Material.getMaterial(44));
		block38.setType(Material.getMaterial(44));
		block39.setType(Material.getMaterial(44));
		block40.setType(Material.getMaterial(44));
		block41.setType(Material.getMaterial(44));
		block42.setType(Material.getMaterial(44));
		block43.setType(Material.getMaterial(44));
		block44.setType(Material.getMaterial(44));
		block45.setType(Material.getMaterial(44));
		block46.setType(Material.getMaterial(44));
		block47.setType(Material.getMaterial(44));
		block48.setType(Material.getMaterial(44));
		
		block49.setType(Material.AIR);
		block50.setType(Material.AIR);
		block51.setType(Material.AIR);
		block52.setType(Material.AIR);
		block53.setType(Material.AIR);
		block54.setType(Material.AIR);
		block55.setType(Material.AIR);
		block56.setType(Material.AIR);
		block57.setType(Material.AIR);
		block58.setType(Material.AIR);
		block59.setType(Material.AIR);
		block60.setType(Material.AIR);
		block61.setType(Material.AIR);
		block62.setType(Material.AIR);
		block63.setType(Material.AIR);
		block64.setType(Material.AIR);
		block65.setType(Material.AIR);
		block66.setType(Material.AIR);
		block67.setType(Material.AIR);
		block68.setType(Material.AIR);
		block69.setType(Material.AIR);
		block70.setType(Material.AIR);
		block71.setType(Material.AIR);
		block72.setType(Material.AIR);
		block73.setType(Material.AIR);
		block74.setType(Material.AIR);
		block75.setType(Material.AIR);
		block76.setType(Material.AIR);
		block77.setType(Material.AIR);
		block78.setType(Material.AIR);
		block79.setType(Material.AIR);
		block80.setType(Material.AIR);
		block81.setType(Material.AIR);
		block82.setType(Material.AIR);
		block83.setType(Material.AIR);
		block84.setType(Material.AIR);
		block85.setType(Material.AIR);
		block86.setType(Material.AIR);
		block87.setType(Material.AIR);
		block88.setType(Material.AIR);
		
		enchant.setType(Material.ENCHANTMENT_TABLE);
		chest1.setType(Material.CHEST);
		chest2.setType(Material.CHEST);
		chest3.setType(Material.CHEST);
		chest4.setType(Material.CHEST);
	}
	
	public static void criarArena(Location location, int largura, Material material) {
        List<Location> cuboid = new ArrayList<>();
        cuboid.clear();
    	for (int bX = -largura; bX <= largura; bX++) {
             for (int bZ = -largura; bZ <= largura; bZ++) {
            	 cuboid.add(location.clone().add(bX, 0, bZ));
             }
    	 }
    	
         for (Location loc1 : cuboid)
              loc1.getBlock().setType(material);
	}
	
	public static void spawnFeast(Location location) {
		FrostHG.getManager().createArena(location, 20, Material.GRASS, 1);
		Location location2 = new Location(location.getWorld(), location.getX(), location.getY(), location.getX());
		FrostHG.getManager().createArena(location2, 30, Material.AIR, 120);
	}
	
	public static void spawnMiniFeast(Location location) {
		criarArena(location, 4, Material.STONE);
		criarArena(location, 2, Material.COBBLESTONE);
		Location location2 = new Location(location.getWorld(), location.getX(), location.getY(), location.getX());
		FrostHG.getManager().createArena(location2, 10, Material.AIR, 120);
		Block block = new Location(location2.getWorld(), location2.getX(), location2.getY(), location2.getZ()).getBlock();
		if (block.getType().equals(Material.STONE)) {
			block.setType(Material.AIR);
		}
		addChest();
		preencherBaus();
	}
	
	public static void spawnMinifeast() {
		Random random = new Random();
		int x = MiniFeast.getCoord(400), z = MiniFeast.getCoord(400);
		int reduceX = random.nextInt(50) + 1, reduceZ = random.nextInt(50) + 1;
		int upX = random.nextInt(50) + 1, upZ = random.nextInt(50) + 1;
		MinifeastLocation = new Location(Bukkit.getWorld("world"), x, Bukkit.getWorld("world").getHighestBlockYAt(x, z), z);
		spawnMiniFeast(MinifeastLocation);
		Timer.sendMSG("§6§lPARTIDA §fSurgiu um novo minifeast em (§6" + (MinifeastLocation.getBlockX() - upZ) + "§f, §6" + (MinifeastLocation.getBlockZ() - reduceX) + "§f) e (§6" + (MinifeastLocation.getBlockX() - upX) + "§f, §6" + (MinifeastLocation.getBlockZ() + reduceZ) + "§f)");
	}

	public static void preencherBaus() {
		if (MinifeastLocation == null)
			return;
		
		Location locationFeast = MiniFeast.MinifeastLocation;
		
		Location chestSpawn1 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY() + 1, locationFeast.getZ());
		Location chestSpawn2 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY() + 1, locationFeast.getZ());
		Location chestSpawn3 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 1, locationFeast.getZ() + 1);
		Location chestSpawn4 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 1, locationFeast.getZ() - 1);
		
		Block chest1 = chestSpawn1.getBlock();
		Block chest2 = chestSpawn2.getBlock();
		Block chest3 = chestSpawn3.getBlock();
		Block chest4 = chestSpawn4.getBlock();
		if (chest1.getType().equals(Material.CHEST) || chest2.getType().equals(Material.CHEST) || chest3.getType().equals(Material.CHEST) || chest4.getType().equals(Material.CHEST)) {
			addChestItemsMinifeast((Chest) chest1.getLocation().getBlock().getState());
			addChestItemsMinifeast((Chest) chest2.getLocation().getBlock().getState());
			addChestItemsMinifeast((Chest) chest3.getLocation().getBlock().getState());
			addChestItemsMinifeast((Chest) chest4.getLocation().getBlock().getState());
		}
	}

	public static int getCoord(int range) {
		return new Random().nextBoolean() ? new Random().nextInt(range - 13) : -new Random().nextInt(range + 17);
	}

	public static void addChestItemsMinifeast(Chest chest) {
		for (int i = 0; i < itensMinifeast.size(); i++)
			if (new Random().nextInt(100) < itensMinifeast.get(i).getChance()) {
				int slot = new Random().nextInt(chest.getBlockInventory().getSize());
				chest.getBlockInventory().setItem(slot, itensMinifeast.get(i).getItem());
			}
		chest.update();
	}

	public static void addItensChance() {
		for (String código : FeastConfig.getBausConfig().getStringList("Minifeast.Itens")) {
			String separador[] = código.split(",");
			String material = separador[0].replace("Material:", "");
			int quantidade = Integer.valueOf(separador[1].replace("Quantidade:", ""));
			int durabilidade = Integer.valueOf(separador[2].replace("Durabilidade:", ""));
			int chance = Integer.valueOf(separador[3].replace("Chance:", ""));

			Material m = null;
			try {
				m = Material.getMaterial(material);
			} catch (NullPointerException e) {
				Bukkit.getConsoleSender().sendMessage("§cErro, Material: " + material + " não encontrado.");
			}

			if (m == null)
				continue;

			itensMinifeast.add(new ItemChance(new ItemBuilder().setMaterial(m).setDurabilidade(durabilidade).finalizar(), chance, (quantidade == 1 ? 0 : quantidade)));

		}
	}
}