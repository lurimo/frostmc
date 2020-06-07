package br.com.frostmc.bungeecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import br.com.frostmc.bungeecord.listener.*;
import br.com.frostmc.bungeecord.util.ClassLoader;
import br.com.frostmc.core.CoreMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.AuthType;
import br.com.frostmc.core.util.enums.GroupsType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMain extends Plugin {
	
	private static BungeeMain instance;
	public static BungeeMain getInstance() { return instance; }
	
	private static Plugin plugin;
	public static Plugin getPlugin() { return plugin; }

	public static CoreMain core;
	public static CoreMain getCore() { return core; }
	
	public static HashMap<UUID, AuthType> checkToLogin = new HashMap<UUID, AuthType>();
	private AtomicInteger index = new AtomicInteger(0);
	private static final List<String> messages = new ArrayList<>();
	
	@Override
	public void onEnable() {
		instance = this; plugin = this;
		
		core = new CoreMain();
		core.mysql.start();
		core.mysql.setup();
		core.box.setBungee(true);
		
		new ClassLoader().initialize();
		
		messages.add("�fTem interesse em fazer parte de nossa �e�lEQUIPE�f? �fAplique-se aqui: �b" + Strings.getWebsite() + "/comunidade/aplicacoes");
		messages.add("�fSiga-nos em nosso twitter �3�l" + Strings.getTwitter() + "�fe fique por dentro de todas as novidades.");
		messages.add("�fQuer participar de nosso �9�lDISCORD�f? Acesse: �e�9" + Strings.getDiscord());
		messages.add("�fDeseja fazer parte de nosso �6�lHack-Report-Chat�f? E ainda concorrer a pr�mios? Entre j�: �3�n" + Strings.getDiscord());
		messages.add("�fEncontrou um jogador �c�lSUSPEITO�r�f? �fUtilize o comando: /report (jogador) (motivo)");
		getPlugin().getProxy().getScheduler().schedule(getPlugin(), new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				if (index.get() >= messages.size()) {
					index = new AtomicInteger(0);
				}
				for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
					player.sendMessage("");
					player.sendMessage("�b�lFROST�f�lMC " + messages.get(index.get()));
					player.sendMessage("");
				}
				index.incrementAndGet();
			}
		}, 3, 3, TimeUnit.MINUTES);
		
		core.getBox().box("[FrostMC - Proxy]", "O plugin foi habilitado com sucesso.");
	}

	@Override
	public void onDisable() {
		instance = null; plugin = null;
		
		core.mysql.close();
		
		core.getBox().box("[FrostMC - Proxy]", "O plugin foi desabilitado com sucesso.");
	}
	
	public boolean hasGroupPermission(ProxiedPlayer player, GroupsType group) {
		if (!PlayerLoginListener.checkGroup.containsKey(player.getName())) {
			return false;
		}
		GroupsType playerGroup = PlayerLoginListener.checkGroup.get(player.getName());
		return playerGroup.ordinal() >= group.ordinal();
	}
	
}
