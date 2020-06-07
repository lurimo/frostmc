package br.com.frostmc.lobby.util.crates;

public class Reward {

	public int ammount;
	public String reward;
	public RewardType type;

	public Reward(int ammount, RewardType type) {
		this.ammount = ammount;
		this.type = type;
		this.reward = "";
	}

	public Reward(String reward, int ammount, RewardType type) {
		this.ammount = ammount;
		this.type = type;
		this.reward = reward;
	}

	public int getAmmount() {
		return ammount;
	}

	public String getReward() {
		return reward;
	}

	public RewardType getType() {
		return type;
	}

	public static enum RewardType {
		MOEDAS("Moedas"),
		FICHAS("Fichas"),
		VIP_LIGHT("Vip Light"),
		VIP_PRIME("Vip Prime"),
		VIP_FROST("Vip Frost"),
		DOUBLE("Double XP");

		private String name;

		private RewardType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
