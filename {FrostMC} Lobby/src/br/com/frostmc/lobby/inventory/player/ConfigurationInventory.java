package br.com.frostmc.lobby.inventory.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import br.com.frostmc.common.command.player.TellCommand;
import br.com.frostmc.common.command.player.TellCommand.TellModes;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.lobby.FrostLobby;
import br.com.frostmc.lobby.commands.player.FlyCommand;

public class ConfigurationInventory {
	
	public static final String titleMain = "§8§nSuas configurações:";

	@SuppressWarnings("deprecation")
	public static void open(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 36, titleMain);

		ItemBuilder fly = null;
		ItemBuilder tell = null;
		ItemBuilder score = null;
		
		if (FlyCommand.usingFlight.contains(player.getUniqueId())) {
			fly = new ItemBuilder().setNome("§aAtivado").setMaterial(Material.getMaterial(351)).setDurabilidade(10).glow();
		} else {
			fly = new ItemBuilder().setNome("§cDesativado").setMaterial(Material.getMaterial(351)).setDurabilidade(8);
		}
		if (TellCommand.tellModes.get(player.getUniqueId()).equals(TellModes.ON)) {
			tell = new ItemBuilder().setNome("§aAtivado").setMaterial(Material.getMaterial(351)).setDurabilidade(10).glow();
		} else {
			tell = new ItemBuilder().setNome("§cDesativado").setMaterial(Material.getMaterial(351)).setDurabilidade(8);
		}
		if (FrostLobby.scoreboard.contains(player.getUniqueId())) {
			score = new ItemBuilder().setNome("§aAtivado").setMaterial(Material.getMaterial(351)).setDurabilidade(10).glow();
		} else {
			score = new ItemBuilder().setNome("§cDesativado").setMaterial(Material.getMaterial(351)).setDurabilidade(8);
		}
		
		inventory.setItem(10, new ItemBuilder().setNome("§aFly").setMaterial(Material.FEATHER).finalizar());
		inventory.setItem(19, fly.finalizar());
		
		inventory.setItem(12, new ItemBuilder().setNome("§aTell").setMaterial(Material.BOOK_AND_QUILL).finalizar());
		inventory.setItem(21, tell.finalizar());
		
		inventory.setItem(14, new ItemBuilder().setNome("§aScoreboard").setMaterial(Material.PAPER).finalizar());
		inventory.setItem(23, score.finalizar());
		
		inventory.setItem(17, new ItemBuilder().setNome("§cVoltar").setMaterial(Material.ARROW).glow().finalizar());
		player.openInventory(inventory);
	}
	
}