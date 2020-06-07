package br.com.frostmc.common.permission.groups;

import java.util.*;

public class ModGC extends MainGroup {
	@Override
	public List<String> getPermissions() {
		final List<String> permissions = new ArrayList<String>();
		for (String str : new String[] { "enchant", "effect" }) {
		      permissions.add("minecraft.command." + str);
		      permissions.add("bukkit.command." + str);
		}
		permissions.add("frostmc.modgc");
		permissions.add("frostmc.mod");
		permissions.add("frostmc.youtuberplus");
		permissions.add("frostmc.trial");
		permissions.add("frostmc.builder");
		permissions.add("frostmc.designer");
		permissions.add("frostmc.youtuber");
		permissions.add("frostmc.pro");
		permissions.add("frostmc.frost");
		permissions.add("frostmc.beta");
		permissions.add("frostmc.prime");
		permissions.add("frostmc.light");
		permissions.add("frostmc.membro");
		return permissions;
	}
}
