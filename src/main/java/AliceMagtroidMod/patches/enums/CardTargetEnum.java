package AliceMagtroidMod.patches.enums;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CardTargetEnum {
	
	@SpireEnum
	public static AbstractCard.CardTarget DOLL;
	
	@SpireEnum
	public static AbstractCard.CardTarget DOLL_OR_ENEMY;
	
	@SpireEnum
	public static AbstractCard.CardTarget DOLL_OR_SELF;
}
