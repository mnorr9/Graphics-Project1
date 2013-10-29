package cityscene;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class Shapes
{

  public void CylinderDiffRadii( GLAutoDrawable drawable, float upper_radius, float lower_radius, float height, float theta )
  {
    GL gl = drawable.getGL();

    float radian, ur, lr, h, t;
    float t_normal, radian_normal;
    float xl, zl;
    float xu, zu;
    /* set the cylinder to be drawn filled */
    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
    /*draw the upper circle*/
    lr = lower_radius;
    ur = upper_radius;
    h = height;
    t = (float) 0.0;
    gl.glPushMatrix();
    gl.glBegin(GL.GL_TRIANGLE_FAN);
    gl.glNormal3f(0.0f, 1.0f, 0.0f);
    gl.glVertex3f(0.0f, h, 0.0f);
    gl.glVertex3f(ur, 0.0f, 0.0f);
    while (t <= 360)
    {
      radian = (float) (Math.PI * t / 180.0);
      gl.glVertex3f((float) (ur * Math.cos(radian)), h, (float) (ur * Math.sin(radian)));
      t = t + theta;
    }
    gl.glEnd();
    gl.glPopMatrix();

    /* draw the lower circle */

    lr = lower_radius;
    ur = upper_radius;
    t = (float) 0.0;
    gl.glPushMatrix();
    gl.glBegin(GL.GL_TRIANGLE_FAN);
    gl.glNormal3f(0.0f, -1.0f, 0.0f);
    gl.glVertex3f(0.0f, 0.0f, 0.0f);
    gl.glVertex3f(lr, 0.0f, 0.0f);
    while (t <= 360)
    {
      radian = (float) (Math.PI * t / 180.0);
      gl.glVertex3f((float) (lr * Math.cos(radian)), 0.0f, (float) (lr * Math.sin(radian)));
      t = t + theta;
    }
    gl.glEnd();
    gl.glPopMatrix();

    h = height;
    t = theta;
    gl.glBegin(GL.GL_QUAD_STRIP);
    gl.glNormal3d(lr, 0.0, 0.0);
    gl.glVertex3f(lr, 0.0f, 0.0f);
    gl.glNormal3d(ur, 0.0, 0.0);
    gl.glVertex3f(ur, h, 0.0f);
    while (t <= 360)
    {
      t_normal = t - theta / 2;

      radian = (float) (Math.PI * t / 180.0);
      radian_normal = (float) (Math.PI * t_normal / 180.0);

      xl = (float) (lr * Math.cos(radian));
      zl = (float) (lr * Math.sin(radian));
      gl.glNormal3f(xl, 0.0f, zl);
      gl.glVertex3f(xl, 0.0f, zl);

      xu = (float) (ur * Math.cos(radian));
      zu = (float) (ur * Math.sin(radian));

      gl.glVertex3f(xu, h, zu);
      t = t + theta;
    }
    gl.glEnd();
  }

  public void Sphere( GLAutoDrawable drawable, float radius, float inc )
  {
    GL gl = drawable.getGL();

    double r, angle2, radian1, radian2;
    double angle1;
    
    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);

     for(angle1=5.0; angle1<180; angle1+=inc) {
          radian1 = angle1*Math.PI/180;
          r = radius*Math.sin(radian1);
      gl.glBegin(GL.GL_POLYGON);
          for(angle2=0.0; angle2<360; angle2+=inc) {
              radian2 = angle2*Math.PI/180;
            gl.glVertex3d(r*Math.sin(radian2), radius*Math.cos(radian1), r*Math.cos(radian2));
        }
      gl.glEnd();
     }
     for(angle1=5.0; angle1<180; angle1+=inc) {
          radian1 = angle1*Math.PI/180;
          r = radius*Math.sin(radian1);
      gl.glBegin(GL.GL_POLYGON);
          for(angle2=0.0; angle2<360; angle2+=inc) {
              radian2 = angle2*Math.PI/180;
            gl.glVertex3d(radius*Math.cos(radian1), r*Math.cos(radian2), r*Math.sin(radian2));
        }
    gl.glEnd();
     }
  }

  public void drawSphere( GLAutoDrawable drawable, double radius, int steps, int slices )
  {
    GL gl = drawable.getGL();

    double [] radii = new double[steps+1];
    double [] heights = new double[steps+1];
    double [] sins = new double[slices+1];
    double [] coss = new double[slices+1];
    
    radii[0] = 0.0;
    heights[0] = radius;
    double deg = 360.0 / (steps*4);
    for (int i = 1; i <= steps; i++)
    {
        double x = radius * Math.cos(Math.toRadians(deg * i));
        double y = radius * Math.sin(Math.toRadians(deg * i));
        radii[i] = y;
        heights[i] = x;
    }
    deg = 360.0 / slices;
    for (int i = 0; i <= slices; i++)
    {
        double cos = Math.cos(Math.toRadians(deg * i));
        double sin = Math.sin(Math.toRadians(deg * i));
        sins[i] = sin;
        coss[i] = cos;
    }
    
    // draw the top of the sphere
    gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glNormal3d(0.0, radius, 0.0);
        gl.glVertex3d(0.0, radius, 0.0); // top center of sphere is on the Y-axis
        for (int j = slices; j >= 0; j--)
        {
            gl.glNormal3d(radii[1]*coss[j], heights[1], radii[1]*sins[j]);
            gl.glVertex3d(radii[1]*coss[j], heights[1], radii[1]*sins[j]);
        }
    gl.glEnd();
    
    // finish the top hemisphere
    for (int i = 1; i < steps; i++)
    {
        gl.glBegin(GL.GL_QUAD_STRIP);
            for (int j = 0; j <= slices; j++)
            {
                gl.glNormal3d(radii[i+1]*coss[j], heights[i+1], radii[i+1]*sins[j]);
                gl.glVertex3d(radii[i+1]*coss[j], heights[i+1], radii[i+1]*sins[j]);
                gl.glNormal3d(radii[i]*coss[j], heights[i], radii[i]*sins[j]);
                gl.glVertex3d(radii[i]*coss[j], heights[i], radii[i]*sins[j]);
            }
        gl.glEnd();
    }
    
    // the bottom hemisphere
    for (int i = steps-1; i >= 1; i--)
    {
        gl.glBegin(GL.GL_QUAD_STRIP);
            for (int j = 0; j <= slices; j++)
            {
                gl.glNormal3d(radii[i]*coss[j], -heights[i], radii[i]*sins[j]);
                gl.glVertex3d(radii[i]*coss[j], -heights[i], radii[i]*sins[j]);
                gl.glNormal3d(radii[i+1]*coss[j], -heights[i+1], radii[i+1]*sins[j]);
                gl.glVertex3d(radii[i+1]*coss[j], -heights[i+1], radii[i+1]*sins[j]);
            }
        gl.glEnd();
    }
    
    // draw the bottom of the sphere
    gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glNormal3d(0.0, -radius, 0.0);
        gl.glVertex3d(0.0, -radius, 0.0); // top center of sphere is on the Y-axis
        for (int j = 0; j <= slices; j++)
        {
            gl.glNormal3d(radii[1]*coss[j], -heights[1], radii[1]*sins[j]);
            gl.glVertex3d(radii[1]*coss[j], -heights[1], radii[1]*sins[j]);
        }
    gl.glEnd();
  }

  void sharpCone( GLAutoDrawable drawable, float radius, float height, float theta )
  {
    GL gl = drawable.getGL();

    float radian, r, h, t;
    float x, y, z;
    /* set the cylinder to be drawn filled */
    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);

    /* draw the upper circle */
    r = radius;
    h = 0.0f;
    t = theta;
    gl.glBegin(GL.GL_TRIANGLE_FAN);
    // gl.glColor3f(1.0f, 1.0f, 0.0f);
    // gl.glNormal3f(0.0f,3*h, 0.0f);
    // gl.glNormal3f(0.0f, 1.0f, 0.0f);
    //gl.glVertex3f(0.0f, 3 * h, 0.0f);
    gl.glVertex3f(0.0f, 3 * height, 0.0f);
    gl.glVertex3f(r, 0.0f, 0.0f);

    gl.glNormal3f(0.0f, -1.0f, 0.0f);

    while (t <= 360) {
        radian = (float) (Math.PI * t / 180.0);
        x = (float) (r * Math.cos(radian));
        z = (float) (r * Math.sin(radian));
        // System.out.println("x: "+ x+" h: "+h+" z: "+ z);
        gl.glNormal3f(x, -1.0f, -z);
        gl.glVertex3f(x, h, z);
        t = t + theta;
    }

    gl.glEnd();
  }

}




