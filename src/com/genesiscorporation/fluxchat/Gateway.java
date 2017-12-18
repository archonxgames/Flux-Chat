package com.genesiscorporation.fluxchat;

import java.net.URI;
import java.time.Instant;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.json.*;

@WebSocket
public class Gateway implements org.eclipse.jetty.websocket.api.WebSocketListener {
	TokenHolder tokenHolder = new TokenHolder();
	SslContextFactory ssl = new SslContextFactory();
	WebSocketClient client = new WebSocketClient(ssl);
	Session session;
	RemoteEndpoint remote;

	public void initGateway(String args[], boolean resumeSession) {
		String usertoken = args[0];
		tokenHolder.setToken(usertoken);
		System.out.println("Token is: " + usertoken);
		client.setConnectTimeout(5000);
		try {
			client.start();
			URI uri = new URI("wss://gateway.discord.gg");
			ClientUpgradeRequest upgradeRequest = new ClientUpgradeRequest();
			client.connect(this, uri, upgradeRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long time = System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis() > time + beatinterval) {
				time = System.currentTimeMillis();
				heartbeat();
			}
		}
	}

	int beatinterval = 500;
	int currentbeat;
	int lastS = 0;

	private void heartbeat() {
		// TODO Auto-generated method stub
		try {
			JSONObject heartbeatdata = new JSONObject();
			heartbeatdata.put("op", 1);
			heartbeatdata.put("d", lastS);
			currentbeat++;
			if (session != null) {
				if (session.isOpen()) {
					System.out.println("Heartbeat: " + heartbeatdata.toString());
					remote.sendString(heartbeatdata.toString());
				} else {
					currentbeat = 0;
				}
			}
		} catch (Exception e) {

		}

	}

	@Override
	public void onWebSocketClose(int arg0, String arg1) {
		System.out.println("Websocket Closed: " + arg1 + " (" + arg0 + ")");
		try {
			String args[] = { tokenHolder.getToken() };
			initGateway(args, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onWebSocketConnect(Session session) {
		this.session = session;
		System.out.println("Connected");
		System.out.println("Sending Identify");
		JSONObject d = new JSONObject();
		JSONObject gatewayHandshake = new JSONObject();
		JSONObject properties = new JSONObject();
		JSONObject presence = new JSONObject();
		JSONObject game = new JSONObject();
		game.put("name", "ToLate Systems");
		game.put("type", 0);
		presence.put("game", game);
		presence.put("status", "online");
		presence.put("since", 91879201);
		presence.put("afk", false);
		properties.put("$os", "linux");
		properties.put("$browser", "linux");
		properties.put("$device", "linux");
		gatewayHandshake.put("presence", presence);
		gatewayHandshake.put("properties", properties);
		String usertoken = "";
		usertoken = tokenHolder.getToken();
		gatewayHandshake.put("token", usertoken);
		d.put("op", 2);
		d.put("d", gatewayHandshake);
		System.out.println("Send: " + d.toString());
		remote = session.getRemote();
		remote.sendStringByFuture(d.toString());
	}

	@Override
	public void onWebSocketError(Throwable arg0) {
		System.out.println("Error Occured");

	}

	@Override
	public void onWebSocketBinary(byte[] arg0, int arg1, int arg2) {
		System.out.println("Rec: " + arg0 + " : " + arg1 + " : " + arg2);

	}

	@Override
	public void onWebSocketText(String arg0) {
		System.out.println("Rec: " + arg0);
		JSONObject data = new JSONObject(arg0);

		if (data.has("s")) {
			if (data.isNull("s") == false) {
				lastS = data.getInt("s");
			}
		}
		if (data.has("d")) {
			if (data.isNull("d") == false) {
				JSONObject d = data.getJSONObject("d");
				if (d.has("heartbeat_interval")) {
					beatinterval = d.getInt("heartbeat_interval");
					System.out.println("Heartbeat interval is now: " + d.getInt("heartbeat_interval"));
				}
			}
		}
		if (data.has("op")) {
			if (data.getInt("op") == 0) {
				// The server dispatched an event
			}
			if (data.getInt("op") == 1) {
				JSONObject returndata = new JSONObject();
				returndata.put("op", 11);
				remote.sendStringByFuture(returndata.toString());
				// JSONObject logger=new JSONObject
			}
			if (data.getInt("op") == 11) {
				System.out.println("Heartbeat ACK");
			}
		}
	}
}
