package br.com.frostmc.hardcoregames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import br.com.frostmc.common.util.event.SchedulerEvent;
import br.com.frostmc.common.util.event.SchedulerEvent.SchedulerType;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;

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
				if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
					return;
				} else {
					Scoreboarding.updateTittle(player);
				}
			}
			return;
		} else if (event.getType() == SchedulerType.SECOND) {
			//if (FrostHG.state == State.JOGO) {
				//if (!Feast.bausSpawned) {
					//new FeastHologram().remove();
					//new FeastHologram().set();
				//}
			//}
			return;
		}
	}

}
