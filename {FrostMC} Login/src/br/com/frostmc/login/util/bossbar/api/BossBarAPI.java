package br.com.frostmc.login.util.bossbar.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Player;

import br.com.frostmc.core.util.Strings;
import br.com.frostmc.login.util.packets.Reflection;

public abstract class BossBarAPI {

	public static String animated = "§a§lTESTE";
	public static int integer = 0;
	
	public static String next() {
		integer++;
		if (integer == 0) {
			animated = "§fTorne-se §a§lVIP§f acessando nossa loja!";
		}
		if (integer == 1) {
			animated = "§fVisite nosso discord: §a" + Strings.getDiscord();
		}
		if (integer == 2) {
			animated = "§fAdquira já o seu §3§lVIP: §a" + Strings.getWebstore();
			integer = 0;
		}
		return animated;
	}
	
	public static String getAnimated() {
		return animated;
	}
	
	private static final Map<UUID, BossBar> barMap = new ConcurrentHashMap<>();
	public static void setMessage(Player player, String message) {
		setMessage(player, message, 100);
	}

	public static void setMessage(Player player, String message, float percentage) {
		setMessage(player, message, percentage, 0);
	}

	public static void setMessage(Player player, String message, float percentage, int timeout) {
		setMessage(player, message, percentage, timeout, 100);
	}

	public static void setMessage(Player player, String message, float percentage, int timeout, float minHealth) {
		if (!barMap.containsKey(player.getUniqueId())) {
			barMap.put(player.getUniqueId(), new BossBar(player, message, percentage, timeout, minHealth));
		}
		BossBar bar = barMap.get(player.getUniqueId());
		if (!bar.message.equals(message)) {
			bar.setMessage(message);
		}
		float newHealth = percentage / 100F * bar.getMaxHealth();
		if (bar.health != newHealth) {
			bar.setHealth(newHealth);
		}
		if (!bar.isVisible()) {
			bar.setVisible(true);
		}
	}

	public static String getMessage(Player player) {
		BossBar bar = getBossBar(player);
		if (bar == null) { return null; }
		return bar.getMessage();
	}

	public static boolean hasBar(@Nonnull Player player) {
		return barMap.containsKey(player.getUniqueId());
	}
	
	public static void removeBar(@Nonnull Player player) {
		BossBar bar = getBossBar(player);
		if (bar == null) { return; }
		bar.setVisible(false);
		barMap.remove(player.getUniqueId());
	}

	public static void setHealth(Player player, float percentage) {
		BossBar bar = getBossBar(player);
		if (bar == null) { return; }
		bar.setHealth(percentage);
	}

	public static float getHealth(@Nonnull Player player) {
		BossBar bar = getBossBar(player);
		if (bar == null) { return -1; }
		return bar.getHealth();
	}

	@Nullable
	public static BossBar getBossBar(@Nonnull Player player) {
		if (player == null) { return null; }
		return barMap.get(player.getUniqueId());
	}

	public static Collection<BossBar> getBossBars() {
		List<BossBar> list = new ArrayList<>(barMap.values());
		return list;
	}

	protected static void sendPacket(Player p, Object packet) {
		if (p == null || packet == null) { throw new IllegalArgumentException("player and packet cannot be null"); }
		try {
			Object handle = Reflection.getHandle(p);
			Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
			Reflection.getMethod(connection.getClass(), "sendPacket", Reflection.getNMSClass("Packet")).invoke(connection, new Object[] { packet });
		} catch (Exception e) {
		}
	}

}
