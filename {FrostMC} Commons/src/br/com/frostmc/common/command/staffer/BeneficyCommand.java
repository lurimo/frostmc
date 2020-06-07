package br.com.frostmc.common.command.staffer;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.core.data.mysql.bungeecord.server.Benificy;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.BeneficyType;
import br.com.frostmc.core.util.enums.RunningType;

public class BeneficyCommand extends BaseCommand {
	
	public BeneficyCommand() {
		super("beneficy", "selecionem um beneficio", Arrays.asList("beneficio", "beneficios", "liberar"));
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
				player.sendMessage("§3§lBENEFICY §fUtilize o comando: /beneficy (beneficy) (on/off)");
				return true;
			}
			if (args.length == 1) {
				player.sendMessage("§3§lBENEFICY §fUtilize o comando: /beneficy (beneficy) (on/off)");
				return true;
			}
			BeneficyType beneficy = BeneficyType.DOUBLEXP;
			try {
				beneficy = BeneficyType.valueOf(args[0].toUpperCase());
			} catch (Exception exception) {
				player.sendMessage("§e§lBENEFICY §fEsse beneficio não foi encontrado.");
				return true;
			}
			if (args[1].equalsIgnoreCase("on")) {
				if (beneficy.equals(BeneficyType.DOUBLEXP)) {
					Benificy.create(RunningType.ACTIVED, BeneficyType.DOUBLEXP);
					Benificy.save(RunningType.ACTIVED, BeneficyType.DOUBLEXP);
					Bukkit.broadcastMessage("§a§lBENEFICY §fO beneficio doublexp foi ativado.");
					player.sendMessage("§a§lBENEFICY §fO beneficio doublexp foi ativado.");
					return true;
				} else if (beneficy.equals(BeneficyType.FULLKIT)) {
					Benificy.create(RunningType.ACTIVED, BeneficyType.FULLKIT);
					Benificy.save(RunningType.ACTIVED, BeneficyType.FULLKIT);
					Bukkit.broadcastMessage("§a§lBENEFICY §fO beneficio fullkit foi ativado.");
					player.sendMessage("§a§lBENEFICY §fO beneficio fullkit foi ativado.");
					return true;
				} else if (beneficy.equals(BeneficyType.VIPLIGHT)) {
					Benificy.create(RunningType.ACTIVED, BeneficyType.VIPLIGHT);
					Benificy.save(RunningType.ACTIVED, BeneficyType.VIPLIGHT);
					Bukkit.broadcastMessage("§a§lBENEFICY §fO beneficio vip light foi ativado.");
					player.sendMessage("§a§lBENEFICY §fO beneficio vip light foi ativado.");
					return true;
				} else if (beneficy.equals(BeneficyType.VIPPRIME)) {
					Benificy.create(RunningType.ACTIVED, BeneficyType.VIPPRIME);
					Benificy.save(RunningType.ACTIVED, BeneficyType.VIPPRIME);
					Bukkit.broadcastMessage("§a§lBENEFICY §fO beneficio vip prime foi ativado.");
					player.sendMessage("§a§lBENEFICY §fO beneficio vip prime foi ativado.");
					return true;
				} else if (beneficy.equals(BeneficyType.VIPFROST)) {
					Benificy.create(RunningType.ACTIVED, BeneficyType.VIPFROST);
					Benificy.save(RunningType.ACTIVED, BeneficyType.VIPFROST);
					Bukkit.broadcastMessage("§a§lBENEFICY §fO beneficio vip frost foi ativado.");
					player.sendMessage("§a§lBENEFICY §fO beneficio vip frost foi ativado.");
					return true;
				}
			} else if (args[1].equalsIgnoreCase("off")) {
				if (beneficy.equals(BeneficyType.DOUBLEXP)) {
					Benificy.create(RunningType.DESACTIVED, BeneficyType.DOUBLEXP);
					Benificy.save(RunningType.DESACTIVED, BeneficyType.DOUBLEXP);
					Bukkit.broadcastMessage("§a§lBENEFICY §fO beneficio doublexp foi desativado.");
					player.sendMessage("§a§lBENEFICY §fO beneficio doublexp foi desativado.");
					return true;
				} else if (beneficy.equals(BeneficyType.FULLKIT)) {
					Benificy.create(RunningType.DESACTIVED, BeneficyType.FULLKIT);
					Benificy.save(RunningType.DESACTIVED, BeneficyType.FULLKIT);
					Bukkit.broadcastMessage("§a§lBENEFICY §fO beneficio fullkit foi desativado.");
					player.sendMessage("§a§lBENEFICY §fO beneficio fullkit foi desativado.");
					return true;
				} else if (beneficy.equals(BeneficyType.VIPLIGHT)) {
					Benificy.create(RunningType.DESACTIVED, BeneficyType.VIPLIGHT);
					Benificy.save(RunningType.DESACTIVED, BeneficyType.VIPLIGHT);
					Bukkit.broadcastMessage("§a§lBENEFICY §fO beneficio vip light foi ativado.");
					player.sendMessage("§a§lBENEFICY §fO beneficio vip light foi ativado.");
					return true;
				} else if (beneficy.equals(BeneficyType.VIPPRIME)) {
					Benificy.create(RunningType.DESACTIVED, BeneficyType.VIPPRIME);
					Benificy.save(RunningType.DESACTIVED, BeneficyType.VIPPRIME);
					Bukkit.broadcastMessage("§a§lBENEFICY §fO beneficio vip prime foi desativado.");
					player.sendMessage("§a§lBENEFICY §fO beneficio vip prime foi desativado.");
					return true;
				} else if (beneficy.equals(BeneficyType.VIPFROST)) {
					Benificy.create(RunningType.DESACTIVED, BeneficyType.VIPFROST);
					Benificy.save(RunningType.DESACTIVED, BeneficyType.VIPFROST);
					Bukkit.broadcastMessage("§a§lBENEFICY §fO beneficio vip frost foi desativado.");
					player.sendMessage("§a§lBENEFICY §fO beneficio vip frost foi desativado.");
					return true;
				}
			} else {
				player.sendMessage("§3§lBENEFICY §fUtilize o comando: /beneficy (beneficy) (on/off)");
				return true;
			}
		}//
		return false;
	}

}
