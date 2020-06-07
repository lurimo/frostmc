package br.com.frostmc.lobby.inventory.player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;

public class ParticleInventory {

	public static final String titleMain = "§8§nSuas particulas:";
	
	public static HashMap<UUID, ParticleModes> effect = new HashMap<UUID, ParticleModes>();
	
	public enum ParticleModes {
		HEART, EXPLOSION, FIRE;
	}
	
	@SuppressWarnings("deprecation")
	public static void open(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 27, titleMain);

		ItemBuilder heart = null;
		ItemBuilder explosion = null;
		ItemBuilder fire = null;
		
		if (effect.containsKey(player.getUniqueId())) {
			if (effect.get(player.getUniqueId()).equals(ParticleModes.HEART)) {
				heart = new ItemBuilder().setNome("§aCoração").setMaterial(Material.getMaterial(351)).setDurabilidade(1).glow().setLore(Arrays.asList("", "§fParticula de coração.", ""));
			} else {
				heart = new ItemBuilder().setNome("§aCoração").setMaterial(Material.getMaterial(351)).setDurabilidade(1).setLore(Arrays.asList("", "§fParticula de coração.", ""));
			}
			if (effect.get(player.getUniqueId()).equals(ParticleModes.EXPLOSION)) {
				explosion = new ItemBuilder().setNome("§aExplosão").setMaterial(Material.getMaterial(351)).setDurabilidade(7).glow().setLore(Arrays.asList("", "§fParticula de explosão.", ""));
			} else {
				explosion = new ItemBuilder().setNome("§aExplosão").setMaterial(Material.getMaterial(351)).setDurabilidade(7).setLore(Arrays.asList("", "§fParticula de explosão.", ""));
			}
			if (effect.get(player.getUniqueId()).equals(ParticleModes.FIRE)) {
				fire = new ItemBuilder().setNome("§aFogo").setMaterial(Material.getMaterial(351)).setDurabilidade(11).glow().setLore(Arrays.asList("", "§fParticula de fogo.", ""));
			} else {
				fire = new ItemBuilder().setNome("§aFogo").setMaterial(Material.getMaterial(351)).setDurabilidade(11).setLore(Arrays.asList("", "§fParticula de fogo.", ""));
			}
		} else {
			heart = new ItemBuilder().setNome("§aCoração").setMaterial(Material.getMaterial(351)).setDurabilidade(1).setLore(Arrays.asList("", "§fParticula de coração.", ""));
			explosion = new ItemBuilder().setNome("§aExplosão").setMaterial(Material.getMaterial(351)).setDurabilidade(7).setLore(Arrays.asList("", "§fParticula de explosão.", ""));
			fire = new ItemBuilder().setNome("§aFogo").setMaterial(Material.getMaterial(351)).setDurabilidade(11).setLore(Arrays.asList("", "§fParticula de fogo.", ""));
		}
		
		
		inventory.setItem(11, heart.finalizar());
		inventory.setItem(12, explosion.finalizar());
		inventory.setItem(13, fire.finalizar());
		
		inventory.setItem(17, new ItemBuilder().setNome("§cVoltar").setMaterial(Material.ARROW).glow().finalizar());
		player.openInventory(inventory);
	}
	
}
