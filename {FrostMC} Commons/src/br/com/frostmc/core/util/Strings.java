package br.com.frostmc.core.util;

public class Strings {
	
	public static String server, prefix, permission, twitter, discord, website, webstore;
	
	static {
		server = "§b§lFROST§f§lMC";
		prefix = server + "§8» ";
		permission = "§8[§c§lERRO§8] §fVocê não possui a permissão necessária para executar esta ação.";
		twitter = "@_FrostMC";
		discord = "discord.io/frostmc";
		website = "www.redefrost.com.br";
		webstore = "loja.redefrost.com.br";
	}
	
	public static String getServer() { return server; }
	public static String getPrefix() { return prefix; }
	public static String getPermission() { return permission; }
	public static String getTwitter() { return twitter; }
	public static String getDiscord() { return discord; }
	public static String getWebsite() { return website; }
	public static String getWebstore() { return webstore; }

	public static String getServerIP(String serverName) {
		if (serverName.toLowerCase().startsWith("skywars-")) {
			return "a" + serverName.replace("SKYWARS-".toLowerCase(), "") + ".skywars.redefrost.com.br";
		}
		if (serverName.toLowerCase().startsWith("party-")) {
			return "a" + serverName.replace("PARTY-".toLowerCase(), "") + ".party.redefrost.com.br";
		}
		if (serverName.toLowerCase().startsWith("hg-")) {
			return serverName.replace("HG-".toLowerCase(), "") + ".hg.redefrost.com.br";
		}
		if (serverName.toLowerCase().startsWith("evento")) {
			return "evento.redefrost.com.br";
		}
		if (serverName.toLowerCase().startsWith("ss")) {
			return "ss.redefrost.com.br";
		}
		if (serverName.toLowerCase().startsWith("build")) {
			return "build.redefrost.com.br";
		}
		if (serverName.toLowerCase().startsWith("gladiator")) {
			return "gladiator.redefrost.com.br";
		}
		if (serverName.toLowerCase().startsWith("onevsone")) {
			return "1v1.redefrost.com.br";
		}
		if (serverName.toLowerCase().startsWith("pvp")) {
			return "pvp.redefrost.com.br";
		}
		if (serverName.toLowerCase().startsWith("lobby")) {
			return "lobby.redefrost.com.br";
		}
		if (serverName.toLowerCase().startsWith("login")) {
			return "login.redefrost.com.br";
		}
		return "Server does not match the defaults to inform the IP.";
	}
}
