package AliceMagtroidMod.cards.Reiuji;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend_Alice extends AbstractAliceCard {
	public static final String SIMPLE_NAME = Defend_Alice.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 1;
	private static final int BLOCK = 5;
	private static final int UPG_BLOCK = 3;

	public Defend_Alice() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.SKILL,
			AbstractCardEnum.ALICE_COLOR,
			CardRarity.BASIC,
			CardTarget.SELF
		);

		this.block = this.baseBlock = BLOCK;
		this.tags.add(CardTags.STARTER_DEFEND);
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new GainBlockAction(p, this.block));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new Defend_Alice();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPG_BLOCK);
			this.initializeDescription();
		}
	}
}