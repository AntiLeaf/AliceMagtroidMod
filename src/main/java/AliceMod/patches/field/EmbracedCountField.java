package AliceMod.patches.field;


import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
		clz = AbstractCard.class,
		method = SpirePatch.CLASS
)
public class EmbracedCountField {
	public static SpireField<Integer> embraced = new SpireField<Integer>(() -> {
		return 0;
	});
	
	public EmbracedCountField() {
	}
}
