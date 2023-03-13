//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ReiujiExhaustSpecificCardAction extends AbstractGameAction {
    private AbstractCard targetCard;
    private CardGroup group;

    public ReiujiExhaustSpecificCardAction(AbstractCard targetCard, CardGroup group) {
        this.targetCard = targetCard;
        this.actionType = ActionType.EXHAUST;
        this.group = group;
    }

    public void update() {
        if (!this.isDone) {
            if (this.group == null || !this.group.group.contains(this.targetCard)) {
                this.group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                this.group.group.add(this.targetCard);
            }

            this.group.moveToExhaustPile(targetCard);
            CardCrawlGame.dungeon.checkForPactAchievement();
            this.targetCard.exhaustOnUseOnce = false;
            this.targetCard.freeToPlayOnce = false;

            this.isDone = true;
        }
    }
}
