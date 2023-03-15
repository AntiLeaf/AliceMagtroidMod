package AliceMagtroidMod.powers;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractAlicePower extends AbstractPower {
	public AbstractAlicePower() {
		super();
	}

	public void triggerOnDollSpawn(AbstractDoll doll, int[] pos) {}
	// Can change the doll
	
	public void triggerAfterDollSpawn(AbstractDoll doll) {}
	
	public void triggerOnDollAct(AbstractDoll doll) {}
	
	public void triggerAfterDollAct(AbstractDoll doll) {}
	
	public void triggerAfterDollBroken(AbstractDoll doll) {}
	
	public void triggerOnDollRecycled(AbstractDoll doll) {}
	
	public void triggerAfterDollRecycled(AbstractDoll doll) {}
}