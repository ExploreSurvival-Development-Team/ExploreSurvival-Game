package exploresurvival.game.gui;

import exploresurvival.game.ExploreSurvival;

public class GuiScreenMainMenu extends GuiScreen {

	public GuiScreenMainMenu(ExploreSurvival game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		controls.add(new Button(game,width/2-100,40,"Test"));
	}

	@Override
	public void onButtonClick(Button button) {
		// TODO Auto-generated method stub

	}

}
