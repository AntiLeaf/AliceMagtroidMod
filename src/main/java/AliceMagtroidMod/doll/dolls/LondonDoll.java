package AliceMagtroidMod.doll.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.action.doll.MoveDollAction;
import AliceMagtroidMod.action.doll.spawn.SpawnDollAction;
import AliceMagtroidMod.doll.localization.DollStrings;
import AliceMagtroidMod.patches.enums.DamageTypeEnum;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LondonDoll extends AbstractDoll {
	public static final String SIMPLE_NAME = LondonDoll.class.getSimpleName();
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME
			+ ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/dolls/" + SIMPLE_NAME + ".png";
	public static final DollStrings dollStrings = DollStrings.getDollString(ID);
	
	public static final int MAX_HP = 1;
	public static final int ACT_DMG = 3;
	public static final int ACT_BLOCK = 3;
	
	public static final int HOURAI_ACT_DMG = 2;
	public static final int HOURAI_ACT_BLOCK = 2;
	
	LondonDoll() {
		super(
				ID,
				IMG_PATH,
				dollStrings.NAME,
				dollStrings.DESCRIPTION
		);
		
		this.maxHP = this.HP = MAX_HP;
		
		this.passiveAmount = this.basePassiveAmount = ACT_DMG;
		this.actAmount = this.baseActAmount = ACT_DMG;
		
		this.actAtStartOfTurn = true;
		this.actAtEndOfTurn = true;
	}
	
	public void updateDescription() {
		this.description = this.parse(this.rawDescription, HOURAI_ACT_DMG);
	}
	
	@Override
	public void onSpawn() {
		this.applyPowers();
		
		if (!AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
			AbstractMonster m = AbstractDungeon.getRandomMonster();
			
			this.addToBot(new DamageAction(m,
					new DamageInfo(
							AbstractDungeon.player,
							this.spawnAmount,
							DamageTypeEnum.DOLL),
					AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		}
	}
	
	public void act(ActTiming timing) {
		this.applyPowers();
		
		if (timing == ActTiming.START_OF_TURN || timing == ActTiming.END_OF_TURN) {
			if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
				AbstractMonster m = AbstractDungeon.getMonsters().monsters.get(0);
				
				this.addToBot(new DamageAction(m,
						new DamageInfo(
								AbstractDungeon.player,
								this.actAmount,
								DamageTypeEnum.DOLL),
						AbstractGameAction.AttackEffect.LIGHTNING));
			}
		}
		else {
			this.addToBot(new GainBlockAction(
					AbstractDungeon.player,
					AbstractDungeon.player,
					this.actAmount));
			
			this.addToBot(new MoveDollAction(this, Position.LEFTMOST, this.getRow(), -1));
		}
	}
	
	@Override
	public void onBroken() {
		AbstractDoll newDoll = AbstractDoll.getRandomDollExceptLondon();
		this.addToBot(new SpawnDollAction(newDoll, Position.RIGHTMOST, this.getRow()));
	}
	
	public void applyPowers() {
		int hourai = AliceMagtroidMod.dollManager.getHouraiCount();
		
		this.actAmount = this.baseActAmount + hourai * HOURAI_ACT_DMG;
	}
	
	public AbstractDoll makeCopy() {
		return new LondonDoll();
	}
	
	public void playSFX() {
		// TODO
	}
}
