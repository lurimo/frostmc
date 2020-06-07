package br.com.frostmc.pvp.inventory.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;

public class StoreKitsInventory {
	
public static String titleStoreKits;
	
	static {
		titleStoreKits = "�8�nCompre uma habilidade";
	}
	
	public static HashMap<Inventory, String> paginaAtual = new HashMap<>();
	public static HashMap<Inventory, List<String>> pagina1 = new HashMap<>(), pagina2 = new HashMap<>(), pagina3 = new HashMap<>(), kitsAdicionados = new HashMap<>();

	@SuppressWarnings("deprecation")
	public static void abrirMenu(Player p) {
		AccountBukkit account = new AccountBukkit(p);
		Inventory inv = p.getServer().createInventory(null, 9 * 6, titleStoreKits);
		paginaAtual.put(inv, "1");
		pagina1.put(inv, new ArrayList<>());
		pagina2.put(inv, new ArrayList<>());
		pagina3.put(inv, new ArrayList<>());
		kitsAdicionados.put(inv, new ArrayList<>());
		inv.setItem(3, new ItemBuilder().setMaterial(Material.getMaterial(351)).setNome("�7Seus kits").setDurabilidade(8).finalizar());
		inv.setItem(4, new ItemBuilder().setMaterial(Material.NETHER_STAR).setNome("�9Informa��o:").setLore(Arrays.asList("", "�fEscolha uma habilidade para", "�fcomprar com suas fichas.", "", "�fOBS: �a�nO pre�o de todos os", "�a�nkits s�o 1 FICHA.", "", "�fFichas: �e" + account.getStatsGlobal().getFichas())).finalizar());
		inv.setItem(5, new ItemBuilder().setMaterial(Material.getMaterial(351)).setNome("�aLoja de kits").setDurabilidade(10).finalizar());
		inv.setItem(27, new ItemBuilder().setMaterial(Material.ARROW).setNome("�cNenhuma p�gina").glow().finalizar());
		inv.setItem(35, new ItemBuilder().setMaterial(Material.ARROW).setNome("�cNenhuma p�gina").glow().finalizar());
		
		addKits(p, inv);

		p.openInventory(inv);
	}

	public static void paginaAnterior(Player p, Inventory inv) {
		int id = 16, pagina = Integer.valueOf(paginaAtual.get(inv));
		paginaAtual.put(inv, String.valueOf(pagina - 1));
		inv.setItem(27, new ItemBuilder().setMaterial(Material.ARROW).setNome("�cNenhuma p�gina").glow().finalizar());
		ArrayList<Integer> idsSetados = new ArrayList<>();
		List<String> kitsPaginaAnterior = new ArrayList<>();
		if (pagina == 2) {
			kitsAdicionados.get(inv).clear();
			kitsPaginaAnterior = pagina1.get(inv);
			pagina2.get(inv).clear();
			inv.setItem(35, new ItemBuilder().setMaterial(Material.ARROW).setNome("�aPr�xima p�gina").glow().finalizar());
		} else if (pagina == 3) {
			for (String kits : pagina3.get(inv))
				kitsAdicionados.get(inv).remove(kits);
			kitsPaginaAnterior = pagina2.get(inv);
			pagina3.get(inv).clear();
			inv.setItem(35, new ItemBuilder().setMaterial(Material.ARROW).setNome("�aPr�xima p�gina").glow().finalizar());
		} else if (pagina == 4) {
			kitsPaginaAnterior = pagina3.get(inv);
		}

		for (String kit : kitsPaginaAnterior) {
			if (id > 42)
				break;

			if (id == 16)
				id = 20;

			if (id == 25)
				id = 29;
			
			if (id == 34)
				id = 38;
			if (!Kits.valueOf(Kits.valueOf(kit.toUpperCase()).name()).equals(Kits.NENHUM) && !KitAPI.hasKitPermission(p, Kits.valueOf(Kits.valueOf(kit.toUpperCase()).name()))) {
				inv.setItem(id, new ItemBuilder().setNome("�c�l" + Kits.valueOf(kit.toUpperCase()).getName()).setMaterial(Kits.valueOf(kit.toUpperCase()).getMaterial()).setLore(Kits.valueOf(kit.toUpperCase()).getLore()).finalizar());
			}
			idsSetados.add(id);
			if (paginaAtual.get(inv).equals("1")) {
				kitsAdicionados.get(inv).add(kit);
			}
			id++;
		}

		if (idsSetados.contains(42))
			return;

		for (int i = 10; i <= 42; i++) {
			if (id == 16)
				id = 20;

			if (id == 25)
				id = 29;
			
			if (id == 34)
				id = 38;
			if (!idsSetados.contains(i))
				inv.setItem(i, null);
		}
	}

