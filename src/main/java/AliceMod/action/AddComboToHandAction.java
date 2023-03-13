package AliceMod.action;

import AliceMod.abstracts.AbstractAliceCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static basemod.BaseMod.MAX_HAND_SIZE;

public class AddComboToHandAction extends AbstractGameAction {

	AbstractAliceCard card;

	public AddComboToHandAction(AbstractAliceCard card) {
		this.card = card;
	}
	
	public void update() {
		if (!this.isDone) {
			if (AbstractDungeon.player.hand.size() < MAX_HAND_SIZE)
				this.addToTop(new MakeTempCardInHandAction(card));
			else {
				AbstractDungeon.player.createHandIsFullDialog();
				this.addToTop(new ReiujiExhaustSpecificCardAction(card, null));
			}

			this.isDone = true;
		}
	}
}
