package AliceMagtroidMod.cards.DEPRECATED;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.action.common.AnonymousAction;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UtsuhosUnstableElement extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = UtsuhosUnstableElement.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 1;
	private static final int UPG_COST = 0;

	public UtsuhosUnstableElement() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.SKILL,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.RARE,
			CardTarget.NONE
		);

		this.exhaust = true;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new AnonymousAction(() -> {
			int amt = BaseMod.MAX_HAND_SIZE - p.hand.size();

			if (amt > 0)
				p.draw(amt);

			for (AbstractCard c : p.discardPile.group)
				this.addToTop(new ExhaustSpecificCardAction(
						c, p.discardPile, true));

			for (AbstractCard c : p.drawPile.group)
				this.addToTop(new ExhaustSpecificCardAction(
						c, p.drawPile, true));
		}));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new UtsuhosUnstableElement();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPG_COST);
			this.initializeDescription();
		}
	}
}