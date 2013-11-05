/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cityscene;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.j2d.TextRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.DecimalFormat;

/**
 *
 * @author nasser
 */
public class CityScene extends JFrame implements GLEventListener, KeyListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private GLCanvas canvas;
    private GLU glu;
    private float camera_x;
    private float camera_y;
    private float camera_z;
    private float center_x;
    private float center_y;
    private float center_z;
    private float up_x;
    private float up_y;
    private float up_z;
    private float angle; // angle of rotation for the camera direction
    private float angle2;
    
    private Animator anim;
    private TextRenderer text;
    private DecimalFormat form;
    private Tree tree;
    private Building building;
    private TrafficLight trafficLight;
    private float sceneBoundary_x;
    private float sceneBoundary_y;
    private float sceneBoundary_z;
    private SpCar car;
    private float drive=-100.0f;
    
    private float drive2=-250.0f;
    private float dooropen=0;
    private float dooropen2=0;
    private float turn = 0;
    private boolean stop;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        CityScene cityScene = new CityScene();
    }

    public CityScene() {
        reset(); // Resets the camera glu.gluLookAt parameters

        text = new TextRenderer(new Font("SansSerif", Font.BOLD, 12));
        form = new DecimalFormat("####0.00");

        sceneBoundary_x = 13f;
        sceneBoundary_z = 13f;
        stop = false;
        
        GLCapabilities caps = new GLCapabilities();
        canvas = new GLCanvas(caps);
        canvas.addGLEventListener(this);
        canvas.addKeyListener(this);
        add(canvas);
        anim = new Animator(canvas);
        anim.start();
        building = new Building();
        car = new SpCar();
        trafficLight = new TrafficLight();

        setTitle("City Scene");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
        centerWindow();

    }

    @Override
    public void init(GLAutoDrawable drawable) {
        // Initialize object state.

        glu = new GLU();
        GL gl = drawable.getGL();

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glClearDepth(1.0f);
        
        //Set color of display window to black.
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        tree = new Tree();
        createDoubleLaneLine(gl);
        createGreenFields(gl);       
        createZebraCrossing(gl);

    }

    @Override
    public void display(GLAutoDrawable drawable) {

        GL gl = drawable.getGL();
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        setCamera(gl, glu);

        gl.glMatrixMode(GL.GL_MODELVIEW);

        gl.glPushMatrix();

          gl.glPushMatrix();
            gl.glRotatef(-90, 1, 0, 0); // Rotate World!
            gl.glCallList(1); // Creates Double Lane Line
            gl.glCallList(2); // Creates Green Fields
            gl.glCallList(3); // Creates Zebra Crossing Box
          gl.glPopMatrix();
            
          plantTree(drawable, 10.0f, -3.0f);
          plantTree(drawable, -3.0f, -3.0f);
          plantTree(drawable, -2.0f, -4.0f);
        
          plantTree(drawable, 5.0f, 10.0f);
          plantTree(drawable, 5.0f,  5.0f);
          plantTree(drawable,-2.0f,  5.0f);        
        
          // Trees at the borders
          plantTree(drawable, -2.0f, -15.0f);
          plantTree(drawable, 5.0f, -10.0f);
        
          drawBuildings(drawable, 3.0f, -1.66f);
          drawBuildings(drawable, 4.5f, -1.66f);
          drawStores(drawable, -4.5f,  -1.66f);
        
          drawCar(gl);
          drawCar2(gl);
        
          drawLights( drawable );
        
        gl.glPopMatrix();

        displayCameraPositionInfo(drawable);

        gl.glFlush();
    }//end of display()

    private void plantTree(GLAutoDrawable drawable, float coord_x, float coord_z){
      GL gl = drawable.getGL();

      gl.glPushMatrix();
        gl.glTranslatef(coord_x, 0.0f, coord_z);
        tree.drawTree(drawable); 
      gl.glPopMatrix();
    }
    
    private void drawBuildings(GLAutoDrawable drawable, float coord_x, float coord_z) {
      GL gl = drawable.getGL();

      // draw building #1
      gl.glPushMatrix();
        gl.glTranslated(coord_x, 0.0, coord_z);
        gl.glRotated( -90, 1.0, 0.0, 0.0 );
        building.drawBuilding(drawable);
      gl.glPopMatrix();
    }
    
    private void drawStores(GLAutoDrawable drawable, float coord_x, float coord_z) {
      GL gl = drawable.getGL();

      // draw building #1
      gl.glPushMatrix();
        gl.glTranslated(coord_x, 0.0, coord_z);
        gl.glRotated( -90, 1.0, 0.0, 0.0 );
        Store.drawStore(drawable);
      gl.glPopMatrix();
    }

    private void drawLights( GLAutoDrawable drawable )
    {
      GL gl = drawable.getGL();

      gl.glPushMatrix();
        gl.glTranslated( 1.08, 0.0, 1.08 );
        gl.glRotated( -135, 0.0, 1.0, 0.0 );
        trafficLight.drawTrafficLight( drawable );
      gl.glPopMatrix();
    }

    private void displayCameraPositionInfo(GLAutoDrawable drawable) {
        text.beginRendering(drawable.getWidth(), drawable.getHeight());
        text.setColor(new Color(255, 255, 255)); // White

        text.draw("Camera x: " + form.format(camera_x) + " y: "
                + form.format(camera_y) + " z: " + form.format(camera_z)
                + " pan: " + form.format(angle), 10, 10);

        text.endRendering();
        text.flush();
    }//displayCameraPositionInfo

    private void createZebraCrossing(GL gl) {

        int index = gl.glGenLists(3);
        gl.glNewList(index, GL.GL_COMPILE);

        // ZEBRA CROSSING ( pedestrian crossing )
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // white
        gl.glLineWidth(2.5f);
        gl.glVertex3f(1.0f, 1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);
        gl.glVertex3f(-1.0f, 1.0f, 0.0f);
        gl.glEnd();

        // NORTH CROSSING
        float x1 = (float) -0.9;
        float x2 = (float) -0.7;
        float interval = (float) -0.4;
        int blocks = 5;

        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // white

        for (int i = 1; i <= blocks; i++) {
            gl.glVertex3f(x1, 1.2f, 0.0f);
            gl.glVertex3f(x2, 1.2f, 0.0f);
            gl.glVertex3f(x2, 1.5f, 0.0f);
            gl.glVertex3f(x1, 1.5f, 0.0f);

            x1 = (float) (x1 - interval);
            x2 = (float) (x2 - interval);

        }// end of for loop
        gl.glEnd();

        // South Crossing
        x1 = (float) -0.9;
        x2 = (float) -0.7;
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // white

        for (int i = 1; i <= blocks; i++) {
            gl.glVertex3f(x1, -1.2f, 0.0f);
            gl.glVertex3f(x2, -1.2f, 0.0f);
            gl.glVertex3f(x2, -1.5f, 0.0f);
            gl.glVertex3f(x1, -1.5f, 0.0f);

            x1 = (float) (x1 - interval);
            x2 = (float) (x2 - interval);

        }// end of for loop
        gl.glEnd();

        // East Crossing
        float y1 = (float) -0.9;
        float y2 = (float) -0.7;

        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // white

        for (int i = 1; i <= blocks; i++) {
            gl.glVertex3f(1.2f, y1, 0.0f);
            gl.glVertex3f(1.2f, y2, 0.0f);
            gl.glVertex3f(1.5f, y2, 0.0f);
            gl.glVertex3f(1.5f, y1, 0.0f);

            y1 = (float) (y1 - interval);
            y2 = (float) (y2 - interval);

        }// end of for loop
        gl.glEnd();

        // West Crossing
        y1 = (float) -0.9;
        y2 = (float) -0.7;

        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // white

        for (int i = 1; i <= blocks; i++) {
            gl.glVertex3f(-1.2f, y1, 0.0f);
            gl.glVertex3f(-1.2f, y2, 0.0f);
            gl.glVertex3f(-1.5f, y2, 0.0f);
            gl.glVertex3f(-1.5f, y1, 0.0f);

            y1 = (float) (y1 - interval);
            y2 = (float) (y2 - interval);

        }// end of for loop
        gl.glEnd();
        gl.glEndList();
    }

    private void createGreenFields(GL gl) {

        int index = gl.glGenLists(1);
        gl.glNewList(index, GL.GL_COMPILE);

        gl.glBegin(GL.GL_QUADS); // Four vertices

        drawSky(gl);

        
        // lower right sidewalk
        gl.glColor3f(0.3f, 0.3f, 0.3f); // Grey
        gl.glVertex3f(-1.0f,  15.0f, 0f);
        gl.glVertex3f(-1.65f, 15.0f, 0f);
        gl.glVertex3f(-1.65f,  1.0f, 0f);
        gl.glVertex3f(-1.0f,   1.0f, 0f);
        
        gl.glVertex3f(-1.65f,  1.00f, 0f);
        gl.glVertex3f(-1.65f,  1.65f, 0f);
        gl.glVertex3f(-15.0f,  1.65f, 0f);
        gl.glVertex3f(-15.0f,   1.0f, 0f);
        
        
        // upper right sidewalk
        gl.glVertex3f(-1.0f,  -15.0f, 0f);
        gl.glVertex3f(-1.65f, -15.0f, 0f);
        gl.glVertex3f(-1.65f,  -1.0f, 0f);
        gl.glVertex3f(-1.0f,   -1.0f, 0f);

        gl.glVertex3f(-1.65f,  -1.00f, 0f);
        gl.glVertex3f(-1.65f,  -1.65f, 0f);
        gl.glVertex3f(-15.0f,  -1.65f, 0f);
        gl.glVertex3f(-15.0f,  -1.00f, 0f);
        
        
        // upper left sidewalk
        gl.glVertex3f(1.0f,  -15.0f, 0f);
        gl.glVertex3f(1.65f, -15.0f, 0f); 
        gl.glVertex3f(1.65f,  -1.0f, 0f);
        gl.glVertex3f(1.0f,   -1.0f, 0f);        

        gl.glVertex3f(1.65f,  -1.00f, 0f);
        gl.glVertex3f(1.65f,  -1.65f, 0f);
        gl.glVertex3f(15.0f,  -1.65f, 0f);
        gl.glVertex3f(15.0f,  -1.00f, 0f);
        
        
        // lower left sidewalk
        gl.glVertex3f(1.0f,  15.0f, 0f);
        gl.glVertex3f(1.65f, 15.0f, 0f); 
        gl.glVertex3f(1.65f,  1.0f, 0f);
        gl.glVertex3f(1.0f,   1.0f, 0f);
        
        gl.glVertex3f(1.65f,  1.00f, 0f);
        gl.glVertex3f(1.65f,  1.65f, 0f);
        gl.glVertex3f(15.0f,  1.65f, 0f);
        gl.glVertex3f(15.0f,  1.00f, 0f);
        
        /***********************************/
                
        // lower right field
        gl.glColor3f(0.0f, 0.3f, 0.0f); // Dark Green        
        gl.glVertex3f(-1.66f, 15.0f, 0f);
        gl.glVertex3f(-15.0f, 15.0f, 0f);
        gl.glVertex3f(-15.0f,  1.66f, 0f);
        gl.glVertex3f(-1.66f,  1.66f, 0f);
        
        // upper right field
        gl.glVertex3f(-1.66f, -15.0f, 0f);
        gl.glVertex3f(-15.0f, -15.0f, 0f);
        gl.glVertex3f(-15.0f,  -1.66f, 0f);
        gl.glVertex3f(-1.66f,  -1.66f, 0f);
        
        // upper left field
        gl.glVertex3f(1.66f, -15.0f, 0f);
        gl.glVertex3f(15.0f, -15.0f, 0f);
        gl.glVertex3f(15.0f,  -1.66f, 0f);
        gl.glVertex3f(1.66f,  -1.66f, 0f);

        // lower left field
        gl.glVertex3f(1.66f, 15.0f, 0f);
        gl.glVertex3f(15.0f, 15.0f, 0f);
        gl.glVertex3f(15.0f,  1.66f, 0f);
        gl.glVertex3f(1.66f,  1.66f, 0f);


        gl.glEnd();
        gl.glEndList();
    }// end of create Green Fields.

    private void drawSky(GL gl) {


        gl.glColor3f(0.7f, 0.9f, 1.0f); //Sky Blue
        
//  (ceiling)        
//        gl.glVertex3f( 15.0f,  15.0f, 30f);
//        gl.glVertex3f(-15.0f,  15.0f, 30f);
//        gl.glVertex3f(-15.0f, -15.0f, 30f);
//        gl.glVertex3f( 15.0f, -15.0f, 30f);

// yz plane (side plane)
        gl.glVertex3f(15.0f,  15.0f, 15.0f);
        gl.glVertex3f(15.0f,  15.0f,  0.0f);
        gl.glVertex3f(15.0f, -15.0f,  0.0f);
        gl.glVertex3f(15.0f, -15.0f, 15.0f);

// yz plane (side plane)
        gl.glVertex3f(-15.0f,  15.0f, 15.0f);
        gl.glVertex3f(-15.0f,  15.0f,  0.0f);
        gl.glVertex3f(-15.0f, -15.0f,  0.0f);
        gl.glVertex3f(-15.0f, -15.0f, 15.0f);


//xz plane (back plane)
        gl.glVertex3f( 15.0f, 15.0f, 15.0f);
        gl.glVertex3f( 15.0f, 15.0f,  0.0f);
        gl.glVertex3f(-15.0f, 15.0f,  0.0f);
        gl.glVertex3f(-15.0f, 15.0f, 15.0f);

// xz plane (front plane)
        gl.glVertex3f( 15.0f, -15.0f, 15.0f);
        gl.glVertex3f( 15.0f, -15.0f,  0.0f);
        gl.glVertex3f(-15.0f, -15.0f,  0.0f);
        gl.glVertex3f(-15.0f, -15.0f, 15.0f);

    }
    
    private void createDoubleLaneLine(GL gl) {
        // create one display list
        int index = gl.glGenLists(1);
        gl.glNewList(index, GL.GL_COMPILE);
        gl.glLineWidth(3.0f);
        // North
        // ***************************************
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(2.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(0.0f, 1.8f, 0.0f);
        gl.glVertex3f(0.0f, 15.0f, 0.0f);
        gl.glEnd();

        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(-0.1f, 1.8f, 0.0f);
        gl.glVertex3f(-0.1f, 15.0f, 0.0f);
        gl.glEnd();

        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White
        gl.glVertex3f(0.9f, 1.7f, 0.0f);
        gl.glVertex3f(-0.9f, 1.7f, 0.0f);
        gl.glEnd();

        // South
        // ***************************************
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(2.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(0.0f, -1.8f, 0.0f);
        gl.glVertex3f(0.0f, -15.0f, 0.0f);
        gl.glEnd();

        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(-0.1f, -1.8f, 0.0f);
        gl.glVertex3f(-0.1f, -15.0f, 0.0f);
        gl.glEnd();

        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White
        gl.glVertex3f(0.9f, -1.7f, 0.0f);
        gl.glVertex3f(-0.9f, -1.7f, 0.0f);
        gl.glEnd();


        // EAST
        // ***************************************
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(2.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(1.8f, 0.0f, 0.0f);
        gl.glVertex3f(15.0f, 0.0f, 0.0f);
        gl.glEnd();

        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(1.8f, -0.1f, 0.0f);
        gl.glVertex3f(15.0f, -0.1f, 0.0f);
        gl.glEnd();

        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White
        gl.glVertex3f(1.7f, 0.9f, 0.0f);
        gl.glVertex3f(1.7f, -0.9f, 0.0f);
        gl.glEnd();

        // WEST
        // ***************************************
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(2.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(-1.8f, 0.0f, 0.0f);
        gl.glVertex3f(-15.0f, 0.0f, 0.0f);
        gl.glEnd();

        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(-1.8f, -0.1f, 0.0f);
        gl.glVertex3f(-15.0f, -0.1f, 0.0f);
        gl.glEnd();


        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White
        gl.glVertex3f(-1.7f, 0.9f, 0.0f);
        gl.glVertex3f(-1.7f, -0.9f, 0.0f);
        gl.glEnd();

        gl.glEndList();
    }// End of create Double Center Yellow Lines

    private void setCamera(GL gl, GLU glu) {
      // Change to projection matrix.
      gl.glMatrixMode(GL.GL_PROJECTION);
      gl.glLoadIdentity();

      // Perspective.
      float widthHeightRatio = (float) getWidth() / (float) getHeight();
      glu.gluPerspective(45, widthHeightRatio, 1, 1000);

      gl.glRotatef(angle2, 0, 1, 0);
      glu.gluLookAt(camera_x, camera_y, camera_z, center_x, center_y,
              center_z, up_x, up_y, up_z);

      gl.glRotatef(angle, 0, 1, 0); // Panning

    }

    private void reset() {
        camera_x = 0;
        camera_y = (float) 1;
        camera_z = (float) -6.20; 

        center_x = 0;
        center_y = 0;
        center_z = 0;

        up_x = 0;
        up_y = 1;
        up_z = 0;

        angle = 0;
        angle2 = 0; 
        
        drive=-100.0f;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        String keyString = "" + ke.getKeyChar() + "";


        if ((ke.getKeyCode() == KeyEvent.VK_UP)
                && ((ke.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            // Moves UPWARD in yx plane
            camera_y += 0.5;
        } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
            // Moves FORWARD on xz plane
            if (camera_z < sceneBoundary_z) {
                camera_z += 0.1;
                center_z += 0.1;
            }
        }


        if ((ke.getKeyCode() == KeyEvent.VK_DOWN)
                && ((ke.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            // Moves DOWNWARD on xz plane
            if (camera_y >= 0.5) // Do not allow to go beneath 'x' plane
            {
                camera_y -= 0.5;
            }
        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            // Moves BACKWARDS on xz plane
            if (camera_z > (sceneBoundary_z * -1)) {
                camera_z -= 0.1;
                center_z -= 0.1;
            }
        }



        if ((ke.getKeyCode() == KeyEvent.VK_LEFT)
                && ((ke.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            // PAN LEFT.
            if (angle == 0f) {
                angle = 359f;
            } else {
                angle -= 1f;
            }

        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            // Moves camera to the LEFT on the xz plane
            if (camera_x < sceneBoundary_x) {
                camera_x += 0.1;
                center_x += 0.1;
            }
        }


        if ((ke.getKeyCode() == KeyEvent.VK_RIGHT)
                && ((ke.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            // PAN RIGHT
            if (angle == 359f) {
                angle = 0f;
            } else {
                angle += 1f;
            }
        } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Moves camera to the RIGHT on the xz plane
            if (camera_x > (sceneBoundary_x * -1) ) {
                camera_x -= 0.1;
                center_x -= 0.1;
            }
        }

        // Resets Camera's glu.gluLookAt parameters
        if (keyString.equals("r")) {
            reset();
        }
        
        if (keyString.equals("t")) {
            drive=-100.0f;
            drive2=-250.0f;
            turn = 0;
            
        }

        // Look left
        if (keyString.equals("<")) {
            angle2 -= 1.0f;
        }
        
        // Look right
        if (keyString.equals(">")) {
            angle2 += 1.0f;
        }        
        
        if (keyString.equals("9")) {
			if (dooropen<70){
			dooropen += 1;
			}
        }        
        if (keyString.equals("8")) {
			if (dooropen>0){
			dooropen -= 1;
			}
        }        
        if (keyString.equals("7")) {
			if (dooropen2<70){
			dooropen2 += 1;
			}
        }        
        if (keyString.equals("6")) {
			if (dooropen2>0){
			dooropen2 -= 1;
			}
        }  
        

       if (keyString.equals("s")) {
            stop = !(stop);
        } 
        
        if( KeyEvent.VK_1 == ke.getKeyCode() )
        {
          trafficLight.updateTrafficColors();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

private void drawCar(GL gl) {
        
        if (drive < -60) {
            gl.glPushMatrix();
                gl.glRotatef(0f, 1.0f, 0.0f, 0.0f);
                gl.glScalef(0.05f, 0.05f, 0.05f);
                gl.glTranslatef(drive, 1.0f, 6.0f);
                car.createCar(gl, 1, 1, 0, dooropen);
                drive += 0.1;
            gl.glPopMatrix();
        } 
        else if (drive >= -60 && drive < 0) {
            gl.glPushMatrix();
            gl.glRotatef(0f, 1.0f, 0.0f, 0.0f);
            gl.glScalef(0.05f, 0.05f, 0.05f);
            gl.glTranslatef(-60.0f, 1.0f, 6.0f);
            car.createCar(gl, 1, 1, 0, dooropen);
            if ( stop == false){
                drive += 0.1;
            }
            gl.glPopMatrix();	
        }
        else if (drive >= 0 && drive < 20) {
            gl.glPushMatrix();
            gl.glScalef(0.05f, 0.05f, 0.05f);
            gl.glTranslatef(drive-60f, 1.0f, 6.0f);
            car.createCar(gl, 1, 1, 0, dooropen); 
            if (stop == false){
                drive += 0.1;
            }
            //turn -= 0.2;
            gl.glPopMatrix();	
        }
        else if (drive >= 20 && drive < 40) {
            gl.glPushMatrix();
            gl.glScalef(0.05f, 0.05f, 0.05f);
            gl.glTranslatef(drive-60f, 1.0f, 6.0f);
            gl.glRotatef(turn, 0.0f, 1.0f, 0.0f);
            car.createCar(gl, 1, 1, 0, dooropen);
            if (stop == false){
                drive += 0.1;
                turn -= 0.2;
            }
            gl.glPopMatrix();	
        }
        else if (drive >= 40 && drive < 50) {
            gl.glPushMatrix();
            gl.glScalef(0.05f, 0.05f, 0.05f);
            gl.glTranslatef(drive-60f, 1.0f, 6.0f);
            gl.glRotatef(turn, 0.0f, 1.0f, 0.0f);
            car.createCar(gl, 1, 1, 0, dooropen);
            if (stop == false){
                drive += 0.1;
                turn -= 0.35;
            }
            gl.glPopMatrix();	
        }
        else if (drive >= 50 && drive < 55) {
            gl.glPushMatrix();
            gl.glScalef(0.05f, 0.05f, 0.05f);
            gl.glTranslatef(drive-60f, 1.0f, 6.0f);
            gl.glRotatef(turn, 0.0f, 1.0f, 0.0f);
            car.createCar(gl, 1, 1, 0, dooropen); 
            if (stop == false){
                drive += 0.1;
                turn -= 0.45;
            }
            gl.glPopMatrix();	
        } 
        else
        {
            gl.glPushMatrix();
                gl.glRotatef(0f, 0.0f, 1.0f, 0.0f);
                gl.glRotatef(-90f, 0.0f, 1.0f, 0.0f);
                gl.glScalef(0.05f, 0.05f, 0.05f);
                gl.glTranslatef(drive-41f, 1.0f, 5.0f);
                car.createCar(gl, 1, 1, 0, dooropen);
                if (stop == false){
                    drive += 0.1;
                }
            gl.glPopMatrix();
        }
    }
    
    private void drawCar2(GL gl){
            gl.glPushMatrix();
                gl.glRotatef(0f, 1.0f, 0.0f, 0.0f);
                gl.glRotatef(90f, 0.0f, 1.0f, 0.0f);
                gl.glScalef(0.05f, 0.05f, 0.05f);
                gl.glTranslatef(drive2, 0.0f, 5.0f);
                car.createCar(gl, 1, 0, 0, dooropen2);
                if (stop == false){
                    drive2 += 0.3;
                }
            gl.glPopMatrix();
    }    
    
    public void centerWindow() 
    {
        Dimension screenSize =
            Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.width > screenSize.width )
            frameSize.width = screenSize.width;
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        setLocation (
                   (screenSize.width - frameSize.width ) >> 1,
                   (screenSize.height - frameSize.height) >> 1
                   );
    }
    
}
