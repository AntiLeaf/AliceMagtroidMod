package AliceMagtroidMod.doll;

import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.doll.dolls.HouraiDoll;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.ArrayList;
import java.util.Queue;

public class DollManager {
	public AbstractPlayer owner;
	public int rowCount = 1;
	public ArrayList<ArrayList<AbstractDoll>> dolls = new ArrayList<>();
	
	private Queue<SpawnInfo> spawnQueue;
	private Queue<ActInfo> actQueue;
	
	int hourai_count = 0;
	
	public DollManager(AbstractPlayer owner) {
		this.owner = owner;
		this.dolls.add(new ArrayList<>());
	}
	
	public float[] getPosition(int row, int col) {
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
		hourai_count = 0;
		for (ArrayList<AbstractDoll> row : dolls)
			for (AbstractDoll doll : row)
				if (doll instanceof HouraiDoll)
					hourai_count++;
		
		return hourai_count; // TODO: may be optimized later
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
	
	public int getRow(AbstractDoll doll) {
		for (int i = 0; i < dolls.size(); i++)
			if (dolls.get(i).contains(doll))
				return i;
		
		return -1;
	}
	
	public int getCol(AbstractDoll doll) {
		for (ArrayList<AbstractDoll> row : dolls)
			if (row.contains(doll))
				return row.indexOf(doll);
		
		return -1;
	}
	
	public int[] getRowAndCol(AbstractDoll doll) {
		for (int i = 0; i < dolls.size(); i++)
			if (dolls.get(i).contains(doll))
				return new int[]{i, dolls.get(i).indexOf(doll)};
		
		return new int[]{-1, -1};
	}
	
	public void atStartOfTurn() {
		for (ArrayList<AbstractDoll> row : dolls)
			for (AbstractDoll doll : row)
				if (doll.actAtStartOfTurn)
					doll.act(AbstractDoll.ActTiming.START_OF_TURN);
	}
	
	public void atEndOfTurn() {
		for (ArrayList<AbstractDoll> row : dolls)
			for (AbstractDoll doll : row)
				if (doll.actAtEndOfTurn)
					doll.act(AbstractDoll.ActTiming.END_OF_TURN);
	}
	
	public void pushSpawnQueue(AbstractDoll doll, AbstractDoll.SpawnPosition position, int row, int col) {
		spawnQueue.add(new SpawnInfo(doll, position, row, col));
	}
	
	public void clearSpawnQueue() {
		while (!spawnQueue.isEmpty()) {
			SpawnInfo info = spawnQueue.peek();
			this.spawnDoll(info.doll, info.position, info.row, info.col);
			
			spawnQueue.remove();
		}
	}
	
	public void spawnDoll(AbstractDoll doll, AbstractDoll.SpawnPosition position, int row, int col) {
		// TODO
	}
	
	public void pushActQueue(AbstractDoll doll, AbstractDoll.ActTiming timing) {
		actQueue.add(new ActInfo(doll, timing));
	}
	
	public void clearActQueue() {
		while (!actQueue.isEmpty()) {
			ActInfo info = actQueue.peek();
			info.doll.act(info.timing);
			
			actQueue.remove();
		}
	}
	
	public void act(AbstractDoll doll, AbstractDoll.ActTiming timing) {
		this.pushActQueue(doll, timing);
		this.clearActQueue();
	}
	
	public static class SpawnInfo {
		AbstractDoll doll;
		AbstractDoll.SpawnPosition position;
		int row, col;
		
		public SpawnInfo(AbstractDoll doll, AbstractDoll.SpawnPosition position, int row, int col) {
			this.doll = doll;
			this.position = position;
			this.row = row;
			this.col = col;
		}
		
		public SpawnInfo(AbstractDoll doll, AbstractDoll.SpawnPosition position, int row) {
			this(doll, position, row, 0);
		}
	}
	
	public static class ActInfo {
		AbstractDoll doll;
		AbstractDoll.ActTiming timing;
		
		public ActInfo(AbstractDoll doll, AbstractDoll.ActTiming timing) {
			this.doll = doll;
			this.timing = timing;
		}
	}
}
