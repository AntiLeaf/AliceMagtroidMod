package AliceMagtroidMod.cards.AliceMagtroid;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.action.common.AnonymousAction;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class Ignite extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = Ignite.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 1;
	private static final int DAMAGE = 7;
	private static final int UPG_COST = 0;

	public Ignite() {
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
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new AnonymousAction(() -> {
			if (!p.hand.isEmpty()) {
				this.addToTop(new SelectCardsInHandAction(
						1, "", false, false,
						(c) -> c != this,
						(cards) -> {
							if (!cards.isEmpty()) {
								AbstractCard c = cards.get(0);
								
								int amt = c.costForTurn;
								if (amt == -1)
									amt = EnergyPanel.getCurrentEnergy();
								
								if (amt > 0)
									this.addToTop(new DrawCardAction(amt));
								
								this.addToTop(new ExhaustSpecificCardAction(c, p.hand));
							}
						}));
			}
		}));

		this.calculateCardDamage(null);

		this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage,
				this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new Ignite();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPG_COST);
			this.initializeDescription();
		}
	}
}