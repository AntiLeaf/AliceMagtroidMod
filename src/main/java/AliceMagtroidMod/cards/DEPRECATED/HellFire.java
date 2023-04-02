package AliceMagtroidMod.cards.DEPRECATED;

import AliceMagtroidMod.AliceMagtroidMod;
import AliceMagtroidMod.cards.AbstractAliceMagtroidCard;
import AliceMagtroidMod.patches.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HellFire extends AbstractAliceMagtroidCard {
	public static final String SIMPLE_NAME = HellFire.class.getSimpleName();

	public static final String ID = AliceMagtroidMod.SIMPLE_NAME + ":" + SIMPLE_NAME;
	public static final String IMG_PATH = "img/cards/" + ID + ".png";
	private static final CardStrings cardStrings =
			CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;

	private static final int COST = 4;
	private static final int DAMAGE = 10;
	private static final int AMT = 5;
	private static final int UPG_AMT = 2;

	public int cnt = 0;

	public HellFire() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.REIUJI_COLOR,
			CardRarity.UNCOMMON,
			CardTarget.ALL_ENEMY
		);

		this.damage = this.baseDamage = DAMAGE;
		this.magicNumber = this.baseMagicNumber = AMT;
		this.updateStatus();
	}

	private void setValue(int val) {
		int delta = val - this.cnt;

		this.updateCost(delta);
		this.upgradeDamage(delta * this.magicNumber);

		this.cnt = val;
	}

	public void updateStatus() {
		if (CardCrawlGame.dungeon != null &&
				AbstractDungeon.currMapNode != null &&
				AbstractDungeon.getMonsters() != null &&
				!AbstractDungeon.getMonsters().areMonstersDead() &&
				!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {

			int monster_cnt = 0;
			for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
				if (!m.isDead && !m.escaped && !m.isDying && m.currentHealth > 0)
					monster_cnt++;

			if (monster_cnt != this.cnt) {
				this.setValue(monster_cnt);
				super.applyPowers();
			}
		}
	}

	@Override
	public void applyPowers() {
		this.updateStatus();
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.calculateCardDamage(null);

		this.addToBot(new DamageAllEnemiesAction(p,
				this.multiDamage, this.damageTypeForTurn,
				AbstractGameAction.AttackEffect.FIRE));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new HellFire();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPG_AMT);
			this.upgradeDamage(this.cnt * UPG_AMT);
			this.initializeDescription();
		}
	}
}