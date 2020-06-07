package br.com.frostmc.bungeecord.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.frostmc.bungeecord.command.player.ReportCommand;
import br.com.frostmc.bungeecord.command.player.ReportCommand.ReportModes;
import br.com.frostmc.bungeecord.command.staff.MaintenanceCommand;
import br.com.frostmc.core.data.mysql.bungeecord.AccountBungee;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.BanType;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.TemporaryType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLoginListener implements Listener {
	
	public ArrayList<String> blacklistedAddress = new ArrayList<String>();
	
	public ArrayList<String> getBlacklistedAddress() {
		return blacklistedAddress;
	}
	
	public static ArrayList<String> checkWhitList = new ArrayList<>();
	public static HashMap<String, GroupsType> checkGroup = new HashMap<String, GroupsType>();
	public static HashMap<String, Long> checkServerPing = new HashMap<>();
	
	public static boolean validateName(String username) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9_]{1,16}");
		Matcher matcher = pattern.matcher(username);
		return matcher.matches();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void asd(LoginEvent event) {
		String player = event.getConnection().getName();
		
		String ipAddress = event.getConnection().getAddress().getHostString();
		
		AccountBungee account = new AccountBungee(player);
		
		if (!validateName(player)) {
			event.setCancelled(true);
			event.setCancelReason("�3�lCHECANDO" + "\n" + "\n" + "�fO servidor encontrou um erro em sua conta." + "\n" + "�fO seu apelido est� com caracter�sticas invalidas." + "\n" + "�fEscolha outro apelido ao iniciar o launcher." + "\n" + "\n" + "�3" + Strings.getWebsite());
			return;
		} else {
			event.setCancelled(false);
		}
		
		if (account.getPunishs().exists()) {
			if (account.getPunishs().getBanType().equals(BanType.BANNED)) {
				if (account.getPunishs().getTemporaryType().equals(TemporaryType.PERMANENT)) {
					event.setCancelled(true);
					event.setCancelReason(account.getPunishs().messageOnEntry());
					return;
				} else if (account.getPunishs().getTemporaryType().equals(TemporaryType.TEMPORARY)) {
					long expire = account.getPunishs().getExpire();
					int seconds = (int) ((expire - System.currentTimeMillis()) / 1000L);
					if (seconds > 0) {
						event.setCancelled(true);
						event.setCancelReason(account.getPunishs().messageOnEntry());
						return;
					} else {
						account.getPunishs().delete();
						event.setCancelled(false);
						return;
					}
				}
			}
		}
		for (String s : getBlacklistedAddress()) {
			if (s.equalsIgnoreCase(ipAddress)) {
				event.setCancelled(true);
				account.getPunishs().setPunishServer(account, null, BanType.BANNED, TemporaryType.PERMANENT, "Conta alternativa", 0L);
				return;
			} else {
				event.setCancelled(false);
			}
		}
		
		if (MaintenanceCommand.maintenance) {
			if (!account.getGroups().hasGroupPermission(GroupsType.TRIAL)) {
				if (!checkWhitList.contains(player)) {
					event.setCancelled(true);
					event.setCancelReason("�3�lMANUTEN��O" + "\n" + "\n" + "�fEstamos no modo �4�lMANUTEN��O�f!" + "\n" + "�fEstamos corrigindo bugs e melhorando sua jogabilidade in-game." + "\n" + "\n" + "�3" + Strings.getWebsite());
					return;
				} else {
					event.setCancelled(false);
					return;
				}
			} else {
				event.setCancelled(false);
			}
		} else {
			event.setCancelled(false);
		}
		
		if (!checkServerPing.containsKey(event.getConnection().getAddress().getHostName())) {
			event.setCancelReason("�3�lCHECANDO" + "\n" + "\n" +  "�fO servidor n�o foi adicionado em sua lista!" + "\n" + "�fAo adicionar voc� devera atualizar a sua lista." + "\n" + "�fEm seguida logue-se novamente ao servidor." + "\n" + "\n" + "�3" + Strings.getWebsite());
			event.setCancelled(true);
			return;
		} else {
			if ((checkServerPing.containsKey(event.getConnection().getAddress().getHostName())) && (System.currentTimeMillis() - ((Long) checkServerPing.get(event.getConnection().getAddress().getHostName())).longValue() < 60000L)) {
				event.setCancelled(false);
				checkServerPing.remove(event.getConnection().getAddress().getHostName());
				return;
			}
		}
		
		System.out.println("[ACCOUNT LOADED] " + player + " entrou no servidor (IP: " + ipAddress + ").");
	}
	
	@EventHandler
	public void asd(PostLoginEvent event) {
		ProxiedPlayer player = event.getPlayer();
		AccountBungee account = new AccountBungee(player.getName());
		checkGroup.put(player.getName(), account.getGroups().getGroupsType());
		System.out.println("PLAYER:" + player.getName() + " | GRUPO: " + account.getGroups().getGroupsType());
		if (account.getGroups().hasGroupPermission(GroupsType.TRIAL)) {
			ReportCommand.reportModes.put(player.getUniqueId(), ReportModes.ON);
		}
	}
	
	@EventHandler
	public void onPluginMessageEvent(PluginMessageEvent e) {
		Connection p = e.getSender();
		if (("WDL|INIT".equalsIgnoreCase(e.getTag())) && ((e.getSender() instanceof ProxiedPlayer))) {
			p.disconnect(new TextComponent("�3�lCHECANDO" + "\n" + "\n" + "�fFoi detectado um mod proibido em sua sess�o!" + "\n" + "�fO mod encontrado � �c�lWorldDownloader �ftire-o ou delete-o!" + "\n" + "\n" + "�3" + Strings.getWebsite()));
		}
		if (("PERMISSIONSREPL".equalsIgnoreCase(e.getTag())) && (new String(e.getData()).contains("mod.worlddownloader"))) {
			p.disconnect(new TextComponent("�3�lCHECANDO" + "\n" + "\n" + "�fFoi detectado um mod proibido em sua sess�o!" + "\n" + "�fO mod encontrado � �c�lWorldDownloader �ftire-o ou delete-o!" + "\n" + "\n" + "�3" + Strings.getWebsite()));
		}
	}

}
