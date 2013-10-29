package cityscene;


import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
*
* @author Mike Norris
*/


public class Building {
 
    
    /**
* This method draws the main tall building structure.
*
* @param drawable
*/
    public void drawBuilding(GLAutoDrawable drawable)
    {
            GL gl = drawable.getGL();

            // Draw main building structure
            building(drawable,1,3,3);

            gl.glPushMatrix();
        gl.glRotatef(-90, 1, 0, 0);

            //***********************************************
            // Draw door
            drawDoor(drawable);
            //***********************************************

            //***********************************************
            // Draw front window
            drawFrontWindow(drawable);
            //***********************************************
            gl.glPopMatrix();
            
            //***********************************************
            // Draw side windows

            drawLeftSideWindow(drawable);
            //***********************************************

            //***********************************************
            // Draw right windows
            drawRightSideWindow(drawable);
            //***********************************************
    }
    
    /**
* This method draws the windows on the left of the building.
*
* @param drawable
*/
    private void drawLeftSideWindow(GLAutoDrawable drawable) {
            //left side
            
            GL gl = drawable.getGL();
            gl.glPushMatrix();
            //gl.glTranslated(0.0, 0.0, -0.05);
            gl.glRotatef(90, 0, 1, 0);
            gl.glTranslated(1.0, 1.0, -0.01);
            
        float x1 = (float) -1.8;
        float x2 = (float) -1.5;
            float y1 = (float) -0.8;
        float y2 = (float) -0.6;
        float interval = (float) -0.4;

        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(0.74902f, 0.847059f, 0.847059f); // light blue

        float interval2 = (float) -0.5;//floor
        int windows = 6;
        int floors =5;

        
        for (int j = 1; j <= floors; j++) {
                for (int i = 1; i <= windows; i++) {
                
         gl.glVertex3f(x1, y1, 0.0f);
         gl.glVertex3f(x2, y1, 0.0f);
         gl.glVertex3f(x2, y2, 0.0f);
         gl.glVertex3f(x1, y2, 0.0f);
        
         x1 = (float) (x1 + interval);
         x2 = (float) (x2 + interval);
        
         if(i==windows){
                 x1 = (float) -1.8;
         x2 = (float) -1.5;
         }
                }// end of for loop
                y1 = (float) (y1 - interval2);
            y2 = (float) (y2 - interval2);
        }// end of for loop


        gl.glEnd();
        gl.glPopMatrix();
        }
    
    /**
* This method draws the windows on the right of the building.
*
* @param drawable
*/
    private void drawRightSideWindow(GLAutoDrawable drawable) {

            //right side
            GL gl = drawable.getGL();
            gl.glPushMatrix();

            gl.glTranslated(1.01, 0.0, 0.01);
            gl.glRotatef(-90, 0, 1, 0);
            gl.glTranslated(1.0, 1.0, 0.00);
            
            float y1 = (float) -0.8;
        float y2 = (float) -0.6;
        float x1 = (float) -0.5;
        float x2 = (float) -0.2;
        float interval = (float) -0.4;


        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(0.74902f, 0.847059f, 0.847059f); // light blue

        float interval2 = (float) -0.5;//floor
        int windows = 6;
        int floors =5;
        
        for (int j = 1; j <= floors; j++) {
                for (int i = 1; i <= windows; i++) {
                
         gl.glVertex3f(x1, y1, 0.0f);
         gl.glVertex3f(x2, y1, 0.0f);
         gl.glVertex3f(x2, y2, 0.0f);
         gl.glVertex3f(x1, y2, 0.0f);
        
         x1 = (float) (x1 - interval);
         x2 = (float) (x2 - interval);
        
         if(i==windows){
                 x1 = (float) -0.5;
         x2 = (float) -0.2;
         }
                }// end of for loop
                y1 = (float) (y1 - interval2);
            y2 = (float) (y2 - interval2);
        }// end of for loop

        gl.glEnd();
      
        gl.glPopMatrix();

        }


