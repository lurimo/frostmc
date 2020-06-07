package br.com.frostmc.hardcoregames.reflection;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;

public class ActionBarMessage {

	private static int VERSION = 47;

	public static void sendActionBar(Player p, String text) {
		CraftPlayer craftplayer = (CraftPlayer) p;
		IChatBaseComponent ichatbasecomponent = ChatSerializer.a("{\"text\": \"" + text + "\"}");
		PacketPlayOutChat packetplayoutchat = new PacketPlayOutChat(ichatbasecomponent, 2);
		if (craftplayer.getHandle().playerConnection.networkManager.getVersion() != ActionBarMessage.VERSION) {
			return;
		}
		craftplayer.getHandle().playerConnection.sendPacket(packetplayoutchat);
	}

}
