package com.tolate.sysutil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicUserInterfaceEvents implements ActionListener {
	GraphicUserInterface parent;

	public void initialize(GraphicUserInterface that) {
		parent = that;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getID() == e.ACTION_PERFORMED) {
			if (e.getActionCommand() == "Quit") {
				parent.parent.cleanupAndExit(0);
			}
		}
	}

}
