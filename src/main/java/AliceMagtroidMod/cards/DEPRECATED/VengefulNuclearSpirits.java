package AliceMagtroidMod.cards.DEPRECATED;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import AliceMagtroidMod.powers.VengefulNuclearSpiritsOnMonsterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VengefulNuclearSpirits extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = VengefulNuclearSpirits.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 1;
	private static final int DAMAGE = 12;
	private static final int UPG_DAMAGE = 2;
	private static final int AMT = 4;
	private static final int UPG_AMT = 1;

	public VengefulNuclearSpirits() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.SKILL,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.COMMON,
			CardTarget.ENEMY
		);
		
		this.damage = this.baseDamage = DAMAGE;
		this.magicNumber = this.baseMagicNumber = AMT;
	}
	
	@Override
	public void applyPowers() {
	
	}
	
	@Override
	public void calculateCardDamage(AbstractMonster mo) {
	
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ApplyPowerAction(m, p,
				new VengefulNuclearSpiritsOnMonsterPower(m, this.damage)));
	}
	
	public int onEmbracedModifyAmount(int amount) {
		this.damage += this.magicNumber;
		if (this.damage != this.baseDamage)
			this.isDamageModified = true;
		
		return 0;
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new VengefulNuclearSpirits();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPG_DAMAGE);
			this.upgradeMagicNumber(UPG_AMT);
			this.initializeDescription();
		}
	}
}