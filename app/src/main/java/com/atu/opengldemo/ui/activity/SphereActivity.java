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

public class SphereActivity extends AppCompatActivity implements IOpenGL {

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
        drawSphere(gl);
    }

    private void drawSphere(GL10 gl) {
        sphere.draw(gl);
    }

    //清屏
    private void clearScreen(GL10 gl) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT);
    }


    //添加光源
    private void initScene(GL10 gl){
        float[] mat_amb = {0.2f * 1.0f, 0.2f * 0.4f, 0.2f * 0.4f, 1.0f,};
        float[] mat_diff = {1.0f, 0.4f, 0.4f, 1.0f,};
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



        gl.glClearColor(0.8f, 0.8f, 0.8f, 0.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glShadeModel(GL10.GL_SMOOTH);

        gl.glEnable(GL10.GL_LIGHTING);//灯光
        gl.glEnable(GL10.GL_LIGHT0);

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambBuf);//材质
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_diffBuf);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_specBuf);
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 64.0f);

        gl.glLoadIdentity();
        GLU.gluLookAt(gl,0.0f, 0.0f, 10.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f);

    }
}
