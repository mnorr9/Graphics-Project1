package cityscene;


import javax.media.opengl.GL;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.glu.GLU;


public class SpCar {
	private static final long serialVersionUID = 1L;
	
	private GLCanvas canvas;
    private GLU glu;
    
	//public Car() {

		public void createCylinder(GL gl, float radius, float height, float theta){
			float PI = 3.1415f;
			float radian, r, h, t;
    	
			r = radius; h = height; t = theta;
			gl.glBegin(GL.GL_TRIANGLE_FAN);
        		gl.glColor3f(0.0f, 0.0f, 0.0f); // black
        		gl.glVertex3f(0.0f, h, 0.0f);
        		gl.glVertex3f(r, h, 0.0f); 
        		while (t <= 360) {
        			radian = (float)(PI * t / 180.0);
        			gl.glVertex3f((float)(r * Math.cos(radian)), 0f, (float)(r * Math.sin(radian)));
        			t = t + theta;
        		}
        	gl.glEnd(); 

        	r = radius; h = height; t = theta;
        	gl.glBegin(GL.GL_TRIANGLE_FAN);
        		gl.glColor3f(0.0f, 0.0f, 0.0f); // black
        		gl.glVertex3f(0.0f, 0.0f, 0.0f);
        		gl.glVertex3f(r, 0.0f, 0.0f); 
        		while (t <= 360) {
        			radian = (float)(PI * t / 180.0);
        			gl.glVertex3f((float)(r * Math.cos(radian)), h, (float)(r * Math.sin(radian)));
        			t = t + theta;
        		} 
        	gl.glEnd();

        	r = radius; h = height; t = theta;
        	gl.glBegin(GL.GL_QUAD_STRIP);
        		gl.glVertex3f(r, 0.0f, 0.0f);
        		gl.glVertex3f(r, h, 0.0f);
        		while (t <= 360) {
        			radian = (float)(PI * t / 180.0);
        			gl.glVertex3f((float)(r * Math.cos(radian)), 0.0f, (float)(r * Math.sin(radian)));
        			gl.glVertex3f((float)(r * Math.cos(radian)), h, (float)(r * Math.sin(radian)));
        			t = t + theta;
        		}
        	gl.glEnd();
		}
    
		public void createPassengerGrayCircle(GL gl, float radius, float height, float theta){
			float PI = 3.1415f;
			float radian, r, h, t;
    	
			r = radius; h = height; t = theta;
			gl.glBegin(GL.GL_TRIANGLE_FAN);
        		gl.glColor3f(0.5f, 0.5f, 0.5f); // black
        		gl.glVertex3f(0.0f, 0.0f, 0.0f);
        		gl.glVertex3f(r, 0.0f, 0.0f); 
        		while (t <= 360) {
        			radian = (float)(PI * t / 180.0);
        			gl.glVertex3f((float)(r * Math.cos(radian)), 0f, (float)(r * Math.sin(radian)));
        			t = t + theta;
        		}
            gl.glEnd(); 
		}
    
		public void createPassengerBlackCircle(GL gl, float radius, float height, float theta){
			float PI = 3.1415f;
			float radian, r, h, t;
    	
			r = radius; h = height; t = theta;
			gl.glBegin(GL.GL_TRIANGLE_FAN);
        		gl.glColor3f(0.0f, 0.0f, 0.0f); // black
        		gl.glVertex3f(0.0f, 0.0f, 0.0f);
        		gl.glVertex3f(r, 0.0f, 0.0f); 
        		while (t <= 360) {
        			radian = (float)(PI * t / 180.0);
        			gl.glVertex3f((float)(r * Math.cos(radian)), 0f, (float)(r * Math.sin(radian)));
        			t = t + theta;
        		}
        	gl.glEnd(); 
		}

