package mario_pixel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
	
	public static final int GAME_WIDTH = 1000;
	public static final int GAME_HEIGHT = 1000;
	public static final Dimension GAME_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	public static final int MARIO_HEIGHT = 20;
	public static final int PLATFORM_HEIGHT = 20;
	public static final int MAX_DISTANCE = 600;
	public static final int MIN_DISTANCE = 300;
	public static final int COIN_DIAMETER = 10;
	int marioX, marioY;
	double platformSpeed = 1;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Mario mario;
	Platform[] platforms = new Platform[3];
	Coin[] coins = new Coin[3];
	int platformIndex = 0;
	int coinsCollected = 0;
	int highScore = 0;
	boolean running;
	Scanner scnr = new Scanner(System.in);
	
	public GamePanel() {
		running = true;
		newPlatformAndCoin();
		newMario();
		this.setFocusable(true);
		this.addKeyListener(new ActionListener());
		this.setPreferredSize(GAME_SIZE);
		random = new Random();
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void newMario() {
		mario = new Mario(350, 600, MARIO_HEIGHT, MARIO_HEIGHT);
	}
	
	public void newPlatformAndCoin() {
		platforms[platformIndex] = new Platform(0, 620, GAME_WIDTH, PLATFORM_HEIGHT, platformSpeed);
		coins[platformIndex] = new Coin(750, 610, COIN_DIAMETER, COIN_DIAMETER, platformSpeed);
	}
	
	public void nextPlatformAndCoin() {
		int someY = GAME_HEIGHT - random.nextInt(MIN_DISTANCE, MAX_DISTANCE);
		int someWidth = random.nextInt(MIN_DISTANCE, MAX_DISTANCE);
		platforms[platformIndex % 3] = new Platform(GAME_WIDTH, someY, someWidth, PLATFORM_HEIGHT, platformSpeed);
		coins[platformIndex % 3] = new Coin(random.nextInt(GAME_WIDTH, GAME_WIDTH + someWidth), someY - COIN_DIAMETER, COIN_DIAMETER, COIN_DIAMETER, platformSpeed);
	}
	
	public void coinCollected(Coin coin) {
		coin.translate(GAME_WIDTH, GAME_HEIGHT);
	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
	}
	
	public void draw(Graphics g) {
		if (running) {
			mario.draw(g);
			for (int i = 0; i < platforms.length; i++) {
				if (platforms[i] != null) {
					platforms[i].draw(g);
					coins[i].draw(g);
				}
			}
			g.setColor(Color.white);
			g.setFont(new Font("Consolas", Font.PLAIN, 30));
			g.drawString("Score: " + coinsCollected, 0, 30);
			if (coinsCollected > highScore) {
				highScore = coinsCollected;
			}
			g.drawString("HighScore: " + highScore, 0, 60);
		}
		else {
			gameOver(g);
		}
	}
	
	public void move() {
		mario.move();
		for (int i = 0; i < platforms.length; i++) {
			if (platforms[i] != null) {
				platforms[i].move();
				coins[i].move();
			}
		}
	}
	
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Consolas", Font.PLAIN, 100));
		g.drawString("GAME OVER", 200, 430);
	}
	
	public void checkCollision() {
		for (int i = 0; i < platforms.length; i++) {
			if (platforms[i] != null) {
				// Mario lands on platform
				if (mario.intersects(platforms[i]) && mario.getCenterY() < platforms[i].getCenterY()) {
					mario.setYVelocity(0);
					mario.jumps = 2;
					mario.deactivateS = true;
				}
				// Mario hits platform from below
				if (mario.intersects(platforms[i]) && mario.getCenterY() > platforms[i].getCenterY()) {
					mario.setYVelocity(Math.abs(mario.getYVelocity()));	
				}	
				// Mario hits platform from right
				if (mario.intersects(platforms[i]) && mario.getCenterX() > platforms[i].getMaxX()) {
					mario.setXVelocity(0);
					mario.deactivateA = true;
				}
				// Mario hits platform from left
				if (mario.intersects(platforms[i]) && mario.getCenterX() < platforms[i].getMinX()) {
					mario.setXVelocity(-platformSpeed);
					mario.deactivateD = true;
				}
				if (mario.intersects(coins[i])) {
					coinsCollected++;
					coinCollected(coins[i]);
				}
			}
		}
	
		// Mario goes off screen
		if (mario.getCenterX() < -50 || mario.getCenterX() > GAME_WIDTH + 50) {
			running = false;
		}
		if (mario.getCenterY() < -200 || mario.getCenterY() > GAME_HEIGHT + 50) {
			running = false;
		}
	}
	
	public void run() {
		// Game Loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				move();
				checkCollision();
				if (GAME_WIDTH - platforms[platformIndex % 3].getMaxX() > random.nextInt(MIN_DISTANCE, MAX_DISTANCE)) {
					platformIndex++;
					platformSpeed += 1;
					nextPlatformAndCoin();
				}
				repaint();
				delta--;
			}
		}
	}
	
	public class ActionListener extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			mario.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e) {
			mario.keyReleased(e);
		}
	}
}
