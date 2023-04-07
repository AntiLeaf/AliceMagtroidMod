package AliceMagtroidMod.doll.dolls;

import AliceMagtroidMod.AliceMagtroidMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.random.Random;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractDoll {
	public static final float WIDTH = 96.0F * Settings.scale;
	public static final float HEIGHT = 96.0F * Settings.scale;
	public static final float NUM_X_OFFSET = 20.0F * Settings.scale;
	public static final float NUM_Y_OFFSET = -12.0F * Settings.scale;
	protected static final float TIP_OFFSET_R_X = 20.0F * Settings.scale;
	protected static final float TIP_OFFSET_L_X = -380.0F * Settings.scale;
	protected static final float TIP_OFFSET_Y = 80.0F * Settings.scale;
	
	public static final float SPAWN_ANIM_TIME = 0.2F;
	public static final float MOVE_ANIM_TIME = 0.5F;
	public static final float ACT_ANIM_TIME = 0.4F;
	public static final float HIT_ANIM_TIME = 0.2F;
	public static final float BROKEN_ANIM_TIME = 0.3F;
	public static final float RECYCLE_ANIM_TIME = 0.5F;
	
	public String ID;
	public String name;
	public String rawDescription, description;
	
	protected ArrayList<PowerTip> tips = new ArrayList<>();
	
	public int maxHP = 0; // may not be used
	public int HP = 0;
	public int block = 0;
	
	public int passiveAmount = 0;
	public int basePassiveAmount = 0;
	
	public int spawnAmount = 0;
	public int baseSpawnAmount = 0;
	
	public int actAmount = 0;
	public int baseActAmount = 0;
	
	public boolean actAtStartOfTurn = false;
	public boolean actAtEndOfTurn = false;
//	public boolean actWhenCommanded = false;
	
	public int brokenAmount = 0; // may be used in the future
	public int baseBrokenAmount = 0;
	
	public Hitbox hb;
	protected Texture img;
	public float cX, cY, tX, tY;
	public float scale;
	protected float fontScale;
	
	protected Color color, shineColor;
	
	public int shownValue = -1;
	public boolean goldenValue = false;
	protected float spawnAnimTimer, moveAnimTimer, actAnimTimer, hitAnimTimer, brokenAnimTimer, recycleAnimTimer;
	// only one of the above timers can be non-zero at a time
	
	public int[] slot, source, dest;
	
	public AbstractDoll(String ID, String IMG_PATH, String name, String rawDescription) {
		this.ID = ID;
		this.img = ImageMaster.loadImage(IMG_PATH);
		this.name = name;
		this.rawDescription = rawDescription;
		this.updateDescription();
		
		this.color = Settings.CREAM_COLOR.cpy();
		this.shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
		
		this.hb = new Hitbox(WIDTH, HEIGHT);
		this.fontScale = 0.7F;
		
		this.spawnAnimTimer = 0.0F;
		this.moveAnimTimer = 0.0F;
		this.actAnimTimer = 0.0F;
		this.hitAnimTimer = 0.0F;
		this.brokenAnimTimer = 0.0F;
		this.recycleAnimTimer = 0.0F;
		
		this.cX = 0.0F;
		this.cY = 0.0F;
		
		slot = new int[]{-1, -1};
		source = new int[]{-1, -1};
		dest = new int[]{-1, -1};
	}
	
	protected String parse(String s, int... args) {
		int[] argList = Arrays.stream(args).toArray();
		
		StringBuilder sb = new StringBuilder();
		int argIndex = 0;
		
		for (String token : s.split(" ")) {
			if (token.startsWith("#")) {
				String color = token.substring(0, token.indexOf("!"));
				token = token.substring(token.indexOf("!"));
				sb.append(color);
			}
			
			if (token.startsWith("!") && token.endsWith("!")) {
				String key = token.substring(1, token.length() - 1).toLowerCase();
				
				switch (key) {
					case "p":
					case "passive":
						sb.append(this.passiveAmount);
						break;
					case "s":
					case "spawn":
						sb.append(this.spawnAmount);
						break;
					case "a":
					case "act":
						sb.append(this.actAmount);
						break;
					case "b":
					case "broken":
						sb.append(this.brokenAmount);
						break;
					default:
						sb.append(token);
						break;
				}
			}
			else if (token.startsWith("{") && token.endsWith("}")) {
				String tmp = "[MISSING_ARGUMENT]";
				if (argIndex < argList.length) {
					tmp = String.valueOf(argList[argIndex]);
					argIndex++;
				}
				sb.append(tmp);
			}
			else {
				sb.append(token);
			}
			
			sb.append(" ");
		}
		
		return sb.toString().trim();
	}
	
	public abstract void updateDescription();
	
	public abstract void act(ActTiming timing);
	
	public void onSpawn() {
		this.applyPowers();
	}
	
	public void onBroken() {}
	
	public void onRecycled() {}
	
	public void onBrokenOrRecycled() {}
	
	public int calcRemainingDamage(int damage) {
		return Integer.max(0, damage - this.HP - this.block);
	}
	
	public boolean takeDamage(int damage) {
		// damage may be greater than HP + block
		// returns true if the doll is broken after taking damage
		int blocked = Integer.min(damage, this.block);
		this.block -= blocked;
		damage -= blocked;
		
		this.HP = Integer.max(0, this.HP - damage);
		return this.HP == 0;
	}
	
	public abstract void applyPowers();
	
	public abstract AbstractDoll makeCopy();
	
//	public void setSlot(int row, int col) {
//
//	}
	
	public void render(SpriteBatch sb) {
		sb.setColor(this.color);
		sb.draw(this.img,
				this.cX - WIDTH / 2,
				this.cY - HEIGHT / 2,
				WIDTH,
				HEIGHT
		);
		
		
		this.renderText(sb);
		this.hb.render(sb);
		this.renderTip(sb);
		this.renderHealthBar(sb);
	}
	
	protected void renderText(SpriteBatch sb) {
		if (this.shownValue != -1) {
			Color c = this.goldenValue ? Settings.GOLD_COLOR : Settings.CREAM_COLOR;
			FontHelper.renderFontCentered(
					sb,
					FontHelper.topPanelInfoFont,
					Integer.toString(this.shownValue),
					this.cX + NUM_X_OFFSET,
					this.cY + HEIGHT / 2 + NUM_Y_OFFSET,
					c
			);
		}
	}
	
	protected void renderTip(SpriteBatch sb) {
		if (this.hb.hovered) {
			this.tips.clear();
			this.tips.add(new PowerTip(this.name, this.description));
			
			TipHelper.queuePowerTips(
					this.hb.cX + this.hb.width / 2 + TIP_OFFSET_R_X,
					this.hb.cY + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY),
					this.tips
			);
		}
	}
	
	public void renderHealthBar(SpriteBatch sb) {
		// TODO
	}
	
	public abstract void playSFX();
	
	public boolean shouldCancelAct(AbstractGameAction action) {
		return this.HP <= 0 || AbstractDungeon.getMonsters().areMonstersBasicallyDead();
	}
	
	public void update() {
		this.hb.update();
		if (this.hb.hovered) {
			TipHelper.renderGenericTip(
					this.tX + WIDTH,
					this.tY + HEIGHT * (2.0F / 3),
					this.name,
					this.description
			);
		}
		
		this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7F);
	}
	
	public void updateAnimation() {
		if (this.spawnAnimTimer > 0.0F) {
			this.spawnAnimTimer -= Gdx.graphics.getDeltaTime();
			if (this.spawnAnimTimer < 0.0F)
				this.spawnAnimTimer = 0.0F;
			
			this.scale = Interpolation.swingIn.apply(0.0F, 1.0F,
					this.spawnAnimTimer / SPAWN_ANIM_TIME);
		}
		else if (this.moveAnimTimer > 0.0F) {
			this.moveAnimTimer -= Gdx.graphics.getDeltaTime();
			if (this.moveAnimTimer < 0.0F)
				this.moveAnimTimer = 0.0F;
			
			float[] src = AliceMagtroidMod.dollManager.calcCenterPosition(source[0], source[1]);
			float[] dst = AliceMagtroidMod.dollManager.calcCenterPosition(dest[0], dest[1]);
			
			this.cX = Interpolation.smooth.apply(src[0], dst[0],
					this.moveAnimTimer / MOVE_ANIM_TIME);
			this.cY = Interpolation.smooth.apply(src[1], dst[1],
					this.moveAnimTimer / MOVE_ANIM_TIME);
		}
		else if (this.actAnimTimer > 0.0F) {
			this.actAnimTimer -= Gdx.graphics.getDeltaTime();
			if (this.actAnimTimer < 0.0F)
				this.actAnimTimer = 0.0F;
			
			// TODO: 思考一下这里应该是什么动画
		}
		else if (this.hitAnimTimer > 0.0F) {
			this.hitAnimTimer -= Gdx.graphics.getDeltaTime();
			if (this.hitAnimTimer < 0.0F)
				this.hitAnimTimer = 0.0F;
			
			// TODO: 思考一下这里应该是什么动画
		}
		else if (this.brokenAnimTimer > 0.0F) {
			this.brokenAnimTimer -= Gdx.graphics.getDeltaTime();
			if (this.brokenAnimTimer < 0.0F)
				this.brokenAnimTimer = 0.0F;
			
			this.color.a = Interpolation.fade.apply(1.0F, 0.0F,
					this.brokenAnimTimer / BROKEN_ANIM_TIME);
		}
		else if (this.recycleAnimTimer > 0.0F) {
			this.recycleAnimTimer -= Gdx.graphics.getDeltaTime();
			if (this.recycleAnimTimer < 0.0F)
				this.recycleAnimTimer = 0.0F;
			
			this.scale = Interpolation.swingIn.apply(1.0F, 0.01F,
					this.recycleAnimTimer / RECYCLE_ANIM_TIME);
		}
		else {
			this.scale = 1.0F;
			this.color.a = 1.0F;
		}
	}
	
