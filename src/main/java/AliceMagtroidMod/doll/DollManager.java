package AliceMagtroidMod.doll;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.action.dolls.WaitDollsToMoveAction;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.doll.dolls.HouraiDoll;
import AliceMagtroidMod.doll.dolls.KyotoDoll;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DollManager {
	public static int MAX_ROW_SIZE = 5;
	public static int MAX_ROW_COUNT = 5;
	
	public AbstractPlayer owner;
	public int rowCount = 1;
	public ArrayList<ArrayList<AbstractDoll>> dolls = new ArrayList<>();
	
//	private ArrayDeque<SpawnInfo> spawnQueue;
//	private ArrayDeque<ActInfo> actQueue;
	
	int hourai_count = 0;
	
	public DollManager(AbstractPlayer owner) {
		this.owner = owner;
		this.dolls.add(new ArrayList<>());
	}
	
	public float[] calcLeftTopPosition(int row, int col) {
		float[] position = new float[2];
		
		position[0] = owner.drawX - 200.0F + col * 100.0F;
		position[1] = owner.drawY + 200.0F - row * 100.0F;
		// TODO: I don't know if copilot is joking
		return position;
	}
	
	public void init() {
		this.rowCount = 1;
		this.dolls = new ArrayList<>();
		this.dolls.add(new ArrayList<>());
		
		this.hourai_count = 0;
	}
	
	public void clear() {
		this.rowCount = 0;
		
		for (ArrayList<AbstractDoll> row : dolls)
			row.clear();
		this.dolls.clear();
		this.dolls = null;
		
		this.hourai_count = -1; // I don't know if it is needed
	}
	
	public boolean isRowFull(int row) {
		return dolls.get(row).size() >= MAX_ROW_SIZE;
	}
	
	public int getHouraiCount() {
		return (int) dolls.stream()
				.flatMap(List::stream)
				.filter(doll -> (doll instanceof HouraiDoll))
				.count();
		// TODO: may be optimized later
	}
	
	public AbstractDoll getDoll(int row, int col) {
		return dolls.get(row).get(col);
	}
	
	public AbstractDoll getLeftmostDoll(int row) {
		return dolls.get(row).get(dolls.get(row).size() - 1);
	}
	
	public AbstractDoll getRightmostDoll(int row) {
		return dolls.get(row).get(0);
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

	public int countSpecificDolls(Predicate<AbstractDoll> predicate) {
		return (int) dolls.stream()
				.flatMap(List::stream)
				.filter(predicate)
				.count();
	}

	public int countSpecificDolls(Class<?> dollClass) {
		return this.countSpecificDolls(doll -> doll.getClass() == dollClass);
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

	public void clearBlockOnPlayerTurnStart() {
		int kyoto_count = this.countSpecificDolls(KyotoDoll.class);
		int max_preserved_block = kyoto_count * KyotoDoll.BLOCK_PRESERVED;

		for (AbstractDoll doll : this.getAllDolls())
			if (doll.block > max_preserved_block)
				doll.block = max_preserved_block;

		this.applyPowersForAllDolls();
	}
	
	public int getMaxBlockPreserved() {
		int kyoto_count = this.countSpecificDolls(KyotoDoll.class);
		return kyoto_count * KyotoDoll.BLOCK_PRESERVED;
	}
	
	public void atStartOfTurn() {
		for (AbstractDoll doll : this.getSpecificDolls(doll -> doll.actAtStartOfTurn))
			doll.act(AbstractDoll.ActTiming.START_OF_TURN);
	}
	
	public void atEndOfTurn() {
		for (AbstractDoll doll : this.getSpecificDolls(doll -> doll.actAtEndOfTurn))
			doll.act(AbstractDoll.ActTiming.END_OF_TURN);
	}
	
	public void reserveForSpawn(int row, int col) {
		dolls.get(row).add(null);
		
		for (int i = dolls.get(row).size() - 1; i >= col; i--)
			this.move(dolls.get(row).get(i), AbstractDoll.Position.MANUAL, row, i + 1);
		
		// the action should be added outside this function
	}
	
	public void updateMovingDolls(AbstractGameAction action, float time) {
		for (AbstractDoll doll : this.getSpecificDolls(doll -> doll.source[0] != -1))
			doll.updateWhileMoving(action, time);
	}
	
	public void resetAllSourceAndDest() {
		for (AbstractDoll doll : this.getAllDolls())
			doll.resetSourceAndDest();
	}
	
	public void removeEmptyEnds() {
		for (ArrayList<AbstractDoll> row : dolls) {
			while (row.size() > 0 && row.get(row.size() - 1) == null)
				row.remove(row.size() - 1);
		}
	}

	public void applyPowersForAllDolls() {
		for (AbstractDoll doll : this.getAllDolls())
			doll.applyPowers();
	}
	
	public void spawn(AbstractDoll doll, AbstractDoll.Position position, int row, int col) {
		// If position == MANUAL then assert the position is valid and = null
		if (position == AbstractDoll.Position.MANUAL) {
			if (this.dolls.get(row).get(col) != null) {
				AliceMagtroidMod.logger.error("DollManager.spawn: position is not empty");
				return;
			}
			
			this.dolls.get(row).set(col, doll);
		}
		else if (position == AbstractDoll.Position.LEFTMOST) {
			if (this.isRowFull(row)) {
				AliceMagtroidMod.logger.error("DollManager.spawn: row is full");
				return;
			}
			
			this.dolls.get(row).add(doll);
		}
		else if (position == AbstractDoll.Position.RIGHTMOST ||
				position == AbstractDoll.Position.UNSPECIFIED) {
			if (this.isRowFull(row)) {
				AliceMagtroidMod.logger.error("DollManager.spawn: row is full");
				return;
			}
			
			this.dolls.get(row).add(0, doll);
		}
	}
	
	public void spawn(AbstractDoll doll, AbstractDoll.Position position, int row) {
		this.spawn(doll, position, row, -1);
	}
	
	// Without waiting
	public void move(AbstractDoll doll, AbstractDoll.Position position, int row, int col) {
		int[] source = doll.getRowAndCol();
		
		if (source[0] == -1) {
			AliceMagtroidMod.logger.error("DollManager.move: doll not found");
			return;
		}
		
		if (source[0] == row && source[1] == col) {
			AliceMagtroidMod.logger.warn("DollManager.move: doll already at the position");
			return;
		}
		
		// Without checking if the position is valid
		this.dolls.get(source[0]).set(source[1], null);
		this.dolls.get(row).set(col, doll);
		
		doll.setSource(source[0], source[1]);
		doll.setDest(row, col);
	}
	
	public void recycle(AbstractDoll doll) {
		int[] rowAndCol = this.getRowAndCol(doll);
		int row = rowAndCol[0], col = rowAndCol[1];
		if (row == -1) {
			AliceMagtroidMod.logger.error("DollManager.recycleDoll: doll not found");
			return;
		}
		
		doll.onRecycled();
		doll.onBrokenOrRecycled();
	}
	
	public void remove(AbstractDoll doll) {
		int[] rowAndCol = this.getRowAndCol(doll);
		int row = rowAndCol[0], col = rowAndCol[1];
		if (row == -1) {
			AliceMagtroidMod.logger.error("DollManager.remove: doll not found");
			return;
		}
		
		this.dolls.get(row).set(col, null);
	}
	
	public void remove(AbstractDoll... dollsToRemove) {
		for (AbstractDoll doll : dollsToRemove)
			this.remove(doll);
	}
	
	public boolean letDollsFillEmptySlots() {
		boolean needToMove = false;
		
		for (int i = 0; i < dolls.size(); i++) {
			ArrayList<AbstractDoll> vec = dolls.get(i);
			int k = 0;
			
			for (int j = 0; j < vec.size(); j++) {
				AbstractDoll doll = vec.get(j);
				if (doll != null) {
					if (j != k) {
						doll.setSource(i, j);
						doll.setDest(i, k);
						vec.set(k, doll);
						needToMove = true;
					}
					
					k++;
				}
			}
		}
		
		return needToMove;
	}

	public int calcDamageOnPlayer(int damage) {
		int res = damage / this.rowCount, reminder = damage % this.rowCount;

		int totalToPlayer = 0;
		for (int i = 0; i < this.rowCount; i++) {
			int dmg = res + (i < reminder ? 1 : 0);
			ArrayList<AbstractDoll> dollsInRow = dolls.get(i);

			for (AbstractDoll doll : dollsInRow) {
				if (dmg == 0)
					break;
				dmg = doll.calcRemainingDamage(dmg);
			}

			totalToPlayer += dmg;
		}

		return totalToPlayer;
	}

	public HashMap<AbstractDoll, Integer> calcDamageOnDolls(int damage) {
		int res = damage / this.rowCount, reminder = damage % this.rowCount;
		HashMap<AbstractDoll, Integer> tbl = new HashMap<>();
		
		for (int i = 0; i < this.rowCount; i++) {
			int dmg = res + (i < reminder ? 1 : 0);
			ArrayList<AbstractDoll> dollsInRow = dolls.get(i);
			
			for (AbstractDoll doll : dollsInRow) {
				if (dmg == 0)
					break;
				
				int remaining = doll.calcRemainingDamage(dmg);
				tbl.put(doll, dmg - remaining);
				dmg = remaining;
			}
		}
		
		return tbl;
	}
	
	// returns true if any doll is broken by the damage
	public boolean handleDamageTbl(HashMap<AbstractDoll, Integer> tbl) {
		boolean anyDollBroken = false;
		
		for (Map.Entry<AbstractDoll, Integer> entry : tbl.entrySet()) {
			AbstractDoll doll = entry.getKey();
			int dmg = entry.getValue();
			
			if (doll.takeDamage(dmg))
				anyDollBroken = true;
		}
		
		return anyDollBroken;
	}
	
	public void checkUpdateAndAddAction() {
		if (this.letDollsFillEmptySlots())
			this.addToTop(new WaitDollsToMoveAction());
	}
	
	public void addToBot(AbstractGameAction action) {
		AbstractDungeon.actionManager.addToBottom(action);
	}
	
	public void addToTop(AbstractGameAction action) {
		AbstractDungeon.actionManager.addToTop(action);
	}
	
	public void addActionsToTop(AbstractGameAction... actions) {
		for (int i = actions.length - 1; i >= 0; i--)
			this.addToTop(actions[i]);
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
	
	public float getMovingScale(float x) { // by default total = 1F
		return MathUtils.cos(x * MathUtils.PI);
	}
	
	public float getMovingScale(float x, float total) {
		return this.getMovingScale(x / total);
	}
}
