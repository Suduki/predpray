package predpray;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public class DisplayHelper {
	public static final int SCREEN_WIDTH = 1080; 
	public static final int SCREEN_HEIGHT = 1080;//480;
	
	private static RenderThread renderThread;
	public Thread renderThreadThread;
	

	public DisplayHelper() {
		renderThread = new RenderThread(this);
		renderThreadThread = new Thread(renderThread);
		renderThreadThread.start();
	}
	
	public void exit() {
		renderThread.stop();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(renderThreadThread.isAlive()) {
//			throw new RuntimeException("renderThreadThread dog inte på rätt sätt!");
		}
	}

	
	private static class RenderThread implements Runnable {

		private boolean running;
		private DisplayHelper displayHelper;
		
		public RenderThread(DisplayHelper displayHelper) {
			this.displayHelper = displayHelper;
		}
		@Override
		public void run() {
			initializeDisplay();

			running = true;
			while(running) {
				glClear(GL_COLOR_BUFFER_BIT);

				Main.getMap().renderMap();
				AnimalHandler.renderAllAnimals();
				
				Display.update();
				Display.sync(60);
				
				checkKeyboardForDisplayChanges();
			}

			Display.destroy();
			System.out.println("cake");
		}
		private void checkKeyboardForDisplayChanges() {
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				displayHelper.exit();
			}
			if (Display.isCloseRequested()) {
				displayHelper.exit();
			}
		}
		private void initializeDisplay() {
			try {
				Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
				Display.setTitle("Hello, predatorprej");
				Display.create();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
			
			
			// Initialization code OpenGL
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);
			glMatrixMode(GL_MODELVIEW);
		}
		public void stop() {
			running = false;
		}
		
	}

	public static void render(Animal animal) {
		
		float screenPositionX = animal.getPositionX() * Node.getDisplayWidth();
		float screenPositionY = animal.getPositionY() * Node.getDisplayHeight();
		
		float dx = Node.getDisplayWidth() / 10;
		float dy = Node.getDisplayHeight() / 10;
		
		
		Main.displayHelper.renderQuad(animal.getColor()[0], animal.getColor()[1], animal.getColor()[2], 
				screenPositionX + dx, screenPositionY + dy, 
				screenPositionX + Node.getDisplayWidth() - dx, screenPositionY + dy, 
				screenPositionX + Node.getDisplayWidth() - dx, screenPositionY + Node.getDisplayHeight() - dy, 
				screenPositionX + dx, screenPositionY + Node.getDisplayHeight() - dy); 
		
	}

	public void renderQuad
		(float red, float green, float blue, 
			float corner1X, float corner1Y,
			float corner2X, float corner2Y,
			float corner3X, float corner3Y,
			float corner4X, float corner4Y) {
		glBegin(GL_QUADS); {
			GL11.glColor3f(red, green, blue);
			GL11.glVertex2f(corner1X, corner1Y);
			GL11.glVertex2f(corner2X, corner2Y);
			GL11.glVertex2f(corner3X, corner3Y);
			GL11.glVertex2f(corner4X, corner4Y);
		} glEnd();
	}

}
