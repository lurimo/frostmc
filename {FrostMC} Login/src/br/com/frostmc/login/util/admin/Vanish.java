package br.com.frostmc.login.util.admin;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.login.util.admin.AdminManager.AdminLevel;

public class Vanish implements Listener {

	static HashMap<UUID, AdminLevel> vanished = new HashMap<>();

	public static void makeVanished(Player p) {
		if (BukkitMain.getPermissions().isDeveloper(p)) {
			makeVanished(p, AdminLevel.DONO);
		} else if (BukkitMain.getPermissions().isAdmin(p)) {
			makeVanished(p, AdminLevel.ADMIN);
		} else if (BukkitMain.getPermissions().isMod(p)) {
			makeVanished(p, AdminLevel.MOD);
		} else if (BukkitMain.getPermissions().isTrial(p)) {
			makeVanished(p, AdminLevel.YOUTUBER);
		}
	}

	@SuppressWarnings("deprecation")
	public static void makeVanished(Player p, AdminLevel level) {
		if (level.equals(AdminLevel.YOUTUBER)) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.showPlayer(p);

				if (!player.getName().equals(p.getName())) {
					if (!BukkitMain.getPermissions().isTrial(player)) {
						player.hidePlayer(p);
					}
				}
			}
		} else if (level.equals(AdminLevel.MOD)) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.showPlayer(p);

				if (!player.getName().equals(p.getName())) {
					if (!BukkitMain.getPermissions().isMod(player)) {
						player.hidePlayer(p);
					}
				}
			}
		} else if (level.equals(AdminLevel.ADMIN)) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.showPlayer(p);

				if (!player.getName().equals(p.getName())) {
					if (!BukkitMain.getPermissions().isAdmin(player)) {
						player.hidePlayer(p);
					}
				}
			}
		} else if (level.equals(AdminLevel.DONO)) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.showPlayer(p);

				if (!player.getName().equals(p.getName())) {
					if (!BukkitMain.getPermissions().isDeveloper(player)) {
						player.hidePlayer(p);
					}
				}
			}
		}
		vanished.put(p.getUniqueId(), level);
	}

	public static boolean isVanished(Player p) {
		return (vanished.containsKey(p.getUniqueId())) && (!((AdminLevel) vanished.get(p.getUniqueId())).equals(AdminLevel.PLAYER));
	}

	public AdminLevel getPlayerLevel(Player p) {
		return (AdminLevel) vanished.get(p.getUniqueId());
	}

	@SuppressWarnings("deprecation")
	public static void updateVanished() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (isVanished(p)) {
				makeVanished(p, (AdminLevel) vanished.get(p.getUniqueId()));
			} else {
				makeVisible(p);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void updateVanished(Player player) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!player.getName().equals(p.getName())) {
				if (isVanished(p)) {
					if (!BukkitMain.getPermissions().isTrial(player)) {
						if (player.canSee(p)) {
							player.hidePlayer(p);
						}
					}
				} else if (!player.canSee(p)) {

					player.showPlayer(p);

				}
			}
		}
	}

	public static void removeVanished(Player p) {
		vanished.remove(p.getUniqueId());
	}

	@SuppressWarnings("deprecation")
	public static void makeVisible(Player p) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.showPlayer(p);
		}
		vanished.put(p.getUniqueId(), AdminLevel.PLAYER);
	}
}
