package AliceMagtroidMod;

import AliceMagtroidMod.action.doll.DollsClearBlockOnPlayerTurnStartAction;
import AliceMagtroidMod.action.doll.DollsTakeDamageAction;
import AliceMagtroidMod.cards.DEPRECATED.*;
import AliceMagtroidMod.characters.AliceMagtroid;
import AliceMagtroidMod.doll.DollManager;
import AliceMagtroidMod.doll.localization.DollStrings;
import AliceMagtroidMod.relics.EyeOfYatagarasu;
import AliceMagtroidMod.variable.HeatVariable;
import AliceMagtroidMod.variable.TempHPVariable;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static AliceMagtroidMod.patches.enums.AliceMagtroidModClassEnum.*;
import static AliceMagtroidMod.patches.enums.AbstractCardEnum.*;

@SuppressWarnings("Duplicates")
@SpireInitializer
public class AliceMagtroidMod implements PostExhaustSubscriber,
		PostBattleSubscriber,
		PostDungeonInitializeSubscriber,
		EditCharactersSubscriber,
		PostInitializeSubscriber,
		EditRelicsSubscriber,
		EditCardsSubscriber,
		EditStringsSubscriber,
		OnCardUseSubscriber,
		EditKeywordsSubscriber,
		OnPowersModifiedSubscriber,
		PostDrawSubscriber,
		PostEnergyRechargeSubscriber,
		OnPlayerLoseBlockSubscriber,
		OnPlayerDamagedSubscriber,
		OnStartBattleSubscriber,
		OnPlayerTurnStartSubscriber,
		PostPlayerUpdateSubscriber,
		RenderSubscriber {
	public static final String SIMPLE_NAME = AliceMagtroidMod.class.getSimpleName();
	
	public static final Logger logger = LogManager.getLogger(AliceMagtroidMod.class.getName());
	
//	private static final String MOD_BADGE = "img/UI/badge.png";
	
	//card backgrounds
	private static final String ATTACK_CC = "img/512/bg_attack_reiuji_s.png";
	private static final String SKILL_CC = "img/512/bg_skill_reiuji_s.png";
	private static final String POWER_CC = "img/512/bg_power_reiuji_s.png";
	private static final String ENERGY_ORB_CC = "img/512/cardOrb.png";
	
	private static final String ATTACK_CC_PORTRAIT = "img/1024/bg_attack_reiuji.png";
	private static final String SKILL_CC_PORTRAIT = "img/1024/bg_skill_reiuji.png";
	private static final String POWER_CC_PORTRAIT = "img/1024/bg_power_reiuji.png";
	private static final String ENERGY_ORB_CC_PORTRAIT = "img/1024/cardOrb.png";
	
	public static final Color PUPPETEER = CardHelper.getColor(0, 191, 255);
	public static final Color PUPPETEER_FLAVOR = CardHelper.getColor(204, 255, 255);
	public static final String CARD_ENERGY_ORB = "img/UI/energyOrb.png";
	
	private static final String MY_CHARACTER_BUTTON = "img/charSelect/AliceButton.png";
	private static final String ALICE_PORTRAIT = "img/charSelect/AlicePortrait.jpg";
	
	private final ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
	//private ArrayList<AbstractRelic> relicsToAdd = new ArrayList<>();
	
	public AliceMagtroidMod() {
		BaseMod.subscribe(this);
		logger.info("creating the color : ALICE_COLOR and ALICE_DERIVATION_COLOR");
		BaseMod.addColor(
				ALICE_MAGTROID_COLOR,
				PUPPETEER,
				PUPPETEER,
				PUPPETEER,
				PUPPETEER,
				PUPPETEER,
				PUPPETEER,
				PUPPETEER,
				ATTACK_CC,
				SKILL_CC,
				POWER_CC,
				ENERGY_ORB_CC,
				ATTACK_CC_PORTRAIT,
				SKILL_CC_PORTRAIT,
				POWER_CC_PORTRAIT,
				ENERGY_ORB_CC_PORTRAIT,
				CARD_ENERGY_ORB
		);
		BaseMod.addColor(
				ALICE_MAGTROID_DERIVATION_COLOR,
				PUPPETEER,
				PUPPETEER,
				PUPPETEER,
				PUPPETEER,
				PUPPETEER,
				PUPPETEER,
				PUPPETEER,
				ATTACK_CC,
				SKILL_CC,
				POWER_CC,
				ENERGY_ORB_CC,
				ATTACK_CC_PORTRAIT,
				SKILL_CC_PORTRAIT,
				POWER_CC_PORTRAIT,
				ENERGY_ORB_CC_PORTRAIT,
				CARD_ENERGY_ORB
		);
	}

//	public static AbstractCard upgraded(AbstractCard card, boolean flag) {
//		if (flag)
//			card.upgrade();
//		return card;
//	}
//
//	public static AbstractReiujiCard upgraded(AbstractReiujiCard card, boolean flag) {
//		if (flag)
//			card.upgrade();
//		return card;
//	}
	
	public void receiveEditCharacters() {
		logger.info("begin editing characters");
		
		logger.info("add " + ALICE_MAGTROID.toString());
		BaseMod.addCharacter(
				new AliceMagtroid("Alice Magtroid"),
				MY_CHARACTER_BUTTON,
				ALICE_PORTRAIT,
				ALICE_MAGTROID
		);
		logger.info("done editing characters");
	}
	
	public void receiveEditRelics() {
		logger.info("Begin editing relics.");
		BaseMod.addRelicToCustomPool(
				new EyeOfYatagarasu(),
				ALICE_MAGTROID_COLOR
		);
		
		logger.info("Relics editing finished.");
	}
	
	public void receiveEditCards() {
		this.loadVariables();
		
		logger.info("starting editing cards");
		
		loadCardsToAdd();
		
		logger.info("adding cards for ALICE_MAGTROID");
		
		for (AbstractCard card : cardsToAdd) {
			logger.info("Adding card : " + card.name);
			BaseMod.addCard(card);
			
			UnlockTracker.unlockCard(card.cardID);
		}
		
		logger.info("done editing cards");
	}
	
	// 必须有这个函数才能初始化
	public static void initialize() {
		new AliceMagtroidMod();
	}
	
	public static String getLangShort() {
		if (Settings.language == Settings.GameLanguage.ZHS ||
				Settings.language == Settings.GameLanguage.ZHT) {
			return "zhs";
		} else {
			return "eng";
		}
	}
	
	private static String getLocalizeFile(String lang, String name) {
		return "localization/" + lang + "/" + name + ".json";
	}
	
	private static String loadJson(String jsonPath) {
		return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
	}
	
	public void receiveEditKeywords() {
		logger.info("Setting up custom keywords");
		
		String keywordsPath = getLocalizeFile(getLangShort(), "keywords");
		
		Gson gson = new Gson();
		Keywords keywords;
		keywords = gson.fromJson(loadJson(keywordsPath), Keywords.class);
		for (Keyword key : keywords.keywords) {
			logger.info("Loading keyword : " + key.NAMES[0]);
			BaseMod.addKeyword(key.NAMES, key.DESCRIPTION);
		}
		logger.info("Keywords setting finished.");
	}
	
	@Override
	public void receiveEditStrings() {
		logger.info("start editing strings");
		
		String lang = getLangShort();
		
		String relicStrings = Gdx.files.internal(getLocalizeFile(lang, "relics"))
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
		
		String cardStrings = Gdx.files.internal(getLocalizeFile(lang, "cards"))
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

		String powerStrings = Gdx.files.internal(getLocalizeFile(lang, "powers"))
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

		String potionStrings = Gdx.files.internal(getLocalizeFile(lang, "potions"))
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);

		String eventStrings = Gdx.files.internal(getLocalizeFile(lang, "events"))
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
		
//		String dollStrings = Gdx.files.internal(getLocalizeFile(lang, "dolls"))
//				.readString(String.valueOf(StandardCharsets.UTF_8));
//		BaseMod.loadCustomStrings(DollStrings.class, dollStrings);
		DollStrings.loadDollStrings(Gdx.files.internal(getLocalizeFile(lang, "dolls"))
				.readString(String.valueOf(StandardCharsets.UTF_8)));

		logger.info("done editing strings");
	}
	
	public void receivePostExhaust(AbstractCard card) {
		// Auto-generated method stub
	}
	
	public void receivePostBattle(AbstractRoom room) {
		dollManager.clear();
		dollManager = null;
	}
	
	public void receiveOnBattleStart(AbstractRoom abstractRoom) {
		dollManager = new DollManager(AbstractDungeon.player);
	}
	
	public void receiveCardUsed(AbstractCard c) {
		// TODO: May need to add some code here
	}
	
	public void receivePostEnergyRecharge() {
		// Auto-generated method stub
	}
	
	public void receivePowersModified() {
		// Auto-generated method stub
		
	}
	
	public void receivePostDungeonInitialize() {
		// Auto-generated method stub
	}
	
	public void receivePostDraw(AbstractCard card) {
		// Auto-generated method stub
	}
	
	public void receivePostInitialize() {
		// Auto-generated method stub
	}

	public void receiveOnPlayerTurnStart() {
		AbstractDungeon.actionManager.addToTop(
				new DollsClearBlockOnPlayerTurnStartAction());
	}

	public void receivePostPlayerUpdate() {
		if (dollManager != null)
			dollManager.applyPowersForAllDolls();
	}
	
	public int receiveOnPlayerDamaged(int amount, DamageInfo damageInfo) {
		if (damageInfo.type == DamageInfo.DamageType.HP_LOSS || dollManager == null)
			return amount;
		
		if (!(damageInfo.owner instanceof AbstractMonster))
			return amount;
		
		int index = getMonsterIndex((AbstractMonster) damageInfo.owner);
		
		int new_amt = dollManager.calcDamageOnPlayer(amount, index);
		if (new_amt != amount) {
			AbstractDungeon.actionManager.addToTop(
					new DollsTakeDamageAction(dollManager.calcDamageOnDolls(amount, index)));
		}
		
		return new_amt;
	}
	
	public int receiveOnPlayerLoseBlock(int amount) {
		if (dollManager != null) {
			int preserved = dollManager.getMaxBlockPreserved();
			
			return Integer.min(amount - preserved, 0);
		}
		else
			return amount;
	}
	
	public void receiveRender(SpriteBatch sb) {
		if (dollManager != null)
			dollManager.render(sb);
	}

	private void loadCardsToAdd() {
		cardsToAdd.clear();
		
		cardsToAdd.add(new Defend_AliceMagtroid());
		cardsToAdd.add(new Strike_AliceMagtroid());
		
	}
	
	private void loadVariables() {
		BaseMod.addDynamicVariable(new HeatVariable());
		BaseMod.addDynamicVariable(new TempHPVariable());
	}

	static class Keywords {
		
		Keyword[] keywords;
	}

	public static void addActionsToTop(AbstractGameAction... actions) {
		ArrayList<AbstractGameAction> temp = new ArrayList<>();

		for (AbstractGameAction act : actions)
			temp.add(0, act);

		for (AbstractGameAction act : temp)
			AbstractDungeon.actionManager.addToTop(act);
	}
	
	public static DollManager dollManager;
	
	public static int getMonsterIndex(AbstractMonster m) {
		int index = 0;
		for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
			if (mon == m)
				break;
			
			if (!mon.isDeadOrEscaped())
				index++;
		}
		return index;
	}
}

