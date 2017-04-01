package leapGUI;

// All position and time values between 0 and 1,000,000

public interface View
{
	public int selectPlayer (int x, int y);
	// returns an id for the selected player
	
	public void releasePlayer (int id);
	
	public int drawPlayer (int x, int y);
	
	public void setTime (int t);
}
