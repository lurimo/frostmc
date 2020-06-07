package br.com.frostmc.lobby.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.lobby.scoreboard.Scoreboarding;
import br.com.frostmc.lobby.util.crates.CrateManager;
import br.com.frostmc.lobby.util.crates.CrateType;

public class CrateSelectInventory implements Listener {

	@SuppressWarnings("deprecation")
	public void generate(Player player, CrateType crateType) {
		Inventory inventory = Bukkit.createInventory(null, 27, "§8§nCaixa de " + crateType.getName());
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);

		List<String> desc = new ArrayList<>();

		crateType.getDescription().stream().filter(prize -> !prize.equals(" ")).forEach(prize -> desc.add("§f» " + prize));

		int boxes = 0;
		int keys = 0;
		
		Material materialKey = Material.AIR;
		Material materialBox = Material.AIR;
		if (crateType == CrateType.DIAMOND) {
			materialBox = Material.DIAMOND_BLOCK;
			materialKey = Material.DIAMOND;
			boxes = gamer.getBox_diamond();
			keys = gamer.getKeyBox_diamond();
		} else if (crateType == CrateType.GOLD) {
			materialBox = Material.GOLD_BLOCK;
			materialKey = Material.GOLD_INGOT;
			boxes = gamer.getBox_gold();
			keys = gamer.getKeyBox_gold();
		} else if (crateType == CrateType.IRON) {
			materialBox = Material.IRON_BLOCK;
			materialKey = Material.IRON_INGOT;
			boxes = gamer.getBox_iron();
			keys = gamer.getKeyBox_iron();
		}
		
		inventory.setItem(4, new ItemBuilder().setNome("§aComprar Caixa").setMaterial(Material.REDSTONE).setLore(Arrays.asList("§a» §fEssa caixa está custando", "§a» " + (crateType.equals(CrateType.DIAMOND) ? "§3§l1 FICHA" : (crateType.equals(CrateType.GOLD) ? "§6§l20.000 MOEDAS" : "§6§l5.000 MOEDAS")))).finalizar());
		inventory.setItem(12, new ItemBuilder().setNome("§aVocê possui §f" + boxes + " §acaixa" + (boxes < 1 ? "" : "s") + ".").setMaterial(materialBox).setQuantia(boxes).finalizar());
		inventory.setItem(13, new ItemBuilder().setNome("§aPrêmios possíveis:").setMaterial(Material.SIGN).setLore(desc).finalizar());
		inventory.setItem(14, new ItemBuilder().setNome("§aVocê possui §f" + keys + " §achave" + (keys < 1 ? "" : "s") + ".").setMaterial(materialKey).setQuantia(keys).finalizar());
		inventory.setItem(22, new ItemBuilder().setNome("§aComprar Chave").setMaterial(Material.EMERALD).setQuantia(1).setLore(Arrays.asList("§a» §fEssa chave está custando", "§a» " + (crateType.equals(CrateType.DIAMOND) ? "§6§l10.000 MOEDAS" : (crateType.equals(CrateType.GOLD) ? "§6§l5.000 MOEDAS" : "§6§l1.000 MOEDAS")))).finalizar());

		inventory.setItem(9, new ItemBuilder().setNome("§cFechar inventário.").setMaterial(Material.ARROW).glow().finalizar());
		inventory.setItem(17, new ItemBuilder().setNome("§aAbrir caixa.").setMaterial(Material.getMaterial(351)).glow().setDurabilidade(10).finalizar());

