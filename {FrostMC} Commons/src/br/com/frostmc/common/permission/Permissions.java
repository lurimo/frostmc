package br.com.frostmc.common.permission;

import org.bukkit.entity.*;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.enums.GroupsType;

import org.bukkit.command.*;

public class Permissions {
	
	public boolean isMembro(Player p) {
		return this.isLight(p) || p.hasPermission("frostmc.membro") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.MEMBRO);
	}
	
	public boolean isLight(Player p) {
		return this.isPrime(p) || p.hasPermission("frostmc.light") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.LIGHT);
	}
	
	public boolean isPrime(Player p) {
		return this.isFrost(p) || p.hasPermission("frostmc.prime") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.PRIME);
	}
	
	public boolean isFrost(Player p) {
		return this.isPro(p) || p.hasPermission("frostmc.frost") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.FROST);
	}

	public boolean isPro(Player p) {
		return this.isYouTuber(p) || p.hasPermission("frostmc.pro") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.PRO);
	}

	public boolean isYouTuber(Player p) {
		return this.isBuilder(p) || p.hasPermission("frostmc.youtuber") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.YOUTUBER);
	}
	
	public boolean isBuilder(Player p) {
		return this.isDesigner(p) || p.hasPermission("frostmc.builder") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.BUILDER);
	}
	
	public boolean isDesigner(Player p) {
		return this.isTrial(p) || p.hasPermission("frostmc.designer") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.DESIGNER);
	}
	
	public boolean isTrial(Player p) {
		return this.isYoutuberPlus(p) || p.hasPermission("frostmc.trial") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.TRIAL);
	}
	
	public boolean isYoutuberPlus(Player p) {
		return this.isMod(p) || p.hasPermission("frostmc.youtuberplus") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.YOUTUBERPLUS);
	}

	public boolean isMod(Player p) {
		return this.isModGC(p) || p.hasPermission("frostmc.mod") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.MOD);
	}

	public boolean isModGC(Player p) {
		return this.isModPlus(p) || p.hasPermission("frostmc.modgc") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.MODGC);
	}

	public boolean isModPlus(Player p) {
		return this.isGerente(p) || p.hasPermission("frostmc.modplus") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.MODPLUS);
	}
	
	public boolean isGerente(Player p) {
		return this.isAdmin(p) || p.hasPermission("frostmc.gerente") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.GERENTE);
	}
	
	public boolean isAdmin(Player p) {
		return this.isDiretor(p) || p.hasPermission("frostmc.admin") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.ADMIN);
	}
	
	public boolean isDiretor(Player p) {
		return this.isDeveloper(p) || p.hasPermission("frostmc.diretor") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.DIRETOR);
	}
	
	public boolean isDeveloper(Player p) {
		return this.isOwner(p) || p.hasPermission("frostmc.developer") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.CODER) || p.isOp();
	}

	public boolean isOwner(Player p) {
		return p.hasPermission("frostmc.dono") || BukkitMain.getPermissionManager().isGroup(p, GroupsType.DONO) || p.isOp();
	}
	
	public boolean isDesigner(CommandSender sender) {
		return this.isTrial(sender) || sender.hasPermission("frostmc.designer");
	}
	
	public boolean isTrial(CommandSender sender) {
		return this.isYoutuberPlus(sender) || sender.hasPermission("frostmc.trial");
	}
	
	public boolean isYoutuberPlus(CommandSender sender) {
		return this.isMod(sender) || sender.hasPermission("frostmc.youtuberplus");
	}

	public boolean isMod(CommandSender sender) {
		return this.isModGc(sender) || sender.hasPermission("frostmc.mod");
	}
	
	public boolean isModGc(CommandSender sender) {
		return this.isModPlus(sender) || sender.hasPermission("frostmc.modgc");
	}
	
	public boolean isModPlus(CommandSender sender) {
		return this.isGerente(sender) || sender.hasPermission("frostmc.modplus");
	}
	
	public boolean isGerente(CommandSender sender) {
		return this.isAdmin(sender) || sender.hasPermission("frostmc.gerente");
	}

	public boolean isAdmin(CommandSender sender) {
		return this.isDiretor(sender)|| sender.hasPermission("frostmc.admin");
	}
	
	public boolean isDiretor(CommandSender sender) {
		return this.isDeveloper(sender) || sender.hasPermission("frostmc.diretor");
	}
	
	public boolean isDeveloper(CommandSender sender) {
		return this.isDono(sender) || sender.hasPermission("frostmc.developer");
	}
	
	public boolean isDono(CommandSender sender) {
		return sender.hasPermission("*");
	}
}
