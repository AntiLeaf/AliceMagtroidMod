package AliceMagtroidMod.cards.Reiuji;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.abstracts.AbstractAliceCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RadiantBlade extends AbstractAliceCard {
	public static final String SIMPLE_NAME = RadiantBlade.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 1;
	private static final int DAMAGE = 7;
	private static final int AMT = 3;

	public RadiantBlade() {
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

	private void dealDamage(AbstractPlayer p, AbstractMonster m, int cnt) {
		int amt = cnt * this.magicNumber;

		this.baseDamage += amt;
		this.calculateCardDamage(null);

		this.addToTop(new DamageAction(m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

		this.baseDamage -= amt;
		this.calculateCardDamage(null);

		this.addToTop(new ModifyDamageAction(this.uuid, amt));
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (!this.upgraded) {
			this.addToBot(new SelectCardsInHandAction(2,
					cardStrings.EXTENDED_DESCRIPTION[0] + this.bracketedName(),
					false, false,
					(c) -> c != this, (cards) -> {
				int cnt = 0;
				for (AbstractCard c : cards) {
					if (c.upgraded)
						cnt++;
					else
						c.upgrade();
				}

				this.dealDamage(p, m, cnt);
			}));
		}
		else {
			int cnt = 0;
			for (AbstractCard c : p.hand.group) {
				if (c == this)
					continue;
				
				if (c.upgraded)
					cnt++;
				else
					c.upgrade();
			}

			this.dealDamage(p, m, cnt);
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new RadiantBlade();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}