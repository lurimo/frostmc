package br.com.frostmc.hardcoregames.timer.feast;

import java.util.Random;

import org.bukkit.inventory.ItemStack;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;

public class ItemChance {

	private int chance;
	private ItemStack item;
	private int randomStack;
	
	public ItemChance(ItemStack item, int chance, int randomStack) {
		this.item = item;
		this.chance = chance;
		this.randomStack = randomStack;
	}

	public ItemStack getItem() {
		if (randomStack != 0)
			return new ItemBuilder().setMaterial(item.getType()).setDurabilidade(item.getDurability()).setQuantia(new Random().nextInt(randomStack) + 1).finalizar();
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}
}