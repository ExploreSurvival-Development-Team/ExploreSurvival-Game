package exploresurvival.game;

import java.io.File;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import exploresurvival.game.gui.GuiScreen;
import exploresurvival.game.gui.GuiMainMenu;
import exploresurvival.game.render.FontRenderer;
import exploresurvival.game.render.RenderEngine;
import exploresurvival.game.util.GameSettings;
import exploresurvival.game.util.LoggerCustomFormatter;
import exploresurvival.game.util.PanelCrashReport;
import exploresurvival.game.util.ScaledResolution;

public class ExploreSurvival extends Thread {

	private static FileHandler fileHandler;
	private static final Logger logger = Logger.getLogger(ExploreSurvival.class.getName());
	public static final String version="indev-210729_03";
	public ExploreSurvival() {
		running=true;
		instance=this;
	}

	public static ExploreSurvival getInstance() {
		return instance;
	}
    private void checkGlError(final String string) {
        final int errorCode = GL11.glGetError();
        if (errorCode != 0) {
            final String errorString = GLU.gluErrorString(errorCode);
            System.out.println("########## GL ERROR ##########");
            System.out.println("@ " + string);
            System.out.println(errorCode + ": " + errorString);
        }
    }
    private static ExploreSurvival instance;
	public RenderEngine renderEngine;
	public FontRenderer fontRenderer;
	public GameSettings gamesettings;
	private boolean fullscreen;
	public static final File SETTINGFILE=new File("settings.json");
	
	public GuiScreen currentScreen;
	public void setCurrentScreen(GuiScreen screen) {
		if(currentScreen!=null) {
			currentScreen.onClose();
		}
		currentScreen=screen;
		if(screen!=null) {
			ScaledResolution sr=new ScaledResolution();
			screen.onOpen(sr.getScaledWidth(), sr.getScaledHeight());
		}
	}
	private void init() throws LWJGLException {
		fullscreen=false;
		Display.setFullscreen(fullscreen);
		Display.setTitle("ExploreSurvival");
		Display.setDisplayMode(new DisplayMode(854,480));
		Display.setResizable(true);
		Display.create();
		Keyboard.create();
		Mouse.create();
		width=Display.getWidth();
		height=Display.getHeight();
		renderEngine=new RenderEngine();
		if(ExploreSurvival.SETTINGFILE.exists())
			try {
				logger.info("Read Settings in " + SETTINGFILE);
				gamesettings=GameSettings.loadSettings();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.severe("Unable to read Settings in " + SETTINGFILE);
				e.printStackTrace();
			}
		if(gamesettings==null) {
			gamesettings=new GameSettings();
			try {
				logger.info("Save Settings in " + SETTINGFILE);
				gamesettings.saveSettings();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.severe("Unable to save Settings in " + SETTINGFILE);
				e.printStackTrace();
			}
		}
		Display.setVSyncEnabled(gamesettings.limitFrames);
		fontRenderer=new FontRenderer("/default.png", renderEngine);
		ScaledResolution sr=new ScaledResolution();
		GL11.glViewport(0, 0, width, height);
		GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, sr.field_25121_a, sr.field_25120_b, 0.0D, 100.0D, 300.0D);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -200.0F);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		setCurrentScreen(new GuiMainMenu(this));
	}
	public boolean running=false;
	long start;
	//int maxframes=60;
	public int width,height;
	private int frames;
	public void run() {
		long l=System.currentTimeMillis();
		int frames = 0;
		try {
			init();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new PanelCrashReport(e);
			running=false;
		}
		try {
			while(running) {
				start=System.currentTimeMillis();
				GL11.glClear(256);
				if((width!=Display.getWidth()||height!=Display.getHeight())&&(Display.getWidth()>0&&Display.getHeight()>0)) {
					width=Display.getWidth();
					height=Display.getHeight();
					ScaledResolution sr=new ScaledResolution();
					GL11.glViewport(0, 0, width, height);
					GL11.glClear(256);
		            GL11.glMatrixMode(5889);
		            GL11.glLoadIdentity();
		            GL11.glOrtho(0.0D, sr.field_25121_a, sr.field_25120_b, 0.0D, 100.0D, 300.0D);
		            GL11.glMatrixMode(5888);
		            GL11.glLoadIdentity();
		            GL11.glTranslatef(0.0F, 0.0F, -200.0F);
		            if(currentScreen!=null) {
		            	currentScreen.controls.clear();
		            	currentScreen.onOpen(sr.getScaledWidth(), sr.getScaledHeight());
		            }
				}
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glEnable(GL11.GL_BLEND);
	            if(currentScreen!=null) {
	            	ScaledResolution sr=new ScaledResolution();
	                int screenWidth = sr.getScaledWidth();
	                int screenHeight = sr.getScaledHeight();
	                int xMouse = Mouse.getX() * screenWidth / this.width;
	                int yMouse = screenHeight - Mouse.getY() * screenHeight / this.height - 1;
	                GL11.glColor4f(1F, 1F, 1F, 1F);
	            	currentScreen.render(xMouse, yMouse);
	            	currentScreen.tick();
	            } else {
	            	while(Mouse.next()) {
	    				
	    			}
	    			while(Keyboard.next()) {
	    				char c=Keyboard.getEventCharacter();
	    				int key=Keyboard.getEventKey();
	    				if(key==Keyboard.KEY_F11) {
	    					toggleFullscreen();
	    				}
	    			}
	            }
	            
				fontRenderer.render("ExploreSurvival "+version+" ("+this.frames+" fps)", 2, 2, 0xFFFFFF);
				GL11.glPopMatrix();
				checkGlError("render 2d");
				/*if(gamesettings.limitFrames) {
					try {
						sleep((long) Math.max((1F/maxframes*1000F)-(System.currentTimeMillis()-start), 0));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/
				frames++;
				if(System.currentTimeMillis()-l>=1000L) {
					this.frames=frames;
					frames=0;
					l=System.currentTimeMillis();
				}
				running=running&&!Display.isCloseRequested();
				Display.update();
			}
			System.exit(0);
		} catch(Throwable e) {
			e.printStackTrace();
			new PanelCrashReport(e);
		}
	}
    public void toggleFullscreen() {
    	try {
    		fullscreen=!fullscreen;
        	if(fullscreen) {
        		Display.setDisplayMode(Display.getDesktopDisplayMode());
        	} else {
        		Display.setDisplayMode(new DisplayMode(854,480));
        		Display.setResizable(true);
        	}
        	Display.setFullscreen(fullscreen);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fullscreen=!fullscreen;
		}
	}
	public static void main(String[] args) {
		File mkdirLogsDir = new File(".\\logs\\");
		if(!mkdirLogsDir .exists()) {
			mkdirLogsDir.mkdir(); // 创建目录
		}
		LocalDateTime dateTime = LocalDateTime.now(); // get the current time
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM-HH.mm.ss");
		try {
			fileHandler = new FileHandler(".\\logs\\log-" + dateTime.format(formatter) + ".log");
			logger.info("The log file was successfully written.");
		} catch (IOException e) {
			logger.severe("Unable to write to log file");
			e.printStackTrace();
		}
		logger.setLevel(Level.ALL);
		fileHandler.setFormatter(new LoggerCustomFormatter());
		logger.addHandler(fileHandler);
        new ExploreSurvival().start();
		logger.info("Launching ExploreSurvival "+version);
		logger.fine("Launching ExploreSurvival in " + ExploreSurvival.class.getName());
    }
}
