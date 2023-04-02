package AliceMagtroidMod.doll.targeting;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.cards.targeting.TargetingHandler;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DollOrSelfTargeting extends TargetingHandler<DollOrSelfTargeting.DollOrPlayer> {
	private DollOrPlayer hovered = null;
	
	public DollOrSelfTargeting() {}
	
	public static DollOrPlayer getTarget(AbstractCard card) {
		return (DollOrPlayer) CustomTargeting.getCardTarget(card);
	}
	
	public boolean hasTarget(AbstractCard card) {
		return this.hovered != null;
	}
	
	public void updateHovered() {
		this.hovered = null;
		
		AbstractPlayer player = AbstractDungeon.player;
		player.hb.update();
		if (player.hb.hovered) {
			this.hovered = new DollOrPlayer(player);
			return;
		}
		
		for (AbstractDoll doll : AliceMagtroidMod.dollManager.getAllDolls()) {
			if (doll.HP <= 0 || doll.getRow() == -1)
				continue;
			
			doll.hb.update();
			if (doll.hb.hovered) {
				this.hovered = new DollOrPlayer(doll);
				break;
			}
		}
	}
	
	public DollOrPlayer getHovered() {
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
		// TODO: what is this?
	}
	
	@Override
	public void updateKeyboardTarget() {
		// TODO: this part may be very complicated
	}
	
	public static class DollOrPlayer {
		public Object obj;
		
		public DollOrPlayer(AbstractDoll doll) {
			this.obj = doll;
		}
		
		public DollOrPlayer(AbstractPlayer player) {
			this.obj = player;
		}
		
		public boolean isDoll() {
			return this.obj instanceof AbstractDoll;
		}
		
		public boolean isPlayer() {
			return this.obj instanceof AbstractPlayer;
		}
		
		public AbstractDoll getDoll() {
			return (AbstractDoll) this.obj;
		}
		
		public AbstractPlayer getPlayer() {
			return (AbstractPlayer) this.obj;
		}
	};
}
