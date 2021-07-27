package exploresurvival.game.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import exploresurvival.game.gui.GuiMainMenu;

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
			URLConnection conn = newsURL.openConnection();
			conn.setDoInput(true);
			conn.connect();
			in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
			String line;
			guiMainMenu.messages.clear();
			guiMainMenu.msg.clear();
			while((line=in.readLine())!=null) {
				guiMainMenu.messages.add(line);
				guiMainMenu.addLine(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			guiMainMenu.messages.add("Faild to fetch news.");
			guiMainMenu.addLine("Faild to fetch news.");
		}
	}
}
