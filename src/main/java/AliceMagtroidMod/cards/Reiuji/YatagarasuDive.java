package AliceMagtroidMod.cards.Reiuji;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.abstracts.AbstractAliceCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import AliceMagtroidMod.powers.CantDealDamageToPlayerPower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.Level;

public class YatagarasuDive extends AbstractAliceCard {
	public static final String SIMPLE_NAME = YatagarasuDive.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 3;
	private static final int DAMAGE = 2;
	private static final int CNT = 7;
	private static final int UPG_CNT = 3;

	public YatagarasuDive() {
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

		this.isSpellCard = true;
		this.damage = this.baseDamage = DAMAGE;
		this.magicNumber = this.baseMagicNumber = CNT;
		this.exhaust = true;
		AlwaysRetainField.alwaysRetain.set(this, true);
	}

	@Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		return m != null &&
				(m.intent == AbstractMonster.Intent.ATTACK ||
				m.intent == AbstractMonster.Intent.ATTACK_BUFF ||
				m.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
				m.intent == AbstractMonster.Intent.ATTACK_DEFEND);
	}

	@Override
	public void onRetained() {
		this.upgradeDamage(1);
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (m != null &&
				(m.intent == AbstractMonster.Intent.ATTACK ||
				m.intent == AbstractMonster.Intent.ATTACK_BUFF ||
				m.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
				m.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {

			for (int i = 0; i < this.magicNumber; i++)
				this.addToBot(new DamageAction(m,
						new DamageInfo(p, this.damage, this.damageTypeForTurn),
						AbstractGameAction.AttackEffect.BLUNT_HEAVY));

			this.addToBot(new ApplyPowerAction(m, p,
					new CantDealDamageToPlayerPower(m, 1)));
		}
		else {
			AliceMagtroidMod.logger.log(Level.WARN,
				"YatagarasuDive: Intent of target is not attack");
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new YatagarasuDive();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPG_CNT);
			this.initializeDescription();
		}
	}
}