package AliceMagtroidMod.cards.AliceMagtroid;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.cards.AliceMagtroidDerivation.Spark;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NuclearRay extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = NuclearRay.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 1;
	private static final int DAMAGE = 1;
	private static final int AMT = 1;
	private static final int CNT = 3;
	private static final int UPG_CNT = 1;

	public NuclearRay() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.COMMON,
			CardTarget.ALL_ENEMY
		);

		this.damage = this.baseDamage = DAMAGE;
		this.magicNumber = this.baseMagicNumber = CNT;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.calculateCardDamage(null);
		
		for (int i = 0; i < this.magicNumber; i++) {
			this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage,
					this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
			
			this.addToBot(new MakeTempCardInDrawPileAction(
					new Spark(), AMT, true, true));
		}
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new NuclearRay();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPG_CNT);
			this.initializeDescription();
		}
	}
}