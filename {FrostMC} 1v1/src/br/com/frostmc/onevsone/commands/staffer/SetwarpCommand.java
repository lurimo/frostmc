package br.com.frostmc.onevsone.commands.staffer;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.onevsone.commands.BaseCommand;
import br.com.frostmc.onevsone.util.warp.WarpsAPI;
import br.com.frostmc.onevsone.util.warp.WarpsAPI.Locais;
import br.com.frostmc.onevsone.util.warp.WarpsAPI.Warps;

public class SetwarpCommand extends BaseCommand {

	public SetwarpCommand() {
		super("setwarp", "set to location", Arrays.asList("warpset"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isAdmin(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("�8[�b�lADMINISTRA��O�8] �fUtilize o comando: �7/set (position)");
				return true;
			}
			if (args.length == 1) {
				try {
					Warps warps = Warps.valueOf(args[0].toUpperCase());
					WarpsAPI.setWarpLocation(player, warps);
					player.sendMessage("�8[�b�lADMINISTRA��O�8] �fVoc� �a�lSETOU �fo spawn da 1v1.");
				} catch (Exception exception) {
					try {
						Locais locais = Locais.valueOf(args[0].toUpperCase());
						WarpsAPI.setWarpLocation(player, locais);
						player.sendMessage("�8[�b�lADMINISTRA��O�8] �fVoc� �a�lSETOU �fa posi��o do jogador " + locais.name().replaceAll("onevsonepos".toUpperCase(), ""));
					} catch (Exception exception2) {
						player.sendMessage("�8[�b�lADMINISTRA��O�8] �fA warp citada n�o foi encontrada!");
						return true;
					}
				}
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}