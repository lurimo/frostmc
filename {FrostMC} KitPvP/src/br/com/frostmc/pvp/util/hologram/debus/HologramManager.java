package br.com.frostmc.pvp.util.hologram.debus;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.holograms.Hologram;

public class HologramManager {
	
	public static int integer = 0;
	
	@SuppressWarnings("deprecation")
	public void next() {
		integer++;
		if (integer == 30) {
			setHolograms();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!BukkitMain.getPermissions().isModPlus(player)) {
					player.sendMessage("�a�lHOLOGRAMA �fOs hologramas foram atualizados!");
					return;
				}
			}
			integer = 0;
		}
	}

	private static final List<PlayerTop> kills = new ArrayList<>();
	private static final List<PlayerTop> deaths = new ArrayList<>();
	private static final List<PlayerTop> streak = new ArrayList<>();
	private static final List<PlayerTop> lava_easy = new ArrayList<>();
	private static final List<PlayerTop> lava_medium = new ArrayList<>();
	private static final List<PlayerTop> lava_hard = new ArrayList<>();
	private static final List<PlayerTop> lava_extreme = new ArrayList<>();
	public Hologram topKills, topDeaths, topStreak, topLava_easy, topLava_medium, topLava_hard, topLava_extreme;

	public boolean initialize() {
		return updateHolograms();
	}

	public boolean updateHolograms() {
		kills.clear();
		deaths.clear();
		streak.clear();
		lava_easy.clear();
		lava_medium.clear();
		lava_hard.clear();
		lava_extreme.clear();
		loadKills(kills);
		loadDeaths(deaths);
		loadStreak(streak);
		loadLava_easy(lava_easy);
		loadLava_medium(lava_medium);
		loadLava_hard(lava_hard);
		loadLava_extreme(lava_extreme);
		return setHolograms();
	}
	
	public void loadKills(List<PlayerTop> list) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_pvp` ORDER BY `kills` DESC LIMIT 10;");
			int x = 0;
			while (resultSet.next()) {
				x++;
				list.add(new PlayerTop(x, resultSet.getString("nickname"), resultSet.getInt("kills")));
			}
			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void loadDeaths(List<PlayerTop> list) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_pvp` ORDER BY `deaths` DESC LIMIT 10;");
			int x = 0;
			while (resultSet.next()) {
				x++;
				list.add(new PlayerTop(x, resultSet.getString("nickname"), resultSet.getInt("deaths")));
			}
			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void loadStreak(List<PlayerTop> list) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_pvp` ORDER BY `killstreak` DESC LIMIT 10;");
			int x = 0;
			while (resultSet.next()) {
				x++;
				list.add(new PlayerTop(x, resultSet.getString("nickname"), resultSet.getInt("killstreak")));
			}
			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void loadLava_easy(List<PlayerTop> list) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_pvp` ORDER BY `lava_easy` DESC LIMIT 10;");
			int x = 0;
			while (resultSet.next()) {
				x++;
				list.add(new PlayerTop(x, resultSet.getString("nickname"), resultSet.getInt("lava_easy")));
			}
			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void loadLava_medium(List<PlayerTop> list) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_pvp` ORDER BY `lava_medium` DESC LIMIT 10;");
			int x = 0;
			while (resultSet.next()) {
				x++;
				list.add(new PlayerTop(x, resultSet.getString("nickname"), resultSet.getInt("lava_medium")));
			}
			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void loadLava_hard(List<PlayerTop> list) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_pvp` ORDER BY `lava_hard` DESC LIMIT 10;");
			int x = 0;
			while (resultSet.next()) {
				x++;
				list.add(new PlayerTop(x, resultSet.getString("nickname"), resultSet.getInt("lava_hard")));
			}
			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void loadLava_extreme(List<PlayerTop> list) {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_pvp` ORDER BY `lava_extreme` DESC LIMIT 10;");
			int x = 0;
			while (resultSet.next()) {
				x++;
				list.add(new PlayerTop(x, resultSet.getString("nickname"), resultSet.getInt("lava_extreme")));
			}
			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean setHolograms() {
		if (topKills != null) {
			topKills.remove();
			topKills = null;
		}
		if (topDeaths != null) {
			topDeaths.remove();
			topDeaths = null;
		}
		if (topStreak != null) {
			topStreak.remove();
			topStreak = null;
		}
		if (topLava_easy != null) {
			topLava_easy.remove();
			topLava_easy = null;
		}
		if (topLava_medium != null) {
			topLava_medium.remove();
			topLava_medium = null;
		}
		if (topLava_hard != null) {
			topLava_hard.remove();
			topLava_hard = null;
		}
		if (topLava_extreme != null) {
			topLava_extreme.remove();
			topLava_extreme = null;
		}
		
		Location killsLocation = new Location(Bukkit.getServer().getWorld("world"), 5000, 94, 5016);
		Location deathsLocation = new Location(Bukkit.getServer().getWorld("world"), 5004, 94, 5016);
		Location streakLocation = new Location(Bukkit.getServer().getWorld("world"), 5016, 94, 5016);
		//Location lavaEasyLocation = new Location(Bukkit.getServer().getWorld("world"), -5.5, 174.5, 12.5);
		//Location lavaMediumLocation = new Location(Bukkit.getServer().getWorld("world"), -5.5, 174.5, 12.5);
		//Location lavaHardLocation = new Location(Bukkit.getServer().getWorld("world"), -5.5, 174.5, 12.5);
		//Location lavaExtremeLocation = new Location(Bukkit.getServer().getWorld("world"), -5.5, 174.5, 12.5);
		
		topKills = new Hologram("�a�lTOP 10 KILLS", killsLocation, true);

		int i = 1;

		for (PlayerTop scoreTop : kills) {
			if (scoreTop != null) {
				String color = i == 1 ? "�a" : i == 2 ? "�e" : i == 3 ? "�c" : "�7";
				String name = (color.equals("�7") ? "�f" : color) + scoreTop.getName();

				String line = color + scoreTop.getId() + "� " + name + " �f| �a" + NumberFormat.getInstance().format(scoreTop.getTop());

				topKills.addLine(line);
				i++;
			}
		}
	
		topDeaths = new Hologram("�c�lTOP 10 DEATHS", deathsLocation, true);

		i = 1;

		for (PlayerTop scoreTop : deaths) {
			if (scoreTop != null) {
				String color = i == 1 ? "�a" : i == 2 ? "�e" : i == 3 ? "�c" : "�7";
				String name = (color.equals("�7") ? "�f" : color) + scoreTop.getName();

				String line = color + scoreTop.getId() + "� " + name + " �f| �a" + NumberFormat.getInstance().format(scoreTop.getTop());

				topDeaths.addLine(line);
				i++;
			}
		}
		
		topStreak = new Hologram("�e�lTOP 10 STREAK", streakLocation, true);

		i = 1;

		for (PlayerTop scoreTop : streak) {
			if (scoreTop != null) {
				String color = i == 1 ? "�a" : i == 2 ? "�e" : i == 3 ? "�c" : "�7";
				String name = (color.equals("�7") ? "�f" : color) + scoreTop.getName();

				String line = color + scoreTop.getId() + "� " + name + " �f| �a" + NumberFormat.getInstance().format(scoreTop.getTop());

				topStreak.addLine(line);
				i++;
			}
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			topKills.hide(player);
			topDeaths.hide(player);
			topStreak.hide(player);
			topLava_easy.hide(player);
			topLava_medium.hide(player);
			topLava_hard.hide(player);
			topLava_extreme.hide(player);
			
			topKills.show(player);
			topDeaths.show(player);
			topStreak.show(player);
			topLava_easy.show(player);
			topLava_medium.show(player);
			topLava_hard.show(player);
			topLava_extreme.show(player);

		}

		/////////////////////////////////
		return true;

	}
}