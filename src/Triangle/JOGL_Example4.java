package Triangle;

import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;


/**
 * This is a basic JOGL app. Feel free to
 * reuse this code or modify it.
 */
public class JOGL_Example4 extends JFrame {
    public static void main(String[] args) {
	final JOGL_Example4 app = new JOGL_Example4();
	//show what we've done
	SwingUtilities.invokeLater (
				    new Runnable() {
					public void run() {
					    app.setVisible(true);
					}
				    }
				    );
    }

    public JOGL_Example4() {
	//set the JFrame title
	super("Fourth JOGL Application");
	//kill the process when the JFrame is closed
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//only three JOGL lines of code ... and here they are
	GLCapabilities glcaps = new GLCapabilities();
	GLCanvas glcanvas = new GLCanvas();
	glcanvas.addGLEventListener(new JOGL_Example4_GLEventListener());
	//add the GLCanvas just like we would any Component
	getContentPane().add(glcanvas, BorderLayout.CENTER);
	setSize(600, 300);
	//center the JFrame on the screen
	centerWindow(this);
    }

    public void centerWindow(Component frame) {
	Dimension screenSize =
	    Toolkit.getDefaultToolkit().getScreenSize();
	Dimension frameSize = frame.getSize();
	if (frameSize.width > screenSize.width )
	    frameSize.width = screenSize.width;
	if (frameSize.height > screenSize.height)
	    frameSize.height = screenSize.height;
	frame.setLocation (
			   (screenSize.width - frameSize.width ) >> 1,
			   (screenSize.height - frameSize.height) >> 1
			   );
    }
}