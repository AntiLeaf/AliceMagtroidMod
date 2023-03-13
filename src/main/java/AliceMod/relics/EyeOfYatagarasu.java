package AliceMod.relics;

import AliceMod.AliceMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class EyeOfYatagarasu extends CustomRelic {
	public static final String SIMPLE_NAME = EyeOfYatagarasu.class.getSimpleName();

	public static final String ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	private static final String IMG = "img/relics/" + SIMPLE_NAME + ".png";
	private static final String IMG_OTL = "img/relics/outline/" + SIMPLE_NAME + ".png";

	public static final int AMT = 3;

	public EyeOfYatagarasu() {
		super(
				ID,
				ImageMaster.loadImage(IMG),
				ImageMaster.loadImage(IMG_OTL),
				RelicTier.STARTER,
				LandingSound.HEAVY
		);

		this.counter = 0;
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new EyeOfYatagarasu();
	}

	@Override
	public void onPlayerEndTurn() {
		int maxHeal = (AbstractDungeon.player.maxHealth + 4) / 5;

		if (EnergyPanel.getCurrentEnergy() > 0) {
			this.counter += AMT;
			this.counter = Integer.min(this.counter, maxHeal);

			this.beginLongPulse();
		}
	}

	@Override
	public void onVictory() {
		if (this.counter > 0)
			AbstractDungeon.player.heal(this.counter, true);

		this.counter = 0;
		this.stopPulse();
	}
}