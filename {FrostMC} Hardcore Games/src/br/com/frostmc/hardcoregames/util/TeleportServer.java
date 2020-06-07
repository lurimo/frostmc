package br.com.frostmc.hardcoregames.util;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.hardcoregames.FrostHG;

public class TeleportServer {
	
	public static void connectPlayer(Player player, ServersType serversType) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		if (serversType.equals(ServersType.ONEVSONE)) {
			out.writeUTF("1v1");
		} else {
			out.writeUTF(serversType.getName().toLowerCase());
		}
		player.sendPluginMessage(FrostHG.getInstance(), "BungeeCord", out.toByteArray());
		player.sendMessage("§3§lCONNECT §fTentando conectar ao servidor §3" + Strings.getServerIP(serversType.getName().toLowerCase()));
	}

}
