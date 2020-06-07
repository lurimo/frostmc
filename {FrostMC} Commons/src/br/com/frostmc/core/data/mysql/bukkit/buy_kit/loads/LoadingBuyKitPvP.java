package br.com.frostmc.core.data.mysql.bukkit.buy_kit.loads;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.buy_kit.pvp.BuyKitPvP;

public class LoadingBuyKitPvP {
	
	public static BuyKitPvP load(Player player) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_buy_kits_pvp` WHERE (`nickname` = '" + player.getName() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String kit = resultSet.getString("kit");
				BuyKitPvP buy_kit = new BuyKitPvP(player, kit);
				resultSet.close();
				preparedStatement.close();
				return buy_kit;
			} else {
				BuyKitPvP buy_kit = new BuyKitPvP(player);
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
	
	public static BuyKitPvP load(OfflinePlayer offlinePlayer) {
		try {
			PreparedStatement preparedStatement = BukkitMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_buy_kits_pvp` WHERE (`nickname` = '" + offlinePlayer.getName() + "');");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String kit = resultSet.getString("kit");
				BuyKitPvP buy_kit = new BuyKitPvP(offlinePlayer, kit);
				resultSet.close();
				preparedStatement.close();
				return buy_kit;
			} else {
				BuyKitPvP buy_kit = new BuyKitPvP(offlinePlayer);
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
