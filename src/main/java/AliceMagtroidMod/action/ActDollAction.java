//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.patches.enums.ActionTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class ActDollAction extends AbstractGameAction {
	AbstractDoll doll;
	AbstractDoll.ActTiming timing;
	
	public ActDollAction(AbstractDoll doll, AbstractDoll.ActTiming timing) {
		this.actionType = ActionTypeEnum.DOLL;
		
		this.doll = doll;
		this.timing = timing;
	}
	
	public void update() {
		if (!this.isDone) {
			AliceMagtroidMod.dollManager.act(this.doll, this.timing);
			
			this.isDone = true;
		}
	}
}
