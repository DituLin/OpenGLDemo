package com.atu.opengldemo.ui.activity;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.atu.opengldemo.opengl.IOpenGL;
import com.atu.opengldemo.opengl.OpenGLRenderer;
import com.atu.opengldemo.ui.Planet;

import javax.microedition.khronos.opengles.GL10;

public class SolarSystemActivity extends AppCompatActivity implements IOpenGL{

    private GLSurfaceView mSurFaceView;

    private Planet sun = new Planet();
    private Planet earth = new Planet();
    private Planet moon = new Planet();

    private int angle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurFaceView = new GLSurfaceView(this);
        mSurFaceView.setRenderer(new OpenGLRenderer(this));
        setContentView(mSurFaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSurFaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSurFaceView.onPause();
    }

    @Override
    public void onDrawScene(GL10 gl) {
        clearScreen(gl);
        drawSolarSystem(gl);
    }

    /**
     * 画太阳系
     * @param gl
     */
    private void drawSolarSystem(GL10 gl) {
        gl.glLoadIdentity();
        GLU.gluLookAt(gl,0.0f, 0.0f, 15.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f);

        gl.glPushMatrix();
        gl.glRotatef(angle,0, 0, 1);
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        sun.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(-angle,0,0,1);
        gl.glTranslatef(3,0,0);
        gl.glScalef(0.5f,0.5f,0.5f);
        gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
        earth.draw(gl);


        gl.glPushMatrix();
        gl.glRotatef(-angle, 0, 0, 1);
        gl.glTranslatef(2, 0, 0);
        // Scale it to 50% of Star B
        gl.glScalef(.5f, .5f, .5f);
        // Rotate around it's own center.
        gl.glRotatef(angle*10, 0, 0, 1);
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        moon.draw(gl);

        gl.glPopMatrix();
        gl.glPopMatrix();

        angle++;

    }

    //清屏
    private void clearScreen(GL10 gl) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT);
    }


}
