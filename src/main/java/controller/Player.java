import javax.swing.JLabel;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.ImageIcon;


public class Player extends JLabel{
	// player fields
	private boolean isSelected = false;
	private int xPos;
	private int yPos;
	private int id;

	// Constructor: sets location and id
	public Player(int x, int y, int identificationNumber) throws Exception{
		super(new ImageIcon(ImageIO.read(new File("../../../icons/roldo.png"))));

        
		xPos = x;
		yPos = y;
		id = identificationNumber;

		setBounds(x,y,50,50);

	}

	//getters and setters
	public void setX(int x) {
		xPos = x;
	}

	public void setY(int y) {
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

	public boolean isSelected() {
		return isSelected;
	}

}