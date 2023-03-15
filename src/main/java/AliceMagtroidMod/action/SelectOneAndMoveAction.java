package AliceMagtroidMod.action;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import java.util.function.Consumer;

public class SelectOneAndMoveAction extends SelectSomeAndMoveAction {
	
	public SelectOneAndMoveAction(CardGroup src, CardGroup dest, int amount, String text,
								  Consumer<AbstractCard> work) {
		super(src.group, src, dest, amount, text, work);
		
		this.actionType = ActionType.CARD_MANIPULATION;
	}
	
	public SelectOneAndMoveAction(CardGroup src, CardGroup dest, int amount, String text) {
		super(src.group, src, dest, amount, text);
	}
}
