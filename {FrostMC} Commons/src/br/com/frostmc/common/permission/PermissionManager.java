package br.com.frostmc.common.permission;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import br.com.frostmc.common.BukkitCommon;
import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.permission.injector.RegExpMatcher;
import br.com.frostmc.common.permission.injector.listener.LoginListener;
import br.com.frostmc.common.permission.injector.permissible.PermissionMatcher;
import br.com.frostmc.common.permission.injector.permissible.RegexPermissions;
import br.com.frostmc.core.util.enums.GroupsType;

public class PermissionManager extends BukkitCommon {
	
	private HashMap<UUID, GroupsType> playerGroups;
	private RegexPermissions regexPerms;
	protected PermissionMatcher matcher;
	protected LoginListener superms;

	public PermissionManager(BukkitMain main) {
		super(main);
		this.matcher = new RegExpMatcher();
	}

	@Override
	public void onEnable() {
		this.superms = new LoginListener(this.getPlugin());
		this.getServer().getPluginManager().registerEvents((Listener) this.superms, (Plugin) this.getPlugin());
		this.regexPerms = new RegexPermissions(this);
		this.playerGroups = new HashMap<UUID, GroupsType>();
	}

	public boolean isGroup(Player player, GroupsType group) {
		return this.playerGroups.containsKey(player.getUniqueId()) && this.playerGroups.get(player.getUniqueId()) == group;
	}

	public boolean hasGroupPermission(Player player, GroupsType group) {
		return this.hasGroupPermission(player.getUniqueId(), group);
	}

	public boolean hasGroupPermission(UUID uuid, GroupsType group) {
		if (!this.playerGroups.containsKey(uuid)) {
			return false;
		}
		GroupsType playerGroup = this.playerGroups.get(uuid);
		return playerGroup.ordinal() >= group.ordinal();
	}

	public RegexPermissions getRegexPerms() {
		return this.regexPerms;
	}

	public PermissionMatcher getPermissionMatcher() {
		return this.matcher;
	}

	public void setPlayerGroup(Player player, GroupsType group) {
		UUID uuid = player.getUniqueId();
		this.playerGroups.put(uuid, group);
	}

	public void setPlayerGroup(OfflinePlayer player, GroupsType group) {
		UUID uuid = player.getUniqueId();
		this.playerGroups.put(uuid, group);
	}

	public void setPlayerGroup(UUID uuid, GroupsType group) {
		this.playerGroups.put(uuid, group);
	}

	public void removePlayerGroup(UUID uuid) {
		this.playerGroups.remove(uuid);
	}

	public GroupsType getPlayerGroup(Player player) {
		if (!this.playerGroups.containsKey(player.getUniqueId())) {
			return GroupsType.MEMBRO;
		}
		return this.playerGroups.get(player.getUniqueId());
	}

	public GroupsType getPlayerGroup(OfflinePlayer player) {
		if (!this.playerGroups.containsKey(player.getUniqueId())) {
			return GroupsType.MEMBRO;
		}
		return this.playerGroups.get(player.getUniqueId());
	}

	public GroupsType getPlayerGroup(UUID uuid) {
		if (!this.playerGroups.containsKey(uuid)) {
			return GroupsType.MEMBRO;
		}
		return this.playerGroups.get(uuid);
	}

	@Override
	public void onDisable() {
		if (this.regexPerms != null) {
			this.regexPerms.onDisable();
			this.regexPerms = null;
		}
		if (this.superms != null) {
			this.superms.onDisable();
			this.superms = null;
		}
		if (this.playerGroups != null) {
			this.playerGroups.clear();
		}
	}
	
}
