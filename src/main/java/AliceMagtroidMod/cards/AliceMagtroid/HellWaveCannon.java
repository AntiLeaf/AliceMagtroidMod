package AliceMagtroidMod.cards.AliceMagtroid;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import AliceMagtroidMod.powers.HellWaveCannonPower;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HellWaveCannon extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = HellWaveCannon.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 2;
	private static final int TEMP_HP = 9;
	private static final int UPG_TEMP_HP = 2;
	private static final int HEAT = 5;
	private static final int UPG_HEAT = 1;

	public HellWaveCannon() {
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

		this.tempHP = this.baseTempHP = TEMP_HP;
		this.heat = this.baseHeat = HEAT;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new AddTemporaryHPAction(p, p, this.tempHP));
		this.addToBot(new ApplyPowerAction(p, p, new HeatPower(this.heat)));
		
		this.addToBot(new ApplyPowerAction(p, p,
				new HellWaveCannonPower(this.tempHP, this.heat)));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new HellWaveCannon();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			
			this.baseTempHP += UPG_TEMP_HP;
			this.tempHP += UPG_TEMP_HP;
			this.upgradedTempHP = true;
			
			this.baseHeat += UPG_HEAT;
			this.heat += UPG_HEAT;
			this.upgradedHeat = true;
			
			this.initializeDescription();
		}
	}
}