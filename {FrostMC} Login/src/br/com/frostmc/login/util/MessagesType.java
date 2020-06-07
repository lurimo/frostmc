package br.com.frostmc.login.util;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector;

import br.com.frostmc.core.util.enums.AuthType;
import br.com.frostmc.login.FrostLogin;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;

public class MessagesType {
	
	public static String subtitle = "";
	
	public static int integerSubTitle = 0;
	
	public static String nextSubTitle(Player player) {
		integerSubTitle++;
		if (FrostLogin.checkToLogin.get(player.getUniqueId()).equals(AuthType.NONLOGGED)) {
			if (integerSubTitle == 0) {
				subtitle = "§7/l_";
			}
			if (integerSubTitle == 1) {
				subtitle = "§7/lo_";
			}
			if (integerSubTitle == 2) {
				subtitle = "§7/log_";
			}
			if (integerSubTitle == 3) {
				subtitle = "§7/logi_";
			}
			if (integerSubTitle == 4) {
				subtitle = "§7/login_";
			}
			if (integerSubTitle == 5) {
				subtitle = "§7/login (_";
			}
			if (integerSubTitle == 6) {
				subtitle = "§7/login (s_";
			}
			if (integerSubTitle == 7) {
				subtitle = "§7/login (se_";
			}
			if (integerSubTitle == 8) {
				subtitle = "§7/login (sen_";
			}
			if (integerSubTitle == 9) {
				subtitle = "§7/login (senh_";
			}
			if (integerSubTitle == 10) {
				subtitle = "§7/login (senha_";
			}
			if (integerSubTitle == 11) {
				subtitle = "§7/login (senha)";
			}
			if (integerSubTitle == 14) {
				integerSubTitle = 0;
			}
		} else if (FrostLogin.checkToLogin.get(player.getUniqueId()).equals(AuthType.NONREGISTERED)) {
			if (integerSubTitle == 0) {
				subtitle = "§7/r_";
			}
			if (integerSubTitle == 1) {
				subtitle = "§7/re_";
			}
			if (integerSubTitle == 2) {
				subtitle = "§7/reg_";
			}
			if (integerSubTitle == 3) {
				subtitle = "§7/regi_";
			}
			if (integerSubTitle == 4) {
				subtitle = "§7/regis_";
			}
			if (integerSubTitle == 5) {
				subtitle = "§7/regist_";
			}
			if (integerSubTitle == 6) {
				subtitle = "§7/registe_";
			}
			if (integerSubTitle == 7) {
				subtitle = "§7/register_";
			}
			if (integerSubTitle == 8) {
				subtitle = "§7/register (_";
			}
			if (integerSubTitle == 9) {
				subtitle = "§7/register (s_";
			}
			if (integerSubTitle == 10) {
				subtitle = "§7/register (se_";
			}
			if (integerSubTitle == 11) {
				subtitle = "§7/register (sen_";
			}
			if (integerSubTitle == 12) {
				subtitle = "§7/register (senh_";
			}
			if (integerSubTitle == 13) {
				subtitle = "§7/register (senha_";
			}
			if (integerSubTitle == 14) {
				subtitle = "§7/register (senha)_";
			}
			if (integerSubTitle == 15) {
				subtitle = "§7/register (senha) (c_";
			}
			if (integerSubTitle == 16) {
				subtitle = "§7/register (senha) (co_";
			}
			if (integerSubTitle == 17) {
				subtitle = "§7/register (senha) (con_";
			}
			if (integerSubTitle == 18) {
				subtitle = "§7/register (senha) (conf_";
			}
			if (integerSubTitle == 19) {
				subtitle = "§7/register (senha) (confi_";
			}
			if (integerSubTitle == 20) {
				subtitle = "§7/register (senha) (confir_";
			}
			if (integerSubTitle == 21) {
				subtitle = "§7/register (senha) (confirm_";
			}
			if (integerSubTitle == 22) {
				subtitle = "§7/register (senha) (confirma_";
			}
			if (integerSubTitle == 23) {
				subtitle = "§7/register (senha) (confirmar_";
			}
			if (integerSubTitle == 24) {
				subtitle = "§7/register (senha) (confirmar s_";
			}
			if (integerSubTitle == 25) {
				subtitle = "§7/register (senha) (confirmar se_";
			}
			if (integerSubTitle == 26) {
				subtitle = "§7/register (senha) (confirmar sen_";
			}
			if (integerSubTitle == 27) {
				subtitle = "§7/register (senha) (confirmar senh_";
			}
			if (integerSubTitle == 28) {
				subtitle = "§7/register (senha) (confirmar senha_";
			}
			if (integerSubTitle == 29) {
				subtitle = "§7/register (senha) (confirmar senha)";
				integerSubTitle = 0;
			}
		}
		return subtitle;
	}
	
	public static String getSubtitle() {
		return subtitle;
	}
	
	public static void sendActionBarMessage(Player p, String text) {
		CraftPlayer craftplayer = (CraftPlayer) p;
		IChatBaseComponent ichatbasecomponent = ChatSerializer.a("{\"text\": \"" + text + "\"}");
		PacketPlayOutChat packetplayoutchat = new PacketPlayOutChat(ichatbasecomponent, 2);
		if (craftplayer.getHandle().playerConnection.networkManager.getVersion() != 47) {
			return;
		}
		craftplayer.getHandle().playerConnection.sendPacket(packetplayoutchat);
	}
	
	public static void sendTitleMessage(Player player, String message) {
		if(((CraftPlayer)player).getHandle().playerConnection.networkManager.getVersion() < 45) {
			return;
		}
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.TITLE, ChatSerializer.a("{\"text\": \"\"}").a(message)));
	}
	
	public static void sendSubTitleMessage(Player player, String message) {
		if(((CraftPlayer)player).getHandle().playerConnection.networkManager.getVersion() < 45) {
			return;
		}
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.SUBTITLE, ChatSerializer.a("{\"text\": \"\"}").a(message)));
	}
	
	public static void clearTitle(Player player) {
		if(((CraftPlayer)player).getHandle().playerConnection.networkManager.getVersion() < 45) {
			return;
		}
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.CLEAR));
	}
}
