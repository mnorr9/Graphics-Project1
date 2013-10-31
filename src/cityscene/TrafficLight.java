package cityscene;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class TrafficLight
{
  public enum lightColors
  {
    red,
    yellow,
    green
  }

  private Shapes shapes;
  private double red, yellow, green;
  private lightColors nsLight, ewLight;
  
  public TrafficLight()
  {
    shapes = new Shapes();
    nsLight = lightColors.red;
    ewLight = lightColors.green;
  }

  public void setRedLight()
  {
    red    = 1.0;
    yellow = 0.0;
    green  = 0.0;
  }
 
  public void setYellowLight()
  {
    red    = 0.0;
    yellow = 1.0;
    green  = 0.0;
  }
  
  public void setGreenLight()
  {
    red    = 0.0;
    yellow = 0.0;
    green  = 1.0;
  }
  
  public void updateTrafficColors()
  {
    if( nsLight == lightColors.red && ewLight == lightColors.yellow )
    {
      nsLight = lightColors.green;
      ewLight = lightColors.red;
    }
    else if( nsLight == lightColors.red && ewLight == lightColors.green )
    {
      nsLight = lightColors.red;
      ewLight = lightColors.yellow;
    }
    else if( nsLight == lightColors.green && ewLight == lightColors.red )
    {
      nsLight = lightColors.yellow;
      ewLight = lightColors.red;
    }
    else if( nsLight == lightColors.yellow && ewLight == lightColors.red )
    {
      nsLight = lightColors.red;
      ewLight = lightColors.green;
    }
  }

  public void drawTrafficLight( GLAutoDrawable drawable )
  {
    GL gl = drawable.getGL();

    gl.glColor3d( 0.7, 0.7, 0.7 );
    gl.glPushMatrix();
      gl.glRotated( 90, 1.0, 0.0, 0.0 );
      gl.glRotated( 90, 0.0, 1.0, 0.0 );
      gl.glScaled( 0.2, 0.2, 0.2 );
      shapes.Cylinder( drawable, 0.3f, 4, 15 );
      gl.glPushMatrix();
        gl.glTranslated( 0.0, 4.0, -0.3 );
        gl.glRotated( 90, 1.0, 0.0, 0.0 );
        shapes.Cylinder( drawable, 0.3f, 7.91f, 15 );
      gl.glPopMatrix();
      gl.glPushMatrix();
        gl.glTranslated( 0.0, 3.2, 7.46 );
        shapes.Cylinder( drawable, 0.15f, 0.75f, 15);
      gl.glPopMatrix();
      gl.glPushMatrix();
        gl.glColor3d( 0.2, 0.2, 0.2 );
        gl.glTranslated( 0.0, 2.7, 7.46 );
        gl.glRotated( 45, 0.0, 1.0, 0.0 );
        drawBody( drawable );
        gl.glPushMatrix();
          drawNorthSouthLights( drawable );
          drawEastWestLights( drawable );
        gl.glPopMatrix();        
      gl.glPopMatrix();
    gl.glPopMatrix();     

  }

  private void drawBody( GLAutoDrawable drawable )
  {
    GL gl = drawable.getGL();
    
    double x = 0.3;
    double y = 0.75;
    double z = 0.3;
    
    gl.glPushMatrix();
      gl.glBegin( GL.GL_QUADS );
    
      //Top Square
      gl.glVertex3d(  x, y, -z );  //Top Right
      gl.glVertex3d( -x, y, -z );  //Top Left
      gl.glVertex3d( -x, y,  z );  //Bottom Left
      gl.glVertex3d(  x, y,  z );  //Bottom Right
    
      //Front Square
      gl.glVertex3d(  x,  y, z );  //Top Right
      gl.glVertex3d( -x,  y, z );  //Top Left
      gl.glVertex3d( -x, -y, z );  //Bottom Left
      gl.glVertex3d(  x, -y, z );  //Bottom Right

      //Right Square
      gl.glVertex3d( x,  y, -z );  //Top Right
      gl.glVertex3d( x,  y,  z );  //Top Left
      gl.glVertex3d( x, -y,  z );  //Bottom Left
      gl.glVertex3d( x, -y, -z );  //Bottom Right

      //Left Square
      gl.glVertex3d( -x,  y, -z );  //Top Left
      gl.glVertex3d( -x,  y,  z );  //Top Right
      gl.glVertex3d( -x, -y,  z );  //Bottom Right
      gl.glVertex3d( -x, -y, -z );  //Bottom Left
   
      //Back Square
      gl.glVertex3d( -x,  y, -z );  //Top Left
      gl.glVertex3d(  x,  y, -z );  //Top Right
      gl.glVertex3d(  x, -y, -z );  //Bottom Right
      gl.glVertex3d( -x, -y, -z );  //Bottom Left

      //Bottom Square
      gl.glVertex3d(  x, -y, -z );  //Top Right
      gl.glVertex3d( -x, -y, -z );  //Top Left
      gl.glVertex3d( -x, -y,  z );  //Bottom Left
      gl.glVertex3d(  x, -y,  z );  //Bottom Right

      gl.glEnd();
    gl.glPopMatrix();
  }

  private void drawNorthSouthLights( GLAutoDrawable drawable )
  {
    GL gl = drawable.getGL();
   
    switch( nsLight )
    {
      case red:
        setRedLight();
        break;
        
      case yellow:
        setYellowLight();
        break;
        
      case green:
        setGreenLight();
        break;
    }
    
    drawLights( drawable );
    gl.glRotated( 180, 0.0, 1.0, 0.0 );
    drawLights( drawable );
  }
  
  private void drawEastWestLights( GLAutoDrawable drawable )
  {
    GL gl = drawable.getGL();
   
    switch( ewLight )
    {
      case red:
        setRedLight();
        break;
        
      case yellow:
        setYellowLight();
        break;
        
      case green:
        setGreenLight();
        break;
    }
    
    gl.glPushMatrix();
      gl.glRotated( 90, 0.0, 1.0, 0.0 );
      drawLights( drawable );
      gl.glRotated( 180, 0.0, 1.0, 0.0 );
      drawLights( drawable );
    gl.glPopMatrix();
  }
  
  private void drawLights( GLAutoDrawable drawable )
  {
    GL gl = drawable.getGL();
    
    double radius = 0.1;
    
    //Top
    gl.glPushMatrix();
      gl.glColor3d( red, 0.0, 0.0 );
      gl.glTranslated( 0.0, 0.375, 0.3 );
      gl.glRotated( 90, 1.0, 0.0, 0.0 );
      shapes.drawHalfSphere( drawable, radius, 5, 15 );  
    gl.glPopMatrix();

    //Middle
    gl.glPushMatrix();
      gl.glColor3d( yellow, yellow, 0.0 );
      gl.glTranslated( 0.0, 0.0, 0.3 );
      gl.glRotated( 90, 1.0, 0.0, 0.0 );
      shapes.drawHalfSphere( drawable, radius, 5, 15 );  
    gl.glPopMatrix();

    //Bottom
    gl.glPushMatrix();
      gl.glColor3d( 0.0, green, 0.0 );
      gl.glTranslated( 0.0, -0.375, 0.3 );
      gl.glRotated( 90, 1.0, 0.0, 0.0 );
      shapes.drawHalfSphere( drawable, radius, 5, 15 );  
    gl.glPopMatrix();
  }

}

