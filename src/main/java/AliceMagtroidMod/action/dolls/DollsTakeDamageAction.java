//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.patches.enums.ActionTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import java.util.HashMap;

public class DollsTakeDamageAction extends AbstractGameAction {
	HashMap<AbstractDoll, Integer> damageTbl;
	
	public DollsTakeDamageAction(HashMap<AbstractDoll, Integer> damageTbl) {
		this.actionType = ActionTypeEnum.DOLL_OPERATE;
		
		this.damageTbl = damageTbl;
	}
	
	public DollsTakeDamageAction() {
		this(new HashMap<>());
	}
	
	void add(AbstractDoll doll, int damage) {
		if (this.damageTbl.containsKey(doll))
			this.damageTbl.put(doll, this.damageTbl.get(doll) + damage);
		else
			this.damageTbl.put(doll, damage);
	}
	
	public void update() {
		if (!this.isDone) {
			if (AliceMagtroidMod.dollManager.handleDamageTbl(this.damageTbl))
				this.addToTop(new ClearBrokenDollsAction());
			
			this.isDone = true;
		}
	}
}
