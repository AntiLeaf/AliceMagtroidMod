package AliceMagtroidMod.doll.targeting;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.dolls.AbstractDoll;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.cards.targeting.TargetingHandler;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DollOrEnemyTargeting extends TargetingHandler<DollOrEnemyTargeting.DollOrMonster> {
	private DollOrMonster hovered = null;
	
	public DollOrEnemyTargeting() {}
	
	public static DollOrMonster getTarget(AbstractCard card) {
		return (DollOrMonster) CustomTargeting.getCardTarget(card);
	}
	
	public boolean hasTarget(AbstractCard card) {
		return this.hovered != null;
	}
	
	public void updateHovered() {
		this.hovered = null;
		
		for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
			if (monster.isDeadOrEscaped())
				continue;
			
			monster.hb.update();
			if (monster.hb.hovered) {
				this.hovered = new DollOrMonster(monster);
				break;
			}
		}
		
		for (AbstractDoll doll : AliceMagtroidMod.dollManager.getAllDolls()) {
			if (doll.HP <= 0 || doll.getRow() == -1)
				continue;
			
			doll.hb.update();
			if (doll.hb.hovered) {
				this.hovered = new DollOrMonster(doll);
				break;
			}
		}
	}
	
	public DollOrMonster getHovered() {
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
	
	public static class DollOrMonster {
		public Object obj;
		
		public DollOrMonster(AbstractDoll doll) {
			this.obj = doll;
		}
		
		public DollOrMonster(AbstractMonster monster) {
			this.obj = monster;
		}
		
		public boolean isDoll() {
			return this.obj instanceof AbstractDoll;
		}
		
		public boolean isMonster() {
			return this.obj instanceof AbstractMonster;
		}
		
		public AbstractDoll getDoll() {
			return (AbstractDoll) this.obj;
		}
		
		public AbstractMonster getMonster() {
			return (AbstractMonster) this.obj;
		}
	};
}
