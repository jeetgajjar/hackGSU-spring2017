package leapGUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel implements View
{
	int time = 0;
	int dotSize = 10;
	
	public Canvas (int width, int height)
	{
		super();
		this.setSize(width, height);
	}
	
	private int mapValue (int value, int srcRange, int destRange)
	{
		return (int) (((double)value / (double)srcRange) * (double)destRange);
	}
	
	@Override
	public int selectPlayer(int x, int y) {
		// TODO Auto-generated method stub
		this.repaint();
		return 0;
	}

	@Override
	public void releasePlayer(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int drawPlayer(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTime(int t)
	{
		time = mapValue(t, 1000000, this.getWidth()-(dotSize+40)); // 40 space to window border
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// Time Slider
		g.setColor(Color.RED);
		g.drawOval(time+20,this.getHeight()-(10+dotSize),dotSize,dotSize);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(20, this.getHeight()-(10+dotSize), this.getWidth()-40, dotSize);
	}

}
