package br.com.frostmc.common.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.server.PluginDisableEvent;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.ServerOptions;
import br.com.frostmc.common.util.event.SchedulerEvent;
import br.com.frostmc.common.util.event.SchedulerEvent.SchedulerType;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.RunningType;
import br.com.frostmc.core.util.enums.TemporaryType;

public class OtherListener implements Listener {
	
	public int tempo = 0;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onUpdate(SchedulerEvent event) {
		if (event.getCurrentTick() % 2 > 0)
			return;
		if (Bukkit.getOnlinePlayers().length <= 0)
			return;
		if (event.getType() == SchedulerType.MINUTE) {
			tempo++;
			if (tempo == 30) {
				System.gc();
				for (Player player : Bukkit.getOnlinePlayers()) {
					AccountBukkit account = new AccountBukkit(player);
					Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
					if (account.getDiaryPvP().exists()) {
						long expire = account.getDiaryPvP().getExpire();
						int seconds = (int) ((expire - System.currentTimeMillis()) / 1000L);
						if (seconds < 0) {
							account.getPlayer().sendMessage("§8[§c§lDIARY§8] §fO kit §c§l" + account.getDiaryPvP().getKit().toLowerCase() + "§f(PVP) expirou!");
							account.getDiaryPvP().delete();
						}
					}
					if (account.getDiaryHG().exists()) {
						long expire = account.getDiaryHG().getExpire();
						int seconds = (int) ((expire - System.currentTimeMillis()) / 1000L);
						if (seconds < 0) {
							account.getPlayer().sendMessage("§8[§c§lDIARY§8] §fO kit diario §c§l" + account.getDiaryHG().getKit().toLowerCase() + "§f(HG) expirou!");
							account.getDiaryHG().delete();
						}
					}
					if (account.getGroups().getTemporaryType().equals(TemporaryType.TEMPORARY)) {
						long expire = account.getGroups().getExpire();
						int seconds = (int) ((expire - System.currentTimeMillis()) / 1000L);
						if (seconds < 0) {
							account.getGroups().setSetBy("CONSOLE");
							account.getGroups().setGroupsType(GroupsType.MEMBRO);
							account.getGroups().setTemporaryType(TemporaryType.PERMANENT);
							account.getGroups().setExpire(0L);
							account.getGroups().save();
							account.getPlayer().kickPlayer(account.getGroups().messageOnKick());
						}
					}
					if (account.getDoubleXP().exists()) {
						if (account.getDoubleXP().getRunningType().equals(RunningType.ACTIVED)) {
							long expire = account.getDoubleXP().getExpire();
							int seconds = (int) ((expire - System.currentTimeMillis()) / 1000L);
							if (seconds < 0) {
								account.getPlayer().sendMessage("§8[§6§lDOUBLEXP§8] §fO seu pacote de 1 hora acabou!");
								account.getDoubleXP().setRunningType(RunningType.DESACTIVED);
								account.getDoubleXP().setExpire(0L);
								account.getDoubleXP().save();
								gamer.setDoublexp(false);
							}
						}
					}
					if (account.getBlacklist().exists()) {
						if (account.getBlacklist().getTemporaryType().equals(TemporaryType.TEMPORARY)) {
							long expire = account.getBlacklist().getExpire();
							int seconds = (int) ((expire - System.currentTimeMillis()) / 1000L);
							if (seconds < 0) {
								account.getPlayer().sendMessage("§a§lBLACKLIST §fO seu tempo de blacklist foi expirado, volte a jogar e não quebre mais nenhuma regra!");
								account.getBlacklist().unBlacklist();
							}
						}
					}
					if (account.getClan().getStatusClan() == null) {
						account.getClan().getGroupClan().delete();
					}
					
					gamer.update();
					if (BukkitMain.getPermissions().isAdmin(player)) {
						player.sendMessage("§8[§6§lSYSTEM§8] §7Os dados de todos os jogadores foram salvos.");
						return;
					}
				}
				tempo = 0;
			}
		}
	}
	
	@EventHandler
	public void asd(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player))
	         return;
		if (event.isCancelled())
			return;
		if (!ServerOptions.PVP.isActive()) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void asd(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player)) {
			if ((event.getCause().equals(EntityDamageEvent.DamageCause.FALL))) {
				if (!ServerOptions.DAMAGE.isActive()) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		if (event.getLeaveMessage().toLowerCase().contains("you logged in from another location")) {
			event.setCancelled(true);
		} else if (event.getLeaveMessage().toLowerCase().contains("flying is not enabled on this server")) {
			event.setCancelled(true);
		} else if (event.getLeaveMessage().toLowerCase().contains("You dropped your items too quickly (Hacking?)")) {
			event.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void asd(PluginDisableEvent event) {
		for (Player players : Bukkit.getOnlinePlayers()) {
			Gamer gamer = BukkitMain.getGamerManager().getGamer(players);

			if (gamer != null) {
				BukkitMain.getGamerManager().update(gamer);
			}
		}
	}

}
