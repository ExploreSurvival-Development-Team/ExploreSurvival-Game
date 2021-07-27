package exploresurvival.game.gui;

import org.lwjgl.opengl.GL11;

import exploresurvival.game.ExploreSurvival;
import exploresurvival.game.render.FontRenderer;
import exploresurvival.game.render.RenderEngine;

public class Button extends Component {
	// w: 按钮宽度 h:按钮高度
	int x,y,w,h,id;
	public boolean enable,visible;
	String text;
	public Button(ExploreSurvival game, int id, int x, int y, String text) {
		this(game,id,x,y,200,20, text);
	}

	public Button(ExploreSurvival game, int id, int x2, int y2, int i, int j, String text) {
		super(game);
		this.id=id;
		this.x=x2;
		this.y=y2;
		this.w=i;
		this.h=j;
		enable=true;
		visible=true;
		this.text=text;
	}
	

	@Override
	public void render(int mouseX, int mouseY) {
		if(visible) {
			FontRenderer font=game.fontRenderer;
			RenderEngine engine=game.renderEngine;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, engine.getTexture("/gui.png"));
			boolean flag = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.w && mouseY < this.y + this.h)&&enable;
			if(flag) {
				GL11.glColor4f(.75F, .75F, 1F, 1F);
			}
			if(!enable) {
				engine.drawImage(x, y, 0, 46, w / 2, h);
				engine.drawImage(x+w / 2, y, 200 - this.w / 2, 46, w / 2, h);
				GuiScreen.drawCenteredString(font, text, x+w/2, y+(h/2-4), 0xa0a0a0);
				return;
			}
			engine.drawImage(x, y, 0, flag? 86 : 66, w / 2, h);
			engine.drawImage(x+w / 2, y, 200 - this.w / 2, flag? 86 : 66, w / 2, h);
			GuiScreen.drawCenteredString(font, text, x+w/2, y+(h/2-4), flag ? 0xffffa0 : 0xe0e0e0);
			GL11.glColor4f(1F, 1F, 1F, 1F);
		}
	}

	@Override
	public void onClose() {
		
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int eventButton, GuiScreen screen) {
		if(eventButton==0) {
			boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.w && mouseY < this.y + this.h;
			if(flag) screen.componentEvent(this);
		}
	}

	@Override
	public void onKeyPress(char eventCharacter, int eventKey, GuiScreen screen) {
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