/*
                                                                                                  .
                                                                                       ..:;,:::i:.
                                                                            ..:i:::,;:.    .ttt:..
                                                                 ..,i:::i,.. ,:.,;t;;
                                                    ...:;,:::i,:,i:::,fi,:i.  ;,t,ij:,
                                          ..:i:::,;::,,:::jt;i::, ijKWWWWWWWWWKKKEGLj,.    ..:;,:::;
                      .KKKKKKKKKKWWLG;;;:.::. ,ttt:.  :;ft, j::j::  ttEKKWWWWW#WWWKEDD:,;:..  .:ttt.
        . KKKKKWKKWWWWWWWWWWW###WEWGDji ;:.i:.   ;,:   j::   f:.j::;j;,,,,jDGLLfWWWWWWWKEGD
       .DKWWWWWWW##KK##W####WWWWWEGGEGG;ij:;,,. .:f,;   t:.,i:,:;jt:.:,:..:.tDDKWWWWWWWWWWK.
,:::DEEKKWWWWWWWWW####EDWWW#WWEEWEDLGLLi,;,;ti::,;:,:i,...   itti..... .:,;;::fjDDDKWWWWWWWW
.tt;,EKKKKKK.WWW##KWWWWWWWWWWKKKEEKEWffD;:f:.. GWWWWKKEDE. :::...... .... .. ,ifLEiDDGGE#WW ;EL
:i.LGGD:,fKKWWG  .EWKWWWKWWKWKKKKEGDEGGjf:..GWKWKKWKEGDf;;Ljfj... .....:,:,..,.,tLE,KKDEEEEK#Wi
t .,;:,tDDEL..,;:::iWWWWKKWWWWW#EEDGGDKGf: DWKEEKKK,fKDGjtj:if;:.   i::,:.:.;:tLitDGDGGEEWW#WWW .
..:fi;:fL:,t    tttWW#WWWWKWWKWWWEDDDGEijjGEKKEGjE;j:KE.,.,:iL;... .:,;,  ..;,;jtLfGGDEGGDDWWKWKK
    :ttt..       .WWWWK#WWW#WWWWWWWKEWtffffWKKK;;:::.::t.GL.jf : :;  ,iiif...fLGLDDDGGDDDLLGEKKEEt
                .WWWKGKE##WWWKWWWDWW#fDLGWGLWWWWWED:.ffj;tE,,Lfjtj,;iGjL. ::GLDDDfKDGDDDDEEKK;DD,
                #WWKKEKWWWWKKKWWWWWWWLGGGDGGLfG#EEDWfj,LKj:,,::;,ifDLL: .:WEEKKKKDEEEEEEEEKKKK
                W#WWEEGDLKEKKKKKWWWWWW#W#L#t:KKKKGWGi,..::::i;i,::::.:::,KKWWWEWKKKKKDEEKKKKKK.
         ..      ..t,WWKKGKKWWWWWWGDDEfWL,jttjDtiiit,ji;;tDj,,GijDKf,,KWKWKDWWEWWWWKKKKKEfKtEL
       . tjt   ;:   E#WWWEGKWKfLWjjfKKEKKKWtitDDi;it:;; WWWKDKEEWKKDGGWEKKDEEKEGEEKKKKKfitt,
      jjt  f  ;  :  WKWWWWWKKKK#DW#WGLLG#G;f;KKj;ijtii :DWLDDDKGGGEWWKKKKELGiEELKKWWWWWG;i ;;
     .tj.  ...EtKKKWKKKEK#WKKWWWWWWKKKGfGGDGWDt;,.tt;, EKDEKKWKEKGGWEDEEKEEDKKEKKDKKKWEWK,t.
    . i: ...DKEEEKWEWKWWWWKWWWWWWWDWKWWWEKEEEKKKEKKDLGL;DG;EKKKWEKWfKEEEKKKEEKKEDGLGEEDLj,E
      :j ...:jDDKEE .KEK#KKWWWWW#EGDKWEWDEEKKEKKEEEDGLG;,.;EKEKKKWKKKWKKKKKKKKKDKKEKKj:W#W
        i   ;..tj..,KKKWW##K#WWDLfjfGDE#EEKEKKKDDGGGGLf.,G:.tEKKKKWWWWWL;:jWWWWWKLtDWWWWE
            t,.      . EWWKKWWfjjjjjjjLWWKEKKEDDDDGGLG;..Gt,.jKWKEKKWKEW:jKWWGjWWWWWK.
         .   ttt;     EWEKEt,:;jjjjjWWWWWDEEDDDDDDGGG:...:G::::ii:;i;jfDLjEE.
            :j;i:.  jffjt;,:::::jKKKWWWWKDGGDDGGE##KKD;W:,Gt:,;:...LKKKKK,
            i:i;fjEKWWjjttt. ;KKDEKEEWWEWDjDKDDGWDLEL;iW#;G;;:.......:.
            LLiLjKKKKEE        tWW#WWWWWWWjjjjjjjfWW#W##KGfj,,::.....,Eft;:,
          LGKf,GKEG.it,           WWWDKKKjjtttttEEEEKWKWtji;;:::GL;iWKjiEGG                       ..
         EKEEEEDt..     ..               ,,,..,   iEEGKKKftiLKfti;.. .                ...:;t:::::t;:
          EEG    .i:      .,:,          ......                            ....,i;::::,i,::,;,,::.:,,
                    .j     .:,     .  .KEDL:.:  .,.             ..:,i:::::i;:..,:;:::::,Lttt .t:t.;,
                    i         ..:;,i. KEEEEEt .     ...:;t:::::t;::..,:;   ,ttt,,;    .,,jtt. ti:;;f
                                  .  EjGKKKf,i;::::,i,:..:;:::::ff;,j:::,      j,::    j,::    t,:,t
                              ..,;tjtitiGi:...;i::::,ifjtti:;.  .tj,:.j,::      j,::    j::.    j,;.
                  ...:;t:::::t;,.WKKKEL .,ittt,:;    .it  t,::::;i,f:;.j::. ..,it,:,.   ;t.:,i,::,:i
       ...,;i::::,i,:..  DKKWWWtLEDWE.: ...  .j,,:         j,:.  ittf:i,f::i;,:.:jf:::::tjj...
t:::::t;:.. .:;.;:,tf,i;   :EEKKWWWf....       f,:: ...:;i::j:;.  ..tji:,,:,ffjtt..     :tttti
i;:,::itttit:i:t.t:tiL:t.     EEEL             ;f:::i;,:.:;i,,:::t;:.i       ;tttt,
j:::t:it::i. ti:;ti:;t;:;.                 ...,it,:::ifjtt;       itttt.
tf::t;,j  ,,::f,::t,::tfi.      ..:,i:::::i;:...    ...ttiti
 if:tj,:.ttt .,,:,.j;;.jtt:::::t;:...      ;tttt;
t:,,:,t;:..:,ft:,::,i,::t       itttt.
:,i;,::,i;:...      .tttti
tt       ,tttt;
*/