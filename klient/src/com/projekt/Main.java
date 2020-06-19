package com.projekt;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.projekt.GUI.SnakePanel;
import com.projekt.game.LocalPlayer;
import com.projekt.game.OnlineMode;
import com.projekt.game.SinglePlayer;

public class Main {
	
	// Właściwości okna
	static JFrame frame;
	static SnakePanel panel;
	static int WIDTH = 1200;
	static int HEIGHT = 900;
	
	// Tryby gry
	public static SinglePlayer singleGame;
	public static LocalPlayer localGame;
	public static OnlineMode onlineGame;
	
	// Wątki
	public static Thread singleThread;
	public static Thread localThread;
	public static Thread onlineThread;

	public static void main(String[] args) {
		init();
		configureWindow();
		run();
	}
	
	private static void run() {
		while(true) {
			render();
		}
	}
	
	private static void init() {
		frame = new JFrame("SnakeMulti");
		panel = new SnakePanel();
		singleGame = new SinglePlayer();
		localGame = new LocalPlayer();
		onlineGame = new OnlineMode();
		singleThread = new Thread(singleGame);
		localThread = new Thread(localGame);
		onlineThread = new Thread(onlineGame);
	}
	
	private static void configureWindow() {
		// Panel properties
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		// Frame properties
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
	}
	
	public static void startSinglePlayer() {
		singleGame.reset();
		singleThread = new Thread(singleGame);
		singleThread.start();
	}
	
	public static void startLocalPlayer() {
		localGame.reset();
		localThread = new Thread(localGame);
		localThread.start();
	}
	
	public static void startOnlineGame(String host) {
		onlineGame.setIP(host);
		onlineThread= new Thread(onlineGame);
		onlineThread.start();
	}

	public static void render() {
		panel.repaint();
	}

}
