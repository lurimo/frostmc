package br.com.frostmc.common.permission.groups;

import java.util.*;

public class Membro extends MainGroup {
	
	@Override
	public List<String> getPermissions() {
		final List<String> permissions = new ArrayList<String>();
		permissions.add("frostmc.membro");
		return permissions;
	}
}
