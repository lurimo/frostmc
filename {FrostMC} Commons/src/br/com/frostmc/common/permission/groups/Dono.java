package br.com.frostmc.common.permission.groups;

import java.util.*;

public class Dono extends MainGroup {

	@Override
	public List<String> getPermissions() {
		final List<String> permissions = new ArrayList<String>();
		permissions.add("*");
		permissions.add("litebans.*");
		return permissions;
	}

}
