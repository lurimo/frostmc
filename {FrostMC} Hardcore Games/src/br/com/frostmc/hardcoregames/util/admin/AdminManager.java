package br.com.frostmc.hardcoregames.util.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;
import br.com.frostmc.hardcoregames.util.DropItem;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;
import br.com.frostmc.hardcoregames.util.kit.hability.Surprise;

public class AdminManager {

	private static ArrayList<UUID> admin = new ArrayList<UUID>(), quick = new ArrayList<UUID>();
	private static HashMap<UUID, ItemStack[]> saveInventory = new HashMap<>(), saveArmor = new HashMap<>();

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
		saveInventory.put(player.getUniqueId(), player.getInventory().getContents());
		saveArmor.put(player.getUniqueId(), player.getInventory().getArmorContents());
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		
		if ((FrostHG.state == State.INICIANDO) || (FrostHG.state == State.INVENCIVEL)) {
			if (FrostHG.getManager().getJogadores().contains(player.getUniqueId()))
				FrostHG.getManager().getJogadores().remove(player.getUniqueId());
			if (FrostHG.getManager().getEspectadores().contains(player.getUniqueId()))
				FrostHG.getManager().getEspectadores().remove(player.getUniqueId());
		} else if (FrostHG.state  == State.JOGO) {
			if (FrostHG.getManager().getJogadores().contains(player.getUniqueId()))
				FrostHG.getManager().getJogadores().remove(player.getUniqueId());
			if (FrostHG.getManager().getEspectadores().contains(player.getUniqueId()))
				FrostHG.getManager().getEspectadores().remove(player.getUniqueId());
				DropItem.dropItems(player, player.getLocation().clone());
				FrostHG.getManager().deathMessage(player, "desistiu da partida e foi eliminado do torneio.");
			
		}
		
		if (FrostHG.getManager().getJogadores().size() >= 1) {
			for (Player players : FrostHG.getManager().getOnlinePlayers()) {
				Scoreboarding.updateJogadores(players, players.getScoreboard());
			}
		}
		
		if ((!admin.contains(player.getUniqueId())) || (admin.isEmpty())) {
			admin.add(player.getUniqueId());
		}
		player.setGameMode(GameMode.CREATIVE);

		Vanish.makeVanished(player);
		Vanish.updateVanished();

		player.sendMessage("�3�lADMIN �fVoc� entrou no modo admin!");
		player.sendMessage("�e�lVANISH �fVoc� est� invis�vel para " + getInvisible(player) + "");
		
		player.getInventory().setItem(0, new ItemBuilder().setNome("�3Informa��es - �7Clique no jogador").setMaterial(Material.NAME_TAG).finalizar());
		player.getInventory().setItem(1, new ItemBuilder().setNome("�3Vanish - �7Clique para sair/entrar").setMaterial(Material.MAGMA_CREAM).finalizar());
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
		
		player.getInventory().setArmorContents(saveArmor.get(player.getUniqueId()));
		player.getInventory().setContents(saveInventory.get(player.getUniqueId()));
		player.updateInventory();
		
		if (FrostHG.state == State.INICIANDO) {
			FrostHG.getManager().getJogadores().add(player.getUniqueId());
		} else if (FrostHG.state  == State.INVENCIVEL) {
			FrostHG.getManager().getJogadores().add(player.getUniqueId());
			FrostHG.getManager().setupPlayer(player);
			new Surprise().setSurprise(player);
			FrostHG.getManager().getKitAPI().giveKit(player, Kits.valueOf(FrostHG.getManager().getKitAPI().getKit(player).toUpperCase()));
		} else if (FrostHG.state  == State.JOGO) {
			FrostHG.getManager().setEspectador(player);
		}
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
