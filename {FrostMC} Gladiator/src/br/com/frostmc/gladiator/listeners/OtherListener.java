package br.com.frostmc.gladiator.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import br.com.frostmc.common.util.event.SchedulerEvent;
import br.com.frostmc.common.util.event.SchedulerEvent.SchedulerType;
import br.com.frostmc.gladiator.FrostGladiator;
import br.com.frostmc.gladiator.battle1v1.GladiatorManager;
import br.com.frostmc.gladiator.scoreboard.Scoreboarding;

public class OtherListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onUpdate(SchedulerEvent event) {
		if (event.getCurrentTick() % 2 > 0)
			return;
		if (Bukkit.getOnlinePlayers().length <= 0)
			return;
		if (event.getType() == SchedulerType.TICK) {
			Scoreboarding.next();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!FrostGladiator.scoreboard.contains(player.getUniqueId())) {
					return;
				} else {
					Scoreboarding.updateTittle(player);
				}
			}
		} else if (event.getType() == SchedulerType.SECOND) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!FrostGladiator.scoreboard.contains(player.getUniqueId())) {
					return;
				} else {
					if (GladiatorManager.fighting.containsKey(player.getUniqueId())) {
						Scoreboarding.updateContager(player);
						GladiatorManager.addTime(player);
					} else {
						GladiatorManager.removeTime(player);
					}
				}
			}
		}
	}

}
