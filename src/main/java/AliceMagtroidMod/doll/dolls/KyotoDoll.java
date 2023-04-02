package AliceMagtroidMod.doll.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.localization.DollStrings;
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
	public static final DollStrings dollStrings = DollStrings.getDollString(ID);
	
	public static final int MAX_HP = 3;

	public static final int BLOCK_PRESERVED = 6;
	
	public static final int HOURAI_DMG_RATE = 25;
	
	KyotoDoll() {
		super(
				ID,
				IMG_PATH,
				dollStrings.NAME,
				dollStrings.DESCRIPTION
		);
		
		this.maxHP = this.HP = MAX_HP;
		this.passiveAmount = this.basePassiveAmount = BLOCK_PRESERVED;
	}
	
	public void updateDescription() {
		this.description = this.parse(this.rawDescription, HOURAI_DMG_RATE);
	}
	
	public void act(ActTiming timing) {
		this.applyPowers();
		
		this.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
	}
	
	public void applyPowers() {
		int hourai_count = AliceMagtroidMod.dollManager.getHouraiCount();
		this.actAmount = AbstractDungeon.player.currentBlock * (HOURAI_DMG_RATE * hourai_count) / 100;
	}
	
	public AbstractDoll makeCopy() {
		return new KyotoDoll();
	}
	
	public void playSFX() {
		// TODO
	}
}
