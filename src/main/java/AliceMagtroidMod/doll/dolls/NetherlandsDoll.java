package AliceMagtroidMod.doll.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.patches.enums.DamageTypeEnum;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class NetherlandsDoll extends AbstractDoll {
	public static final String SIMPLE_NAME = NetherlandsDoll.class.getSimpleName();
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME
			+ ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/dolls/" + SIMPLE_NAME + ".png";
	
	public static final int MAX_HP = 4;
	public static final int BUFF_AMOUNT = 1;
	public static final int ACT_DMG = 4;
	
	public static final int HOURAI_ACT_DMG = 3;
	
	NetherlandsDoll() {
		super();
		
		this.maxHP = this.HP = MAX_HP;
		
		this.spawnAmount = this.baseSpawnAmount = BUFF_AMOUNT;
		this.actAmount = this.baseActAmount = ACT_DMG;
		
		this.actAtStartOfTurn = true;
	}
	
	public void updateDescription() {
		// TODO
		this.description = "Each Netherlands doll gives alice " + this.spawnAmount + " Strength and Dexterity.\n"
				+ "Act (at start of turn): Alice gains " + this.actAmount + " Vigor.";
	}
	
	@Override
	public void onSpawn() {
		this.applyPowers();
		
		this.addToTop(new ApplyPowerAction(
				AbstractDungeon.player,
				AbstractDungeon.player,
				new StrengthPower(AbstractDungeon.player, this.spawnAmount)));
		this.addToTop(new ApplyPowerAction(
				AbstractDungeon.player,
				AbstractDungeon.player,
				new DexterityPower(AbstractDungeon.player, this.spawnAmount)));
	}
	
	public void act(ActTiming timing) {
		this.applyPowers();
		
		this.addToTop(new ApplyPowerAction(
				AbstractDungeon.player,
				AbstractDungeon.player,
				new VigorPower(AbstractDungeon.player, this.actAmount)));
	}
	
	public void applyPowers() {
		int hourai = AliceMagtroidMod.dollManager.getHouraiCount();
		
		this.actAmount = this.baseActAmount + hourai * HOURAI_ACT_DMG;
	}
	
	public AbstractDoll makeCopy() {
		return new NetherlandsDoll();
	}
	
	public void render(SpriteBatch sb) {
		// TODO
	}
	
	public void playSFX() {
		// TODO
	}
}
