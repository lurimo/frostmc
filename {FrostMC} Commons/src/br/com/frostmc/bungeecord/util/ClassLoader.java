package br.com.frostmc.bungeecord.util;

import br.com.frostmc.bungeecord.BungeeMain;
import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.core.util.loader.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;

public class ClassLoader {

	public boolean initialize() {

		for (Class<?> classes : Getter.getClassesForPackage(BungeeMain.getPlugin(), "br.com.frostmc.bungeecord")) {
			try {
				if (ProxyCommand.class.isAssignableFrom(classes) && classes != ProxyCommand.class) {
					ProxyCommand command = (ProxyCommand) classes.newInstance();
					ProxyServer.getInstance().getPluginManager().registerCommand(BungeeMain.getPlugin(), command);
				}
			} catch (Exception exception) {
				BungeeMain.getCore().getBox().box("[FrostMC - Proxy]", "Erro ao carregar o comando " + classes.getSimpleName() + "!" + exception);
				return false;
			}

			try {
				Listener listener = null;
				if (Listener.class.isAssignableFrom(classes)) {
					listener = (Listener) classes.newInstance();
				}
				if (listener == null)
					continue;
				ProxyServer.getInstance().getPluginManager().registerListener(BungeeMain.getPlugin(), listener);

			} catch (Exception exception) {
				BungeeMain.getCore().getBox().box("[FrostMC - Proxy]", "Erro ao carregar a Listener " + classes.getSimpleName() + "!" + exception);
				return false;
			}
		}
		return true;
	}

}
