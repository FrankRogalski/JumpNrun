package main;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Spielfeld {
	Random r = new Random();
	private ArrayList<Box> boxes = new ArrayList<Box>();
	
	private GraphicsContext gc;
	private KI ki;

	private int warten = 0;
	
	public Spielfeld(GraphicsContext gc) {
		this.gc = gc;
		ki = new KI(gc, 10);
		ki.nextPlayer();
		reset();
	}
	
	public void reset() {
		double w = gc.getCanvas().getWidth(),
				h = gc.getCanvas().getHeight();
		
		boxes = new ArrayList<Box>();
		boxes.add(new Box(gc, 0, h / 4 * 3, w, h / 4));
	}
	
	public void show() {
		gc.setFill(Color.BLACK);
		gc.fillText("score: " + System.getProperty("line.separator") + ki.allScores(), 10, 10);
		gc.fillText("Durchschnitt: " + System.getProperty("line.separator") + ki.avgScores(), 100, 10);

		warten += boxes.get(0).getSpeed();
		if (warten > gc.getCanvas().getWidth() / 2) {
			boxes.add(new Box(gc, 
					gc.getCanvas().getWidth() / 4 + r.nextInt((int) gc.getCanvas().getWidth() / 4), 
					gc.getCanvas().getHeight() / 4 + r.nextInt((int) gc.getCanvas().getHeight() / 4)
					));
			warten = 0;
		}

		for (int i = boxes.size() - 1; i >= 0; i--) {
			Box b = boxes.get(i);
			b.update();
			b.show();
			if (b.getX() + b.getW() <= 0) {
				boxes.remove(i);
			}
		}
		
		if (ki.playerJumps(boxes)) {
			reset();
		}
	}
}