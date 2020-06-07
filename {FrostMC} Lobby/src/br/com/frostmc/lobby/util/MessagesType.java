package br.com.frostmc.lobby.util;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;

public class MessagesType {
	
	public static void sendActionBarMessage(Player p, String text) {
		CraftPlayer craftplayer = (CraftPlayer) p;
		IChatBaseComponent ichatbasecomponent = ChatSerializer.a("{\"text\": \"" + text + "\"}");
		PacketPlayOutChat packetplayoutchat = new PacketPlayOutChat(ichatbasecomponent, 2);
		if (craftplayer.getHandle().playerConnection.networkManager.getVersion() != 47) {
			return;
		}
		craftplayer.getHandle().playerConnection.sendPacket(packetplayoutchat);
	}
	
	public static void sendTitleMessage(Player player, String message, String message2) {
		if(((CraftPlayer)player).getHandle().playerConnection.networkManager.getVersion() < 45) {
			return;
		}
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.TITLE, ChatSerializer.a("{\"text\": \"\"}").a(message)));
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.SUBTITLE, ChatSerializer.a("{\"text\": \"\"}").a(message2)));
	}
	
	public static void clearTitle(Player player) {
		if(((CraftPlayer)player).getHandle().playerConnection.networkManager.getVersion() < 45) {
			return;
		}
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.CLEAR));
	}
}
