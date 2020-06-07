package br.com.frostmc.hardcoregames.commands.staffer;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class ToggleCommand extends BaseCommand {
	
	public ToggleCommand() {
		super("toggle");
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isYoutuberPlus(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("");
				player.sendMessage("§3§lTOGGLE §fUtilize o comando: /toggle (kits) (on/off) §8- §7desativar/ativar os kits.");
				player.sendMessage("§3§lTOGGLE §fUtilize o comando: /toggle (kit) <kit> (on/off) §8- §7desativar/ativar o kit.");
				player.sendMessage("");
				return true;
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("kits")) {
					if (args[1].equalsIgnoreCase("off")) {
						if (FrostHG.getManager().disablekit == true) {
							player.sendMessage("§3§lKIT §fOs kits já estão §c§lDESABILITADO");
							return true;
						}
						FrostHG.getManager().disablekit = true;
						for (Player players : FrostHG.getManager().getOnlinePlayers()) {
							FrostHG.getManager().getKitAPI().removeKit(players);
						}
						Bukkit.broadcastMessage("§3§lKIT §fTodos os kits foi §c§lDESABILITADO");
						player.sendMessage("§3§lKIT §fVocê §c§lDESABILITADO §fos kits.");
						for (Player todos : FrostHG.getManager().getOnlinePlayers()) {
							Scoreboarding.updateKit(player, todos.getScoreboard());
						}
						return true;
					} else if (args[1].equalsIgnoreCase("on")) {
						if (FrostHG.getManager().disablekit == false) {
							player.sendMessage("§3§lKIT §fOs kits já estão §a§lHABILITADO");
							return true;
						}
						FrostHG.getManager().disablekit = false;
						Bukkit.broadcastMessage("§3§lKIT §fTodos os kits foi §a§lHABILITADO");
						player.sendMessage("§3§lKIT §fVocê §a§lHABILITADO §ftodos os kits.");
						for (Player todos : FrostHG.getManager().getOnlinePlayers()) {
							Scoreboarding.updateKit(player, todos.getScoreboard());
						}
						return true;
					}
				}
			}
			if (args.length == 3) {
				if (args[0].equalsIgnoreCase("kit")) {
					Kits kits = Kits.NENHUM;
					try {
						kits = Kits.valueOf(args[1].toUpperCase());
					} catch (Exception exception) {
						sendError(player, "§fEsse kit não foi encontrado.");
						return true;
					}
					if (args[2].equalsIgnoreCase("on")) {
						if (FrostHG.getManager().kitsDesativados.contains(kits.name())) {
							FrostHG.getManager().kitsDesativados.remove(kits.name());
							commandSender.sendMessage("§b§lKIT §fO Kit §a" + kits.name().toLowerCase() + " §ffoi §a§lHABILITADO");
							Bukkit.broadcastMessage("§b§lKIT §fO Kit §a" + kits.name().toLowerCase() + " §ffoi §a§lHABILITADO");
						} else {
							commandSender.sendMessage("§b§lKIT §fO Kit §a" + kits.name().toLowerCase() + " §fja está §a§lHABILITADO");
						}
					} else if (args[2].equalsIgnoreCase("off")) {
						if (FrostHG.getManager().kitsDesativados.contains(kits.name())) {
							commandSender.sendMessage("§b§lKIT §fO Kit §a" + kits.name().toLowerCase() + " §fja está §c§lDESABILITADO");
						} else {
							FrostHG.getManager().kitsDesativados.add(kits.name());
							commandSender.sendMessage("§b§lKIT §fO Kit §a" + kits.name().toLowerCase() + " §ffoi §c§lDESABILITADO");
							Bukkit.broadcastMessage("§b§lKIT §fO Kit §a" + kits.name().toLowerCase() + " §ffoi §c§lDESABILITADO");
							for (Player on : FrostHG.getManager().getOnlinePlayers()) {
								 if (FrostHG.getManager().getJogadores().contains(on.getUniqueId())) {
									 if (FrostHG.getManager().getKitAPI().hasKit(on, kits)) {
										 FrostHG.getManager().getKitAPI().removeKit(on);
										player.sendMessage("§b§lKIT §fO seu Kit §a" + kits.name().toLowerCase() + " §ffoi §c§lDESABILITADO");
									 }
								 }
							}
						}
					}
				}
			}
		} else {
			return true;
		}
		return false;
	}

}
