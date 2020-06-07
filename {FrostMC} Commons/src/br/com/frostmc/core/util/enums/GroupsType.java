package br.com.frostmc.core.util.enums;

import br.com.frostmc.common.permission.groups.Admin;
import br.com.frostmc.common.permission.groups.Builder;
import br.com.frostmc.common.permission.groups.Designer;
import br.com.frostmc.common.permission.groups.Developer;
import br.com.frostmc.common.permission.groups.Diretor;
import br.com.frostmc.common.permission.groups.Dono;
import br.com.frostmc.common.permission.groups.Frost;
import br.com.frostmc.common.permission.groups.Gerente;
import br.com.frostmc.common.permission.groups.Light;
import br.com.frostmc.common.permission.groups.MainGroup;
import br.com.frostmc.common.permission.groups.Membro;
import br.com.frostmc.common.permission.groups.Mod;
import br.com.frostmc.common.permission.groups.ModGC;
import br.com.frostmc.common.permission.groups.ModPlus;
import br.com.frostmc.common.permission.groups.Prime;
import br.com.frostmc.common.permission.groups.Pro;
import br.com.frostmc.common.permission.groups.Trial;
import br.com.frostmc.common.permission.groups.Youtuber;
import br.com.frostmc.common.permission.groups.Youtuberplus;

public enum GroupsType {

	MEMBRO("§7", new Membro(), true),
    LIGHT("§a", new Light(), true),
    PRIME("§3", new Prime(), true),
    FROST("§b", new Frost(), true),
    PRO("§6", new Pro(), true),
    YOUTUBER("§b", new Youtuber(), true),
    DESIGNER("§2", new Designer(), true),
    BUILDER("§e", new Builder(), true),
    TRIAL("§5", new Trial(), true), 
    YOUTUBERPLUS("§3", new Youtuberplus(), true),
    MOD("§5", new Mod(), true),
    MODGC("§5", new ModGC(), true),
    MODPLUS("§5", new ModPlus(), true),
    GERENTE("§c", new Gerente(), true),
    ADMIN("§c", new Admin(), true),
    DIRETOR("§4", new Diretor(), true),
    CODER("§3", new Developer(), true),
    DONO("§4", new Dono(), true);
    
    private MainGroup group;
    private String chatColor;
    private boolean enabled;
    
    private GroupsType(String chatColor, MainGroup group, boolean enabled) {
        this.chatColor = chatColor;
    	this.group = group;
    	this.enabled = enabled;
    }
    
    public MainGroup getGroup() {
        return this.group;
    }
    
    public String getChatColor() {
		return chatColor;
	}
    
    public boolean isEnabled() {
		return enabled;
	}
    
    public String fullName() {
    	return chatColor + "§l" + name().replace("plus".toUpperCase(), "+".toUpperCase()) + " §7";
    }

}
