package AliceMagtroidMod.cards.Reiuji;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import AliceMagtroidMod.powers.TheSunStealerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheSunStealer extends AbstractAliceCard {
	public static final String SIMPLE_NAME = TheSunStealer.class.getSimpleName();
	
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;

	public TheSunStealer() {
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

		this.isSpellCard = true;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ApplyPowerAction(p, p,
				new TheSunStealerPower()));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new TheSunStealer();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.isInnate = true;
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}