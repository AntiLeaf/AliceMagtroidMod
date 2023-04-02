//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action.doll.recycle;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.patches.enums.ActionTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class RecycleDollAction extends AbstractGameAction {
	AbstractDoll doll;
	AbstractDoll.Position position;
	int row, col;
	
	public RecycleDollAction(AbstractDoll doll) {
		this.actionType = ActionTypeEnum.DOLL_OPERATE;
		this.duration = AbstractDoll.RECYCLE_ANIM_TIME;
		
		this.doll = doll;
	}
	
	public void update() {
		if (this.shouldCancelAction())
			this.isDone = true;
		else {
			if (this.duration == AbstractDoll.RECYCLE_ANIM_TIME) {
				this.doll.beginToBeRecycled();
			}
			
			AliceMagtroidMod.dollManager.updateAnimation();
			
			this.tickDuration();
			
			if (this.isDone) {
				AliceMagtroidMod.dollManager.recycle(this.doll);
				AliceMagtroidMod.dollManager.checkUpdateAndAddAction();
			}
		}
	}
}
