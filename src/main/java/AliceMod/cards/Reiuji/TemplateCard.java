package AliceMod.cards.Reiuji;

import AliceMod.AliceMod;
import AliceMod.abstracts.AbstractAliceCard;
import AliceMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@Deprecated
public class TemplateCard extends AbstractAliceCard {
	public static final String SIMPLE_NAME = TemplateCard.class.getSimpleName();

	public static final String ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;

	public TemplateCard() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.SKILL,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.UNCOMMON,
			CardTarget.SELF
		);
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {

	}
	
	@Override
	public AbstractCard makeCopy() {
		return new TemplateCard();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.initializeDescription();
		}
	}
}