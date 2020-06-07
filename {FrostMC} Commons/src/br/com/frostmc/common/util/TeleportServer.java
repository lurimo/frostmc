package br.com.frostmc.common.util;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.ServersType;

public class TeleportServer {
	
	public static void connectPlayer(Player player, ServersType serversType) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		if (serversType.equals(ServersType.ONEVSONE)) {
			out.writeUTF("1v1");
		} else {
			out.writeUTF(serversType.getName().toLowerCase());
		}
		player.sendPluginMessage(BukkitMain.getInstance(), "BungeeCord", out.toByteArray());
		player.sendMessage("§8[§c§lCONNECT§8] §fTentando conectar ao servidor §e" + Strings.getServerIP(serversType.getName().toLowerCase()));
	}

}
