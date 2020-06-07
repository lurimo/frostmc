package br.com.frostmc.bungeecord.command.staff;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.bungeecord.listener.PlayerLoginListener;
import br.com.frostmc.core.data.mysql.bungeecord.AccountBungee;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AvisoCommand extends ProxyCommand {

	public AvisoCommand() {
		super("aviso", "", "broadcast", "bc");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
			ProxiedPlayer player = getPlayer(commandSender);
			AccountBungee account = new AccountBungee(player.getName());
			if (!hasGroupPermission(player, GroupsType.YOUTUBERPLUS)) {
				player.sendMessage(Strings.getPermission());
				return;
			}
			if (args.length == 0) {
				if (hasGroupPermission(player, GroupsType.ADMIN)) {
					player.sendMessage("");
					player.sendMessage("�aUtilize o comando: /aviso (server/all) (message)");
					player.sendMessage("");
				}
				player.sendMessage("");
				player.sendMessage("�f/aviso rec (server/all) �8- �7Aviso para grava��o.");
				player.sendMessage("�f/aviso live (url) �8- �7Aviso para streamar.");
				player.sendMessage("�f/aviso jogando (server/all) �8- �7Aviso para jogar.");
				player.sendMessage("");
				return;
			}
			if (args.length == 1) {
				if (hasGroupPermission(player, GroupsType.ADMIN)) {
					player.sendMessage("�aUtilize o comando: /aviso (server/all) (message)");
				}
				player.sendMessage("");
				player.sendMessage("�f/aviso rec (server/all) �8- �7Aviso para grava��o.");
				player.sendMessage("�f/aviso live (url) �8- �7Aviso para streamar.");
				player.sendMessage("�f/aviso jogando (server/all) �8- �7Aviso para jogar.");
				player.sendMessage("");
				return;
			}
			if (args.length == 2) {
				if (!account.getGroups().hasGroupPermission(GroupsType.YOUTUBERPLUS)) {
					return;
				}
				if (args[0].equalsIgnoreCase("rec")) {
					if (args[1].equalsIgnoreCase("server")) {
						for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
							GroupsType playerGroup = PlayerLoginListener.checkGroup.get(player.getName());
							if(all.getServer().getInfo().equals(player.getServer().getInfo())) {
								all.sendMessage(" ");
								all.sendMessage("�e�lAN�NCIO �fOl� jogadores, " + playerGroup.fullName() + player.getName() + " �fir� �b�lGRAVAR �fnesse servidor.");
								all.sendMessage(" ");
							}
						}
						return;
					} else if (args[1].equalsIgnoreCase("all")) {
						GroupsType playerGroup = PlayerLoginListener.checkGroup.get(player.getName());
						BungeeCord.getInstance().broadcast("�e�lAN�NCIO �fOl� jogadores, " + playerGroup.fullName() + player.getName() + " �fir� �b�lGRAVAR �fno servidor �e�l" + player.getServer().getInfo().getName().toUpperCase() + " �3�l/connect " + player.getServer().getInfo().getName().toLowerCase().replace("-", "_"));
						return;
					}
				} else if (args[0].equalsIgnoreCase("live")) {
					GroupsType playerGroup = PlayerLoginListener.checkGroup.get(player.getName());
					BungeeCord.getInstance().broadcast("�e�lAN�NCIO �fOl� jogadores, " + playerGroup.fullName() + player.getName() + " �festa �b�lAO VIVO �fem �3" + args[1]);
					return;
				} else if (args[0].equalsIgnoreCase("playing") || args[0].equalsIgnoreCase("jogando")) {
					if (args[1].equalsIgnoreCase("server")) {
						for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
							if(all.getServer().getInfo().equals(player.getServer().getInfo())) {
								GroupsType playerGroup = PlayerLoginListener.checkGroup.get(player.getName());
								all.sendMessage("�e�lAN�NCIO �fOl� jogadores, " + playerGroup.fullName() + player.getName() + " �fir� �b�lJOGAR �fnesse servidor.");
								return;
							}
						}
					} else if (args[1].equalsIgnoreCase("all")) {
						GroupsType playerGroup = PlayerLoginListener.checkGroup.get(player.getName());
						BungeeCord.getInstance().broadcast("�e�lAN�NCIO �fOl� jogadores, " + playerGroup.fullName() + player.getName() + " �fir� �b�lJOGAR �fno servidor �e�l" + player.getServer().getInfo().getName().toUpperCase() + " �3�l/connect " + player.getServer().getInfo().getName().toLowerCase().replace("-", "_"));
						return;
					}
				}
			}
			if (!account.getGroups().hasGroupPermission(GroupsType.ADMIN)) {
				return;
			}
			String message = "";
			for(Integer integer = Integer.valueOf(1); integer < args.length; integer++) {
				message = message + args[integer] + " ";
			}
			if (args[0].equalsIgnoreCase("server")) {
				for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
					if(all.getServer().getInfo().equals(player.getServer().getInfo())) {
						all.sendMessage("�e�lAN�NCIO �f" + ChatColor.translateAlternateColorCodes('&', message));
					}
				}
				return;
			} else if (args[0].equalsIgnoreCase("all")) {
				BungeeCord.getInstance().broadcast("�e�lAN�NCIO �f" + ChatColor.translateAlternateColorCodes('&', message));
				return;
			}
		} else {
			CommandSender player = commandSender;
			if (args.length == 0) {
				player.sendMessage("�e�lAN�NCIO �fUtilize o comando: /aviso (message)");
				return;
			}
			String message = "";
			for(Integer integer = Integer.valueOf(0); integer < args.length; integer++) {
				message = message + args[integer] + " ";
			}
			BungeeCord.getInstance().broadcast("�e�lAN�NCIO �f" + ChatColor.translateAlternateColorCodes('&', message));
		}
	}

}
