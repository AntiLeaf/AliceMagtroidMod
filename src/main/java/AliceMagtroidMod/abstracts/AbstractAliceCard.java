package AliceMagtroidMod.abstracts;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.powers.InvisibleHasUsedSpellPower;
import AliceMagtroidMod.relics.StarryCloak;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static AliceMagtroidMod.AliceMagtroidMod.PUPPETEER_FLAVOR;

public abstract class AbstractAliceCard extends CustomCard implements SpawnModificationCard {
	protected static final Color CYAN_COLOR = new Color(0f, 204f / 255f, 0f, 1f);

	public boolean cantBePlayed = false;
	public boolean isSpellCard = false;
	public boolean isSupplement = false;

	public int baseHeat = -1;
	public int heat = -1;
	public boolean upgradedHeat = false;
	public boolean isHeatModified = false;
	
	public int baseTempHP = -1;
	public int tempHP = -1;
	public boolean upgradedTempHP = false;
	public boolean isTempHPModified = false;
	
	public AbstractAliceCard(
			String id,
			String name,
			String img,
			int cost,
			String rawDescription,
			AbstractCard.CardType type,
			AbstractCard.CardColor color,
			AbstractCard.CardRarity rarity,
			AbstractCard.CardTarget target
	) {
		super(
				id,
				name,
				"img/cards/Reiuji/Th123Cirno.png",
				cost,
				rawDescription,
				type,
				color,
				rarity,
				target
		);
		
		FlavorText.AbstractCardFlavorFields.boxColor.set(this, PUPPETEER_FLAVOR);
	}

	@Override
	public void triggerWhenDrawn() {
		if (this.isSupplement)
			this.addToTop(new DrawCardAction(1));

		super.triggerWhenDrawn();
	}

	@Override
	public void applyPowers() {
		super.applyPowers();

		this.heat = this.baseHeat;
		this.tempHP = this.baseTempHP;

		if (AbstractDungeon.player.hasRelic(StarryCloak.ID)) {
			if (this.baseHeat >= 0)
				this.heat += 1;

			if (this.baseTempHP >= 0)
				this.tempHP += 1;
		}
		
		this.isHeatModified = (this.heat != this.baseHeat);
		this.isTempHPModified = (this.tempHP != this.baseTempHP);
	}

	@Override
	 public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		if (!super.canUse(p, m))
			return false;

		if (this.cantBePlayed)
			return false;

		return !(this.isSpellCard &&
				p.hasPower(InvisibleHasUsedSpellPower.POWER_ID) &&
				this != ((InvisibleHasUsedSpellPower) p.getPower(InvisibleHasUsedSpellPower.POWER_ID)).spell);
	}

	@Override
	public void triggerOnGlowCheck() {
		this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
	}

	@Override
	public AbstractCard makeStatEquivalentCopy() {
		AbstractAliceCard card = (AbstractAliceCard) super.makeStatEquivalentCopy();

		card.cantBePlayed = this.cantBePlayed;
		card.isSpellCard = this.isSpellCard;
		card.isSupplement = this.isSupplement;
		
		card.baseHeat = this.baseHeat;
		card.heat = this.heat;
		card.upgradedHeat = this.upgradedHeat;
		card.isHeatModified = this.isHeatModified;
		
		card.baseTempHP = this.baseTempHP;
		card.tempHP = this.tempHP;
		card.upgradedTempHP = this.upgradedTempHP;
		card.isTempHPModified = this.isTempHPModified;
		
		// Copilot is good ↑

		return card;
	}

	public void triggerOnLeaveHand(boolean isExhaust, boolean isEndOfTurn) {

	}

	public void triggerOnLeaveHand(boolean isExhaust) {
		this.triggerOnLeaveHand(isExhaust, false);
	}

	@Override
	public void triggerOnExhaust() {
		this.triggerOnLeaveHand(true);
		super.triggerOnExhaust();
	}

	@Override
	public void triggerOnManualDiscard() {
		this.triggerOnLeaveHand(false, false);
		super.triggerOnManualDiscard();
	}

	@Override
	public void triggerOnEndOfPlayerTurn() {
		if (!this.retain)
			this.triggerOnLeaveHand(false, true);
		
		super.triggerOnEndOfPlayerTurn();
	}

	public boolean removeEmbraceAfterPlayed() {
		return true;
	}
	
	public boolean canHaveInfinityEmbrace() {
		return false;
	}
	
	public int onEmbracedModifyAmount(int amount) {
		return amount;
	}

	public void addActionsToTop(AbstractGameAction... actions) {
		AliceMagtroidMod.addActionsToTop(actions);
	}

	public String bracketedName() {
		return "(" + this.name + ")";
	}
}
