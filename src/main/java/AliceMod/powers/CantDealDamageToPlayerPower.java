package AliceMod.powers;

import AliceMod.AliceMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CantDealDamageToPlayerPower extends AbstractPower {
	public static final String SIMPLE_NAME = CantDealDamageToPlayerPower.class.getSimpleName();

	public static final String POWER_ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/powers/" + SIMPLE_NAME + ".png";
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS =
			powerStrings.DESCRIPTIONS;

	public CantDealDamageToPlayerPower(AbstractMonster owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		
		this.type = PowerType.DEBUFF;
		this.updateDescription();
		this.img = new Texture("img/powers/Nineball32.png");
//		this.img = new Texture(IMG_PATH);
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]
				+ this.owner.name + DESCRIPTIONS[2];
	}

	@Override
	public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
		if (type != DamageInfo.DamageType.THORNS &&
				type != DamageInfo.DamageType.HP_LOSS)
			return 0;

		return damage;
	}

	@Override
	public void atEndOfTurn(boolean isPlayer) {
		if (!isPlayer) {
			if (this.amount == 1)
				this.addToBot(new RemoveSpecificPowerAction(
						this.owner, this.owner,
						CantDealDamageToPlayerPower.POWER_ID));
			else
				this.addToBot(new ReducePowerAction(
						this.owner, this.owner,
						CantDealDamageToPlayerPower.POWER_ID, 1));
		}
	}
}