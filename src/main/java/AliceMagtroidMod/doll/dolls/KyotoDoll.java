package AliceMagtroidMod.doll.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.patches.enums.DamageTypeEnum;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KyotoDoll extends AbstractDoll {
	public static final String SIMPLE_NAME = KyotoDoll.class.getSimpleName();
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME
			+ ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/dolls/" + SIMPLE_NAME + ".png";
	
	public static final int MAX_HP = 4;
	
	public static final int HOURAI_DMG_RATE = 25;
	
	KyotoDoll() {
		super();
		
		this.maxHP = this.HP = MAX_HP;
	}
	
	public void updateDescription() {
		// TODO
	}
	
	public void act(ActTiming timing) {
		this.applyPowers();
		
		this.addToBot(new DrawCardAction(AbstractDungeon.player, 1));
	}
	
	public void applyPowers() {
		// TODO
	}
	
	public AbstractDoll makeCopy() {
		return new KyotoDoll();
	}
	
	public void render(SpriteBatch sb) {
		// TODO
	}
	
	public void playSFX() {
		// TODO
	}
}
