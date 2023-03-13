package AliceMod.cards.Reiuji;

import AliceMod.AliceMod;
import AliceMod.abstracts.AbstractAliceCard;
import AliceMod.action.ReiujiExhaustSpecificCardAction;
import AliceMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SolarWind extends AbstractAliceCard {
	public static final String SIMPLE_NAME = SolarWind.class.getSimpleName();
	
	public static final String ID = AliceMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + SIMPLE_NAME + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK = 4;
	private static final int UPGRADE_PLUS_BLOCK = 2;

	public SolarWind() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			AbstractCard.CardType.SKILL,
			AbstractCardEnum.REIUJI_COLOR,
			AbstractCard.CardRarity.UNCOMMON,
			CardTarget.SELF
		);
		
		this.block = this.baseBlock = BLOCK;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		Map<String, ArrayList<AbstractCard>> map = new HashMap<>();
		
		for (AbstractCard card : p.hand.group) {
			if (card == this)
				continue;
			
			String key = card.cardID;
			
			if (!map.containsKey(key))
				map.put(key, new ArrayList<>());
			
			map.get(key).add(card);
		}
		
		ArrayList<AbstractCard> res = new ArrayList<>();
		
		for (String key : map.keySet()) {
			ArrayList<AbstractCard> tmp = map.get(key);
			res.add(tmp.get(AbstractDungeon.miscRng.random(0, tmp.size() - 1)));
		}
		
		while (!res.isEmpty()) {
			int k = AbstractDungeon.miscRng.random(0, res.size() - 1);
			this.addToBot(new ReiujiExhaustSpecificCardAction(res.get(k), p.hand));
			res.remove(k);
			
			this.addToBot(new GainBlockAction(p, p, this.block));
		}
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new SolarWind();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			
			this.upgradeBlock(UPGRADE_PLUS_BLOCK);
			this.initializeDescription();
		}
	}
}