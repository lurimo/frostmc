package br.com.frostmc.pvp.scoreboard;

import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.spigotmc.ProtocolInjector;
import org.spigotmc.ProtocolInjector.PacketTabHeader;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.LeagueType;
import br.com.frostmc.pvp.FrostPvP;
import br.com.frostmc.pvp.util.admin.AdminManager;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;

public class Scoreboarding {
	
	/**
	 * @shooyutt
	 */
	
	public static String title = "�b�lFROST PVP";
	public static int integer = 0;
	
	public static String next() {
		integer++;
		if (integer == 0) {
			title = "�b�lF�f�lROST PVP";//9
		}
		if (integer == 1) {
			title = "�3�lF�b�lR�f�lOST PVP";//8
		}
		if (integer == 2) {
			title = "�3�lFR�b�lO�f�lST PVP";//7
		}
		if (integer == 3) {
			title = "�3�lFRO�b�lS�f�lT PVP";//6
		}
		if (integer == 4) {
			title = "�3�lFROS�b�lT �f�lPVP";//5
		}
		if (integer == 5) {
			title = "�3�lFROST �b�lP�f�lVP";//4
		}
		if (integer == 6) {
			title = "�3�lFROST P�b�lV�f�lP";//3
		}
		if (integer == 7) {
			title = "�3�lFROST PV�b�lP";//2
		}
		if (integer == 8) {
			title = "�3�lFROST PVP";//1
		}
		
		if (integer == 9) {
			title = "�b�lF�3�lROST PVP";//1
		}
		if (integer == 10) {
			title = "�f�lF�b�lR�3�lOST PVP";//2
		}
		if (integer == 11) {
			title = "�f�lFR�b�lO�3�lST PVP";//3
		}
		if (integer == 12) {
			title = "�f�lFRO�b�lS�3�lT PVP";//4
		}
		if (integer == 13) {
			title = "�f�lFROS�b�lT �3�lPVP";//5
		}
		if (integer == 14) {
			title = "�f�lFROST �b�lP�3�lVP";//6
		}
		if (integer == 15) {
			title = "�f�lFROST P�b�lV�3�lP";//7
		}
		if (integer == 16) {
			title = "�f�lFROST PV�b�lP";//8
		}
		if (integer == 17) {
			title = "�f�lFROST PVP";//9
			integer = 0;
		}
		return title;
	}
	
	public static String getTitle() {
		return title;
	}
	
	public static void setScoreboard(Player player) {
		sendScoreboard(player);
		setTab(player);
	}
	
