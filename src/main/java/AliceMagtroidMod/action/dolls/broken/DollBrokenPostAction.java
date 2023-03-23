//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action.dolls.broken;

import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.patches.enums.ActionTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class DollBrokenPostAction extends AbstractGameAction {
	ArrayList<AbstractDoll> brokenDolls;
	
	public DollBrokenPostAction(ArrayList<AbstractDoll> brokenDolls) {
		this.actionType = ActionTypeEnum.DOLL_OPERATE;
		this.duration = DEFAULT_DURATION; // 0.5F
		
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
