package com.atu.opengldemo.ui.activity;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.atu.opengldemo.opengl.IOpenGL;
import com.atu.opengldemo.opengl.OpenGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * 利用 OpenGL ES 画 点 、线 、 面
 */
public class OpenGLOneActivity extends AppCompatActivity implements IOpenGL{

    //三个顶点坐标
    float[] vertexArray = new float[]{
            -0.8f , -0.4f * 1.732f , 0.0f ,
            0.8f , -0.4f * 1.732f , 0.0f ,
            0.0f , 0.4f * 1.732f , 0.0f ,
    };

    //四个顶点坐标
    float[] vertexLineArray = new float[]{
            -0.8f, -0.4f * 1.732f, 0.0f,
            -0.4f, 0.4f * 1.732f, 0.0f,
            0.0f, -0.4f * 1.732f, 0.0f,
            0.4f, 0.4f * 1.732f, 0.0f,
    };

    // 三角形坐标
    float vertexTriangleArray[] = {
            -0.8f, -0.4f * 1.732f, 0.0f,
            0.0f, -0.4f * 1.732f, 0.0f,
            -0.4f, 0.4f * 1.732f, 0.0f,
            0.0f, -0.0f * 1.732f, 0.0f,
            0.8f, -0.0f * 1.732f, 0.0f,
            0.4f, 0.4f * 1.732f, 0.0f,
    };

    // 20 面体
    static final float X=.525731112119133606f;
    static final float Z=.850650808352039932f;
    static float vertices[] = new float[]{
            -X, 0.0f, Z, X, 0.0f, Z, -X, 0.0f, -Z, X, 0.0f, -Z,
            0.0f, Z, X, 0.0f, Z, -X, 0.0f, -Z, X, 0.0f, -Z, -X,
            Z, X, 0.0f, -Z, X, 0.0f, Z, -X, 0.0f, -Z, -X, 0.0f
    };
    static short indices[] = new short[]{
            0,4,1, 0,9,4, 9,5,4, 4,5,8, 4,8,1,
            8,10,1, 8,3,10, 5,3,8, 5,2,3, 2,7,3,
            7,10,3, 7,6,10, 7,11,6, 11,0,6, 0,1,6,
            6,1,10, 9,0,11, 9,11,2, 9,2,5, 7,2,11 };

    float[] colors = {
            0f, 0f, 0f, 1f,
            0f, 0f, 1f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 1f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 1f, 1f,
            1f, 1f, 0f, 1f,
            1f, 1f, 1f, 1f,
            1f, 0f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f
    };

    GLSurfaceView mSurFaceView;
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private ShortBuffer indexBuffer;

    int index;
    int angle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurFaceView = new GLSurfaceView(this);
        mSurFaceView.setRenderer(new OpenGLRenderer(this));
        setContentView(mSurFaceView);
        initSampleData();
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

//       drawPoint(gl);

//       drawLines(gl);

//       drawTriangle(gl);

       drawSample(gl);
    }

    //清屏
    private void clearScreen(GL10 gl) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);//红色
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT);
    }

    //画三角形
    private void drawTriangle(GL10 gl) {

        ByteBuffer vbb  = ByteBuffer.allocateDirect(vertexTriangleArray.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertex = vbb.asFloatBuffer();
        vertex.put(vertexTriangleArray);
        vertex.position(0);

        gl.glLoadIdentity();
        gl.glTranslatef(0,0,-4);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3,GL10.GL_FLOAT, 0, vertex);//通知 OpenGL ES 图形库顶点坐标
        index++;
        index %= 10;
        switch (index) {
            case 0:
            case 1:
            case 2:
                gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);//红
                gl.glDrawArrays(GL10.GL_TRIANGLES,0,6);
                break;
            case 3:
            case 4:
            case 5:
                gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);//绿
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,6);
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);//蓝
                gl.glDrawArrays(GL10.GL_TRIANGLE_FAN,0,6);
                break;
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }
    // 画线
    private void drawLines(GL10 gl) {
        ByteBuffer vbb  = ByteBuffer.allocateDirect(vertexLineArray.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertex = vbb.asFloatBuffer();
        vertex.put(vertexLineArray);
        vertex.position(0);

        gl.glLoadIdentity();
        gl.glTranslatef(0,0,-4);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3,GL10.GL_FLOAT, 0, vertex);//通知 OpenGL ES 图形库顶点坐标
        index++;
        index %= 10;
        switch (index) {
            case 0:
            case 1:
            case 2:
                gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);//红
                gl.glDrawArrays(GL10.GL_LINES,0,4);
                break;
            case 3:
            case 4:
            case 5:
                gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);//绿
                gl.glDrawArrays(GL10.GL_LINE_STRIP,0,4);
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);//蓝
                gl.glDrawArrays(GL10.GL_LINE_LOOP,0,4);
                break;
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);



    }
    //画点
    private void drawPoint(GL10 gl) {
        ByteBuffer  vbb = ByteBuffer.allocateDirect(vertexArray.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertex = vbb.asFloatBuffer();//存放三个顶点坐标
        vertex.put(vertexArray);
        vertex.position(0);
        gl.glColor4f(1.0f,0.0f,0.0f,1.0f);//设置红色
        gl.glPointSize(8f);//设置大小
        gl.glLoadIdentity();
        gl.glTranslatef(0,0,-4); //设置位移
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开 Pipeline 的Vectex 顶点“开关”
        gl.glVertexPointer(3,GL10.GL_FLOAT, 0, vertex);//通知 OpenGL ES 图形库顶点坐标
        gl.glDrawArrays(GL10.GL_POINTS,0,3);//使用 glDrawArrays 绘制 3 个顶点
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//关闭 Pipeline 的Vectex 顶点“开关”

    }

    // 示例 20 面体
    private void drawSample(GL10 gl) {
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);
        gl.glRotatef(angle, 0, 1, 0);//旋转
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);//顶点
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);//颜色
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
                GL10.GL_UNSIGNED_SHORT, indexBuffer);//面
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
        angle++;
    }

    private void initSampleData() {
        ByteBuffer vbb
                = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        ByteBuffer cbb
                = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
        ByteBuffer ibb
                = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }


}
