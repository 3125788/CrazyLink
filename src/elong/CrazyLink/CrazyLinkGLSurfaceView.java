/**********************************************************
 * 项目名称：山寨“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年10月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/

package elong.CrazyLink;

import elong.CrazyLink.Core.ControlCenter;
import elong.CrazyLink.Interaction.ScreenTouch;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Message;
import android.view.MotionEvent;

public class CrazyLinkGLSurfaceView extends GLSurfaceView{
	
    private SceneRenderer mRenderer;		//场景渲染器
    Context mContext;
    
	static boolean m_bThreadRun = false;
	 
	static ControlCenter controlCenter;
	
	ScreenTouch screenTouch;
	

	
	public CrazyLinkGLSurfaceView(CrazyLinkActivity activity) {
        super(activity);
        mContext = this.getContext();
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
        
        
        
    	if (!m_bThreadRun)
    	{
    		m_bThreadRun = true;
    		controlCenter = new ControlCenter(mContext);
    		//ctlExchange = new CtlExchange(col1, row1, col2, row2);	
    		new Thread()
    		{
    			public void run()
    			{
    				while(true)
    				{
    					try
    			        {
    						controlCenter.run();
    			      	  	Thread.sleep(CrazyLinkConstent.DELAY_MS);//休息50ms
    			        }
    			        catch(Exception e)
    			        {
    			      	  	e.printStackTrace();
    			        }        			  
    				}
    			}
    		}.start();   	    	
    	}
        
    }
	
    //触摸事件回调方法
    @Override public boolean onTouchEvent(MotionEvent e) {
    	if(screenTouch != null)
    	{
    		screenTouch.Touch(e);
    	}
        return true;
    }	
	
	private class SceneRenderer implements GLSurfaceView.Renderer 
    { 

        public void onDrawFrame(GL10 gl) {  
        	gl.glShadeModel(GL10.GL_SMOOTH);		//着色模式为平滑着色
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);//清除颜色缓冲区及深度缓冲区
        	gl.glMatrixMode(GL10.GL_MODELVIEW);		//设置矩阵为模式矩阵
        	gl.glLoadIdentity();					//设置当前矩阵为单位矩阵
        	gl.glTranslatef(0f, 0f, -10f);			        	
        	
        	controlCenter.draw(gl);
        }  
        
        public void onSurfaceChanged(GL10 gl, int width, int height) {
       	
        	screenTouch = new ScreenTouch(mContext, width, height);
        	gl.glViewport(0, 0, width, height);        	//设置当前矩阵为投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);        //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();            			//计算透视投影的比例
           	CrazyLinkConstent.translateRatio = (float) width / height;
        	CrazyLinkConstent.screentRatio = (float) width / height;       //调用此方法计算产生透视投影矩阵
           	//gl.glFrustumf(-CrazyLinkConstent.screentRatio, CrazyLinkConstent.screentRatio, -1, 1, 1, 100);
           	gl.glOrthof(-CrazyLinkConstent.screentRatio*CrazyLinkConstent.GRID_NUM/2, 
           			CrazyLinkConstent.screentRatio*CrazyLinkConstent.GRID_NUM/2, 
           			-1*CrazyLinkConstent.GRID_NUM/2, 
           			1*CrazyLinkConstent.GRID_NUM/2, 10, 100);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        	gl.glDisable(GL10.GL_DITHER);			//关闭抗抖动
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glClearColor(0,0,0,0);            	//设置屏幕背景色黑色RGBA   
            gl.glShadeModel(GL10.GL_SMOOTH);        //设置着色模型为平滑着色
            gl.glEnable(GL10.GL_DEPTH_TEST);		//启用深度测试

            /*********以下为增加透明效果的代码***********/
            /*透明效果对素材图片也有要求，要求图片的背景是透明的*/
        	gl.glEnable(GL10.GL_BLEND);  
        	gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);  
        	gl.glEnable(GL10.GL_ALPHA_TEST);
        	gl.glAlphaFunc(GL10.GL_GREATER,0.1f);
        	/*********透明效果***********/
        	
            controlCenter.initTexture(gl);         
            controlCenter.initDraw(gl);
            
    		Message msg = new Message();
    	    msg.what = ControlCenter.LOADING_START;
    	    ControlCenter.mHandler.sendMessage(msg);

        }
                
    }
	

}


