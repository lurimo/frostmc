package br.com.frostmc.lobby.util.crates.types;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.lobby.util.crates.Crate;
import br.com.frostmc.lobby.util.crates.CrateType;
import br.com.frostmc.lobby.util.crates.Reward;
import br.com.frostmc.lobby.util.crates.Reward.RewardType;

public class Gold extends Crate {

	public Gold(Player player) {
		super("Ouro", CrateType.GOLD, null, new ItemStack(Material.AIR));

		Reward reward = null;
		int random = getRandom().nextInt(1000);

		if (random < 400) {
			setRewardIcon(new ItemBuilder().setNome("§6§l10.000 MOEDAS").setMaterial(Material.GOLD_NUGGET).finalizar());
			reward = new Reward(10000, RewardType.MOEDAS);

		} else if (random >= 400 && random < 500) {
			
			setRewardIcon(new ItemBuilder().setNome("§6§l20.000 MOEDAS").setMaterial(Material.GOLD_NUGGET).finalizar());
			reward = new Reward(20000, RewardType.MOEDAS);

		} else if (random >= 500 && random < 600) {

			setRewardIcon(new ItemBuilder().setNome("§6§l30.000 MOEDAS").setMaterial(Material.GOLD_NUGGET).finalizar());
			reward = new Reward(30000, RewardType.MOEDAS);

		} else if (random >= 600 && random < 700) {

			setRewardIcon(new ItemBuilder().setNome("§3§l2 DOUBLE XP").setMaterial(Material.EXP_BOTTLE).finalizar());
			reward = new Reward(2, RewardType.DOUBLE);

		} else if (random >= 700 && random < 800) {

			setRewardIcon(new ItemBuilder().setNome("§3§l4 DOUBLE XP").setMaterial(Material.EXP_BOTTLE).finalizar());
			reward = new Reward(4, RewardType.DOUBLE);

		} else if (random >= 800 && random < 900) {

			setRewardIcon(new ItemBuilder().setNome("§3§l6 DOUBLE XP").setMaterial(Material.EXP_BOTTLE).finalizar());
			reward = new Reward(6, RewardType.DOUBLE);

		} else if (random >= 900 && random < 990) {

			setRewardIcon(new ItemBuilder().setNome("§3§l2 FICHAS").setMaterial(Material.DIAMOND).finalizar());
			reward = new Reward(3, RewardType.FICHAS);

		} else if (random >= 990) {

			setRewardIcon(new ItemBuilder().setNome("§fVip §3§lPRIME §8(§fQuatro dias§8)").setMaterial(Material.NETHER_STAR).finalizar());
			reward = new Reward(1, RewardType.VIP_PRIME);

		}

		setReward(reward);
	}

}
