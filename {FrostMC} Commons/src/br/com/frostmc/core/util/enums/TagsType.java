 package br.com.frostmc.core.util.enums;

public enum TagsType {
	
    MEMBRO(GroupsType.MEMBRO.getChatColor(), "frostmc.membro", GroupsType.MEMBRO, 1), 
    LIGHT(GroupsType.LIGHT.getChatColor(), "frostmc.light", GroupsType.LIGHT, 2),
    PRIME(GroupsType.PRIME.getChatColor(), "frostmc.prime", GroupsType.PRIME, 3),
    FROST(GroupsType.FROST.getChatColor(), "frostmc.frost", GroupsType.FROST, 4),
    PRO(GroupsType.PRO.getChatColor(), "frostmc.pro", GroupsType.PRO, 5),
    YOUTUBER(GroupsType.YOUTUBER.getChatColor(), "frostmc.youtuber", GroupsType.YOUTUBER, 6),
    DESIGNER(GroupsType.DESIGNER.getChatColor(), "frostmc.designer", GroupsType.DESIGNER, 7),
    BUILDER(GroupsType.BUILDER.getChatColor(), "frostmc.builder", GroupsType.BUILDER, 8),
    TRIAL(GroupsType.TRIAL.getChatColor(), "frostmc.trial", GroupsType.TRIAL, 9),
    YOUTUBERPLUS(GroupsType.YOUTUBERPLUS.getChatColor(), "frostmc.youtuberplus", GroupsType.YOUTUBERPLUS, 10),
    MOD(GroupsType.MOD.getChatColor(), "frostmc.mod", GroupsType.MOD, 11),
    MODGC(GroupsType.MODGC.getChatColor(), "frostmc.modgc", GroupsType.MODGC, 12),
    MODPLUS(GroupsType.MODPLUS.getChatColor(), "frostmc.modplus", GroupsType.MODPLUS, 13),
    GERENTE(GroupsType.GERENTE.getChatColor(), "frostmc.gerente", GroupsType.GERENTE, 14),
    ADMIN(GroupsType.ADMIN.getChatColor(), "frostmc.admin", GroupsType.ADMIN, 15),
    WEBCORNO("§d", "frostmc.diretor", GroupsType.DIRETOR, 16),
    BAIANO("§9", "frostmc.diretor", GroupsType.DIRETOR, 17),
    GAMER("§a", "frostmc.diretor", GroupsType.DIRETOR, 18),
    PEDREIRO("§2", "frostmc.diretor", GroupsType.DIRETOR, 19),
    DIRETOR(GroupsType.DIRETOR.getChatColor(), "frostmc.diretor", GroupsType.DIRETOR, 20),
    CODER(GroupsType.CODER.getChatColor(), "frostmc.developer", GroupsType.CODER, 21),
    DONO(GroupsType.DONO.getChatColor(), "frostmc.dono", GroupsType.DONO, 22);
	
    private String color;
    private String permissionGroup;
    private GroupsType group;
    private int order;
    
    private TagsType(String color, String permissionGroup, GroupsType group, int order) {
        this.color = color;
        this.permissionGroup = permissionGroup;
        this.group = group;
        this.order = order;
    }
    
    public String getColor() {  return this.color; }
    public String getPermissionGroup() { return permissionGroup; }
    
    public GroupsType getGroup() { 	return group; }
    public int getOrder() { return order; }
    
    public String fullName() {
    	return this.color + "§l" + name().replaceAll("plus".toUpperCase(), "+".toUpperCase()) + "§7 ";
    }
    
}
