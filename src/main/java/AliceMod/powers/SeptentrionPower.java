package AliceMod.powers;

import AliceMod.AliceMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class SeptentrionPower extends TwoAmountPower {
	public static final String SIMPLE_NAME = SeptentrionPower.class.getSimpleName();

	public static final String POWER_ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/powers/" + SIMPLE_NAME + ".png";
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS =
			powerStrings.DESCRIPTIONS;

	private static final int CNT = 7;

	public SeptentrionPower(int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		this.amount2 = 0;
		
		this.type = PowerType.BUFF;
		this.updateDescription();
		this.img = new Texture("img/powers/Nineball32.png");
//		this.img = new Texture(IMG_PATH);
	}

	@Override
	public void onUseCard(AbstractCard card, UseCardAction action) {
//		this.fontScale = 8.0F;
		this.amount2++;

		if (this.amount2 == CNT) {
			this.flash();
			this.addToBot(new GainEnergyAction(this.amount));
			this.amount2 = 0;
		}
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
				this.amount2 + DESCRIPTIONS[2];
	}
}