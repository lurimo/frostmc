package br.com.frostmc.onevsone.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import br.com.frostmc.common.util.event.SchedulerEvent;
import br.com.frostmc.common.util.event.SchedulerEvent.SchedulerType;
import br.com.frostmc.onevsone.Frost1v1;
import br.com.frostmc.onevsone.scoreboard.Scoreboarding;

public class OtherListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onUpdate(SchedulerEvent event) {
		if (event.getType() != SchedulerType.TICK)
			return;
		if (event.getCurrentTick() % 2 > 0)
			return;
		if (Bukkit.getOnlinePlayers().length <= 0)
			return;
		Scoreboarding.next();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!Frost1v1.scoreboard.contains(player.getUniqueId())) {
				return;
			} else {
				Scoreboarding.updateTittle(player);
			}
		}
	}

}
