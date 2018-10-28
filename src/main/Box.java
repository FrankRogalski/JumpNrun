package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Box {
	private double x, y, w, h, speed = 6;

	private GraphicsContext gc;

	public Box(GraphicsContext gc, double x, double y, double w, double h) {
		this.y = y;
		this.x = x;
		this.w = w;
		this.h = h;
		this.gc = gc;
	}

	public Box(GraphicsContext gc, double w, double h) {
		y = gc.getCanvas().getHeight() - h;
		x = gc.getCanvas().getWidth();
		this.w = w;
		this.h = h;
		this.gc = gc;
	}

	public void update() {
		x -= speed;
	}

	public void show() {
		gc.setFill(Color.BLACK);
		gc.fillRect(x, y, w, h);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}