package AliceMagtroidMod.powers;

import AliceMagtroidMod.doll.dolls.AbstractDoll;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractAliceMagtroidPower extends AbstractPower {
	public AbstractAliceMagtroidPower() {
		super();
	}

	public void triggerOnDollSpawn(AbstractDoll doll, int[] pos) {}
	// Can do modify to the doll
	
	public void triggerAfterDollSpawn(AbstractDoll doll) {}
	
	public void triggerOnDollAct(AbstractDoll doll) {}
	
	public void triggerAfterDollAct(AbstractDoll doll) {}
	
	public void triggerAfterDollBroken(AbstractDoll doll) {}
	
	public void triggerOnDollRecycled(AbstractDoll doll) {}
	
	public void triggerAfterDollRecycled(AbstractDoll doll) {}
}