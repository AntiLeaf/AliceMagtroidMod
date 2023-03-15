package AliceMagtroidMod.cards.ReiuijiDerivation;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.abstracts.AbstractAliceCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Supernova extends AbstractAliceCard {
	public static final String SIMPLE_NAME = Supernova.class.getSimpleName();
	
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -2;
	private static final int DAMAGE = 48;
	private static final int UPG_DAMAGE = 12;

	public Supernova() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.SKILL,
			AbstractCardEnum.REIUJI_DERIVATION_COLOR,
			CardRarity.SPECIAL,
			CardTarget.SELF
		);

		this.damage = this.baseDamage = DAMAGE;
		this.damageType = this.damageTypeForTurn = DamageInfo.DamageType.THORNS;
		this.cantBePlayed = true;
		this.isSupplement = true;
		
		this.cardsToPreview = new BlackHole();
	}
	
	public void applyPowers() {
	
	}

	public void use(AbstractPlayer p, AbstractMonster m) {

	}

	@Override
	public void triggerWhenDrawn() {
		super.triggerWhenDrawn();

		AbstractPlayer p = AbstractDungeon.player;
		this.calculateCardDamage(null);
		this.addToTop(new DamageAllEnemiesAction(
				p, this.multiDamage, this.damageTypeForTurn,
				AbstractGameAction.AttackEffect.FIRE));
	}

	@Override
	public void triggerOnLeaveHand(boolean isExhaust, boolean isEndOfTurn) {
		AbstractCard temp = new BlackHole();
		if (this.upgraded)
			temp.upgrade();

		this.addToTop(new MakeTempCardInDrawPileAction(
				temp, 1, true, true));

		if (!isExhaust)
			this.addToTop(new ExhaustSpecificCardAction(
					this, AbstractDungeon.player.discardPile, true));

		super.triggerOnLeaveHand(isExhaust, isEndOfTurn);
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new Supernova();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPG_DAMAGE);
			this.initializeDescription();
		}
	}
}