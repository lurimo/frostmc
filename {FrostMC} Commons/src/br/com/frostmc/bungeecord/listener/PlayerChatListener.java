package br.com.frostmc.bungeecord.listener;

import br.com.frostmc.bungeecord.BungeeMain;
import br.com.frostmc.core.util.enums.GroupsType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerChatListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void chatEvent(ChatEvent event) {
		ProxiedPlayer player = (ProxiedPlayer) event.getSender();
		//if (player.getServer().getInfo().equals(ProxyServer.getInstance().getServerInfo("login"))) {
			//if (event.getMessage().startsWith("/")) {
				//if (event.getMessage().startsWith("/login") ||  event.getMessage().startsWith("/register")) {
					//event.setCancelled(false);
					//return;
				//} else {
					//event.setCancelled(true);
					//player.sendMessage("§3§lLOGIN §fVocê não pode executar comandos no servidor de login.");
					//return;
				//}
			//}
		//}
		if (BungeeMain.getInstance().hasGroupPermission(player, GroupsType.YOUTUBERPLUS) && !BungeeMain.getInstance().hasGroupPermission(player, GroupsType.MODGC)) {
			if (player.getServer().getInfo().equals(ProxyServer.getInstance().getServerInfo("ss"))) {
				if (event.getMessage().startsWith("/")) {
					event.setCancelled(true);
					player.sendMessage("§c§lMENSAGEM §fVocê está sendo telado não pode executar comandos.");
					return;
				}
			}
		} else if (BungeeMain.getInstance().hasGroupPermission(player, GroupsType.TRIAL)) {
			if (event.getMessage().startsWith("#")) {
				event.setCancelled(true);
				for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
					if (BungeeMain.getInstance().hasGroupPermission(players, GroupsType.TRIAL)) {
						players.sendMessage("§e§l[STAFF] " + PlayerLoginListener.checkGroup.get(player.getName()).fullName() + player.getName() + "§f: " + event.getMessage().replaceAll("#", ""));
					}
				}
				return;
			}
		}
		
		if (event.getMessage().startsWith("/")) {
			if (event.getMessage().contains(":") && !BungeeMain.getInstance().hasGroupPermission(player, GroupsType.ADMIN)) {
				event.setCancelled(true);
				player.sendMessage("§c§lMENSAGEM §fVocê não pode executar comandos que possuam \":\".");
				return;
			} else {
				event.setCancelled(false);
			}
		}
	}

}
