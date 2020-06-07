package br.com.frostmc.bungeecord.command.staff;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.core.data.mysql.bungeecord.server.Event;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.EventsType;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.RunningType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class EventCommand extends ProxyCommand {

	public EventCommand() {
		super("event", "player join to event", "evento");
	}
	
	public enum Awards {
		NONE, VIP;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
			ProxiedPlayer player = getPlayer(commandSender);
			Event event = new Event();
			if (!hasGroupPermission(player, GroupsType.YOUTUBERPLUS)) {
				player.sendMessage(Strings.getPermission());
				return;
			}
			if (args.length == 0) {
				player.sendMessage("");
				player.sendMessage("§f/evento (arena/minifrost/gladiator/mlg/staffvsplayer) (none/vip) §8- §7Inicie um tipo de evento.");
				player.sendMessage("§f/evento custom (nome) (premiação) §8- §7Inicie um tipo de evento a seu gosto.");
				player.sendMessage("");
				return;
			}
			
			if (event.exists()) {
				player.sendMessage("§c§lEVENT §fJa temos um evento em andamento.");
				return;
			}
			
			if (!args[0].equalsIgnoreCase("custom")) {
				
				EventsType eventsType = EventsType.ARENA;
				try {
					eventsType = EventsType.valueOf(args[0].toUpperCase());
				} catch (Exception exception) {
					player.sendMessage("§c§lERRO §fEsse tipo de evento não foi encontrado.");
					return;
				}
				
				Awards awards = Awards.NONE;
				try {
					awards = Awards.valueOf(args[1].toUpperCase());
				} catch (Exception exception) {
					player.sendMessage("§c§lERRO §fEsse tipo de premiação não foi encontrado.");
					return;
				}
				
				event.create(player.getName(), eventsType.name(), RunningType.ACTIVED);
				for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
					players.sendMessage("");
					players.sendMessage("§3§l- §fEvento: §a§l" + eventsType.name().toUpperCase());
					players.sendMessage("§3§l- §fPremiação: §e§l" + awards.name().toUpperCase());
					TextComponent message = new TextComponent("§3§l- §fServidor: §b§l/join");
					message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aClique para entrar no evento!").create()));
					message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/connect evento"));
					players.sendMessage(message);
					players.sendMessage("");
				}
				player.sendMessage("§a§lEVENT §fVocê iniciou o evento §a§l" + args[0] + " §fcom premiação §e§l" + awards.name().toUpperCase());
				return;
			} else {
				Awards awards = Awards.NONE;
				
				try {
					awards = Awards.valueOf(args[2].toUpperCase());
				} catch (Exception exception) {
					player.sendMessage("§c§lERRO §fEsse tipo de premiação não foi encontrado.");
					return;
				}
				event.create(player.getName(), args[1].toUpperCase(), RunningType.ACTIVED);
				for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
					players.sendMessage("");
					players.sendMessage("§3§l- §fEvento: §a§l" + args[1].toUpperCase());
					players.sendMessage("§3§l- §fPremiação: §e§l" + awards.name().toUpperCase());
					TextComponent message = new TextComponent("§3§l- §fServidor: §b§l/join");
					message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aClique para entrar no evento!").create()));
					message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/connect evento"));
					players.sendMessage(message);
					players.sendMessage("");
				}
				player.sendMessage("§a§lEVENT §fVocê iniciou o evento §a§l" + args[0].toUpperCase() + " §fcom premiação §e§l" + awards.name().toUpperCase());
				return;
			}
		} else {
			return;
		}
	}

}
