package br.com.frostmc.hardcoregames.timer.feast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.hardcoregames.FrostHG;

public class ServerAPI {
	
	public static HashMap<String, Object> valores = new HashMap<>();
	
	public static void sendConsoleMSG(String msg) {
		Bukkit.getConsoleSender().sendMessage(msg);
	}
	
	public static boolean checkItem(ItemStack item, String display) {
		return (item != null && item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
		&& item.getItemMeta().getDisplayName().startsWith(display));
	}

	public static String calcularTempo(FormatUtil formato, int valor) {
		long calculado = 0;
		if (formato.equals(FormatUtil.MINUTOS)) {
			calculado = valor * 60000;
		} else if (formato.equals(FormatUtil.HORAS)) {
			calculado = valor * 3600000;
		} else if (formato.equals(FormatUtil.DIAS)) {
			calculado = valor * 86400000;
		} else if (formato.equals(FormatUtil.MESES)) {
			calculado = valor * Long.valueOf(String.valueOf("2592000000"));
		}
		return String.valueOf(calculado + System.currentTimeMillis());
	}

	public static String timePlayer(String tempo) {
		Long joindate = Long.valueOf(tempo);
	    Long date = System.currentTimeMillis() - joindate;
	    long seconds = date / 1000L % 60L;
	    long minutes = date / (60L * 1000L) % 60L;
	    long hours = date / (60L * 60L * 1000L) % 24L;
	    long days = date / (24L * 60L * 60L * 1000L);
	    String fulldate = days+"d "+hours+"h "+minutes+"m "+seconds+"s";
		return fulldate.replaceAll("-", "");
	}
	
    public static String formatarTempo(Integer i) {
    	if (i.intValue() >= 60) {
			Integer time = Integer.valueOf(i.intValue() / 60);
			String add = "";
			if (time.intValue() > 1) {
			  	add = "s";
			}
		    return time + " minuto" + add;
    	}
		Integer time = i;
	    String add = "";
	    if (time.intValue() > 1) {
	    	add = "s";
	    }
		return time + " segundo" + add;
    }
	
	public static String formatarSegundos(Integer i) {
		int minutes = i.intValue() / 60;
	    int seconds = i.intValue() % 60;
	    String disMinu = (minutes < 10 ? "" : "") + minutes;
	    String disSec = (seconds < 10 ? "0" : "") + seconds;
	    String formattedTime = disMinu + ":" + disSec;
	    return formattedTime;
	}
	
	public static String cap(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
	
	public static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static String getCooldown(Long started) {
	    Long agora = System.currentTimeMillis();
	    Long time = agora - started;
	    long segundos = time / 1000L % 60L;
	    return String.valueOf(segundos + "s").replaceAll("-", "");
	}
	
	public static String getTimer(Long started) {
	    Long agora = System.currentTimeMillis();
	    Long time = agora - started;
	    long segundos = time / 1000L % 60L;
	    long minutos = time / (60L * 1000L) % 60L;
	    long horas = time / (60L * 60L * 1000L) % 24L;
	    long dias = time / (24L * 60L * 60L * 1000L);
	    boolean d = false, h = false, m = false, s = false;
	    StringBuilder string = new StringBuilder();
	    if (dias != 0L) {
	    	if (dias == 1) {
	    		string.append(dias + " dia, ");
	    	} else {
	    		string.append(dias + " dias, ");
	    	}
	    	d = true;
	    }
	    if (horas != 0L) {
	    	if (horas == 1) {
	    		string.append(horas + " hora, ");
	    	} else {
	    		string.append(horas + " horas, ");
	    	}
	    	h = true;
	    }
	    if (minutos != 0L) {
	    	if (minutos == 1) {
	    		string.append(minutos + " minuto e ");
	    	} else {
		    	string.append(minutos + " minutos e ");
	    	}
	    	m = true;
	    }
	    if (segundos != 0L) {
	    	if (segundos == 1) {
	    		string.append(segundos + " segundo");
	    	} else {
	    		string.append(segundos + " segundos");
	    	}
	    	s = true;
	    } else {
	    	if ((!d) && (!h) && (!m) && (!s))
	    		return String.valueOf((started - agora)).replaceAll("-", "") + "ms";
	    }
		return string.toString().replaceAll("-", "") + ".";
	}
	
	public static void sendLog(String msg, boolean erro) {
		new BukkitRunnable() {
	    public void run() {
	    	try {
	            File saveTo = new File(FrostHG.getInstance().getDataFolder(), erro ? "erros.log" : "logs.log");
	            if (!saveTo.exists()) {
	                 saveTo.createNewFile();
	            }
	            FileWriter fw = new FileWriter(saveTo, true);
	            PrintWriter pw = new PrintWriter(fw);
	            pw.println(msg);
	            pw.flush();
	            pw.close();
	         } catch (IOException e) {
	        	 e.printStackTrace();
	         }
	    }
	    }.runTaskAsynchronously(FrostHG.getInstance());
	}
	
	public enum FormatUtil {
		
		MESES("Meses", "Mes"),
		DIAS("Dias", "Dia"),
		HORAS("Horas", "Hora"),
		MINUTOS("Minutos", "Minuto");
		
		private String nome, sobrenome;
		
		FormatUtil(String nome, String sobrenome) {
			this.nome = nome;
			this.sobrenome = sobrenome;
		}

		public String getNome() {
			return nome;
		}
		
		public String getSobreNome() {
			return sobrenome;
		}

		public static FormatUtil getFromString(String formato) {
			for (FormatUtil formatos : values())
				 if (formatos.getNome().toLowerCase().equals(formato.toLowerCase())) {
					 return formatos;
				 } else if (formatos.getSobreNome().toLowerCase().equals(formato.toLowerCase())) {
					 return formatos;
				 }
			return null;
		}
	}
	
	public static void autoMSG() {
		int minutos = FrostHG.getInstance().getConfig().getInt("Servidor.Config.AutoMSG.Minutos") * 1200;
		new BukkitRunnable() {
		String prefixo = FrostHG.getInstance().getConfig().getString("Servidor.Config.AutoMSG.Prefixo").replaceAll("&", "§");
		int atual = 0, max = FeastConfig.mensagens.size();
		public void run() {
			if (atual == max) {
				atual = 0;
			}
			
			Bukkit.broadcastMessage(prefixo + " " + FeastConfig.mensagens.get(atual));
			atual++;
		}
		}.runTaskTimer(FrostHG.getInstance(), minutos, minutos);
	}
}