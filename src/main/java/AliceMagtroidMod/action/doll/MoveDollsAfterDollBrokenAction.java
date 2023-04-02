//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action.doll;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.action.doll.broken.DollBrokenPostAction;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.patches.enums.ActionTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import java.util.ArrayList;

public class MoveDollsAfterDollBrokenAction extends AbstractGameAction {
	DollBrokenPostAction postAction;
	
	public MoveDollsAfterDollBrokenAction(DollBrokenPostAction postAction) {
		this.actionType = ActionTypeEnum.DOLL_OPERATE;
		
		this.postAction = postAction;
	}
	
	public MoveDollsAfterDollBrokenAction(ArrayList<AbstractDoll> dolls) {
		this(new DollBrokenPostAction(dolls));
	}
	
	public void update() {
		if (!this.isDone) {
			this.addToTop(postAction);
			AliceMagtroidMod.dollManager.checkUpdateAndAddAction();
			
			this.isDone = true;
		}
	}
}
