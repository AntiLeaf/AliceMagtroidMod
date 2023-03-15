package AliceMagtroidMod.cards.Reiuji;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AbsoluteDomain extends AbstractAliceCard {
	public static final String SIMPLE_NAME = AbsoluteDomain.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 2;

	public AbsoluteDomain() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.SKILL,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.UNCOMMON,
			CardTarget.NONE
		);

		this.exhaust = true;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
//		if (p.hasPower(HeatPower.POWER_ID))
//			this.addToBot(new ApplyPowerAction(p, p,
//					new HeatPower(p.getPower(HeatPower.POWER_ID).amount)));

		if (TempHPField.tempHp.get(this) > 0)
			this.addToBot(new AddTemporaryHPAction(p, p,
					TempHPField.tempHp.get(this)));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new AbsoluteDomain();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.exhaust = false;
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}