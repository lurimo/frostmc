package br.com.frostmc.core.data.mysql.bukkit.diary.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.diary.pvp.DiaryPvP;

public class LoadingDiaryPvP {
	
	public static DiaryPvP load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_diary_pvp` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String kit = resultSet.getString("kit");
				long expire = resultSet.getLong("expire");
				DiaryPvP diaryPvP = new DiaryPvP(player, kit, expire);
				resultSet.close();
				preparedStatement.close();
				return diaryPvP;
			} else {
				DiaryPvP diaryPvP = new DiaryPvP(player);
				resultSet.close();
				preparedStatement.close();
				return diaryPvP;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do kit diario pvp.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static DiaryPvP load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_diary_pvp` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String kit = resultSet.getString("kit");
				long expire = resultSet.getLong("expire");
				DiaryPvP diaryPvP = new DiaryPvP(offlinePlayer, kit, expire);
				resultSet.close();
				preparedStatement.close();
				return diaryPvP;
			} else {
				DiaryPvP diaryPvP = new DiaryPvP(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return diaryPvP;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do kit diario pvp.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static DiaryPvP load(UUID uuid) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_diary_pvp` WHERE (`uuid` = '" + uuid + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String kit = resultSet.getString("kit");
				long expire = resultSet.getLong("expire");
				DiaryPvP diaryPvP = new DiaryPvP(uuid, kit, expire);
				resultSet.close();
				preparedStatement.close();
				return diaryPvP;
			} else {
				DiaryPvP diaryPvP = new DiaryPvP(uuid);
				resultSet.close();
				preparedStatement.close();
				return diaryPvP;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do kit diario pvp.");
			exception.printStackTrace();
		}
		return null;
	}

}
