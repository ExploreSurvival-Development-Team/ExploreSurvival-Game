package exploresurvival.game.gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import exploresurvival.game.ExploreSurvival;
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
		for (Component c : controls) {
			c.onClose();
		}
	}
	public void onKeyPress(char eventCharacter, int eventKey) {

	}
	public void onMouseClick(int var1, int var2, int eventButton) {

	}

	public void render(int mouseX, int mouseY) {
		renderBG();
		for (Component c : controls) {
			c.render(mouseX, mouseY);
		}
	}

	public void tick() {
		while (Mouse.next()) {
			this.mouseEvent();
		}
		while (Keyboard.next()) {
			this.keyboardEvent();
		}
		for(Component c:controls) {
			c.tick();
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
		for(Component c:controls) {
			c.keyboardEvent(this);
		}
	}
	public void mouseEvent() {
		if (Mouse.getEventButtonState()) {
			ScaledResolution sr = new ScaledResolution();
			int var1 = Mouse.getEventX() / sr.scaleFactor;
			int var2 = sr.getScaledHeight() - (Mouse.getEventY() / sr.scaleFactor);
			this.onMouseClick(var1, var2, Mouse.getEventButton());
		}
		for(Component c:controls) {
			c.mouseEvent(this);
		}
	}
	private void renderBG() {
		  ScaledResolution sr=new ScaledResolution();
	      int var3 = sr.getScaledWidth();
	      int var4 = sr.getScaledHeight();
	      GL11.glClear(16640);
	      ShapeRenderer var5 = ShapeRenderer.instance;
	      int var6 = game.renderengine.getTexture("/background.png");
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

}
