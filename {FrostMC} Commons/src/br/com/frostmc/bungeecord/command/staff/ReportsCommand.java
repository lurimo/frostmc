package br.com.frostmc.bungeecord.command.staff;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.bungeecord.command.player.ReportCommand;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ReportsCommand extends ProxyCommand {

	public ReportsCommand() {
		super("reports", "", "reportlist");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
			ProxiedPlayer player = getPlayer(commandSender);
			if (!hasGroupPermission(player, GroupsType.TRIAL)) {
				player.sendMessage(Strings.getPermission());
				return;
			}
			if (args.length == 0) {
				if (ReportCommand.reports.size() == 0) {
					player.sendMessage("§3§lREPORT §fNão tem nenhum jogador reportado.");
					return;
				}
				player.sendMessage(" ");
				player.sendMessage("§3§lREPORTS [" + ReportCommand.reports.size() + "]");
				player.sendMessage(" ");
				for (String report : ReportCommand.reports) {
					TextComponent list = new TextComponent("§b" + report + " foi reportado. [" + (ReportCommand.reportStaffer.get(report) == null ? "Não verificado" : "Verificado") + "]");
					list.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("" + "\n" + "§3§lREPORT §8- §f" + report + "\n" + "\n" + "§fReportado por: §4" + ReportCommand.reportVitima.get(report) + "\n" + "§fMotivo: §c" + ReportCommand.reportReason.get(report) + "\n" + "§fData: §a" + ReportCommand.reportDate.get(report) + "\n" + "§fServidor: §3" + ReportCommand.reportServer.get(report) + "\n" + "\n" + "§a§nClique para teleportar." + "\n").create()));
					list.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + report + " verificar"));
					player.sendMessage(list);
				}
				player.sendMessage(" ");
				return;
			}
		} else {
			return;
		}
	}

}
