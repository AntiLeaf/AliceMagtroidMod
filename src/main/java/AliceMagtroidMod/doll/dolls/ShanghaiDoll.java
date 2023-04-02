package AliceMagtroidMod.doll.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.localization.DollStrings;
import AliceMagtroidMod.patches.enums.DamageTypeEnum;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShanghaiDoll extends AbstractDoll {
	public static final String SIMPLE_NAME = ShanghaiDoll.class.getSimpleName();
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME
			+ ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/dolls/" + SIMPLE_NAME + ".png";
	public static final DollStrings dollStrings = DollStrings.getDollString(ID);
	
	public static final int MAX_HP = 4;
	public static final int SPAWN_DMG = 8;
	public static final int ACT_DMG = 2;
	
	public static final int HOURAI_SPAWN_DMG = 4;
	public static final int HOURAI_ACT_DMG = 2;
	
	ShanghaiDoll() {
		super(
				ID,
				IMG_PATH,
				dollStrings.NAME,
				dollStrings.DESCRIPTION
		);
		
		this.maxHP = this.HP = MAX_HP;
		
		this.spawnAmount = this.baseSpawnAmount = SPAWN_DMG;
		this.actAmount = this.baseActAmount = ACT_DMG;
		
		this.actAtEndOfTurn = true;
	}
	
	public void updateDescription() {
		this.description = this.parse(this.rawDescription, HOURAI_SPAWN_DMG, HOURAI_ACT_DMG);
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
		
		if (!AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
			this.addToBot(new DamageAllEnemiesAction(
					AbstractDungeon.player,
					DamageInfo.createDamageMatrix(this.actAmount, true),
					DamageTypeEnum.DOLL,
					AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
		}
	}
	
	public void applyPowers() {
		int hourai = AliceMagtroidMod.dollManager.getHouraiCount();
		
		this.spawnAmount = this.baseSpawnAmount + hourai * HOURAI_SPAWN_DMG;
		this.actAmount = this.baseActAmount + hourai * HOURAI_ACT_DMG;
	}
	
	public AbstractDoll makeCopy() {
		return new ShanghaiDoll();
	}
	
	public void playSFX() {
		// TODO
	}
}