        /**
* this cube uses vertex based normals
* @param drawable
* @param x
* @param y
* @param z
*/
    public static void cube(GLAutoDrawable drawable, float x, float y, float z){
            //x y z dimensions
        // cube started at 0 0 0
        
        GL gl = drawable.getGL();
        gl.glPushMatrix();

        
        gl.glBegin(GL.GL_QUAD_STRIP);
                                                        //front
        gl.glNormal3d(0.5, 0.5, -0.5);//n1
        gl.glVertex3f(x, y, 0.0f);//1
        gl.glNormal3d(0.5, -0.5, -0.5);//n2
        gl.glVertex3f(x, 0.0f, 0.0f);//2
        gl.glNormal3d(-0.5, 0.5, -0.5);//n3
        gl.glVertex3f(0.0f, y, 0.0f);//3
        gl.glNormal3d(-0.5, -0.5, -0.5); //n4
        gl.glVertex3f(0.0f, 0.0f, 0.0f);//4 //main point, bottom left
                                                //left
        gl.glNormal3d(-0.5, 0.5, 0.5);//n5
        gl.glVertex3f(0.0f, y, z);//5
        gl.glNormal3d(-0.5, -0.5, 0.5);//n6
        gl.glVertex3f(0.0f, 0.0f, z);//6
                                                //back
        gl.glNormal3d(0.5, 0.5, 0.5); //n7
        gl.glVertex3f(x, y, z);//7
        gl.glNormal3d(0.5, -0.5, 0.5);//n8
        gl.glVertex3f(x, 0.0f, z);//8
                                                //right
        gl.glNormal3d(0.5, 0.5, -0.5);//n1
        gl.glVertex3f(x, y, 0.0f);//1
        gl.glVertex3f(x, 0.0f, 0.0f);//2
        //gl.glVertex3f(x, 0.0f, 0.0f);//2
        gl.glEnd();
        //top and bottom
        gl.glBegin(GL.GL_QUADS);
                                                //top
        gl.glNormal3d(0.5, 0.5, -0.5);//n1
        gl.glVertex3f(x, y, 0.0f);//1
        gl.glNormal3d(0.5, 0.5, 0.5); //n7
        gl.glVertex3f(x, y, z);//7
        gl.glNormal3d(-0.5, 0.5, 0.5);//n5
        gl.glVertex3f(0.0f, y, z);//5
        gl.glNormal3d(-0.5, 0.5, -0.5);//n3
        gl.glVertex3f(0.0f, y, 0.0f);//3
                                                //bottom
        gl.glNormal3d(0.5, -0.5, -0.5);//n2
        gl.glVertex3f(x, 0.0f, 0.0f);//2
        gl.glNormal3d(-0.5, -0.5, -0.5); //n4
        gl.glVertex3f(0.0f, 0.0f, 0.0f);//4
        gl.glNormal3d(-0.5, -0.5, 0.5);//n6
        gl.glVertex3f(0.0f, 0.0f, z);//6
        gl.glNormal3d(0.5, -0.5, 0.5);//n8
        gl.glVertex3f(x, 0.0f, z);//8
        gl.glEnd();
        gl.glPopMatrix();
    }//end of cube
    
