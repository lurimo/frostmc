package br.com.frostmc.hardcoregames.util.combatlog;

import java.util.UUID;

public class CombatLog {

	private UUID uniqueId, lastHit;
	private Long tempo, lastHitTempo;
	
	public CombatLog(UUID uniqueId, UUID lastHit) {
		this.uniqueId = uniqueId;
		this.lastHit = lastHit;
		this.lastHitTempo = System.currentTimeMillis();
		this.tempo = System.currentTimeMillis() + 5000L;
	}
	
	public UUID getUniqueId() {
		return uniqueId;
	}

	public UUID getLastHit() {
		return lastHit;
	}

	public void setLastHit(UUID lastHit) {
		this.lastHit = lastHit;
		this.tempo = System.currentTimeMillis() + 4000L;
		this.lastHitTempo = System.currentTimeMillis();
	}

	public Long getTempo() {
		return tempo;
	}
	
	public boolean inCombat() {
		return tempo > System.currentTimeMillis();
	}

	public Long getLastHitTempo() {
		return lastHitTempo;
	}
}