//	abstract public void updateWhileSpawn(SpawnDollAction action, float time);
//
//	abstract public void updateWhileAct(DollActAction action, float time);
//
//	abstract public void updateWhileBroken(AbstractGameAction action, float time);
//
//	abstract public void updateWhileRecycled(AbstractGameAction action, float time);
//
//	public void updateWhileMoving(AbstractGameAction action, float time) {
//	}
	
	public int getRow() {
		return this.slot[0];
	}
	
	public int getCol() {
		return this.slot[1];
	}
	
	public int[] getRowAndCol() {
		return this.slot;
	}
	
	public void setSource(int[] rowAndCol) {
		source[0] = rowAndCol[0];
		source[1] = rowAndCol[1];
	}
	
	public void setSource(int row, int col) {
		source[0] = row;
		source[1] = col;
	}
	
	public void setDest(int[] rowAndCol) {
		dest[0] = rowAndCol[0];
		dest[1] = rowAndCol[1];
	}
	
	public void setDest(int row, int col) {
		dest[0] = row;
		dest[1] = col;
	}
	
	public void resetSourceAndDest() {
		source[0] = -1;
		source[1] = -1;
		dest[0] = -1;
		dest[1] = -1;
		
		if (this.moveAnimTimer > 0.0F) {
			this.moveAnimTimer = 0.0F;
		}
	}
	
	public void setSlot(int row, int col) {
		this.slot[0] = row;
		this.slot[1] = col;
	}
	
	public void setSlot(int[] rowAndCol) {
		this.slot[0] = rowAndCol[0];
		this.slot[1] = rowAndCol[1];
	}
	
	public void beginToSpawn() {
		this.spawnAnimTimer = SPAWN_ANIM_TIME;
	}
	
	public void beginToMove() {
		this.moveAnimTimer = MOVE_ANIM_TIME;
	}
	
	public void beginToAct() {
		this.actAnimTimer = ACT_ANIM_TIME;
	}
	
	public void beginToBeHit() {
		this.hitAnimTimer = HIT_ANIM_TIME;
	}
	
	public void beginToBeBroken() {
		this.brokenAnimTimer = BROKEN_ANIM_TIME;
	}
	
	public void beginToBeRecycled() {
		this.recycleAnimTimer = RECYCLE_ANIM_TIME;
	}
	
	void addToBot(AbstractGameAction action) {
		AbstractDungeon.actionManager.addToBottom(action);
	}
	
	void addToTop(AbstractGameAction action) {
		AbstractDungeon.actionManager.addToTop(action);
	}
	
	public enum Position {
		RIGHTMOST,
		LEFTMOST,
		MANUAL,
		UNSPECIFIED
	}

	static public class MoveDestination {
		public Position position;
		public int row;
		public int col;

		public MoveDestination(Position position, int row, int col) {
			this.position = position;
			this.row = row;
			this.col = col;
		}
	}
	
	public enum ActTiming {
		SPECIAL,
		START_OF_TURN,
		END_OF_TURN,
		COMMANDED,
		HOURAI
	}
	
	public static Class<?>[] dollClasses = {
			ShanghaiDoll.class,
			HouraiDoll.class,
			FranceDoll.class,
			OrleansDoll.class,
			NetherlandsDoll.class,
			LondonDoll.class,
			KyotoDoll.class
	};
	
	public static AbstractDoll getRandomDoll(Random rng) {
		Class<?> c = dollClasses[rng.random(dollClasses.length - 1)];
		try {
			return (AbstractDoll) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static AbstractDoll getRandomDoll() {
		return getRandomDoll(AbstractDungeon.cardRandomRng);
	}
	
	public static AbstractDoll getRandomDollExceptLondon(Random rng) {
		int k = rng.random(dollClasses.length - 2);
		if (dollClasses[k] == LondonDoll.class)
			k++;
		
		Class<?> c = dollClasses[k];
		try {
			return (AbstractDoll) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace(); // But how could this happen?
			return null;
		}
	}
	
	public static AbstractDoll getRandomDollExceptLondon() {
		return getRandomDollExceptLondon(AbstractDungeon.cardRandomRng);
	}
}
