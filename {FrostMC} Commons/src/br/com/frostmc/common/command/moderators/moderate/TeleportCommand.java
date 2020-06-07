package br.com.frostmc.common.command.moderators.moderate;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.util.fake.FakeManager;
import br.com.frostmc.core.util.Strings;

public class TeleportCommand extends BaseCommand {

	public TeleportCommand() {
		super("teleport", "", Arrays.asList("tp"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {

			Player player = getPlayer(commandSender);
			
			if (!BukkitMain.getPermissions().isYoutuberPlus(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("global") || args[0].equalsIgnoreCase("all")) {
					for (Player players : Bukkit.getOnlinePlayers()) {
						players.teleport(player);
					}
					sendWarning(player.getName() + " teleportou-se todos os jogadores!");
					player.sendMessage("�8[�a�lTELEPORT�8] �fVoc� �e�lTELEPORTOU �ftodos os jogadores para sua localiza��o.");
					return true;
				} else {
					if (Bukkit.getPlayer(args[0]) != null) {
						Player player2 = Bukkit.getPlayer(args[0]);
						player.teleport(player2);
						if (FakeManager.fake.containsKey(player.getUniqueId())) {
							sendWarning(player.getName() + "(" + FakeManager.fakeList.get(player.getName()) + ") teleportou-se para " + player2.getName());
						} else {
							sendWarning(player.getName() + " teleportou-se para " + player2.getName());
						}
						
						player.sendMessage("�8[�a�lTELEPORT�8] �fVoc� foi teleportado para o jogador �e" + player2.getName() + "�f.");
					} else {
						sendOfflinePlayerMessage(commandSender, args[0]);
						return false;
					}
				}
			} else if (args.length == 2) {
				if (Bukkit.getPlayer(args[0]) != null) {
					if (Bukkit.getPlayer(args[1]) != null) {
						Player player1 = Bukkit.getPlayer(args[0]);
						Player player2 = Bukkit.getPlayer(args[1]);
						player1.teleport(player2);
					
						if (FakeManager.fake.containsKey(player.getUniqueId())) {
							sendWarning(player.getName() + "(" + FakeManager.fakeList.get(player.getName()) + ") teleportou " + player1.getName() + " para " + player2.getName());
						} else {
							sendWarning(player.getName() + " teleportou " + player1.getName() + " para " + player2.getName());
						}

						player.sendMessage("�8[�a�lTELEPORT�8] �fVoc� teleportou o jogador �e" + player1.getName() + "�f para o jogador �e" + player2.getName() + "�f");
					} else {
						sendOfflinePlayerMessage(commandSender, args[0]);
						return false;
					}
				} else {
					sendOfflinePlayerMessage(commandSender, args[0]);
					return false;
				}
			} else if (args.length == 3) {
				if (!isInteger(args[0]) && !isInteger(args[1]) && !isInteger(args[2])) {
					commandSender.sendMessage("�8[�a�lTELEPORT�8] �fUtilize o comando: /tp (x) (y) (z)");
					return false;
				}
				
				Location loc = new Location(player.getWorld(), getInteger(args[0]), getInteger(args[1]), getInteger(args[2]));
				
				loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
				player.teleport(loc);

				if (FakeManager.fake.containsKey(player.getUniqueId())) {
					sendWarning(player.getName() + "(" + FakeManager.fakeList.get(player.getName()) + ") teleportou-se para as coordenadas " + getInteger(args[0]) + ", " + getInteger(args[1]) + ", " + getInteger(args[2]));
				} else {
					sendWarning(player.getName() + " teleportou-se para as coordenadas " + getInteger(args[0]) + ", " + getInteger(args[1]) + ", " + getInteger(args[2]));
				}
				
				player.sendMessage("�8[�a�lTELEPORT�8] �fVoc� foi teleportado para X:�e" + args[0] + " �fY:�e" + args[1] + "�f Z:�e" + args[2]);

			} else {
				commandSender.sendMessage("�8[�a�lTELEPORT�8] �fUtilize o comando: /tp (player) (player2)");
				return false;
			}
		} else {
			return true;
		}

		return true;
	}
}