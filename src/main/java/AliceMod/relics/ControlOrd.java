package AliceMod.relics;

import AliceMod.AliceMod;
import AliceMod.powers.HeatPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ControlOrd extends CustomRelic {
	public static final String SIMPLE_NAME = ControlOrd.class.getSimpleName();

	public static final String ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	private static final String IMG = "img/relics/" + SIMPLE_NAME + ".png";
	private static final String IMG_OTL = "img/relics/outline/" + SIMPLE_NAME + ".png";

	public ControlOrd() {
		super(
				ID,
				ImageMaster.loadImage(IMG),
				ImageMaster.loadImage(IMG_OTL),
				RelicTier.COMMON,
				LandingSound.HEAVY
		);
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new ControlOrd();
	}

	@Override
	public void atBattleStartPreDraw() {
		AbstractPlayer p = AbstractDungeon.player;

		int amt = 10 - p.currentHealth * 10 / p.maxHealth;
		this.addToBot(new ApplyPowerAction(p, p, new HeatPower(amt)));
	}
}