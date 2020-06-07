package br.com.frostmc.core.util.logger;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageBox {

	private Boolean bungee;
	
	public void setBungee(Boolean bungee) {
		this.bungee = bungee;
	}
	
	public void box(String title, String... paragraph) {
		ArrayList<String> buffer = new ArrayList<String>();
		String at = "";
		int side1 = (int) Math.round(25 - ((title.length() + 4) / 2d));
		int side2 = (int) (26 - ((title.length() + 4) / 2d));
		at += '+';
		for (int t = 0; t < side1; t++)
			at += '-';
		at += "{ " + title + " }";
		for (int t = 0; t < side2; t++)
			at += '-';
		at += '+';
		buffer.add(at);
		at = "";
		buffer.add("|                                                   |");
		for (String s : paragraph) {
			at += "| ";
			int left = 49;
			for (int t = 0; t < s.length(); t++) {
				at += s.charAt(t);
				left--;
				if (left == 0) {
					at += " |";
					buffer.add(at);
					at = "";
					at += "| ";
					left = 49;
				}
			}
			while (left-- > 0)
				at += ' ';
			at += " |";
			buffer.add(at);
			at = "";
		}
		buffer.add("|                                                   |");
		buffer.add("+---------------------------------------------------+");
		System.out.println(" ");
		for (String line : buffer.toArray(new String[buffer.size()])) {
			System.out.println(line);
		}
		System.out.println(" ");
	}

	public void log(Object object, String plugin) {
		if (bungee) {
			ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§a" + plugin + " - " + object));
			return;
		}
		Bukkit.getServer().getConsoleSender().sendMessage("§a" + plugin + " - " + object);
	}

	public void debug(Object object, String plugin) {
		if (bungee) {
			ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§e" + plugin + " - " + object));
			return;
		}
		Bukkit.getServer().getConsoleSender().sendMessage("§e" + plugin + " - " + object);
	}

}
