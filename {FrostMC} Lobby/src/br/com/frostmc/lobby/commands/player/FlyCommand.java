package br.com.frostmc.lobby.commands.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.lobby.commands.BaseCommand;

public class FlyCommand extends BaseCommand {

	public FlyCommand() {
		super("fly", "use flight", Arrays.asList("voar"));
	}
	
	public static ArrayList<UUID> usingFlight = new ArrayList<>();

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			
			if (!BukkitMain.getPermissions().isLight(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			
			if (args.length == 0) {
				if (usingFlight.contains(player.getUniqueId())) {
					usingFlight.remove(player.getUniqueId());
					player.setAllowFlight(false);
					player.setFlying(false);
					player.sendMessage("§8[§a§lFLIGHT§8] §fSuas asas foram cortas pelo §cdemônio§f do gás oculto. Você não poderá mais voar.");
				} else {
					usingFlight.add(player.getUniqueId());
					player.setAllowFlight(true);
					player.setFlying(true);
					player.sendMessage("§8[§a§lFLIGHT§8] §fUm §aMILAGRE§f! Agora você pode voar!");
				}
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
