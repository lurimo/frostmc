package br.com.frostmc.lobby.inventory.enums;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

public enum ItemsCache {
	
	SELECT_SERVER("§3Servidores - §7Clique para abrir", Material.COMPASS, Arrays.asList("§fSelecione um modo de jogo.")),
	SELECT_LOBBY("§3Lobbies - §7Clique para abrir", Material.NETHER_STAR, Arrays.asList("§fSelecione um lobby para entrar.")),
	SELECT_STORE("§3Loja - §7Clique para abrir", Material.EMERALD, Arrays.asList("§fAcesse a loja do servidor.")),
	@SuppressWarnings("deprecation")
	SELECT_MENU("§3Menu - §7Clique para abrir", Material.getMaterial(342), Arrays.asList("§fClique para abrir o menu.")),
	PLAYERS_ON("§fJogadores - §a§lON", Material.SLIME_BALL, Arrays.asList("§fClique para desativar os jogadores.")),
	PLAYERS_OFF("§fJogadores - §c§lOFF", Material.MAGMA_CREAM, Arrays.asList("§fClique para ativar os jogadores."));
	
	private String name;
	private Material material;
	private List<String> lore;
	
	private ItemsCache(String name, Material material, List<String> lore) {
		this.name = name;
		this.material = material;
		this.lore = lore;
	}
	
	public String getName() { return name; }
	public Material getMaterial() { return material; }
	public List<String> getLore() { return lore; }
	
}