package AliceMod;

import AliceMod.abstracts.AbstractAliceCard;
import AliceMod.cards.Reiuji.*;
import AliceMod.characters.Reiuji;
import AliceMod.powers.InvisibleHasUsedSpellPower;
import AliceMod.relics.EyeOfYatagarasu;
import AliceMod.variable.HeatVariable;
import AliceMod.variable.TempHPVariable;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static AliceMod.patches.enums.AbstractCardEnum.REIUJI_COLOR;
import static AliceMod.patches.enums.AbstractCardEnum.REIUJI_DERIVATION_COLOR;
import static AliceMod.patches.ReiujiModClassEnum.REIUJI;

@SuppressWarnings("Duplicates")
@SpireInitializer
public class AliceMod implements PostExhaustSubscriber,
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
		OnStartBattleSubscriber {
	public static final String SIMPLE_NAME = AliceMod.class.getSimpleName();
	
	public static final Logger logger = LogManager.getLogger(AliceMod.class.getName());
	
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
	
	public static final Color CAUTION = CardHelper.getColor(0, 191, 255);
	public static final Color CAUTION_FLAVOR = CardHelper.getColor(204, 255, 255);
	public static final String CARD_ENERGY_ORB = "img/UI/energyOrb.png";
	
	private static final String MY_CHARACTER_BUTTON = "img/charSelect/ReiujiButton.png";
	private static final String REIUJI_PORTRAIT = "img/charSelect/ReiujiPortrait.jpg";
	
	private static final String CARD_STRING = "localization/ReiujiMod_cards.json";
	private static final String CARD_STRING_ZH = "localization/ReiujiMod_cards-zh.json";
	private static final String RELIC_STRING = "localization/ReiujiMod_relics.json";
	private static final String RELIC_STRING_ZH = "localization/ReiujiMod_relics-zh.json";
	private static final String POWER_STRING = "localization/ReiujiMod_powers.json";
	private static final String POWER_STRING_ZH = "localization/ReiujiMod_powers-zh.json";
	private static final String POTION_STRING = "localization/ReiujiMod_potions.json";
	private static final String POTION_STRING_ZH = "localization/ReiujiMod_potions-zh.json";
	private static final String KEYWORD_STRING = "localization/ReiujiMod_keywords.json";
	private static final String KEYWORD_STRING_ZH = "localization/ReiujiMod_keywords-zh.json";
	private static final String EVENT_PATH = "localization/ReiujiMod_events.json";
	private static final String EVENT_PATH_ZH = "localization/ReiujiMod_events-zh.json";
	
	private final ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
	//private ArrayList<AbstractRelic> relicsToAdd = new ArrayList<>();
	
	public AliceMod() {
		BaseMod.subscribe(this);
		logger.info("creating the color : REIUJI_COLOR and REIUJI_DERIVATION_COLOR");
		BaseMod.addColor(
				REIUJI_COLOR,
				CAUTION,
				CAUTION,
				CAUTION,
				CAUTION,
				CAUTION,
				CAUTION,
				CAUTION,
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
				REIUJI_DERIVATION_COLOR,
				CAUTION,
				CAUTION,
				CAUTION,
				CAUTION,
				CAUTION,
				CAUTION,
				CAUTION,
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
		
		logger.info("add " + REIUJI.toString());
		BaseMod.addCharacter(
				new Reiuji("Reiuji Utsuho"),
				MY_CHARACTER_BUTTON,
				REIUJI_PORTRAIT,
				REIUJI
		);
		logger.info("done editing characters");
	}
	
	public void receiveEditRelics() {
		logger.info("Begin editing relics.");
		BaseMod.addRelicToCustomPool(
				new EyeOfYatagarasu(),
				REIUJI_COLOR
		);
		
		logger.info("Relics editing finished.");
	}
	
	public void receiveEditCards() {
		this.loadVariables();
		
		logger.info("starting editing cards");
		
		loadCardsToAdd();
		
		logger.info("adding cards for REIUJI");
		
		for (AbstractCard card : cardsToAdd) {
			logger.info("Adding card : " + card.name);
			BaseMod.addCard(card);
			
			UnlockTracker.unlockCard(card.cardID);
		}
		
		logger.info("done editing cards");
	}
	
	// 必须有这个函数才能初始化
	public static void initialize() {
		new AliceMod();
	}
	
	public void receivePostExhaust(AbstractCard c) {
		// Auto-generated method stub
	}
	
	public void receivePostBattle(AbstractRoom r) {
	
	}
	
	public void receiveOnBattleStart(AbstractRoom abstractRoom) {

	}
	
	public void receiveCardUsed(AbstractCard c) {
		if (c instanceof AbstractAliceCard) {
			AbstractAliceCard card = (AbstractAliceCard) c;

			if (card.isSpellCard) {
				AbstractPlayer p = AbstractDungeon.player;

				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(p, p, new InvisibleHasUsedSpellPower(card)));
			}

//			if (card.isCombo)
//				ReiujiMod.comboCheck(card);
		}
	}
	
	public void receivePostEnergyRecharge() {
	
	}
	
	public void receivePowersModified() {
		// Auto-generated method stub
		
	}
	
	public void receivePostDungeonInitialize() {
		// Auto-generated method stub
	}
	
	public void receivePostDraw(AbstractCard arg0) {
		// Auto-generated method stub
	}
	
	public int receiveOnPlayerDamaged(int amount, DamageInfo damageInfo) {
		return amount; //
	}
	
	public int receiveOnPlayerLoseBlock(int amount) {
		return amount;
	}
	
	private static String loadJson(String jsonPath) {
		return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
	}
	
	public void receiveEditKeywords() {
		logger.info("Setting up custom keywords");
		
		String keywordsPath;
		switch (Settings.language) {
			case ZHT:
			case ZHS:
				keywordsPath = KEYWORD_STRING_ZH;
				break;
			default:
				keywordsPath = KEYWORD_STRING;
				break;
		}
		
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
		
		String relicStrings,
				cardStrings,
				powerStrings,
				potionStrings,
				eventStrings,
				relic,
				card,
				power,
				potion,
				event;
		
		if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
			logger.info("lang == zh");
			card = CARD_STRING_ZH;
			relic = RELIC_STRING_ZH;
			power = POWER_STRING_ZH;
			potion = POTION_STRING_ZH;
			event = EVENT_PATH_ZH;
		} else {
			logger.info("lang == eng");
			card = CARD_STRING;
			relic = RELIC_STRING;
			power = POWER_STRING;
			potion = POTION_STRING;
			event = EVENT_PATH;
		}
		
		relicStrings = Gdx.files.internal(relic).readString(
				String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);

		cardStrings = Gdx.files.internal(card).readString(
				String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

		powerStrings = Gdx.files.internal(power).readString(
				String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

		potionStrings = Gdx.files.internal(potion).readString(
				String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);

		eventStrings = Gdx.files.internal(event).readString(
				String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(EventStrings.class, eventStrings);

		logger.info("done editing strings");
	}
	
	public void receivePostInitialize() {
		// Nothing
	}
	
	private void loadCardsToAdd() {
		cardsToAdd.clear();
		
		cardsToAdd.add(new Defend_Alice());
		cardsToAdd.add(new Strike_Alice());
		
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
	
//	public static AbstractCard getRandomReiujiCard() {
//		return AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
//	}
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