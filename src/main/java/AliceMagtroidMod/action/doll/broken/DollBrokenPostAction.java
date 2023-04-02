//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action.doll.broken;

import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.patches.enums.ActionTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import java.util.ArrayList;

public class DollBrokenPostAction extends AbstractGameAction {
	ArrayList<AbstractDoll> brokenDolls;
	
	public DollBrokenPostAction(ArrayList<AbstractDoll> brokenDolls) {
		this.actionType = ActionTypeEnum.DOLL_OPERATE;
//		this.duration = AbstractDoll.BROKEN_ANIM_TIME;
		
		this.brokenDolls = brokenDolls;
	}
	
	public void update() {
		if (!this.isDone) {
			for (AbstractDoll doll : this.brokenDolls) {
				doll.onBroken();
				doll.onBrokenOrRecycled();
			}
			
			this.isDone = true;
		}
	}
}
