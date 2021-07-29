package exploresurvival.game.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import exploresurvival.game.ExploreSurvival;
import exploresurvival.game.gui.GuiMainMenu;

import static exploresurvival.game.ExploreSurvival.SETTINGFILE;

public class ThreadFetchMessage extends Thread {
	private static final URL newsURL;
	GuiMainMenu guiMainMenu;
	static {
		URL url;
		try {
			url=new URL("https://www.exploresurvival.ml/news.txt");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			url=null;
		}
		newsURL=url;
	}
	public ThreadFetchMessage(GuiMainMenu guiMainMenu) {
		this.guiMainMenu=guiMainMenu;
	}
	public void run() {
		if(newsURL==null) return;
		BufferedReader in;
		try {
			ExploreSurvival.logger.info("Load news in " + newsURL);
			URLConnection conn = newsURL.openConnection();
			conn.setDoInput(true);
			conn.connect();
			in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
			String line;
			synchronized(guiMainMenu.msg) {
				guiMainMenu.msg.clear();
			}
			guiMainMenu.messages.clear();
			while((line=in.readLine())!=null) {
				guiMainMenu.messages.add(line);
				synchronized(guiMainMenu.msg) {
					guiMainMenu.addLine(line);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			synchronized(guiMainMenu.msg) {
				if(guiMainMenu.msg.size()==0||!guiMainMenu.msg.get(guiMainMenu.msg.size()-1).equals("Failed to fetch news.")) {
					ExploreSurvival.logger.severe("Unable to load news in " + newsURL);
					ExploreSurvival.logger.fine("Cause : " + e.toString());
					ExploreSurvival.logger.fine("---- Begin Warning");
					ExploreSurvival.logger.fine("You can't get ExploreSurvival news.");
					ExploreSurvival.logger.fine("That means you can't get the latest news and updates on ExploreSurvival.");
					ExploreSurvival.logger.fine("If you don't have an Internet connection, ignore this warning");
					ExploreSurvival.logger.fine("---- End Warning");
					guiMainMenu.messages.add("Failed to fetch news.");
					guiMainMenu.addLine("Failed to fetch news.");
				}
			}
			
		}
	}
}
