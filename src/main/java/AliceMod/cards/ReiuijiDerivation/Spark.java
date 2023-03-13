package AliceMod.cards.ReiuijiDerivation;

import AliceMod.AliceMod;
import AliceMod.abstracts.AbstractAliceCard;
import AliceMod.patches.enums.AbstractCardEnum;
import AliceMod.powers.HeatPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Spark extends AbstractAliceCard {
	public static final String SIMPLE_NAME = Spark.class.getSimpleName();
	
	public static final String ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -2;
	private static final int AMT = 1;
	private static final int UPG_AMT = 1;

	public Spark() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.REIUJI_DERIVATION_COLOR,
			CardRarity.SPECIAL,
			CardTarget.SELF
		);

		this.heat = this.baseHeat = AMT;
		this.cantBePlayed = true;
		this.isSupplement = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {

	}

	@Override
	public void triggerWhenDrawn() {
		super.triggerWhenDrawn();

		AbstractPlayer p = AbstractDungeon.player;
		this.addToTop(new ApplyPowerAction(p, p,
				new HeatPower(this.heat)));
		this.addToTop(new ExhaustSpecificCardAction(this, p.hand));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new Spark();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPG_AMT);
			this.initializeDescription();
		}
	}
}