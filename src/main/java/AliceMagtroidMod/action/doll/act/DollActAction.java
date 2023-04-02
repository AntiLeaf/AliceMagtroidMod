//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action.doll.act;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.patches.enums.ActionTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DollActAction extends AbstractGameAction {
	AbstractDoll doll;
	AbstractDoll.ActTiming timing;
	
	public DollActAction(AbstractDoll doll, AbstractDoll.ActTiming timing) {
		this.duration = AbstractDoll.ACT_ANIM_TIME;
		
		this.actionType = ActionTypeEnum.DOLL_OPERATE;
		
		this.doll = doll;
		this.timing = timing;
	}
	
	public void update() {
		if (this.shouldCancelAction() || this.doll.shouldCancelAct(this))
			this.isDone = true;
		else {
			if (this.duration == AbstractDoll.ACT_ANIM_TIME) {
				this.doll.beginToAct();
			}
			
			AliceMagtroidMod.dollManager.updateAnimation();
			
			this.tickDuration();
			
			if (this.isDone) {
				this.doll.act(this.timing);
				
				if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
					AbstractDungeon.actionManager.clearPostCombatActions();
			}
			
			if (!Settings.FAST_MODE)
				this.addToTop(new WaitAction(0.1F));
		}
	}
}
