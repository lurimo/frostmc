package br.com.frostmc.login.commands.player;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.data.mysql.bukkit.authentication.Authentication;
import br.com.frostmc.core.util.enums.AuthType;
import br.com.frostmc.login.FrostLogin;
import br.com.frostmc.login.commands.BaseCommand;
import br.com.frostmc.login.inventory.ManagerInventory;
import br.com.frostmc.login.scoreboard.Scoreboarding;

public class RegisterCommand extends BaseCommand {

	public RegisterCommand() {
		super("register", "register go to server", Arrays.asList("registrar", "registro"));
	}

	public String[] registerBlocks = new String[] { "senha", "123456", "654321", "password", "server", "server", "servidor" };
	
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			AccountBukkit account = new AccountBukkit(player);
			Authentication authentication = account.getAuthentication();
			if (!FrostLogin.checkToLogin.get(player.getUniqueId()).equals(AuthType.NONREGISTERED)) {
				player.sendMessage("�8[�c�lERRO�8] �fEste nome de usu�rio j� possui uma conta no servidor, utilize �a/login <senha>�f.");
				return true;
			}
			if (args.length <= 1) {
				player.sendMessage("�8[�b�lLOGIN�8] �fUtilize o comando: �a/register (senha) (confirmar senha)");
				return true;
			}
			if (args.length == 2) {
				if (args[0].length() < 5) {
					player.sendMessage("�8[�c�lERRO�8] �fPara sua seguran�a, sua senha deve conter no m�nimo 6 digitos.");
					return true;
				}
				if (args[1].equalsIgnoreCase(args[0])) {
					for (String block : registerBlocks) {
						if (args[0].contains(block)) {
							player.sendMessage("�8[�c�lERRO�8] �fEsta senha � muito simples, tente algo mais complexo e/ou pessoal.");
							return true;
						}
					}
					FrostLogin.checkToLogin.put(player.getUniqueId(), AuthType.LOGGED);
					authentication.setIpAddrees(account.getGroups().getIpAddrees());
					authentication.setAuthType(AuthType.LOGGED);
					authentication.setPassword(args[0]);
					authentication.save();
					player.sendMessage("�8[�b�lLOGIN�8] �fSua conta foi registrada com sucesso!");
					if (BukkitMain.getPermissions().isTrial(player)) {
						player.sendMessage("�8[�b�lLOGIN�8] �fSua senha: �a" + args[0]);
						player.sendMessage("�8[�b�lLOGIN�8] �fSeu codigo de seguran�a �: " + authentication.getSecurityCode());
					}
					ManagerInventory.sendItens(player);
					Scoreboarding.setScoreboard(player);
					return true;
				} else  {
					player.sendMessage("�8[�b�lLOGIN�8] �fConfirme sua senha.");
					return true;
				}
			}
		} else {
			return true;
		}
		return false;
	}

}
