package br.com.frostmc.bungeecord.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.frostmc.bungeecord.BungeeMain;
import br.com.frostmc.bungeecord.command.staff.MaintenanceCommand;
import br.com.frostmc.core.util.Strings;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PingServerListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void proxyPingEvent(ProxyPingEvent event) {
		PlayerLoginListener.checkServerPing.put(event.getConnection().getAddress().getHostName(), Long.valueOf(System.currentTimeMillis()));
		ServerPing ping = event.getResponse();
		ServerPing.Protocol version = ping.getVersion();
		ping.getPlayers().setMax(BungeeMain.getInstance().getProxy().getPlayers().size() + 1);
		ping.getPlayers().setOnline(BungeeMain.getInstance().getProxy().getPlayers().size());
		
		if (version.getProtocol() > 47) {
			version.setProtocol(999);
			version.setName("1.7x - 1.8x");
		} else if (MaintenanceCommand.maintenance) {
			version.setProtocol(999);
			version.setName("Manutenção");
		} else {
			version.setProtocol(version.getProtocol());
			version.setName("");
		}
		//version.setProtocol((MaintenanceCommand.maintenance ? 999 : version.getProtocol()));
		String[] motd = {
				"         §3§l❄ §fAcesse: §b" + Strings.getWebstore().replaceAll("https://", "").replaceAll("/", "") + "§r §3§l❄",
				" §3§l❄ §fUse o Cupom §3§lABERTURA15 §fem nossa §b§lLOJA §3§l❄",
				"   §3§l❄ §fVenha jogar a melhor experiencia do §3§lPVP §3§l❄",
				};
		
		ping.setDescription("  §8§l§m-]§7§l§m-§f§l§m--§7 " + Strings.server + " §7» §fMinecraft Network §f§l§m--§7§l§m-§8§l§m[-§r" + "\n" + (MaintenanceCommand.maintenance ? "        §fEstamos atualmente em §cManutenção§f!" : motd[new Random().nextInt(motd.length)]));
		List<String> list = new ArrayList<String>();
		list.add("    " + Strings.getServer() + " §8» §fInformações:");
		list.add("");
		if (MaintenanceCommand.maintenance != true) {
			list.add(" §fVersôes disponiveis: §a1.7x - 1.8x");
			list.add(" §fJogadores jogando: §a" + ping.getPlayers().getOnline());
		} else {
			list.add(" §fEstamos no modo §c§lMANUTENCAO");
			list.add(" §fEm breve voltaremos a ativa.");
		}
		list.add("");
		list.add(" §fTwitter: §3" + Strings.getTwitter().replaceAll("https://", "").replaceAll("/", ""));
		list.add(" §fDiscord: §3" + Strings.getDiscord());
		list.add(" §fSite: §3" + Strings.getWebsite().replaceAll("https://", "").replaceAll("/", ""));
		list.add("");
		ServerPing.PlayerInfo[] playerInfo = new ServerPing.PlayerInfo[list.size()];
		for (int i = 0; i < playerInfo.length; i++) {
			playerInfo[i] = new ServerPing.PlayerInfo(list.get(i), "");
		}
		ping.getPlayers().setSample(playerInfo);
		ping.setVersion(version);
	}

}
