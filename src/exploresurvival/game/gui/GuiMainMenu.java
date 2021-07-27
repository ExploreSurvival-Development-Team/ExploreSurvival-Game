package exploresurvival.game.gui;

import exploresurvival.game.ExploreSurvival;
import exploresurvival.game.render.FontRenderer;
import exploresurvival.game.render.RenderEngine;
import exploresurvival.game.render.ShapeRenderer;

import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiMainMenu extends GuiScreen {

	public GuiMainMenu(ExploreSurvival game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		controls.add(new Button(game,0,10,100,"Play"));
		controls.add(new Button(game,1,10,125,"Settings"));
		controls.add(new Button(game,2,10,150,"Tutorial"));
		controls.add(new Button(game,3,10,175,"Exit Game"));
	}
	@Override
	public void render(int mouseX, int mouseY) {
		super.render(mouseX, mouseY);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, game.renderengine.getTexture("/logo.png"));
		ShapeRenderer s=ShapeRenderer.instance;
		s.begin();
		int posY=30;
		int posZ=27;
		s.vertexUV(posZ, posY, 0, 0, 0);
		s.vertexUV(posZ+331/2, posY, 0, 1, 0);
		s.vertexUV(posZ+331/2, posY+130/2, 0, 1, 1);
		s.vertexUV(posZ, posY+130/2, 0, 0, 1);
		s.end();
	}
	

	@Override
	public void onButtonClick(Button button) {
		// TODO Auto-generated method stub
		if(button.id==1) {
			game.setCurrentScreen(new GuiSettings(game,this));
		}
	}

}
