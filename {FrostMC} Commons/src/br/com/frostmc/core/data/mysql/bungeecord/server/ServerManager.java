package br.com.frostmc.core.data.mysql.bungeecord.server;

import java.sql.PreparedStatement;

import br.com.frostmc.bungeecord.BungeeMain;

public class ServerManager {
	
	public String nome, motd;
	public int id, ip, onlinePlayers, maxPlayers;
	
	public void createServer(int id, int ip, String nome, int onlinePlayers, int maxPlayers, String motd) {
		try {
			PreparedStatement preparedStatement = BungeeMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_list` WHERE (`id` = '?');");
			preparedStatement.execute("INSERT INTO `server_list` (`id`, `ip`, `nome`, `onlineplayers`, `maxplayers`, `motd`) VALUES ('" + id + "', '" + ip + "', '" + nome + "', '" + onlinePlayers + "', '" + maxPlayers  + "', '" + motd + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void delete(int id) {
		try {
			PreparedStatement preparedStatement = BungeeMain.getCore().getMySQL().getConnection().prepareStatement("SELECT * FROM `server_event` WHERE (`ip` = '" + id + "');");
			preparedStatement.execute("DELETE FROM `server_list` WHERE (`id` = '" + id + "');");
			preparedStatement.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}
