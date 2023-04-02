package AliceMagtroidMod.doll.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.doll.localization.DollStrings;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class NetherlandsDoll extends AbstractDoll {
	public static final String SIMPLE_NAME = NetherlandsDoll.class.getSimpleName();
	public static final String ID = AliceMagtroidMod.SIMPLE_NAME
			+ ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/dolls/" + SIMPLE_NAME + ".png";
	public static final DollStrings dollStrings = DollStrings.getDollString(ID);
	
	public static final int MAX_HP = 4;
	public static final int BUFF_AMOUNT = 1;
	public static final int ACT_AMOUNT = 4;
	
	public static final int HOURAI_ACT_AMOUNT = 2;
	
	NetherlandsDoll() {
		super(
				ID,
				IMG_PATH,
				dollStrings.NAME,
				dollStrings.DESCRIPTION
		);
		
		this.maxHP = this.HP = MAX_HP;
		
		this.passiveAmount = this.basePassiveAmount = BUFF_AMOUNT;
		this.actAmount = this.baseActAmount = ACT_AMOUNT;
		
		this.actAtStartOfTurn = true;
	}
	
	public void updateDescription() {
		this.description = this.parse(this.rawDescription, HOURAI_ACT_AMOUNT);
	}
	
	@Override
	public void onSpawn() {
		this.applyPowers();
		
		this.addToTop(new ApplyPowerAction(
				AbstractDungeon.player,
				AbstractDungeon.player,
				new StrengthPower(AbstractDungeon.player, this.passiveAmount)));
		this.addToTop(new ApplyPowerAction(
				AbstractDungeon.player,
				AbstractDungeon.player,
				new DexterityPower(AbstractDungeon.player, this.passiveAmount)));
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
		
		this.actAmount = this.baseActAmount + hourai * HOURAI_ACT_AMOUNT;
	}
	
	public AbstractDoll makeCopy() {
		return new NetherlandsDoll();
	}
	
	public void playSFX() {
		// TODO
	}
}
