package mario_pixel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Platform extends Rectangle {
	
	public static final int LOWEST_LEVEL = 700;
	public static final int HIGHEST_LEVEL = 400;
	public static final int MIN_LENGTH = 50;
	public static final int MAX_LENGTH = 250;
	double initialSpeed;
	Random random;
	
	public Platform(int x, int y, int width, int height, double speed) {
		super(x, y, width, height);
		initialSpeed = speed;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
	}
	
	public void move() {
		x -= initialSpeed;
	}
}
