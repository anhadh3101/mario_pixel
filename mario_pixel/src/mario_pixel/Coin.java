package mario_pixel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Coin extends Rectangle {
	
	double initialSpeed;
	
	public Coin(int x, int y, int width, int height, double speed) {
		super(x, y, width, height);
		initialSpeed = speed;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(x, y, width, height);
	}
	
	public void move() {
		x -= initialSpeed;
	}
}
