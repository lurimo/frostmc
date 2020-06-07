package br.com.frostmc.core.data.mysql.bukkit.buy_quests.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.buy_quests.Quests;

public class LoadingQuests {
	
	public static Quests load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_quests` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int iron = resultSet.getInt("iron");
				int gold = resultSet.getInt("gold");
				int diamond = resultSet.getInt("diamond");
				int key_iron = resultSet.getInt("key_iron");
				int key_gold = resultSet.getInt("key_gold");
				int key_diamond = resultSet.getInt("key_diamond");
				Quests quests = new Quests(player, iron, gold, diamond, key_iron, key_gold, key_diamond);
				resultSet.close();
				preparedStatement.close();
				return quests;
			} else {
				Quests quests = new Quests(player);
				resultSet.close();
				preparedStatement.close();
				return quests;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações de suas caixas.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static Quests load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_quests` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int iron = resultSet.getInt("iron");
				int gold = resultSet.getInt("gold");
				int diamond = resultSet.getInt("diamond");
				int key_iron = resultSet.getInt("key_iron");
				int key_gold = resultSet.getInt("key_gold");
				int key_diamond = resultSet.getInt("key_diamond");
				Quests quests = new Quests(offlinePlayer, iron, gold, diamond, key_iron, key_gold, key_diamond);
				resultSet.close();
				preparedStatement.close();
				return quests;
			} else {
				Quests quests = new Quests(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return quests;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações de suas caixas.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static Quests load(UUID uuid) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_quests` WHERE (`uuid` = '" + uuid + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int iron = resultSet.getInt("iron");
				int gold = resultSet.getInt("gold");
				int diamond = resultSet.getInt("diamond");
				int key_iron = resultSet.getInt("key_iron");
				int key_gold = resultSet.getInt("key_gold");
				int key_diamond = resultSet.getInt("key_diamond");
				Quests quests = new Quests(uuid, iron, gold, diamond, key_iron, key_gold, key_diamond);
				resultSet.close();
				preparedStatement.close();
				return quests;
			} else {
				Quests quests = new Quests(uuid);
				resultSet.close();
				preparedStatement.close();
				return quests;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações de suas caixas.");
			exception.printStackTrace();
		}
		return null;
	}

}