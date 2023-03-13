package AliceMod.cards.Reiuji;

import AliceMod.AliceMod;
import AliceMod.abstracts.AbstractAliceCard;
import AliceMod.patches.enums.AbstractCardEnum;
import AliceMod.powers.HeatPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class InstantCharge extends AbstractAliceCard {
	public static final String SIMPLE_NAME = InstantCharge.class.getSimpleName();

	public static final String ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 0;
	private static final int VUL_CNT = 1;
	private static final int HEAT = 4;
	private static final int UPG_HEAT = 2;

	public InstantCharge() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.SKILL,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.COMMON,
			CardTarget.SELF
		);

		this.heat = this.baseHeat = HEAT;
		this.magicNumber = this.baseMagicNumber = VUL_CNT;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ApplyPowerAction(p, p,
				new VulnerablePower(p, this.magicNumber, false)));
		this.addToBot(new ApplyPowerAction(p, p, new HeatPower(this.heat)));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new InstantCharge();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPG_HEAT);
			this.initializeDescription();
		}
	}
}