package br.com.frostmc.core.util.enums;

public enum ServersType {
	
	DEFAULT_SERVER(-1, "10.0.0.6", 25565),
	
	LOGIN(1, DEFAULT_SERVER.getIpAddrees(), 25564),
	LOBBY(2, DEFAULT_SERVER.getIpAddrees(), 25563),
	PVP(3, DEFAULT_SERVER.getIpAddrees(), 25562),
	ONEVSONE(4, DEFAULT_SERVER.getIpAddrees(), 25561),
	GLADIATOR(5, DEFAULT_SERVER.getIpAddrees(), 25560),
	
	SS(6, DEFAULT_SERVER.getIpAddrees(), 25559),
	
	HG_A1(8, DEFAULT_SERVER.getIpAddrees(), 25558),
	HG_A2(9, DEFAULT_SERVER.getIpAddrees(), 25557),
	HG_A3(10, DEFAULT_SERVER.getIpAddrees(), 25556),
	HG_A4(11, DEFAULT_SERVER.getIpAddrees(), 25555),
	HG_A5(12,DEFAULT_SERVER.getIpAddrees(), 25554),
	HG_A6(13,DEFAULT_SERVER.getIpAddrees(), 25553),
	HG_A7(14,DEFAULT_SERVER.getIpAddrees(), 25552),
	
	EVENTO(15, DEFAULT_SERVER.getIpAddrees(), 25551);
	
	private String ipAddrees;
	private int id, port;
	
	private ServersType(int id, String ipAddrees, int port) {
		this.id = id;
		this.ipAddrees = ipAddrees;
		this.port = port;
	}
	
	public int getId() { return id; }
	public String getIpAddrees() { return ipAddrees; }
	public int getPort() { return port; }
	public String getName() {
		return name();
	}
	
	public static ServersType getServerType(ServersType serversType) {
		ServersType type = ServersType.DEFAULT_SERVER;
		for (ServersType types : values())
			if (types.equals(serversType))
				type = types;
		return type;
	}

	public static ServersType getServerType(int id) {
		ServersType type = ServersType.DEFAULT_SERVER;
		for (ServersType types : values())
			if (types.getId() == id)
				type = types;
		return type;
	}

}