		public void createCircle(GL gl, float radius, float height, float theta){
			float PI = 3.1415f;
			float radian, r, h, t;
    	
			r = radius; h = height; t = theta;
			gl.glBegin(GL.GL_TRIANGLE_FAN);
        		gl.glVertex3f(0.0f, 0.0f, 0.0f);
        		gl.glVertex3f(r, 0.0f, 0.0f); 
        		while (t <= 360) {
        			radian = (float)(PI * t / 180.0);
        			gl.glVertex3f((float)(r * Math.cos(radian)), 0f, (float)(r * Math.sin(radian)));
        			t = t + theta;
        		}
        	gl.glEnd(); 
		}

		public void createSpokes(GL gl, float length){
			gl.glBegin(GL.GL_TRIANGLES);
        		gl.glLineWidth(122.5f);
        		gl.glColor3f(0.5f, 0.5f, 0.5f); // gray
        		gl.glVertex3f(0.0f, 0.0f, 0.0f);
        		gl.glVertex3f(0.3f, 0.0f, (length-0.13f));      
        		gl.glVertex3f(-0.3f, 0.0f, (length-0.13f));      
        	gl.glEnd();
		}

		public void createWheel(GL gl) {
			gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
			createCylinder(gl, 2, 1, 4);
			createPassengerGrayCircle(gl, 1.8f, 1, 5);
			createPassengerBlackCircle(gl, 1.7f, 1, 5);
			createPassengerGrayCircle(gl, 0.4f, 1, 5);
	   		int t = 0;
	   		while (t <= 360){
	   			gl.glRotatef(t, 0.0f, 1.0f, 0.0f);
	   			createSpokes(gl, 1.8f);
	   			t = t + 30;
	   		}
		}
		private float spin = 0;
		public void create4Wheels(GL gl) {
			gl.glPushMatrix();
	    	gl.glRotatef(spin, 0f,0f, 1f);
	    	createWheel(gl);
	    	gl.glPopMatrix();
	        gl.glPushMatrix();
	        	gl.glRotatef(180, 1.0f,  0.0f, 0.0f);
	        	gl.glTranslatef(0.0f, 0.0f, -10.0f);
	        	gl.glRotatef(spin, 0f,0f, 1f);
	        	createWheel(gl);
	        gl.glPopMatrix();
	        gl.glPushMatrix();
	        	//gl.glRotatef(-90,  1.0f, 0.0f, 0.0f);
	        	gl.glTranslatef(13.0f, 0.0f, 0.0f);
	        	gl.glPushMatrix();
	        	gl.glRotatef(spin, 0f,0f, 1f);
	        	createWheel(gl);
	        	gl.glPopMatrix();
	        	gl.glRotatef(180,  1.0f, 0.0f, 0.0f);
	        	gl.glTranslatef(0.0f, 0.0f, -10.0f);
	        	gl.glRotatef(spin, 0f,0f, 1f);
	        	createWheel(gl);
	        gl.glPopMatrix();
	 		spin-=1;

		}

		public void createTrunk(GL gl) {
			gl.glColor3f(1.0f, 1.0f,  0.0f);
			gl.glBegin(GL.GL_POLYGON);
    			gl.glVertex3f(-3.0f, -1.0f, 0.0f);
    			gl.glVertex3f(-3.0f, -1.0f, 10.0f);
    			gl.glVertex3f(-3.5f, 2.0f, 10.0f);
    			gl.glVertex3f(-3.0f, 3.5f, 10.0f);
    			gl.glVertex3f(-3.0f, 3.5f, 0.0f);
    			gl.glVertex3f(-3.5f, 2.0f, 0.0f);
    		gl.glEnd();
    		gl.glColor3f(0.0f,  0.0f,  0.0f);
    		gl.glBegin(GL.GL_POLYGON);
				gl.glVertex3f(-3.0f, -1.0f, 1.0f);
				gl.glVertex3f(-3.2f, 0.0f, 2.0f);
				gl.glVertex3f(-3.2f, 0.0f, 8.0f);
				gl.glVertex3f(-3.0f, -1.0f, 9.0f);
			gl.glEnd();
			createExhaust(gl);
			createBreakLights(gl);
			gl.glColor3f(1.0f, 1.0f, 0.0f);
			createSideTrunk(gl);
			gl.glPushMatrix();
				gl.glTranslatef(0f, 0f, 10.0f);
				createSideTrunk(gl);
			gl.glPopMatrix();		
		}
    
