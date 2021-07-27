package exploresurvival.game.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import exploresurvival.game.ExploreSurvival;
import exploresurvival.game.util.ScaledResolution;

public abstract class Component {
	ExploreSurvival game;
	int width,height;
	public Component(ExploreSurvival game) {
		this.game=game;
		ScaledResolution sr=new ScaledResolution();
		width=sr.getScaledWidth();
		height=sr.getScaledHeight();
	}
	public abstract void render(int mouseX, int mouseY);
	public abstract void onClose();
	public abstract void onMouseClick(int var1, int var2, int eventButton, GuiScreen screen);
	public abstract void onKeyPress(char eventCharacter, int eventKey, GuiScreen screen);
	public abstract void tick();
	public void keyboardEvent(GuiScreen screen) {
		if(Keyboard.getEventKeyState()) {
	         this.onKeyPress(Keyboard.getEventCharacter(), Keyboard.getEventKey(), screen);
	      }
	}
	public void mouseEvent(GuiScreen screen) {
		if(Mouse.getEventButtonState()) {
	    	 ScaledResolution sr = new ScaledResolution();
	         int var1 = Mouse.getEventX() / sr.scaleFactor;
	         int var2 = sr.getScaledHeight() - (Mouse.getEventY() / sr.scaleFactor);
	         this.onMouseClick(var1, var2, Mouse.getEventButton(), screen);
	      }
	}
}
