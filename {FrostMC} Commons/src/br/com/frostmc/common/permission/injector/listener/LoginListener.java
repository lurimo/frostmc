package br.com.frostmc.common.permission.injector.listener;

import org.bukkit.scheduler.*;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.util.enums.GroupsType;

import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import org.bukkit.event.*;
import java.util.*;
import org.bukkit.permissions.*;
import org.bukkit.event.player.*;

public class LoginListener implements Listener {
	
	private BukkitMain main;
	private Map<UUID, PermissionAttachment> attachments;

	public LoginListener(BukkitMain main) {
		this.attachments = new HashMap<UUID, PermissionAttachment>();
		this.main = main;
		new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					AccountBukkit accountManager = new AccountBukkit(player);
					LoginListener.this.updateAttachment(player, accountManager.getGroups().getGroupsType());
				}
			}
		}.runTaskLater((Plugin) main, 10L);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void asd(AsyncPlayerPreLoginEvent event) {
		try {
			AccountBukkit accountManager = new AccountBukkit(event.getUniqueId());
			BukkitMain.getPermissionManager().setPlayerGroup(event.getUniqueId(), accountManager.getGroups().getGroupsType());
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao carregar", "as permissões do jogador " + event.getEventName() + ". (`players_accounts`)");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void asd(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		AccountBukkit accountManager = new AccountBukkit(player);
		LoginListener.this.updateAttachment(player, accountManager.getGroups().getGroupsType());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void asdd(PlayerLoginEvent event) {
		if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
			this.removeAttachment(event.getPlayer());
			BukkitMain.getPermissionManager().removePlayerGroup(event.getPlayer().getUniqueId());
		}
	}

	public void updateAttachment(Player player, GroupsType group) {
		PermissionAttachment attach = this.attachments.get(player.getUniqueId());
		Permission playerPerm = this.getCreateWrapper(player, player.getUniqueId().toString());
		if (attach == null) {
			attach = player.addAttachment((Plugin) this.main);
			this.attachments.put(player.getUniqueId(), attach);
			attach.setPermission(playerPerm, true);
		}
		playerPerm.getChildren().clear();
		for (String perm : group.getGroup().getPermissions()) {
			if (!playerPerm.getChildren().containsKey(perm)) {
				playerPerm.getChildren().put(perm, true);
			}
		}
		player.recalculatePermissions();
	}

	private Permission getCreateWrapper(Player player, String name) {
		Permission perm = this.main.getServer().getPluginManager().getPermission(name);
		if (perm == null) {
			perm = new Permission(name, "Permissao interna", PermissionDefault.FALSE);
			this.main.getServer().getPluginManager().addPermission(perm);
		}
		return perm;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent event) {
		this.removeAttachment(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onKick(PlayerKickEvent event) {
		this.removeAttachment(event.getPlayer());
	}

	protected void removeAttachment(Player player) {
		PermissionAttachment attach = this.attachments.remove(player.getUniqueId());
		if (attach != null) {
			attach.remove();
		}
		this.main.getServer().getPluginManager().removePermission(player.getUniqueId().toString());
	}

	public void onDisable() {
		for (PermissionAttachment attach : this.attachments.values()) {
			attach.remove();
		}
		this.attachments.clear();
	}
}
