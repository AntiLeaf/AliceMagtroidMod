package AliceMod.cards.Reiuji;

import AliceMod.AliceMod;
import AliceMod.abstracts.AbstractAliceCard;
import AliceMod.action.AddEmbraceAction;
import AliceMod.action.AnonymousAction;
import AliceMod.patches.enums.AbstractCardEnum;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

public class VoidWalking extends AbstractAliceCard {
	public static final String SIMPLE_NAME = VoidWalking.class.getSimpleName();

	public static final String ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 1;
	private static final int AMT = 1;

	public VoidWalking() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.SKILL,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.UNCOMMON,
			CardTarget.NONE
		);

		this.magicNumber = this.baseMagicNumber = AMT;
		this.exhaust = true;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new AnonymousAction(() -> {
			for (int i = p.hand.group.size() - 1; i >= 0; i--) {
				AbstractCard card = p.hand.group.get(i);
				if (card != this)
					this.addToTop(new AddEmbraceAction(card, this.magicNumber));
			}
		}));

		this.addToBot(new ApplyPowerAction(p, p,
				new EquilibriumPower(p, this.magicNumber)));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new VoidWalking();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			AlwaysRetainField.alwaysRetain.set(this, true);
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}