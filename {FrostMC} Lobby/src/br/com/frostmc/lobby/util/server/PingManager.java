package br.com.frostmc.lobby.util.server;

import java.io.*;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import br.com.frostmc.core.util.enums.ServersType;

public class PingManager {

	private InetSocketAddress host;
	public JSONObject json;
	public int maxPlayers = 0;
	public String motd = "";
	public int onlinePlayers = 0;
	private int timeoutInt = 100;

	public PingManager(ServersType serverConnect) {
		this.host = new InetSocketAddress(serverConnect.getIpAddrees(), serverConnect.getPort());
		try {
			fetchData();
		} catch (Exception exception) {
		}
	}


	public void fetchData() throws Exception {
		Socket socket = new Socket();
		socket.setSoTimeout(timeoutInt);
		socket.connect(host, timeoutInt);
		OutputStream outputStream = socket.getOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		InputStream inputStream = socket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream handshake = new DataOutputStream(b);
		handshake.writeByte(0);
		writeVarInt(handshake, 47);
		writeVarInt(handshake, host.getHostString().length());
		handshake.writeBytes(host.getHostString());
		handshake.writeShort(host.getPort());
		writeVarInt(handshake, 1);
		writeVarInt(dataOutputStream, b.size());
		dataOutputStream.write(b.toByteArray());
		dataOutputStream.writeByte(1);
		dataOutputStream.writeByte(0);
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		readVarInt(dataInputStream);
		int id = readVarInt(dataInputStream);
		if (id == -1) {
			dataOutputStream.close();
			outputStream.close();
			inputStreamReader.close();
			inputStream.close();
			socket.close();
			throw new IOException("Ocorreu um erro ao verificar o dataInputStream");
		}
		int length = readVarInt(dataInputStream);
		if (length == -1) {
			dataOutputStream.close();
			outputStream.close();
			inputStreamReader.close();
			inputStream.close();
			socket.close();
			throw new IOException("Ocorreu um erro ao verificar o dataInputStream");
		}
		byte[] in = new byte[length];
		dataInputStream.readFully(in);
		String json = new String(in);
		long now = System.currentTimeMillis();
		dataOutputStream.writeByte(9);
		dataOutputStream.writeByte(1);
		dataOutputStream.writeLong(now);
		readVarInt(dataInputStream);
		id = readVarInt(dataInputStream);
		if (id == -1) {
			dataOutputStream.close();
			outputStream.close();
			inputStreamReader.close();
			inputStream.close();
			socket.close();
			throw new IOException("ERRO");
		}
		this.json = (JSONObject) new JSONParser().parse(json);
		dataOutputStream.close();
		outputStream.close();
		inputStreamReader.close();
		inputStream.close();
		socket.close();
		processAll();
	}

	private JSONObject getJson() {
		if (json == null) {
			return null;
		}
		return json;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public String getMotd() {
		return motd;
	}

	public int getOnlinePlayers() {
		return onlinePlayers;
	}

	public boolean isOnline() {
		return json != null;
	}

	@SuppressWarnings("unchecked")
	public void processAll() {
		motd = String.valueOf(getJson().get("description")).replace("\u00c2", "");
		JSONArray array = new JSONArray();
		JSONObject site = null;
		array.add(getJson().get("players"));
		for (Object anArray : array) {
			site = (JSONObject) anArray;
		}
		maxPlayers = (int) ((Long) site.get("max")).longValue();
		onlinePlayers = (int) ((Long) site.get("online")).longValue();
	}

	private int readVarInt(DataInputStream in) throws IOException {
		byte k;
		int i = 0;
		int j = 0;
		do {
			k = in.readByte();
			i |= (k & 127) << j++ * 7;
			if (j <= 5) {
				continue;
			}
			throw new RuntimeException("VarInt muito grande");
		} while ((k & 128) == 128);
		return i;
	}

	private void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
		do {
			if ((paramInt & -128) == 0) {
				out.writeByte(paramInt);
				return;
			}
			out.writeByte(paramInt & 127 | 128);
			paramInt >>>= 7;
		} while (true);
	}

}