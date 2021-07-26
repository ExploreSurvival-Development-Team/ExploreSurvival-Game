package ExploreSurvival.Game;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import ExploreSurvival.Game.render.FontRenderer;
import ExploreSurvival.Game.render.RenderEngine;
import ExploreSurvival.Game.render.ShapeRenderer;
import ExploreSurvival.Game.util.GameSettings;
import ExploreSurvival.Game.util.ScaledResolution;

public class ExploreSurvival extends Thread {
	private boolean fullscreen;
	public ExploreSurvival() {
		running=true;
		instance=this;
	}
	private static ExploreSurvival instance;
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
	public RenderEngine renderengine;
	public FontRenderer fontrenderer;
	public GameSettings gamesettings;
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
		renderengine=new RenderEngine();
		gamesettings=new GameSettings();
		try {
			fontrenderer=new FontRenderer("/default.png", renderengine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean running=false;
	long start;
	int maxframes=60;
	public int width,height;
	private int frames;
	public void run() {
		long l=System.currentTimeMillis();
		int frames = 0;
		try {
			init();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		while(running) {
			start=System.currentTimeMillis();
			GL11.glClear(256);
			while(Keyboard.next()) {
				char c=Keyboard.getEventCharacter();
				int key=Keyboard.getEventKey();
				if(key==Keyboard.KEY_F11) {
					toggleFullscreen();
				}
			}
			if(width!=Display.getWidth()||height!=Display.getHeight()) {
				width=Display.getWidth();
				height=Display.getHeight();
				GL11.glViewport(0, 0, width, height);
			}
			while(Mouse.next()) {
				
			}
			ScaledResolution sr=new ScaledResolution();
			GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glClear(256);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0D, sr.field_25121_a, sr.field_25120_b, 0.0D, 100.0D, 300.0D);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0F, 0.0F, -200.0F);
			fontrenderer.render("ExploreSurvival ("+this.frames+" frames)", 2, 2, 0xFFFFFF);
			GL11.glPopMatrix();
			checkGlError("render 2d");
			if(gamesettings.limitFrames) {
				try {
					sleep((long) Math.max((1F/maxframes*1000F)-(System.currentTimeMillis()-start), 0));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(System.currentTimeMillis()-l>=1000L) {
				this.frames=frames;
				frames=0;
				l=System.currentTimeMillis();
			} else frames++;
			running=!Display.isCloseRequested();
			Display.update();
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
        //System.out.println("Hello World!");
        new ExploreSurvival().start();
    }
}
