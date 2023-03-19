//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AliceMagtroidMod.action;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import AliceMagtroidMod.patches.enums.ActionTypeEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import java.util.ArrayList;
import java.util.Arrays;

public class SpawnDollAction extends AbstractGameAction {
	AbstractDoll doll;
	AbstractDoll.Position position;
	int row, col;
	
	public SpawnDollAction(AbstractDoll doll, AbstractDoll.Position position, int row, int col) {
		this.actionType = ActionTypeEnum.DOLL;
		
		this.doll = doll;
		this.position = position;
		this.row = row;
		this.col = col;
	}
	
	public void update() {
		if (!this.isDone) {
			AliceMagtroidMod.dollManager.spawn(this.doll, this.position, this.row, this.col);
			
			this.isDone = true;
		}
	}
}
