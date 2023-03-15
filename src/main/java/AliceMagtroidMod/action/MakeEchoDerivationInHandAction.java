//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MakeEchoDerivationInHandAction extends AbstractGameAction {
    public AbstractReiujiEchoCard card;

    public MakeEchoDerivationInHandAction(AbstractReiujiEchoCard derivation) {
        this.card = derivation;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (!this.isDone) {
            AbstractPlayer p = AbstractDungeon.player;
            
            if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                p.createHandIsFullDialog();
                card.triggerOnDerivationLeavesHand(false, true, false);
            }
            else
                p.hand.addToBottom(card.makeDerivation());

            this.isDone = true;
        }
    }
}
