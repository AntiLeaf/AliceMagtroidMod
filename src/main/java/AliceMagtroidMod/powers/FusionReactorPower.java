package AliceMagtroidMod.powers;

import AliceMagtroidMod.AliceMagtroidMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FusionReactorPower extends AbstractPower {
	public static final String SIMPLE_NAME = FusionReactorPower.class.getSimpleName();

	public static final String POWER_ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/powers/" + SIMPLE_NAME + ".png";
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS =
			powerStrings.DESCRIPTIONS;

	public FusionReactorPower(int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		
		this.type = PowerType.BUFF;
		this.updateDescription();
		this.img = new Texture("img/powers/Nineball32.png");
//		this.img = new Texture(IMG_PATH);
	}

	@Override
	public void onExhaust(AbstractCard card) {
		boolean flag = false;
		for (AbstractCard t : AbstractDungeon.player.masterDeck.group)
			if (t.uuid == card.uuid) {
				flag = true;
				break;
			}

		if (flag)
			this.addToBot(new AddTemporaryHPAction(
					this.owner, this.owner, this.amount));
		else
			this.addToBot(new GainBlockAction(
					this.owner, this.owner, this.amount));
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount +
				DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
	}
}