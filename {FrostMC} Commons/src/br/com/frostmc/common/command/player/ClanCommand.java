package br.com.frostmc.common.command.player;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.util.tag.Tags;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.data.mysql.bukkit.clan.stats.StatusClan;
import br.com.frostmc.core.util.enums.GroupClanType;

public class ClanCommand extends BaseCommand {
	
	public ClanCommand() {
		super("clan");
	}

	public HashMap<UUID, String> invitedClan = new HashMap<UUID, String>();
	public HashMap<UUID, Player> invitedPlayer = new HashMap<UUID, Player>();
	
	public static boolean nameClan(String name) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9_]{1,16}");
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}
	
	public static boolean tagClan(String tag) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9_]{5}");
		Matcher matcher = pattern.matcher(tag);
		return matcher.matches();
	}
	
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			AccountBukkit accountBukkit = new AccountBukkit(player);
			if (args.length == 0) {
				if (accountBukkit.getClan().getGroupClan().getClan() == null) {
					player.sendMessage("§3§lCLAN §fUtilize o comando: /clan criar (name) (tag)");
				} else {
					if (accountBukkit.getClan().getGroupClan().hasClanTypePermission(GroupClanType.MEMBRO)) {
						player.sendMessage("§3§lCLAN §fUtilize o comando: /clan criar (name) (tag)");
						player.sendMessage("§3§lCLAN §fUtilize o comando: /clan entrar (clan)");
						player.sendMessage("§3§lCLAN §fUtilize o comando: /clan upar (player) (cargo)");
						player.sendMessage("§3§lCLAN §fUtilize o comando: /clan leave/sair");
						player.sendMessage("§3§lCLAN §fUtilize o comando: /clan convidar (player)");
						player.sendMessage("§3§lCLAN §fUtilize o comando: /clan remover (player)");
						player.sendMessage("§3§lCLAN §fUtilize o comando: /clan status");
						player.sendMessage("§3§lCLAN §fUtilize o comando: /clan status (clan)");
						player.sendMessage("§3§lCLAN §fUtilize o comando: /clan excluir");

					}
				}
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("criar")) {
					player.sendMessage("§3§lCLAN §fUtilize o comando: /clan criar (name) (tag)");
					return true;
				} else if (args[0].equalsIgnoreCase("status")) {
					if (accountBukkit.getClan().getGroupClan().getClan() == null) {
						player.sendMessage("§3§lCLAN §fVocê não está em nenhuma clan.");
						return true;
					}
					player.sendMessage("§f§l§m---------------------------");
					player.sendMessage("§aClan: §f" + accountBukkit.getClan().getGroupClan().getClan() + " §3- §f" + accountBukkit.getClan().getStatusClan().getClanTag());
					player.sendMessage("§aGrupo: " + accountBukkit.getClan().getGroupClan().getClanType().getFullName());
					player.sendMessage("");
					player.sendMessage("§aLiga: " + accountBukkit.getClan().getStatusClan().getLeagueType().fullName() + " " + accountBukkit.getClan().getStatusClan().getRankPosition());
					player.sendMessage("§aXP: §f" + NumberFormat.getInstance().format(accountBukkit.getClan().getStatusClan().getXp()));
					player.sendMessage("");
					player.sendMessage("§aVitorias: §f" + NumberFormat.getInstance().format(accountBukkit.getClan().getStatusClan().getVitorias()));
					player.sendMessage("§aDerrotas: §f" + NumberFormat.getInstance().format(accountBukkit.getClan().getStatusClan().getDerrotas()));
					player.sendMessage("");
					player.sendMessage("§aMembros:");
					accountBukkit.getClan().getGroupClan().load();
					for (String membros : accountBukkit.getClan().getGroupClan().membro) {
						player.sendMessage("§8» §f" + membros + "\n");
					}
					player.sendMessage("§f§l§m---------------------------");
					return true;
				} else if (args[0].equalsIgnoreCase("upar")) {
					if (accountBukkit.getClan().getGroupClan().getClan() == null) {
						player.sendMessage("§3§lCLAN §fVocê não está em nenhuma clan.");
						return true;
					}
					if (!accountBukkit.getClan().getGroupClan().hasClanTypePermission(GroupClanType.RECRUTER)) {
						player.sendMessage("§3§lCLAN §fVocê não tem permissão para alterar grupos.");
						return true;
					}
					player.sendMessage("§3§lCLAN §fUtilize o comando: /clan upar (player) (cargo)");
					return true;
				} else if (args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("sair")) {
					if (accountBukkit.getClan().getGroupClan().hasClanTypePermission(GroupClanType.OWNER)) {
						player.sendMessage("§3§lCLAN §fVocê é o dono desse clan, você não pode sair.");
						player.sendMessage("§3§lCLAN §fUtilize o comando: /clan excluir");
						return true;
					}
					if (accountBukkit.getClan().getGroupClan().getClan() == null) {
						player.sendMessage("§3§lCLAN §fVocê não está em nenhuma clan.");
						return true;
					}
					accountBukkit.getClan().getGroupClan().delete();
					player.sendMessage("§3§lCLAN §fVocê §c§lSAIU §fdo clan §a" + accountBukkit.getClan().getGroupClan().getClan() + " §fcom sucesso.");
					return true;
				} else if (args[0].equalsIgnoreCase("excluir")) {
					if (!accountBukkit.getClan().getGroupClan().hasClanTypePermission(GroupClanType.OWNER)) {
						player.sendMessage("§3§lCLAN §fVocê não é o criador desse clan.");
						return true;
					}
					accountBukkit.getClan().getGroupClan().delete();
					accountBukkit.getClan().getStatusClan().delete();
					player.sendMessage("§3§lCLAN §fVocê §c§lEXCLUIU §fo clan §a" + accountBukkit.getClan().getGroupClan().getClan() + " §fcom sucesso.");
					return true;
				} else if (args[0].equalsIgnoreCase("convidar")) {
					if (accountBukkit.getClan().getGroupClan().getClan() == null) {
						player.sendMessage("§3§lCLAN §fVocê não está em nenhuma clan.");
						return true;
					}
					if (!accountBukkit.getClan().getGroupClan().hasClanTypePermission(GroupClanType.RECRUTER)) {
						player.sendMessage("§3§lCLAN §fVocê não pode convidar jogadores, por que você não é recruter.");
						return true;
					}
					player.sendMessage("§3§lCLAN §fUtilize o comando: /clan convidar (player)");
					return true;
				} else if (args[0].equalsIgnoreCase("remover")) {
					if (accountBukkit.getClan().getGroupClan().getClan() == null) {
						player.sendMessage("§3§lCLAN §fVocê não está em nenhuma clan.");
						return true;
					}
					if (!accountBukkit.getClan().getGroupClan().hasClanTypePermission(GroupClanType.MODERADOR)) {
						player.sendMessage("§3§lCLAN §fVocê não pode remover jogadores, por que você não é moderador.");
						return true;
					}
					player.sendMessage("§3§lCLAN §fUtilize o comando: /clan remover (player)");
					return true;
				} else if (args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("entrar")) {
					player.sendMessage("§3§lCLAN §fUtilize o comando: /clan entrar (clan)");
					return true;
				} else {
					player.sendMessage("§3§lCLAN §fEsse argumento não foi encontrado.");
					return true;
				}
			}
			if (args.length == 2) {
				Player target = Bukkit.getPlayer(args[1]);
				AccountBukkit accountTarget = new AccountBukkit(target);
				StatusClan playerClan = accountBukkit.getClan().getStatusClan();
				StatusClan targetClan = accountTarget.getClan().getStatusClan();
				if (args[0].equalsIgnoreCase("criar")) {
					if (args[0].length() < 7) {
						player.sendMessage("§3§lCLAN §fNão pode passar de 8 digitos do nome do clan.");
						return true;
					}
					player.sendMessage("§3§lCLAN §fUtilize o comando: /clan criar " + args[1] + " (tag)");
					return true;
				} else if (args[0].equalsIgnoreCase("convidar")) {
					if (target == null) {
						player.sendMessage("§3§lCLAN §fEsse jogador encontra-se offline.");
						return true;
					}
					if (target.getName().equals(player.getName())) {
						player.sendMessage("§3§lCLAN §fNão pode convidar si mesmo.");
						return true;
					}
					if (accountBukkit.getClan().getGroupClan().getClan() == null) {
						player.sendMessage("§3§lCLAN §fVocê não está em nenhuma clan.");
						return true;
					}
					if (accountTarget.getClan().getGroupClan().getClan() != null) {
						if (accountTarget.getClan().getGroupClan().getClan().equals(accountBukkit.getClan().getGroupClan().getClan())) {
							player.sendMessage("§3§lCLAN §fO jogador " + target.getName() + " §festá nessa clan!");
						} else  {
							player.sendMessage("§3§lCLAN §fO jogador " + target.getName() + " §festá na clan §b" + accountTarget.getClan().getGroupClan().getClan());
						}
						return true;
					}
					if (accountBukkit.getClan().getGroupClan().membro.size() > 10) {
						if (!BukkitMain.getPermissions().isLight(target)) {
							player.sendMessage("§3§lCLAN §fO limite de jogadores em um clan foi atingido, somente vips podem entrar.");
							return true;
						}
					}
					if (invitedClan.containsKey(target.getUniqueId()) && invitedClan.get(target.getUniqueId()).equals(accountBukkit.getClan().getGroupClan().getClan())) {
						player.sendMessage("§3§lCLAN §fO jogador §a" + target.getName() + " §fjá foi §a§lCONVIDADO §fpara entrar no clan.");
						return true;
					}
					invitedClan.put(target.getUniqueId(), accountBukkit.getClan().getGroupClan().getClan());
					invitedPlayer.put(target.getUniqueId(), player);
					target.sendMessage("§3§lCLAN §fVocê recebeu um §a§lCONVITE §fpara entrar no clan §e§l" + accountBukkit.getClan().getGroupClan().getClan() + "§f.");
					target.sendMessage("§3§lCLAN §fUtilize o comando: /clan entrar " + accountBukkit.getClan().getGroupClan().getClan());
					player.sendMessage("§3§lCLAN §fVocê §a§lCONVIDOU §fo jogador §a" + target.getName() + " §fpara o clan com sucesso.");
					return true;
				} else if (args[0].equalsIgnoreCase("remover")) {
					if (target == null) {
						player.sendMessage("§3§lCLAN §fEsse jogador encontra-se offline.");
						return true;
					}
					if (target.getName().equals(player.getName())) {
						player.sendMessage("§3§lCLAN §fNão pode convidar si mesmo.");
						return true;
					}
					if (accountTarget.getClan().getGroupClan() == null) {
						player.sendMessage("§3§lCLAN §fEsse jogador não está no clan.");
						return true;
					}
					if (!playerClan.getClanName().equals(targetClan.getClanName())) {
						player.sendMessage("§3§lCLAN §fEsse jogador não é do clan.");
						return true;
					}
					accountTarget.getClan().getGroupClan().delete();
					target.sendMessage("§3§lCLAN §fVocê foi kickado do clan §e§l" + accountBukkit.getClan().getGroupClan().getClan() + "§f.");
					player.sendMessage("§3§lCLAN §fVocê kickou o jogador §a" + target.getName() + " §fdo clan com sucesso.");
					Tags.updateTag(target);
					return true;
				} else if (args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("entrar")) {
					if (accountBukkit.getClan().getGroupClan().getClan() != null) {
						player.sendMessage("§3§lCLAN §fVocê já está em uma clan.");
						return true;
					}
					if (!invitedClan.containsKey(player.getUniqueId())) {
						player.sendMessage("§3§lCLAN §fVocê não foi convidado para §c§lNENHUM §fclan.");
						return true;
					}
					if (invitedClan.get(player.getUniqueId()).equals(args[1])) {
						invitedClan.remove(player.getUniqueId());
						accountBukkit.getClan().getGroupClan().put(GroupClanType.MEMBRO, args[1]);
						invitedPlayer.get(player.getUniqueId()).sendMessage("§a§lCLAN §fO jogador " + target.getName() + " §a§lENTROU §fno clan!");
						player.sendMessage("§3§lCLAN §fVocê §a§lENTROU §fno clan §e§l" + args[1] + " §fseja bem-vindo(a).");
						Tags.updateTag(player);
						return true;
					} else {
						player.sendMessage("§3§lCLAN §fVocê não foi §c§lCONVIDADO §fpara entrar nesse clan.");
						return true;
					}
				}
				return true;
			}
			if (args.length == 3) {
				Player target = Bukkit.getPlayer(args[1]);
				if (args[0].equalsIgnoreCase("criar")) {
					if (args[2].length() < 4) {
						player.sendMessage("§3§lCLAN §fNão pode passar de 5 digitos do nome da tag do clan.");
						return true;
					}
					if (accountBukkit.getClan().getGroupClan().getClan() != null) {
						player.sendMessage("§3§lCLAN §fVocê já está em uma clan.");
						if (!accountBukkit.getClan().getGroupClan().hasClanType(GroupClanType.OWNER)) {
							player.sendMessage("§3§lCLAN §fPara criar outra, você precisa excluir esse clan.");
							player.sendMessage("§3§lCLAN §fUtilize o comando: /clan excluir");
							return true;
						} else {
							player.sendMessage("§3§lCLAN §fPara criar um clan, você precisa sair desse clan.");
							player.sendMessage("§3§lCLAN §fUtilize o comando: /clan sair");
							return true;
						}
					}
					if (accountBukkit.getClan().getStatusClan().exists("nameClan", args[1])) {
						player.sendMessage("§3§lCLAN §fO nome dessa clan já é existente.");
						return true;
					}
					if (accountBukkit.getClan().getStatusClan().exists("tagClan", args[2])) {
						player.sendMessage("§3§lCLAN §fA tag dessa clan já é existente.");
						return true;
					}
					accountBukkit.getClan().getStatusClan().put(args[1], args[2]);
					accountBukkit.getClan().getGroupClan().put(GroupClanType.OWNER, args[1]);
					player.sendMessage("§3§lCLAN §fVocê criou o clan " + args[1] + " com sucesso.");
					Tags.updateTag(player);
					return true;
				} else if (args[0].equalsIgnoreCase("upar")) {
					GroupClanType clanType = GroupClanType.MEMBRO;
					try {
						clanType = GroupClanType.valueOf(args[2].toUpperCase());
					} catch (Exception exception) {
						sendError(player, "Esse cargo não foi encontrado.");
						return true;
					}
					AccountBukkit accountTarget = new AccountBukkit(target);
					if (target == null) {
						player.sendMessage("§3§lCLAN §fEsse jogador encontra-se offline.");
						return true;
					}
					if (target.getName().equals(player.getName())) {
						player.sendMessage("§3§lCLAN §fNão pode convidar si mesmo.");
						return true;
					}
					if (accountTarget.getClan().getGroupClan().hasClanType(clanType)) {
						player.sendMessage("§3§lCLAN §fEsse jogador já está com o grupo " + clanType.getFullName());
						return true;
					}
					if (accountTarget.getClan().getGroupClan().hasClanType(accountBukkit.getClan().getGroupClan().getClanType())) {
						if (!accountTarget.getClan().getStatusClan().getOwner().equals(player.getName())) {
							player.sendMessage("§3§lCLAN §fVocê não pode alterar o grupo desse jogador.");
							return true;
						}
					}
					accountTarget.getClan().getGroupClan().setClanType(clanType);
					accountTarget.getClan().getGroupClan().save();
					target.sendMessage("§3§lCLAN §fVocê foi §a§lPROMOVIDO §fpara " + clanType.getFullName() + "§f!");
					player.sendMessage("§3§lCLAN §fVocê §a§lPROMOVEU§f o jogador §a" + target.getName() + " §fpara " + clanType.getFullName() + " §fcom sucesso.");
					return true;
				}
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
