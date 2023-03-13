package AliceMod.cardmodifier;

import AliceMod.AliceMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class StackableCardModifierManager {
	public StackableCardModifierManager() {
	}

	@Nullable
	public static AbstractCardModifier getFirstModifier(AbstractCard card, String id) {
		ArrayList<AbstractCardModifier> a = CardModifierManager.getModifiers(card, id);
		return a.isEmpty() ? null : a.get(0);
	}

	@Nullable
	public static StackableCardModifier getStackableModifier(AbstractCard card, String id) {
		AbstractCardModifier modifier = getFirstModifier(card, id);
		return (modifier instanceof StackableCardModifier) ?
				(StackableCardModifier) modifier : null;
	}

	public static int getStackedValue(AbstractCard card, String id) {
		StackableCardModifier modifier = getStackableModifier(card, id);
		return modifier != null ? modifier.amount : 0;
	}

	public static ArrayList<AbstractCardModifier> modifiers(AbstractCard c) {
		return CardModifierManager.modifiers(c);
	}

	public static void addModifier(AbstractCard card, AbstractCardModifier modifier) {
		if (!modifier.shouldApply(card))
			return;

		if (modifier instanceof StackableCardModifier) {
			StackableCardModifier mod = (StackableCardModifier) modifier;
			String id = mod.identifier(card);

			StackableCardModifier original = getStackableModifier(card, id);
			if (original != null) {
				original.stackAmount(card, mod.amount);
				return;
			}
		}

		CardModifierManager.addModifier(card, modifier);
	}

	public static void reduceModifier(AbstractCard card, String id, int amt) {
		StackableCardModifier mod = getStackableModifier(card, id);
		if (mod == null) {
			AliceMod.logger.log(Level.WARN,
					"\"" + id + "\" is not stackable or not found");
			return;
		}

		mod.reduceAmount(card, amt);
	}

	public static void removeSpecificModifier(AbstractCard card, AbstractCardModifier mod, boolean includeInherent) {
		CardModifierManager.removeSpecificModifier(card, mod, includeInherent);
	}

	public static void removeModifiersById(AbstractCard card, String id, boolean includeInherent) {
		CardModifierManager.removeModifiersById(card, id, includeInherent);
	}

	public static boolean hasModifier(AbstractCard card, String id) {
		return CardModifierManager.hasModifier(card, id);
	}

	public static ArrayList<AbstractCardModifier> getModifiers(AbstractCard card, String id) {
		return CardModifierManager.getModifiers(card, id);
	}

	public static void removeAllModifiers(AbstractCard card, boolean includeInherent) {
		CardModifierManager.removeAllModifiers(card, includeInherent);
	}

	public static void copyModifiers(AbstractCard oldCard, AbstractCard newCard, boolean includeInherent, boolean replace, boolean removeOld) {
		CardModifierManager.copyModifiers(oldCard, newCard,
				includeInherent, replace, removeOld);
	}

	public static void testBaseValues(AbstractCard card) {
		CardModifierManager.testBaseValues(card);
	}

	public static void removeEndOfTurnModifiers(final AbstractCard card) {
		CardModifierManager.removeEndOfTurnModifiers(card);
	}

	public static void removeWhenPlayedModifiers(final AbstractCard card) {
		CardModifierManager.removeWhenPlayedModifiers(card);
	}

	public static void onApplyPowers(AbstractCard card) {
		CardModifierManager.onApplyPowers(card);
	}

	public static void onCalculateCardDamage(AbstractCard card, AbstractMonster mo) {
		CardModifierManager.onCalculateCardDamage(card, mo);
	}

	public static String onCreateDescription(AbstractCard card, String rawDescription) {
		return CardModifierManager.onCreateDescription(card, rawDescription);
	}

	public static String onRenderTitle(AbstractCard card, String cardName) {
		return CardModifierManager.onRenderTitle(card, cardName);
	}

	public static void onUseCard(AbstractCard card, AbstractCreature target, UseCardAction action) {
		CardModifierManager.onUseCard(card, target, action);
	}

	public static void onCardDrawn(AbstractCard card) {
		CardModifierManager.onCardDrawn(card);
	}

	public static void onCardExhausted(AbstractCard card) {
		CardModifierManager.onCardExhausted(card);
	}

	public static void onCardRetained(AbstractCard card) {
		CardModifierManager.onCardRetained(card);
	}

	public static float onModifyBaseDamage(float damage, AbstractCard card, AbstractMonster mo) {
		return CardModifierManager.onModifyBaseDamage(damage, card, mo);
	}
	public static float onModifyDamage(float damage, AbstractCard card, AbstractMonster mo) {
		return CardModifierManager.onModifyDamage(damage, card, mo);
	}

	public static float onModifyDamageFinal(float damage, AbstractCard card, AbstractMonster mo) {
		return CardModifierManager.onModifyDamageFinal(damage, card, mo);
	}

	public static float onModifyBaseBlock(float block, AbstractCard card) {
		return CardModifierManager.onModifyBaseBlock(block, card);
	}

	public static float onModifyBlock(float block, AbstractCard card) {
		return CardModifierManager.onModifyBlock(block, card);
	}

	public static float onModifyBlockFinal(float block, AbstractCard card) {
		return CardModifierManager.onModifyBlockFinal(block, card);
	}

	public static float onModifyBaseMagic(float magic, AbstractCard card) {
		return CardModifierManager.onModifyBaseMagic(magic, card);
	}

	public static void onUpdate(AbstractCard card) {
		CardModifierManager.onUpdate(card);
	}

	public static void onRender(AbstractCard card, SpriteBatch sb) {
		CardModifierManager.onRender(card, sb);
	}

	public static void atEndOfTurn(AbstractCard card, CardGroup group) {
		CardModifierManager.atEndOfTurn(card, group);
	}

	public static void onOtherCardPlayed(AbstractCard card, AbstractCard otherCard, CardGroup group) {
		CardModifierManager.onOtherCardPlayed(card, otherCard, group);
	}

	public static boolean canPlayCard(AbstractCard card) {
		return CardModifierManager.canPlayCard(card);
	}

	private static void addToBot(AbstractGameAction action) {
		AbstractDungeon.actionManager.addToBottom(action);
	}

	private static void addToTop(AbstractGameAction action) {
		AbstractDungeon.actionManager.addToTop(action);
	}
}
