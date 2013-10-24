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
import java.awt.Font;
import java.text.DecimalFormat;

/**
 *
 * @author nasser
 */
public class CityScene extends JFrame implements GLEventListener, KeyListener{

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
    private Animator anim;
    private TextRenderer text;
    private DecimalFormat form;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CityScene cityScene = new CityScene();
    }

    public CityScene() {

        camera_x = 0;
        camera_y = (float) 10 ; //-8.10;
        camera_z = (float) -10 ; //  3.30; // -10
        
//        center_x = 0;
//        center_y = (float) 5.10;
//        center_z = 0;
//        
//        up_x = 0;
//        up_y = 1;
//        up_z = 0;
//        
        angle = 0;
        
        text = new TextRenderer(new Font("SansSerif", Font.BOLD, 12));
        form = new DecimalFormat("####0.00");
        
        center_x = 0;
        center_y = 0;
        center_z = 0;
        
        up_x = 0;
        up_y = 1;
        up_z = 0;
        
        text = new TextRenderer(new Font("SansSerif", Font.BOLD, 12));
        form = new DecimalFormat("####0.00");
        
        GLCapabilities caps = new GLCapabilities();
        canvas = new GLCanvas(caps);
        canvas.addGLEventListener(this);
        canvas.addKeyListener(this);
        add(canvas);
        anim = new Animator(canvas);
        anim.start();
        
        setTitle("City Scene");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

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
        
        // Camera Set Up
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        
        // Perspective.
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        glu.gluLookAt(camera_x, camera_y, camera_z, center_x, center_y,
                center_z, up_x, up_y, up_z);
        
        // 
        createDoubleLaneLine(gl);
        createGreenFields(gl);
        createZebraCrossing(gl);
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        GL gl = drawable.getGL();
        setCamera(gl, glu); 
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glRotatef(90, 1, 0, 0);
        gl.glCallList(1); // Creates Double Lane Line
        gl.glCallList(2); // Creates Green Fields
        gl.glCallList(3); // Creates Zebra Crossing Box
        gl.glPopMatrix();
        
        displayCameraPositionInfo(drawable);
        gl.glFlush();
    }//end of display()

    
    private void displayCameraPositionInfo(GLAutoDrawable drawable){
        text.beginRendering(drawable.getWidth(), drawable.getHeight());
        text.setColor(new Color(255, 255, 255)); // White

        text.draw("Camera x: " + form.format(camera_x) + "  y: "
                + form.format(camera_y) + "  z: " + form.format(camera_z)
                + "  pan: " + form.format(angle), 10, 10);

        text.endRendering();
        text.flush();                
    }//displayCameraPositionInfo 
    
    private void createZebraCrossing(GL gl) {
        
        int index = gl.glGenLists(3);        
        gl.glNewList(index, GL.GL_COMPILE);
        
        // ZEBRA CROSSING ( pedestrian crossing )
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(1.0f, 1.0f, 1.0f);  // white
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
        gl.glColor3f(1.0f, 1.0f, 1.0f);  // white

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
        gl.glColor3f(1.0f, 1.0f, 1.0f);  // white

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
        gl.glColor3f(1.0f, 1.0f, 1.0f);  // white

        for (int i = 1; i <= blocks; i++) {
            gl.glVertex3f(1.2f, y1, 0.0f);
            gl.glVertex3f(1.2f, y2, 0.0f);
            gl.glVertex3f(1.5f, y2, 0.0f);
            gl.glVertex3f(1.5f, y1, 0.0f);

            y1 = (float) (y1 - interval);
            y2 = (float) (y2 - interval);

        }// end of for loop
        gl.glEnd();
        
        // Weast Crossing
        y1 = (float) -0.9;
        y2 = (float) -0.7;

        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f);  // white

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
        
        gl.glBegin(GL.GL_QUADS); // Four vertice

        gl.glColor3f(0.0f, 0.3f, 0.0f);  // Dark Green

        
            gl.glRotated(center_z, 1.0, 0.0, 0.0);
        
        // upper left field
        gl.glVertex3f(-1.0f, 6.0f, 0f);// coordinates x, y, z 
        gl.glVertex3f(-6.0f, 6.0f, 0f);
        gl.glVertex3f(-6.0f, 1.0f, 0f);
        gl.glVertex3f(-1.0f, 1.0f, 0f);
        
        

        // lower left field
        gl.glVertex3f(-1.0f, -6.0f, 0f);// coordinates x, y, z 
        gl.glVertex3f(-6.0f, -6.0f, 0f);
        gl.glVertex3f(-6.0f, -1.0f, 0f);
        gl.glVertex3f(-1.0f, -1.0f, 0f);

        // lower right field
        gl.glVertex3f(1.0f, -6.0f, 0f);
        gl.glVertex3f(6.0f, -6.0f, 0f); // coordinates x, y, z        
        gl.glVertex3f(6.0f, -1.0f, 0f);
        gl.glVertex3f(1.0f, -1.0f, 0f);

        // upper right field
        gl.glVertex3f(1.0f, 6.0f, 0f);
        gl.glVertex3f(6.0f, 6.0f, 0f); // coordinates x, y, z        
        gl.glVertex3f(6.0f, 1.0f, 0f);
        gl.glVertex3f(1.0f, 1.0f, 0f);


            
        gl.glEnd();
        gl.glEndList();
    }// end of create Green Fields.
    
    private void createDoubleLaneLine(GL gl) {       
        // create one display list
        int index = gl.glGenLists(1);        
        gl.glNewList(index, GL.GL_COMPILE);
        
        // North
        // ***************************************
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(2.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(0.0f, 1.8f, 0.0f);
        gl.glVertex3f(0.0f, 6.0f, 0.0f);      
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(-0.1f, 1.8f, 0.0f);
        gl.glVertex3f(-0.1f, 6.0f, 0.0f);      
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White
        gl.glVertex3f( 0.9f, 1.7f, 0.0f);
        gl.glVertex3f(-0.9f, 1.7f, 0.0f);      
        gl.glEnd();
        
        // South
        // ***************************************
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(2.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(0.0f, -1.8f, 0.0f);
        gl.glVertex3f(0.0f, -6.0f, 0.0f);      
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(-0.1f, -1.8f, 0.0f);
        gl.glVertex3f(-0.1f, -6.0f, 0.0f);      
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White
        gl.glVertex3f( 0.9f, -1.7f, 0.0f);
        gl.glVertex3f(-0.9f, -1.7f, 0.0f);      
        gl.glEnd();
        
        
        // EAST
        // ***************************************
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(2.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(1.8f, 0.0f, 0.0f);
        gl.glVertex3f(6.0f, 0.0f, 0.0f);      
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(1.8f, -0.1f, 0.0f);
        gl.glVertex3f(6.0f, -0.1f, 0.0f);      
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White
        gl.glVertex3f(1.7f,  0.9f, 0.0f);
        gl.glVertex3f(1.7f, -0.9f, 0.0f);      
        gl.glEnd();
        
        // WEST
        // ***************************************
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(2.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(-1.8f, 0.0f, 0.0f);
        gl.glVertex3f(-6.0f, 0.0f, 0.0f);      
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(-1.8f, -0.1f, 0.0f);
        gl.glVertex3f(-6.0f, -0.1f, 0.0f);      
        gl.glEnd();    
        
        
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glLineWidth(3.5f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White
        gl.glVertex3f(-1.7f,  0.9f, 0.0f);
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
        /*
         * For panning, the rotation call needs to be before the resetiing of 
         * the camera 
         */
        gl.glRotatef(angle, 0.f, 1.f, 0.f);/* orbit the Y axis */
        glu.gluLookAt(camera_x, camera_y, camera_z, center_x, center_y,
                center_z, up_x, up_y, up_z);
       
        
        // Change back to model view matrix.
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        
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
        
        if (ke.getKeyCode() == KeyEvent.VK_UP) {
            // Moves camera FORWARD
            camera_z+= 0.1;
            center_z+= 0.1;
        }
        
        if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            // Moves camera BACK
            camera_z-= 0.1;
            center_z-= 0.1;
        }
        
        
        if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            // Moves camera to the LEFT
            camera_x-= 0.1;
            center_x-= 0.1;
        }
        
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Moves camera to the RIGHT
            camera_x+= 0.1;
            center_x+= 0.1;


             
        }        
        
        if (keyString.equals("x")){
             center_x+=0.5;
        }
        
       if (keyString.equals("l")){
             // Pan left
           if ((angle <= 180) && (angle > -180)){
               angle -= 1f;
           }

        }
       if (keyString.equals("r")){
             // Pan right
           if ((angle >= -180) && (angle < 180)){
             angle += 1f;             
           }
        }       
        if (keyString.equals("h")){
            
            angle=0;
        }
       
       
        if (keyString.equals("y")){
            camera_y+=0.1;
            //center_y-= camera_y;


        }

        if (keyString.equals("z")){
            camera_z+=0.1;
        }
        

        if (keyString.equals("a")){
            camera_x-=0.1;
        }
        if (keyString.equals("b")){
            //camera_y++;
            camera_y-=0.1;
        }
        if (keyString.equals("c")){
            camera_z-=0.1;
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