	public static void addKits(Player p, Inventory inv) {
		int id = 16;
		ArrayList<String> names = new ArrayList<>();
		for (Kits kits : Kits.values()) {
			if (!names.contains(kits.name())) {
				names.add(kits.name());
			}
		}
		Collections.sort(names);
		for (int i = 0; i < names.size(); i++) {
			for (Kits list : Kits.values()) {
				if (((String) names.get(i)).equalsIgnoreCase(list.getName())) {
					if (!list.equals(Kits.NENHUM) && !KitAPI.hasKitPermission(p, Kits.valueOf(list.name()))) {
						if (id > 42)
							break;

						if (id == 16)
							id = 20;

						if (id == 25)
							id = 29;
						
						if (id == 34)
							id = 38;
						inv.setItem(id, new ItemBuilder().setNome("�c�l" + list.getName()).setMaterial(list.getMaterial()).setLore(list.getLore()).finalizar());
						
						pagina1.get(inv).add(list.getName());
						kitsAdicionados.get(inv).add(list.getName());
						id++;
					}
				}
			}
		}
		inv.setItem(27, new ItemBuilder().setMaterial(Material.ARROW).setNome("�cNenhuma p�gina").glow().finalizar());
		if (id > 42)
			inv.setItem(35, new ItemBuilder().setMaterial(Material.ARROW).setNome("�aPr�xima p�gina").glow().finalizar());
		if (id == 16) {
			inv.setItem(31, new ItemBuilder().setMaterial(Material.REDSTONE).setNome("�cVoc� possu� todos os Kits").finalizar());
		}
	}

	public static void proximaPagina(Player p, Inventory inv) {
		paginaAtual.put(inv, String.valueOf(Integer.valueOf(paginaAtual.get(inv)) + 1));
		int pagina = Integer.valueOf(paginaAtual.get(inv));
		inv.setItem(27, new ItemBuilder().setMaterial(Material.ARROW).setNome("�cP�gina anterior").glow().finalizar());
		int id = 16;
		inv.setItem(35, new ItemBuilder().setMaterial(Material.ARROW).setNome("�cNenhuma p�gina").finalizar());
		ArrayList<Integer> idsSetados = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		for (Kits kits : Kits.values()) {
			if (!names.contains(kits.name())) {
				names.add(kits.name());
			}
		}
		Collections.sort(names);
		for (int totalDeKits = 0; totalDeKits < names.size(); totalDeKits++) {
			for (Kits list : Kits.values()) {
				if (((String) names.get(totalDeKits)).equalsIgnoreCase(list.getName())) {
					if (!list.equals(Kits.NENHUM) && !KitAPI.hasKitPermission(p, list)) {
						 if (!kitsAdicionados.get(inv).contains(list.getName())) {
							 if (id > 42)
									break;

								if (id == 16)
									id = 20;

								if (id == 25)
									id = 29;
								
								if (id == 34)
									id = 38;
								inv.setItem(id, new ItemBuilder().setNome("�c�l" + list.getName()).setMaterial(list.getMaterial()).setLore(list.getLore()).finalizar());
								kitsAdicionados.get(inv).add(list.getName());
								if (pagina == 2) {
									pagina2.get(inv).add(list.getName());
								} else if (pagina == 3) {
									pagina3.get(inv).add(list.getName());
								}
								idsSetados.add(id);
								id++;
						 }
					}
				}
			}
		}
		if (idsSetados.contains(42))
			return;

		for (int i = 10; i <= 42; i++) {
			if (id > 42)
				break;

			if (id == 16)
				id = 20;

			if (id == 25)
				id = 29;
			
			if (id == 34)
				id = 38;

			if (!idsSetados.contains(i))
				inv.setItem(i, null);
		}
		if (idsSetados.contains(42))
			return;

		for (int i = 10; i <= 42; i++) {
			if (id > 42)
				break;

			if (id == 16)
				id = 20;

			if (id == 25)
				id = 29;
			
			if (id == 34)
				id = 38;

			if (!idsSetados.contains(i))
				inv.setItem(i, null);
		}
		inv.setItem(27, new ItemBuilder().setMaterial(Material.ARROW).setNome("�cP�gina anterior").glow().finalizar());
		inv.setItem(35, new ItemBuilder().setMaterial(Material.ARROW).setNome("�cNenhuma p�gina").glow().finalizar());
		if (id > 42) {
			inv.setItem(35, new ItemBuilder().setMaterial(Material.ARROW).setNome("�aPr�xima p�gina").glow().finalizar());
		}
	}

	@EventHandler
	public static void onClose(InventoryCloseEvent e) {
		Inventory inv = e.getInventory();
		if (paginaAtual.containsKey(inv))
			paginaAtual.remove(inv);

		if (pagina1.containsKey(inv))
			pagina1.remove(inv);

		if (pagina2.containsKey(inv))
			pagina2.remove(inv);

		if (pagina3.containsKey(inv))
			pagina3.remove(inv);

		if (kitsAdicionados.containsKey(inv))
			kitsAdicionados.remove(inv);
	}

}
