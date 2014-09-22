package predpray;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class main {
	public static void main(String[] args) {
		try {
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
