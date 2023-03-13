package AliceMod.powers;

import AliceMod.AliceMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EscapeTheVoidPower extends AbstractPower {
	public static final String SIMPLE_NAME = EscapeTheVoidPower.class.getSimpleName();

	public static final String POWER_ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/powers/" + SIMPLE_NAME + ".png";
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS =
			powerStrings.DESCRIPTIONS;

	public EscapeTheVoidPower(int amount) {
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
	public void onCardDraw(AbstractCard card) {
		if (card instanceof VoidCard)
			this.addToBot(new DrawCardAction(this.amount));
	}
	
	@Override
	public void updateDescription() {
		this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
	}
}