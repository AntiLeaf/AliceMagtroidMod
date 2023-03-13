package AliceMod.cards.Reiuji;

import AliceMod.AliceMod;
import AliceMod.abstracts.AbstractAliceCard;
import AliceMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlameStorm extends AbstractAliceCard {
	
	public static final String SIMPLE_NAME = FlameStorm.class.getSimpleName();
	
	public static final String ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int MULTIPLIER = 2;
	private static final int UPG_MULTIPLIER = 1;
	
	public FlameStorm() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			AbstractCard.CardType.ATTACK,
			AbstractCardEnum.REIUJI_COLOR,
			AbstractCard.CardRarity.UNCOMMON,
			AbstractCard.CardTarget.ALL_ENEMY
		);
		
		this.magicNumber = this.baseMagicNumber = MULTIPLIER;
	}
	
	@Override
	public void applyPowers() {
		int cnt = AbstractDungeon.player.exhaustPile.size();
		this.damage = this.baseDamage = cnt * this.magicNumber;
		
		super.applyPowers();
		this.isDamageModified = this.damage != this.baseDamage;
		
		this.rawDescription = DESCRIPTION + " NL " + cardStrings.EXTENDED_DESCRIPTION[0];
		this.initializeDescription();
	}
	
	@Override
	public void onMoveToDiscard() {
		this.rawDescription = DESCRIPTION;
		this.initializeDescription();
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		int cnt = AbstractDungeon.player.exhaustPile.size();
		this.damage = this.baseDamage = cnt * this.magicNumber;
		this.calculateCardDamage(null);
		
		this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage,
				this.damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new FlameStorm();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPG_MULTIPLIER);
			this.initializeDescription();
		}
	}
}