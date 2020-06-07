package br.com.frostmc.common.util.gamer;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.BukkitMain;

public class GamerManager {
	
	private HashMap<UUID, Gamer> gamers = new HashMap<UUID, Gamer>();

	public Gamer addGamer(Player player) {
		gamers.put(player.getUniqueId(), new Gamer(player));
		return gamers.get(player.getUniqueId());
	}
	
	public Gamer addGamer(OfflinePlayer offlinePlayer) {
		gamers.put(offlinePlayer.getUniqueId(), new Gamer(offlinePlayer));
		return gamers.get(offlinePlayer.getUniqueId());
	}
	
	public Gamer addGamer(UUID uuid) {
		gamers.put(uuid, new Gamer(uuid));
		return gamers.get(uuid);
	}
	
	public void removeGamer(Player player) {
		gamers.remove(player.getUniqueId());
	}
	
	public void removeGamer(OfflinePlayer offlinePlayer) {
		gamers.remove(offlinePlayer.getUniqueId());
	}

	public void removeGamer(UUID uuid) {
		gamers.remove(uuid);
	}

	public Gamer getGamer(Player player) {
		if (!gamers.containsKey(player.getUniqueId())) {

			Gamer newData = new Gamer(player);
			
			newData.loadProfile();

			gamers.put(player.getUniqueId(), newData);
		}
		return gamers.get(player.getUniqueId());
	}
	
	public Gamer getGamer(OfflinePlayer offlinePlayer) {
		if (!gamers.containsKey(offlinePlayer.getUniqueId())) {

			Gamer newData = new Gamer(offlinePlayer);

			newData.loadProfile();

			gamers.put(offlinePlayer.getUniqueId(), newData);
		}
		return gamers.get(offlinePlayer.getUniqueId());
	}
	
	public Gamer getGamer(UUID uuid) {
		if (!gamers.containsKey(uuid)) {

			Gamer newData = new Gamer(uuid);
			
			newData.loadProfile();

			gamers.put(uuid, newData);
		}
		return gamers.get(uuid);
	}

	public HashMap<UUID, Gamer> getGamers() {
		return gamers;
	}

	public void update(Gamer gamer) {
		new BukkitRunnable() {
			public void run() {	
				gamer.update();
				gamers.remove(gamer.getUuid());
			}
		}.runTaskAsynchronously(BukkitMain.getPlugin());
	}
	
}