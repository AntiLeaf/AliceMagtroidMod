package AliceMagtroidMod.cards.DEPRECATED;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ScatterSparks extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = ScatterSparks.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 1;
	private static final int DAMAGE = 1;
	private static final int TIMES = 10;
	private static final int UPG_TIMES = 4;

	public ScatterSparks() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.UNCOMMON,
			CardTarget.ENEMY
		);

		this.damage = this.baseDamage = DAMAGE;
		this.magicNumber = this.baseMagicNumber = TIMES;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		// TODO: 应该是随机伤害
		
		for (int i = 0; i < this.magicNumber; i++)
			this.addToBot(new DamageAction(m,
				new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
					AbstractGameAction.AttackEffect.FIRE));
	}

	@Override
	public void applyPowers() {
		// pass
	}
	
	@Override
	public void calculateCardDamage(AbstractMonster mo) {
	
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new ScatterSparks();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPG_TIMES);
			this.initializeDescription();
		}
	}
}