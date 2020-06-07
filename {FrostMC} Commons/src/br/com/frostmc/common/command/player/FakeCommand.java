package br.com.frostmc.common.command.player;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.util.fake.FakeManager;
import br.com.frostmc.core.util.Strings;

public class FakeCommand extends BaseCommand {
	
	public FakeCommand() {
		super("fake", "aplique-se para equipe", Arrays.asList("fakes", "fakelist", "fakereset", "tfake"));
	}

	public enum Modes {
		LIST, RESET, RANDOM;
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (!isPlayer(commandSender)) {
			return true;
		}
		Player player = (Player) commandSender;
		if (!BukkitMain.getPermissions().isFrost(player)) {
			player.sendMessage(Strings.getPermission());
			return true;
		}
		if (args.length == 0) {
			player.sendMessage("�8[�3�lFAKE�8] �fUtilize o comando: /fake (nick/random/list/reset)");
			return true;
		}
		if (args.length == 1) {
			if (args[0].toLowerCase().equals("list")) {
				if (FakeManager.fakes.size() == 0) {
					player.sendMessage("�8[�3�lFAKE�8] �fN�o tem nenhum jogador utilizando o /fake.");
					return true;
				}
				player.sendMessage(" ");
				player.sendMessage("�8[�3�lFAKE�8] �fJogadores a utilizar fake:");
				player.sendMessage(" ");
				for (String fakes : FakeManager.fakes) {
					player.sendMessage(FakeManager.fakeList.get(fakes) + " �8- �7(�f" + fakes + "�7)" + "\n");
				}
				player.sendMessage(" ");
				return true;
			}
			if (args[0].equals("#") || args[0].toLowerCase().equals("reset")) {
				if (!FakeManager.fake.containsKey(player.getUniqueId())) {
					player.sendMessage("�8[�3�lFAKE�8] �fVoc� n�o est� utilizando nenhum fake no momento.");
					return true;
				}
				FakeManager.removeFake(player);
				player.sendMessage("�8[�3�lFAKE�8] �fSeu nickname foi resetado ao original.");
				return true;
			}
			if (FakeManager.fake.containsKey(player.getUniqueId())) {
				player.sendMessage("�8[�3�lFAKE�8] �fVoc� j� est� utilizando um fake, para remov�-lo e colocar um novo, digite �7/fake #�f");
				return true;
			}
			if (args[0].toLowerCase().equals("random")) {
				if (FakeManager.random.length == 0) {
					player.sendMessage("�8[�c�lERRO�8] �fNenhum fake aleat�rio foi encontrado.");
					return true;
				}
				String fake = FakeManager.random[new Random().nextInt(FakeManager.random.length)];
				if (!FakeManager.fakes.contains(fake)) {
					sendWarning(player.getName() + " agora esta usando o fake " + fake);
					FakeManager.setFake(player, fake);
					player.sendMessage("�8[�3�lFAKE�8] �fEscolhi um nickname aleat�rio para voc�: �a" + fake);
					return true;
				}
			}
			if (!validString(args[0])) {
				 player.sendMessage("�8[�c�lERRO�8] �fEste nickname cont�m caracteres inv�lidos!"); 
				 return false; 
			}
			if (args[0].length() > 12) {
				player.sendMessage("�8[�c�lERRO�8] �fEste nickname � muito grande, escolha um menor."); 
				return false; 
			} 
			if (args[0].length() < 4) { 
				player.sendMessage("�8[�c�lERRO�8] �fEste nick � muito pequeno, escolha um maior."); 
				return false; 
			}
			if (isPremium(args[0])) {
				player.sendMessage("�8[�c�lERRO�8] �fV� que este nick pertence a uma conta original. Por conta de nossas pol�ticas, isto n�o � permitido.");
				return true;
			}
			if (FakeManager.fakes.contains(args[0])) {
				player.sendMessage("�8[�c�lERRO�8] �fAlgu�m j� est� utilizando este fake no momento.");
				return true;
			}
			sendWarning(player.getName() + " agora esta usando o fake " + args[0]);
			FakeManager.setFake(player, args[0]);
			player.sendMessage("�8[�c�lERRO�8] �fSeu nickname foi alterado para �a" + args[0] + "�f.");
			return true;
		}
		return false;
	}
	
	public boolean validString(String str) {
		return str.matches("[a-zA-Z0-9]+") && !str.contains(".com");
	}

	private String getUrlContent(String url) {
		try {
			InputStream connection = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection));
			String line = null;
			String content = "";
			while ((line = reader.readLine()) != null)
				content += line;
			return content;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public boolean isPremium(String fake) {
		return getUrlContent("https://api.mojang.com/users/profiles/minecraft/" + fake).length() > 0;
	}

}
