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
import elong.CrazyLink.CrazyLinkConstent;
import javax.microedition.khronos.opengles.GL10;

public class DrawBackGround {
	
	private IntBuffer   mVertexBuffer;		//�����������ݻ���
    private FloatBuffer   mTextureBuffer;	//�����������ݻ���
    int vCount=0;							//��������     
    int textureId;							//��������
    float textureRatio;						//Ϊ��׼ȷ��ȡ����ͼƬ�е��زĶ�����Ҫ��������ı任��
    public DrawBackGround(int textureId)
    {
    	this.textureId=textureId;    	
    }	
	//�����������ݵĳ�ʼ��
    private void initVertexBuffer()
    {
    	    	
        vCount=6;//�����������һ�������������������α�ʾ������Ҫ6������   

        int width = CrazyLinkConstent.REAL_WIDTH/2;
        int height = CrazyLinkConstent.REAL_HEIGHT/2;
        int vertices[]=new int[]//����������������
        {
           	-width*CrazyLinkConstent.ADP_SIZE,height*CrazyLinkConstent.ADP_SIZE,0,
        	-width*CrazyLinkConstent.ADP_SIZE,-height*CrazyLinkConstent.ADP_SIZE,0,
        	width*CrazyLinkConstent.ADP_SIZE,-height*CrazyLinkConstent.ADP_SIZE,0,
        	width*CrazyLinkConstent.ADP_SIZE,-height*CrazyLinkConstent.ADP_SIZE,0,
        	width*CrazyLinkConstent.ADP_SIZE,height*CrazyLinkConstent.ADP_SIZE,0,
        	-width*CrazyLinkConstent.ADP_SIZE,height*CrazyLinkConstent.ADP_SIZE,0
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
    
    //�����������ݵĳ�ʼ��    
    private void initTextureBuffer()
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
        mTextureBuffer = cbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mTextureBuffer.put(textureCoors);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
    	return;
    }
	

    public void draw(GL10 gl)
    {   
    	initVertexBuffer();	//����col,row��ʼ����������
    	initTextureBuffer();	//����witch����ʼ������������
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
}
