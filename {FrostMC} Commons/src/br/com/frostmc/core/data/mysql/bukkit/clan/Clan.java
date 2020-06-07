package br.com.frostmc.core.data.mysql.bukkit.clan;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.core.data.mysql.bukkit.clan.group.GroupClan;
import br.com.frostmc.core.data.mysql.bukkit.clan.loads.LoadGroupClan;
import br.com.frostmc.core.data.mysql.bukkit.clan.loads.LoadStatusClan;
import br.com.frostmc.core.data.mysql.bukkit.clan.stats.StatusClan;

public class Clan {
	
	public GroupClan groupClan;
	public StatusClan statusClan;
	
	public Clan(Player player) {
		this.groupClan = LoadGroupClan.load(player);
		this.statusClan = LoadStatusClan.load(this.groupClan.getClan());
	}
	
	public Clan(OfflinePlayer offlinePlayer) {
		this.groupClan = LoadGroupClan.load(offlinePlayer);
		this.statusClan = LoadStatusClan.load(this.groupClan.getClan());
	}
	
	public GroupClan getGroupClan() {
		return groupClan;
	}
	
	public StatusClan getStatusClan() {
		return statusClan;
	}

}
