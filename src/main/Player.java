package main;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {
	private double x, y, w = 50, h = 50, speed = 0, panicButtonX, panicButtonY, grav = 10;

	private long score = 0;

	private byte jumped = 2;

	private GraphicsContext gc;

	public Player(GraphicsContext gc, double panicButtonX, double panicButtonY) {
		x = gc.getCanvas().getWidth() * 0.2;
		y = gc.getCanvas().getHeight() * 0.6;
		this.gc = gc;
		this.panicButtonX = panicButtonX;
		this.panicButtonY = panicButtonY;
	}

	public void update() {
		speed++;
		if (speed > grav) {
			speed = grav;
		}
		y += speed;
	}

	public void jump() {
		if (jumped < 2) {
			speed -= gc.getCanvas().getWidth() / 80 + gc.getCanvas().getHeight() / 80;
			jumped++;
		}
	}

	public boolean touching(final ArrayList<Box> boxes) {
		for (Box b : boxes) {
			if (y + w > b.getY() && b.getX() <= x + w && b.getX() + b.getW() >= x) {
				if (y + w * 0.75 >= b.getY()) {
					return true;
				}
				jumped = 0;
				y = b.getY() - w;
			} else if (y + w >= gc.getCanvas().getHeight()) {
				return true;
			}
		}
		score++;
		return false;
	}

	public void show() {
		gc.setFill(Color.PINK);
		gc.fillRect(x, y, w, h);
	}

	public void mutate() {
		Random r = new Random();
		if (r.nextDouble() < 0.01) {
			panicButtonX = KI.map(r.nextInt(101), 0, 100, 0.25, 0.5);
			panicButtonY = KI.map(r.nextInt(101), 0, 100, 0.25, 0.5);
		}
	}

	public Player mate(Player partner) {
		double panicX, panicY;
		Random r = new Random();
		if (r.nextBoolean() == true) {
			panicX = panicButtonX;
			panicY = partner.getPanicButtonY();
		} else {
			panicX = partner.getPanicButtonX();
			panicY = panicButtonY;
		}
		return new Player(gc, panicX, panicY);
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
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

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getPanicButtonX() {
		return panicButtonX;
	}

	public void setPanicButton(double panicButtonX) {
		this.panicButtonX = panicButtonX;
	}

	public double getPanicButtonY() {
		return panicButtonY;
	}

	public void setPanicButtonY(double panicButtonY) {
		this.panicButtonY = panicButtonY;
	}
}