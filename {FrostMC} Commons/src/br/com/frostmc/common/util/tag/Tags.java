package br.com.frostmc.common.util.tag;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.json.JSONChatClickEventType;
import br.com.frostmc.common.json.JSONChatExtra;
import br.com.frostmc.common.json.JSONChatHoverEventType;
import br.com.frostmc.common.json.JSONChatMessage;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.util.enums.LeagueType;
import br.com.frostmc.core.util.enums.TagsType;

public class Tags implements Listener{
	
	public static HashMap<Player, TagsType> tags = new HashMap<>();
	
	public static boolean changeTag;
	
	public static TagsType getTag(Player player) {
		return tags.get(player);
	}
	
	public static TagsType removeTags(Player player) {
		return tags.remove(player);
	}
	
	public static void findTag(Player player) {
		ArrayList<TagsType> list = new ArrayList<>();
		for(TagsType tags : TagsType.values()) {
			if(hasTagPermission(player, tags)) {
				list.add(tags);
			}
		}
		setTag(player, list.get(list.size() - 1));
	}
	
	public static void updateTag(Player player) {
		setTag(player, tags.get(player));
	}
	
	public static boolean hasTagPermission(Player player, TagsType tag) {
		return player.hasPermission(tag.getPermissionGroup());
	}
	
	public boolean usingTag(Player player) {
		return tags.containsKey(player);
	}
	
	public static void setTag(Player player, TagsType tag) {
		tags.put(player, tag);
		AccountBukkit account = new AccountBukkit(player);
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		NameTagEdit.setNameTag(player.getName(), "A" + ((1000 - tag.getOrder())) + (15 + gamer.getLeagueType().getOrder()), (tag.equals(TagsType.MEMBRO) ? "§7" : (changeTag == true ? tag.getColor() : tag.fullName())), " §7[" + gamer.getLeagueType().getColor() + gamer.getLeagueType().getSymbol() + "§7]");
		player.setDisplayName((account.getClan().getGroupClan().getClan() != null ? "§7[" + account.getClan().getStatusClan().getLeagueType().getColor() + account.getClan().getStatusClan().getClanTag() + "§7]§r " : "") + (tag.equals(TagsType.MEMBRO) ? TagsType.MEMBRO.getColor() : (changeTag == true ? tag.getColor() : tag.fullName())) + player.getName() + " §7[" + gamer.getLeagueType().getColor() + gamer.getLeagueType().getSymbol() + "§7]");
	}
	
	public static JSONChatExtra create(TagsType tag, Player player) {
		JSONChatExtra extra = new JSONChatExtra("§f, " + tag.getColor() + "§l" + tag.name().replaceAll("plus".toUpperCase(), "+".toUpperCase()));
		extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, String.valueOf(tags.get(player).equals(tag) ? "§cVocê está utilizando está tag!" : "§fExemplo: " + (changeTag == true ? tag.getColor() : tag.fullName()) + player.getName() + "\n" + ChatColor.GREEN + "§nClique para selecionar!"));
		extra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/tag " + tag.name().toLowerCase());
		return extra;
	}

	public static void sendTags(Player player) {
		JSONChatMessage message = new JSONChatMessage("§3§lTAGS: ", null, null);
		JSONChatExtra membro = new JSONChatExtra(TagsType.MEMBRO.getColor() + "§l" + TagsType.MEMBRO.name());
		membro.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, ChatColor.WHITE + "Exemplo: " + ChatColor.GRAY + player.getName() + "\n" + ChatColor.GREEN + "§nClique para selecionar!");
		membro.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/tag membro");
		message.addExtra(membro);
		TagsType[] arrayOfTags;
		int a = (arrayOfTags = TagsType.values()).length;
		int b = (arrayOfTags = TagsType.values()).length;
		for (a = 0; a < b; a++) {
			TagsType tags = arrayOfTags[a];
			if (!tags.equals(TagsType.MEMBRO) && hasTagPermission(player, tags)) {
				JSONChatExtra extra = create(tags, player);
				message.addExtra(extra);
			}
		}
		JSONChatExtra ponto = new JSONChatExtra("§f.");
		message.addExtra(ponto);
		message.sendToPlayer(player.getPlayer());
	}
	
	public static JSONChatExtra createLiga(LeagueType ligasType, Player player) {
		AccountBukkit account = new AccountBukkit(player);
		JSONChatExtra extra = new JSONChatExtra(", " + String.valueOf(account.getStatsGlobal().getLeagueType().equals(ligasType) ? "§c" + ligasType.name() : ligasType.getColor() + ligasType.name()));
		extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, String.valueOf(account.getStatsGlobal().getLeagueType().equals(ligasType) ? "§cVocê está nessa liga!" : "§fSymbol: " + ligasType.getColor() + ligasType.getSymbol() + "\n" + "§fXP minimo: §e" + ligasType.getXp()));
		return extra;
	}
	
}
