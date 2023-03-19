package AliceMagtroidMod.doll.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

public abstract class AbstractDoll {
	public String name;
	public String description;
	public String ID;
	
	protected ArrayList<PowerTip> tips = new ArrayList<>(); // TODO: What is this?
	
	public int maxHP = 0; // may not be used
	public int HP = 0;
	public int block = 0;
	
	public int passiveAmount = 0;
	public int basePassiveAmount = 0;
	
	public int spawnAmount = 0;
	public int baseSpawnAmount = 0;
	
	public int actAmount = 0;
	public int baseActAmount = 0;
	
	public boolean actAtStartOfTurn = false;
	public boolean actAtEndOfTurn = false;
//	public boolean actWhenCommanded = false;
	
	public int brokenAmount = 0; // may be used in the future
	public int baseBrokenAmount = 0;
	
	public Hitbox hb;
	protected Texture img;
	protected float fontScale;
	
	public int[] shown_values = new int[2];
	protected float animTimer;
	
	public AbstractDoll() {
		// TODO
	}
	
	public abstract void updateDescription();
	
	public static Class<?>[] dollClasses = {
			ShanghaiDoll.class,
			HouraiDoll.class,
			FranceDoll.class,
			OrleansDoll.class,
			NetherlandsDoll.class,
			LondonDoll.class,
			KyotoDoll.class
	};
	
	public static AbstractDoll getRandomDoll(Random rng) {
		Class<?> c = dollClasses[rng.random(dollClasses.length - 1)];
		try {
			return (AbstractDoll) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static AbstractDoll getRandomDoll() {
		return getRandomDoll(AbstractDungeon.cardRandomRng);
	}
	
	public static AbstractDoll getRandomDollExceptLondon(Random rng) {
		int k = rng.random(dollClasses.length - 2);
		if (dollClasses[k] == LondonDoll.class)
			k++;
		
		Class<?> c = dollClasses[k];
		try {
			return (AbstractDoll) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static AbstractDoll getRandomDollExceptLondon() {
		return getRandomDollExceptLondon(AbstractDungeon.cardRandomRng);
	}
	
	public abstract void act(ActTiming timing);
	
	public void onSpawn() {
		this.applyPowers();
	}
	
	public void onBroken() {}
	
	public void onRecycled() {}
	
	public void onBrokenOrRecycled() {}
	
	public int calcRemainingDamage(int damage) {
		// TODO
		return Integer.max(0, damage - this.HP - this.block);
	}
	
	public boolean onDamageReceived(int damage) {
		// TODO
		// damage may be greater than HP + block
		// returns true if the doll is broken after taking damage
		return damage >= this.HP + this.block;
	}
	
	public abstract void applyPowers();
	
	public abstract AbstractDoll makeCopy();
	
	public void updateAnimation() {
		// TODO
	}
	
	public void setSlot(int row, int col, int max_row) {
		// TODO
	}
	
	public abstract void render(SpriteBatch sb);
	
	protected void renderText(SpriteBatch sb) {
		// TODO
	}
	
	public void renderHealthBar(SpriteBatch sb) {
		// TODO
	}
	
	public void triggerAnimation() {
	
	}
	
	public abstract void playSFX();
	
	static {
		// TODO
	}
	
	public int getRow() {
		return AliceMagtroidMod.dollManager.getRow(this);
	}
	
	public int getCol() {
		return AliceMagtroidMod.dollManager.getCol(this);
	}
	
	public int[] getRowAndCol() {
		return AliceMagtroidMod.dollManager.getRowAndCol(this);
	}
	
	void addToBot(AbstractGameAction action) {
		AbstractDungeon.actionManager.addToBottom(action);
	}
	
	void addToTop(AbstractGameAction action) {
		AbstractDungeon.actionManager.addToTop(action);
	}
	
	public enum Position {
		RIGHTMOST,
		LEFTMOST,
		MANUAL,
		UNSPECIFIED
	}
	
	public enum ActTiming {
		SPECIAL,
		START_OF_TURN,
		END_OF_TURN,
		COMMANDED,
		HOURAI
	}
}
