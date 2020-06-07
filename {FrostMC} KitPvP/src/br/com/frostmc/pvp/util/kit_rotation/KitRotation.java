package br.com.frostmc.pvp.util.kit_rotation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.pvp.FrostPvP;
import br.com.frostmc.pvp.util.hability.Kits;

public class KitRotation {
	
	public HashMap<String, ArrayList<String>> freeKits = new HashMap<String, ArrayList<String>>();
	public List<String> free_kits = new ArrayList<String>();
	
	public KitRotation() {
		
		free_kits.add("PVP");
		free_kits.add("SURPRISE");
		
		List<Kits> kits = new ArrayList<Kits>();
		for (Kits kit : Kits.values()) {
			if (!kit.equals(Kits.NENHUM) && !kit.equals(Kits.PVP)) {
				kits.add(kit);
			}
		}
		for (int i = 0;i < 4; i++) {
			Kits kit = kits.get(new Random().nextInt(kits.size()));
			free_kits.add(kit.name());
			kits.remove(kit);
		}
		
	}
	
	public void loadFreeKits() {
		new BukkitRunnable() {
			public void run() {
				freeKits.clear();
				ArrayList<String> group = new ArrayList<String>();
				group.add("membro");
				try {
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					for (String ranks : group) {
						preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `kits_rotation` WHERE rank = '" + ranks + "';");
						resultSet = preparedStatement.executeQuery();
						ArrayList<String> kits = new ArrayList<String>();
						while (resultSet.next()) {
							kits.add(resultSet.getString("kit").toUpperCase());
						}
						freeKits.put(ranks, kits);
						System.out.println("[KIT-ROTATION] >> " + kits);
					}
					resultSet.close();
					preparedStatement.close();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		}.runTaskAsynchronously(FrostPvP.getPlugin());
	}
	
	public void apply() {
		Statement statement = BukkitMain.getCore().getMySQL().getStatement();
		try {
			statement.executeUpdate("TRUNCATE kits_rotation");
			for (String kits : free_kits) {
				statement.executeUpdate("INSERT INTO `kits_rotation` (`rank`, `kit`) VALUES ('" + "membro" + "', '" + kits + "');");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		loadFreeKits();
	}

}
