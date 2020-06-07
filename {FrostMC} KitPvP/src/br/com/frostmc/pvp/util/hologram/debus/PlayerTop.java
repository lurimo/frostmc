package br.com.frostmc.pvp.util.hologram.debus;

public class PlayerTop {

	private String name;
	private Integer id;
	private int top;

	public PlayerTop(Integer id, String name, int top) {
		this.id = id;
		this.name = name;
		this.top = top;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getTop() {
		return top;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public static class PlayerTopHGWins {

		private String name;
		private Integer id;
		private int top;

		public PlayerTopHGWins(Integer id, String name, int top) {
			this.id = id;
			this.name = name;
			this.top = top;
		}

		public Integer getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getTop() {
			return top;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setTop(int top) {
			this.top = top;
		}
	}
}
