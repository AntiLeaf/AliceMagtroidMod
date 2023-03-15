//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action;

import AliceMagtroidMod.embrace.EmbraceManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AddEmbraceAction extends AbstractGameAction {
    private AbstractCard targetCard;
    private int amount;

    public AddEmbraceAction(AbstractCard targetCard, int amount) {
        this.targetCard = targetCard;
        this.amount = amount;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (!this.isDone) {
            EmbraceManager.addEmbrace(this.targetCard, this.amount);
            this.targetCard.applyPowers();
            
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();

            this.isDone = true;
        }
    }
}
