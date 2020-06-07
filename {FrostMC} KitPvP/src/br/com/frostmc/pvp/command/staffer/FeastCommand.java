package br.com.frostmc.pvp.command.staffer;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.pvp.FrostPvP;
import br.com.frostmc.pvp.command.BaseCommand;
import br.com.frostmc.pvp.scoreboard.Scoreboarding;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class FeastCommand extends BaseCommand {
	
	public FeastCommand() {
		super("feast");
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = (Player) commandSender;
			
			if (args.length == 0) {
				setCompassTarget(player);
				return true;
			}
			if (!BukkitMain.getPermissions().isAdmin(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			
			if (args.length == 1) {
				player.sendMessage("�8[�a�lFEAST�8] �fUtilize o comando: /feast (force/tempo/pos1/pos2)");
				if (args[0].equalsIgnoreCase("force")) {
					player.sendMessage("�8[�a�lFEAST�8] �fVoc� for�ou o spawn do feast. Tome cuidado ao utilizar esta ferramenta para n�o prejudicar os jogadores.");
					FrostPvP.getFeast().tempo = 1;
					return true;
				} else if (args[0].equalsIgnoreCase("pos1")) {
					FrostPvP.getConfigManager().registerInConfig(player, "Feast.pos1");
					player.sendMessage("�8[�a�lFEAST�8] �fVoc� setou a primeira localiza��o do feast: �f(�6" + player.getLocation().getBlockX() + "�f, �6" + player.getLocation().getBlockY() + "�f, �6" + player.getLocation().getBlockZ() + "�f).");
					return true;
				} else if (args[0].equalsIgnoreCase("pos2")) {
					if (FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1") == null) {
						player.sendMessage("�8[�a�lFEAST�8] �fVoc� ainda n�o setou a primeira localiza��o.");
						return true;
					}
					FrostPvP.getConfigManager().registerInConfig(player, "Feast.pos2");
					player.sendMessage("�8[�a�lFEAST�8] �fVoc� setou a segunda localiza��o do feast:�f (�6" + player.getLocation().getBlockX() + "�f, �6" + player.getLocation().getBlockY() + "�f, �6" + player.getLocation().getBlockZ() + "�f).");
					return true;
				} else if (isInteger(args[0])) {
					FrostPvP.getFeast().tempo = Integer.valueOf(args[0]);
					player.sendMessage("�8[�a�lFEAST�8] �fTempo de spawn do feast alterado para: �a" + Scoreboarding.getTimerFormat(FrostPvP.getFeast().tempo));
					return true;
				}
			}
		} else {
			return true;
		}
		return true;
	}	
	
	private void setCompassTarget(Player player) {
		if (WarpsAPI.isInWarp(player, Warps.ARENA)) {
			if (player.getInventory().contains(Material.COMPASS))  {
				player.setCompassTarget(FrostPvP.getConfigManager().getLocationFromConfig("Warps.Feast.pos1"));
				player.sendMessage("�8[�a�lFEAST�8] �fB�ssola agora indicando a dire��o do feast.");
			} else {
				player.sendMessage("�8[�c�lERRO�8] �fVoc� n�o possui uma b�ssola no invent�rio.");
			}
		} else {
			player.sendMessage("�8[�c�lERRO�8] �fVoc� n�o est� jogando na warp arena, logo, nenhum feast dispon�vel.");
		}
	}
	
}
