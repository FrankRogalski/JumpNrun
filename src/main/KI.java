package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;

public class KI {
	private GraphicsContext gc;
	private Player[] players;
	private Player current;
	private int playerID = 0;
	private ArrayList<Double> avg = new ArrayList<Double>();

	public KI(GraphicsContext gc, int popanz) {
		players = new Player[popanz];
		Random r = new Random();
		double panicButtonX, panicButtonY;

		for (int i = 0; i < players.length; i++) {
			panicButtonX = map(r.nextInt(101), 0, 100, 0.25, 0.5);
			panicButtonY = map(r.nextInt(101), 0, 100, 0.25, 0.5);
			players[i] = new Player(gc, panicButtonX, panicButtonY);
		}
		this.gc = gc;
		avg.add((double) 0);
	}

	public boolean playerJumps(ArrayList<Box> boxes) {
		DecimalFormat df = new DecimalFormat("###,###.###");
		double jump, distX, distY;

		int posTextY = 25, i = 1;

		final double h = gc.getCanvas().getHeight(), w = gc.getCanvas().getWidth(), panicX = current.getPanicButtonX(),
				panicY = current.getPanicButtonY();
		
		gc.fillText("PanicX: " + df.format(panicX), 400, 10);
		gc.fillText("PanicY: " + df.format(panicY), 400, 25);

		gc.fillText("Jump Werte:", 250, 10);
		
		for (Box b : boxes) {
			if (b.getX() > current.getX() + current.getW()) {
				distX = b.getX() - (current.getX() + current.getW());
				distY = b.getY() - (current.getY() + current.getH());
				jump = map(distX, 0, w, panicX, 0);
				jump += map(distY, h, -h, -panicY, panicY);
				gc.fillText("Box " + i + " Jump Wert: " + df.format(jump), 250, posTextY);
				i++;
				posTextY += 15;
				if (jump >= 0.35) {
					current.jump();
					break;
				}
			}
		}
		current.update();
		if (current.touching(boxes)) {
			nextPlayer();
			return true;
		}
		current.show();
		return false;

	}

	public String avgScores() {
		if (avg.size() > 0) {
			String avgs = "";
			double scores = 0, anz = 0;
			for (int i = 0; i < players.length; i++) {
				scores += players[i].getScore();
				if (players[i].getScore() > 0) {
					anz++;
				}
			}
			scores /= anz;
			avg.set(avg.size() - 1, scores);
			for (int i = avg.size() - 10; i < avg.size(); i++) {
				if (i >= 0) {
					avgs += "Generation: " + (i + 1) + ": " + (Math.round(avg.get(i) * 100) / 100.0)
							+ System.getProperty("line.separator");
				}
			}
			return avgs;
		}
		return "";
	}

	public String allScores() {
		String scores = "";
		for (int i = 0; i < players.length; i++) {
			scores += players[i].getScore() + System.getProperty("line.separator");
		}
		return scores;
	}

	public void nextPlayer() {
		if (playerID == players.length) {
			generate(selektion());
			playerID = 0;
			avg.add((double) 0);
		}
		current = players[playerID];
		playerID++;
	}

	private long selektion() {
		long maxScore = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i].getScore() > maxScore) {
				maxScore = players[i].getScore();
			}
		}
		return maxScore;
	}

	private void generate(long maxScore) {
		Player partner;
		Random r = new Random();
		long partnerScore;
		int index;
		byte ran;

		for (int i = 0; i < players.length; i++) {
			do {
				index = r.nextInt(players.length);
				partner = players[index];
				partnerScore = (long) map(partner.getScore(), 0, maxScore, 0, 100);
				
				ran = (byte) r.nextInt(101);
			} while (partnerScore >= ran);
			players[i] = players[i].mate(partner);
			players[i].mutate();
		}
	}

	public static double map(double value, double min, double max, double nMin, double nMax) {
		return ((value - min) / (max - min)) * (nMax - nMin) + nMin;
	}

	public Player getCurrent() {
		return current;
	}

	public void setCurrent(Player current) {
		this.current = current;
	}
}