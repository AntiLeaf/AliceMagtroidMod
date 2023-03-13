package AliceMod.powers;

import AliceMod.AliceMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ShootingStarPower extends AbstractPower implements InvisiblePower {
	public static final String SIMPLE_NAME = ShootingStarPower.class.getSimpleName();

	public static final String POWER_ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/powers/" + SIMPLE_NAME + ".png";
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS =
			powerStrings.DESCRIPTIONS;

	public ShootingStarPower(int amount) {
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
	public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
		if (type != DamageInfo.DamageType.HP_LOSS)
			damage = Float.max(damage - this.amount, 0f);

		return damage;
	}
	
	@Override
	public void updateDescription() {
		this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
	}

	@Override
	public void atStartOfTurn() {
		this.addToBot(new RemoveSpecificPowerAction(
				AbstractDungeon.player, AbstractDungeon.player,
				ShootingStarPower.POWER_ID));
	}
}