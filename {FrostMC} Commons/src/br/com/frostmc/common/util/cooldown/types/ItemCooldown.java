package br.com.frostmc.common.util.cooldown.types;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.common.util.cooldown.Cooldown;

public class ItemCooldown extends Cooldown {

    @Getter
    private ItemStack item;

    @Getter
    @Setter
    private boolean selected;

    public ItemCooldown(ItemStack item, String name, Long duration) {
        super(name, duration);
        this.item = item;
    }
}
