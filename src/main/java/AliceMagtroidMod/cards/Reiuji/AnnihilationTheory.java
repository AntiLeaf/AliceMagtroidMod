package AliceMagtroidMod.cards.Reiuji;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.abstracts.AbstractAliceCard;
import AliceMagtroidMod.embrace.EmbraceManager;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AnnihilationTheory extends AbstractAliceCard {
	public static final String SIMPLE_NAME = AnnihilationTheory.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 6;
	private static final int DAMAGE = 6;

	public AnnihilationTheory() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.RARE,
			CardTarget.ENEMY
		);

		this.damage = this.baseDamage = DAMAGE;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		int cnt = 1 + EmbraceManager.getEmbrace(this);
		for (int i = 0; i < cnt; i++)
			this.addToBot(new DamageAction(m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.SLASH_HEAVY));
	}
	
	@Override
	public boolean removeEmbraceAfterPlayed() {
		return false;
	}
	
	public boolean canHaveInfinityEmbrace() {
		return this.upgraded;
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new AnnihilationTheory();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}