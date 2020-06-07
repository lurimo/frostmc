package br.com.frostmc.hardcoregames.scoreboard;

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
import br.com.frostmc.common.util.ServerOptions;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.LeagueType;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.timer.Timer;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.admin.AdminManager;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;

public class Scoreboarding {
	
	public static String title = (!ServerOptions.EVENTO.isActive() ? "§f§lHARDCORE GAMES" : "§f§lEVENTO");
	public static int integer = 0;
	
	public static String next() {
		integer++;
		if (!ServerOptions.EVENTO.isActive()) {
			if (integer == 1) {
				title = "§b§lH§f§lARDCORE GAMES";
			}
			if (integer == 2) {
				title = "§3§lH§b§lA§f§lRDCORE GAMES";
			}
			if (integer == 3) {
				title = "§3§lHA§b§lR§f§lDCORE GAMES";
			}
			if (integer == 4) {
				title = "§3§lHAR§b§lD§f§lCORE GAMES";
			}
			if (integer == 5) {
				title = "§3§lHARD§b§lC§f§lORE GAMES";
			}
			if (integer == 6) {
				title = "§3§lHARDC§b§lO§f§lRE GAMES";
			}
			if (integer == 7) {
				title = "§3§lHARDCO§b§lR§f§lE GAMES";
			}
			if (integer == 8) {
				title = "§3§lHARDCOR§b§lE §f§lGAMES";
			}
			if (integer == 9) {
				title = "§3§lHARDCORE §b§lG§f§lAMES";
			}
			if (integer == 10) {
				title = "§3§lHARDCORE G§b§lA§f§lMES";
			}
			if (integer == 11) {
				title = "§3§lHARDCORE GA§b§lM§f§lES";
			}
			if (integer == 12) {
				title = "§3§lHARDCORE GAM§b§lE§f§lS";
			}
			if (integer == 13) {
				title = "§3§lHARDCORE GAME§b§lS";
			}
			if (integer == 14) {
				title = "§3§lHARDCORE GAMES";
			}
			
			if (integer == 15) {
				title = "§b§lH§3§lARDCORE GAMES";
			}
			if (integer == 16) {
				title = "§f§lH§b§lA§3§lRDCORE GAMES";
			}
			if (integer == 17) {
				title = "§f§lHA§b§lR§3§lDCORE GAMES";
			}
			if (integer == 18) {
				title = "§f§lHAR§b§lD§3§lCORE GAMES";
			}
			if (integer == 19) {
				title = "§f§lHARD§b§lC§3§lORE GAMES";
			}
			if (integer == 20) {
				title = "§f§lHARDC§b§lO§3§lRE GAMES";
			}
			if (integer == 21) {
				title = "§f§lHARDCO§b§lR§3§lE GAMES";
			}
			if (integer == 22) {
				title = "§f§lHARDCOR§b§lE §3§lGAMES";
			}
			if (integer == 23) {
				title = "§f§lHARDCORE §b§lG§3§lAMES";
			}
			if (integer == 24) {
				title = "§f§lHARDCORE G§b§lA§3§lMES";
			}
			if (integer == 25) {
				title = "§f§lHARDCORE GA§b§lM§3§lES";
			}
			if (integer == 26) {
				title = "§f§lHARDCORE GAM§b§lE§3§lS";
			}
			if (integer == 27) {
				title = "§f§lHARDCORE GAME§b§lS";
				integer = 0;
			}
		} else {
			if (integer == 0) {
				title = "§b§lE§f§lVENTO";
			}
			if (integer == 1) {
				title = "§3§lE§b§lV§f§lENTO";
			}
			if (integer == 2) {
				title = "§3§lEV§b§lE§f§lNTO";
			}
			if (integer == 3) {
				title = "§3§lEVE§b§lN§f§lTO";
			}
			if (integer == 4) {
				title = "§3§lEVEN§b§lT§f§lO";
			}
			if (integer == 5) {
				title = "§3§lEVENT§b§lO";
			}
			if (integer == 6) {
				title = "§3§lEVENTO";
			}
			
			if (integer == 7) {
				title = "§b§lE§3§lVENTO";
			}
			if (integer == 8) {
				title = "§f§lE§b§lV§3§lENTO";
			}
			if (integer == 9) {
				title = "§f§lEV§b§lE§3§lNTO";
			}
			if (integer == 10) {
				title = "§f§lEVE§b§lN§3§lTO";
			}
			if (integer == 11) {
				title = "§f§lEVEN§b§lT§3§lO";
			}
			if (integer == 12) {
				title = "§f§lEVENT§b§lO";
			}
			if (integer == 13) {
				title = "§f§lEVENTO";
				integer = 0;
			}
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
		sendTab(player, "\n" + Strings.server + "\n" + "\n" + "   §fVocê está conectado ao §3§lHG-" + FrostHG.getManager().getSala() + "§f." + "\n", "\n" + "        §fGrupo: " + groups.fullName() + "§8| §fLiga: " + league.fullName() + " §8| §fXP: §9" + xp + " §8| §fClan: §b" + clan + "\n" + "§fTwitter: §3" + Strings.getTwitter() + " §8| §fDiscord: §3" + Strings.getDiscord().replaceAll("https://", "") + " §8| §fSite: §3" + Strings.getWebsite().replaceAll("https://", "").replaceAll("/", "") + "\n" + "\n" + "§fPara você comprar §3§lFICHA§f, §9§lDOUBLEXP§f, §a§lVIP §facesse: §3" + Strings.getWebstore().replaceAll("https://", ""));
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
		if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
			return;
		}
		Objective obj = player.getScoreboard().getObjective("score");
		obj.setDisplayName(getTitle());
	}
	
