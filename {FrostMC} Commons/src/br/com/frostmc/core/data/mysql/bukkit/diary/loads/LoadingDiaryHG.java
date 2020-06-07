package br.com.frostmc.core.data.mysql.bukkit.diary.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.diary.hg.DiaryHG;

public class LoadingDiaryHG {
	
	public static DiaryHG load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_diary_hg` WHERE (`uuid` = '" + player.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String kit = resultSet.getString("kit");
				long expire = resultSet.getLong("expire");
				DiaryHG DiaryHG = new DiaryHG(player, kit, expire);
				resultSet.close();
				preparedStatement.close();
				return DiaryHG;
			} else {
				DiaryHG DiaryHG = new DiaryHG(player);
				resultSet.close();
				preparedStatement.close();
				return DiaryHG;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do kit diario hg.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static DiaryHG load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_diary_hg` WHERE (`uuid` = '" + offlinePlayer.getUniqueId() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String kit = resultSet.getString("kit");
				long expire = resultSet.getLong("expire");
				DiaryHG DiaryHG = new DiaryHG(offlinePlayer, kit, expire);
				resultSet.close();
				preparedStatement.close();
				return DiaryHG;
			} else {
				DiaryHG DiaryHG = new DiaryHG(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return DiaryHG;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do kit diario hg.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static DiaryHG load(UUID uuid) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_diary_hg` WHERE (`uuid` = '" + uuid + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String kit = resultSet.getString("kit");
				long expire = resultSet.getLong("expire");
				DiaryHG DiaryHG = new DiaryHG(uuid, kit, expire);
				resultSet.close();
				preparedStatement.close();
				return DiaryHG;
			} else {
				DiaryHG DiaryHG = new DiaryHG(uuid);
				resultSet.close();
				preparedStatement.close();
				return DiaryHG;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do kit diario hg.");
			exception.printStackTrace();
		}
		return null;
	}

}