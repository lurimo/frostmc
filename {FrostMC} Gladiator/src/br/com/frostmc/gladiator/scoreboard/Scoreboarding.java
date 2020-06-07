package br.com.frostmc.gladiator.scoreboard;

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
import br.com.frostmc.gladiator.FrostGladiator;
import br.com.frostmc.gladiator.battle1v1.GladiatorManager;
import br.com.frostmc.gladiator.util.admin.AdminManager;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;

public class Scoreboarding {
	
	/**
	 * @shooyutt
	 */
	
	public static String title = "§b§lGLADIATOR";
	public static int integer = 0;
	
	public static String next() {
		integer++;
		if (integer == 0) {
			title = "§b§lG§f§lLADIATOR";//9
		}
		if (integer == 1) {
			title = "§3§lG§b§lL§f§lADIATOR";//8
		}
		if (integer == 2) {
			title = "§3§lGL§b§lA§f§lDIATOR";//7
		}
		if (integer == 3) {
			title = "§3§lGLA§b§lD§f§lIATOR";//6
		}
		if (integer == 4) {
			title = "§3§lGLAD§b§lI§f§lATOR";//5
		}
		if (integer == 5) {
			title = "§3§lGLADIA§b§lT§f§lOR";//4
		}
		if (integer == 6) {
			title = "§3§lGLADIAT§b§lO§f§lR";//3
		}
		if (integer == 7) {
			title = "§3§lGLADIATO§b§lR";//2
		}
		if (integer == 8) {
			title = "§3§lGLADIATOR";//2
		}
		
		if (integer == 9) {
			title = "§b§lG§3§lLADIATOR";//1
		}
		if (integer == 10) {
			title = "§f§lG§b§lL§3§lADIATOR";//2
		}
		if (integer == 11) {
			title = "§f§lGL§b§lA§3§lDIATOR";//3
		}
		if (integer == 12) {
			title = "§f§lGLA§b§lD§3§lIATOR";//4
		}
		if (integer == 13) {
			title = "§f§lGLAD§b§lI§3§lATOR";//5
		}
		if (integer == 14) {
			title = "§f§lGLADIA§b§lT§3§lOR";//6
		}
		if (integer == 15) {
			title = "§f§lGLADIAT§b§lO§3§lR";//7
		}
		if (integer == 16) {
			title = "§f§lGLADIATO§b§lR";//8
		}
		if (integer == 17) {
			title = "§f§lGLADIATOR";//9
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
		sendTab(player, "\n" + Strings.server + "\n" + "\n" + "   §fVocê está conectado ao §3§lGLADIATOR§f." + "\n", "\n" + "        §fGrupo: " + groups.fullName() + " §8| §fLiga: " + league.fullName() + " §8| §fXP: §9" + xp + " §8| §fClan: §b" + clan + "\n" + "§fTwitter: §3" + Strings.getTwitter() + " §8| §fDiscord: §3" + Strings.getDiscord().replaceAll("https://", "") + " §8| §fSite: §3" + Strings.getWebsite().replaceAll("https://", "").replaceAll("/", "") + "\n" + "\n" + "§fPara você comprar §3§lFICHA§f, §9§lDOUBLEXP§f, §a§lVIP §facesse: §3" + Strings.getWebstore().replaceAll("https://", ""));
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
		if (!FrostGladiator.scoreboard.contains(player.getUniqueId())) {
			return;
		}
		Objective obj = player.getScoreboard().getObjective("score");
		obj.setDisplayName(getTitle());
	}
	
	public static void updateOpponent(Player player) {
		if (player == null) {
			return;
		}
		if (player.getScoreboard() == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		team = null;
		if (player.getScoreboard().getTeam("opponent") == null) {
			team = player.getScoreboard().registerNewTeam("opponent");
		} else {
			team = player.getScoreboard().getTeam("opponent");
		}
		String opponente = GladiatorManager.opponent.get(player.getUniqueId());
	    if (GladiatorManager.opponent.get(player.getUniqueId()) == null) {
    		team.setSuffix("§3Ninguém");
	    } else {
	    	team.setSuffix(" §3" + getMod(opponente));
	    }
	}
	
	public static void updateStats(Player player) {
		 Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		if (player == null) {
			return;
		}
		if (player.getScoreboard() == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		if (player.getScoreboard().getTeam("victory") == null) {
			team = player.getScoreboard().registerNewTeam("victory");
		} else {
			team = player.getScoreboard().getTeam("victory");
		}
    	team.setSuffix(" §a" + NumberFormat.getInstance().format(gamer.getVictory_gladiator()));
    	
    	if (player.getScoreboard().getTeam("defeat") == null) {
			team = player.getScoreboard().registerNewTeam("defeat");
		} else {
			team = player.getScoreboard().getTeam("defeat");
		}
    	team.setSuffix(" §a" + NumberFormat.getInstance().format(gamer.getDefeat_gladiator()));
    	
    	if (player.getScoreboard().getTeam("streak") == null) {
			team = player.getScoreboard().registerNewTeam("streak");
		} else {
			team = player.getScoreboard().getTeam("streak");
		}
    	team.setSuffix(" §a" + NumberFormat.getInstance().format(gamer.getWinstreak_gladiator()));
	}
	
	public static void updateContager(Player player) {
		if (player == null) {
			return;
		}
		if (player.getScoreboard() == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		Team team = null;
		
		if (player.getScoreboard().getTeam("contager") == null) {
			team = player.getScoreboard().registerNewTeam("contager");
		} else {
			team = player.getScoreboard().getTeam("contager");
		}
		team.setSuffix(" §b" + givetTimer(GladiatorManager.getTime(player)));
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
	
	public static void sendScoreboard(Player player) {
		if (FrostGladiator.scoreboard.contains(player.getUniqueId())) {
			
			Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		    Objective obj = sb.registerNewObjective("score", "dummy");
		    obj.setDisplayName(getTitle());
		    obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		    
		    Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		    
		    if (AdminManager.isAdmin(player)) {
		    	makeScore("§1", null, null, null, sb, 3);
			    makeScore("§f§nMODO", "admin1", " §f§nADMIN", null, sb, 2);
			    makeScore("§0", null, null, null, sb, 1);
				makeScore("§3www.frostmc", "site", "§3.com.br", null, sb, 0);
				player.setScoreboard(sb);
				return;
		    }
		    if (GladiatorManager.opponent.containsKey(player.getUniqueId())) {
		    	makeScore("§3", null, null, null, sb, 6);
	        	makeScore("§fTempo:", "contager", " §b" + givetTimer(GladiatorManager.getTime(player)), null, sb, 5);
			    makeScore("§2", null, null, null, sb, 4);
			    makeScore(" §fBatalhando", "batalhando", " §fcontra:", null, sb, 3);
			    
			    String opponente = GladiatorManager.opponent.get(player.getUniqueId());
			    if (GladiatorManager.opponent.get(player.getUniqueId()) == null) {
			    	makeScore("§1", "opponent", " §3Ninguém", null, sb, 2);
			    } else {
			    	makeScore("§1", "opponent", " §3" + getMod(opponente), null, sb, 2);
			    }
			    
			    makeScore("§0", null, null, null, sb, 1);
				makeScore("§3www.frostmc", "site", "§3.com.br", null, sb, 0);
				player.setScoreboard(sb);
				return;
		    } else {
		    	makeScore("§3", null, null, null, sb, 8);
			    makeScore(" §fVitórias:", "victory", " §a" + NumberFormat.getInstance().format(gamer.getVictory_gladiator()), null, sb, 7);
			    makeScore(" §fDerrotas:", "defeat", " §a" + NumberFormat.getInstance().format(gamer.getDefeat_gladiator()), null, sb, 6);
			    makeScore(" §fWinStreak:", "streak", " §a" + NumberFormat.getInstance().format(gamer.getWinstreak_gladiator()), null, sb, 5);
			    makeScore("§2", null, null, null, sb, 4);
			    makeScore(" §fBatalhando", "batalhando", " §fcontra:", null, sb, 3);
			    
			    String opponente = GladiatorManager.opponent.get(player.getUniqueId());
			    if (GladiatorManager.opponent.get(player.getUniqueId()) == null) {
			    	makeScore("§1", "opponent", " §3Ninguém", null, sb, 2);
			    } else {
			    	makeScore("§1", "opponent", " §3" + getMod(opponente), null, sb, 2);
			    }
			    
			    makeScore("§0", null, null, null, sb, 1);
				makeScore("§3www.frostmc", "site", "§3.com.br", null, sb, 0);
				player.setScoreboard(sb);
				return;
		    }
		}
	}
	
	public static String givetTimer(Integer i) {
		int minutes = i.intValue() / 60;
		int seconds = i.intValue() % 60;
		String disMinu = (minutes < 10 ? "" : "") + minutes; 
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = disMinu + "m " + disSec + "s";
		return formattedTime;
	}
	
	public static void removeScoreboard(Player player) {
		player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
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
