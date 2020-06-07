package br.com.frostmc.common.command.player;

import java.text.NumberFormat;
import java.util.Arrays;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.util.fake.FakeManager;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.util.Longs;
import br.com.frostmc.core.util.enums.BanType;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.LeagueType;
import br.com.frostmc.core.util.enums.RunningType;
import br.com.frostmc.core.util.enums.TemporaryType;

public class AccountCommand extends BaseCommand {

	public AccountCommand() {
		super("account", "visualize a sua conta", Arrays.asList("acc", ""));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			 Player player = getPlayer(commandSender);
			 AccountBukkit account = new AccountBukkit(player);
			 if (args.length == 0) {
				 player.sendMessage("§f§l§m---------------------------");
				 player.sendMessage("§aConfira suas informações abaixo:");
				 player.sendMessage("§aUUID: §f" + player.getUniqueId());
				 player.sendMessage("§f§l§m---------------------------");
				 player.sendMessage("§aGrupo: " + BukkitMain.getPermissionManager().getPlayerGroup(player).fullName() + "§aexpira em §e" + String.valueOf(account.getGroups().getTemporaryType().equals(TemporaryType.PERMANENT) ? "Nunca" : Longs.messageSmall(account.getGroups().getExpire())) + "§a.");
				 player.sendMessage("§aSetado por: §f" + account.getGroups().getSetBy());
				 player.sendMessage("");
				 player.sendMessage("§aRegistro:");
				 player.sendMessage(" §f- Senha: §7" + account.getAuthentication().getPassword());
				 if (BukkitMain.getPermissions().isTrial(player)) {
					 player.sendMessage(" §f- Codigo de segurança: §7" + account.getAuthentication().getSecurityCode());
				 }
				 player.sendMessage(" §f- Primeiro login: §7" + account.getGroups().getFirstLogin());
				 player.sendMessage(" §f- Último login: §7" + account.getGroups().getLastLogin());
				 player.sendMessage("");
				 player.sendMessage("§aLiga: " + account.getStatsGlobal().getLeagueType().fullName());
				 player.sendMessage("§aXP: §f" + NumberFormat.getInstance().format(account.getStatsGlobal().getXp()));
				 player.sendMessage("");
				 player.sendMessage("§aMoedas: §f" + NumberFormat.getInstance().format(account.getStatsGlobal().getMoedas()));
				 player.sendMessage("§aFichas: §f" + NumberFormat.getInstance().format(account.getStatsGlobal().getFichas()));
				 if (account.getDiaryPvP().exists()) {
					 player.sendMessage("");
					 player.sendMessage("§aKit diario(PVP): §f" + account.getDiaryPvP().getKit());
					 player.sendMessage("§aIrá expirar em: §f" + Longs.messageSmall(account.getDiaryPvP().getExpire()));
				 }
				 if (account.getDiaryHG().exists()) {
					 player.sendMessage("");
					 player.sendMessage("§aKit diario(HG): §f" + account.getDiaryHG().getKit());
					 player.sendMessage("§aIrá expirar em: §f" + Longs.messageSmall(account.getDiaryHG().getExpire()));
				 }
				 if (account.getDoubleXP().getRunningType().equals(RunningType.ACTIVED)) {
					 player.sendMessage("");
					 player.sendMessage("§aSeu doublexp irá expirar em " + Longs.messageSmall(account.getDoubleXP().getExpire()));
				 }
				 if (account.getBlacklist().exists()) {
					 player.sendMessage("");
					 if (account.getBlacklist().getTemporaryType().equals(TemporaryType.PERMANENT)) {
						 player.sendMessage("§aVocê está impossibilitado de entrar em eventos permanentemente" + (BukkitMain.getPermissions().isTrial(player) ? " pelo staffer: " + account.getBlacklist().getStaffer() : "") + ".");
						 player.sendMessage("§aExpira em Nunca.");
					 } else if (account.getBlacklist().getTemporaryType().equals(TemporaryType.TEMPORARY)) {
						 player.sendMessage("§aVocê está impossibilitado de entrar em eventos temporariamente" + (BukkitMain.getPermissions().isTrial(player) ? " pelo staffer: " + account.getBlacklist().getStaffer() : "") + ". (blacklist)");
						 player.sendMessage("§aExpira em " + Longs.messageSmall(account.getBlacklist().getExpire()));
					 }
				 }
				 if (account.getPunishments().exists()) {
					 player.sendMessage("");
					 if (account.getPunishments().getBanType().equals(BanType.BANNED)) {
						 if (account.getPunishments().getTemporaryType().equals(TemporaryType.PERMANENT)) {
							 player.sendMessage("§aVocê está permanentemente do servidor" + (BukkitMain.getPermissions().isTrial(player) ? " por: " + account.getPunishments().getStaffer() : "") + ".");
						 } else if (account.getPunishments().getTemporaryType().equals(TemporaryType.TEMPORARY)) {
							 player.sendMessage("§aVocê está banido temporariamente do servidor" + (BukkitMain.getPermissions().isTrial(player) ? " por: " + account.getPunishments().getStaffer() : "") + ". Expira em " + Longs.messageSmall(account.getPunishments().getExpire()));
						 }
					 } else if (account.getPunishments().getBanType().equals(BanType.MUTATED)) {
						 if (account.getPunishments().getTemporaryType().equals(TemporaryType.PERMANENT)) {
							 player.sendMessage("§aVocê está mutado permanentemente do servidor" + (BukkitMain.getPermissions().isTrial(player) ? " por: " + account.getPunishments().getStaffer() : "") + ".");
						 } else if (account.getPunishments().getTemporaryType().equals(TemporaryType.TEMPORARY)) {
							 player.sendMessage("§aVocê está mutado temporariamente do servidor" + (BukkitMain.getPermissions().isTrial(player) ? " por: " + account.getPunishments().getStaffer() : "") + ". Expira em " + Longs.messageSmall(account.getPunishments().getExpire()));
						 }
					 }
				 }
				 player.sendMessage("§f§l§m---------------------------");
				 return true;
			 }
			 if (args.length == 1) {
				 Player target = getPlayer(args[0]);
				 if (FakeManager.fakes.contains(args[0])) {
					 player.sendMessage("§f§l§m---------------------------");
					 player.sendMessage("§aConfira as informações dê " + args[0] + ":");
					 player.sendMessage("§aUUID: §f" + player.getUniqueId());
					 player.sendMessage("§f§l§m---------------------------");
					 player.sendMessage("§aGrupo: " + GroupsType.MEMBRO.fullName() + "§aexpira em §eNunca§a.");
					 player.sendMessage("§aSetado por: §fCONSOLE");
					 player.sendMessage("");
					 player.sendMessage("§aLiga: " + LeagueType.PRIMARIO.fullName());
					 player.sendMessage("§aXP: §f0");
					 player.sendMessage("");
					 player.sendMessage("§aMoedas: §f0");
					 player.sendMessage("§aFichas: §f0");
					 player.sendMessage("§f§l§m---------------------------");
					 return true;
				 }
				 if (!isOnline(target)) {
					 OfflinePlayer targetoff = getOfflinePlayer(args[0]);
					 AccountBukkit accountoff = new AccountBukkit(targetoff);
					 if (!accountoff.getStatsGlobal().exists()) {
						 player.sendMessage("§3§lACCOUNT §fEsse jogador não foi encontrado em nosso banco de dados.");
						 return true;
					 }
					 player.sendMessage("§f§l§m---------------------------");
					 player.sendMessage("§aConfira as informações dê " + targetoff.getName() + ":");
					 player.sendMessage("§aUUID: §f" + targetoff.getUniqueId());
					 player.sendMessage("§f§l§m---------------------------");
					 player.sendMessage("§aGrupo: " + BukkitMain.getPermissionManager().getPlayerGroup(targetoff).fullName() + "§aexpira em §e" + String.valueOf(accountoff.getGroups().getTemporaryType().equals(TemporaryType.PERMANENT) ? "Nunca" : Longs.messageSmall(accountoff.getGroups().getExpire())) + "§a.");
					 if (BukkitMain.getPermissions().isModPlus(player)) {
						 player.sendMessage("§aSetado por: §f" + accountoff.getGroups().getSetBy());
					 }
					 player.sendMessage("");
					 player.sendMessage("§aLiga: " + accountoff.getStatsGlobal().getLeagueType().fullName());
					 player.sendMessage("§aXP: §f" + NumberFormat.getInstance().format(accountoff.getStatsGlobal().getXp()));
					 player.sendMessage("");
					 player.sendMessage("§aMoedas: §f" + NumberFormat.getInstance().format(accountoff.getStatsGlobal().getMoedas()));
					 player.sendMessage("§aFichas: §f" + NumberFormat.getInstance().format(accountoff.getStatsGlobal().getFichas()));
					 if (accountoff.getDoubleXP().getRunningType().equals(RunningType.ACTIVED)) {
						 player.sendMessage("");
						 player.sendMessage("§aDoublexp irá expirar em " + Longs.messageSmall(accountoff.getDoubleXP().getExpire()));
					 }
					 if (accountoff.getBlacklist().exists()) {
						 player.sendMessage("");
						 if (accountoff.getBlacklist().getTemporaryType().equals(TemporaryType.PERMANENT)) {
							 player.sendMessage("§aJogador impossibilitado de entrar em eventos permanentemente" + (BukkitMain.getPermissions().isTrial(player) ? " pelo staffer: " + accountoff.getBlacklist().getStaffer() : "") + ". (blacklist)");
							 player.sendMessage("§aExpira em Nunca.");
						 } else if (accountoff.getBlacklist().getTemporaryType().equals(TemporaryType.TEMPORARY)) {
							 player.sendMessage("§aJogador impossibilitado de entrar em eventos temporariamente" + (BukkitMain.getPermissions().isTrial(player) ? " pelo staffer: " + accountoff.getBlacklist().getStaffer() : "") + ". (blacklist)");
							 player.sendMessage("§aExpira em " + Longs.messageSmall(accountoff.getBlacklist().getExpire()));
						 }
					 }
					 if (accountoff.getPunishments().exists()) {
						 player.sendMessage("");
						 if (accountoff.getPunishments().getBanType().equals(BanType.BANNED)) {
							 if (accountoff.getPunishments().getTemporaryType().equals(TemporaryType.PERMANENT)) {
								 player.sendMessage("§aJogador banido permanentemente do servidor" + (BukkitMain.getPermissions().isTrial(player) ? " por: " + accountoff.getPunishments().getStaffer() : "") + ".");
							 } else if (accountoff.getPunishments().getTemporaryType().equals(TemporaryType.TEMPORARY)) {
								 player.sendMessage("§aJogador banido temporariamente do servidor" + (BukkitMain.getPermissions().isTrial(player) ? " por: " + accountoff.getPunishments().getStaffer() : "") + ". Expira em " + Longs.messageSmall(accountoff.getPunishments().getExpire()));
							 }
						 } else if (accountoff.getPunishments().getBanType().equals(BanType.MUTATED)) {
							 if (accountoff.getPunishments().getTemporaryType().equals(TemporaryType.PERMANENT)) {
								 player.sendMessage("§aJogador mutado permanentemente do servidor" + (BukkitMain.getPermissions().isTrial(player) ? " por: " + accountoff.getPunishments().getStaffer() : "") + ".");
							 } else if (accountoff.getPunishments().getTemporaryType().equals(TemporaryType.TEMPORARY)) {
								 player.sendMessage("§aJogador mutado temporariamente do servidor" + (BukkitMain.getPermissions().isTrial(player) ? " por: " + accountoff.getPunishments().getStaffer() : "") + ". Expira em " + Longs.messageSmall(accountoff.getPunishments().getExpire()));
							 }
						 }
					 }
					 player.sendMessage("§f§l§m---------------------------");
					 return true;
				 }
				 AccountBukkit accounton = new AccountBukkit(target);
				 player.sendMessage("§f§l§m---------------------------");
				 player.sendMessage("§aConfira as informações dê " + target.getName() + ":");
				 player.sendMessage("§aUUID: §f" + target.getUniqueId());
				 player.sendMessage("§f§l§m---------------------------");
				 player.sendMessage("§aGrupo: " + BukkitMain.getPermissionManager().getPlayerGroup(target).fullName() + "§aexpira em §e" + String.valueOf(accounton.getGroups().getTemporaryType().equals(TemporaryType.PERMANENT) ? "Nunca" : Longs.messageSmall(accounton.getGroups().getExpire())) + "§a.");
				 if (BukkitMain.getPermissions().isModPlus(player)) {
					 player.sendMessage("§aSetado por: §f" + accounton.getGroups().getSetBy());
				 }
				 player.sendMessage("");
				 player.sendMessage("§aLiga: " + accounton.getStatsGlobal().getLeagueType().fullName());
				 player.sendMessage("§aXP: §f" + NumberFormat.getInstance().format(accounton.getStatsGlobal().getXp()));
				 player.sendMessage("");
				 player.sendMessage("§aMoedas: §f" + NumberFormat.getInstance().format(accounton.getStatsGlobal().getMoedas()));
				 player.sendMessage("§aFichas: §f" + NumberFormat.getInstance().format(accounton.getStatsGlobal().getFichas()));
				 if (accounton.getDoubleXP().getRunningType().equals(RunningType.ACTIVED)) {
					 player.sendMessage("");
					 player.sendMessage("§aDoublexp irá expirar em " + Longs.messageSmall(accounton.getDoubleXP().getExpire()));
				 }
				 if (accounton.getBlacklist().exists()) {
					 player.sendMessage("");
					 if (accounton.getBlacklist().getTemporaryType().equals(TemporaryType.PERMANENT)) {
						 player.sendMessage("§aJogador impossibilitado de entrar em eventos permanentemente" + (BukkitMain.getPermissions().isTrial(player) ? " pelo staffer: " + accounton.getBlacklist().getStaffer() : "") + ". (blacklist)");
						 player.sendMessage("§aExpira em Nunca.");
					 } else if (accounton.getBlacklist().getTemporaryType().equals(TemporaryType.TEMPORARY)) {
						 player.sendMessage("§aJogador impossibilitado de entrar em eventos temporariamente" + (BukkitMain.getPermissions().isTrial(player) ? " pelo staffer: " + accounton.getBlacklist().getStaffer() : "") + ". (blacklist)");
						 player.sendMessage("§aExpira em " + Longs.messageSmall(accounton.getBlacklist().getExpire()));
					 }
				 }
				 if (accounton.getPunishments().exists()) {
					 player.sendMessage("");
					 if (accounton.getPunishments().getBanType().equals(BanType.BANNED)) {
						 if (accounton.getPunishments().getTemporaryType().equals(TemporaryType.PERMANENT)) {
							 player.sendMessage("§aJogador banido permanentemente do servidor" + (BukkitMain.getPermissions().isTrial(player) ? " por: " + accounton.getPunishments().getStaffer() : "") + ".");
						 } else if (accounton.getPunishments().getTemporaryType().equals(TemporaryType.TEMPORARY)) {
							 player.sendMessage("§aJogador banido temporariamente do servidor" + (BukkitMain.getPermissions().isTrial(player) ? " por: " + accounton.getPunishments().getStaffer() : "") + ". Expira em " + Longs.messageSmall(accounton.getPunishments().getExpire()));
						 }
					 } else if (accounton.getPunishments().getBanType().equals(BanType.MUTATED)) {
						 if (accounton.getPunishments().getTemporaryType().equals(TemporaryType.PERMANENT)) {
							 player.sendMessage("§aJogador mutado permanentemente do servidor" + (BukkitMain.getPermissions().isTrial(player) ? " por: " + accounton.getPunishments().getStaffer() : "") + ".");
						 } else if (accounton.getPunishments().getTemporaryType().equals(TemporaryType.TEMPORARY)) {
							 player.sendMessage("§aJogador mutado temporariamente do servidor" + (BukkitMain.getPermissions().isTrial(player) ? " por: " + accounton.getPunishments().getStaffer() : "") + ". Expira em " + Longs.messageSmall(accounton.getPunishments().getExpire()));
						 }
					 }
				 }
				 player.sendMessage("§f§l§m---------------------------");
				 return true;
			 }
		} else {
			return true;
		}
		return false;
	}
}