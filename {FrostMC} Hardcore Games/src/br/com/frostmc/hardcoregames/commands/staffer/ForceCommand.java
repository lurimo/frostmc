package br.com.frostmc.hardcoregames.commands.staffer;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;
import br.com.frostmc.hardcoregames.timer.Timer;
import br.com.frostmc.hardcoregames.timer.feast.Feast;
import br.com.frostmc.hardcoregames.timer.minifeast.MiniFeast;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class ForceCommand extends BaseCommand {

	public ForceCommand() {
		super("force");
	}

	public enum ForceModes {
		KIT, FEAST, MINIFEAST;
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
				player.sendMessage("§3§lFORCE §fUtilize o comando: /force (kit/feast/minifeast) (player/kit)");
				return true;
			}
			
			if (args.length == 1) {
				ForceModes modes = ForceModes.KIT;
				try {
					modes = ForceModes.valueOf(args[0].toUpperCase());
				} catch (Exception exception) {
					player.sendMessage("§3§lFORCE §fO modo citado não foi encontrado.");
					return true;
				}
				if (modes.equals(ForceModes.KIT)) {
					player.sendMessage("§3§lFORCE §fUtilize o comando: /force kit [kit]");
					return true;
				} else if (modes.equals(ForceModes.FEAST)) {
					if (FrostHG.state != State.JOGO) {
						player.sendMessage("§3§lFORCE §fA partida precisa está no estágio jogo.");
						return true;
					}
					if (Feast.bausSpawned) {
						player.sendMessage("§3§lFORCE §fO feast já foi spawnado.");
						return true;
					}
					Feast.bausSpawned = true;
					Feast.sendFeastMessage();
					Feast.prepararFeast();
					Feast.addChest();
					Feast.preencherBaus();
					Timer.sendMSG("§6§lPARTIDA §fO Feast spawnou em (§6" + Feast.feastLoc.getBlockX() + "§f, §6" + Feast.feastLoc.getBlockY() + "§f, §6" + Feast.feastLoc.getBlockZ() + "§f) Digite /feast para apontar a bússola.");
					return true;
				} else if (modes.equals(ForceModes.MINIFEAST)) {
					if (FrostHG.state != State.JOGO) {
						player.sendMessage("§3§lFORCE §fA partida precisa está no estágio jogo.");
						return true;
					}
					MiniFeast.spawnMinifeast();
					return true;
				}
			}
			
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("kit")) {
					if (FrostHG.state==State.INICIANDO) {
						player.sendMessage("§3§lFORCE §A partida ainda não iniciou.");
						return true;
					}
					Kits kits = Kits.NENHUM;
					try {
						kits = Kits.valueOf(args[1].toUpperCase());
					} catch (Exception exception) {
						player.sendMessage("§3§lFORCE §fO kit §e" + args[1] + " §fnão foi encontrado.");
						return true;
					}
					for (Player todos : FrostHG.getManager().getOnlinePlayers()) {
						if (FrostHG.getManager().getJogadores().contains(todos.getUniqueId())) {
							FrostHG.getManager().getKitAPI().removeKit(todos);
							FrostHG.getManager().getKitAPI().setKit(todos, kits);
							FrostHG.getManager().getKitAPI().giveKit(todos, kits);
							Scoreboarding.updateKit(todos, todos.getScoreboard());
							todos.sendMessage("§3§lFORCE §fVocê recebeu o kit §e" + kits.name().toLowerCase() + "§f!");
						}
					}
					player.sendMessage("§3§lFORCE §fVocê forçou o kit §e" + kits.name().toLowerCase() + " §f!");
					return true;
				} else {
					if (FrostHG.state==State.INICIANDO) {
						player.sendMessage("§3§lFORCE §A partida ainda não iniciou.");
						return true;
					}
					Kits kits = Kits.NENHUM;
					try {
						kits = Kits.valueOf(args[1].toUpperCase());
					} catch (Exception exception) {
						player.sendMessage("§3§lFORCE §fO kit §e" + args[1] + " §fnão foi encontrado.");
						return true;
					}
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						sendOfflinePlayerMessage(player, args[1]);
						return true;
					}
					if (FrostHG.getManager().getJogadores().contains(target.getUniqueId())) {
						FrostHG.getManager().getKitAPI().removeKit(target);
						FrostHG.getManager().getKitAPI().setKit(target, kits);
						FrostHG.getManager().getKitAPI().giveKit(target, kits);
						Scoreboarding.updateKit(target, target.getScoreboard());
						target.sendMessage("§3§lFORCE §fVocê recebeu o kit §e" + kits.name().toLowerCase() + "§f!");
						player.sendMessage("§3§lFORCE §fVocê forcou o kit §e" + kits.name().toLowerCase() + " §fpara o jogador " + target.getName());
					}
					return true;
				}
			}
			
		} else {
			return true;
		}
		return false;
	}

}
