package AliceMagtroidMod.cards.DEPRECATED;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import AliceMagtroidMod.powers.SeptentrionPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Septentrion extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = Septentrion.class.getSimpleName();
	
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int AMT = 7;
	private static final int DRAW = 1;
	private static final int UPG_DRAW = 2;

	public Septentrion() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.POWER,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.UNCOMMON,
			CardTarget.SELF
		);

		this.magicNumber = this.baseMagicNumber = DRAW;
		this.isSpellCard = true;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new DrawCardAction(this.magicNumber));

		this.addToBot(new ApplyPowerAction(p, p,
				new SeptentrionPower(AMT)));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new Septentrion();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPG_DRAW);
			this.initializeDescription();
		}
	}
}