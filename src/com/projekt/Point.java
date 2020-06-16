package com.projekt;

import java.io.Serializable;

public class Point implements Serializable{
	
	private static final long serialVersionUID = 1380192131039742215L;
	
	int x;
	int y;
	
	public Point(int x, int y){
		setPoint(x, y);
	}
	
	public Point(Point p) {
		setPoint(p);
	}
	
	public boolean equals(Point p) {
		return (this.x == p.x && this.y == p.y);
	}
	
	public void setPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPoint(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
