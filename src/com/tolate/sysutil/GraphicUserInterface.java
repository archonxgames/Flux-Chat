package com.tolate.sysutil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.genesiscorporation.fluxchat.Init;
import com.genesiscorporation.fluxchat.main;

public class GraphicUserInterface {
	Init parent;

	public void init(Init that) {
		parent = that;
		GraphicUserInterfaceEvents guievents = new GraphicUserInterfaceEvents();
		guievents.initialize(this);
		JFrame frame = new JFrame();
		frame.setSize(600, 600);
		frame.setTitle("Flux Chat");
		frame.setVisible(true);
		JMenuBar menubar = new JMenuBar();
		JMenu c = new JMenu();
		JMenuItem menuitem = new JMenuItem();
		menuitem.setText("Quit");
		menuitem.setActionCommand("Quit");
		menuitem.addActionListener(guievents);
		c.add(menuitem);
		c.setText("Main Menu");
		menubar.add(c);
		frame.setJMenuBar(menubar);
		ImageIcon image = new ImageIcon();
		frame.setIconImage(image.getImage());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

}
