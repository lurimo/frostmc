package br.com.frostmc.pvp.util.combatlog;

import java.util.HashMap;
import java.util.UUID;

public class Combat {
	
	public static HashMap<UUID, CombatLog> combatLogs = new HashMap<>();
	
	public static boolean inCombat(UUID uuid) {
		if (!combatLogs.containsKey(uuid))
			return false;
		return combatLogs.get(uuid).inCombat();
	}

}
