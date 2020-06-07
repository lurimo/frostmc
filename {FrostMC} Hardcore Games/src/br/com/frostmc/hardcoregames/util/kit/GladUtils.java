package br.com.frostmc.hardcoregames.util.kit;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.frostmc.hardcoregames.util.kit.hability.Gladiator;

public class GladUtils {

	private Block mainBlock;
	private Player player1, player2;
	private int tempo, mainBlockY, arenaID;
	private Location player1Loc, player2Loc;
	private boolean checar, cancelar;
	
	public GladUtils(Block mainBlock, int arenaID, Player p1, Player p2) {
		this.checar = false;
		this.mainBlock = mainBlock;
		this.player1 = p1;
		this.player2 = p2;
		this.player1Loc = p1.getLocation().clone();
		this.player2Loc = p2.getLocation().clone();
		this.tempo = 151;
		this.mainBlockY = mainBlock.getLocation().getBlockY() - 2;
		this.arenaID = arenaID;
		this.checar = true;
		this.cancelar = false;
	}
	
	public void check() {
		if (!checar)
			return;
		
		if (player1.getLocation().getBlockY() < mainBlockY || player1.getLocation().getBlockY() > mainBlockY + 12) {
			cancelGlad();
			return;
		} else if (player2.getLocation().getBlockY() < mainBlockY || player2.getLocation().getBlockY() > mainBlockY + 12) {
			cancelGlad();
			return;
		}
		if (this.tempo == 60) {
			player1.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 9999999, 2));
			player2.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 9999999, 2));
		} else if (this.tempo == 0) {
			cancelGlad();
			return;
		}	
		this.tempo--;
	}

	public Block getMainBlock() {
		return mainBlock;
	}

	public void setMainBlock(Block mainBlock) {
		this.mainBlock = mainBlock;
	}
	
	public void setCheck(boolean check) {
		this.checar = check;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public int getMainBlockY() {
		return mainBlockY;
	}

	public void setMainBlockY(int mainBlockY) {
		this.mainBlockY = mainBlockY;
	}

	public Location getPlayer2Loc() {
		return player2Loc;
	}

	public void setPlayer2Loc(Location player2Loc) {
		this.player2Loc = player2Loc;
	}

	public Location getPlayer1Loc() {
		return player1Loc;
	}

	public void setPlayer1Loc(Location player1Loc) {
		this.player1Loc = player1Loc;
	}

	public int getArenaID() {
		return arenaID;
	}

	public void setArenaID(int arenaID) {
		this.arenaID = arenaID;
	}
	
	public void teleportBack() {
		player2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 100000));
		player1.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 100000));
		player1.setFallDistance(0.0F);
		player1.setNoDamageTicks(100);
		player1.getPlayer().teleport(player1Loc);
		player1.setFallDistance(0.0F);
		player2.getPlayer().teleport(player2Loc);
		player2.setFallDistance(0.0F);
		player2.setNoDamageTicks(100);
	}
	
	public Location getBackForPlayer(Player p) {
		if (p.getUniqueId().equals(player1.getUniqueId())) {
			return player1Loc.clone();
		} else if (p.getUniqueId().equals(player2.getUniqueId())) {
			return player2Loc.clone();
		}
		return null;
	}
	
	public void cancelGlad() {
		if (player1.isOnline()) {
			player1.setFallDistance(0.0F);
			player1.setNoDamageTicks(100);
			player1.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 100000));
			if (player1.hasPotionEffect(PotionEffectType.WITHER))
				player1.removePotionEffect(PotionEffectType.WITHER);
		}
		if (player2.isOnline()) {
			player2.setFallDistance(0.0F);
			player2.setNoDamageTicks(100);
			if (player2.hasPotionEffect(PotionEffectType.WITHER))
				player2.removePotionEffect(PotionEffectType.WITHER);
			player2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 100000));
		}
		teleportBack();
		Gladiator.removerBlocos(mainBlock.getLocation());
		this.cancelar = true;
		this.checar = false;
	}

	public boolean isCancelar() {
		return cancelar;
	}

	public void setCancelar(boolean cancelar) {
		this.cancelar = cancelar;
	}
}
