package AliceMagtroidMod.powers;

import AliceMagtroidMod.AliceMagtroidMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class DarkGraspPower extends AbstractPower implements InvisiblePower {
	public static final String SIMPLE_NAME = DarkGraspPower.class.getSimpleName();

	public static final String POWER_ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/powers/" + SIMPLE_NAME + ".png";
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS =
			powerStrings.DESCRIPTIONS;

	public DarkGraspPower(int amount) {
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
	public void onAfterCardPlayed(AbstractCard c) {
		ArrayList<AbstractCard> cards = new ArrayList<>();
		
		for (AbstractCard card : AbstractDungeon.player.hand.group)
			if (card != c)
				cards.add(card);
		
		if (!cards.isEmpty()) {
			this.addToBot(new AddEmbraceAction(
					cards.get(AbstractDungeon.cardRandomRng.random(cards.size() - 1)),
					this.amount));
			
			this.flash();
		}
	}
	
	@Override
	public void updateDescription() {
		this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
	}
}