    /**
* this cube uses vertex based normals
* @param drawable
* @param x
* @param y
* @param z
*/
    public static void building(GLAutoDrawable drawable, float x, float y, float z){
            //x y z dimensions
        // cube started at 0 0 0
        
        GL gl = drawable.getGL();
        gl.glPushMatrix();

        
        gl.glBegin(GL.GL_QUAD_STRIP);
        
        // brown
        gl.glColor3f(0.36f, 0.25f, 0.20f);

                                                                //front
        gl.glNormal3d(0.5, 0.5, -0.5);//n1
        gl.glVertex3f(x, y, 0.0f);//1
        gl.glNormal3d(0.5, -0.5, -0.5);//n2
        gl.glVertex3f(x, 0.0f, 0.0f);//2
        gl.glNormal3d(-0.5, 0.5, -0.5);//n3
        gl.glVertex3f(0.0f, y, 0.0f);//3
        gl.glNormal3d(-0.5, -0.5, -0.5); //n4
        gl.glVertex3f(0.0f, 0.0f, 0.0f);//4 //main point, bottom left
                                                          //left
        gl.glNormal3d(-0.5, 0.5, 0.5);//n5
        gl.glVertex3f(0.0f, y, z);//5
        gl.glNormal3d(-0.5, -0.5, 0.5);//n6
        gl.glVertex3f(0.0f, 0.0f, z);//6
                                                //back
        gl.glNormal3d(0.5, 0.5, 0.5); //n7
        gl.glVertex3f(x, y, z);//7
        gl.glNormal3d(0.5, -0.5, 0.5);//n8
        gl.glVertex3f(x, 0.0f, z);//8
                                                //right
        gl.glNormal3d(0.5, 0.5, -0.5);//n1
        gl.glVertex3f(x, y, 0.0f);//1
        gl.glVertex3f(x, 0.0f, 0.0f);//2
        //gl.glVertex3f(x, 0.0f, 0.0f);//2
        gl.glEnd();
        //top and bottom
        gl.glBegin(GL.GL_QUADS);
                                                //top
        gl.glNormal3d(0.5, 0.5, -0.5);//n1
        gl.glVertex3f(x, y, 0.0f);//1
        gl.glNormal3d(0.5, 0.5, 0.5); //n7
        gl.glVertex3f(x, y, z);//7
        gl.glNormal3d(-0.5, 0.5, 0.5);//n5
        gl.glVertex3f(0.0f, y, z);//5
        gl.glNormal3d(-0.5, 0.5, -0.5);//n3
        gl.glVertex3f(0.0f, y, 0.0f);//3
                                                //bottom
        gl.glNormal3d(0.5, -0.5, -0.5);//n2
        gl.glVertex3f(x, 0.0f, 0.0f);//2
        gl.glNormal3d(-0.5, -0.5, -0.5); //n4
        gl.glVertex3f(0.0f, 0.0f, 0.0f);//4
        gl.glNormal3d(-0.5, -0.5, 0.5);//n6
        gl.glVertex3f(0.0f, 0.0f, z);//6
        gl.glNormal3d(0.5, -0.5, 0.5);//n8
        gl.glVertex3f(x, 0.0f, z);//8
        gl.glEnd();


        
        gl.glPopMatrix();
    }//end of cube


    /**
* this method draw the door of the building
* @param drawable

*/
        private static void drawDoor(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glPushMatrix();

        gl.glTranslated(1.5, -1.5, 0.0);
        gl.glTranslated(0.0, 0.0, -0.01);
        
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // white

        gl.glVertex3f(-0.9f, 1.2f, 0.0f);
        gl.glVertex3f(-0.7f, 1.2f, 0.0f);
        gl.glVertex3f(-0.7f, 1.5f, 0.0f);
        gl.glVertex3f(-0.9f, 1.5f, 0.0f);
        
        gl.glTranslated(0.5, 0.0, 0.0);
        
        gl.glColor3f(0.0f, 0.0f, 0.0f); // black for shadow

        gl.glVertex3f(-1.1f, 1.2f, 0.0f);
        gl.glVertex3f(-0.9f, 1.2f, 0.0f);
        gl.glVertex3f(-0.9f, 1.5f, 0.0f);
        gl.glVertex3f(-1.1f, 1.5f, 0.0f);

        gl.glEnd();
        gl.glPopMatrix();
        }
        
    /**
* this method draws the windows of the building
*
* @param drawable
*/
        private static void drawFrontWindow(GLAutoDrawable drawable) {
                GL gl = drawable.getGL();
                gl.glPushMatrix();
                gl.glTranslated(1.5, -2.0, -0.01);
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(0.74902f, 0.847059f, 0.847059f); // light blue

        //**********************************
        // Draw front windows
        //**********************************
        float x1 = (float) -0.8;
        float x2 = (float) -0.6;
        float y1 = (float) 1.2;
        float y2 = (float) 1.4;
        float interval = (float) -0.4;//window spacing
        float interval2 = (float) -0.5;//floor
        int windows = 2;
        int floors = 5;
        
            for (int j = 1; j <= floors; j++) {
                for (int i = 1; i <= windows; i++) {
                
         gl.glVertex3f(x1, y1, 0.0f);
         gl.glVertex3f(x2, y1, 0.0f);
         gl.glVertex3f(x2, y2, 0.0f);
         gl.glVertex3f(x1, y2, 0.0f);
        
         x1 = (float) (x1 + interval);
         x2 = (float) (x2 + interval);
        
         if(i==windows){
                 x1 = (float) -0.8;
         x2 = (float) -0.6;
         }
                }// end of for loop
                y1 = (float) (y1 + interval2);
            y2 = (float) (y2 + interval2);
        }// end of for loop
            
        gl.glEnd();
        gl.glPopMatrix();
        }
        

}
