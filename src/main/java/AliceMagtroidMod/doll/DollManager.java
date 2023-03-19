package AliceMagtroidMod.doll;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.doll.dolls.HouraiDoll;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DollManager {
	public static int MAX_ROW_SIZE = 5;
	public static int MAX_ROW_COUNT = 5;
	
	public AbstractPlayer owner;
	public int rowCount = 1;
	public ArrayList<ArrayList<AbstractDoll>> dolls = new ArrayList<>();
	
	private ArrayDeque<SpawnInfo> spawnQueue;
	private ArrayDeque<ActInfo> actQueue;
	
	int hourai_count = 0;
	
	public DollManager(AbstractPlayer owner) {
		this.owner = owner;
		this.dolls.add(new ArrayList<>());
	}
	
	public float[] getPosition(int row, int col) {
		// TODO
		return new float[2];
	}
	
	public void init() {
		this.rowCount = 1;
		this.dolls = new ArrayList<>();
		this.dolls.add(new ArrayList<>());
		this.spawnQueue = new ArrayDeque<>();
		this.actQueue = new ArrayDeque<>();
		
		this.hourai_count = 0;
	}
	
	public void clear() {
		this.rowCount = 0;
		
		for (ArrayList<AbstractDoll> row : dolls)
			row.clear();
		this.dolls.clear();
		this.dolls = null;
		
		this.spawnQueue.clear();
		this.spawnQueue = null;
		
		this.actQueue.clear();
		this.actQueue = null;
		
		this.hourai_count = -1; // I don't know if it is needed
	}
	
	private void handleSpawnDoll(AbstractDoll doll, AbstractDoll.Position position, int row, int col) {
		if (row >= dolls.size()) {
			// This should not be permitted. Report this issue.
			AliceMagtroidMod.logger.error("DollManager.spawnDoll: row >= dolls.size()");
			return;
		}
		
		if (col >= MAX_ROW_SIZE) {
			// This should not be permitted too. Report this issue.
			AliceMagtroidMod.logger.error("DollManager.spawnDoll: col > MAX_ROW_SIZE");
			return;
		}
		
		ArrayList<AbstractDoll> vec = dolls.get(row);
		if (vec.size() == MAX_ROW_SIZE) {
			AbstractDoll leftmost = vec.get(vec.size() - 1);
			leftmost.onRecycled();
			leftmost.onBrokenOrRecycled();
			
			vec.remove(vec.size() - 1);
			// Then the doll should be already removed
		}
		
		doll.onSpawn();
		
		if (position == AbstractDoll.Position.LEFTMOST) {
			vec.add(0, doll);
		} else if (position == AbstractDoll.Position.RIGHTMOST ||
				position == AbstractDoll.Position.UNSPECIFIED) {
			vec.add(doll);
		} else if (position == AbstractDoll.Position.MANUAL) {
			vec.add(col, doll);
		}
	}
	
	public int getHouraiCount() {
		return (int) dolls.stream()
				.flatMap(List::stream)
				.filter(doll -> (doll instanceof HouraiDoll))
				.count();
		// TODO: may be optimized later
	}
	
	public ArrayList<AbstractDoll> getRightmostDolls() {
		return dolls.stream()
				.map(row -> row.get(0))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<AbstractDoll> getAllDolls() {
		return dolls.stream()
				.flatMap(List::stream)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public ArrayList<AbstractDoll> getSpecificDolls(Predicate<AbstractDoll> predicate) {
		return dolls.stream()
				.flatMap(List::stream)
				.filter(predicate)
				.collect(Collectors.toCollection(ArrayList::new));
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
		for (AbstractDoll doll : this.getSpecificDolls(doll -> doll.actAtStartOfTurn))
			doll.act(AbstractDoll.ActTiming.START_OF_TURN);
	}
	
	public void atEndOfTurn() {
		for (AbstractDoll doll : this.getSpecificDolls(doll -> doll.actAtEndOfTurn))
			doll.act(AbstractDoll.ActTiming.END_OF_TURN);
	}
	
	public void pushSpawnQueue(AbstractDoll doll, AbstractDoll.Position position, int row, int col) {
		if ((position == AbstractDoll.Position.UNSPECIFIED) == (col == -1))
			AliceMagtroidMod.logger.error("DollManager.pushSpawnQueue: position and col are not consistent");
		
		spawnQueue.addLast(new SpawnInfo(doll, position, row, col));
	}
	
	public void pushSpawnQueue(AbstractDoll doll, AbstractDoll.Position position, int row) {
		this.pushSpawnQueue(doll, position, row, -1);
	}
	
	public void clearSpawnQueue() {
		while (!spawnQueue.isEmpty()) {
			SpawnInfo info = spawnQueue.pollFirst();
			this.handleSpawnDoll(info.doll, info.position, info.row, info.col);
		}
	}
	
	public void spawn(AbstractDoll doll, AbstractDoll.Position position, int row, int col) {
		this.pushSpawnQueue(doll, position, row, col);
		this.clearSpawnQueue();
	}
	
	public void spawn(AbstractDoll doll, AbstractDoll.Position position, int row) {
		this.pushSpawnQueue(doll, position, row);
		this.clearSpawnQueue();
	}
	
	public void pushActQueue(AbstractDoll doll, AbstractDoll.ActTiming timing) {
		actQueue.addLast(new ActInfo(doll, timing));
	}
	
	public void clearActQueue() {
		while (!actQueue.isEmpty()) {
			ActInfo info = actQueue.pollFirst();
			info.doll.act(info.timing);
		}
	}
	
	public void act(AbstractDoll doll, AbstractDoll.ActTiming timing) {
		this.pushActQueue(doll, timing);
		this.clearActQueue();
	}
	
	public void move(AbstractDoll doll, AbstractDoll.Position position, int row, int col) {
		int[] rowAndCol = this.getRowAndCol(doll);
		if (rowAndCol[0] == -1) {
			AliceMagtroidMod.logger.error("DollManager.move: doll not found");
			return;
		}
		
		this.dolls.get(rowAndCol[0]).remove(rowAndCol[1]);
		this.dolls.get(row).add(col, doll);
		
		// TODO: maybe more things to do
	}
	
	public static class SpawnInfo {
		AbstractDoll doll;
		AbstractDoll.Position position;
		int row, col;
		
		public SpawnInfo(AbstractDoll doll, AbstractDoll.Position position, int row, int col) {
			this.doll = doll;
			this.position = position;
			this.row = row;
			this.col = col;
		}
		
		public SpawnInfo(AbstractDoll doll, AbstractDoll.Position position, int row) {
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
