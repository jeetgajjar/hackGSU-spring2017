package leapGUI;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JavaWindow extends JFrame
{
	final int WIDTH=1010, HEIGHT=500;
	
	int lastID=0;
	Canvas canvas;
	
	public JavaWindow ()
	{
		super();
		
		this.setTitle("LEAP");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		canvas = new Canvas(WIDTH, HEIGHT);
		
		this.add(canvas);
		this.pack();
		this.setSize(WIDTH,HEIGHT);
		this.setVisible(true);
	}
	
	public View getView()
	{
		return this.canvas;
	}
	
}
