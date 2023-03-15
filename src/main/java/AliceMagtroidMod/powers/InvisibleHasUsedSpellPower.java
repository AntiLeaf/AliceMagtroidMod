package AliceMagtroidMod.powers;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceCard;
import AliceMagtroidMod.patches.enums.AbstractPowerEnum;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class InvisibleHasUsedSpellPower extends AbstractPower implements InvisiblePower {
	public static final String SIMPLE_NAME = InvisibleHasUsedSpellPower.class.getSimpleName();

	public static final String POWER_ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/powers/" + SIMPLE_NAME + ".png";
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS =
			powerStrings.DESCRIPTIONS;

	public AbstractAliceCard spell;

	public InvisibleHasUsedSpellPower(AbstractAliceCard spell) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = -1;
		this.spell = spell;
		
		this.type = AbstractPowerEnum.NEUTRAL;
		this.updateDescription();
		this.img = new Texture("img/powers/Nineball32.png");
//		this.img = new Texture(IMG_PATH);
	}
	
	@Override
	public void updateDescription() {
//		this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
		// TODO
	}

	@Override
	public boolean canPlayCard(AbstractCard c) {
		if (c instanceof AbstractAliceCard) {
			AbstractAliceCard card = (AbstractAliceCard) c;

			return !(card.isSpellCard && card != this.spell);
		}
		return true;
	}

	@Override
	public void atEndOfRound() {
		this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
	}
}