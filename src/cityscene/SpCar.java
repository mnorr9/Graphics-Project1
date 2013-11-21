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
// Create the spokes for the car
		public void createSpokes(GL gl, float length){
			gl.glBegin(GL.GL_TRIANGLES);
        		gl.glLineWidth(122.5f);
        		gl.glColor3f(0.5f, 0.5f, 0.5f); // gray
        		gl.glVertex3f(0.0f, 0.0f, 0.0f);
        		gl.glVertex3f(0.3f, 0.0f, (length-0.13f));      
        		gl.glVertex3f(-0.3f, 0.0f, (length-0.13f));      
        	gl.glEnd();
		}
// Create the wheels for the car
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
		// Create all the wheels for the car and allow the tires to rotate
		// to simulate the tire spinning
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
		//Create the trunk of the car
		public void createTrunk(GL gl, float red, float green, float blue) {
			gl.glColor3f(red, green,  blue);
			gl.glBegin(GL.GL_POLYGON);
			    gl.glNormal3d(-1f, 0f, 0f);
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
			gl.glColor3f(red, green, blue);
			createSideTrunk(gl);
			gl.glPushMatrix();
				gl.glTranslatef(0f, 0f, 10.0f);
				createSideTrunk(gl);
			gl.glPopMatrix();		
		}
		// Create the exhaust of car
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
		// Create the break light for the car
		public void createBreakLights(GL gl) {
			gl.glPushMatrix();
				gl.glNormal3d(0f, 1f, 0f);
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
		// Create the sides that make up the trunk of the car
		public void createSideTrunk(GL gl) {
			gl.glPushMatrix();
			gl.glBegin(GL.GL_QUAD_STRIP);
				gl.glNormal3d(0f, 0f, -1f);
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
		// Create the front part of the car
		public void createFrontSide(GL gl) {
			gl.glPushMatrix();
				gl.glBegin(GL.GL_QUAD_STRIP);
				    gl.glNormal3d(0f, 0f, -1f);
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
		// Create the hood of the car
		private void createHood(GL gl){
			gl.glBegin(GL.GL_QUAD_STRIP);
			    gl.glNormal3d(1f, 1f, 0f);
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
		// Create the headlights of the car
		// Create lighting to simulate the light coming
		// from the headlights of the car
		// create it here so as to move along with the car
		private void createHeadLights(GL gl){
		    float lightPos[] = {0f, 0f , 1f};
		    float ambient[] = {1.0f, 1.0f, 1.0f};
		    float diffuse[] = {1f, 1f, 1f};
		    float specular[] = {1f, 1f, 1f};
		    float spot_direction[] = { 1.0f, -1.0f, 0.0f };


                    
		    gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos, 1);
		    gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, ambient, 1);
		    gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, specular, 1);
		    gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, diffuse, 1);
		    gl.glLightf(GL.GL_LIGHT1, GL.GL_CONSTANT_ATTENUATION, 1.5f);
		    gl.glLightf(GL.GL_LIGHT1, GL.GL_SPOT_CUTOFF, 45.0f);
		    gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPOT_DIRECTION, spot_direction, 0);
		    gl.glLightf(GL.GL_LIGHT1, GL.GL_SPOT_EXPONENT, 4.0f);

		    gl.glEnable(GL.GL_LIGHT1);


			gl.glColor3f(1.0f, 1.0f, 1.0f);
			gl.glBegin(GL.GL_QUADS);
				gl.glNormal3d(1f, 0f, 0f);
				gl.glVertex3f(15.8f, 1.87f, 0.2f);
				gl.glVertex3f(16.6f, 1.3f, 0.4f);
				gl.glVertex3f(16.8f, 1.2f, 1.8f); 	
				gl.glVertex3f(16.0f, 1.83f, 0.8f); 	    	
			gl.glEnd();    	
			gl.glBegin(GL.GL_QUADS);
				gl.glNormal3d(1f, 0f, 0f);
				gl.glVertex3f(15.8f, 1.87f, 9.8f);
				gl.glVertex3f(16.6f, 1.3f, 9.6f);
				gl.glVertex3f(16.8f, 1.2f, 8.2f); 	
				gl.glVertex3f(16.0f, 1.83f, 9.2f); 	    	
			gl.glEnd();    	
		}
		// Put all the parts together to create the front part of the car
		private void createFront(GL gl, float red, float green, float blue){
			createHeadLights(gl);
			gl.glColor3f(red, green, blue);
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
		// Create the top rear part of the car
		private void createRearWindow (GL gl, float red, float green, float blue) {
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
					gl.glColor3f(red, green, blue);
					gl.glNormal3d(0f, 1f, 0f);
					gl.glVertex3f(-1.0f, 3.5f, 0.0f);
					gl.glVertex3f(-1.0f, 3.5f, 10.0f);
					gl.glVertex3f(-3.0f, 3.5f, 0.0f);
					gl.glVertex3f(-3.0f, 3.5f, 10.0f);			
				gl.glEnd();
			gl.glPopMatrix();
		}
		// Create the roof of the car
		private void createRoof(GL gl, float red, float green, float blue) {
			gl.glPushMatrix();
				gl.glBegin(GL.GL_QUAD_STRIP);
					gl.glColor3f(red, green, blue);
					gl.glVertex3f(5.0f, 4.5f, 0.0f);
					gl.glVertex3f(5.0f, 4.5f, 10.0f);
					gl.glVertex3f(2.0f, 4.5f, 0.0f);
					gl.glVertex3f(2.0f, 4.5f, 10.0f);			
				gl.glEnd();
			gl.glPopMatrix();	 
			gl.glPushMatrix();
				gl.glBegin(GL.GL_QUAD_STRIP);
					gl.glColor3f(red, green, blue);
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
		// Create the windows for the car
		private void createWindow(GL gl) {
			gl.glColor3f(0f, 0f, 0f);
			gl.glBegin(GL.GL_QUAD_STRIP);
				gl.glVertex3f(2.5f, 3.3f, 0.0f);
				gl.glVertex3f(2.5f, 4.5f, 0.0f);
				gl.glVertex3f(8.0f, 2.9f, 0.0f);
				gl.glVertex3f(5.0f, 4.5f, 0.0f);			
			gl.glEnd();
		}
		//Create the windshield for the car
		private void createWindshield(GL gl) {
			gl.glColor3f(0f ,0f, 0f);
			gl.glBegin(GL.GL_QUAD_STRIP);
				gl.glVertex3f(5.0f, 4.5f, 0.0f);
				gl.glVertex3f(5.0f, 4.5f, 10.0f);
				gl.glVertex3f(8.0f, 2.9f, 0.0f);
				gl.glVertex3f(8.0f, 2.9f, 10.0f);			
			gl.glEnd();
		}
		// Combine the doors and the window to make one part
		private void createDoorAndWindow(GL gl, float red, float green, float blue, float dooropen, float doorup) {
			gl.glPushMatrix();
				gl.glColor3f(0f, 0f, 0f);
				createWindow(gl);
				gl.glColor3f(red, green, blue);
				createDoor(gl);
			gl.glPopMatrix();
			gl.glPushMatrix();
				gl.glTranslatef(8f, 0f, 10f);
				gl.glRotatef(dooropen, 0f, 8f, doorup);
				gl.glTranslatef(-8f, 0f, 0f);
				createWindow(gl);
				gl.glColor3f(red, green, blue);
				createDoor(gl);
			gl.glPopMatrix();
		}
		// Create the door for the car
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
		// Create the bottom rail that goes under the door of the car
		private void createBottomRail(GL gl, float red, float green, float blue){
			gl.glColor3f(red, green, blue);
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
		// Create the front grill for the car
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
		// Create the car
		public void createCar(GL gl, float red, float green, float blue, float dooropen, float doorup) {
			createFront(gl, red, green, blue);
			gl.glColor3f(0f, 1f, 0f);
			createBottomRail(gl, red, green, blue);
			createDoorAndWindow(gl, red, green, blue, dooropen, doorup);
			createWindshield(gl);
			createRoof(gl, red, green, blue);
			createRearWindow(gl, red, green, blue);
			createTrunk(gl, red, green, blue);
  	        create4Wheels(gl);
		}

                
                
}
