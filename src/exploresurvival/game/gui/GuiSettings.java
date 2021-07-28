package exploresurvival.game.gui;

import exploresurvival.game.ExploreSurvival;
import exploresurvival.game.util.GameSettings;

public class GuiSettings extends GuiScreen {
	GuiScreen parent;
	public GuiSettings(ExploreSurvival game, GuiScreen parent) {
		super(game);
		// TODO Auto-generated constructor stub
		this.parent=parent;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		GameSettings settings=game.gamesettings;
		for (int i = 0; i < GameSettings.settingcount; ++i) {
            this.controls.add(new SettingButton(game, width / 2 - 155 + i % 2 * 160, height / 6 + 24 * (i >> 1), settings, i));
        }
		this.controls.add(new Button(game, 1, this.width / 2 - 100, this.height / 6 + 168, "Done"));
	}

	@Override
	public void onButtonClick(Button button) {
		if(button.id==1) {
			game.setCurrentScreen(parent);
		}
	}
	@Override
	public void render(int mouseX, int mouseY) {
		super.render(mouseX, mouseY);
		this.drawCenteredString(game.fontRenderer, "Settings", width/2, 20, 0xffffff);
	}

}
