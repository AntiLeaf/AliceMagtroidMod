package AliceMagtroidMod.cards.AliceMagtroid;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WardrobeMalfunction extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = WardrobeMalfunction.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 0;
	private static final int DAMAGE = 10;
	private static final int UPG_DAMAGE = 3;

	public WardrobeMalfunction() {
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
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new DamageAction(m,
				new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
		
		int amt = 0;
		for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
			if (!mo.isDeadOrEscaped())
				amt++;
		}
		
		this.addToBot(new DamageAction(p,
				new DamageInfo(p, amt, DamageInfo.DamageType.THORNS),
				AbstractGameAction.AttackEffect.SHIELD));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new WardrobeMalfunction();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPG_DAMAGE);
			this.initializeDescription();
		}
	}
}