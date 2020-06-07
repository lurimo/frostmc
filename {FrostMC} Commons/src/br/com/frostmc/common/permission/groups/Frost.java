package br.com.frostmc.common.permission.groups;

import java.util.ArrayList;
import java.util.List;

public class Frost extends MainGroup{

	@Override
    public List<String> getPermissions() {
        final List<String> permissions = new ArrayList<String>();
		permissions.add("frostmc.frost");
		permissions.add("frostmc.prime");
		permissions.add("frostmc.light");
		permissions.add("frostmc.membro");
        return permissions;
    }
}
