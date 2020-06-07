package br.com.frostmc.bungeecord.command.staff;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.bungeecord.listener.PlayerLoginListener;
import br.com.frostmc.core.data.mysql.bungeecord.AccountBungee;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MaintenanceCommand extends ProxyCommand {

	public MaintenanceCommand() {
		super("manutencao", "", "maintenance");
	}

	public static boolean maintenance = true;
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
			ProxiedPlayer player = getPlayer(commandSender);
			AccountBungee account = new AccountBungee(player.getName());
			if (!account.getGroups().hasGroupPermission(GroupsType.ADMIN)) {
				player.sendMessage(Strings.getPermission());
				return;
			}
			if (args.length == 0) {
				player.sendMessage("");
				player.sendMessage("�f/maintenance (on/off) �8- �7ativar/desativar a manuten��o.");
				player.sendMessage("�f/maintenance (add/remove) (player) �8- �7adicionar/remover um jogador da manuten��o.");
				player.sendMessage("");
				return;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("on")) {
					if (maintenance) {
						player.sendMessage("�aO servidor j� est� em modo manuten��o.");
					} else {
						maintenance = true;
						for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
							AccountBungee accountAll = new AccountBungee(all.getName());
							if (!accountAll.getGroups().hasGroupPermission(GroupsType.TRIAL)) {
								all.disconnect("�c�lERRO" + "\n" + "\n" + "�fO servidor est� no modo �4�lMANUTEN�AO�f," + "\n" + "�fEstamos corrigindo bugs indesejaveis e atualizando." + "\n" + "�fEm breve n�s iremos voltar, fiquem atentos no discord." + "\n" + "\n" + Strings.getDiscord());
							}
						}
						BungeeCord.getInstance().broadcast("");
						BungeeCord.getInstance().broadcast("�cO servidor entrou em modo de MANUTEN��O.");
						BungeeCord.getInstance().broadcast("");
						player.sendMessage("�aVoc� ativou o modo manuten��o no servidor.");
						return;
					}
				} else if (args[0].equalsIgnoreCase("off")) {
					if (!maintenance) {
						player.sendMessage("�cO servidor j� est� sem o modo manuten��o.");
					} else {
						maintenance = false;
						BungeeCord.getInstance().broadcast("");
						BungeeCord.getInstance().broadcast("�aO servidor saiu do modo de MANUTEN��O.");
						BungeeCord.getInstance().broadcast("");
						player.sendMessage("�cVoc� desativou o modo manuten��o do servidor.");
						return;
					}
				}
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("add")) {
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if (target == null) {
						if (PlayerLoginListener.checkWhitList.contains(args[1])) {
							player.sendMessage("�aO jogador �f" + args[1] + "�a j� se encontra em nossa WHITELIST.");
							return;
						}
						PlayerLoginListener.checkWhitList.add(args[1]);
						player.sendMessage("�aO jogador �f" + args[1] + "�a foi adicionado a WHITELIST com sucesso.");
						return;
					}
					if (PlayerLoginListener.checkWhitList.contains(target.getName())) {
						player.sendMessage("�aO jogador �f" + target.getName() + "�a j� se encontra em nossa WHITELIST.");
						return;
					}
					PlayerLoginListener.checkWhitList.add(target.getName());
					player.sendMessage("�aO jogador �f" + target.getName() + "�a foi adicionado a WHITELIST com sucesso.");
					return;
				} else if (args[0].equalsIgnoreCase("remove")) {
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if (target == null) {
						if (!PlayerLoginListener.checkWhitList.contains(args[1])) {
							player.sendMessage("�cO jogador " + args[1] + " j� n�o se encontra em nossa WHITELIST.");
							return;
						}
						PlayerLoginListener.checkWhitList.remove(args[1]);
						player.sendMessage("�aVoc� removeu " + args[1] + " da WHITELIST do servidor.");
						return;
					}
					if (!PlayerLoginListener.checkWhitList.contains(target.getName())) {
						player.sendMessage("�cO jogador \" + args[1] + \" j� n�o se encontra em nossa WHITELIST.");
						return;
					}
					PlayerLoginListener.checkWhitList.remove(target.getName());
					player.sendMessage("�aVoc� removeu " + target.getName() + " da WHITELIST do servidor.");
					return;
				}
			}
		} else {
			CommandSender player = commandSender;
			if (args.length == 0) {
				player.sendMessage("");
				player.sendMessage("�f/maintenance (on/off) �8- �7ativar/desativar a manuten��o.");
				player.sendMessage("�f/maintenance (add/remove) (player) �8- �7adicionar/remover um jogador da manuten��o.");
				player.sendMessage("");
				return;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("on")) {
					if (maintenance) {
						player.sendMessage("�aO servidor j� est� em modo manuten��o.");
					} else {
						maintenance = true;
						for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
							AccountBungee accountAll = new AccountBungee(all.getName());
							if (!accountAll.getGroups().hasGroupPermission(GroupsType.TRIAL)) {
								all.disconnect("�c�lERRO" + "\n" + "\n" + "�fO servidor est� no modo �4�lMANUTEN�AO�f," + "\n" + "�fEstamos corrigindo bugs indesejaveis e atualizando." + "\n" + "�fEm breve n�s iremos voltar, fiquem atentos no discord." + "\n" + "\n" + Strings.getDiscord());
							}
						}
						BungeeCord.getInstance().broadcast("");
						BungeeCord.getInstance().broadcast("�cO servidor entrou em modo de MANUTEN��O.");
						BungeeCord.getInstance().broadcast("");
						player.sendMessage("�aVoc� ativou o modo manuten��o no servidor.");
						return;
					}
				} else if (args[0].equalsIgnoreCase("off")) {
					if (!maintenance) {
						player.sendMessage("�cO servidor j� est� sem o modo manuten��o.");
					} else {
						maintenance = false;
						BungeeCord.getInstance().broadcast("");
						BungeeCord.getInstance().broadcast("�aO servidor saiu do modo de MANUTEN��O.");
						BungeeCord.getInstance().broadcast("");
						player.sendMessage("�cVoc� desativou o modo manuten��o do servidor.");
						return;
					}
				}
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("add")) {
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if (target == null) {
						if (PlayerLoginListener.checkWhitList.contains(args[1])) {
							player.sendMessage("�aO jogador �f" + args[1] + "�a j� se encontra em nossa WHITELIST.");
							return;
						}
						PlayerLoginListener.checkWhitList.add(args[1]);
						player.sendMessage("�aO jogador �f" + args[1] + "�a foi adicionado a WHITELIST com sucesso.");
						return;
					}
					if (PlayerLoginListener.checkWhitList.contains(target.getName())) {
						player.sendMessage("�aO jogador �f" + target.getName() + "�a j� se encontra em nossa WHITELIST.");
						return;
					}
					PlayerLoginListener.checkWhitList.add(target.getName());
					player.sendMessage("�aO jogador �f" + target.getName() + "�a foi adicionado a WHITELIST com sucesso.");
					return;
				} else if (args[0].equalsIgnoreCase("remove")) {
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if (target == null) {
						if (!PlayerLoginListener.checkWhitList.contains(args[1])) {
							player.sendMessage("�cO jogador " + args[1] + " j� n�o se encontra em nossa WHITELIST.");
							return;
						}
						PlayerLoginListener.checkWhitList.remove(args[1]);
						player.sendMessage("�aVoc� removeu " + args[1] + " da WHITELIST do servidor.");
						return;
					}
					if (!PlayerLoginListener.checkWhitList.contains(target.getName())) {
						player.sendMessage("�cO jogador \" + args[1] + \" j� n�o se encontra em nossa WHITELIST.");
						return;
					}
					PlayerLoginListener.checkWhitList.remove(target.getName());
					player.sendMessage("�aVoc� removeu " + target.getName() + " da WHITELIST do servidor.");
					return;
				}
			}
		}
	}

}
