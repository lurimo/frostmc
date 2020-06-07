package br.com.frostmc.gladiator.util.admin;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.gladiator.util.warp.WarpManager;

public class AdminManager {

	private static ArrayList<UUID> admin = new ArrayList<UUID>(), quick = new ArrayList<UUID>();

	public boolean initialize() {
		return true;
	}

	public static void setAdmin(Player player, boolean admin) {
		if (admin)
			setAdmin(player);
		else
			setPlayer(player);
	}

	public static void setAdmin(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		if ((!admin.contains(player.getUniqueId())) || (admin.isEmpty())) {
			admin.add(player.getUniqueId());
		}
		player.setGameMode(GameMode.CREATIVE);

		Vanish.makeVanished(player);
		Vanish.updateVanished();

		player.sendMessage("�3�lADMIN �fVoc� entrou no modo admin!");
		player.sendMessage("�e�lVANISH �fVoc� est� invis�vel para " + getInvisible(player) + "");
		
		player.getInventory().setItem(0, new ItemBuilder().setNome("�3Informa��es - �7Clique no jogador").setMaterial(Material.NAME_TAG).finalizar());
		player.getInventory().setItem(4, new ItemBuilder().setNome("�3Vanish - �7Clique para sair/entrar").setMaterial(Material.MAGMA_CREAM).finalizar());
		player.updateInventory();
	}

	public static void setPlayer(Player player) {
		admin.remove(player.getUniqueId());

		Vanish.makeVisible(player);
		Vanish.updateVanished();

		if (player.isOnline()) {
			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage("�a�lADMIN �fVoc� saiu do modo admin!");
			player.sendMessage("�e�lVANISH �fVoc� est� vis�vel para �7�lMEMBROS");
		}
		WarpManager.send(player);
	}

	@SuppressWarnings("deprecation")
	public void setPlayerQuit(Player player) {
		admin.remove(player.getUniqueId());

		for (Player players : Bukkit.getOnlinePlayers()) {
			players.showPlayer(player);
		}

		if (player.isOnline()) {
			player.getInventory().clear();

			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage("�a�lADMIN �fVoc� saiu do modo admin!");
			player.sendMessage("�e�lVANISH �fVoc� est� vis�vel para �7�lMEMBROS");
		}

	}

	public AdminLevel getPlayerLevel(Player p) {
		return (AdminLevel) Vanish.vanished.get(p.getUniqueId());
	}

	public static boolean isAdmin(Player player) {
		return admin.contains(player.getUniqueId());
	}

	public boolean isVanished(Player p) {
		return (Vanish.vanished.containsKey(p.getUniqueId()));
	}

	public boolean isQuick(Player player) {
		return quick.contains(player.getUniqueId());
	}

	public static enum AdminLevel {
		PLAYER, YOUTUBER, MOD, ADMIN, DONO;
	}

	public static String getInvisible(Player player) {
		if (BukkitMain.getPermissions().isDeveloper(player)) {
			return "�4�lDIRETORES �f�fe abaixo!!";
		}
		if (BukkitMain.getPermissions().isAdmin(player)) {
			return "�5�lADMINS �fe abaixo!";
		}
		if (BukkitMain.getPermissions().isMod(player)) {
			return "�5�lMODS �fe abaixo!";
		}
		if (BukkitMain.getPermissions().isTrial(player)) {
			return "�d�lTRIALS �fe abaixo!";
		}
		return "�7�lMEMBROS";
	}
}
