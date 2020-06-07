package br.com.frostmc.bungeecord.command.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import br.com.frostmc.bungeecord.BungeeMain;
import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.information.Date;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ReportCommand extends ProxyCommand {

	public ReportCommand() {
		super("report");
	}

	public enum ReportModes {
		OFF, ON, LIST, DROP;
	}
	
	public static HashMap<UUID, ReportModes> reportModes = new HashMap<>();
	public ArrayList<UUID> cooldown = new ArrayList<>();
	
	public static ArrayList<String> reports = new ArrayList<>();
	public static HashMap<String, String> reportVitima = new HashMap<>();
	public static HashMap<String, String> reportReason = new HashMap<>();
	public static HashMap<String, String> reportDate = new HashMap<>();
	public static HashMap<String, String> reportServer = new HashMap<>();
	public static HashMap<String, String> reportStaffer = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
			ProxiedPlayer player = getPlayer(commandSender);
			if (args.length == 0) {
				player.sendMessage("");
				player.sendMessage("§8[§c§lERRO§8] §fVocê utilizou o comando de maneira incorreta. Utilize: /report (player) (reason)");
				player.sendMessage("");
				return;
			}
			if (args.length == 1) {
				if (!hasGroupPermission(player, GroupsType.YOUTUBERPLUS)) {
					player.sendMessage("");
					player.sendMessage("§8[§c§lERRO§8] §fVocê utilizou o comando de maneira incorreta. Utilize: /report (player) (reason)");
					player.sendMessage("");
					return;
				} else {
					ReportModes modes = ReportModes.OFF;
					try {
						modes = ReportModes.valueOf(args[0].toUpperCase());
					} catch (Exception exception) {
						player.sendMessage("");
						player.sendMessage("§8[§e§lREPORTES§8]");
						player.sendMessage("");
						player.sendMessage(" §cSTAFF");
						player.sendMessage("   §f/report <on/off/list> <verificar/remover>");
						player.sendMessage("");
						player.sendMessage(" §aJOGADORES");
						player.sendMessage("   §f/report <player> <reason>");
						player.sendMessage("");
						return ;
					}
					if (modes.equals(ReportModes.ON)) {
						if (reportModes.get(player.getUniqueId()).equals(ReportModes.ON)) {
							player.sendMessage("§8[§e§lREPORTES§8] §fVocê já está com os reportes habilitados.");
							return;
						}
						reportModes.put(player.getUniqueId(), ReportModes.ON);
						player.sendMessage("§8[§a§lREPORTES§8] §fVocê ativou os reportes com sucesso.");
						return;
					} else if (modes.equals(ReportModes.OFF)) {
						if (reportModes.get(player.getUniqueId()).equals(ReportModes.OFF)) {
							player.sendMessage("§8[§c§lREPORTES§8] §fVocê já está com os reportes desabilitados.");
							return;
						}
						reportModes.put(player.getUniqueId(), ReportModes.OFF);
						player.sendMessage("§8[§c§lREPORTES§8] §fVocê desativou os reportes com sucesso.");
						return;
					} else if (modes.equals(ReportModes.LIST)) {
						if (reports.size() == 0) {
							player.sendMessage("");
							player.sendMessage("§cNenhum jogador foi reportado recentemente.");
							player.sendMessage("");
							return;
						}
						player.sendMessage(" ");
						player.sendMessage("§e§lREPORTS [" + reports.size() + "]");
						player.sendMessage(" ");
						for (String report : reports) {
							TextComponent list = new TextComponent("§b" + report + " foi reportado. [" + (reportStaffer.get(report) == null ? "Não verificado" : "Verificado") + "]");
							list.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("" + "\n" + "§3§lREPORT §8- §f" + report + "\n" + "\n" + "§fReportado por: §4" + reportVitima.get(report) + "\n" + "§fMotivo: §c" + reportReason.get(report) + "\n" + "§fData: §a" + reportDate.get(report) + "\n" + "§fServidor: §3" + reportServer.get(report) + "\n" + "\n" + "§a§nClique para teleportar." + "\n").create()));
							list.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + report + " verificar"));
							player.sendMessage(list);
						}
						player.sendMessage(" ");
						return;
					} else if (modes.equals(ReportModes.DROP)) {
						if (!hasGroupPermission(player, GroupsType.ADMIN))
							return;
						for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
							reports.remove(players.getName());
							reportVitima.remove(players.getName());
							reportReason.remove(players.getName());
							reportDate.remove(players.getName());
							reportServer.remove(players.getName());
							reportStaffer.put(player.getName(), players.getName());
						}
						player.sendMessage("§cTodos os reports foram removidos.");
						return;
					} else {
						player.sendMessage("");
						player.sendMessage("§8[§c§lERRO§8] §fVocê utilizou o comando de maneira incorreta. Utilize: /report (player) (reason)");
						player.sendMessage("");
						return;
					}
				}
			}
			
			ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
			if (target == null) {
				sendOfflinePlayerMessage(player, args[0]);
				return;
			}
			
			if (args[1].equalsIgnoreCase("verificar")) {
				if (!reports.contains(target.getName())) {
					player.sendMessage("§cEste jogador não foi reportado recentemente.");
					return;
				}
				if (reportStaffer.containsKey(target.getName())) {
					if (!player.getName().equals(reportStaffer.get(target.getName()))) {
						player.sendMessage("§cNão é possível remover o jogador, já que o mesmo está sendo verificado por §b" + reportStaffer.get(target.getName()));
						return;
					}
				}
				for (ProxiedPlayer staffers : ProxyServer.getInstance().getPlayers()) {
					if (hasGroupPermission(staffers, GroupsType.TRIAL) && reportModes.get(staffers.getUniqueId()).equals(ReportModes.ON)) {
						staffers.sendMessage("§fO staff §a" + player.getName() + " §firá verificar o suspeito §c" + target.getName() + "§f.");
					}
				}
				reportStaffer.put(player.getName(), target.getName());
				ServerInfo server = BungeeMain.getInstance().getProxy().getServerInfo(target.getServer().getInfo().getName());
				if(!player.getServer().getInfo().equals(server)) {
					player.connect(server);
					return;
				} else {
					player.chat("/tp " + target.getName());
				}
				player.sendMessage("§aVocê irá verificar o suspeito §f" + target.getName() + "§a.");
				TextComponent message = new TextComponent("§fPara remover o jogador §a§l[CLIQUE AQUI]");
				message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new ComponentBuilder("§aClique para remover o jogador.").create()));
				message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + target.getName() + " remover"));
				player.sendMessage(message);
				return;
			} else if (args[1].equalsIgnoreCase("remover")) {
				if (!reports.contains(target.getName())) {
					player.sendMessage("§cEste jogador não foi reportado recentemente.");
					return;
				}
				if (reportStaffer.containsKey(target.getName())) {
					if (!player.getName().equals(reportStaffer.get(target.getName()))) {
						player.sendMessage("§cNão é possível remover o jogador, já que o mesmo está sendo verificado por §b" + reportStaffer.get(target.getName()));
						return;
					}
				} else {
					player.sendMessage("§cEsse jogador não está sendo verificado.");
					return;
				}
				ProxiedPlayer vitima = ProxyServer.getInstance().getPlayer(reportVitima.get(target.getName()));
				vitima.sendMessage("§fO jogador §c" + target.getName() + " §festá limpo e foi liberado.");
				reports.remove(target.getName());
				reportVitima.remove(target.getName());
				reportReason.remove(target.getName());
				reportDate.remove(target.getName());
				reportServer.remove(target.getName());
				reportStaffer.remove(target.getName());
				player.sendMessage("§fO jogador §a" + target.getName() + " §ffoi removido dos reports.");
				return;
			}
			
			String reason = getArgs(args, 1);
			String motiveSave = getArgs(args, 1).toLowerCase();

			if (motiveSave.contains("staff") || motiveSave.contains("lixo") || motiveSave.contains("cade") || motiveSave.contains("buceta") || motiveSave.contains("desgraçado") || motiveSave.contains("caralho") || motiveSave.contains("PVP") || motiveSave.contains("mds") || motiveSave.contains("olha") || motiveSave.contains("muito") || motiveSave.contains("'-") || motiveSave.contains("mush") || motiveSave.contains("ajuda") || motiveSave.contains("blatant") || motiveSave.contains("fudido") || motiveSave.contains("tela") || motiveSave.contains("evento") || motiveSave.contains("'-") || motiveSave.contains("bosta") || motiveSave.contains("fudidao") || motiveSave.contains("telar")) {
				commandSender.sendMessage("§cApenas reporte o necessário, sem ofensas contra nossa equipe ou algo do gênero.");
				return;
			}
			
			if (reports.contains(target.getName())) {
				player.sendMessage("§aEste jogador já foi reportado recentemente, algum membro de nossa equipe irá o vericá-lo assim que possível..");
				return;
			}
			
			if (reportStaffer.containsKey(target.getName())) {
				player.sendMessage("§aUm membro de nossa equipe já está olhando o suspeito.");
				return;
			}
			
			if(cooldown.contains(player.getUniqueId())) {
				player.sendMessage("§cAguarde para enviar outra denúncia.");
				return;
			} else {
				cooldown.add(player.getUniqueId());
			}
			
			player.sendMessage("");
			player.sendMessage("§cVocê reportou " + target.getName() + " com sucesso. ");
			player.sendMessage("");
			player.sendMessage("§fAssim que possível, algum membro de nossa equipe irá verificá-lo.");
			player.sendMessage("§fAgradecemos sua §cdenúncia§f, faremos o possível para resolver.");
			reports.add(target.getName());
			reportVitima.put(target.getName(), player.getName());
			reportReason.put(target.getName(), reason);
			reportDate.put(target.getName(), Date.getMonth());
			reportServer.put(target.getName(), target.getServer().getInfo().getName().toLowerCase());
			
			for (ProxiedPlayer staffers : ProxyServer.getInstance().getPlayers()) {
				if (hasGroupPermission(staffers, GroupsType.TRIAL) && reportModes.get(staffers.getUniqueId()).equals(ReportModes.ON)) {
					staffers.sendMessage("");
					TextComponent teleport = new TextComponent("§fO jogador §a" + target.getName() + " §ffoi reportado.");
					teleport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("" + "\n" + "§3§lREPORT §8- §f" + target.getName() + "\n" + "\n" + "§fReportado por: §4" + reportVitima.get(target.getName()) + "\n" + "§fMotivo: §c" + reportReason.get(target.getName()) + "\n" + "§fData: §a" + reportDate.get(target.getName()) + "\n" + "§fServidor: §3" + reportServer.get(target.getName()) + "\n" + "\n" + "§a§nClique para teleportar." + "\n").create()));
					teleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + target.getName() + " verificar"));
                    staffers.sendMessage(teleport);
					staffers.sendMessage("");
				}
			}
			
			BungeeCord.getInstance().getScheduler().schedule(BungeeMain.getInstance(), new Runnable() {
					public void run() {
						cooldown.remove(player.getUniqueId());
					}
			}, 30, TimeUnit.SECONDS);
			
		} else {
			return;
		}
	}

}

