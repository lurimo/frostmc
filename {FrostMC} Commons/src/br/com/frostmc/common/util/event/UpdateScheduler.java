package br.com.frostmc.common.util.event;

import org.bukkit.Bukkit;

import br.com.frostmc.common.util.event.SchedulerEvent.SchedulerType;

public class UpdateScheduler implements Runnable {
	
	private long currentTick;

	@Override
	public void run() {
		currentTick++;
        Bukkit.getPluginManager().callEvent(new SchedulerEvent(SchedulerType.TICK, currentTick));

        if (currentTick % 20 == 0) {
            Bukkit.getPluginManager().callEvent(new SchedulerEvent(SchedulerType.SECOND, currentTick));
        }

        if (currentTick % 1200 == 0) {
            Bukkit.getPluginManager().callEvent(new SchedulerEvent(SchedulerType.MINUTE, currentTick));
        }
	}
}