		public void createExhaust(GL gl) {
			gl.glPushMatrix();
				gl.glColor3f(0.3f, 0.3f, 0.3f);
				gl.glTranslated(-3.2f,  -0.5f, 4.2f);
				gl.glRotatef(90, 0f, 0f, 1f);
				createCircle(gl, 0.3f, 0.3f, 5f);
				gl.glTranslated(0f, 0f, 0.7f);
				createCircle(gl, 0.3f, 0.3f, 5f);
				gl.glTranslated(0f, 0f, 0.7f);
				createCircle(gl, 0.3f, 0.3f, 5f);
				gl.glTranslated(0f, 0f, 0.7f);
				createCircle(gl, 0.3f, 0.3f, 5f);
			gl.glPopMatrix();
		}
    
		public void createBreakLights(GL gl) {
			gl.glPushMatrix();
				gl.glColor3f(1.0f, 0.0f, 0.0f);
				gl.glTranslated(-3.2f,  2.6f, 1.0f);
				gl.glRotatef(75, 0f, 0f, 1f);
				createCircle(gl, 0.7f, 0.7f, 5f);
				gl.glTranslated(0f, 0f, 1.7f);
				createCircle(gl, 0.7f, 0.7f, 5f);
				gl.glTranslated(0f, 0f, 4.7f);
				createCircle(gl, 0.7f, 0.7f, 5f);
				gl.glTranslated(-0.1f, 0.1f, 1.7f);
				createCircle(gl, 0.7f, 0.7f, 5f);
			gl.glPopMatrix();
		}
    
		public void createSideTrunk(GL gl) {
			gl.glPushMatrix();
			gl.glBegin(GL.GL_QUAD_STRIP);
        		gl.glVertex3f(-3.0f, -1.0f, 0.0f);   //Group 1
        		gl.glVertex3f(-2.0f, -1.0f, 0.0f);   //wp0
        		gl.glVertex3f(-3.2f, 0.0f, 0.0f);
        		gl.glVertex3f(-2.2f, -0.2f, 0.0f);   //wp1
        		gl.glVertex3f(-3.5f, 2.0f, 0.0f);    //Group 2
        		gl.glVertex3f(-2.0f, 1.0f, 0.0f);    //wp2
        		gl.glVertex3f(-3.0f, 3.5f, 0.0f);    //Group 3
        		gl.glVertex3f(-1.2f, 1.9f, 0.0f);    //wp3
        		gl.glVertex3f(0.0f, 3.5f, 0.0f);     //Group 4
        		gl.glVertex3f(0.0f, 2.2f, 0.0f);     //wp4
        		gl.glVertex3f(2.5f, 3.3f, 0.0f);     //Group 5
        		gl.glVertex3f(1.2f, 1.9f, 0.0f);
        		gl.glVertex3f(2.5f, 1.7f, 0.0f);     //Group 6
        		gl.glVertex3f(2.0f, 1.0f, 0.0f);
        		gl.glVertex3f(4.0f, 0.2f, 0.0f);     //Group 7
        		gl.glVertex3f(2.2f, -0.2f, 0.0f);
        		gl.glVertex3f(4.0f, -1.0f, 0.0f);    //Group 8
        		gl.glVertex3f(2.0f, -1.0f, 0.0f);
        	gl.glEnd();
        	gl.glPopMatrix();
		}

