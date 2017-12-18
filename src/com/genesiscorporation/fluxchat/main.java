package com.genesiscorporation.fluxchat;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.server.WebSocketHandshake;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;

public class main {
	public static void main(String[] args) {
		TokenHolder tokenHolder = new TokenHolder();
		Gateway gateway = new Gateway();
		gateway.initGateway(args, false);
	}
}
