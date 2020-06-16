package com.projekt.game;

import java.util.ArrayList;

import com.projekt.entities.Direction;
import com.projekt.entities.Food;
import com.projekt.entities.Snake;

public class LocalPlayer extends GameMode implements Runnable{
	
	ArrayList<Snake> snakes;
	Food food;
	
	// Game Properties
	public static boolean running = false;
	public static boolean paused = false;
	static int gameSpeed = 100000000;
	
	public LocalPlayer() {
		snakes = new ArrayList<Snake>();
		snakes.add(new Snake());
		snakes.add(new Snake(780, 580, Direction.left));
		food = new Food();
	}
	
	public void reset() {
		snakes.get(0).reset();
		snakes.get(1).reset();
		food.reset();
		running = false;
		paused = false;
	}

	@Override
	public void run() {
		long last = System.nanoTime();
		running = true;
		
		while(running) {
			if(!paused) {
				if(System.nanoTime() - last > gameSpeed) {
					last = System.nanoTime();
					tick();
				}
			}
			
		}
		
	}
	
	private void tick() {
		snakes.get(0).tick();
		snakes.get(1).tick();
		food.tick();
		
		if(snakes.get(0).checkCollisionWith(food)) {
			food = new Food();
		}
		
		if(snakes.get(1).checkCollisionWith(food)) {
			food = new Food();
		}
		
		if(snakes.get(0).checkCollisionWith(snakes.get(1))) {
			food = new Food();
		}
		
		if(snakes.get(1).checkCollisionWith(snakes.get(0))) {
			food = new Food();
		}
	}
	
	
	public Snake getSnake(int x) {
		return snakes.get(x);
	}
	
	public Food getFood() {
		return food;
	}
}
