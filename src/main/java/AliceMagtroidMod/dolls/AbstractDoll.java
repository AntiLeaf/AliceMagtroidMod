package AliceMagtroidMod.dolls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;

import java.util.ArrayList;

public abstract class AbstractDoll {
    public String name;
    public String description;
    public String ID;

    protected ArrayList<PowerTip> tips = new ArrayList<>(); // TODO: What is this?

    public int MAX_HP = 0; // may not be used
    public int HP = 0;
    public int block = 0;
    public int actAmount = 0;
    public int spawnAmount = 0;
    public int brokenAmount = 0; // may be used in the future

    public Hitbox hb;
    protected Texture img;
    protected float fontScale;

    public int shown_values[] = new int[2];
    protected float animTimer;

    public AbstractDoll() {
        // TODO
    }

    public abstract void updateDescription();

    public abstract void onSpawn();

    public abstract void onAct();

    public abstract void onBroken();

    public static AbstractDoll getRandomDoll(boolean useCardRng) {
        // TODO
        return null;
    }

    public void onStartOfTurn() {}

    public void onEndOfTurn() {}

    public void applyPowers(AbstractCreature owner) {}

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
}
