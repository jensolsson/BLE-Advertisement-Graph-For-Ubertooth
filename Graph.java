//
// Graph.java - Will display BLE advertisement packets when output from spectool_raw is piped to it.
// Written by Jens Olsson <www.jensolsson.se>
//
// Compile: javac Graph.java
//
// Sample usage: sudo spectool_raw | java Graph
// Sample usage (remote machine has the ubertooth dongle): ssh -t user@host sudo spectool_raw | java Graph
//
// Open issues: Is the Ubertooth spectool fast enough to catch all advertisement packets or will some of them be ignored?


import javax.swing.*;
import java.util.*;   
import java.io.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;

public class Graph extends JFrame {
	
	private static Graph graph;
	private GraphArea graphArea;
	private ArrayList<Level> history = new ArrayList<Level>();
	
	int max=-1000;
	int min=1000;
	
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} 
		catch (Exception e) {
			// handle exception
		}
		
		graph = new Graph();
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {
				String line = buffer.readLine();
				String[] parts = line.split(" ");
				graph.add(parts[0+3], parts[24+3], parts[78+3]);
			}
			catch(Exception e) {}
		}
		
	}
	
	
	public Graph() {
		setTitle("BLE Advertisement Debugger");
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		graphArea = new GraphArea();
		getContentPane().add(graphArea);
		setVisible(true);
	}

	public void add(String l37, String l38, String l39) {
		try {
			min = Math.min(min, Integer.parseInt(l37));
			min = Math.min(min, Integer.parseInt(l38));
			min = Math.min(min, Integer.parseInt(l39));
			max = Math.max(max, Integer.parseInt(l37));
			max = Math.max(max, Integer.parseInt(l38));
			max = Math.max(max, Integer.parseInt(l39));
		}
		catch(Exception e) {}

		graphArea.add(new Level(l37, l38, l39));
		graphArea.repaint();
	}
	
	class Level {
		public int c37;
		public int c38;
		public int c39;
		public Level(String l37, String l38, String l39) {
			c37 = Integer.parseInt(l37);
			c38 = Integer.parseInt(l38);
			c39 = Integer.parseInt(l39);
		}
	}
	
	class GraphArea extends JComponent {
		private Image backbuffer;
		private Graphics backg;
		private int w;
		private int h;
		
		public GraphArea() {

		}
		
		public void add(Level l) {
			if(backbuffer == null) {
				w = getSize().width;
				h = getSize().height;
				backbuffer = createImage(1024, 768);
				backg = backbuffer.getGraphics();
				backg.setColor(Color.black);
				backg.fillRect(0, 0, 1024, 768);
			}
			
			backg.drawImage(backbuffer, -1, 0, this);
			
			
			backg.setColor(Color.black);
			backg.fillRect(1023, 0, 1, 768);
			
			
			backg.setColor(new Color(Math.min(255, 270+l.c37), 0, 0));
			backg.fillRect(1023, 100-l.c37/4, 1, 50+l.c37/2);
			
			if(l.c37 > -40) {
				char[] db = (l.c37 + "dB").toCharArray();
				backg.drawChars(db, 0, db.length, 980, 100+l.c37/4+5);
			}

			backg.setColor(new Color(Math.min(255, 270+l.c38), 0, 0));
			backg.fillRect(1023, 300-l.c38/4, 1, 50+l.c38/2);

			if(l.c38 > -40) {
				char[] db = (l.c38 + "dB").toCharArray();
				backg.drawChars(db, 0, db.length, 980, 300+l.c38/4+5);
			}


			backg.setColor(new Color(Math.min(255, 270+l.c39), 0, 0));
			backg.fillRect(1023, 500-l.c39/4, 1, 50+l.c39/2);
			
			if(l.c39 > -40) {
				char[] db = (l.c39 + "dB").toCharArray();
				backg.drawChars(db, 0, db.length, 980, 500+l.c39/4+5);
			}
			
			
			repaint();
		}
		
		
		public void paint(Graphics g) {
			g.drawImage(backbuffer, 0, 0, this);
		}
	}
	

}
