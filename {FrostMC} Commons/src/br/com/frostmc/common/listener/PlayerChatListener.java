package br.com.frostmc.common.listener;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.ServerOptions;
import br.com.frostmc.core.util.Longs;

public class PlayerChatListener implements Listener {
	
	public String[] chatBlocks = new String[] { "weaven", "mush", "mu$h", "cogumelomc", "weavenmc", "avontsmc", "likekits", "mc-mush", "m u s h", "a v o n t s", "h3aver", "l1k3kits", "l1k3k1ts", "heavermc", "heaver", "avontsmc.com.br", "w.e.a.v.e.n", "m.u.s.h", "a.v.o.n.t.s", ".com", ".br", ".net", ".us", ".host", ". c o m", ". com", ". b r", ".br" };
	public static String[] tellBlocks = new String[] { "server", "servidor", "preto", "freekill", "fudido", "negro", "ddos", "net", "derrubar", "cair", "cai", "macaco", "gorila", "preto", "picolão de asfalto", "baiano", "nordestino", "gordo", "vesgo", "piche", "maguila", "vadia", "puta", "piranha", "vagabunda", "cusão" };
	public HashMap<UUID, Long> chatCooldown = new HashMap<>();
	
	@EventHandler
	public void asd(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		for (String string : chatBlocks) {
			if (event.getMessage().toLowerCase().contains(string.toLowerCase())) {
				player.sendMessage("§8[§c§lERRO§8] §fPara manter a ordem no chat da rede, bloqueamos sua mensagem. Ela contém palavras que preferimos que não sejam ditas.");
				event.setCancelled(true);
				return;
			}
		}
		
		if (!ServerOptions.CHAT.isActive()) {
			if (!BukkitMain.getPermissions().isFrost(player)) {
				event.setCancelled(true);
				player.sendMessage("§8[§c§lERRO§8] §fO chat está desativado no momento.");
				return;
			} else {
				event.setCancelled(false);
			}
		}
		
		if (chatCooldown.containsKey(player.getUniqueId())) {
			long expire = chatCooldown.get(player.getUniqueId());
			int seconds = (int) ((expire - System.currentTimeMillis()) / 1000L);
			if (seconds > 0) {
				event.setCancelled(true);
				player.sendMessage("§8[§c§lERRO§8] §fAguarde §c" + Longs.messageSmall(chatCooldown.get(player.getUniqueId())) + " §fpara enviar outra mensagem.");
				return;
			} else {
				event.setCancelled(false);
				chatCooldown.remove(player.getUniqueId());
			}
		}
		
		String message = null;
		
		if (BukkitMain.getPermissions().isFrost(player)) {
			message = "§f: §f" + event.getMessage().replaceAll("%", "%%").replaceAll("&", "§");
		} else {
			message = "§f: §7" + event.getMessage().replaceAll("%", "%%");
		}
		
		event.setFormat(player.getDisplayName() + message);
		
		if (!BukkitMain.getPermissions().isFrost(player)) {
			chatCooldown.put(player.getUniqueId(), Longs.fromString("5s"));
		}
	}

}
