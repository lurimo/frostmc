package br.com.frostmc.common.util.fake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.frostmc.common.util.tag.Tags;
import br.com.frostmc.core.util.enums.TagsType;

public class FakeManager {

	public static String[] random = { "zPedrinhoGames_", "iGabrielPvPz", "JoaoCesarGamer", "FlavioHG_", "FernandoGamesPvP", "JoseGame007", "Iguinho_gameplay", "AmigoDeTodos", "CombeyyZ", "EduardinhaPvP", "SpokBLATANT", "EduardinhoPvP", "CombedBy", "GabrielzinPvP", "LipinPVP" };
	
	public static ArrayList<String> fakes = new ArrayList<>();
	public static HashMap<UUID, String> fake = new HashMap<>();
	public static HashMap<String, String> fakeList = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	public static void setFake(Player player, String args) {
		fake.put(player.getUniqueId(), player.getName());
		fakeList.put(args, player.getName());
		FakePlayerUtils.respawnPlayer(player, Bukkit.getOnlinePlayers());
		FakePlayerUtils.changePlayerName(player, args);
		fakes.add(args);
		player.setCustomName(args);
		Tags.setTag(player, TagsType.MEMBRO);
	}
	
	@SuppressWarnings("deprecation")
	public static void removeFake(Player player) {
		FakePlayerUtils.respawnPlayer(player, Bukkit.getOnlinePlayers());
		fakes.remove(player.getName());
		fakeList.remove(player.getName());
		FakePlayerUtils.changePlayerName(player, fake.get(player.getUniqueId()));
		fake.remove(player.getUniqueId());
		player.setCustomName(player.getName());
		Tags.findTag(player);
	}
}
