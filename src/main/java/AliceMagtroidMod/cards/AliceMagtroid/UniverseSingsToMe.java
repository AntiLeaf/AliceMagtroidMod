package AliceMagtroidMod.cards.AliceMagtroid;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.cardmodifier.modifiers.ExhaustHandAlternateCostCardModifier;
import AliceMagtroidMod.cards.AliceMagtroidDerivation.*;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UniverseSingsToMe extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = UniverseSingsToMe.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 5;

	public UniverseSingsToMe() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.SKILL,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.RARE,
			CardTarget.SELF
		);

		this.isSpellCard = true;
		this.exhaust = true;
		AlwaysRetainField.alwaysRetain.set(this, true);
		CardModifierManager.addModifier(this,
				new ExhaustHandAlternateCostCardModifier());

		this.cardsToPreview = new MolecularCloud();
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractCard temp = new MolecularCloud();
		if (this.upgraded)
			temp.upgrade();

		this.addToBot(new MakeTempCardInDrawPileAction(
				temp, 1, true, true));
	}

	@Override
	public AbstractCard makeCopy() {
		return new UniverseSingsToMe();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.cardsToPreview.upgrade();
		}
	}

	public static boolean isDerivation(AbstractCard c) {
		return (c instanceof MolecularCloud) ||
				(c instanceof MainSequenceStar) ||
				(c instanceof RedGiantStar) ||
				(c instanceof Supernova) ||
				(c instanceof BlackHole);
	}
}