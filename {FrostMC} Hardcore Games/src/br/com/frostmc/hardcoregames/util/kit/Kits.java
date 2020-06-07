package br.com.frostmc.hardcoregames.util.kit;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import br.com.frostmc.core.util.enums.TagsType;

public enum Kits {
	
	NENHUM(Material.STONE_SWORD, "Nenhum", Arrays.asList("", "�9�oInforma��es:", " �fN�o possui nenhuma", " �fhabilidade.", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
	KANGAROO(Material.FIREWORK, "Kangaroo", Arrays.asList("", "�9�oInforma��es:", " �fPule mais alto", " �fcom seu foguete!", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
	SURPRISE(Material.CAKE, "Surprise", Arrays.asList("", "�9�oInforma��es:", " �fVenha com um kit aleat�rio", " �fAo iniciar a partida!", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
	BARBARIAN(Material.WOOD_SWORD, "Barbarian", Arrays.asList("", "�9�oInforma��es:", " �fMelhore a sua espada", " �fUtilizando as almas!", " �fde seus inimigos!", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
	BOXER(Material.STONE_SWORD, "Boxer", Arrays.asList("", "�9�oInforma��es:", " �fD� mais dano com", " �fSuas m�os!", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
	MINER(Material.STONE_PICKAXE, "Miner", Arrays.asList("", "�9�oInforma��es:", " �fMinere mais rapido", " �fcom sua pickareta", " �fespecial!", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
    MONK(Material.BLAZE_ROD, "Monk", Arrays.asList("", "�9�oInforma��es:", " �fDesarme seus inimigos", " �fcom sua varinha", " �fmagica!", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
    CANNIBAL(Material.RAW_FISH, "Cannibal", Arrays.asList("", "�9�oInforma��es:", " �fVolte para a idade m�dia", " �fe deixe todos seus inimigos", " �fcom fome!", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
	NINJA(Material.EMERALD, "Ninja", Arrays.asList("", "�9�oInforma��es:", " �fTeleporte para seu", " �f�ltimo inimigo hitado", " �fApertando SHIFT!", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
    STOMPER(Material.IRON_BOOTS, "Stomper", Arrays.asList("", "�9�oInforma��es:", " �fPositeie seus inimigos!", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
    URGAL(Material.POTION, "Urgal", Arrays.asList("", "�9�oInforma��es:", " �fUse suas po��es para", " �fganhar for�a e derrotar", " �fseus inimigos mais facilmente.", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
	THOR(Material.WOOD_AXE, "Thor", Arrays.asList("", "�9�oInforma��es:", " �fBote fogo em seus", " �foponentes com um", " �fraio!", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
	GLADIATOR(Material.IRON_FENCE, "Gladiator", Arrays.asList("", "�9�oInforma��es:", " �fConvoque seus oponentes", " �fpara uma batalha", " �fnos ares!", "", "�aClique para selecionar."), TagsType.MEMBRO, 1),
    VIPER(Material.SPIDER_EYE, "Viper", Arrays.asList("", "�9�oInforma��es:", " �fEnvenene seus inimigos!", "", "�aClique para selecionar."), TagsType.LIGHT, 1), 
	SNAIL(Material.STRING, "Snail", Arrays.asList("", "�9�oInforma��es:", " �fDeixe seus inimigos", " �fmais lerdos!", "", "�aClique para selecionar."), TagsType.LIGHT, 1),
	FISHERMAN(Material.FISHING_ROD, "Fisherman", Arrays.asList("", "�9�oInforma��es:", " �fPesque seus inimigos", " �fe os traga at� voc�!", "", "�aClique para selecionar."), TagsType.LIGHT, 1),
	ACHILLES(Material.WOOD_SWORD, "Achilles", Arrays.asList("", "�9�oInforma��es:", " �fDe mais dano do que", " �fo normal.", "", "�aClique para selecionar."), TagsType.LIGHT, 1),
	CULTIVATOR(Material.SAPLING, "Cultivator", Arrays.asList("", "�9�oInforma��es:", " �fCultive plantas rapidamente.", "", "�aClique para selecionar."), TagsType.LIGHT, 1),
	ENDERMAGE(Material.NETHER_BRICK_ITEM, "Endermage", Arrays.asList("", "�9�oInforma��es:", " �fTeleport seus inimigos", " �fatraves de um portal.", "", "�aClique para selecionar."), TagsType.LIGHT, 1),
	MADMAN(Material.FERMENTED_SPIDER_EYE, "Madman", Arrays.asList("", "�9�oInforma��es:", " �fEsquente a cabe�a", " �fde seus inimigos.", "", "�aClique para selecionar."), TagsType.LIGHT, 1),
	FORGER(Material.COAL, "Forger", Arrays.asList("", "�9�oInforma��es:", " �fDerretem seus minerios", " �fapenas colocando em", " �fseu inventario.", "", "�aClique para selecionar."), TagsType.LIGHT, 1),
	GRAPPLER(Material.LEASH, "Grappler", Arrays.asList("", "�9�oInforma��es:", " �fVire um cowboy com", " �fsua corda.", "", "�aClique para selecionar."), TagsType.LIGHT, 1),
    SCOUT(Material.POTION, "Scout", Arrays.asList("", "�9�oInforma��es:", " �fGanhe po��es de velocidade", " �fe use-as para correr de seus advers�rios.", "", "�aClique para selecionar."), TagsType.LIGHT, 1),
	HULK(Material.SADDLE, "Hulk", Arrays.asList("", "�9�oInforma��es:", " �fPegue seus inimigos", " �fcom facilidade.", "", "�aClique para selecionar."), TagsType.LIGHT, 1),
	JACKHAMMER(Material.STONE_AXE, "JackHammer", Arrays.asList("", "�9�oInforma��es:", " �fCavem um buraco", " �fat� o infinito.", "", "�aClique para selecionar."), TagsType.LIGHT, 1),
	LAUNCHER(Material.SPONGE, "Launcher", Arrays.asList("", "�9�oInforma��es:", " �fPule em suas sponjas", " �fmagicas.", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	LUMBERJACK(Material.WOOD_AXE, "LumberJack", Arrays.asList("", "�9�oInforma��es:", " �fQuebrem sem se", " �fesfor�ar.", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	REAPER(Material.WOOD_HOE, "Reaper", Arrays.asList("", "�9�oInforma��es:", " �fVire o Harry Potter �", " �ftankem veneno(wither)", " �fem seus inimigos.", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	SPECIALIST(Material.BOOK, "Specialist", Arrays.asList("", "�9�oInforma��es:", " �fVire uma bruxa e", " �fenquante seus equipamentos.", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	TIMELORD(Material.WATCH, "TimeLord", Arrays.asList("", "�9�oInforma��es:", " �fCongele seus inimigos no", " �ftempo.", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	TOWER(Material.LEATHER_BOOTS, "Tower", Arrays.asList("", "�9�oInforma��es:", " �fPositeie seus inimigos", " �fcom press�o mais leve.", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	ANCHOR(Material.ANVIL, "Anchor", Arrays.asList("", "�9�oInforma��es:", " �fFique com o knockback", " �fzerado ao hitar o�inimigo", " �fe ao receber.", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	DEMOMAN(Material.STONE_PLATE, "Demoman", Arrays.asList("", "�9�oInforma��es:", " �fVire um demonio", " �fesplodindo as coisas.", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	FIREMAN(Material.WATER_BUCKET, "Fireman", Arrays.asList("", "�9�oInforma��es:", " �fN�o queime no fogo", " �fMestre da �gua.", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	TANK(Material.TNT, "Tank", Arrays.asList("", "�9�oInforma��es:", " �fTankem esplos�es.", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	TURTLE(Material.DIAMOND_CHESTPLATE, "Turtle", Arrays.asList("", "�9�oInforma��es:", " �fAo apertar shift ", " �flever�s menos dano.", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	VIKING(Material.IRON_AXE, "Viking", Arrays.asList("", "�9�oInforma��es:", " �fDesperte sua ironia ", " �fe sua inveja com a ", " �ffuria de seu machado.", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	WORM(Material.DIRT, "Worm", Arrays.asList("", "�9�oInforma��es:", " �fCavem seus buracos", " �fMuito mais rapido!", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	PHANTOM(Material.FEATHER, "Phantom", Arrays.asList("", "�9�oInforma��es:", " �fVoe como um p�ssaro", " �fpelo mapa!", "", "�aClique para selecionar."), TagsType.PRIME, 1),
	MAGMA(Material.LAVA_BUCKET, "Magma", Arrays.asList("", "�9�oInforma��es:", " �fN�o tome dano na lava", " �fe deixe seus advers�rio pegando fogo!", "", "�aClique para selecionar."), TagsType.PRIME, 1);

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
	
	public int getPrice() {
		return price;
	}
}