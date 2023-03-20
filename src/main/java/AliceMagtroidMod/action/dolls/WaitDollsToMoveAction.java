//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class WaitDollsToMoveAction extends AbstractGameAction {
	
	public WaitDollsToMoveAction() {
		this.actionType = ActionType.WAIT;
		this.duration = DEFAULT_DURATION;
	}
	
	public void update() {
		if (this.shouldCancelAction())
			this.isDone = true;
		else {
			AliceMagtroidMod.dollManager.updateMovingDolls(
					this, DEFAULT_DURATION - this.duration);
			
			this.tickDuration();
			
			if (this.isDone) {
				AliceMagtroidMod.dollManager.resetAllSourceAndDest();
			}
		}
	}
}
