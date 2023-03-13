package AliceMod.powers;

import AliceMod.AliceMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class HellWaveCannonPower extends TwoAmountPower {
	public static final String SIMPLE_NAME = HellWaveCannonPower.class.getSimpleName();

	public static final String POWER_ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/powers/" + SIMPLE_NAME + ".png";
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS =
			powerStrings.DESCRIPTIONS;

	public HellWaveCannonPower(int tempHP, int heat) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = tempHP;
		this.amount2 = heat;
		
		this.type = PowerType.BUFF;
		this.updateDescription();
		this.img = new Texture("img/powers/Nineball32.png");
//		this.img = new Texture(IMG_PATH);
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount +
				DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
		
		boolean flag = false;
		for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn)
			if (c.type == AbstractCard.CardType.ATTACK) {
				flag = true;
				break;
			}
		
		this.description += (flag ? DESCRIPTIONS[3] : DESCRIPTIONS[4]);
	}

	@Override
	public void atEndOfTurn(boolean isPlayer) {
		if (isPlayer) {
			boolean flag = false;
			for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn)
				if (c.type == AbstractCard.CardType.ATTACK) {
					flag = true;
					break;
				}
			
			if (!flag) {
				this.addToBot(new AddTemporaryHPAction(
						this.owner, this.owner, this.amount));
				this.addToBot(new ApplyPowerAction(
						this.owner, this.owner, new HeatPower(this.amount2)));
			}
			
			this.addToBot(new RemoveSpecificPowerAction(
					AbstractDungeon.player, AbstractDungeon.player, HellWaveCannonPower.POWER_ID));
		}
	}
}