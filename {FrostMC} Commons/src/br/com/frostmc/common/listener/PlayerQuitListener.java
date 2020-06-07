package br.com.frostmc.common.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.fake.FakeManager;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.data.mysql.bukkit.authentication.Authentication;
import br.com.frostmc.core.util.enums.AuthType;

public class PlayerQuitListener implements Listener{
	
	@EventHandler
	public void asd(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		AccountBukkit account = new AccountBukkit(player);
		Authentication authentication = account.getAuthentication();
		if (authentication.exists()) {
			if (!authentication.getAuthType().equals(AuthType.NONREGISTERED)) {
				authentication.setAuthType(AuthType.NONLOGGED);
				authentication.save();
			}
		}
		
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		gamer.update();
		
		if (FakeManager.fake.containsKey(player.getUniqueId())) {
			FakeManager.fakes.remove(player.getName());
			FakeManager.fakeList.remove(player.getName());
			FakeManager.fake.remove(player.getUniqueId());
		}
		
		System.out.println("[ACCOUNT] " +  player.getName() + " dados salvos!");
	}
	
	@EventHandler
	public void asd(PlayerKickEvent event) {
		Player player = event.getPlayer();
		AccountBukkit account = new AccountBukkit(player);
		Authentication authentication = account.getAuthentication();
		if (authentication.exists()) {
			if (!authentication.getAuthType().equals(AuthType.NONREGISTERED)) {
				authentication.setAuthType(AuthType.NONLOGGED);
				authentication.save();
			}
		}
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		gamer.update();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void asd(PluginDisableEvent event) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			AccountBukkit account = new AccountBukkit(player);
			Authentication authentication = account.getAuthentication();
			if (authentication.exists()) {
				if (!authentication.getAuthType().equals(AuthType.NONREGISTERED)) {
					authentication.setAuthType(AuthType.NONLOGGED);
					authentication.save();
				}
			}
			Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
			gamer.update();
		}
	}
	
}
