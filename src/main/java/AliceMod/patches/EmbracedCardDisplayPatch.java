package AliceMod.patches;

import AliceMod.cardmodifier.StackableCardModifierManager;
import AliceMod.cardmodifier.modifiers.EmbraceOfTheVoidCardModifier;
import AliceMod.patches.field.AbstractCardEmbracedColorField;
import AliceMod.patches.field.EmbracedCountField;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class EmbracedCardDisplayPatch {
	
	@SpirePatch(clz = AbstractCard.class, method = "triggerOnGlowCheck")
	public static class EmbracedCardGlowPatch {
		@SpirePrefixPatch
		public static void Prefix(AbstractCard card) {
			if (StackableCardModifierManager.hasModifier(
					card, EmbraceOfTheVoidCardModifier.ID))
				card.glowColor = Color.VIOLET;
		}
	}
	
	@SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
	public static class EmbracedCardRenderEnergyPatch {
		
		private static class CustomLocator extends SpireInsertLocator {
			public int[] Locate(CtBehavior ctMethodToPatch)
					throws CannotCompileException, PatchingException {
				int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(),
						new Matcher.MethodCallMatcher(
								AbstractCard.class, "freeToPlay"));
				return new int[]{loc[loc.length - 1] + 3}; // Caution!!
			}
		}
		
		@SpireInsertPatch(locator = CustomLocator.class, localvars = {"costColor"})
		public static void Insert(AbstractCard card, SpriteBatch sb, @ByRef Color[] costColor) {
			if (costColor[0] == Color.WHITE && EmbracedCountField.embraced.get(card) > 0)
				costColor[0] = AbstractCardEmbracedColorField.ENERGY_COST_EMBRACED_COLOR.get();
		}
	}
}
