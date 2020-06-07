package br.com.frostmc.pvp.util.hability;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import br.com.frostmc.core.util.enums.TagsType;

public enum Kits {
	
	NENHUM(Material.STONE_SWORD, "Nenhum", Arrays.asList("", "�9�oInforma��es:", "�fN�o possui nenhuma", "�fhabilidade.", "", "�aClique para selecionar!"), TagsType.MEMBRO, 1),
	PVP(Material.STONE_SWORD, "PvP", Arrays.asList("", "�9�oInforma��es:", "�fEsse kit n�o possui", "�fnenhuma habilidade", "�fp�rem voc� ir� pegar", "�fuma espada sharpness I.", "", "�aClique para selecionar!"), TagsType.MEMBRO, 1),
	ARCHER(Material.BOW, "Archer", Arrays.asList("", "�9�oInforma��es:", "�fReceba um arco e ", "�fflechas infinito.", "", "�aClique para selecionar!"), TagsType.MEMBRO, 1),
	KANGAROO(Material.FIREWORK, "Kangaroo", Arrays.asList("", "�9�oInforma��es:", "�fPule mais alto ", "�fcom seu foguete!", "", "�aClique para selecionar!"), TagsType.MEMBRO, 1),
	ANCHOR(Material.ANVIL, "Anchor", Arrays.asList("", "�9�oInforma��es:", "�fN�o tome e nem ", "�fd� knockback em seus", "�finimigos.", "", "�aClique para selecionar!"), TagsType.MEMBRO, 1),
	CRITICAL(Material.COAL_BLOCK, "Critical", Arrays.asList("", "�9�oInforma��es:", "�fD� golpes criticos", "�fcom facilidade.", "", "�aClique para selecionar!"), TagsType.MEMBRO, 1),
	FISHERMAN(Material.FISHING_ROD, "Fisherman", Arrays.asList("", "�9�oInforma��es:", "�fPesque seus inimigos", "�f e os traga at� voc�!", "", "�aClique para selecionar!"), TagsType.MEMBRO, 1),
	MONK(Material.BLAZE_ROD, "Monk", Arrays.asList("", "�9�oInforma��es:", "�fEmbaralhe o invent�rio ", "�fde seus inimigos!", "", "�aClique para selecionar!"), TagsType.MEMBRO, 1),
	NINJA(Material.COMPASS, "Ninja", Arrays.asList("", "�9�oInforma��es:", "�fTeleporte-se at� ", "�fseus inimigos!", "", "�aClique para selecionar!"), TagsType.MEMBRO, 1),
	PHANTOM(Material.FEATHER, "Phantom", Arrays.asList("", "�9�oInforma��es:", "�fTenha a habilidade de ", "�fvoo por 5 segundos!", "", "�aClique para selecionar!"), TagsType.LIGHT, 1),
	VIPER(Material.SPIDER_EYE, "Viper", Arrays.asList("", "�9�oInforma��es:", "�fEnvenene seus ", "�finimigos!", "", "�aClique para selecionar!"), TagsType.LIGHT, 1),
	SNAIL(Material.STRING, "Snail", Arrays.asList("", "�9�oInforma��es:", "�fDeixe seus inimigos ", "�fmais lerdos!", "", "�aClique para selecionar!"), TagsType.LIGHT, 1),
	HULK(Material.SADDLE, "Hulk", Arrays.asList("", "�9�oInforma��es:", "�fCarregue seus inimigos ", "�fnas costas!", "", "�aClique para selecionar!"), TagsType.LIGHT, 1),
	THOR(Material.WOOD_AXE, "Thor", Arrays.asList("", "�9�oInforma��es:", "�fBote fogo em seus ", "�foponentes com um", "�fraio!", "", "�aClique para selecionar!"), TagsType.LIGHT, 1),
	SWITCHER(Material.SNOW_BALL, "Switcher", Arrays.asList("", "�9�oInforma��es:", "�fTroque de lugar ", "�fcom seu oponente!", "", "�aClique para selecionar!"), TagsType.LIGHT, 1), 
	GLADIATOR(Material.IRON_FENCE, "Gladiator", Arrays.asList("", "�9�oInforma��es:", "�fConvoque seus oponentes", "�fpara uma batalha", "�fnos ares!", "", "�aClique para selecionar!"), TagsType.LIGHT, 1), 
	STOMPER(Material.IRON_BOOTS, "Stomper", Arrays.asList("", "�9�oInforma��es:", "�fPositeie seus ", "�finimigos!", "", "�aClique para selecionar!"), TagsType.LIGHT, 1), 
	MAGMA(Material.FIRE, "Magma", Arrays.asList("", "�9�oInforma��es:", "�fBote fogo em seus ", "�foponentes!", "", "�aClique para selecionar!"), TagsType.PRIME, 1), 
	GRANDPA(Material.STICK, "Grandpa", Arrays.asList("", "�9�oInforma��es:", "�fJogue seus inimigos", "�fpara longe!", "", "�aClique para selecionar!"), TagsType.PRIME, 1),
	ANTISTOMPER(Material.DIAMOND_BOOTS, "AntiStomper", Arrays.asList("", "�9�oInforma��es:", "�fN�o seja pisotiado", "�fpor stomper's.", "", "�aClique para selecionar!"), TagsType.PRIME, 1),
	TURTLE(Material.BEACON, "Turtle", Arrays.asList("", "�9�oInforma��es:", "�fN�o leve dano de", "�fqueda ao cair.", "", "�aClique para selecionar!"), TagsType.PRIME, 1),
	SPECIALIST(Material.BOOK, "Specialist", Arrays.asList("", "�9�oInforma��es:", "�fAo matar seus", "�finimigos ganhar� 1XP", "�fpara encantar seus", "�fequipamentos!", "", "�aClique para selecionar!"), TagsType.PRIME, 1);

	private Material material;
	private String name;
	private List<String> lore;
	private TagsType tagPermission;
	private int price;
	
	private Kits(Material material, String name, List<String> lore, TagsType tagPermission, int price) {
		this.material = material;
		this.name = name;
		this.lore = lore;
		this.tagPermission = tagPermission;
		this.price = price;
	}
	
	public Material getMaterial() {
		return this.material;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public TagsType getTagPermission() {
		return tagPermission;
	}
	
	public static Kits getKitName(String kitName) {
		Kits kit = Kits.PVP;
		for (Kits kits : values()) {
			if (kit.name().equals(kits.name())) {
				return kit;
			}
		}
		return null;
	}
	
	public int getPrice() {
		return price;
	}
}