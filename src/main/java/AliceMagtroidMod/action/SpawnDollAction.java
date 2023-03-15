//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action;

import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.patches.enums.ActionTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import java.util.ArrayList;
import java.util.Arrays;

public class SpawnDollAction extends AbstractGameAction {
	AbstractDoll doll;
	int row, col;
	
	public SpawnDollAction(AbstractDoll doll, int row, int col) {
		this.actionType = ActionTypeEnum.DOLL;
		
		this.doll = doll;
		this.row = row;
		this.col = col;
	}
	
	public void update() {
		if (!this.isDone) {
			// TODO
			
			this.isDone = true;
		}
	}
}
