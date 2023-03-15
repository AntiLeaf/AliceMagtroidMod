package AliceMagtroidMod.powers;

import AliceMagtroidMod.AliceMagtroidMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class IronDefensePower extends AbstractPower {
	public static final String SIMPLE_NAME = IronDefensePower.class.getSimpleName();

	public static final String POWER_ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/powers/" + SIMPLE_NAME + ".png";
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS =
			powerStrings.DESCRIPTIONS;

	public IronDefensePower(int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		
		this.type = PowerType.BUFF;
		this.updateDescription();
		this.img = new Texture("img/powers/Nineball32.png");
//		this.img = new Texture(IMG_PATH);
	}
	
	@Override
	public void updateDescription() {
		this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
	}
	
	@Override
	public void onGainedBlock(float amt) {
		this.addToBot(new AddTemporaryHPAction(this.owner, this.owner,
				this.amount * (int) amt));
	}

	@Override
	public void atEndOfTurn(boolean isPlayer) {
		if (isPlayer) {
			this.addToBot(new RemoveSpecificPowerAction(
					AbstractDungeon.player, AbstractDungeon.player, this));
		}
	}
}