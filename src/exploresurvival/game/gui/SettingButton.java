package exploresurvival.game.gui;

import exploresurvival.game.ExploreSurvival;
import exploresurvival.game.util.GameSettings;

public class SettingButton extends Button {
	GameSettings settings;
	int id;
	public SettingButton(ExploreSurvival game, int x2, int y2, GameSettings settings, int id) {
		super(game, 0, x2, y2, 150, 20, settings.getSettingName(id));
		this.settings=settings;
		this.id=id;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onMouseClick(int mouseX, int mouseY, int eventButton, GuiScreen screen) {
		if(eventButton==0) {
			boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.w && mouseY < this.y + this.h;
			if(flag) {
				settings.toggleSetting(id);
				text=settings.getSettingName(id);
			}
		}
	}
}
