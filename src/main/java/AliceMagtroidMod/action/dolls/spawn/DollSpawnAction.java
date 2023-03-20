//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action.dolls.spawn;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.action.dolls.WaitDollsToMoveAction;
import AliceMagtroidMod.action.dolls.recycle.RecycleDollAction;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.patches.enums.ActionTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DollSpawnAction extends AbstractGameAction {
	AbstractDoll doll;
	AbstractDoll.Position position;
	int row, col;
	
	public DollSpawnAction(AbstractDoll doll, AbstractDoll.Position position, int row, int col) {
		this.actionType = ActionTypeEnum.DOLL_OPERATE;
		
		this.doll = doll;
		this.position = position;
		this.row = row;
		this.col = col;
	}
	
	public DollSpawnAction(AbstractDoll doll, AbstractDoll.Position position, int row) {
		this(doll, position, row, -1);
	}
	
	public void update() {
		if (this.shouldCancelAction())
			this.isDone = true;
		else {
			if (AliceMagtroidMod.dollManager.isRowFull(this.row)) {
				AliceMagtroidMod.addActionsToTop(
						new RecycleDollAction(
								AliceMagtroidMod.dollManager.getLeftmostDoll(this.row)),
						new DollSpawnAction(this.doll, this.position, this.row, this.col)
				);
				
				this.isDone = true;
			}
			else if (this.position == AbstractDoll.Position.MANUAL &&
					AliceMagtroidMod.dollManager.getDoll(this.row, this.col) != null) {
				AliceMagtroidMod.dollManager.reserveForSpawn(this.row, this.col);
				AliceMagtroidMod.addActionsToTop(
						new WaitDollsToMoveAction(),
						new DollSpawnAction(this.doll, this.position, this.row, this.col)
				);
				
				this.isDone = true;
			}
			
			if (!this.isDone) {
				if (this.duration == DEFAULT_DURATION)
					AliceMagtroidMod.dollManager.spawn(this.doll, this.position, this.row, this.col);
				
				this.tickDuration();
				this.doll.updateWhileSpawn(this, DEFAULT_DURATION - this.duration);
			}
		}
	}
}
