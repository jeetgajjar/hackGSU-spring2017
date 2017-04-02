package model;

public class Player{
	// player fields
	private boolean isSelected = false;
	private double xPos;
	private double yPos;
	private int id;

	// Constructor: sets location and id
	public Player(double x, double y, int identificationNumber) {
		xPos = x;
		yPos = y;
		id = id;
	}

	//getters and setters
	public void setX(double x) {
		xPos = x;
	}

	public void setY(double y) {
		yPos = y;
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public int getID() {
		return id;
	}

}