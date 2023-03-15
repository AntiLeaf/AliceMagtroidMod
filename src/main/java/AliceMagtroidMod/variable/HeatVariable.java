package AliceMagtroidMod.variable;

import AliceMagtroidMod.cards.AbstractAliceCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class HeatVariable extends DynamicVariable {
		@Override
		public String key() {
			return "Heat";
		}

		@Override
		public boolean isModified(AbstractCard c) {
			if (!(c instanceof AbstractAliceCard))
				return false;

			AbstractAliceCard card = (AbstractAliceCard) c;
			return card.heat == card.baseHeat;
		}

		@Override
		public void setIsModified(AbstractCard card, boolean v) {
			// Do something such that isModified will return the value v.
			// This method is only necessary if you want smith upgrade previews to function correctly.
		}

		@Override
		public int value(AbstractCard card) {
			return (card instanceof AbstractAliceCard) ?
					((AbstractAliceCard) card).heat : 0;
		}

		@Override
		public int baseValue(AbstractCard card) {
			return (card instanceof AbstractAliceCard) ?
					((AbstractAliceCard) card).baseHeat : 0;
		}

		@Override
		public boolean upgraded(AbstractCard card) {
			return this.isModified(card);
		}
}
