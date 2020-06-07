package br.com.frostmc.bungeecord.command.staff;

import java.util.concurrent.TimeUnit;

import br.com.frostmc.bungeecord.BungeeMain;
import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.core.data.mysql.bungeecord.AccountBungee;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.ServersType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class EnviarCommand extends ProxyCommand {

	public EnviarCommand() {
		super("enviar", "send");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (commandSender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer) commandSender;
			AccountBungee playerManager = new AccountBungee(player.getName());
			if (!playerManager.getGroups().hasGroupPermission(GroupsType.ADMIN)) {
				player.sendMessage(Strings.getPermission());
				return;
			}
			if (args.length == 0) {
				player.sendMessage("");
				player.sendMessage("§f/enviar (jogador) (servidor) §8- §7Puxe um jogador para um servidor determinado.");
				player.sendMessage("§f/enviar tpserver (ex-servidor) (servidor) §8- §7Puxar todos os jogadores de um servidor para outro servidor.");
				player.sendMessage("§f/enviar all (servidor) §8- §7Puxar todos os jogadores para um servidor determinado.");
				player.sendMessage("");
				return;
			}
			if (args.length == 1) {
				ProxiedPlayer target = BungeeMain.getInstance().getProxy().getPlayer(args[0]);
				if (target == null) {
					sendOfflinePlayerMessage(player, args[0]);
					return;
				}
				player.sendMessage("");
				player.sendMessage("§f/enviar (jogador) (servidor) §8- §7Puxe um jogador para um servidor determinado.");
				player.sendMessage("§f/enviar tpserver (ex-servidor) (servidor) §8- §7Puxar todos os jogadores de um servidor para outro servidor.");
				player.sendMessage("§f/enviar all (servidor) §8- §7Puxar todos os jogadores para um servidor determinado.");
				player.sendMessage("");
				return;
			}
			if (args.length == 2) {
				ProxiedPlayer target = BungeeMain.getInstance().getProxy().getPlayer(args[0]);
				ServersType server = ServersType.LOGIN;
				try {
					server = ServersType.valueOf(args[1].toUpperCase());
				} catch (Exception exception) {
					sendError(player, "Servidor não foi encontrado.");
					return;
				}
				if (server.equals(ServersType.LOGIN)) {
					player.sendMessage("§cServidor somente para login, você não possui permissão para entrar.");
					return;
				}
				if (args[0].equalsIgnoreCase("all")) {
					for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
						if(!all.getServer().getInfo().equals(BungeeMain.getInstance().getProxy().getServerInfo(server.getIpAddrees()))) {
							all.connect(ProxyServer.getInstance().getServerInfo(server.getIpAddrees()));
							BungeeMain.getInstance().getProxy().getScheduler().schedule(BungeeMain.getPlugin(), new Runnable() {
								public void run() {
									all.sendMessage("§3§lSEND §fVocê foi §a§lTELEPORTADO§f.");
								}
							}, 3, TimeUnit.SECONDS);
						}
					}
					player.sendMessage("§3§lSEND §fVocê teleportou todos os jogadores até o servidor: §a" + server.getIpAddrees());
					return;
				}
				
				if(!target.getServer().getInfo().equals(BungeeMain.getInstance().getProxy().getServerInfo(server.getIpAddrees()))) {
					target.connect(ProxyServer.getInstance().getServerInfo(server.getIpAddrees()));
					BungeeMain.getInstance().getProxy().getScheduler().schedule(BungeeMain.getPlugin(), new Runnable() {
						public void run() {
							target.sendMessage("§3§lSEND §fVocê foi §a§lTELEPORTADO§f.");
						}
					}, 3, TimeUnit.SECONDS);
				}
				player.sendMessage("§3§lSEND §fVocê teleportou o jogador §a" + target.getName() + "§f, até o servidor: §a" + server.getIpAddrees());
				return;
			}
			if (args.length == 3) {
				ServersType server = ServersType.LOGIN;
				try {
					server = ServersType.valueOf(args[2].toUpperCase());
				} catch (Exception exception) {
					player.sendMessage("§6§lSERVER §fO servidor citado não foi encontrado");
					return;
				}
				ServersType exserver = ServersType.LOGIN;
				try {
					exserver = ServersType.valueOf(args[1].toUpperCase());
				} catch (Exception exception) {
					player.sendMessage("§6§lSERVER §fO ex-servidor citado não foi encontrado");
					return;
				}
				if (args[0].equalsIgnoreCase("tpserver")) {
					if (args[1].equalsIgnoreCase(exserver.getIpAddrees())) {
						for(ProxiedPlayer all : ProxyServer.getInstance().getServerInfo(exserver.getIpAddrees()).getPlayers()) {
							if(!all.getServer().getInfo().equals(BungeeMain.getInstance().getProxy().getServerInfo(server.getIpAddrees()))) {
								all.connect(ProxyServer.getInstance().getServerInfo(server.getIpAddrees()));
								BungeeMain.getInstance().getProxy().getScheduler().schedule(BungeeMain.getPlugin(), new Runnable() {
									public void run() {
										all.sendMessage("§3§lSEND §fVocê foi §a§lTELEPORTADO§f.");
									}
								}, 3, TimeUnit.SECONDS);
							}
						}
						player.sendMessage("§3§lSEND §fVocê teleportou todos os jogadores do servidor §c" + exserver + " §faté o servidor: §a" + server.getIpAddrees());
						return;
					}
				}
			}
		} else {
			return;
		}
		
	}

}
