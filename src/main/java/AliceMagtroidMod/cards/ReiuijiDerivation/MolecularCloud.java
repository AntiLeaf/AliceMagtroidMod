package AliceMagtroidMod.cards.ReiuijiDerivation;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MolecularCloud extends AbstractAliceCard {
	public static final String SIMPLE_NAME = MolecularCloud.class.getSimpleName();
	
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -2;
	private static final int TEMP_HP = 28;
	private static final int UPG_TEMP_HP = 6;

	public MolecularCloud() {
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

		this.block = this.baseBlock = TEMP_HP;
		this.cantBePlayed = true;
		this.isSupplement = true;
		
		this.cardsToPreview = new MainSequenceStar();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {

	}

	@Override
	public void triggerWhenDrawn() {
		super.triggerWhenDrawn();

		AbstractPlayer p = AbstractDungeon.player;
		this.addToTop(new AddTemporaryHPAction(p, p, this.block));
	}

	@Override
	public void triggerOnLeaveHand(boolean isExhaust, boolean isEndOfTurn) {
		AbstractCard temp = new MainSequenceStar();
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
		return new MolecularCloud();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPG_TEMP_HP);
			this.initializeDescription();

			this.cardsToPreview.upgrade();
		}
	}
}