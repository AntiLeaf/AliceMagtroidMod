package AliceMagtroidMod.doll.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OrleansDoll extends AbstractDoll {
	public static final String SIMPLE_NAME = OrleansDoll.class.getSimpleName();
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME
			+ ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/dolls/" + SIMPLE_NAME + ".png";
	
	public static final int MAX_HP = 5;
	public static final int SPAWN_BLOCK = 3;
	public static final int ACT_BLOCK = 1;
	
	public static final int HOURAI_SPAWN_BLOCK = 2;
	public static final int HOURAI_ACT_BLOCK = 1;
	
	OrleansDoll() {
		super();
		
		this.maxHP = this.HP = MAX_HP;
		
		this.spawnAmount = this.baseSpawnAmount = SPAWN_BLOCK;
		this.actAmount = this.baseActAmount = ACT_BLOCK;
		
		this.actAtStartOfTurn = true;
	}
	
	public void updateDescription() {
		// TODO
		this.description = "Spawn: Alice gains " + this.spawnAmount + " block.\n"
				+ "Act (at start of turn): Alice and all dolls gain " + this.actAmount + " block.";
	}
	
	@Override
	public void onSpawn() {
		this.applyPowers();
		
		this.addToTop(new GainBlockAction(AbstractDungeon.player, this.spawnAmount));
	}
	
	@Override
	public void atStartOfTurn() {
		this.applyPowers();
		
		this.addToTop(new GainBlockAction(AbstractDungeon.player, this.actAmount));
		for (AbstractDoll doll : AliceMagtroidMod.dollManager.getAllDolls()) {
			if (doll != null) {
				doll.block += this.actAmount;
			}
		}
	}
	
	public void applyPowers() {
		int hourai = AliceMagtroidMod.dollManager.getHouraiCount();
		
		this.spawnAmount = this.baseSpawnAmount + hourai * HOURAI_SPAWN_BLOCK;
		this.actAmount = this.baseActAmount + hourai * HOURAI_ACT_BLOCK;
	}
	
	public AbstractDoll makeCopy() {
		return new OrleansDoll();
	}
	
	public void render(SpriteBatch sb) {
		// TODO
	}
	
	public void playSFX() {
		// TODO
	}
}