		public void createFrontSide(GL gl) {
			gl.glPushMatrix();
				gl.glBegin(GL.GL_QUAD_STRIP);
					gl.glVertex3f(8.0f, -1.0f, 0.0f);   //Group 1
					gl.glVertex3f(11f, -1.0f, 0.0f);   //wp0
					gl.glVertex3f(8.0f, 1.0f, 0.0f);
					gl.glVertex3f(10.8f, -0.2f, 0.0f);   //wp1
					gl.glVertex3f(8.0f, 2.9f, 0.0f);    //Group 2
					gl.glVertex3f(11.0f, 1.0f, 0.0f);    //wp2
					gl.glVertex3f(11.0f, 2.9f, 0.0f);    //Group 3
					gl.glVertex3f(11.8f, 1.9f, 0.0f);    //wp3
					gl.glVertex3f(13.0f, 3.1f, 0.0f);     //Group 4
					gl.glVertex3f(13.0f, 2.2f, 0.0f);     //wp4
					gl.glVertex3f(14.2f, 2.9f, 0.0f);     //Group 5
					gl.glVertex3f(14.0f, 1.9f, 0.0f);
					gl.glVertex3f(16.0f, 1.7f, 0.0f);     //Group 6
					gl.glVertex3f(15.0f, 1.0f, 0.0f);
					gl.glVertex3f(18.0f, 0.2f, 0.0f);     //Group 7
					gl.glVertex3f(15.2f, -0.2f, 0.0f);
					gl.glVertex3f(18.0f, -1.0f, 0.0f);    //Group 8
					gl.glVertex3f(15.0f, -1.0f, 0.0f); 
				gl.glEnd();
			gl.glPopMatrix();
		}
    
		private void createHood(GL gl){
			gl.glBegin(GL.GL_QUAD_STRIP);
				gl.glVertex3f(8.0f, 2.9f, 0.0f);
				gl.glVertex3f(8.0f, 2.9f, 10.0f);
				gl.glVertex3f(11.8f, 2.9f, 0.0f);
				gl.glVertex3f(11.8f, 2.9f, 10.0f);
				gl.glVertex3f(13.0f, 3.1f, 0.0f);
				gl.glVertex3f(13.0f, 3.1f, 10.0f);
				gl.glVertex3f(14.2f, 2.9f, 0.0f);
				gl.glVertex3f(14.2f, 2.9f, 10.0f);
				gl.glVertex3f(16.0f, 1.7f, 0.0f);
				gl.glVertex3f(16.0f, 1.7f, 10.0f);
				gl.glVertex3f(18.0f, 0.2f, 0.0f);
				gl.glVertex3f(18.0f, 0.2f, 10.0f);
				gl.glVertex3f(18.0f, -1.0f, 0.0f);
				gl.glVertex3f(18.0f, -1.0f, 10.0f); 	
			gl.glEnd();
		}
    
		private void createHeadLights(GL gl){
			gl.glColor3f(1.0f, 1.0f, 1.0f);
			gl.glBegin(GL.GL_QUADS);
				gl.glVertex3f(15.8f, 1.87f, 0.2f);
				gl.glVertex3f(16.6f, 1.3f, 0.4f);
				gl.glVertex3f(16.8f, 1.2f, 1.8f); 	
				gl.glVertex3f(16.0f, 1.83f, 0.8f); 	    	
			gl.glEnd();    	
			gl.glBegin(GL.GL_QUADS);
				gl.glVertex3f(15.8f, 1.87f, 9.8f);
				gl.glVertex3f(16.6f, 1.3f, 9.6f);
				gl.glVertex3f(16.8f, 1.2f, 8.2f); 	
				gl.glVertex3f(16.0f, 1.83f, 9.2f); 	    	
			gl.glEnd();    	
		}
    
		private void createFront(GL gl){
			createHeadLights(gl);
			gl.glColor3f(1.0f,1.0f,0.0f);
			createHood(gl);
			createFrontSide(gl);
			gl.glPushMatrix();
    			gl.glTranslatef(0f, 0f, 10f);
    			createFrontSide(gl);
    		gl.glPopMatrix();
    		createGrill(gl);
		}

		public void getCirclePoint(GL gl, float theta) {
			float PI = 3.1415f;
			float radian, r, h, t;
			r = 2.0f; h = 2.0f; t = 20f;
       	
			radian = (float)(PI * t / 180.0);
			gl.glVertex3f((float)(r * Math.cos(radian)), 0f, (float)(r * Math.sin(radian)));
			t = t + theta;
		}
		
