package br.com.frostmc.common.command.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.util.Longs;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.RunningType;

public class DoublexpCommand extends BaseCommand{

	public DoublexpCommand() {
		super("doublexp", "multiplique seu xp por 2", Arrays.asList("xp", "xps"));
	}
	
	public enum DoublexpModes {
		ADD, REMOVE;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = (Player) commandSender;
			AccountBukkit account = new AccountBukkit(player);
			Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
			if (args.length == 0) {
				if (BukkitMain.getPermissions().isAdmin(player)) {
					player.sendMessage("�3�lDOUBLEXP �fUtilize o comando: /doublexp (player) (add/remove) (quantity)");
				}
				if (account.getDoubleXP().getRunningType().equals(RunningType.DESACTIVED)) {
					if (account.getDoubleXP().getQuantity() >= 1) {
						account.getDoubleXP().removeQuantity(1);
						account.getDoubleXP().setRunningType(RunningType.ACTIVED);
						account.getDoubleXP().setExpire(Longs.fromString("1h"));
						account.getDoubleXP().save();
						gamer.setDoublexp(true);
						player.sendMessage("�8[�6�lDOUBLEXP�8] �fUm doublexp foi ativado por �e" + Longs.messageSmall(account.getDoubleXP().getExpire()));
						return true;
					} else {
						player.sendMessage("�8[�6�lDOUBLEXP�8] �fVoc� n�o possui nenhum DOUBLEXP ativo no momento.");
						return true;
					}
				} else {
					if (gamer.isDoublexp()) {
						player.sendMessage("�8[�6�lDOUBLEXP�8] �fVoc� j� possui um pacote de DOUBLEXP ativo no momento.");
						return true;
					}
				}
			}
			if (!BukkitMain.getPermissions().isAdmin(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
					AccountBukkit accountOffline = new AccountBukkit(offlinePlayer);
					if (!accountOffline.getDoubleXP().exists()) {
						player.sendMessage("�8[�c�lERRO�8] �fEsse jogador n�o foi encontrado em nosso banco de dados.");
						return true;
					}
					player.sendMessage("�8[�6�lDOUBLEXP�8] �fUtilize o comando: /doublexp (player) (add/remove) (quantity)");
					return true;
				}
				AccountBukkit accountTarget = new AccountBukkit(target);
				if (!accountTarget.getDoubleXP().exists()) {
					player.sendMessage("�8[�c�lERRO�8] �fEsse jogador n�o foi encontrado em nosso banco de dados.");
					return true;
				}
				player.sendMessage("�8[�6�lDOUBLEXP�8] �fUtilize o comando: /doublexp (player) (add/remove) (quantity)");
				return true;
			}
			if (args.length == 3) {
				DoublexpModes modes = DoublexpModes.REMOVE;
				try {
					modes = DoublexpModes.valueOf(args[1].toUpperCase());
				} catch (Exception exception) {
					player.sendMessage("�8[�6�lDOUBLEXP�8] �fEsse modo n�o foi identificado.");
					return true;
				}
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
					AccountBukkit accountOffline = new AccountBukkit(offlinePlayer);
					if (modes.equals(DoublexpModes.ADD)) {
						if (isInteger(args[2])) {
							accountOffline.getDoubleXP().addQuantity(Integer.valueOf(args[2]));
							accountOffline.getDoubleXP().save();
							sendWarning(player.getName() + " adicionou " + args[2] + " quantidade de xp para " + offlinePlayer.getName());
							player.sendMessage("�8[�6�lDOUBLEXP�8] �fVoc� adicionou �b" + args[2] + " �fquantidade de xp para " + offlinePlayer.getName());
							return true;
						}
					} else if (modes.equals(DoublexpModes.REMOVE)) {
						if (isInteger(args[2])) {
							accountOffline.getDoubleXP().removeQuantity(Integer.valueOf(args[2]));
							accountOffline.getDoubleXP().save();
							sendWarning(player.getName() + " removeu " + args[2] + " quantidade de xp d� " + offlinePlayer.getName());
							player.sendMessage("�8[�6�lDOUBLEXP�8] �fVoc� removeu �b" + args[2] + " �fquantidade de xp d� " + offlinePlayer.getName());
							return true;
						}
					}
					return true;
				}
				AccountBukkit accountTarget = new AccountBukkit(target);
				if (modes.equals(DoublexpModes.ADD)) {
					if (isInteger(args[2])) {
						accountTarget.getDoubleXP().addQuantity(Integer.valueOf(args[2]));
						accountTarget.getDoubleXP().save();
						sendWarning(player.getName() + " adicionou " + args[2] + " quantidade de xp para " + target.getName());
						player.sendMessage("�8[�6�lDOUBLEXP�8] �fVoc� adicionou �b" + args[2] + " �fquantidade de xp para " + target.getName());
						target.sendMessage("�8[�6�lDOUBLEXP�8] �fVoc� ganhou �b" + args[2] + " �fquantidade de xp.");
						return true;
					}
				} else if (modes.equals(DoublexpModes.REMOVE)) {
					if (isInteger(args[2])) {
						accountTarget.getDoubleXP().removeQuantity(Integer.valueOf(args[2]));
						accountTarget.getDoubleXP().save();
						sendWarning(player.getName() + " removeu " + args[2] + " quantidade de xp d� " + target.getName());
						player.sendMessage("�8[�6�lDOUBLEXP�8] �fVoc� removeu �b" + args[2] + " �fquantidade de xp d� " + target.getName());
						target.sendMessage("�8[�6�lDOUBLEXP�8] �fForam removidos �b" + args[2] + " �fquantidade de xp.");
						return true;
					}
				}
			}
		} else {
			CommandSender player = commandSender;
			if (args.length == 0) {
				player.sendMessage("�8[�6�lDOUBLEXP�8] �fUtilize o comando: /doublexp (player) (add/remove) (quantity)");
				return true;
			}
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
					AccountBukkit accountOffline = new AccountBukkit(offlinePlayer);
					if (!accountOffline.getDoubleXP().exists()) {
						player.sendMessage("�8[�c�lERRO�8] �fEsse jogador n�o foi encontrado em nosso banco de dados.");
						return true;
					}
					player.sendMessage("�8[�6�lDOUBLEXP�8] �fUtilize o comando: /doublexp (player) (add/remove) (quantity)");
					return true;
				}
				AccountBukkit accountTarget = new AccountBukkit(target);
				if (!accountTarget.getDoubleXP().exists()) {
					player.sendMessage("�8[�c�lERRO�8] �fEsse jogador n�o foi encontrado em nosso banco de dados.");
					return true;
				}
				player.sendMessage("�8[�6�lDOUBLEXP�8] �fUtilize o comando: /doublexp (player) (add/remove) (quantity)");
				return true;
			}
			if (args.length == 3) {
				DoublexpModes modes = DoublexpModes.REMOVE;
				try {
					modes = DoublexpModes.valueOf(args[1].toUpperCase());
				} catch (Exception exception) {
					player.sendMessage("�8[�6�lDOUBLEXP�8] �fEsse modo n�o foi identificado.");
					return true;
				}
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
					AccountBukkit accountOffline = new AccountBukkit(offlinePlayer);
					if (modes.equals(DoublexpModes.ADD)) {
						if (isInteger(args[2])) {
							accountOffline.getDoubleXP().addQuantity(Integer.valueOf(args[2]));
							accountOffline.getDoubleXP().save();
							sendWarning(player.getName() + " adicionou " + args[2] + " quantidade de xp para " + offlinePlayer.getName());
							player.sendMessage("�8[�6�lDOUBLEXP�8] �fVoc� adicionou �b" + args[2] + " �fquantidade de xp para " + offlinePlayer.getName());
							return true;
						}
					} else if (modes.equals(DoublexpModes.REMOVE)) {
						if (isInteger(args[2])) {
							accountOffline.getDoubleXP().removeQuantity(Integer.valueOf(args[2]));
							accountOffline.getDoubleXP().save();
							sendWarning(player.getName() + " removeu " + args[2] + " quantidade de xp d� " + offlinePlayer.getName());
							player.sendMessage("�8[�6�lDOUBLEXP�8] �fVoc� removeu �b" + args[2] + " �fquantidade de xp d� " + offlinePlayer.getName());
							return true;
						}
					}
					return true;
				}
				AccountBukkit accountTarget = new AccountBukkit(target);
				if (modes.equals(DoublexpModes.ADD)) {
					if (isInteger(args[2])) {
						accountTarget.getDoubleXP().addQuantity(Integer.valueOf(args[2]));
						accountTarget.getDoubleXP().save();
						sendWarning(player.getName() + " adicionou " + args[2] + " quantidade de xp para " + target.getName());
						player.sendMessage("�8[�6�lDOUBLEXP�8] �fVoc� adicionou �b" + args[2] + " �fquantidade de xp para " + target.getName());
						target.sendMessage("�8[�6�lDOUBLEXP�8] �fVoc� ganhou �b" + args[2] + " �fquantidade de xp." );
						return true;
					}
				} else if (modes.equals(DoublexpModes.REMOVE)) {
					if (isInteger(args[2])) {
						accountTarget.getDoubleXP().removeQuantity(Integer.valueOf(args[2]));
						accountTarget.getDoubleXP().save();
						sendWarning(player.getName() + " removeu " + args[2] + " quantidade de xp d� " + target.getName());
						player.sendMessage("�8[�6�lDOUBLEXP�8] �fVoc� removeu �b" + args[2] + " �fquantidade de xp d� " + target.getName());
						target.sendMessage("�8[�6�lDOUBLEXP�8] �fForam removidos �b" + args[2] + " �fquantidade de xp.");
						return true;
					}
				}
			}
			return true;
		}
		return false;
	}

}