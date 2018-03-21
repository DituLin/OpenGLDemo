package com.atu.opengldemo.ui.activity;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.atu.opengldemo.opengl.IOpenGL;
import com.atu.opengldemo.opengl.OpenGLRenderer;
import com.atu.opengldemo.ui.Sphere;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class LightingActivity extends AppCompatActivity implements IOpenGL {

    private GLSurfaceView mSurFaceView;

    private Sphere sphere = new Sphere();

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
        initScene(gl);
        sphere.draw(gl);
        drawScene(gl);
    }

    //清屏
    private void clearScreen(GL10 gl) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT);
    }

    //光线
    public void initScene(GL10 gl){
        float[] amb = { 1.0f, 1.0f, 1.0f, 1.0f, };
        float[] diff = { 1.0f, 1.0f, 1.0f, 1.0f, };
        float[] spec = { 1.0f, 1.0f, 1.0f, 1.0f, };
        float[] pos = { 0.0f, 5.0f, 5.0f, 1.0f, };
        float[] spot_dir = { 0.0f, -1.0f, 0.0f, };
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_CULL_FACE);

        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        ByteBuffer abb = ByteBuffer.allocateDirect(amb.length*4);
        abb.order(ByteOrder.nativeOrder());
        FloatBuffer ambBuf = abb.asFloatBuffer();
        ambBuf.put(amb);
        ambBuf.position(0);

        ByteBuffer dbb = ByteBuffer.allocateDirect(diff.length*4);
        dbb.order(ByteOrder.nativeOrder());
        FloatBuffer diffBuf = dbb.asFloatBuffer();
        diffBuf.put(diff);
        diffBuf.position(0);

        ByteBuffer sbb = ByteBuffer.allocateDirect(spec.length*4);
        sbb.order(ByteOrder.nativeOrder());
        FloatBuffer specBuf = sbb.asFloatBuffer();
        specBuf.put(spec);
        specBuf.position(0);

        ByteBuffer pbb = ByteBuffer.allocateDirect(pos.length*4);
        pbb.order(ByteOrder.nativeOrder());
        FloatBuffer posBuf = pbb.asFloatBuffer();
        posBuf.put(pos);
        posBuf.position(0);

        ByteBuffer spbb = ByteBuffer.allocateDirect(spot_dir.length*4);
        spbb.order(ByteOrder.nativeOrder());
        FloatBuffer spot_dirBuf = spbb.asFloatBuffer();
        spot_dirBuf.put(spot_dir);
        spot_dirBuf.position(0);

        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambBuf);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffBuf);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specBuf);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, posBuf);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPOT_DIRECTION, spot_dirBuf);
        gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_EXPONENT, 0.0f);
//        gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_CUTOFF, 1.0f);

        gl.glLoadIdentity();
        GLU.gluLookAt(gl,0.0f, 4.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

    }
    //材质
    public void drawScene(GL10 gl) {
        float[] mat_amb = {0.2f * 0.4f, 0.2f * 0.4f, 0.2f * 1.0f, 1.0f,};
		float[] mat_diff = {0.4f, 0.4f, 1.0f, 1.0f,};
		float[] mat_spec = {1.0f, 1.0f, 1.0f, 1.0f,};


		ByteBuffer mabb = ByteBuffer.allocateDirect(mat_amb.length*4);
		mabb.order(ByteOrder.nativeOrder());
		FloatBuffer mat_ambBuf = mabb.asFloatBuffer();
		mat_ambBuf.put(mat_amb);
		mat_ambBuf.position(0);

		ByteBuffer mdbb = ByteBuffer.allocateDirect(mat_diff.length*4);
		mdbb.order(ByteOrder.nativeOrder());
		FloatBuffer mat_diffBuf = mdbb.asFloatBuffer();
		mat_diffBuf.put(mat_diff);
		mat_diffBuf.position(0);

		ByteBuffer msbb = ByteBuffer.allocateDirect(mat_spec.length*4);
		msbb.order(ByteOrder.nativeOrder());
		FloatBuffer mat_specBuf = msbb.asFloatBuffer();
		mat_specBuf.put(mat_spec);
		mat_specBuf.position(0);

    	gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambBuf);
    	gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_diffBuf);
    	gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_specBuf);
    	gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 64.0f);


        sphere.draw(gl);
    }


}
