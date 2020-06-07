package br.com.frostmc.common.command.we;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;

public class WandCommand extends BaseCommand implements Listener{

	public WandCommand() {
		super("wand", "Take a wand to modify the world.");
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);

			if (!BukkitMain.getPermissions().isYoutuberPlus(player)) {
				return true;
			}

			if (args.length != 0) {
				commandSender.sendMessage("§3§lWORLDEDIT §fUtilize o comando: /wand");
				return false;
			}

			player.getInventory().addItem(new ItemBuilder().setNome("§aWand").setMaterial(Material.BLAZE_ROD).finalizar());
			player.sendMessage("§a§lWORLDEDIT §fVocê recebeu sua varinha.");
		} else {
			return true;
		}
		return true;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getItemInHand().getType().equals((Object) Material.BLAZE_ROD) && player.getItemInHand().getItemMeta().hasDisplayName() & player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aWand")) {
			if (BukkitMain.getPermissions().isYoutuberPlus(event.getPlayer())) {
				if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					Block clicked = event.getClickedBlock();
					BukkitMain.getWorldEdit().setFirstPosition(event.getPlayer().getUniqueId(), clicked.getLocation());
					event.getPlayer().sendMessage("§a§lWAND §fA primeira localização foi setada §6(" + clicked.getLocation().getBlockX() + "," + clicked.getLocation().getBlockY() + "," + clicked.getLocation().getBlockZ() + ").");
				} else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					Block clicked = event.getClickedBlock();
					BukkitMain.getWorldEdit().setSecondPosition(event.getPlayer().getUniqueId(), clicked.getLocation());
					event.getPlayer().sendMessage("§a§lWAND §fA segunda localização foi setada §6(" + clicked.getLocation().getBlockX() + "," + clicked.getLocation().getBlockY() + "," + clicked.getLocation().getBlockZ() + ").");
				}
				event.setCancelled(true);
			}
		}
	}
	
}
