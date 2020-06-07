package br.com.frostmc.common.util;

public enum ServerOptions {
	
	CHAT(true),
	BUILD(true),
	PVP(true),
	DAMAGE(true),
	EVENTO(true);
	
	private boolean active;
	
	private ServerOptions(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

}
