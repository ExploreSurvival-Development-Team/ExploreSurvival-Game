package exploresurvival.game.gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import exploresurvival.game.ExploreSurvival;
import exploresurvival.game.render.FontRenderer;
import exploresurvival.game.render.ShapeRenderer;
import exploresurvival.game.util.ScaledResolution;

public abstract class GuiScreen {
	ExploreSurvival game;
	public ArrayList<Component> controls;
	int width, height;

	public GuiScreen(ExploreSurvival game) {
		this.game = game;
		controls = new ArrayList<Component>();
	}

	public abstract void init();
	public abstract void onButtonClick(Button button);
	public void componentEvent(Component c) {
		if(c instanceof Button) {
			onButtonClick((Button)c);
		}
	}

	public void onOpen(int width, int height) {
		this.width = width;
		this.height = height;
		init();
	}

	public void onClose() {
		for (int i=0;i<controls.size();i++) {
			controls.get(i).onClose();
		}
		controls.clear();
	}
	public void onKeyPress(char eventCharacter, int eventKey) {

	}
	public void onMouseClick(int var1, int var2, int eventButton) {

	}

	public void render(int mouseX, int mouseY) {
		renderBG();
		for (int i=0;i<controls.size();i++) {
			controls.get(i).render(mouseX, mouseY);
		}
		while (Mouse.next()) {
			this.mouseEvent();
		}
		while (Keyboard.next()) {
			this.keyboardEvent();
		}
	}

	public void tick(float passedTime) {
		for (int i=0;i<controls.size();i++) {
			controls.get(i).tick();
		}
	}

	public void keyboardEvent() {
		if (Keyboard.getEventKeyState()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_F11) {
				game.toggleFullscreen();
				return;
			}
			this.onKeyPress(Keyboard.getEventCharacter(), Keyboard.getEventKey());
		}
		for (int i=0;i<controls.size();i++) {
			controls.get(i).keyboardEvent(this);
		}
	}
	public void mouseEvent() {
		if (Mouse.getEventButtonState()) {
			ScaledResolution sr = new ScaledResolution();
			int var1 = Mouse.getEventX() / sr.scaleFactor;
			int var2 = sr.getScaledHeight() - (Mouse.getEventY() / sr.scaleFactor);
			this.onMouseClick(var1, var2, Mouse.getEventButton());
		}
		for (int i=0;i<controls.size();i++) {
			controls.get(i).mouseEvent(this);
		}
	}
	private void renderBG() {
		  ScaledResolution sr=new ScaledResolution();
	      int var3 = sr.getScaledWidth();
	      int var4 = sr.getScaledHeight();
	      GL11.glClear(16640);
	      ShapeRenderer var5 = ShapeRenderer.instance;
	      int var6 = game.renderEngine.getTexture("/background.png");
	      GL11.glBindTexture(3553, var6);
	      GL11.glColor4f(1, 1, 1, 1);
	      float var7 = 32.0F;
	      var5.begin();
	      var5.color(4210752);
	      var5.vertexUV(0.0F, (float)var4, 0.0F, 0.0F, (float)var4 / 32.0F);
	      var5.vertexUV((float)var3, (float)var4, 0.0F, (float)var3 / 32.0F, (float)var4 / 32.0F);
	      var5.vertexUV((float)var3, 0.0F, 0.0F, (float)var3 / 32.0F, 0.0F);
	      var5.vertexUV(0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
	      var5.end();
	   }
	public static void drawCenteredString(final FontRenderer var0, final String var1, final int var2, final int var3, final int var4) {
        var0.render(var1, var2 - var0.getWidth(var1) / 2, var3, var4);
    }
    public static void drawRect(int par0, int par1, int par2, int par3, int par4)
    {
        int var5;

        if (par0 < par2)
        {
            var5 = par0;
            par0 = par2;
            par2 = var5;
        }

        if (par1 < par3)
        {
            var5 = par1;
            par1 = par3;
            par3 = var5;
        }

        float var10 = (float)(par4 >> 24 & 255) / 255.0F;
        float var6 = (float)(par4 >> 16 & 255) / 255.0F;
        float var7 = (float)(par4 >> 8 & 255) / 255.0F;
        float var8 = (float)(par4 & 255) / 255.0F;
        ShapeRenderer var9 = ShapeRenderer.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(var6, var7, var8, var10);
        var9.begin();
        var9.vertex((float)par0, (float)par3, 0.0f);
        var9.vertex((float)par2, (float)par3, 0.0f);
        var9.vertex((float)par2, (float)par1, 0.0f);
        var9.vertex((float)par0, (float)par1, 0.0f);
        var9.end();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
