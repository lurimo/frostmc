package br.com.frostmc.common.permission.groups;

import java.util.ArrayList;
import java.util.List;

public class Legend extends MainGroup {

	@Override
	public List<String> getPermissions() {
        final List<String> permissions = new ArrayList<String>();
		permissions.add("extrememc.legend");
		permissions.add("extrememc.light");
		permissions.add("extrememc.membro");
        return permissions;
	}

}
