package com.atu.opengldemo.opengl;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by atu on 2018/3/17.
 */

public class OpenGLRenderer implements GLSurfaceView.Renderer{

    private final IOpenGL mOpenGL;

    public OpenGLRenderer(IOpenGL iOpenGL) {
        mOpenGL = iOpenGL;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置背景颜色
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_NICEST);


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0,0,width,height);//窗口显示  大小
        gl.glMatrixMode(GL10.GL_PROJECTION);//矩阵模型 投影
        gl.glLoadIdentity();// 单位矩阵 - 复位
        GLU.gluPerspective(gl, 45.0f,
                (float) width / (float) height,
                0.1f, 100.0f);  // 透视投影
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (mOpenGL != null) {
            mOpenGL.onDrawScene(gl);
        }
    }

}
