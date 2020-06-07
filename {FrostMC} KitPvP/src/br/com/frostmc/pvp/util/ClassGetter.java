package br.com.frostmc.pvp.util;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.frostmc.pvp.FrostPvP;
import br.com.frostmc.pvp.command.BaseCommand;

public class ClassGetter {
	
	public static void loadCommand() {
		for (Class<?> classes : ClassGetter.getClassesForPackage(FrostPvP.getPlugin(FrostPvP.class), "br.com.frostmc.pvp.command")) {
			try {
				if (BaseCommand.class.isAssignableFrom(classes) && classes != BaseCommand.class) {
					BaseCommand pvpCommand = (BaseCommand) classes.newInstance();
					((CraftServer) Bukkit.getServer()).getCommandMap().register(pvpCommand.getName(), pvpCommand);
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public static void loadListener() {
		for (Class<?> classes : ClassGetter.getClassesForPackage(FrostPvP.getPlugin(FrostPvP.class), "br.com.frostmc.pvp")) {
			try {
				if (Listener.class.isAssignableFrom(classes)) {
					Listener listener = (Listener) classes.newInstance();
					Bukkit.getPluginManager().registerEvents(listener, FrostPvP.getPlugin(FrostPvP.class));
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public static ArrayList<Class<?>> getClassesForPackage(final JavaPlugin plugin, final String pkgname) {
		final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		final CodeSource src = plugin.getClass().getProtectionDomain().getCodeSource();
		if (src != null) {
			final URL resource = src.getLocation();
			resource.getPath();
			processJarfile(resource, pkgname, classes);
		}
		return classes;
	}

	private static Class<?> loadClass(final String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unexpected ClassNotFoundException loading class '" + className + "'");
		} catch (NoClassDefFoundError e2) {
			return null;
		}
	}

	private static void processJarfile(final URL resource, final String pkgname, final ArrayList<Class<?>> classes) {
		final String relPath = pkgname.replace('.', '/');
		final String resPath = resource.getPath().replace("%20", " ");
		final String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
		JarFile jarFile;
		try {
			jarFile = new JarFile(jarPath);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
		}
		final Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			final JarEntry entry = entries.nextElement();
			final String entryName = entry.getName();
			String className = null;
			if (entryName.endsWith(".class") && entryName.startsWith(relPath)
					&& entryName.length() > relPath.length() + "/".length()) {
				className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
			}
			if (className != null) {
				final Class<?> c = loadClass(className);
				if (c == null) {
					continue;
				}
				classes.add(c);
			}
		}
		try {
			jarFile.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
}
