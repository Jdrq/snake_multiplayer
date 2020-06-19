package com.serwer.snakeonline;

import java.io.IOException;

import com.serwer.entities.Food;
import com.serwer.snakeonline.players.Player;

public class Output implements Runnable{
	
	Player player;
	Player other;
	Food food;
	
	
	public Output(Player p, Player o, Food f) {
		this.player = p;
		this.other = o;
		this.food = f;
	}

	@Override
	public void run() {
			try {
				
				player.getObjectOutputStream().writeBoolean(true);
				player.getObjectOutputStream().writeObject(player.getSnake().getLocation());
				player.getObjectOutputStream().writeObject(other.getSnake().getLocation());
				player.getObjectOutputStream().writeInt(food.getX());
				player.getObjectOutputStream().writeInt(food.getY());
				player.getObjectOutputStream().reset();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
