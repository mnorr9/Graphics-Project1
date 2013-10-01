package Triangle;

import java.util.ArrayList;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;


/**
* For our purposes only two of the GLEventListeners matter.
* Those would be init() and display().
*/

public class JOGL_Example4_GLEventListener implements GLEventListener
{
    private GL glLib; //declare globally
    
    private GLU gluLib;
    
    /**
    * Take care of initialization here.
    */    
    public void init(GLAutoDrawable gld) {
        glLib = gld.getGL();
        gluLib = new GLU();
        /*  Set color of display window to white.  */
        glLib.glClearColor (1.0f, 1.0f, 1.0f, 0.0f);       

        /*  Set parameters for world-coordinate clipping window.  */
        glLib.glMatrixMode (GL.GL_PROJECTION);
        gluLib.gluOrtho2D (-100.0, 100.0, -100.0, 100.0);

        /*  Set mode for constructing geometric transformation matrix.  */
        glLib.glMatrixMode (GL.GL_MODELVIEW);
    }

    /**
     * Take care of drawing here.
     */
    public void display(GLAutoDrawable drawable) {
	/*  Define initial position for triangle.  */
      
      ArrayList verts = new ArrayList();
      //End points of the first triangle
      wcPt2d temp = new wcPt2d(-50.0f, -25.0f);
      verts.add(temp);
      temp = new wcPt2d(50.0f, -25.0f);
      verts.add(temp);
      temp = new wcPt2d(0.0f, 50.0f);
      verts.add(temp);

      glLib.glClear(GL.GL_COLOR_BUFFER_BIT);   //  Clear display window.

      glLib.glColor3f(0.0f, 0.0f, 1.0f);       //  Set fill color to blue.
      glLib.glViewport(0, 0, 300, 300);     //  Set left viewport.
      this.triangle(verts);                //  Display triangle.

      /*  Rotate triangle and display in right half of display window.  */
      glLib.glColor3f (1.0f, 0.0f, 0.0f);         //  Set fill color to red. 
      glLib.glViewport (300, 0, 300, 300);     //  Set right viewport.  
      glLib.glRotatef (90.0f, 0.0f, 0.0f, 1.0f);   //  Rotate about z axis.  
      this.triangle(verts);           //  Display red rotated triangle.

      glLib.glFlush ( );
    }
    
    public void reshape(
			GLAutoDrawable drawable,
			int x,
			int y,
			int width,
			int height
			) {}
    
    public void displayChanged(
			       GLAutoDrawable drawable,
			       boolean modeChanged,
			       boolean deviceChanged
			       ) {}
    
    public void triangle(ArrayList param) {
        int k;

        glLib.glBegin(GL.GL_TRIANGLES);
        
        //Draws the triangle
        for (k = 0; k < 3; k++)
            glLib.glVertex2f(((wcPt2d)param.get(k)).x, ((wcPt2d)param.get(k)).y);
        glLib.glEnd( );
    }
}