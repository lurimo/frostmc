 package br.com.frostmc.lobby.inventory.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


import br.com.frostmc.common.util.itemBuilder.ItemBuilder;

public class ServersInventory {
	
	
	public static final String title = "�8�nEscolha um modo de jogo:";
	public static final Inventory inventory = Bukkit.createInventory(null, 27, title);

	@SuppressWarnings("deprecation")
	public static void open(Player player) {
		
		
		inventory.setItem(12, new ItemBuilder().setNome("�aKit PvP").setMaterial(Material.DIAMOND_SWORD).setLore(Arrays.asList("", "�eInforma��es do modo de jogo:", "", "�fO Kit PvP � o lugar ideal para", "�faprimorar suas habilidades.", "", "�fTemos diferentes arenas e warps.", "�fVenha se tornar o melhor jogador!", "", "�eClique com o �fbot�o esquerdo/direito", "�epara conectar nesse servidor.")).finalizar());
		//inventory.setItem(12, new ItemBuilder().setNome("�aHardcore Games").setMaterial(Material.MUSHROOM_SOUP).setLore(Arrays.asList("", "�eJogadores: �f" + playersOnlineHG + "/" + playersMaxHG, "", "�eInforma��es do modo de jogo:", "", "�fColoque todo o seu treino em pratica!", "", "�fNesse modo, 80 jogadores batalham", "�fentre si at� que reste apenas um", "�fsobrevivente, que ven�a o melhor", "�fjogador.",  "", "�eClique com o �fbot�o esquerdo �epara", "�eentrar na sala com mais jogadores!", "", "�eClique com o �fbot�o direito �epara ", "�ever todas as salas dispon�veis.")).setQuantia(playersOnlineHG).finalizar());
		inventory.setItem(13, new ItemBuilder().setNome("�a1 vs 1").setMaterial(Material.BLAZE_ROD).setLore(Arrays.asList("", "�eInforma��es do modo de jogo:", "", "�fTreine suas habilidades em pvp", "�fno servidor de Um vs. Um!", "", "�fLute contra seus oponentes", "�fde uma forma certa e justa!", "", "�eClique com o �fbot�o esquerdo/direito", "�epara conectar nesse servidor.")).finalizar());
		inventory.setItem(14, new ItemBuilder().setNome("�aGladiator").setMaterial(Material.getMaterial(101)).setLore(Arrays.asList("", "�eInforma��es do modo de jogo:", "", "�fTreine suas habilidades em pvp", "�fno servidor de Gladiator!", "", "�fDesafie seus oponentes para", "�fuma batalha nos c�us!", "", "�eClique com o �fbot�o esquerdo/direito", "�epara conectar nesse servidor.")).finalizar());
		
		player.openInventory(inventory);
	}
	
}