	public static void setTab(Player player) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		GroupsType groups = BukkitMain.getPermissionManager().getPlayerGroup(player);
		LeagueType league = gamer.getLeagueType();
		int xp = gamer.getXp();
		String clan = "Nenhum";
		sendTab(player, "\n" + Strings.server + "\n" + "\n" + "   �fVoc� est� conectado ao �3�lPVP�f." + "\n", "\n" + "        �fGrupo: " + groups.fullName() + " �8| �fLiga: " + league.fullName() + " �8| �fXP: �9" + xp + " �8| �fClan: �b" + clan + "\n" + "�fTwitter: �3" + Strings.getTwitter() + " �8| �fDiscord: �3" + Strings.getDiscord().replaceAll("https://", "") + " �8| �fSite: �3" + Strings.getWebsite().replaceAll("https://", "").replaceAll("/", "") + "\n" + "\n" + "�fPara voc� comprar �3�lFICHA�f, �9�lDOUBLEXP�f, �a�lVIP �facesse: �3" + Strings.getWebstore().replaceAll("https://", ""));
	}
	
	public static void updateTittle(Player player) {
		if (player == null) {
			return;
		}
		if (player.getScoreboard() == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		if (!FrostPvP.scoreboard.contains(player.getUniqueId())) {
			return;
		}
		Objective obj = player.getScoreboard().getObjective("score");
		obj.setDisplayName(getTitle());
	}
	
	public static void updateKills(Player player, Scoreboard scoreboard) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		team = null;
		if (scoreboard.getTeam("kills") == null) {
			team = scoreboard.registerNewTeam("kills");
		} else {
			team = scoreboard.getTeam("kills");
		}
		team.setSuffix(" �a" + NumberFormat.getInstance().format(gamer.getKills_pvp()));
	}
	
	public static void updateFeastTime(Player player, Scoreboard scoreboard) {
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		team = null;
		if (scoreboard.getTeam("feast") == null) {
			team = scoreboard.registerNewTeam("feast");
		} else {
			team = scoreboard.getTeam("feast");
		}
		team.setSuffix(" �e" + getTimerFormat(FrostPvP.getFeast().tempo));
	}
	
	public static void updateDeaths(Player player, Scoreboard scoreboard) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		team = null;
		if (scoreboard.getTeam("deaths") == null) {
			team = scoreboard.registerNewTeam("deaths");
		} else {
			team = scoreboard.getTeam("deaths");
		}
		team.setSuffix(" �a" + NumberFormat.getInstance().format(gamer.getDeaths_pvp()));
	}
	
	public static void updateStreak(Player player, Scoreboard scoreboard) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		team = null;
		if (scoreboard.getTeam("streak") == null) {
			team = scoreboard.registerNewTeam("streak");
		} else {
			team = scoreboard.getTeam("streak");
		}
		team.setSuffix(" �a" + NumberFormat.getInstance().format(gamer.getKillstreak_pvp()));
	}
	
	public static void updateXp(Player player, Scoreboard scoreboard) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		team = null;
		if (scoreboard.getTeam("xp") == null) {
			team = scoreboard.registerNewTeam("xp");
		} else {
			team = scoreboard.getTeam("xp");
		}
		team.setSuffix(" �9" + NumberFormat.getInstance().format(gamer.getXp()));
	}
	
	public static void updateModes(Player player, Scoreboard scoreboard) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		team = null;
		if (scoreboard.getTeam("moedas") == null) {
			team = scoreboard.registerNewTeam("moedas");
		} else {
			team = scoreboard.getTeam("moedas");
		}
		team.setSuffix(" �a" + NumberFormat.getInstance().format(gamer.getMoedas()));
	}
	
	public static void updateLiga(Player player, Scoreboard scoreboard) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);

		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		if (scoreboard.getTeam("liga") == null) {
			team = scoreboard.registerNewTeam("liga");
		} else {
			team = scoreboard.getTeam("liga");
		}
		team.setSuffix(" " + gamer.getLeagueType().fullName());
	}
	
	public static void updateKit(Player player, Scoreboard scoreboard) {
		if (player == null) {
			return;
		}
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		team = null;
		if (scoreboard.getTeam("kit") == null) {
			team = scoreboard.registerNewTeam("kit");
		} else {
			team = scoreboard.getTeam("kit");
		}
		team.setSuffix(" �3" + KitAPI.getKit(player));
	}
	
	public static void updateLava_easy(Player player, Scoreboard scoreboard) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		team = null;
		if (scoreboard.getTeam("lava_easy") == null) {
			team = scoreboard.registerNewTeam("lava_easy");
		} else {
			team = scoreboard.getTeam("lava_easy");
		}
		team.setSuffix(" �a" + gamer.getLava_easy_pvp());
	}
	
	public static void updateLava_medium(Player player, Scoreboard scoreboard) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		team = null;
		if (scoreboard.getTeam("lava_medium") == null) {
			team = scoreboard.registerNewTeam("lava_medium");
		} else {
			team = scoreboard.getTeam("lava_medium");
		}
		team.setSuffix(" �e" + gamer.getLava_medium_pvp());
	}
	
	public static void updateLava_hard(Player player, Scoreboard scoreboard) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		team = null;
		if (scoreboard.getTeam("lava_hard") == null) {
			team = scoreboard.registerNewTeam("lava_hard");
		} else {
			team = scoreboard.getTeam("lava_hard");
		}
		team.setSuffix(" �6" + gamer.getLava_hard_pvp());
	}
	
	public static void updateLava_extreme(Player player, Scoreboard scoreboard) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		team = null;
		if (scoreboard.getTeam("lava_extreme") == null) {
			team = scoreboard.registerNewTeam("lava_extreme");
		} else {
			team = scoreboard.getTeam("lava_extreme");
		}
		team.setSuffix(" �c" + gamer.getLava_extreme_pvp());
	}
	
	public static String getMod(String name) {
        if (name.length() == 16) {
            String shorts = name.substring(0, name.length() - 4);
            return shorts;
        }
        if (name.length() == 15) {
            String shorts = name.substring(0, name.length() - 3);
            return shorts;
        }
        if (name.length() == 14) {
            String shorts = name.substring(0, name.length() - 2);
            return shorts;
        }
        if (name.length() == 13) {
            String shorts = name.substring(0, name.length() - 1);
            return shorts;
        }
        return name;
    }
	
	public static String getTimerFormat(int timer) {
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
	
	public static void sendScoreboard(Player player) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
	    Objective obj = sb.registerNewObjective("score", "dummy");
	    obj.setDisplayName(getTitle());
	    obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	    
	    Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		
	    if (AdminManager.isAdmin(player)) {
	    	makeScore("�1", null, null, null, sb, 3);
		    makeScore("�f�nMODO", "admin1", " �f�nADMIN", null, sb, 2);
		    makeScore("�0", null, null, null, sb, 1);
			makeScore("�3www.frostmc", "site", "�3.com.br", null, sb, 0);
			player.setScoreboard(sb);
			return;
	    }
	    
		if (WarpsAPI.isInWarp(player, Warps.ARENA)) {
			makeScore("�3", null, null, null, sb, 9);
		    makeScore(" �fKills:", "kills", " �a" + NumberFormat.getInstance().format(gamer.getKills_pvp()), null, sb, 8);
		    makeScore(" �fDeaths:", "deaths", " �a" + NumberFormat.getInstance().format(gamer.getDeaths_pvp()), null, sb, 7);
		    makeScore(" �fKillStreak:", "streak", " �a" + NumberFormat.getInstance().format(gamer.getKillstreak_pvp()), null, sb, 6);
		    makeScore("�2", null, null, null, sb, 5);
		    makeScore(" �fFeast:", "feast", " �e" + getTimerFormat(FrostPvP.getFeast().tempo), null, sb, 4);
		    makeScore("�1", null, null, null, sb, 3);
		    makeScore(" �fKit:", "kit", " �3" + KitAPI.getKit(player), null, sb, 2);
		    makeScore("�0", null, null, null, sb, 1);
			makeScore("�3www.redefrost", "site", "�3.com.br", null, sb, 0);
			player.setScoreboard(sb);
			return;
		}
		
		if (WarpsAPI.isInWarp(player, Warps.LAVACHALLENGE)) {
		    makeScore("�3", null, null, null, sb, 9);
		    makeScore(" �fF�cil:", "lava_easy", " �a" + gamer.getLava_easy_pvp(), null, sb, 8);
		    makeScore(" �fM�dio:", "lava_medium", " �e" + gamer.getLava_medium_pvp(), null, sb, 7);
		    makeScore(" �fD�ficil:", "lava_hard", " �6" + gamer.getLava_hard_pvp(), null, sb, 6);
		    makeScore(" �fExtremo:", "lava_extreme", " �c" + gamer.getLava_extreme_pvp(), null, sb, 5);
		    makeScore("�1", null, null, null, sb, 4);
		    makeScore(" �fLiga:", "liga", " " + gamer.getLeagueType().fullName(), null, sb, 3);
		    makeScore(" �fXP:", "xp", " �9" + NumberFormat.getInstance().format(gamer.getXp()), null, sb, 2);
		    makeScore("�0", null, null, null, sb, 1);
			makeScore("�3www.redefrost", "site", "�3.com.br", null, sb, 0);
			player.setScoreboard(sb);	
			return;
		}
		
		if (WarpsAPI.isInWarp(player, Warps.FPS)) {
			makeScore("�3", null, null, null, sb, 10);
		    makeScore(" �fKills:", "kills", " �a" + NumberFormat.getInstance().format(gamer.getKills_pvp()), null, sb, 9);
		    makeScore(" �fDeaths:", "deaths", " �a" + NumberFormat.getInstance().format(gamer.getDeaths_pvp()), null, sb, 8);
		    makeScore(" �fKillStreak:", "streak", " �a" + NumberFormat.getInstance().format(gamer.getKillstreak_pvp()), null, sb, 7);
		    makeScore("�2", null, null, null, sb, 6);
		    makeScore(" �fWarp:", "warp", " �b" + WarpsAPI.getPlayerWarp(player), null, sb, 5);
		    makeScore("�1", null, null, null, sb, 4);
		    makeScore(" �fLiga:", "liga", " " + gamer.getLeagueType().fullName(), null, sb, 3);
		    makeScore(" �fXP:", "xp", " �9" + NumberFormat.getInstance().format(gamer.getXp()), null, sb, 2);
		    makeScore("�0", null, null, null, sb, 1);
			makeScore("�3www.redefrost", "site", "�3.com.br", null, sb, 0);
			player.setScoreboard(sb);	
			return;
		}
		
		makeScore("�3", null, null, null, sb, 10);
	    makeScore(" �fKills:", "kills", " �a" + NumberFormat.getInstance().format(gamer.getKills_pvp()), null, sb, 9);
	    makeScore(" �fDeaths:", "deaths", " �a" + NumberFormat.getInstance().format(gamer.getDeaths_pvp()), null, sb, 8);
	    makeScore(" �fKillStreak:", "streak", " �a" + NumberFormat.getInstance().format(gamer.getKillstreak_pvp()), null, sb, 7);
	    makeScore("�2", null, null, null, sb, 6);
	    makeScore(" �fKit:", "kit", " �3" + KitAPI.getKit(player), null, sb, 5);
	    makeScore("�1", null, null, null, sb, 4);
	    makeScore(" �fLiga:", "liga", " " + gamer.getLeagueType().fullName(), null, sb, 3);
	    makeScore(" �fXP:", "xp", " �9" + NumberFormat.getInstance().format(gamer.getXp()), null, sb, 2);
	    makeScore("�0", null, null, null, sb, 1);
		makeScore("�3www.redefrost", "site", "�3.com.br", null, sb, 0);
		player.setScoreboard(sb);	
		return;
	}
	
	public static void removeScoreboard(Player player) {
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
	}
	
	private static FastOfflinePlayer getFastOfflinePlayer(String string, String teamname, String suffix, String prefix, Scoreboard sb) {
		FastOfflinePlayer fast = new FastOfflinePlayer(string);
		if (teamname != null) {
			Team team = null;
			if (sb.getTeam(teamname)==null) {
				team = sb.registerNewTeam(teamname);
			} else {
				team = sb.getTeam(teamname);
			}
			if (suffix != null) {
				team.setSuffix(suffix);
			}
			if (prefix != null) {
				team.setPrefix(prefix);
			}
			team.addPlayer(fast);
		}
		return fast;
	}
	
	@SuppressWarnings("deprecation")
	private static void makeScore(String string, String teamname, String suffix, String prefix, Scoreboard sb, int integer) {
		Objective obj = null;
		if (sb.getObjective("score") == null) {
			obj = sb.registerNewObjective("score", "dummy");
		} else {
			obj = sb.getObjective("score");
		}
		obj.getScore(getFastOfflinePlayer(string, teamname, suffix, prefix, sb)).setScore(integer);
	}
	
	public static void sendTab(Player p, String cima, String baixo) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() < 46) {
			return;
		}
		IChatBaseComponent tabcima = ChatSerializer.a("{\"text\": \"" + cima + "\"}");
		IChatBaseComponent tabbaixo = ChatSerializer.a("{\"text\": \"" + baixo + "\"}");
		PacketTabHeader packet = new ProtocolInjector.PacketTabHeader(tabcima, tabbaixo);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

}