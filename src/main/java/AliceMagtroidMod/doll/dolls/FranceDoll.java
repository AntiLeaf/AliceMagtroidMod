package AliceMagtroidMod.doll.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.patches.enums.DamageTypeEnum;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FranceDoll extends AbstractDoll {
	public static final String SIMPLE_NAME = FranceDoll.class.getSimpleName();
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME
			+ ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/dolls/" + SIMPLE_NAME + ".png";
	
	public static final int MAX_HP = 8;
	public static final int SPAWN_BLOCK = 3;
	public static final int ACT_BLOCK = 3;
	
	public static final int HOURAI_SPAWN_BLOCK = 3;
	public static final int HOURAI_ACT_BLOCK = 3;
	
	FranceDoll() {
		super();
		
		this.maxHP = this.HP = MAX_HP;
		
		this.spawnAmount = this.baseSpawnAmount = SPAWN_BLOCK;
		this.actAmount = this.baseActAmount = ACT_BLOCK;
		
		this.actAtStartOfTurn = true;
	}
	
	public void updateDescription() {
		// TODO
		this.description = "Spawn: France dolls spawn with " + this.spawnAmount + " block.\n"
				+ "Act (at start of turn): Gain " + this.actAmount + " block.";
	}
	
	@Override
	public void onSpawn() {
		this.applyPowers();
		
		this.block = this.spawnAmount;
	}
	
	@Override
	public void atStartOfTurn() {
		this.applyPowers();
		
		this.block += this.actAmount;
	}
	
	@Override
	public int calcRemainingDamage(int damage) {
		return 0;
	}
	
	public void applyPowers() {
		int hourai = AliceMagtroidMod.dollManager.getHouraiCount();
		
		this.spawnAmount = this.baseSpawnAmount + hourai * HOURAI_SPAWN_BLOCK;
		this.actAmount = this.baseActAmount + hourai * HOURAI_ACT_BLOCK;
	}
	
	public AbstractDoll makeCopy() {
		return new FranceDoll();
	}
	
	public void render(SpriteBatch sb) {
		// TODO
	}
	
	public void playSFX() {
		// TODO
	}
}