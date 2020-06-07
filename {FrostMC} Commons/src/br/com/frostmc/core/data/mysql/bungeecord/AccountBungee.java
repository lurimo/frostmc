package br.com.frostmc.core.data.mysql.bungeecord;

import br.com.frostmc.core.data.mysql.bungeecord.group.Groups;
import br.com.frostmc.core.data.mysql.bungeecord.group.loads.LoadingGroups;
import br.com.frostmc.core.data.mysql.bungeecord.punishments.Punishs;
import br.com.frostmc.core.data.mysql.bungeecord.punishments.loads.LoadingPunishs;

public class AccountBungee {
	
	public Groups groups;
	public Punishs punishs;
	public String player;
	
	public AccountBungee(String player) {
		this.player = player;
		this.groups = LoadingGroups.load(player);
		this.punishs = LoadingPunishs.load(player);
	}
	
	public String getPlayer() { return player; }
	
	public Groups getGroups() { return groups; }
	
	public Punishs getPunishs() { return punishs; }
	
}
