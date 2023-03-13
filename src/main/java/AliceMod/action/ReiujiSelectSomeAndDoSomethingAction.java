//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMod.action;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.function.Consumer;

@Deprecated
public class ReiujiSelectSomeAndDoSomethingAction extends AbstractGameAction {
	private final SelectCardsAction act;
	private final CardGroup selected;
	private final int amount;

	public ReiujiSelectSomeAndDoSomethingAction(
			ArrayList<AbstractCard> cardList,
            int amount, String text, Consumer<AbstractCard> work) {
		this.amount = amount;
		this.selected = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

		this.act = new SelectCardsAction(cardList, amount, text, (cards) -> {
			for (AbstractCard card : cards) {
				this.selected.addToBottom(card);
				work.accept(card);
//				card.stopGlowing();
			}

			AbstractDungeon.player.hand.refreshHandLayout();
		});
	}
	
	public void update() {
		if (!this.isDone) {
			if (this.amount > 0)
				this.addToTop(this.act);
			
			this.isDone = true;
		}
	}
}
