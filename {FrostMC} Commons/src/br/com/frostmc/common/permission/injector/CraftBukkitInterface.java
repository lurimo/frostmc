package br.com.frostmc.common.permission.injector;

import org.bukkit.*;

public class CraftBukkitInterface {
	
    private static final String VERSION;
    
    static {
        final Class<?> serverClass = Bukkit.getServer().getClass();
        if (!serverClass.getSimpleName().equals("CraftServer")) {
            VERSION = null;
        }
        else if (serverClass.getName().equals("org.bukkit.craftbukkit.CraftServer")) {
            VERSION = ".";
        }
        else {
            String name = serverClass.getName();
            name = name.substring("org.bukkit.craftbukkit".length());
            name = (VERSION = name.substring(0, name.length() - "CraftServer".length()));
        }
    }
    
    public static String getCBClassName(final String simpleName) {
        if (CraftBukkitInterface.VERSION == null) {
            return null;
        }
        return "org.bukkit.craftbukkit" + CraftBukkitInterface.VERSION + simpleName;
    }
    
    public static Class<?> getCBClass(final String name) {
        if (CraftBukkitInterface.VERSION == null) {
            return null;
        }
        try {
            return Class.forName(getCBClassName(name));
        }
        catch (ClassNotFoundException ex) {
            return null;
        }
    }
    
}
