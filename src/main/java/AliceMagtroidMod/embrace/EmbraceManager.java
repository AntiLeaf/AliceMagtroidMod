package AliceMagtroidMod.embrace;

import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.cardmodifier.StackableCardModifierManager;
import AliceMagtroidMod.patches.field.EmbracedCountField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import org.jetbrains.annotations.NotNull;

public class EmbraceManager {
	public EmbraceManager() {
	
	}
	
	public static int getEmbrace(AbstractCard card) {
		return StackableCardModifierManager.getStackedValue(
				card, EmbraceOfTheVoidCardModifier.ID);
	}
	
	public static int getMaxEmbrace(AbstractCard card) {
		if (card instanceof AbstractAliceMagtroidCard) {
			AbstractAliceMagtroidCard c = (AbstractAliceMagtroidCard) card;
			
			if (c.canHaveInfinityEmbrace())
				return Integer.MAX_VALUE;
			
			if (c.cantBePlayed)
				return 0;
		}
		
		if (card.costForTurn < 0)
			return card.costForTurn < -1 ? 0 : Integer.MAX_VALUE;
		
		return card.cost - EmbraceManager.getEmbrace(card);
	}
	
	public static void addEmbrace(AbstractCard card, int amt) {
		amt = Integer.min(amt, EmbraceManager.getMaxEmbrace(card));
		
		if (amt > 0) {
			if (card instanceof AbstractAliceMagtroidCard)
				amt = ((AbstractAliceMagtroidCard) card).onEmbracedModifyAmount(amt);
			
			if (amt > 0)
				StackableCardModifierManager.addModifier(
						card, new EmbraceOfTheVoidCardModifier(amt));
		}
	}
	
	public static void calcEmbraced(@NotNull AbstractCard card) {
		if (card.costForTurn <= 0)
			return;
		
		int amt = Integer.min(card.costForTurn, EmbraceManager.getEmbrace(card));
		card.updateCost(-amt);
		
		EmbracedCountField.embraced.set(card, amt);
	}
	
	public static void resetEmbraced(@NotNull AbstractCard card) {
		if (card.costForTurn < 0)
			return;
		
		int amt = EmbracedCountField.embraced.get(card);
		if (amt > 0)
			card.updateCost(amt);
		
		EmbracedCountField.embraced.set(card, 0);
	}
	
	public static void updateEmbraced(@NotNull AbstractCard card) {
		if (card.costForTurn >= 0) {
			EmbraceManager.resetEmbraced(card);
			EmbraceManager.calcEmbraced(card);
		}
	}
}
