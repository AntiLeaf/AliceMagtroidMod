package AliceMagtroidMod.doll.dolls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;

import java.util.ArrayList;

public abstract class AbstractDoll {
    public String name;
    public String description;
    public String ID;

    protected ArrayList<PowerTip> tips = new ArrayList<>(); // TODO: What is this?

    public int maxHP = 0; // may not be used
    public int HP = 0;
    public int block = 0;
    
    public int spawnAmount = 0;
    public int baseSpawnAmount = 0;
    
    public int actAmount = 0;
    public int baseActAmount = 0;
    
    public boolean actAtStartOfTurn = false;
    public boolean actAtEndOfTurn = false;
    public boolean actWhenCommanded = false;
    
    public int brokenAmount = 0; // may be used in the future
    public int baseBrokenAmount = 0;
    
    public int extraActCount = 0;

    public Hitbox hb;
    protected Texture img;
    protected float fontScale;

    public int[] shown_values = new int[2];
    protected float animTimer;

    public AbstractDoll() {
        // TODO
    }

    public abstract void updateDescription();

    public static AbstractDoll getRandomDoll(boolean useCardRng) {
        // TODO
        return null;
    }
    
    
    public void onSpawn() {}
    public void atStartOfTurn() {}

    public void atEndOfTurn() {}
    
    public void triggerWhenCommanded() {}
    
    public void onBroken() {}
    
    public void onRecycled() {}
    
    public void onBrokenOrRecycled() {}
    
    public int calcRemainingDamage(int damage) {
        // TODO
        return Integer.max(0, damage - this.HP - this.block);
    }
    
    public void onDamageReceived(int damage) {
        // TODO
        // damage may be greater than HP + block
    }

    public abstract void applyPowers();

    public abstract AbstractDoll makeCopy();

    public void updateAnimation() {
        // TODO
    }

    public void setSlot(int row, int col, int max_row) {
        // TODO
    }

    public abstract void render(SpriteBatch sb);

    protected void renderText(SpriteBatch sb) {
        // TODO
    }

    public void renderHealthBar(SpriteBatch sb) {
        // TODO
    }

    public void triggerAnimation() {

    }

    public abstract void playSFX();

    static {
        // TODO
    }
    
    void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
}
