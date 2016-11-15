package predpray;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
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
import static org.lwjgl.opengl.GL11.glVertex2f;

import static predpray.Constants.*;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;


public class DisplayHelper {
	public static final int SCREEN_WIDTH = 1080; 
	public static final int SCREEN_HEIGHT = 1080;//480;
	public static final int SCREEN_SIDE_WIDTH = 360;
	private static TrueTypeFont font;
	private static Font awtFont;
	
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

//				main.getMap().renderMap();
//				Main.getMap().renderSmell();
				main.getMap().renderGrass();
				AnimalHandler.renderAllAnimals();
				renderStrings();
				
				
				Display.update();
				
				Display.sync(60);
				
				checkKeyboardForDisplayChanges();
			}

			Display.destroy();
			System.out.println("cake");
		}
		private void renderStrings() {
			drawString(SCREEN_WIDTH + 20,20,"NumRabbits = " + AnimalHandler.getNumberOfRabbits());
			drawString(SCREEN_WIDTH + 20,40,"NumFoxes   = " + AnimalHandler.getNumberOfFoxes());
			CustomComparator c = new CustomComparator();
			ArrayList<Fox> bestFoxes = AnimalHandler.getBestFoxes();
			bestFoxes.sort(c);
			int offset = 0;
			for(Fox bestFox : bestFoxes)
			{
				if (bestFox != null) {
					drawString(SCREEN_WIDTH + 20,60+offset,  "Best fox");
					drawString(SCREEN_WIDTH + 20,80+offset,  "  Id           = " + bestFox.id);
					drawString(SCREEN_WIDTH + 20,100+offset, "  Hunger       = " + bestFox.hunger);
					drawString(SCREEN_WIDTH + 20,120+offset, "  Hunger limit = " + bestFox.getHungerLimitDeath());
					drawString(SCREEN_WIDTH + 20,140+offset, "  Kill count   = " + bestFox.killCount);
				}
				offset += 100;
			}
		}
		private void checkKeyboardForDisplayChanges() {
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				displayHelper.exit();
			}
			if (Display.isCloseRequested()) {
				displayHelper.exit();
			}
			//TODO: event
			if (Mouse.isButtonDown(0))
			{
					main.createFoxesInASquare(Mouse.getX()*Map.numberOfNodesX/DisplayHelper.SCREEN_WIDTH, 
							Mouse.getY()*Map.numberOfNodesX/DisplayHelper.SCREEN_HEIGHT, 5);
			}
				if (Mouse.isButtonDown(1))
			{
					main.createRabbitsInASquare(Mouse.getX()*Map.numberOfNodesX/DisplayHelper.SCREEN_WIDTH, 
							Mouse.getY()*Map.numberOfNodesX/DisplayHelper.SCREEN_HEIGHT, 5);
			}
		}
		private void initializeDisplay() {
			try {
				Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH + SCREEN_SIDE_WIDTH, SCREEN_HEIGHT));
				Display.setTitle("Hello, predatorprej");
				Display.create();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
			
			// Initialization code OpenGL
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, SCREEN_WIDTH + SCREEN_SIDE_WIDTH, SCREEN_HEIGHT, 0, 1, -1);
			glMatrixMode(GL_MODELVIEW);
			
			Font awtFont = new Font("Courier", Font.PLAIN, 18);
			font = new TrueTypeFont(awtFont, true);
		}
		public void stop() {
			running = false;
		}
		
	}

	private static void drawString(int x, int y, String text)
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor3f(1f, 1f, 1f);
		font.drawString(new Float(x), new Float(y), text, new Color(1f, 1f, 1f));
		
		GL11.glDisable(GL11.GL_BLEND);
		
	}
	
	
	public static void render(Animal animal) {
		float screenPositionX = animal.getPositionX() * Node.getDisplayWidth();
		float screenPositionY = animal.getPositionY() * Node.getDisplayHeight();
		
		if (animal instanceof Fox && animal.renderMe)
		{
			float animalSize = ((Fox)animal).killCount;
			animalSize = animalSize/100;
			float dx = Node.getDisplayWidth()*(1+animalSize);
			float dy = Node.getDisplayHeight()*(1+animalSize);

			renderFox((Fox)animal, screenPositionX, screenPositionY, dx, dy);
		}
		else if (animal instanceof Rabbit &&  animal.renderMe)
		{
			float dx = Node.getDisplayWidth() / 10;
			float dy = Node.getDisplayHeight() / 10;
			renderRabbit((Rabbit)animal, screenPositionX, screenPositionY, dx, dy);
		}
	}
	
	public static void renderFox (Fox animal, float screenPositionX, float screenPositionY, float dx, float dy)
	{
		int numCorners = 4;
		float[] cornersX = {dx, Node.getDisplayWidth() - dx, Node.getDisplayWidth() - dx, dx};
		float[] cornersY = {dy, dy, Node.getDisplayHeight() - dy, Node.getDisplayHeight() - dy};
		for (int i = 0; i < numCorners; ++i)
		{
			cornersX[i] += screenPositionX;
			cornersY[i] += screenPositionY;
		}

		cornersX[0] = (cornersX[0] + cornersX[1])/2;
		cornersX[1] = cornersX[0]; 
		renderQuad(animal.getColor(), cornersX, cornersY, numCorners);
		
		glBegin(GL_LINE_LOOP); {
			GL11.glColor3f(0f, 0f, 0f);
			for (int i = 0; i < numCorners; ++i)
			{
				glVertex2f(cornersX[i],cornersY[i]);
			}
		} glEnd();		
	}
	public static void renderRabbit (Rabbit animal, float screenPositionX, float screenPositionY, float dx, float dy)
	{
		drawCircle(screenPositionX + Node.getDisplayWidth()*0.5f, screenPositionY + Node.getDisplayWidth()*0.5f, 
				Node.getDisplayWidth()*0.5f, 0f, 360f, 8, animal.color);
	}
	
	public static void drawCircle(float x, float y, float r, double startingAngleDeg, double endAngleDeg, int slices, PredPrayColor color) {
        int radius = (int) r;

        double arcAngleLength = (endAngleDeg - startingAngleDeg) / 360f;

        float[] vertexesX = new float[4];
        float[] vertexesY = new float[4];

        double initAngle = Math.PI / 180f * startingAngleDeg;
        float prevXA = (float) Math.sin(initAngle) * radius;
        float prevYA = (float) Math.cos(initAngle) * radius;

        for(int arcIndex = 0; arcIndex < slices+1; arcIndex++) {
            double angle = Math.PI * 2 * ((float)arcIndex) / ((float)slices);
            angle += Math.PI / 180f;
            angle *= arcAngleLength;
            int index = 0;
            float xa = (float) Math.sin(angle) * radius;
            float ya = (float) Math.cos(angle) * radius;
            vertexesX[index] = x;
            vertexesY[index] = y;
            vertexesX[index+1] = x+prevXA;
            vertexesY[index+1] = y+prevYA;
            vertexesX[index+2] = x+xa;
            vertexesY[index+2] = y+ya;
            vertexesX[index+3] = x;
            vertexesY[index+3] = y;
            
            renderQuad(color, vertexesX, vertexesY, 4);
            
            prevXA = xa;
            prevYA = ya;
        }
    }
	
	public static void renderLine(PredPrayColor color, float[] cornersX,  float[] cornersY)
	{
		glBegin(GL_LINES); {
			
		} glEnd();
	}
	
	
	public static void renderQuad(PredPrayColor color,
			float[] cornersX,  float[] cornersY, int numEdges)
	{
		glBegin(GL_QUADS); {
			GL11.glColor3f(color.red, color.green, color.blue);
			for (int i = 0; i < numEdges; ++i) {
				GL11.glVertex2f(cornersX[i], cornersY[i]);
			}
		} glEnd();
	}

	
	public void renderQuad(PredPrayColor color, 
			float corner1X, float corner1Y,
			float corner2X, float corner2Y,
			float corner3X, float corner3Y,
			float corner4X, float corner4Y) {
		renderQuad(color.red, color.green, color.blue, 
				corner1X, corner1Y, 
				corner2X, corner2Y, 
				corner3X, corner3Y, 
				corner4X, corner4Y);
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
