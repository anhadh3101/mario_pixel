package mario_pixel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Mario extends Rectangle {
	
	double initialVelocity = 2;
	double xVelocity;
	double yVelocity;
	int jumpVelocity = 8;
	int jumps = 2;
	boolean deactivateW = false;
	boolean deactivateA = false;
	boolean deactivateS = false;
	boolean deactivateD = false;
	
	public Mario(int x, int y, int width, int height) {
		super(x, y, width, height);
		xVelocity = 0;
		yVelocity = initialVelocity;
	}
	
	public void setXVelocity(double newVelocity) {
		xVelocity = newVelocity;
	}
	
	public double getXVelocity() {
		return xVelocity;
	}
	
	public void setYVelocity(double newVelocity) {
		yVelocity = newVelocity; 	
	}
	
	public double getYVelocity() {
		return yVelocity;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
	
	public void move() {
		y += yVelocity;
		x += xVelocity;
		yVelocity += 0.1;
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == e.VK_W && !deactivateW && jumps > 0) {
			jumps--;
			deactivateA = false;
			deactivateS = false;
			deactivateD = false;
			yVelocity = -jumpVelocity;
		}
		if (e.getKeyCode() == e.VK_S && !deactivateS) {
			deactivateA = false;
			deactivateD = false;
			yVelocity = jumpVelocity;
		}
		if (e.getKeyCode() == e.VK_A && !deactivateA) {
			deactivateD = false;
			xVelocity = -initialVelocity;
		}
		if (e.getKeyCode() == e.VK_D && !deactivateD) {
			deactivateA = false;
			xVelocity = Math.abs(initialVelocity);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == e.VK_A) {
			xVelocity = 0;
		}
		if (e.getKeyCode() == e.VK_D) {
			xVelocity = 0;
		}
	}
	
}
