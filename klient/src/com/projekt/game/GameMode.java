package com.projekt.game;

import com.projekt.controls.Input;
import com.projekt.entities.Food;
import com.projekt.entities.Snake;

public class GameMode {
	
	Snake snake;
	Food food;
	boolean paused = false;
	Input input;
	
	public Snake getSnake() {
		return snake;
	}
	
	public Food getFood() {
		return food;
	}
}
