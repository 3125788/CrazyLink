/**********************************************************
 * ��Ŀ���ƣ�ɽկ������������Ϸ7�ս̳�
 * ��          �ߣ�֣����
 * ��Ѷ΢����SuperCube3D
 * ��          �ڣ�2013��11��
 * ��          ������Ȩ����   ��Ȩ�ؾ�
 * ��Դ���빩�����о�ѧϰOpenGL ES����AndroidӦ���ã�
 * ����ȫ���򲿷�������ҵ��;
 ********************************************************/

package elong.CrazyLink.Draw;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.opengl.GLUtils;

import elong.CrazyLink.CrazyLinkConstent;


public class DrawTimeBar {
	
	int mMaxTime = 0;

	
	private IntBuffer   mVertexBuffer;		//�����������ݻ���
	private IntBuffer   mNumVertexBuffer;		//�����������ݻ���
    private FloatBuffer   mTextureBuffer;	//�����������ݻ���
    private FloatBuffer   mNumTextureBuffer;	//�����������ݻ���
    int vCount=0;							//��������     
    int textureId;							//��������
    int numTextureId;
    
    final int mBitmapW = 64;
    final int mBitmapH = 32;
    final int mFontSize = 24;	//�����С
    
    float textureRatio;						//Ϊ��׼ȷ��ȡ����ͼƬ�е��زĶ�����Ҫ��������ı任��
    public DrawTimeBar(GL10 gl, int textureId, int maxTime)
    {
    	this.textureId=textureId;
    	this.mMaxTime = maxTime;
    	initNumTextureBuffer();
    	int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		this.numTextureId = textures[0];
    }	
	//�����������ݵĳ�ʼ��
    private void initVertexBuffer(float percent)
    {
    	    	
    	float w = 64 * 2.5f;
    	int h = 24;
        vCount=6;//�����������һ�������������������α�ʾ������Ҫ6������   
        float deltaX = 2*w*CrazyLinkConstent.ADP_SIZE * percent;
        float deltaY = -64 * 4.5f * CrazyLinkConstent.ADP_SIZE;
        int vertices[]=new int[]//����������������
        {
           	(int)(-w*CrazyLinkConstent.ADP_SIZE),h*CrazyLinkConstent.ADP_SIZE+(int)deltaY,0,
        	(int)(-w*CrazyLinkConstent.ADP_SIZE),-h*CrazyLinkConstent.ADP_SIZE+(int)deltaY,0,
        	(int)(-w*CrazyLinkConstent.ADP_SIZE+deltaX),-h*CrazyLinkConstent.ADP_SIZE+(int)deltaY,0,
        	(int)(-w*CrazyLinkConstent.ADP_SIZE+deltaX),-h*CrazyLinkConstent.ADP_SIZE+(int)deltaY,0,
        	(int)(-w*CrazyLinkConstent.ADP_SIZE+deltaX),h*CrazyLinkConstent.ADP_SIZE+(int)deltaY,0,
        	(int)(-w*CrazyLinkConstent.ADP_SIZE),h*CrazyLinkConstent.ADP_SIZE+(int)deltaY,0
        };
        //���������������ݻ���
        //int����ռ��4���ֽڣ����ת��Ϊbyte������ʱ��Ҫ*4
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());		//���ñ��ص��ֽ�˳��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������    	        
        mVertexBuffer = vbb.asIntBuffer();		//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);			//�򻺳����з��붥����������
        mVertexBuffer.position(0);				//���û�������ʼλ��
        return;
    }
    
    private void initNumVertexBuffer()
    {
        vCount=6;//�����������һ�������������������α�ʾ������Ҫ6������
        float deltaX = 0*CrazyLinkConstent.ADP_SIZE;
        float deltaY = -64 * 4.5f *CrazyLinkConstent.ADP_SIZE;
        int vertices[]=new int[]//����������������
        {
           	-mBitmapW/2*CrazyLinkConstent.ADP_SIZE+(int)deltaX,mBitmapH/2*CrazyLinkConstent.ADP_SIZE+(int)deltaY,0,
        	-mBitmapW/2*CrazyLinkConstent.ADP_SIZE+(int)deltaX,-mBitmapH/2*CrazyLinkConstent.ADP_SIZE+(int)deltaY,0,
        	mBitmapW/2*CrazyLinkConstent.ADP_SIZE+(int)deltaX,-mBitmapH/2*CrazyLinkConstent.ADP_SIZE+(int)deltaY,0,
        	mBitmapW/2*CrazyLinkConstent.ADP_SIZE+(int)deltaX,-mBitmapH/2*CrazyLinkConstent.ADP_SIZE+(int)deltaY,0,
        	mBitmapW/2*CrazyLinkConstent.ADP_SIZE+(int)deltaX,mBitmapH/2*CrazyLinkConstent.ADP_SIZE+(int)deltaY,0,
        	-mBitmapW/2*CrazyLinkConstent.ADP_SIZE+(int)deltaX,mBitmapH/2*CrazyLinkConstent.ADP_SIZE+(int)deltaY,0
        };
        //���������������ݻ���
        //int����ռ��4���ֽڣ����ת��Ϊbyte������ʱ��Ҫ*4
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());		//���ñ��ص��ֽ�˳��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������    	        
        mNumVertexBuffer = vbb.asIntBuffer();		//ת��Ϊint�ͻ���
        mNumVertexBuffer.put(vertices);			//�򻺳����з��붥����������
        mNumVertexBuffer.position(0);				//���û�������ʼλ��
        return;
    }

    
    //�����������ݵĳ�ʼ��    
    private void initTextureBuffer(int witch)
    {
        textureRatio = (float)(1/4.0f);		//ͼƬ��4���������زĶ�����ɣ�ÿ����Ҫ����witch׼ȷ�ػ�ȡ��Ӧ���ز�
        float textureCoors[]=new float[]	//��������S��T����ֵ����
	    {
        	witch * textureRatio,0,
        	witch * textureRatio,1,
        	(witch+1) * textureRatio,1,
        	(witch+1) * textureRatio,1,
        	(witch+1) * textureRatio,0,        	
        	witch * textureRatio,0
	    };        
        
        //���������������ݻ���
        //int����ռ��4���ֽڣ����ת��Ϊbyte������ʱ��Ҫ*4
        ByteBuffer cbb = ByteBuffer.allocateDirect(textureCoors.length*4);
        cbb.order(ByteOrder.nativeOrder());//���ñ����ֽ�˳��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        mTextureBuffer = cbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mTextureBuffer.put(textureCoors);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
    	return;
    }
    
    private void initNumTextureBuffer()
    {
        float textureCoors[]=new float[]	//��������S��T����ֵ����
	    {
        	0,0,
        	0,1,
        	1,1,
        	1,1,
        	1,0,        	
        	0,0
	    };        
        
        //���������������ݻ���
        //int����ռ��4���ֽڣ����ת��Ϊbyte������ʱ��Ҫ*4
        ByteBuffer cbb = ByteBuffer.allocateDirect(textureCoors.length*4);
        cbb.order(ByteOrder.nativeOrder());//���ñ����ֽ�˳��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        mNumTextureBuffer = cbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mNumTextureBuffer.put(textureCoors);//�򻺳����з��붥����ɫ����
        mNumTextureBuffer.position(0);//���û�������ʼλ��
    	return;
    }
    
	private void bindTexture(GL10 gl, Bitmap bmp)
	{
		gl.glBindTexture(GL10.GL_TEXTURE_2D, numTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);//ָ����С���˷���
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);//ָ���Ŵ���˷���
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);//ָ��S��������ͼģʽ
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);//ָ��T��������ͼģʽ
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
		bmp.recycle();
	}


	Bitmap genBitmap(int life)
	{
		//���ɵ�λͼҪ��ALPHAͨ����Ϣ�������޷�����͸��������
		Bitmap bitmap = Bitmap.createBitmap(mBitmapW, mBitmapH, Config.ARGB_4444);
		Canvas canvas = new Canvas(bitmap);

		Paint paint = new Paint();
		paint.setColor(Color.TRANSPARENT);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
		paint.setTextSize(mFontSize);	
		paint.setColor(Color.WHITE);
		String str = Integer.toString(life);			
		canvas.drawText(str, 20, 24, paint);	//��д��λ�ã����ݾ���������Ե���һ��			

		return bitmap;
	}
	
	public void drawNumber(GL10 gl, int life)
    {
		if (life < 0) return;
    	Bitmap bmp = genBitmap(life);
    	bindTexture(gl, bmp);
    	initNumVertexBuffer();	//����col,row��ʼ����������

    	//�������꣬����ʹ�ö�������
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
    		3,				//ÿ���������������Ϊ3  xyz 
    		GL10.GL_FIXED,	//��������ֵ������Ϊ GL_FIXED
    		0, 				//����������������֮��ļ��
    		mNumVertexBuffer	//������������
        );
        
        //�������꣬��������
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //����ʹ����������
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //Ϊ����ָ������uv��������
        gl.glTexCoordPointer
        (
    		2, 					//ÿ���������������������� S��T
    		GL10.GL_FLOAT, 		//��������
    		0, 					//����������������֮��ļ��
    		mNumTextureBuffer		//������������
        );        		
        gl.glBindTexture(GL10.GL_TEXTURE_2D,numTextureId);//Ϊ���ʰ�ָ������ID����   
        
        //����ͼ��
        gl.glDrawArrays
        (
    		GL10.GL_TRIANGLES, 
    		0, 
    		vCount
        );
        gl.glDisable(GL10.GL_TEXTURE_2D);//�ر�����
    	
    }



    void drawTimeBackground(GL10 gl)
    {   
    	initVertexBuffer(1.0f);	//����col,row��ʼ����������
    	initTextureBuffer(0);	//����witch����ʼ������������        
        //�������꣬����ʹ�ö�������
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
    		3,				//ÿ���������������Ϊ3  xyz 
    		GL10.GL_FIXED,	//��������ֵ������Ϊ GL_FIXED
    		0, 				//����������������֮��ļ��
    		mVertexBuffer	//������������
        );
        
        //�������꣬��������
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //����ʹ����������
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //Ϊ����ָ������uv��������
        gl.glTexCoordPointer
        (
    		2, 					//ÿ���������������������� S��T
    		GL10.GL_FLOAT, 		//��������
    		0, 					//����������������֮��ļ��
    		mTextureBuffer		//������������
        );        		
        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureId);//Ϊ���ʰ�ָ������ID����   
        
        //����ͼ��
        gl.glDrawArrays
        (
    		GL10.GL_TRIANGLES, 
    		0, 
    		vCount
        );
        gl.glDisable(GL10.GL_TEXTURE_2D);//�ر�����
    }
    
    void drawTimeProgressBar(GL10 gl, int leftTime)
    {   
    	if(leftTime > mMaxTime) leftTime = mMaxTime;
    	float percent = (float)leftTime / (float)mMaxTime;
    	initVertexBuffer(percent);	//����col,row��ʼ����������
    	int picId = 1;
    	if(percent > 0.2f) picId = 1;
    	else if(percent > 0.1f) picId = 2;
    	else picId = 3;
    		
    	initTextureBuffer(picId);	//����witch����ʼ������������        
        //�������꣬����ʹ�ö�������
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
    		3,				//ÿ���������������Ϊ3  xyz 
    		GL10.GL_FIXED,	//��������ֵ������Ϊ GL_FIXED
    		0, 				//����������������֮��ļ��
    		mVertexBuffer	//������������
        );
        
        //�������꣬��������
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //����ʹ����������
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //Ϊ����ָ������uv��������
        gl.glTexCoordPointer
        (
    		2, 					//ÿ���������������������� S��T
    		GL10.GL_FLOAT, 		//��������
    		0, 					//����������������֮��ļ��
    		mTextureBuffer		//������������
        );        		
        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureId);//Ϊ���ʰ�ָ������ID����   
        
        //����ͼ��
        gl.glDrawArrays
        (
    		GL10.GL_TRIANGLES, 
    		0, 
    		vCount
        );
        gl.glDisable(GL10.GL_TEXTURE_2D);//�ر�����
    }
    
    
    public void draw(GL10 gl, int leftTime)
    {    
    	drawNumber(gl, leftTime);
    	drawTimeProgressBar(gl, leftTime);
    	drawTimeBackground(gl);
    }
}


