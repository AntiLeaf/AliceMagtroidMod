package AliceMagtroidMod.doll.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.action.doll.act.DollActAction;
import AliceMagtroidMod.doll.localization.DollStrings;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HouraiDoll extends AbstractDoll {
	public static final String SIMPLE_NAME = HouraiDoll.class.getSimpleName();
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME
			+ ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/dolls/" + SIMPLE_NAME + ".png";
	public static final DollStrings dollStrings = DollStrings.getDollString(ID);
	
	public static final int MAX_HP = 5;
	
	HouraiDoll() {
		super(
				ID,
				IMG_PATH,
				dollStrings.NAME,
				dollStrings.DESCRIPTION
		);
		
		this.maxHP = this.HP = MAX_HP;
		
		this.actAmount = this.baseActAmount = 1;
	}
	
	public void updateDescription() {
		this.description = this.parse(this.rawDescription);
	}
	
	public void act(ActTiming timing) {
		this.applyPowers();
		
		for (AbstractDoll doll : AliceMagtroidMod.dollManager.getRightmostDolls())
			if (doll != null && !(doll instanceof HouraiDoll)) {
				this.addToBot(new DollActAction(doll, ActTiming.HOURAI));
			}
	}
	
	public void applyPowers() {}
	
	public AbstractDoll makeCopy() {
		return new HouraiDoll();
	}
	
	public void playSFX() {
		// TODO
	}
}