		private void createRearWindow (GL gl) {
			gl.glPushMatrix();
				gl.glColor3f(0f, 0f, 0f);
				gl.glBegin(GL.GL_TRIANGLES);
					gl.glVertex3f(-1.0f, 3.5f, 0.0f);
					gl.glVertex3f(2.0f, 4.5f, 0.0f);
					gl.glVertex3f(2.0f, 3.3f, 0.0f);
				gl.glEnd();
			gl.glPushMatrix();
				gl.glTranslatef(0f,0f,10f);
				gl.glBegin(GL.GL_TRIANGLES);
					gl.glVertex3f(-1.0f, 3.5f, 0.0f);
					gl.glVertex3f(2.0f, 4.5f, 0.0f);
					gl.glVertex3f(2.0f, 3.3f, 0.0f);
				gl.glEnd();
			gl.glPopMatrix();
		
			gl.glBegin(GL.GL_QUAD_STRIP);
				gl.glVertex3f(-1.0f, 3.5f, 0.0f);
				gl.glVertex3f(-1.0f, 3.5f, 10.0f);
				gl.glVertex3f(2.0f, 4.5f, 0.0f);
				gl.glVertex3f(2.0f, 4.5f, 10.0f);
			gl.glEnd();
			gl.glPopMatrix();
			gl.glPushMatrix();
				gl.glBegin(GL.GL_QUAD_STRIP);
					gl.glColor3f(1.0f, 1.0f, 0.0f);
					gl.glVertex3f(-1.0f, 3.5f, 0.0f);
					gl.glVertex3f(-1.0f, 3.5f, 10.0f);
					gl.glVertex3f(-3.0f, 3.5f, 0.0f);
					gl.glVertex3f(-3.0f, 3.5f, 10.0f);			
				gl.glEnd();
			gl.glPopMatrix();
		}
	
		private void createRoof(GL gl) {
			gl.glPushMatrix();
				gl.glBegin(GL.GL_QUAD_STRIP);
					gl.glColor3f(1.0f, 1.0f, 0.0f);
					gl.glVertex3f(5.0f, 4.5f, 0.0f);
					gl.glVertex3f(5.0f, 4.5f, 10.0f);
					gl.glVertex3f(2.0f, 4.5f, 0.0f);
					gl.glVertex3f(2.0f, 4.5f, 10.0f);			
				gl.glEnd();
			gl.glPopMatrix();	 
			gl.glPushMatrix();
				gl.glBegin(GL.GL_QUAD_STRIP);
					gl.glColor3f(1.0f, 1.0f, 0.0f);
					gl.glVertex3f(2.5f, 3.3f, 0.0f);
					gl.glVertex3f(2.5f, 4.5f, 0.0f);
					gl.glVertex3f(2.0f, 3.3f, 0.0f);
					gl.glVertex3f(2.0f, 4.5f, 0.0f);			
				gl.glEnd();
				gl.glTranslatef(0f, 0f, 10f);
				gl.glBegin(GL.GL_QUAD_STRIP);
					gl.glVertex3f(2.5f, 3.3f, 0.0f);
					gl.glVertex3f(2.5f, 4.5f, 0.0f);
					gl.glVertex3f(2.0f, 3.3f, 0.0f);
					gl.glVertex3f(2.0f, 4.5f, 0.0f);			
				gl.glEnd();
			gl.glPopMatrix();		
		}
	
		private void createWindow(GL gl) {
			gl.glColor3f(0f, 0f, 0f);
			gl.glBegin(GL.GL_QUAD_STRIP);
				gl.glVertex3f(2.5f, 3.3f, 0.0f);
				gl.glVertex3f(2.5f, 4.5f, 0.0f);
				gl.glVertex3f(8.0f, 2.9f, 0.0f);
				gl.glVertex3f(5.0f, 4.5f, 0.0f);			
			gl.glEnd();
		}
	
		private void createWindshield(GL gl) {
			gl.glColor3f(0f ,0f, 0f);
			gl.glBegin(GL.GL_QUAD_STRIP);
				gl.glVertex3f(5.0f, 4.5f, 0.0f);
				gl.glVertex3f(5.0f, 4.5f, 10.0f);
				gl.glVertex3f(8.0f, 2.9f, 0.0f);
				gl.glVertex3f(8.0f, 2.9f, 10.0f);			
			gl.glEnd();
		}
	
