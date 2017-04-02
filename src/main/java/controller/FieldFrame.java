
public class FieldFrame {
	private int[][] roster;
	private int size;

	public FieldFrame() {
		size = 10;
		roster = new int[10][2];
		for (int i = 0; i < size; i++) {
			roster[i] = new int[] {i,i};
		}
	}

	public int[][] getRoster() {
		return roster;
	}


}