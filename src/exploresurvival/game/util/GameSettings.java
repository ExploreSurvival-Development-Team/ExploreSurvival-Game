package exploresurvival.game.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exploresurvival.game.ExploreSurvival;
import exploresurvival.game.gui.GuiScreen;

import static exploresurvival.game.ExploreSurvival.SETTINGFILE;

public class GameSettings {
	public int guiScale=0;
	public boolean limitFrames=true;
	public int keyForward=Keyboard.KEY_W;
	public int keyBackward=Keyboard.KEY_S;
	public int keyLeft=Keyboard.KEY_A;
	public int keyRight=Keyboard.KEY_D;
	public static final int settingcount=2;
	public void saveSettings(File f) throws Exception {
		Gson gson=new GsonBuilder().setPrettyPrinting().create();
		FileWriter fw = null;
		try {
			fw=new FileWriter(f);
			gson.toJson(this, fw);
			fw.close();
		} catch(Exception e) {
			if(fw!=null) fw.close();
			throw e;
		}
	}
	public void saveSettings() throws Exception {
		saveSettings(SETTINGFILE);
	}
	public static GameSettings loadSettings(File f) throws Exception {
		Gson gson=new Gson();
		FileReader fr = null;
		try {
			fr=new FileReader(f);
			GameSettings settings=gson.fromJson(fr, GameSettings.class);
			fr.close();
			return settings;
		} catch(Exception e) {
			if(fr!=null) fr.close();
			throw e;
		}
	}
	public static GameSettings loadSettings() throws Exception {
		return loadSettings(SETTINGFILE);
	}
	public String getSettingName(int id) {
		switch(id) {
		case 0:
			return "Gui scale: "+(guiScale==0?"Auto":guiScale+"x");
		case 1:
			return "Limit frames: "+limitFrames;
		default:
			return "";
		}
	}
	public void toggleSetting(int id) {
		switch(id) {
		case 0:
			if(guiScale>=8) {
				guiScale=0;
				break;
			}
			if(guiScale==0) {
				guiScale=1;
				break;
			}
			guiScale*=2;
			break;
		case 1:
			limitFrames=!limitFrames;
			Display.setVSyncEnabled(limitFrames);
			break;
		}
		if(id==0&&ExploreSurvival.getInstance().currentScreen!=null) {
			GuiScreen screen=ExploreSurvival.getInstance().currentScreen;
			screen.controls.clear();
			ScaledResolution sr=new ScaledResolution();
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
			GL11.glClear(256);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0D, sr.field_25121_a, sr.field_25120_b, 0.0D, 100.0D, 300.0D);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0F, 0.0F, -200.0F);
			screen.onOpen(sr.getScaledWidth(), sr.getScaledHeight());
		}
		try {
			saveSettings();
		} catch (Exception e) {
			ExploreSurvival.logger.severe("Unable to save Settings in " + SETTINGFILE);
			ExploreSurvival.logger.fine("Cause : " + e.toString());
			ExploreSurvival.logger.fine("---- Begin Warning");
			ExploreSurvival.logger.fine("Your Settings profile cannot be modified.");
			ExploreSurvival.logger.fine("Your Settings will be restored when the game is closed.");
			ExploreSurvival.logger.fine("---- End Warning");
			e.printStackTrace();
		}
	}
}
