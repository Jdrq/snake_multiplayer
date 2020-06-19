package com.serwer.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.serwer.Constants;

public class Food extends Entity{
	
	public Food(int x, int y) {
		this.X = x;
		this.Y = y;
	}
	
	public Food() {
		this.X = new Random().nextInt(30) * Constants.size;
		this.Y = new Random().nextInt(30) * Constants.size;
	}
	
	public void reset() {
		this.X = new Random().nextInt(30) * Constants.size;
		this.Y = new Random().nextInt(30) * Constants.size;
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void render(Graphics g, Color c, int offsetX, int offsetY) {
		g.setColor(c);
		g.fillRect(offsetX+X, offsetY+Y, Constants.size, Constants.size);
	}
	
}
