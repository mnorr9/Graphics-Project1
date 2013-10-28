package com.group2;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class Store {
	
	/**
	 * This method draws the main store structure.
	 * 
	 * @param drawable
	 */
	public static void drawStore(GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();	
				
		gl.glPushMatrix();
		
		// draw main store builing
		Building.building(drawable,2.0f,1.0f,0.5f);
		
		//draw door
		gl.glTranslated(0.4, 0.0, 0.0);
		drawDoor(drawable);
			
		//draw front windows
		gl.glTranslated(-0.8, 0.0, 0.0);
		drawStoreWindow(drawable);
		
		gl.glTranslated(1.2, 0.0, 0.0);
		drawStoreWindow(drawable);
		
		gl.glPopMatrix();
	}
	
	/**
     * this method draw the door of the store
     * 
     * @param drawable

     */
	private static void drawDoor(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        
        gl.glRotatef(-90, 1, 0, 0);
        gl.glTranslated(1.5, -1.5, -.01);
        
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f);  // white

        gl.glVertex3f(-0.9f, 1.2f, 0.0f);
        gl.glVertex3f(-0.7f, 1.2f, 0.0f);
        gl.glVertex3f(-0.7f, 1.5f, 0.0f);
        gl.glVertex3f(-0.9f, 1.5f, 0.0f);
        
        gl.glTranslated(0.5, 0.0, 0.0);
        
        gl.glColor3f(0.0f, 0.0f, 0.0f);  // black for shadow

        gl.glVertex3f(-1.1f, 1.2f, 0.0f);
        gl.glVertex3f(-0.9f, 1.2f, 0.0f);
        gl.glVertex3f(-0.9f, 1.5f, 0.0f);
        gl.glVertex3f(-1.1f, 1.5f, 0.0f);

        gl.glEnd();

	}
	
	 /**
     * this method draws the windows of the building
     * 
     * @param drawable
     */
	
	/**
	 * This method draws the windows on the front of the store.
	 * @param drawable
	 */
	private static void drawStoreWindow(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.glPushMatrix();
	     
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(0.74902f, 0.847059f, 0.847059f);  // light blue

        //**********************************
        // Draw front windows 
        //**********************************        
        float x1 = (float) -0.8;
        float x2 = (float) -0.6;
        float y1 = (float) 1.2;
        float y2 = (float) 1.4;
             
        gl.glVertex3f(x1, y1, 0.0f);
        gl.glVertex3f(x2, y1, 0.0f);
        gl.glVertex3f(x2, y2, 0.0f);
        gl.glVertex3f(x1, y2, 0.0f);
              	
        gl.glEnd();
        gl.glPopMatrix();
	}

}


