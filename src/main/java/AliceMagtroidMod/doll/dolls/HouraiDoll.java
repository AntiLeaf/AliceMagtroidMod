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

public class HouraiDoll extends AbstractDoll {
	public static final String SIMPLE_NAME = HouraiDoll.class.getSimpleName();
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME
			+ ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/dolls/" + SIMPLE_NAME + ".png";
	
	public static final int MAX_HP = 5;
	
	HouraiDoll() {
		super();
		
		this.maxHP = this.HP = MAX_HP;
		
		this.actAmount = this.baseActAmount = 1;
		this.actWhenCommanded = true;
	}
	
	public void updateDescription() {
		// TODO
		this.description = "Hourai dolls can buff other kinds of dolls.\n"
				+ "Act (when commanded): All rightmost dolls will act extra "
				+ this.actAmount + " time(s) next time.";
	}
	
	@Override
	public void triggerWhenCommanded() {
		this.applyPowers();
		
		for (AbstractDoll doll : AliceMagtroidMod.dollManager.getRightmostDolls())
			if (doll != null && !(doll instanceof HouraiDoll)) {
				doll.extraActCount += this.actAmount;
			}
	}
	
	public void applyPowers() {}
	
	public AbstractDoll makeCopy() {
		return new HouraiDoll();
	}
	
	public void render(SpriteBatch sb) {
		// TODO
	}
	
	public void playSFX() {
		// TODO
	}
}
