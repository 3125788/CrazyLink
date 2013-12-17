/**********************************************************
 * 项目名称：山寨腾讯“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年12月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/

package elong.CrazyLink.Draw;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import elong.CrazyLink.CrazyLinkConstent;

import javax.microedition.khronos.opengles.GL10;

public class DrawAnimal {
	
	private IntBuffer   mVertexBuffer;		//顶点坐标数据缓冲
    private FloatBuffer   mTextureBuffer;	//顶点纹理数据缓冲
    int vCount=0;							//顶点数量     
    int textureId;							//纹理索引
    float textureRatio;						//为了准确获取纹理图片中的素材对象，需要设置纹理的变换率
    public DrawAnimal(int textureId)
    {
    	this.textureId=textureId;    	
    }	
	//顶点坐标数据的初始化
    private void initVertexBuffer(int col, int row)
    {
    	    	
        vCount=6;//顶点的数量，一个正方形用两个三角形表示，共需要6个顶点
        int w = 32*CrazyLinkConstent.ADP_SIZE;
        int h = 32*CrazyLinkConstent.ADP_SIZE;
        int deltaX = ((col-3)*2*w);
        int deltaY = ((row-3)*2*h);
        int vertices[]=new int[]//顶点坐标数据数组
        {
           	-w+deltaX,h+deltaY,0,
        	-w+deltaX,-h+deltaY,0,
        	w+deltaX,-h+deltaY,0,
        	w+deltaX,-h+deltaY,0,
        	w+deltaX,h+deltaY,0,
        	-w+deltaX,h+deltaY,0
        };
        //创建顶点坐标数据缓冲
        //int类型占用4个字节，因此转换为byte的数据时需要*4
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());		//设置本地的字节顺序
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题    	        
        mVertexBuffer = vbb.asIntBuffer();		//转换为int型缓冲
        mVertexBuffer.put(vertices);			//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);				//设置缓冲区起始位置
        return;
    }
    
    //顶点纹理数据的初始化    
    private void initTextureBuffer(int witch)
    {
        textureRatio = (float)(1/8.0f);		//图片是(7+1)个独立的素材对象组成，每次需要根据witch准确地获取对应的素材
        float textureCoors[]=new float[]	//顶点纹理S、T坐标值数组
	    {
        	(witch - 1) * textureRatio,0,
        	(witch - 1) * textureRatio,1,
        	witch * textureRatio,1,
        	witch * textureRatio,1,
        	witch * textureRatio,0,        	
        	(witch - 1) * textureRatio,0
	    };        
        
        //创建顶点纹理数据缓冲
        //int类型占用4个字节，因此转换为byte的数据时需要*4
        ByteBuffer cbb = ByteBuffer.allocateDirect(textureCoors.length*4);
        cbb.order(ByteOrder.nativeOrder());//设置本地字节顺序
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        mTextureBuffer = cbb.asFloatBuffer();//转换为int型缓冲
        mTextureBuffer.put(textureCoors);//向缓冲区中放入顶点着色数据
        mTextureBuffer.position(0);//设置缓冲区起始位置
    	return;
    }
	

    public void draw(GL10 gl, int witch, int col, int row)
    {   
    	initVertexBuffer(col, row);	//根据col,row初始化顶点坐标
    	initTextureBuffer(witch);	//根据witch来初始化纹理顶点数据
    	//gl.glTranslatef(col * textureRatio, row * textureRatio, 0);	//在x=col,y=row的位置绘制选定的素材对象        
        //顶点坐标，允许使用顶点数组
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
    		3,				//每个顶点的坐标数量为3  xyz 
    		GL10.GL_FIXED,	//顶点坐标值的类型为 GL_FIXED
    		0, 				//连续顶点坐标数据之间的间隔
    		mVertexBuffer	//顶点坐标数据
        );
        
        //纹理坐标，开启纹理
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //允许使用纹理数组
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //为画笔指定纹理uv坐标数据
        gl.glTexCoordPointer
        (
    		2, 					//每个顶点两个纹理坐标数据 S、T
    		GL10.GL_FLOAT, 		//数据类型
    		0, 					//连续纹理坐标数据之间的间隔
    		mTextureBuffer		//纹理坐标数据
        );        		
        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureId);//为画笔绑定指定名称ID纹理   
        
        //绘制图形
        gl.glDrawArrays
        (
    		GL10.GL_TRIANGLES, 
    		0, 
    		vCount
        );
        gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
    }
}