		player.openInventory(inventory);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getClickedInventory() != null) {
			if (event.getClickedInventory().getTitle().contains("§8§nCaixa de")) {
				event.setCancelled(true);

				if (event.getInventory().getSize() != 27 && event.getInventory().getSize() != 26) {
					return;
				}

				event.setCancelled(true);

				Player player = (Player) event.getWhoClicked();
				Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
				ItemStack item = event.getCurrentItem();
				Material material = item.getType();

				CrateType crate = CrateType.getType(event.getClickedInventory().getTitle().replace("§8§nCaixa de ", ""));

				if (material == Material.getMaterial(351)) {
					int durability = item.getDurability();

					if (durability == 8) {
						player.closeInventory();
					} else if (durability == 10) {
						new CrateManager().generate(crate, gamer);
					}
				}
				
				if (material == Material.REDSTONE) {
					if (crate.equals(CrateType.DIAMOND)) {
						if (gamer.getFichas() >= 1) {
							gamer.removeFichas(1);
							gamer.addBox_diamond(1);
							
							player.sendMessage("§a§lCAIXA §fVocê adquiriu uma §bCaixa de Diamante §fpor §3§l1 FICHA§f.");
							player.closeInventory();
						} else {
							player.sendMessage("§c§lCAIXA §fVocê não possui §3§lFICHAS §fo suficiente!");
							player.closeInventory();
						}
					} else if (crate.equals(CrateType.GOLD)) {
						if (gamer.getMoedas() >= 20000) {
							gamer.removeMoedas(20000);
							gamer.addBox_gold(1);
							
							player.sendMessage("§a§lCAIXA §fVocê adquiriu uma §6Caixa de Ouro §fpor §6§l15.000 MOEDAS§f.");
							player.closeInventory();
						} else {
							player.sendMessage("§c§lCAIXA §fVocê não possui §6§lMOEDAS §fo suficiente!");
							player.closeInventory();
						}
					} else if (crate.equals(CrateType.IRON)) {
						if (gamer.getMoedas() >= 15000) {
							gamer.removeMoedas(15000);
							gamer.addBox_iron(1);
							
							player.sendMessage("§a§lCAIXA §fVocê adquiriu §7Caixa de Prata §fpor §6§l15.000 MOEDAS§f.");
							player.closeInventory();
						} else {
							player.sendMessage("§c§lCAIXA §fVocê não possui §6§lMOEDAS §fo suficiente!");
							player.closeInventory();
						}
					}
				} else if (material == Material.EMERALD) {
					if (crate.equals(CrateType.DIAMOND)) {
						if (gamer.getMoedas() >= 10000) {
							gamer.removeMoedas(10000);
							gamer.addKeyBox_diamond(1);
							
							player.sendMessage("§a§lCAIXA §fVocê adquiriu uma §bChave de Diamante §fpor §6§l10.000 MOEDAS§f.");
							player.closeInventory();
						} else {
							player.sendMessage("§c§lCAIXA §fVocê não possui §6§lMOEDAS §fo suficiente!");
							player.closeInventory();
						}
					} else if (crate.equals(CrateType.GOLD)) {
						if (gamer.getMoedas() >= 5000) {
							gamer.removeMoedas(5000);
							gamer.addKeyBox_gold(1);
							
							player.sendMessage("§a§lCAIXA §fVocê adquiriu uma §6Chave de Ouro §fpor §6§l5.000 MOEDAS§f.");
							player.closeInventory();
						} else {
							player.sendMessage("§c§lCAIXA §fVocê não possui §6§lMOEDAS §fo suficiente!");
							player.closeInventory();
						}
					} else if (crate.equals(CrateType.IRON)) {
						if (gamer.getMoedas() >= 1000) {
							gamer.removeMoedas(1000);
							gamer.addKeyBox_iron(1);
							
							player.sendMessage("§a§lCAIXA §fVocê adquiriu §7Chave de Prata §fpor §6§l1.000 MOEDAS§f.");
							player.closeInventory();
						} else {
							player.sendMessage("§c§lCAIXA §fVocê não possui §6§lMOEDAS §fo suficiente!");
							player.closeInventory();
						}
					}
					Scoreboarding.setScoreboard(player);
				} else if (material == Material.ARROW) {
					player.closeInventory();
				}
			}
		}
	}

}
