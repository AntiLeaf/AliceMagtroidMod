//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action.doll;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.patches.enums.ActionTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class ClearBrokenDollsAction extends AbstractGameAction {
	ArrayList<AbstractDoll> brokenDolls;
	
	public ClearBrokenDollsAction() {
		this.actionType = ActionTypeEnum.DOLL_OPERATE;
		this.duration = AbstractDoll.BROKEN_ANIM_TIME;
	}
	
	public void update() {
		if (this.shouldCancelAction())
			this.isDone = true;
		else {
			if (this.duration == AbstractDoll.BROKEN_ANIM_TIME) {
				this.brokenDolls = AliceMagtroidMod.dollManager.getSpecificDolls(
						doll -> doll.HP == 0);
				
				for (AbstractDoll doll : this.brokenDolls)
					doll.beginToBeBroken();
			}
			
			AliceMagtroidMod.dollManager.updateAnimation();
			
			this.tickDuration();
			
			if (this.isDone) {
				for (AbstractDoll doll : this.brokenDolls)
					AliceMagtroidMod.dollManager.remove(doll);
				
				this.addToTop(new MoveDollsAfterDollBrokenAction(this.brokenDolls));
				
				if (!Settings.FAST_MODE)
					this.addToTop(new WaitAction(0.1F));
			}
		}
	}
}
