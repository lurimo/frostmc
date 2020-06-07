package br.com.frostmc.lobby.util.particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.frostmc.lobby.FrostLobby;

public class ParticleManager {

	public static void executeSpiral(Location boxLocation, ParticleEffect effect, float radius) {
		double xRotation = 0, yRotation = 0, zRotation = 5.0D;
		double angularVelocityX = 0.01570796326794897D, angularVelocityY = 0.01847995678582231D,
				angularVelocityZ = 0.0202683397005793D;
		double xSubtract = 0, ySubtract = 0, zSubtract = 0;

		float step = 0.0F;
		boolean enableRotation = true;
		int particles = 20;

		Location location = boxLocation.clone();
		location.add(0.0D, 1.0D, 0.0D);
		location.subtract(xSubtract, ySubtract, zSubtract);
		double inc = 6.283185307179586D / particles;
		double angle = step * inc;
		Vector v = new Vector();
		v.setX(Math.cos(angle) * radius);
		v.setZ(Math.sin(angle) * radius);
		UtilVelocity.rotateVector(v, xRotation, yRotation, zRotation);
		if (enableRotation) {
			UtilVelocity.rotateVector(v, angularVelocityX * step, angularVelocityY * step,
					angularVelocityZ * step);
		}

		effect.setParticle(location.add(v), 0, 0, 0, 0, 1);

		step -= 1.0F;
	}
	
	public static void setNoFlexZone(Location location, ParticleEffect effect, double r) {
		new BukkitRunnable() {
			
			double phi = 0;
			
			@Override
			public void run() {
				phi += Math.PI/10;
				for (double alpha = 0; alpha <= 2*Math.PI; alpha += Math.PI/40) {
					double x = r*Math.cos(alpha)*Math.sin(phi);
					double y = r*Math.cos(phi) + 1.5;
					double z = r*Math.sin(alpha)*Math.sin(phi);
					location.add(x, y, z);
					effect.setParticle(location, 0, 0, 0, 0, 1);
					location.subtract(x, y, z);
				}
				if (phi > 15*Math.PI) {
					cancel();
				}
			}
		}.runTaskTimer(FrostLobby.getInstance(), 0L, 1L);
	}
 
    public void fazerParticula(Player p, Location loc, double x, double y,double value) {
        ParticleEffect.MAGIC_CRIT.setParticle(p, loc.clone().add(Math.sin(value)*x,y+Math.sin(value),Math.cos(value)*x), 0, 0, 0, 0, 1);  
    }

}
