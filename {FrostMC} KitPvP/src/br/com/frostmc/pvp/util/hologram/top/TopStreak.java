package br.com.frostmc.pvp.util.hologram.top;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.pvp.util.hologram.HologramAPI;

public class TopStreak {
	
	public static ArrayList<Integer> streak = new ArrayList<>();
	public static ArrayList<String> nickname = new ArrayList<>();
	
	public void load() {
		try {
			Statement statement = BukkitMain.getCore().getMySQL().getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `players_stats_pvp` ORDER BY `killstreak` DESC;");
			int x = 0;
			while (resultSet.next()) {
				x++;
				if (x > 15) {
					break;
				} else {
					if (resultSet.getInt("killstreak") != 0) {
						if (!nickname.contains(resultSet.getString("nickname"))) {
							nickname.add(resultSet.getString("nickname"));
							streak.add(resultSet.getInt("killstreak"));
						} else {
							nickname.clear();
							streak.clear();
							load();
						}
					}
				}
			}
			resultSet.close();
			statement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public double x = 4996;
	public double y = 94;
	public double z = 5016;

	public void set() {
		load();
		Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
		spawnHologram(loc);
	}

	public void remove() {
		Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
		removeHologram(loc);
	}

	public void spawnHologram(Location loc) {

		Location message = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY(), loc.getZ());
		Location locationOne = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 0.6, loc.getZ());
		Location locationTwo = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 0.9, loc.getZ());
		Location locationTree = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 1.2, loc.getZ());
		Location locationFour = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 1.5, loc.getZ());
		Location locationFive = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 1.8, loc.getZ());
		Location locationSix = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 2.1, loc.getZ());
		Location locationSeven = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 2.4, loc.getZ());
		Location locationEight = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 2.7, loc.getZ());
		Location locationNine = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 3.0, loc.getZ());
		Location locationTen = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 3.3, loc.getZ());
		Location locationEleven = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 3.6, loc.getZ());
		Location locationTwelve = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 3.9, loc.getZ());
		Location locationThirteen = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 4.2, loc.getZ());
		Location locationFourteen = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 4.5, loc.getZ());
		Location locationFifteen = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 4.8, loc.getZ());

		String one = "";
		String two = "";
		String tree = "";
		String four = "";
		String five = "";
		String six = "";
		String seven = "";
		String eight = "";
		String nine = "";
		String ten = "";
		String eleven = "";
		String twelve = "";
		String thirteen = "";
		String fourteen = "";
		String fifteen = "";

		if (nickname.size() == 0) {
			one = "�71. �f-";
			two = "�72. �f-";
			tree = "�73. �f-";
			four = "�74. �f-";
			five = "�75. �f-";
			six = "�76. �f-";
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 1) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. �f-";
			tree = "�73. �f-";
			four = "�74. �f-";
			five = "�75. �f-";
			six = "�76. �f-";
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 2) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. �f-";
			four = "�74. �f-";
			five = "�75. �f-";
			six = "�76. �f-";
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 3) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. �f-";
			five = "�75. �f-";
			six = "�76. �f-";
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 4) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. �f-";
			six = "�76. �f-";
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 5) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. �f-";
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 6) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 7) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 8) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 9) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 10) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. " + nickname.get(9) + " �f| �a" + NumberFormat.getInstance().format(streak.get(9));
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 11) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. " + nickname.get(9) + " �f| �a" + NumberFormat.getInstance().format(streak.get(9));
			eleven = "�711. " + nickname.get(10) + " �f| �a" + NumberFormat.getInstance().format(streak.get(10));
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 12) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. " + nickname.get(9) + " �f| �a" + NumberFormat.getInstance().format(streak.get(9));
			eleven = "�711. " + nickname.get(10) + " �f| �a" + NumberFormat.getInstance().format(streak.get(10));
			twelve = "�712. " + nickname.get(11) + " �f| �a" + NumberFormat.getInstance().format(streak.get(11));
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 13) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. " + nickname.get(9) + " �f| �a" + NumberFormat.getInstance().format(streak.get(9));
			eleven = "�711. " + nickname.get(10) + " �f| �a" + NumberFormat.getInstance().format(streak.get(10));
			twelve = "�712. " + nickname.get(11) + " �f| �a" + NumberFormat.getInstance().format(streak.get(11));
			thirteen = "�713. " + nickname.get(12) + " �f| �a" + NumberFormat.getInstance().format(streak.get(12));
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 14) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. " + nickname.get(9) + " �f| �a" + NumberFormat.getInstance().format(streak.get(9));
			eleven = "�711. " + nickname.get(10) + " �f| �a" + NumberFormat.getInstance().format(streak.get(10));
			twelve = "�712. " + nickname.get(11) + " �f| �a" + NumberFormat.getInstance().format(streak.get(11));
			thirteen = "�713. " + nickname.get(12) + " �f| �a" + NumberFormat.getInstance().format(streak.get(12));
			fourteen = "�714. " + nickname.get(13) + " �f| �a" + NumberFormat.getInstance().format(streak.get(13));
			fifteen = "�715. �f-";
		} else if (nickname.size() == 15) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. " + nickname.get(9) + " �f| �a" + NumberFormat.getInstance().format(streak.get(9));
			eleven = "�711. " + nickname.get(10) + " �f| �a" + NumberFormat.getInstance().format(streak.get(10));
			twelve = "�712. " + nickname.get(11) + " �f| �a" + NumberFormat.getInstance().format(streak.get(11));
			thirteen = "�713. " + nickname.get(12) + " �f| �a" + NumberFormat.getInstance().format(streak.get(12));
			fourteen = "�714. " + nickname.get(13) + " �f| �a" + NumberFormat.getInstance().format(streak.get(13));
			fifteen = "�715. " + nickname.get(14) + " �f| �a" + NumberFormat.getInstance().format(streak.get(14));
		}
		
		HologramAPI.criarHolograma(message, "�e�lTOP STREAK");
		HologramAPI.criarHolograma(locationOne, one);
		HologramAPI.criarHolograma(locationTwo, two);
		HologramAPI.criarHolograma(locationTree, tree);
		HologramAPI.criarHolograma(locationFour, four);
		HologramAPI.criarHolograma(locationFive, five);
		HologramAPI.criarHolograma(locationSix, six);
		HologramAPI.criarHolograma(locationSeven, seven);
		HologramAPI.criarHolograma(locationEight, eight);
		HologramAPI.criarHolograma(locationNine, nine);
		HologramAPI.criarHolograma(locationTen, ten);
		HologramAPI.criarHolograma(locationEleven, eleven);
		HologramAPI.criarHolograma(locationTwelve, twelve);
		HologramAPI.criarHolograma(locationThirteen, thirteen);
		HologramAPI.criarHolograma(locationFourteen, fourteen);
		HologramAPI.criarHolograma(locationFifteen, fifteen);
	}

	public void removeHologram(Location loc) {

		Location message = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY(), loc.getZ());
		Location locationOne = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 0.6, loc.getZ());
		Location locationTwo = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 0.9, loc.getZ());
		Location locationTree = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 1.2, loc.getZ());
		Location locationFour = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 1.5, loc.getZ());
		Location locationFive = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 1.8, loc.getZ());
		Location locationSix = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 2.1, loc.getZ());
		Location locationSeven = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 2.4, loc.getZ());
		Location locationEight = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 2.7, loc.getZ());
		Location locationNine = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 3.0, loc.getZ());
		Location locationTen = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 3.3, loc.getZ());
		Location locationEleven = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 3.6, loc.getZ());
		Location locationTwelve = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 3.9, loc.getZ());
		Location locationThirteen = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 4.2, loc.getZ());
		Location locationFourteen = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 4.5, loc.getZ());
		Location locationFifteen = new Location(Bukkit.getWorld("world"), loc.getX(), loc.getY() - 4.8, loc.getZ());

		String one = "";
		String two = "";
		String tree = "";
		String four = "";
		String five = "";
		String six = "";
		String seven = "";
		String eight = "";
		String nine = "";
		String ten = "";
		String eleven = "";
		String twelve = "";
		String thirteen = "";
		String fourteen = "";
		String fifteen = "";

		if (nickname.size() == 0) {
			one = "�71. �f-";
			two = "�72. �f-";
			tree = "�73. �f-";
			four = "�74. �f-";
			five = "�75. �f-";
			six = "�76. �f-";
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 1) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. �f-";
			tree = "�73. �f-";
			four = "�74. �f-";
			five = "�75. �f-";
			six = "�76. �f-";
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 2) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. �f-";
			four = "�74. �f-";
			five = "�75. �f-";
			six = "�76. �f-";
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 3) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. �f-";
			five = "�75. �f-";
			six = "�76. �f-";
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 4) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. �f-";
			six = "�76. �f-";
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 5) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. �f-";
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 6) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. �f-";
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 7) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. �f-";
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 8) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. �f-";
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 9) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. �f-";
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 10) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. " + nickname.get(9) + " �f| �a" + NumberFormat.getInstance().format(streak.get(9));
			eleven = "�711. �f-";
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 11) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. " + nickname.get(9) + " �f| �a" + NumberFormat.getInstance().format(streak.get(9));
			eleven = "�711. " + nickname.get(10) + " �f| �a" + NumberFormat.getInstance().format(streak.get(10));
			twelve = "�712. �f-";
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 12) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. " + nickname.get(9) + " �f| �a" + NumberFormat.getInstance().format(streak.get(9));
			eleven = "�711. " + nickname.get(10) + " �f| �a" + NumberFormat.getInstance().format(streak.get(10));
			twelve = "�712. " + nickname.get(11) + " �f| �a" + NumberFormat.getInstance().format(streak.get(11));
			thirteen = "�713. �f-";
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 13) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. " + nickname.get(9) + " �f| �a" + NumberFormat.getInstance().format(streak.get(9));
			eleven = "�711. " + nickname.get(10) + " �f| �a" + NumberFormat.getInstance().format(streak.get(10));
			twelve = "�712. " + nickname.get(11) + " �f| �a" + NumberFormat.getInstance().format(streak.get(11));
			thirteen = "�713. " + nickname.get(12) + " �f| �a" + NumberFormat.getInstance().format(streak.get(12));
			fourteen = "�714. �f-";
			fifteen = "�715. �f-";
		} else if (nickname.size() == 14) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. " + nickname.get(9) + " �f| �a" + NumberFormat.getInstance().format(streak.get(9));
			eleven = "�711. " + nickname.get(10) + " �f| �a" + NumberFormat.getInstance().format(streak.get(10));
			twelve = "�712. " + nickname.get(11) + " �f| �a" + NumberFormat.getInstance().format(streak.get(11));
			thirteen = "�713. " + nickname.get(12) + " �f| �a" + NumberFormat.getInstance().format(streak.get(12));
			fourteen = "�714. " + nickname.get(13) + " �f| �a" + NumberFormat.getInstance().format(streak.get(13));
			fifteen = "�715. �f-";
		} else if (nickname.size() == 15) {
			one = "�71. " + nickname.get(0) + " �f| �a" + NumberFormat.getInstance().format(streak.get(0));
			two = "�72. " + nickname.get(1) + " �f| �a" + NumberFormat.getInstance().format(streak.get(1));
			tree = "�73. " + nickname.get(2) + " �f| �a" + NumberFormat.getInstance().format(streak.get(2));
			four = "�74. " + nickname.get(3) + " �f| �a" + NumberFormat.getInstance().format(streak.get(3));
			five = "�75. " + nickname.get(4) + " �f| �a" + NumberFormat.getInstance().format(streak.get(4));
			six = "�76. " + nickname.get(5) + " �f| �a" + NumberFormat.getInstance().format(streak.get(5));
			seven = "�77. " + nickname.get(6) + " �f| �a" + NumberFormat.getInstance().format(streak.get(6));
			eight = "�78. " + nickname.get(7) + " �f| �a" + NumberFormat.getInstance().format(streak.get(7));
			nine = "�79. " + nickname.get(8) + " �f| �a" + NumberFormat.getInstance().format(streak.get(8));
			ten = "�710. " + nickname.get(9) + " �f| �a" + NumberFormat.getInstance().format(streak.get(9));
			eleven = "�711. " + nickname.get(10) + " �f| �a" + NumberFormat.getInstance().format(streak.get(10));
			twelve = "�712. " + nickname.get(11) + " �f| �a" + NumberFormat.getInstance().format(streak.get(11));
			thirteen = "�713. " + nickname.get(12) + " �f| �a" + NumberFormat.getInstance().format(streak.get(12));
			fourteen = "�714. " + nickname.get(13) + " �f| �a" + NumberFormat.getInstance().format(streak.get(13));
			fifteen = "�715. " + nickname.get(14) + " �f| �a" + NumberFormat.getInstance().format(streak.get(14));
		}

		HologramAPI.removerHolograma(message, "�e�lTOP 10 STREAK");
		HologramAPI.removerHolograma(locationOne, one);
		HologramAPI.removerHolograma(locationTwo, two);
		HologramAPI.removerHolograma(locationTree, tree);
		HologramAPI.removerHolograma(locationFour, four);
		HologramAPI.removerHolograma(locationFive, five);
		HologramAPI.removerHolograma(locationSix, six);
		HologramAPI.removerHolograma(locationSeven, seven);
		HologramAPI.removerHolograma(locationEight, eight);
		HologramAPI.removerHolograma(locationNine, nine);
		HologramAPI.removerHolograma(locationTen, ten);
		HologramAPI.removerHolograma(locationEleven, eleven);
		HologramAPI.removerHolograma(locationTwelve, twelve);
		HologramAPI.removerHolograma(locationThirteen, thirteen);
		HologramAPI.removerHolograma(locationFourteen, fourteen);
		HologramAPI.removerHolograma(locationFifteen, fifteen);
	}

}
