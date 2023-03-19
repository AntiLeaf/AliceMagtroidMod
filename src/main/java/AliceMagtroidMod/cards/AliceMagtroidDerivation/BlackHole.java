package AliceMagtroidMod.cards.AliceMagtroidDerivation;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.cards.AliceMagtroid.UniverseSingsToMe;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.Level;

public class BlackHole extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = BlackHole.class.getSimpleName();
	
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -2;
	private static final int AMT = 1;

	public BlackHole() {
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

		this.magicNumber = this.baseMagicNumber = AMT;
		this.cantBePlayed = true;
		this.isSupplement = true;

//		this.cardsToPreview = new MolecularCloud();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {

	}

	@Override
	public void triggerWhenDrawn() {
		super.triggerWhenDrawn();

		AbstractPlayer p = AbstractDungeon.player;
		int cnt = 0;
		for (AbstractCard c : p.hand.group)
			if (c != this)
				cnt++;

		if (cnt > 0) {
			AbstractCard card = null;

			if (!this.upgraded) {
				int k = Integer.min(cnt,
						AbstractDungeon.cardRng.random(0, cnt));

				for (AbstractCard c : p.hand.group)
					if (c != this && --k == 0) {
						card = c;
						break;
					}

				if (card != null)
					this.addToTop(new ExhaustSpecificCardAction(
							card, p.hand));
				else
					AliceMagtroidMod.logger.log(Level.WARN,
							"BlackHole: No selected card!");
			}
			else
				this.addToTop(new SelectCardsInHandAction(
						this.magicNumber, "", false, false, (c) -> true,
						(cards) -> {
							for (AbstractCard qwq : cards)
								this.addToTop(new ExhaustSpecificCardAction(
										qwq, p.hand));
						}
				));
		}
	}

	@Override
	public void triggerOnLeaveHand(boolean isExhaust, boolean isEndOfTurn) {
		AbstractCard temp = new MolecularCloud();
		if (this.upgraded)
			temp.upgrade();

		this.addToTop(new MakeTempCardInDrawPileAction(
				temp, 1, true, true));

		super.triggerOnLeaveHand(isExhaust, isEndOfTurn);
	}

	@Override
	public void triggerOnExhaust() {
		AbstractPlayer p = AbstractDungeon.player;

		if (!p.exhaustPile.isEmpty())
			this.addToTop(new SelectCardsAction(
					p.exhaustPile.group, this.magicNumber, "", false,
					(c) -> !UniverseSingsToMe.isDerivation(c),
					(cards) -> {
						for (AbstractCard t : cards) {
							p.exhaustPile.removeCard(t);

							if (p.hand.size() == BaseMod.MAX_HAND_SIZE)
								p.hand.addToBottom(t);
							else {
								p.discardPile.addToBottom(t);
								p.createHandIsFullDialog();
							}
						}
					}
			));

		super.triggerOnExhaust();
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new BlackHole();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.initializeDescription();

//			this.cardsToPreview.upgrade();
		}
	}
}