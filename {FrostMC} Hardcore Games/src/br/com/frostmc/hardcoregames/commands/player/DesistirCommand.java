package br.com.frostmc.hardcoregames.commands.player;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;
import br.com.frostmc.hardcoregames.util.DropItem;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.TeleportServer;

public class DesistirCommand extends BaseCommand {

	public DesistirCommand() {
		super("desistir", "desistir do torneio", Arrays.asList("leave", "sair"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (args.length == 0) {
				if (FrostHG.state == State.INICIANDO) {
					player.sendMessage("§6§lTORNEIO §fEsse comando so podera ser executado a partir da §6Invencibilidade§f.");
				} else if (FrostHG.state == State.INVENCIVEL || FrostHG.state == State.JOGO) {
					if (!FrostHG.getManager().getJogadores().contains(player.getUniqueId())) {
						player.sendMessage("§6§lTORNEIO §fVocê precisar estar como jogador para desistir da partida.");
						return true;
					}
					if (BukkitMain.getPermissions().isLight(player)) {
						FrostHG.getManager().setEspectador(player);
					} else {
						FrostHG.getManager().getJogadores().remove(player.getUniqueId());
						FrostHG.getManager().getAntiLogged().add(player.getUniqueId());
						TeleportServer.connectPlayer(player, ServersType.LOBBY);
					}
					DropItem.dropItems(player, player.getLocation().clone());
					FrostHG.getManager().deathMessage(player, "desistiu da partida e foi eliminado do torneio.");
					if (FrostHG.getManager().getJogadores().size() >= 1) {
						for (Player players : FrostHG.getManager().getOnlinePlayers()) {
							Scoreboarding.updateJogadores(players, players.getScoreboard());
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
