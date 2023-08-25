package mario_pixel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFrame extends JFrame {
	
	GamePanel panel;
	
	public GameFrame() {
		panel = new GamePanel();
		this.add(panel);
		this.setTitle("Mario Pixel");
		this.setBackground(Color.blue);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
