package br.com.frostmc.hardcoregames.timer.feast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import br.com.frostmc.hardcoregames.FrostHG;

public class FeastConfig {

	public static ArrayList<String> mensagens = new ArrayList<>();
	static boolean trySave = false, saveBaus;
	static FileConfiguration baus;
	static File fbaus;
	
	public static void loadConfigs() {
		fbaus = new File(FrostHG.getInstance().getDataFolder(), "baus.yml");
		if (!fbaus.exists()) {
		     try {
		    	 fbaus.createNewFile();
		     } catch (IOException e) {
		    	 e.printStackTrace();
		      }
		}
		baus = YamlConfiguration.loadConfiguration(fbaus);
	}
	
	public static void loadConfig() {
		loadConfigs();
		
		if (!getBausConfig().contains("Feast.Itens")) {
			getBausConfig().set("Feast.Itens", Arrays.asList(
			"Material:DIAMOND_HELMET,Quantidade:1,Durabilidade:0,Chance:20",
			"Material:DIAMOND_CHESTPLATE,Quantidade:1,Durabilidade:0,Chance:19",
			"Material:DIAMOND_LEGGINGS,Quantidade:1,Durabilidade:0,Chance:19",
			"Material:DIAMOND_BOOTS,Quantidade:1,Durabilidade:0,Chance:20",
			"Material:DIAMOND_SWORD,Quantidade:1,Durabilidade:0,Chance:19",
			"Material:DIAMOND_AXE,Quantidade:1,Durabilidade:0,Chance:19",
			"Material:DIAMOND_PICKAXE,Quantidade:1,Durabilidade:0,Chance:19",
			"Material:FLINT_AND_STEEL,Quantidade:1,Durabilidade:0,Chance:26",
			"Material:WATER_BUCKET,Quantidade:1,Durabilidade:0,Chance:37",
			"Material:LAVA_BUCKET,Quantidade:1,Durabilidade:0,Chance:35",
			"Material:BOW,Quantidade:1,Durabilidade:0,Chance:40",
			
			"Material:POTION,Quantidade:1,Durabilidade:16418,Chance:32",
			"Material:POTION,Quantidade:1,Durabilidade:16424,Chance:32",
			"Material:POTION,Quantidade:1,Durabilidade:16420,Chance:33",
			"Material:POTION,Quantidade:1,Durabilidade:16428,Chance:31",
			"Material:POTION,Quantidade:1,Durabilidade:16426,Chance:30",
			"Material:POTION,Quantidade:1,Durabilidade:16417,Chance:32",
			"Material:POTION,Quantidade:1,Durabilidade:16419,Chance:33",
			"Material:POTION,Quantidade:1,Durabilidade:16421,Chance:34",
			
			"Material:COOKED_BEEF,Quantidade:30,Durabilidade:0,Chance:40",
			"Material:ENDER_PEARL,Quantidade:12,Durabilidade:0,Chance:31",
			"Material:ENDER_PEARL,Quantidade:5,Durabilidade:0,Chance:21",
			"Material:GOLDEN_APPLE,Quantidade:15,Durabilidade:0,Chance:30",
			"Material:EXP_BOTTLE,Quantidade:32,Durabilidade:0,Chance:42",
			"Material:WEB,Quantidade:28,Durabilidade:0,Chance:13",
			"Material:TNT,Quantidade:28,Durabilidade:0,Chance:39",
			"Material:DIAMOND,Quantidade:12,Durabilidade:0,Chance:13",
			"Material:ARROW,Quantidade:31,Durabilidade:0,Chance:31"
			));
			
			saveBaus = true;
		}
		
		if (!getBausConfig().contains("Minifeast.Itens")) {
			
			getBausConfig().set("Minifeast.Itens", Arrays.asList(
			"Material:IRON_SWORD,Quantidade:1,Durabilidade:0,Chance:28",
			"Material:EXP_BOTTLE,Quantidade:15,Durabilidade:0,Chance:45",
			"Material:IRON_PICKAXE,Quantidade:1,Durabilidade:0,Chance:28",
			"Material:POTION,Quantidade:1,Durabilidade:16393,Chance:30",
			"Material:POTION,Quantidade:1,Durabilidade:16417,Chance:30",
			"Material:INK_SACK,Quantidade:35,Durabilidade:3,Chance:39",
			"Material:COOKED_BEEF,Quantidade:35,Durabilidade:0,Chance:39",
			"Material:ENDER_PEARL,Quantidade:10,Durabilidade:0,Chance:45",
			"Material:MONSTER_EGG,Quantidade:7,Durabilidade:95,Chance:34",
			"Material:BONE,Quantidade:12,Durabilidade:0,Chance:35",
			"Material:WEB,Quantidade:10,Durabilidade:0,Chance:25",
			"Material:TNT,Quantidade:10,Durabilidade:0,Chance:25",
			"Material:LAVA_BUCKET,Quantidade:1,Durabilidade:0,Chance:10"
			));
			
			saveBaus = true;
		}
		
		
		if (saveBaus) {
			saveBausConfig();
		}
		
		Feast.addItensChance();
	}
	
	public static FileConfiguration getBausConfig() {
		return baus;
	}
	
	public static void saveBausConfig() {
		try {
			baus.save(fbaus);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
