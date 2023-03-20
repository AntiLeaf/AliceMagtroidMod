//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action.dolls.act;

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
		this.duration = DEFAULT_DURATION; // 0.5F
		
		this.actionType = ActionTypeEnum.DOLL_OPERATE;
		
		this.doll = doll;
		this.timing = timing;
	}
	
	public void update() {
		if (this.shouldCancelAction() || this.doll.shouldCancelAct(this))
			this.isDone = true;
		else {
			// TODO ?
			
			this.doll.updateWhileAct(this, DEFAULT_DURATION - this.duration);
			
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
