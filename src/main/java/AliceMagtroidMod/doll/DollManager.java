package AliceMagtroidMod.doll;

import AliceMagtroidMod.doll.dolls.AbstractDoll;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.ArrayList;

public class DollManager {
	public AbstractPlayer owner;
	public int rowCount = 1;
	public ArrayList<ArrayList<AbstractDoll>> dolls = new ArrayList<>();
	
	int hourai_count = 0;
	
	public DollManager(AbstractPlayer owner) {
		this.owner = owner;
		this.dolls.add(new ArrayList<>());
	}
	
	float[] getPosition(int row, int col) {
		// TODO
		return new float[2];
	}
	
	static {
		int MAX_ROW_SIZE = 5;
		int MAX_ROW_COUNT = 5;
	}
	
	public void clear() {
		// TODO
	}
	
	public int getHouraiCount() {
		// TODO
		return hourai_count;
	}
	
	public void spawnDoll(int row) {
	
	}
	
	public ArrayList<AbstractDoll> getRightmostDolls() {
		ArrayList<AbstractDoll> res = new ArrayList<>();
		
		for (ArrayList<AbstractDoll> row : dolls)
			if (!row.isEmpty())
				res.add(row.get(0));
		
		return res;
	}
	
	public ArrayList<AbstractDoll> getAllDolls() {
		ArrayList<AbstractDoll> res = new ArrayList<>();
		
		for (ArrayList<AbstractDoll> row : dolls)
			res.addAll(row);
		
		return res;
	}
}
