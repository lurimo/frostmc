package br.com.frostmc.hardcoregames.util.hologram.hologram;

import org.bukkit.Location;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.timer.Timer;
import br.com.frostmc.hardcoregames.timer.feast.Feast;
import br.com.frostmc.hardcoregames.util.hologram.HologramAPI;

public class FeastHologram {
	
	public void addFeast(Location location) {
		HologramAPI.criarHolograma(location.clone().add(0.5, 3.0, 0.5), "§eFeast irá spawnar em:");
		HologramAPI.criarHolograma(location.clone().add(0.5, 2.7, 0.5), "§f" + FrostHG.getManager().getTimerFormat(1200 - Timer.tempo));
	}
	
	public void removeFeast(Location location) {
		HologramAPI.removerHolograma(location.clone().add(0.5, 3.0, 0.5), "§eFeast irá spawnar em:");
		HologramAPI.removerHolograma(location.clone().add(0.5, 2.7, 0.5), "§f" + FrostHG.getManager().getTimerFormat(1200 - Timer.tempo));
	}
	
	public void set() {
		Location location = Feast.feastLoc;
		addFeast(location);
	}
	
	public void remove() {
		Location location = Feast.feastLoc;
		removeFeast(location);
	}
}
