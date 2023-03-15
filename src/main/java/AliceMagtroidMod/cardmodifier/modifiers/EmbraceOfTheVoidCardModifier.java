package AliceMagtroidMod.cardmodifier.modifiers;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceCard;
import AliceMagtroidMod.cardmodifier.StackableCardModifier;
import AliceMagtroidMod.embrace.EmbraceManager;
import AliceMagtroidMod.patches.field.EmbracedCountField;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class EmbraceOfTheVoidCardModifier extends StackableCardModifier {
	private static final String SIMPLE_NAME = EmbraceOfTheVoidCardModifier.class.getSimpleName();
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;

	public EmbraceOfTheVoidCardModifier(int amt) {
		super(amt);
	}

	public EmbraceOfTheVoidCardModifier() {
		super(1);
	}

	@Override
	public String identifier(AbstractCard card) {
		return ID;
	}

	@Override
	public boolean removeOnCardPlayed(AbstractCard card) {
		return !((card instanceof AbstractAliceCard) &&
				!((AbstractAliceCard) card).removeEmbraceAfterPlayed());
	}

	@Override
	public boolean isInherent(AbstractCard card) {
		return false;
	}

	@Override
	public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(
				new VoidCard(), this.amount, true, true));
	}
	
	@Override
	public void afterStackAmount(AbstractCard card, int amt) {
		EmbraceManager.updateEmbraced(card);
	}
	
	@Override
	public void afterReduceAmount(AbstractCard card, int amt) {
		EmbraceManager.updateEmbraced(card);
	}
	
	@Override
	public void onInitialApplication(AbstractCard card) {
		this.afterStackAmount(card, this.amount);
	}
	
	@Override
	public void onRemove(AbstractCard card) {
		this.afterReduceAmount(card, this.amount);
	}

	public AbstractCardModifier makeCopy() {
		return new EmbraceOfTheVoidCardModifier();
	}

	@Override
	public boolean shouldApply(AbstractCard card) {
		return EmbraceManager.getMaxEmbrace(card) > 0;
	}

	@Override
	public String modifyDescription(String rawDescription, AbstractCard card) {
		String res = rawDescription + " NL " +
				GameDictionary.parentWord.get("Embrace") + " " + this.amount;
		
		int amt = EmbracedCountField.embraced.get(card);
		if (amt != this.amount)
				res += "(" + EmbracedCountField.embraced.get(card) + ")";
		
		res += (Settings.lineBreakViaCharacter ? " " : "") + LocalizedStrings.PERIOD;
		return res;
	}
}