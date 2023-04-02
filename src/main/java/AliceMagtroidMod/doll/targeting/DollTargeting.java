package AliceMagtroidMod.doll.targeting;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.cards.targeting.TargetingHandler;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DollTargeting extends TargetingHandler<AbstractDoll> {
	private AbstractDoll hovered = null;
	
	public DollTargeting() {}
	
	public static AbstractDoll getTarget(AbstractCard card) {
		return (AbstractDoll) CustomTargeting.getCardTarget(card);
	}
	
	public boolean hasTarget(AbstractCard card) {
		return this.hovered != null;
	}
	
	public void updateHovered() {
		this.hovered = null;
		
		for (AbstractDoll doll : AliceMagtroidMod.dollManager.getAllDolls()) {
			if (doll.HP <= 0 || doll.getRow() == -1)
				continue;
			
			doll.hb.update();
			if (doll.hb.hovered) {
				this.hovered = doll;
				break;
			}
		}
	}
	
	public AbstractDoll getHovered() {
		return this.hovered;
	}
	
	public void clearHovered() {
		this.hovered = null;
	}
	
	public boolean hasTarget() {
		return this.hovered != null;
	}
	
	@Override
	public void renderReticle(SpriteBatch sb) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void updateKeyboardTarget() {
		// TODO Auto-generated method stub
	}
}
