package br.com.frostmc.core.data.mysql.bukkit.buy_kit.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.buy_kit.hg.BuyKitHG;

public class LoadingBuyKitHG {
	
	public static BuyKitHG load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_buy_kits_hg` WHERE (`nickname` = '" + player.getName() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String kit = resultSet.getString("kit");
				BuyKitHG buy_kit = new BuyKitHG(player, kit);
				resultSet.close();
				preparedStatement.close();
				return buy_kit;
			} else {
				BuyKitHG buy_kit = new BuyKitHG(player);
				resultSet.close();
				preparedStatement.close();
				return buy_kit;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do seu kit.");
			exception.printStackTrace();
		}
		return null;
	}
	
	public static BuyKitHG load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_buy_kits_hg` WHERE (`nickname` = '" + offlinePlayer.getName() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String kit = resultSet.getString("kit");
				BuyKitHG buy_kit = new BuyKitHG(offlinePlayer, kit);
				resultSet.close();
				preparedStatement.close();
				return buy_kit;
			} else {
				BuyKitHG buy_kit = new BuyKitHG(offlinePlayer);
				resultSet.close();
				preparedStatement.close();
				return buy_kit;
			}
		} catch (Exception exception) {
			BukkitMain.getCore().getBox().box("[FrostMC - Common]", "Ocorreu um erro ao coletar", "infomações do seu kit.");
			exception.printStackTrace();
		}
		return null;
	}

}
