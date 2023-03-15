package AliceMagtroidMod.cards.Reiuji;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HellsArtificialSun extends AbstractReiujiEchoCard {
	public static final String SIMPLE_NAME = HellsArtificialSun.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 1;
	private static final int DAMAGE = 11;

	public HellsArtificialSun() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.RARE,
			CardTarget.ALL_ENEMY
		);

		this.damage = this.baseDamage = DAMAGE;
		this.exhaust = true;
		this.isSpellCard = true;
	}
	
	public HellsArtificialSun(HellsArtificialSun original) {
		super(original);
	}
	
	public void initAsDerivation() {
		if (this.original == null) {
			AliceMagtroidMod.logger.warn("Original card is null!");
			return;
		}
		
		super.initAsDerivation();
		
		this.cost = COST;
		this.damage = this.baseDamage = DAMAGE;
		this.isSpellCard = true;
		
		for (int i = 0; i < this.original.timesUpgraded; i++)
			this.upgrade();
	}
	
	public AbstractReiujiEchoCard makeDerivation() {
		return new HellsArtificialSun(this);
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.calculateCardDamage(null);

		this.addToBot(new ExhaustAction(1, !this.upgraded, false));
		this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage,
				this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new HellsArtificialSun();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}