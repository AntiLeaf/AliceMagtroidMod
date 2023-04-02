//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action.doll;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.patches.enums.ActionTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DollsClearBlockOnPlayerTurnStartAction extends AbstractGameAction {

	public DollsClearBlockOnPlayerTurnStartAction() {
		this.actionType = ActionTypeEnum.DOLL_OPERATE;
	}
	
	public void update() {
		if (!this.isDone) {
			AliceMagtroidMod.dollManager.clearBlockOnPlayerTurnStart();
			this.isDone = true;
		}
	}
}
