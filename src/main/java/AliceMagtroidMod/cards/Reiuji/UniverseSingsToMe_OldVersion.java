package AliceMagtroidMod.cards.Reiuji;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceCard;
import AliceMagtroidMod.cards.ReiuijiDerivation.*;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

@Deprecated
public class UniverseSingsToMe_OldVersion extends AbstractAliceCard {
	public static final String SIMPLE_NAME = UniverseSingsToMe_OldVersion.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 5;

	public UniverseSingsToMe_OldVersion() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.SKILL,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.RARE,
			CardTarget.SELF
		);

		this.isSpellCard = true;
		this.exhaust = true;
		AlwaysRetainField.alwaysRetain.set(this, true);
		this.ignoreEnergyOnUse = true;

		this.cardsToPreview = new MolecularCloud();
	}

	@Override
	public boolean hasEnoughEnergy() {
		AbstractPlayer p = AbstractDungeon.player;

		for (AbstractRelic r : p.relics)
			if (!r.canPlay(this))
				return false;

		for (AbstractBlight b : p.blights)
			if (!b.canPlay(this))
				return false;

		for (AbstractCard c : p.hand.group)
			if (!c.canPlay(this))
				return false;

		if (!this.freeToPlay()) {
			int cnt = EnergyPanel.getCurrentEnergy();
			for (AbstractCard card : p.hand.group)
				if (card != this)
					cnt++;

			if (cnt < this.costForTurn) {
				this.cantUseMessage = AbstractCard.TEXT[11];
				return false;
			}
			else
				return true;
		}

		return true;
	}

	@Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		return super.canUse(p, m);
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (!this.freeToPlay()) {
			int used = Integer.max(this.costForTurn,
					EnergyPanel.getCurrentEnergy());

			if (used > 0)
				EnergyPanel.useEnergy(used);

			int amt = this.costForTurn - used;
			if (amt > 0) {
				this.addToTop(new SelectCardsInHandAction(
						amt, "", false, false,
						(c) -> c != this,
						(cards) -> {
							for (AbstractCard card : cards)
								p.hand.moveToExhaustPile(card);
						}
				));
			}
		}

		AbstractCard temp = new MolecularCloud();
		if (this.upgraded)
			temp.upgrade();

		this.addToBot(new MakeTempCardInDrawPileAction(
				temp, 1, true, true));
	}

	@Override
	public AbstractCard makeCopy() {
		return new UniverseSingsToMe_OldVersion();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.initializeDescription();

			this.cardsToPreview.upgrade();
		}
	}

	public static boolean isDerivation(AbstractCard c) {
		return (c instanceof MolecularCloud) ||
				(c instanceof MainSequenceStar) ||
				(c instanceof RedGiantStar) ||
				(c instanceof Supernova) ||
				(c instanceof BlackHole);
	}
}