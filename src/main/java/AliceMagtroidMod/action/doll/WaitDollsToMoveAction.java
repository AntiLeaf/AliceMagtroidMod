//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action.doll;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class WaitDollsToMoveAction extends AbstractGameAction {
	
	public WaitDollsToMoveAction() {
		this.actionType = ActionType.WAIT;
		this.duration = AbstractDoll.MOVE_ANIM_TIME;
	}
	
	public void update() {
		if (this.shouldCancelAction())
			this.isDone = true;
		else {
			AliceMagtroidMod.dollManager.updateAnimation();
			
			this.tickDuration();
			
			if (this.isDone) {
				AliceMagtroidMod.dollManager.resetAllSourceAndDest();
			}
		}
	}
}
