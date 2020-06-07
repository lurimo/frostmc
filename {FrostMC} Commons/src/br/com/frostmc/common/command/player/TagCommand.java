package br.com.frostmc.common.command.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.util.tag.Tags;
import br.com.frostmc.core.util.enums.TagsType;

public class TagCommand extends BaseCommand {

	public TagCommand() {
		super("tag", "selecione uma tag", Arrays.asList("tags", "nametag", "tagname"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = (Player) commandSender;
			if (args.length == 0) {
				Tags.sendTags(player);
				return true;
			}
			if (args.length == 1) {
				TagsType tags = TagsType.MEMBRO;
				try {
					if (args[0].toUpperCase().equalsIgnoreCase("youtuber+")) {
						tags = TagsType.YOUTUBERPLUS;
					} else if (args[0].toUpperCase().equalsIgnoreCase("mod+")) {
						tags = TagsType.MODPLUS;
					} else {
						tags = TagsType.valueOf(args[0].toUpperCase());
					}
				} catch (Exception exception) {
					player.sendMessage("§8[§3§lTAG§8] §fNossa rede não possui a tag citada.");
					return true;
				}
				if(!Tags.tags.containsKey(player.getPlayer())) {
					Tags.setTag(player, TagsType.MEMBRO);
				}
				if(Tags.tags.get(player.getPlayer()).equals(tags)) {
					player.sendMessage("§8[§c§lERRO§8] §fVocê já está utilizando essa tag.");
					return true;
				}
				if (!Tags.hasTagPermission(player, tags)) {
					player.sendMessage("§8[§c§lERRO§8] §fVocê não possui permissão para aplicar esta tag.");
					return true;
				}
				Tags.setTag(player, tags);
				player.sendMessage("§8[§3§lTAG§8] §fA tag " + tags.getColor() + "§l" + tags.name().replaceAll("plus".toUpperCase(), "+".toUpperCase()) + " §ffoi selecionada.");
				return true;
			}
			if (args.length == 2) {
				if (BukkitMain.getPermissions().isDiretor(player)) {
					if (args[0].equalsIgnoreCase("change")) {
						if (args[1].equalsIgnoreCase("on")) {
							if (Tags.changeTag == true) {
								player.sendMessage("§8[§3§lTAG§8] §fO formato da tag já está costumizada!");
								return true;
							}
							Tags.changeTag = true;
							for (Player players : Bukkit.getOnlinePlayers()) {
								Tags.updateTag(players);
							}
							player.sendMessage("§8[§3§lTAG§8] §fO formato de tag foi mudado para o modo costumizado.");
							return true;
						} else if (args[1].equalsIgnoreCase("off")) {
							if (Tags.changeTag == false) {
								player.sendMessage("§8[§3§lTAG§8] §fO formato da tag já está costumizada!");
								return true;
							}
							Tags.changeTag = false;
							for (Player players : Bukkit.getOnlinePlayers()) {
								Tags.updateTag(players);
							}
							player.sendMessage("§8[§3§lTAG§8] §fO formato de tag foi mudado para o modo normal.");
							return true;
						}
					}
				}
			}
			if (!BukkitMain.getPermissions().isDiretor(player)) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
