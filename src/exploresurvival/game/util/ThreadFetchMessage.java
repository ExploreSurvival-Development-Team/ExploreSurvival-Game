package exploresurvival.game.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import exploresurvival.game.ExploreSurvival;
import exploresurvival.game.gui.GuiMainMenu;

public class ThreadFetchMessage extends Thread {
	private static final URL newsURL;
	private static FileHandler fileHandler;
	private static Logger logger = Logger.getLogger(ExploreSurvival.class.getName());
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
		LocalDateTime dateTime = LocalDateTime.now(); // get the current time
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH.mm.ss");
		try {
			fileHandler = new FileHandler();
		} catch (IOException e) {
			logger.severe("Unable to write to log file");
			e.printStackTrace();
		}
		fileHandler.setFormatter(new LoggerCustomFormatter());
		logger.setLevel(Level.ALL);
		logger.addHandler(fileHandler);
		if(newsURL==null) return;
		BufferedReader in;
		try {
			logger.info("Load news in " + newsURL);
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
					logger.severe("Unable to load news in " + newsURL);
					guiMainMenu.messages.add("Failed to fetch news.");
					guiMainMenu.addLine("Failed to fetch news.");
				}
			}
			
		}
	}
}
