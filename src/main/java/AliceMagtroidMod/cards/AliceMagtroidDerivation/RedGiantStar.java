package AliceMagtroidMod.cards.AliceMagtroidDerivation;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RedGiantStar extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = RedGiantStar.class.getSimpleName();
	
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -2;
	private static final int ENERGY = 2;
	private static final int UPG_ENERGY = 1;

	public RedGiantStar() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.SKILL,
			AbstractCardEnum.REIUJI_DERIVATION_COLOR,
			CardRarity.SPECIAL,
			CardTarget.SELF
		);

		this.magicNumber = this.baseMagicNumber = ENERGY;
		this.cantBePlayed = true;
		this.isSupplement = true;
		
		this.cardsToPreview = new Supernova();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {

	}

	@Override
	public void triggerWhenDrawn() {
		super.triggerWhenDrawn();

		this.addToTop(new GainEnergyAction(this.magicNumber));
	}

	@Override
	public void triggerOnLeaveHand(boolean isExhaust, boolean isEndOfTurn) {
		AbstractCard temp = new Supernova();
		if (this.upgraded)
			temp.upgrade();

		this.addToTop(new MakeTempCardInDrawPileAction(
				temp, 1, true, true));

		if (!isExhaust)
			this.addToTop(new ExhaustSpecificCardAction(
					this, AbstractDungeon.player.discardPile, true));

		super.triggerOnLeaveHand(isExhaust, isEndOfTurn);
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new RedGiantStar();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPG_ENERGY);
			this.initializeDescription();

			this.cardsToPreview.upgrade();
		}
	}
}