package AliceMagtroidMod.patches.field;


import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.StaticSpireField;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
		clz = AbstractCard.class,
		method = SpirePatch.CLASS
)
public class AbstractCardEmbracedColorField {
	public static StaticSpireField<Color> ENERGY_COST_EMBRACED_COLOR =
			new StaticSpireField<Color>(() -> {
		return Color.VIOLET;
	});
	
	public AbstractCardEmbracedColorField() {
	}
}
