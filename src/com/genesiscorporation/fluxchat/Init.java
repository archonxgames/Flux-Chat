package com.genesiscorporation.fluxchat;

import com.tolate.sysutil.GraphicUserInterface;

public class Init {
	Gateway gateway = new Gateway();
	GraphicUserInterface gui = new GraphicUserInterface();

	public void initialize(String[] args) {
		TokenHolder tokenHolder = new TokenHolder();
		gui.init(this);
		gateway.initGateway(args, false);
	}

	public void cleanupAndExit(int i) {
		gateway.isShuttingDown = true;
		gateway.session.close();
		gateway.client.destroy();
		System.exit(i);
	}
}
