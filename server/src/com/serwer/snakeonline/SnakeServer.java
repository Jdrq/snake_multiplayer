package com.serwer.snakeonline;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.serwer.entities.Food;
import com.serwer.snakeonline.players.Player;

public class SnakeServer {
	
	static SnakeServer server;
	
	// Właściwości serwera
	int port = 6110;
	ServerSocket socket;
	
	// Gracze
	Player player1;
	Player player2;
	Input player1Input;
	Input player2Input;
	Output player1Output;
	Output player2Output;
	
	// Entities
	Food food;
	
	// Właściwości gry
	boolean running = false;
	int gameSpeed = 100000000;
	
	// Wątki
	Thread player1InputThread;
	Thread player2InputThread;
	Thread player1OutputThread;
	Thread player2OutputThread;
	
	public SnakeServer() {
		init();
	}
	
	private void init() {
		try {
			System.out.println("Rozpoczynam");
			socket = new ServerSocket(port);
			player1 = new Player(1);
			player2 = new Player(2);
			food = new Food();
			player1Input = new Input(player1);
			player2Input = new Input(player2);
			
			player1Output = new Output(player1, player2, food);
			player2Output = new Output(player2, player1, food);
			
			player1InputThread = new Thread(player1Input);
			player2InputThread = new Thread(player2Input);
			player1OutputThread = new Thread(player1Output);
			player2OutputThread = new Thread(player2Output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void listenForPlayers() throws IOException {
		System.out.println("Nasłuchuje na porcie " + port + "...");
		while(!player1.isAssigned() || !player2.isAssigned()) {
			Socket socket = server.socket.accept();
			if(!player1.isAssigned()) {
				System.out.println("Gracz 1 połączony");
				player1.setSocket(socket);
				player1.setObjectInputStream();
				player1.setObjectOutputStream();
				player1.setAssigned(true);
			}else if(!player2.isAssigned()) {
				System.out.println("Gracz 2 połączony");
				player2.setSocket(socket);
				player2.setObjectInputStream();
				player2.setObjectOutputStream();
				player2.setAssigned(true);
			}
		}

	}
	
	private void run() {
		running = true;
		
		player1InputThread.start();
		player2InputThread.start();
		
		//player1OutputThread.start();
		//player2OutputThread.start();
		
		long last = System.nanoTime();
		
		while(running) {
			if(System.nanoTime() - last > gameSpeed) {
				last = System.nanoTime();
				tick();
			}
			
			sendData();
			
		}
	}
	
	private void sendData() {
		try {
			
			player1.getObjectOutputStream().writeBoolean(true);
			player1.getObjectOutputStream().writeObject(player1.getSnake().getLocation());
			player1.getObjectOutputStream().writeObject(player2.getSnake().getLocation());
			player1.getObjectOutputStream().writeInt(food.getX());
			player1.getObjectOutputStream().writeInt(food.getY());
			player1.getObjectOutputStream().reset();
			
			player2.getObjectOutputStream().writeBoolean(true);
			player2.getObjectOutputStream().writeObject(player2.getSnake().getLocation());
			player2.getObjectOutputStream().writeObject(player1.getSnake().getLocation());
			player2.getObjectOutputStream().writeInt(food.getX());
			player2.getObjectOutputStream().writeInt(food.getY());
			player2.getObjectOutputStream().reset();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void tick() {
		
		player1.tick();
		player2.tick();
		
		checkCollision(player1, player2);
		checkOutOfBounds(player1, player2);
		checkCollisionWithFood(player1, player2);
		
	}
	
	private void checkCollisionWithFood(Player p1, Player p2) {
		if(p1.getSnake().getLocation().get(0).getX() == food.getX() && p1.getSnake().getLocation().get(0).getY() == food.getY()) {
			p1.getSnake().increaseLength();
			food = new Food();
		}
		
		if(p2.getSnake().getLocation().get(0).getX() == food.getX() && p2.getSnake().getLocation().get(0).getY() == food.getY()) {
			p2.getSnake().increaseLength();
			food = new Food();
		}
	}
	
	private void checkOutOfBounds(Player p1, Player p2) {
		
		if(p1.getSnake().getLocation().get(0).getX() >= 800|| p1.getSnake().getLocation().get(0).getX() < 0 || p1.getSnake().getLocation().get(0).getY() >= 800 || p1.getSnake().getLocation().get(0).getY() < 0) {
			p1.reset();
		}
		
		if(p2.getSnake().getLocation().get(0).getX() >= 800|| p2.getSnake().getLocation().get(0).getX() < 0 || p2.getSnake().getLocation().get(0).getY() >= 800 || p2.getSnake().getLocation().get(0).getY() < 0) {
			p2.reset();
		}
	}
	
	private void checkCollision(Player p1, Player p2) {
		
		for(int i = 0; i < p2.getLocation().size(); i++) {
			if(p1.getSnake().getLocation().get(0).equals(p2.getSnake().getLocation().get(i))) {
				
				if(i == 0) {
					player1.reset();
					player2.reset();
				}
				
				player1.reset();
			}
		}
		
		for(int i = 0; i < p1.getLocation().size(); i++) {
			if(p2.getLocation().get(0).equals(p1.getLocation().get(i))) {
				
				if(i == 0) {
					player1.reset();
					player2.reset();
				}
				
				player2.reset();
			}
		}
	}

	public static void main(String[] args) {
		server = new SnakeServer();
		try {
			server.listenForPlayers();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		server.run();
	}

}
