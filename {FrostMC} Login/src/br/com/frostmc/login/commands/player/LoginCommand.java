package br.com.frostmc.login.commands.player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.data.mysql.bukkit.authentication.Authentication;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.AuthType;
import br.com.frostmc.login.FrostLogin;
import br.com.frostmc.login.commands.BaseCommand;
import br.com.frostmc.login.inventory.ManagerInventory;
import br.com.frostmc.login.scoreboard.Scoreboarding;

public class LoginCommand extends BaseCommand {

	public LoginCommand() {
		super("login", "login go to server", Arrays.asList("logar"));
	}
	
	public static HashMap<UUID, Integer> errors = new HashMap<UUID, Integer>();
	
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			AccountBukkit account = new AccountBukkit(player);
			Authentication authentication = account.getAuthentication();
			if (FrostLogin.checkToLogin.get(player.getUniqueId()).equals(AuthType.LOGGED)) {
				player.sendMessage("�8[�c�lERRO�8] �fSua conta j� est� logada.");
				return true;
			}
			if (args.length == 0) {
				if (BukkitMain.getPermissions().isTrial(player)) {
					player.sendMessage("�b�lFROST�f�lMC");
					player.sendMessage("");
					player.sendMessage("�8[�b�lLOGIN�8] �fBem-vindo de volta! Utilize o comando: �a/login (senha) (codigo de seguran�a)");
				} else {
					player.sendMessage("�b�lFROST�f�lMC");
					player.sendMessage("");
					player.sendMessage("�8[�b�lLOGIN�8] �fBem-vindo de volta! Utilize o comando: �a/login (senha)");
				}
				return true;
			}
			if (args.length == 1)  {
				if (!BukkitMain.getPermissions().isTrial(player)) {
					if(args[0].equalsIgnoreCase((String) authentication.getPassword())) {
						FrostLogin.checkToLogin.put(player.getUniqueId(), AuthType.LOGGED);
						authentication.setAuthType(AuthType.LOGGED);
						authentication.setIpAddrees(account.getGroups().getIpAddrees());
						authentication.save();
						ManagerInventory.sendItens(player);
						Scoreboarding.setScoreboard(player);
						player.sendMessage("�8[�b�lLOGIN�8] �fVoc� efetuou o login com sucesso.");
						player.sendMessage("�8[�b�lLOGIN�8] �fUtilize a b�ssola para acessar o lobby.");
						return true;
					} else {
						if (errors.get(player.getUniqueId()) == 3) {
							errors.remove(player.getUniqueId());
							player.kickPlayer("�8[�c�lERRO�8]" + "\n" + "\n" + "�fVoc� errou sua senha mais de 3 vezes!" + "\n" + "�fEsqueceu sua senha? Entre em contato com um administrador." + "\n" + "\n" + "�b" + Strings.getWebsite());
							return true;
						}
						errors.put(player.getUniqueId(), errors.get(player.getUniqueId()) + 1);
						player.sendMessage("�8[�c�lERRO�8] �fA senha inserida n�o corresponde com o nosso banco de dados. Tente novamente.");
					}
				} else {
					player.sendMessage("�8[�b�lLOGIN�8] �fUtilize o comando: �a/login " + args[0] + " (codigo de seguran�a)");
					player.sendMessage("�8[�c�lERRO�8] �fDigite seu codigo de seguran�a.");
					return true;
				}
			}
			if (args.length == 2) {
				if (BukkitMain.getPermissions().isTrial(player)) {
					if (args[1].equalsIgnoreCase((String) authentication.getSecurityCode())) {
						if(args[0].equalsIgnoreCase((String) authentication.getPassword())) {
							FrostLogin.checkToLogin.put(player.getUniqueId(), AuthType.LOGGED);
							authentication.setAuthType(AuthType.LOGGED);
							authentication.setIpAddrees(account.getGroups().getIpAddrees());
							authentication.save();
							ManagerInventory.sendItens(player);
							Scoreboarding.setScoreboard(player);
							player.sendMessage("�8[�b�lLOGIN�8] �fVoc� efetuou o login com sucesso.");
							player.sendMessage("�8[�b�lLOGIN�8] �fUtilize a b�ssola para acessar o lobby.");
							return true;
						} else {
							if (errors.get(player.getUniqueId()) == 3) {
								errors.remove(player.getUniqueId());
								player.kickPlayer("�8[�c�lERRO�8]" + "\n" + "\n" + "�fVoc� errou sua senha mais de 3 vezes!" + "\n" + "�fEsqueceu sua senha? Entre em contato com um administrador." + "\n" + "\n" + "�3" + Strings.getWebsite());
								return true;
							}
							errors.put(player.getUniqueId(), errors.get(player.getUniqueId()) + 1);
							player.sendMessage("�c�lERRO �f7A senha inserida n�o corresponde com o nosso banco de dados. Tente novamente.");
						}
					} else {
						if (!args[1].contains("#")) {
							player.sendMessage("�8[�c�lERRO�8] �fVoc� deve inserir uma �aHASHTAG �fno come�o do seu c�digo de seguran�a.");
							player.sendMessage("�8[�b�lLOGIN�8] �fExemplo: #12345");
							return true;
						} else {
							player.sendMessage("�8[�c�lERRO�8] �fSeu codigo de seguran�a est� incorreto. Tente novamente.");
							return true;
						}
					}
				}
			}
		} else {
			return true;
		}
		return false;
	}


}
