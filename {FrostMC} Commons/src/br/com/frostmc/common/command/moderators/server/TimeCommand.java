package br.com.frostmc.common.command.moderators.server;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.core.util.Strings;

public class TimeCommand extends BaseCommand {

	public TimeCommand() {
		super("time", "alterar o tempo", Arrays.asList("timer"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isAdmin(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length < 2) {
				player.sendMessage("�8[�6�lTIME�8] �fUtilize o comando: /time setar (dia/noite) | /time add (value)");
				return false;
			}
			if (args[0].equals("setar") || args[1].equals("set")) {
				int value;
				if (args[1].equals("dia") || args[1].equals("day")) {
					value = 0;
				} else {
					if (args[1].equals("noite") || args[1].equals("night")) {
						value = 19000;
					} else {
						value = getInteger(commandSender, args[1], 0);
					}
				}
				for (World world : Bukkit.getWorlds()) {
					world.setTime(value);
				}
				sendWarning(player.getName() + " setou o tempo do servidor para " + value);
				player.sendMessage("�a�lTIME �fTempo setado para " + value);
			} else if (args[0].equals("add") || args[1].equals("adicionar")) {
				int value = getInteger(commandSender, args[1], 0);
				for (World world : Bukkit.getWorlds()) {
					world.setFullTime(world.getFullTime() + value);
				}
				sendWarning(player.getName() + " adicionou o tempo do servidor para " + value);
				player.sendMessage("�8[�6�lTIME�8] �fO valor " + value + " foi adicionado ao tempo!");
			} else {
				player.sendMessage("�8[�6�lTIME�8] �fUtilize o comando: /time set (dia/noite) | /time add (value)");
			}
		} else {
			CommandSender player = commandSender;
			if (args.length < 2) {
				player.sendMessage("�8[�6�lTIME�8] �fUtilize o comando: /time setar (dia/noite) | /time add (value)");
				return false;
			}
			if (args[0].equals("setar") || args[1].equals("set")) {
				int value;
				if (args[1].equals("dia") || args[1].equals("day")) {
					value = 0;
				} else {
					if (args[1].equals("noite") || args[1].equals("night")) {
						value = 19000;
					} else {
						value = getInteger(commandSender, args[1], 0);
					}
				}
				for (World world : Bukkit.getWorlds()) {
					world.setTime(value);
				}
				sendWarning(player.getName() + " setou o tempo do servidor para " + value);
				player.sendMessage("�8[�6�lTIME�8] �fTempo setado para " + value);
			} else if (args[0].equals("add") || args[1].equals("adicionar")) {
				int value = getInteger(commandSender, args[1], 0);
				for (World world : Bukkit.getWorlds()) {
					world.setFullTime(world.getFullTime() + value);
				}
				sendWarning(player.getName() + " adicionou o tempo do servidor para " + value);
				player.sendMessage("�8[�6�lTIME�8] �fO valor " + value + " foi adicionado ao tempo!");
			} else {
				player.sendMessage("�8[�6�lTIME�8] �fUtilize o comando: /time set (dia/noite) | /time add (value)");
			}
		}
		return false;
	}

}
