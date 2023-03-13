package AliceMod.cards.Reiuji;

import AliceMod.AliceMod;
import AliceMod.abstracts.AbstractAliceCard;
import AliceMod.patches.enums.AbstractCardEnum;
import AliceMod.powers.IronDefensePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IronDefense extends AbstractAliceCard {
	public static final String SIMPLE_NAME = IronDefense.class.getSimpleName();
	
	public static final String ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK = 3;
	private static final int UPG_BLOCK = 1;

	public IronDefense() {
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
		
		this.block = this.baseBlock = BLOCK;
		this.exhaust = true;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ApplyPowerAction(p, p,
				new IronDefensePower(1)));
		
		this.addToBot(new GainBlockAction(p, p, this.block));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new IronDefense();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPG_BLOCK);
			this.exhaust = false;
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}