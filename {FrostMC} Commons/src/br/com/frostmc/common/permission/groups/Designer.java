package br.com.frostmc.common.permission.groups;

import java.util.ArrayList;
import java.util.List;

public class Designer extends MainGroup{
	
	@Override
	public List<String> getPermissions() {
		final List<String> permissions = new ArrayList<String>();
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
