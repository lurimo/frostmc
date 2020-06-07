package br.com.frostmc.pvp.command.staffer;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.pvp.command.BaseCommand;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Locais;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class SetwarpCommand extends BaseCommand {

	public SetwarpCommand() {
		super("setwarp", "setar as warps", Arrays.asList("set", "warpset"));
	}
	
	public String getCoordinates(Location location) {
		return "X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ();
	}
	
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isAdmin(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("");
				player.sendMessage("�8[�b�lADMINISTRA��O�8]");
				player.sendMessage("   �e�lSET-WARPS");
				player.sendMessage("");
				player.sendMessage(" �f/set (arena(n�mero 1-5)) �8- �7Para setar os spawns nas arenas.");
				player.sendMessage(" �f/set (spawn) �8- �7Para setar o spawn.");
				player.sendMessage(" �f/set (fps) �8- �7Para setar a fps.");
				player.sendMessage(" �f/set (lavachallenge) �8- �7Para setar a lava challenge.");
				player.sendMessage(" �f/set (1v1) �8- �7Para setar a 1v1.");
				player.sendMessage(" �f/set (onevsonepos1) �8- �7Para setar a posi��o do jogador 1.");
				player.sendMessage(" �f/set (onevsonepos2) �8- �7Para setar a posi��o do jogador 2.");
				player.sendMessage("");
				return true;
			}
			try {
				if (args[0].equalsIgnoreCase("1v1")) {
					WarpsAPI.setWarpLocation(player, Warps.ONEVSONE);
					player.sendMessage("�e�lWARP �7Voc� setou a warp �a1v1 �fcom sucesso.");
					return true;
				} else if (args[0].equalsIgnoreCase("feast")) {
					if (args[1].equalsIgnoreCase("pos1") || args[1].equalsIgnoreCase("pos2")) {
						WarpsAPI.registerInConfig(player, "Warps.Feast." + args[1]);
						player.sendMessage("");
						player.sendMessage("Voc� fixou �6" + args[1] + "�7 do �aFeast�7.");
						player.sendMessage("Coordenadas �e" + getCoordinates(player.getLocation()) + "�7.");
						player.sendMessage("");
					}
					return true;
				} else {
					Warps warp = Warps.valueOf(args[0].toUpperCase());
					WarpsAPI.setWarpLocation(player, warp);
					player.sendMessage("�8[�b�lADMINISTRA��O�8] �fVoc� setou a warp �a�l" + warp.name().toLowerCase() + "�f!");
					return true;
				}
			} catch(Exception exception) {
				try {
					Locais local = Locais.valueOf(args[0].toUpperCase());
					WarpsAPI.setWarpLocation(player, local);
					player.sendMessage("�8[�b�lADMINISTRA��O�8] �fVoc� setou o local �a" + local.name().toLowerCase() + "�f!");
					return true;
				} catch(Exception exception3) {
					sendError(player, "Essa warp/local n�o foi encontrada.");
				}
			}
		} else {
			return true;
		}
		return false;
	}

}
