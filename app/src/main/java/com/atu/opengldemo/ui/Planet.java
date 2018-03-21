package com.atu.opengldemo.ui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * 绘制行星
 * 太阳居中，逆时针自转。
 * 地球绕太阳顺时针公转，本身不自转。
 * 月亮绕地球顺时针公转，自身逆时针自转。
 * Created by atu on 2018/3/21.
 */

public class Planet {

    // Our vertices.
    protected float vertices[];
    // Our vertex buffer.
    protected FloatBuffer vertexBuffer;

    public Planet() {
        float a=(float)(1.0f/(2.0f-2f*Math.cos(72f*Math.PI/180.f)));
        float bx=(float)(a*Math.cos(18*Math.PI/180.0f));
        float by=(float)(a*Math.sin(18*Math.PI/180f));
        float cy=(float)(-a * Math.cos(18*Math.PI/180f));
        vertices=new float[]{
                0,a,0.5f,cy,-bx,by,bx,by,-0.5f,cy
        };
        ByteBuffer vbb
                = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glDrawArrays(GL10.GL_LINE_LOOP,0,5);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }


}
