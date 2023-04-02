package AliceMagtroidMod.cards.DEPRECATED;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UpThrust extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = UpThrust.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 1;
	private static final int DAMAGE = 7;
	private static final int UPG_DAMAGE = 3;
	private static final int AMT = 1;

	public UpThrust() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.COMMON,
			CardTarget.ENEMY
		);

		this.damage = this.baseDamage = DAMAGE;
		this.magicNumber = this.baseMagicNumber = AMT;
	}
	
	@Override
	public void triggerOnGlowCheck() {
		super.triggerOnGlowCheck();
		
		boolean flag = false;
		for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisTurn)
			if (card != this && card.cardID.equals(this.cardID)) {
				flag = true;
				break;
			}
		
		if (!flag)
			this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new DamageAction(m,
				new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
		
		boolean flag = false;
		for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisTurn)
			if (card != this && card.cardID.equals(this.cardID)) {
				flag = true;
				break;
			}
		
		if (!flag) {
			this.addToBot(new GainEnergyAction(this.magicNumber));
			this.addToBot(new DrawCardAction(this.magicNumber));
		}
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new UpThrust();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPG_DAMAGE);
			this.initializeDescription();
		}
	}
}