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


public class DrawLife {
	
	private IntBuffer   mLifeVertexBuffer;		//�����������ݻ���
	private IntBuffer   mNumVertexBuffer;		//�����������ݻ���
    private FloatBuffer   mLifeTextureBuffer;	//�����������ݻ���
    private FloatBuffer   mNumTextureBuffer;	//�����������ݻ���
    int vCount=0;							//��������     
    int textureId;							//��������
    int numTextureId;
    
    final int mBitmapW = 64;
    final int mBitmapH = 32;
    final int mFontSize = 24;	//�����С
    
    float textureRatio;						//Ϊ��׼ȷ��ȡ����ͼƬ�е��زĶ�����Ҫ��������ı任��
    public DrawLife(GL10 gl, int textureId)
    {
    	this.textureId=textureId;
    	initNumTextureBuffer();
    	int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		this.numTextureId = textures[0];
    }	
	//�����������ݵĳ�ʼ��
    private void initVertexBuffer(int col)
    {
    	    	
    	int w = 16;
    	int h = 16;
        vCount=6;//�����������һ�������������������α�ʾ������Ҫ6������   
        int deltaX = ((col + 2)*2*w*CrazyLinkConstent.ADP_SIZE);
        int deltaY = 10*2*h*CrazyLinkConstent.ADP_SIZE;
        int vertices[]=new int[]//����������������
        {
           	-w*CrazyLinkConstent.ADP_SIZE+deltaX,h*CrazyLinkConstent.ADP_SIZE+deltaY,0,
        	-w*CrazyLinkConstent.ADP_SIZE+deltaX,-h*CrazyLinkConstent.ADP_SIZE+deltaY,0,
        	w*CrazyLinkConstent.ADP_SIZE+deltaX,-h*CrazyLinkConstent.ADP_SIZE+deltaY,0,
        	w*CrazyLinkConstent.ADP_SIZE+deltaX,-h*CrazyLinkConstent.ADP_SIZE+deltaY,0,
        	w*CrazyLinkConstent.ADP_SIZE+deltaX,h*CrazyLinkConstent.ADP_SIZE+deltaY,0,
        	-w*CrazyLinkConstent.ADP_SIZE+deltaX,h*CrazyLinkConstent.ADP_SIZE+deltaY,0
        };
        //���������������ݻ���
        //int����ռ��4���ֽڣ����ת��Ϊbyte������ʱ��Ҫ*4
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());		//���ñ��ص��ֽ�˳��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������    	        
        mLifeVertexBuffer = vbb.asIntBuffer();		//ת��Ϊint�ͻ���
        mLifeVertexBuffer.put(vertices);			//�򻺳����з��붥����������
        mLifeVertexBuffer.position(0);				//���û�������ʼλ��
        return;
    }
    
    private void initNumVertexBuffer()
    {
        vCount=6;//�����������һ�������������������α�ʾ������Ҫ6������
        float deltaX = 32*5*CrazyLinkConstent.ADP_SIZE;
        float deltaY = 64*5*CrazyLinkConstent.ADP_SIZE;
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
        textureRatio = (float)(1/2.0f);		//ͼƬ��2���������زĶ�����ɣ�ÿ����Ҫ����witch׼ȷ�ػ�ȡ��Ӧ���ز�
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
        mLifeTextureBuffer = cbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mLifeTextureBuffer.put(textureCoors);//�򻺳����з��붥����ɫ����
        mLifeTextureBuffer.position(0);//���û�������ʼλ��
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
		canvas.drawText(str, 20, 28, paint);	//��д��λ�ã����ݾ���������Ե���һ��			

		return bitmap;
	}
	
	public void drawNumber(GL10 gl, int life)
    {
		life -= 3;
		if (life <= 0) return;
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



    void drawLife(GL10 gl, int pic, int col)
    {   
    	initVertexBuffer(col);	//����col,row��ʼ����������
    	initTextureBuffer(pic);	//����witch����ʼ������������
    	//gl.glTranslatef(col * textureRatio, row * textureRatio, 0);	//��x=col,y=row��λ�û���ѡ�����زĶ���        
        //�������꣬����ʹ�ö�������
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
    		3,				//ÿ���������������Ϊ3  xyz 
    		GL10.GL_FIXED,	//��������ֵ������Ϊ GL_FIXED
    		0, 				//����������������֮��ļ��
    		mLifeVertexBuffer	//������������
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
    		mLifeTextureBuffer		//������������
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
    
    public void draw(GL10 gl, int life)
    {
    	int lifeCnt = life;
    	if (life > 3) lifeCnt = 3;
    	for(int i = 0; i < 3; i++)
    	{
    		if(lifeCnt >= 3)
    			drawLife(gl, 1, i);
    		else
    			drawLife(gl, 0, i);
    		lifeCnt++;
    	}
    	drawNumber(gl, life);
    }
}

