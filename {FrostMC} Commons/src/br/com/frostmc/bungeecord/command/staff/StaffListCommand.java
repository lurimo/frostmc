package br.com.frostmc.bungeecord.command.staff;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.bungeecord.listener.PlayerLoginListener;
import br.com.frostmc.core.data.mysql.bungeecord.AccountBungee;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class StaffListCommand extends ProxyCommand {

	public StaffListCommand() {
		super("stafflist", "", "staffs");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
			ProxiedPlayer player = getPlayer(commandSender);
			AccountBungee account = new AccountBungee(player.getName());
			if (!account.getGroups().hasGroupPermission(GroupsType.TRIAL)) {
				player.sendMessage(Strings.getPermission());
				return;
			}
			if (args.length == 0) {
				player.sendMessage("");
				int i = 0;
				for (ProxiedPlayer staffer : BungeeCord.getInstance().getPlayers()) {
					GroupsType groupsType = PlayerLoginListener.checkGroup.get(staffer.getName());
					if (hasGroupPermission(staffer, GroupsType.TRIAL)) {
						i++;
						TextComponent message = new TextComponent(groupsType.fullName() + staffer.getName() + " �3(�b" + Strings.getServerIP(staffer.getServer().getInfo().getName()) + "�3)");
						message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("�aClique para ir at� o staffer " + staffer.getName()).create()));
						if (!player.getServer().getInfo().equals(ProxyServer.getInstance().getServerInfo(staffer.getServer().getInfo().getName()))) {
							message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/connect " + staffer.getServer().getInfo().getName()));
						} else {
							message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + staffer.getName()));
						}
						player.sendMessage(message);
					}
				}
				player.sendMessage("");
				player.sendMessage("�fStaffs online: �3" + i);
				player.sendMessage("");
				return;
			}
		} else {
			return;
		}
		
	}

}