	public static void updateTimer(Player player, Scoreboard scoreboard) {
		if (player == null) {
			return;
		}
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId()))
			return;
		Team team = null;
		if (scoreboard.getTeam("time") == null) {
			team = scoreboard.registerNewTeam("time");
		} else {
			team = scoreboard.getTeam("time");
		}
		switch (FrostHG.state) {
		case INICIANDO:
			team.setSuffix(" §b" + FrostHG.getManager().getTimerFormat(Timer.tempo));
			break;
		case INVENCIVEL:
			team.setSuffix(" por: §b" + FrostHG.getManager().getTimerFormat(Timer.tempo));
			break;
		case JOGO:
			team.setSuffix(" jogo: §b" + FrostHG.getManager().getTimerFormat(Timer.tempo));
			break;
		default:
			team.setSuffix(" §b" + FrostHG.getManager().getTimerFormat(Timer.tempo));
			break;
		}
	}
	
	public static void updateKills(Player player, Scoreboard scoreboard) {
		if (player == null) {
			return;
		}
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId()))
			return;
		Team team = null;
		if (scoreboard.getTeam("kills") == null) {
			team = scoreboard.registerNewTeam("kills");
		} else {
			team = scoreboard.getTeam("kills");
		}
		team.setSuffix(" §3" + FrostHG.getManager().kills.get(player.getUniqueId()));
	}
	
	public static void updateLiga(Player player, Scoreboard scoreboard) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		if (player == null) {
			return;
		}
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId()))
			return;
		Team team = null;
		if (scoreboard.getTeam("liga") == null) {
			team = scoreboard.registerNewTeam("liga");
		} else {
			team = scoreboard.getTeam("liga");
		}
		team.setSuffix(" " + gamer.getLeagueType().fullName());
	}
	
	public static void updateJogadores(Player player, Scoreboard scoreboard) {
		if (player == null) {
			return;
		}
		if (scoreboard == null) {
			return;
		}
		if (!player.isOnline()) {
			return;
		}
		if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId()))
			return;
		Team team = null;
		if (scoreboard.getTeam("jogadores") == null) {
			team = scoreboard.registerNewTeam("jogadores");
		} else {
			team = scoreboard.getTeam("jogadores");
		}
		team.setSuffix(" §b" + FrostHG.getManager().getJogadores().size() + "/" + Bukkit.getMaxPlayers());
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
		if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId()))
			return;
		Team team = null;
		if (scoreboard.getTeam("kit") == null) {
			team = scoreboard.registerNewTeam("kit");
		} else {
			team = scoreboard.getTeam("kit");
		}
		team.setSuffix(" §3" + FrostHG.getManager().getKitAPI().getKit(player));
	}
	
	public static void sendScoreboard(Player player) {
		if (FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
			Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
			LeagueType leagueType = gamer.getLeagueType();
			Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		    Objective obj = sb.registerNewObjective("score", "dummy");
		    obj.setDisplayName(getTitle());
		    obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			
		    if (FrostHG.state==State.INICIANDO) {
		    	makeScore("§3", null, null, null, sb, 9);
				makeScore("§fIniciando em:", "time", " §b" + FrostHG.getManager().getTimerFormat(Timer.tempo), null, sb, 8);
				makeScore("§fJogadores:", "jogadores", " §b" + FrostHG.getManager().getJogadores().size() + "/" + Bukkit.getMaxPlayers(), null, sb, 7);
				makeScore("§2", null, null, null, sb, 6);
				makeScore("§fSala:", "sala", " §b#" + FrostHG.getManager().getSala(), null, sb, 5);
				makeScore("§1", null, null, null, sb, 4);
				makeScore("§fLiga:", "liga", " " + leagueType.fullName(), null, sb, 3);
				makeScore("§fSeu Kit:", "kit", " §3" + FrostHG.getManager().getKitAPI().getKit(player), null, sb, 2);
				makeScore("§0", null, null, null, sb, 1);
				makeScore("§3www.frostmc", "site", "§3.com.br", null, sb, 0);
				player.setScoreboard(sb);
		    	return;
		    } else if (FrostHG.state==State.INVENCIVEL) {
		    	makeScore("§3", null, null, null, sb, 9);
				makeScore("§fInvencível", "time", " por: §b" + FrostHG.getManager().getTimerFormat(Timer.tempo), null, sb, 8);
				makeScore("§fJogadores:", "jogadores", " §b" + FrostHG.getManager().getJogadores().size() + "/" + Bukkit.getMaxPlayers(), null, sb, 7);
				makeScore("§2", null, null, null, sb, 6);
				makeScore("§fSala:", "sala", " §b#" + FrostHG.getManager().getSala(), null, sb, 5);
				makeScore("§1", null, null, null, sb, 4);
				makeScore("§fLiga:", "liga", " " + leagueType.fullName(), null, sb, 3);
				makeScore("§fSeu Kit:", "kit", " §3" + FrostHG.getManager().getKitAPI().getKit(player), null, sb, 2);
				makeScore("§0", null, null, null, sb, 1);
				makeScore("§3www.frostmc", "site", "§3.com.br", null, sb, 0);
				player.setScoreboard(sb);
		    	return;
		    } else if (FrostHG.state==State.JOGO) {
		    	if (FrostHG.getManager().getEspectadores().contains(player.getUniqueId()) || AdminManager.isAdmin(player)) {
			    	makeScore("§4", null, null, null, sb, 10);
					makeScore("§fTempo de", "time", " jogo: §b" + FrostHG.getManager().getTimerFormat(Timer.tempo), null, sb, 9);
					makeScore("§fJogadores:", "jogadores", " §b" + FrostHG.getManager().getJogadores().size() + "/" + Bukkit.getMaxPlayers(), null, sb, 8);
					makeScore("§3", null, null, null, sb, 7);
					makeScore("§fSala:", "sala", " §b#" + FrostHG.getManager().getSala(), null, sb, 6);
					makeScore("§2", null, null, null, sb, 5);
					makeScore("§7MODO ", "specmessage1", "§7ESPECTADOR", null, sb, 4);
					makeScore("§1", null, null, null, sb, 3);
					makeScore("§fLiga:", "liga", " " + leagueType.fullName(), null, sb, 2);
					makeScore("§0", null, null, null, sb, 1);
					makeScore("§3www.frostmc", "site", "§3.com.br", null, sb, 0);
					player.setScoreboard(sb);
			    	return;
			    }
		    	makeScore("§3", null, null, null, sb, 10);
				makeScore("§fTempo de", "time", " jogo: §b" + FrostHG.getManager().getTimerFormat(Timer.tempo), null, sb, 9);
				makeScore("§fJogadores:", "jogadores", " §b" + FrostHG.getManager().getJogadores().size() + "/" + Bukkit.getMaxPlayers(), null, sb, 8);
				makeScore("§fKills:", "kills", " §3" + FrostHG.getManager().kills.get(player.getUniqueId()), null, sb, 7);
				makeScore("§2", null, null, null, sb, 6);
				makeScore("§fSala:", "sala", " §b#" + FrostHG.getManager().getSala(), null, sb, 5);
				makeScore("§1", null, null, null, sb, 4);
				makeScore("§fLiga:", "liga", " " + leagueType.fullName(), null, sb, 3);
				makeScore("§fSeu Kit:", "kit", " §3" + FrostHG.getManager().getKitAPI().getKit(player), null, sb, 2);
				makeScore("§0", null, null, null, sb, 1);
				makeScore("§3www.frostmc", "site", "§3.com.br", null, sb, 0);
				player.setScoreboard(sb);
		    	return;
		    } else if (FrostHG.state==State.FINAL) {
		    	makeScore("§3", null, null, null, sb, 9);
				makeScore("§fJogadores:", "jogadores", " §b" + FrostHG.getManager().getJogadores().size() + "/" + Bukkit.getMaxPlayers(), null, sb, 8);
				makeScore("§fKills:", "kills", " §3" + FrostHG.getManager().kills.get(player.getUniqueId()), null, sb, 7);
				makeScore("§2", null, null, null, sb, 6);
				makeScore("§fSala:", "sala", " §b#" + FrostHG.getManager().getSala(), null, sb, 5);
				makeScore("§1", null, null, null, sb, 4);
				makeScore("§fLiga:", "liga", " " + leagueType.fullName(), null, sb, 3);
				makeScore("§fSeu Kit:", "kit", " §3" + FrostHG.getManager().getKitAPI().getKit(player), null, sb, 2);
				makeScore("§0", null, null, null, sb, 1);
				makeScore("§3www.frostmc", "site", "§3.com.br", null, sb, 0);
				player.setScoreboard(sb);
		    	return;
		    }
		}
	}
	
	public static void removeScoreboard(Player player) {
		player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
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
