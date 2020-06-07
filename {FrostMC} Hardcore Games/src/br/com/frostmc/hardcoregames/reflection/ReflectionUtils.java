package br.com.frostmc.hardcoregames.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectionUtils {
	
	public static Class<?> getNMSClass(String nmsClassString) {
	    try {
	    	String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		    String name = "net.minecraft.server." + version + nmsClassString;
		    Class<?> nmsClass = Class.forName(name);
		    return nmsClass;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}
	public static Object getConnection(Player player) {
	    try {
	    	Method getHandle = player.getClass().getMethod("getHandle");
		    Object nmsPlayer = getHandle.invoke(player);
		    Field conField = nmsPlayer.getClass().getField("playerConnection");
		    Object con = conField.get(nmsPlayer);
		    return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}

}
