/**********************************************************
 * ��Ŀ��ƣ�ɽկ���������Ϸ7�ս̳�
 * ��          �ߣ�֣����
 * ��Ѷ΢����SuperCube3D
 * ��          �ڣ�2013��10��
 * ��          ������Ȩ����   ��Ȩ�ؾ�
 * ��Դ���빩�����о�ѧϰOpenGL ES����AndroidӦ���ã�
 * ����ȫ���򲿷�������ҵ��;
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
	
    private SceneRenderer mRenderer;		//������Ⱦ��
    Context mContext;
    
	static boolean m_bThreadRun = false;
	 
	static ControlCenter controlCenter;
	
	ScreenTouch screenTouch;
	

	
	public CrazyLinkGLSurfaceView(CrazyLinkActivity activity) {
        super(activity);
        mContext = this.getContext();
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ
        
        
        
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
    			      	  	Thread.sleep(CrazyLinkConstent.DELAY_MS);//��Ϣ50ms
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
	
    //�����¼��ص�����
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
        	gl.glShadeModel(GL10.GL_SMOOTH);		//��ɫģʽΪƽ����ɫ
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);//�����ɫ��������Ȼ�����
        	gl.glMatrixMode(GL10.GL_MODELVIEW);		//���þ���Ϊģʽ����
        	gl.glLoadIdentity();					//���õ�ǰ����Ϊ��λ����
        	gl.glTranslatef(0f, 0f, -10f);			        	
        	
        	controlCenter.draw(gl);
        }  
        
        public void onSurfaceChanged(GL10 gl, int width, int height) {
       	
           	CrazyLinkConstent.translateRatio = (float) width / height;
        	CrazyLinkConstent.screentRatio = (float) width / height;       //���ô˷����������͸��ͶӰ����
   			CrazyLinkConstent.ADP_SIZE = CrazyLinkConstent.UNIT_SIZE * CrazyLinkConstent.VIEW_HEIGHT/height * width/CrazyLinkConstent.VIEW_WIDTH;   			
        	screenTouch = new ScreenTouch(mContext, width, height);        	
        	gl.glViewport(0, 0, width, height);        	//���õ�ǰ����ΪͶӰ����
            gl.glMatrixMode(GL10.GL_PROJECTION);        //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity();            			//����͸��ͶӰ�ı���
           	
           	gl.glOrthof(-CrazyLinkConstent.screentRatio*CrazyLinkConstent.GRID_NUM/2, 
           			CrazyLinkConstent.screentRatio*CrazyLinkConstent.GRID_NUM/2, 
           			-1*CrazyLinkConstent.GRID_NUM/2, 
           			1*CrazyLinkConstent.GRID_NUM/2, 10, 100);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        	gl.glDisable(GL10.GL_DITHER);			//�رտ�����
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//�����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ
            gl.glClearColor(0,0,0,0);            	//������Ļ����ɫ��ɫRGBA   
            gl.glShadeModel(GL10.GL_SMOOTH);        //������ɫģ��Ϊƽ����ɫ
            gl.glEnable(GL10.GL_DEPTH_TEST);		//������Ȳ���

            /*********����Ϊ����͸��Ч��Ĵ���***********/
            /*͸��Ч����ز�ͼƬҲ��Ҫ��Ҫ��ͼƬ�ı�����͸����*/
        	gl.glEnable(GL10.GL_BLEND);  
        	gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);  
        	gl.glEnable(GL10.GL_ALPHA_TEST);
        	gl.glAlphaFunc(GL10.GL_GREATER,0.1f);
        	/*********͸��Ч��***********/
        	
            controlCenter.initTexture(gl);         
            controlCenter.initDraw(gl);
            
    		Message msg = new Message();
    	    msg.what = ControlCenter.LOADING_START;
    	    ControlCenter.mHandler.sendMessage(msg);

        }
                
    }
	

}


