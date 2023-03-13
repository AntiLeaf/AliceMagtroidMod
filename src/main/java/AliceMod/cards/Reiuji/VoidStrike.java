package AliceMod.cards.Reiuji;

import AliceMod.AliceMod;
import AliceMod.abstracts.AbstractAliceCard;
import AliceMod.action.AddEmbraceAction;
import AliceMod.embrace.EmbraceManager;
import AliceMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VoidStrike extends AbstractAliceCard {
	public static final String SIMPLE_NAME = VoidStrike.class.getSimpleName();

	public static final String ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 2;
	private static final int DAMAGE = 12;
	private static final int UPG_DAMAGE = 3;
	private static final int AMT = 1;

	public VoidStrike() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.BASIC,
			CardTarget.ENEMY
		);

		this.damage = this.baseDamage = DAMAGE;
		this.magicNumber = this.baseMagicNumber = AMT;
		this.tags.add(CardTags.STRIKE);
	}

	@Override
	public void applyPowers() {
		super.applyPowers();

		if (EmbraceManager.getEmbrace(this) > 0) {
			this.damage *= 2;
			this.isDamageModified = (this.damage != this.baseDamage);
		}
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new DamageAction(m,
				new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));

		this.addToBot(new AddEmbraceAction(this, this.magicNumber));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new VoidStrike();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPG_DAMAGE);
			this.initializeDescription();
		}
	}
}