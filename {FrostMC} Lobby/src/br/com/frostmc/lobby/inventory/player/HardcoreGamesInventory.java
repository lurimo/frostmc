package br.com.frostmc.lobby.inventory.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.lobby.FrostLobby;
import br.com.frostmc.lobby.util.server.PingManager;

public class HardcoreGamesInventory {

	public void startUpdate() {
		new BukkitRunnable() {
			public void run() {

				updateInventory();

			}
		}.runTaskTimer(FrostLobby.getInstance(), 1, 5);
	}
	
	public static final String title = "§8§nServidores do Frost-HG";
	public static final Inventory inventory = Bukkit.createInventory(null, 36, title);
	
	public void updateInventory() {
		PingManager hga1 = new PingManager(ServersType.HG_A1);
		PingManager hga2 = new PingManager(ServersType.HG_A2);
		PingManager hga3 = new PingManager(ServersType.HG_A3);
		PingManager hga4 = new PingManager(ServersType.HG_A4);
		PingManager hga5 = new PingManager(ServersType.HG_A5);
		PingManager hga6 = new PingManager(ServersType.HG_A6);
		PingManager hga7 = new PingManager(ServersType.HG_A7);
		
		inventory.setItem(10, getItemHgRoom(hga1, "1", hga1.getOnlinePlayers()).finalizar());
		inventory.setItem(11, getItemHgRoom(hga2, "2", hga2.getOnlinePlayers()).finalizar());
		inventory.setItem(12, getItemHgRoom(hga3, "3", hga3.getOnlinePlayers()).finalizar());
		inventory.setItem(13, getItemHgRoom(hga4, "4", hga4.getOnlinePlayers()).finalizar());
		inventory.setItem(14, getItemHgRoom(hga5, "5", hga5.getOnlinePlayers()).finalizar());
		inventory.setItem(15, getItemHgRoom(hga6, "6", hga6.getOnlinePlayers()).finalizar());
		inventory.setItem(16, getItemHgRoom(hga7, "7", hga7.getOnlinePlayers()).finalizar());
	}
	
	@SuppressWarnings("deprecation")
	public static ItemBuilder getItemHgRoom(PingManager pingManager, String room, int playersOnlineHG) {
		if (pingManager.getMotd().contains("iniciando")) {
			return new ItemBuilder().setNome("§a§na" + room + ".hg.frostmc.com.br").setMaterial(Material.getMaterial(351)).setDurabilidade(10).setLore(Arrays.asList("", "§fA partida está no estágio §a§lINICIANDO§f.", "§fIniciando: §a" + pingManager.getMotd().replaceAll("iniciando: ", ""), "§fJogadores: §a" + pingManager.getOnlinePlayers() + "/" + pingManager.getMaxPlayers(), "")).setQuantia(playersOnlineHG);
		} else if (pingManager.getMotd().contains("invencibilidade")) {
			return new ItemBuilder().setNome("§e§na" + room + ".hg.frostmc.com.br").setMaterial(Material.getMaterial(351)).setDurabilidade(11).setLore(Arrays.asList("", "§fA partida está no estágio §e§lINVENCIBILIDADE§f.", "§fInvencibilidade: §e" + pingManager.getMotd().replaceAll("invencibilidade: ", ""), "§fJogadores: §e" + pingManager.getOnlinePlayers() + "/" + pingManager.getMaxPlayers(), "")).setQuantia(playersOnlineHG);
		} else if (pingManager.getMotd().contains("andamento")) {
			return new ItemBuilder().setNome("§6§na" + room + ".hg.frostmc.com.br").setMaterial(Material.getMaterial(351)).setDurabilidade(14).setLore(Arrays.asList("", "§fA partida está no estágio §6§lANDAMENTO§f.", "§fAndamento: §6" + pingManager.getMotd().replaceAll("andamento: ", ""), "§fJogadores: §6" + pingManager.getOnlinePlayers() + "/" + pingManager.getMaxPlayers(), "")).setQuantia(playersOnlineHG);
		} else if (pingManager.getMotd().contains("winner")) {
			return new ItemBuilder().setNome("§c§na" + room + ".hg.frostmc.com.br").setMaterial(Material.getMaterial(351)).setDurabilidade(1).setLore(Arrays.asList("", "§fA partida está no estágio §c§lFINAL§f.", "§fA partida foi §c§lENCERRADA§f.", ""));
		} else if (pingManager.getMotd().contains("Minecraft")) {
			return new ItemBuilder().setNome("§9§na" + room + ".hg.frostmc.com.br").setMaterial(Material.getMaterial(351)).setDurabilidade(12).setLore(Arrays.asList("", "§fEsse servidor está §9§lREINICIANDO§f.", "§fAguarde um momento para entrar.", ""));
		} else if (!pingManager.getMotd().contains(":")) {
			return new ItemBuilder().setNome("§9§na" + room + ".hg.frostmc.com.br").setMaterial(Material.getMaterial(351)).setDurabilidade(2).setLore(Arrays.asList("", "§fEsse servidor está §c§lOFFLINE§f.", "§fConecte-se em uma sala §c§lDISPONIVEL§f.", ""));
		}
		return new ItemBuilder().setMaterial(Material.AIR);
	}
	
	public static void open(Player player) {

		PingManager hga1 = new PingManager(ServersType.HG_A1);
		PingManager hga2 = new PingManager(ServersType.HG_A2);
		PingManager hga3 = new PingManager(ServersType.HG_A3);
		PingManager hga4 = new PingManager(ServersType.HG_A4);
		PingManager hga5 = new PingManager(ServersType.HG_A5);
		PingManager hga6 = new PingManager(ServersType.HG_A6);
		PingManager hga7 = new PingManager(ServersType.HG_A7);
		
		inventory.setItem(10, getItemHgRoom(hga1, "1", hga1.getOnlinePlayers()).finalizar());
		inventory.setItem(11, getItemHgRoom(hga2, "2", hga2.getOnlinePlayers()).finalizar());
		inventory.setItem(12, getItemHgRoom(hga3, "3", hga3.getOnlinePlayers()).finalizar());
		inventory.setItem(13, getItemHgRoom(hga4, "4", hga4.getOnlinePlayers()).finalizar());
		inventory.setItem(14, getItemHgRoom(hga5, "5", hga5.getOnlinePlayers()).finalizar());
		inventory.setItem(15, getItemHgRoom(hga6, "6", hga6.getOnlinePlayers()).finalizar());
		inventory.setItem(16, getItemHgRoom(hga7, "7", hga7.getOnlinePlayers()).finalizar());
		
		player.openInventory(inventory);
	}

}