		private void createDoorAndWindow(GL gl) {
			gl.glColor3f(0f, 0f, 0f);
			createWindow(gl);
			gl.glColor3f(1.0f, 1.0f, 0.0f);
			createDoor(gl);
			gl.glPushMatrix();
				gl.glTranslatef(0f, 0f, 10f);
				createWindow(gl);
				gl.glColor3f(1.0f, 1.0f, 0.0f);
				createDoor(gl);
			gl.glPopMatrix();
		}
	
		private void createDoor(GL gl) {
			gl.glBegin(GL.GL_QUAD_STRIP);
				gl.glVertex3f(2.5f, 3.3f, 0.0f);
				gl.glVertex3f(8.0f, 2.9f, 0.0f);
				gl.glVertex3f(2.5f, 1.7f, 0.0f);
				gl.glVertex3f(8.0f, 1.7f, 0.0f);			
				gl.glVertex3f(4.0f, 0.2f, 0.0f);
				gl.glVertex3f(8.0f, 0.2f, 0.0f);			
				gl.glVertex3f(4.0f, -0.4f, 0.0f);
				gl.glVertex3f(8.0f, -0.4f, 0.0f);			
			gl.glEnd();
		}
	
		private void createBottomRail(GL gl){
			gl.glColor3f(1.0f, 1.0f, 0.0f);
			gl.glBegin(GL.GL_QUAD_STRIP);
				gl.glVertex3f(2.5f, -0.4f, 0.0f);
				gl.glVertex3f(8.0f, -0.4f, 0.0f);
				gl.glVertex3f(2.5f, -1.0f, 0.0f);
				gl.glVertex3f(8.0f, -1.0f, 0.0f);					
			gl.glEnd();	
			gl.glPushMatrix();
				gl.glTranslatef(0f,0f,10);
				gl.glBegin(GL.GL_QUAD_STRIP);
					gl.glVertex3f(2.5f, -0.4f, 0.0f);
					gl.glVertex3f(8.0f, -0.4f, 0.0f);
					gl.glVertex3f(2.5f, -1.0f, 0.0f);
					gl.glVertex3f(8.0f, -1.0f, 0.0f);					
				gl.glEnd();	
			gl.glPopMatrix();		
		}
	
		private void createGrill(GL gl){
			gl.glColor3f(0.0f, 0.0f, 0.0f);
			gl.glBegin(GL.GL_QUAD_STRIP);
				gl.glVertex3f(18.01f, -0.2f, 2.5f);
				gl.glVertex3f(18.01f, -0.2f, 7.5f);
				gl.glVertex3f(18.01f, -0.6f, 2.5f);
				gl.glVertex3f(18.01f, -0.6f, 7.5f); 	
			gl.glEnd();
			gl.glColor3f(1.0f, 1.0f, 1.0f);
			gl.glBegin(GL.GL_QUAD_STRIP);
				gl.glVertex3f(18.01f, -0.2f, 1.0f);
				gl.glVertex3f(18.01f, -0.2f, 1.5f);
				gl.glVertex3f(18.01f, -0.6f, 1.0f);
				gl.glVertex3f(18.01f, -0.6f, 1.5f); 	
			gl.glEnd();
			gl.glBegin(GL.GL_QUAD_STRIP);
				gl.glVertex3f(18.01f, -0.2f, 9.0f);
				gl.glVertex3f(18.01f, -0.2f, 8.5f);
				gl.glVertex3f(18.01f, -0.6f, 9.0f);
				gl.glVertex3f(18.01f, -0.6f, 8.5f); 	
			gl.glEnd();
		}
	
		public void createCar(GL gl) {
			createFront(gl);
			gl.glColor3f(0f, 1f, 0f);
			createBottomRail(gl);
			createDoorAndWindow(gl);
			createWindshield(gl);
			createRoof(gl);
			createRearWindow(gl);
			createTrunk(gl);
  	        create4Wheels(gl);
		}

                
                
}