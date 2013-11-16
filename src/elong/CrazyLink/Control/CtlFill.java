/**********************************************************
 * 项目名称：山寨“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年10月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/

package elong.CrazyLink.Control;

import android.os.Message;
import elong.CrazyLink.Core.ControlCenter;
import elong.CrazyLink.Interface.IControl;

public class CtlFill implements IControl{
	int mDeltaY;	
	int mStep = 25;				//偏移步长	
	boolean mStop = false;			//是否停止状态

	public CtlFill()
	{
		mStop = false;
		mDeltaY = 100;		
	}
	
	public void run()
	{
		//将偏移范围限定在-0.5~0.5间，超出指定区间即掉头
		if(!mStop)
		{
			mDeltaY -= mStep;		
			if(mDeltaY <= 0)
			{
				mStop = true;
			}
			if(mStop)
			{
				Message msg = new Message();
			    msg.what = ControlCenter.FILL_END;
			    ControlCenter.mHandler.sendMessage(msg);
			}			
		}
	}
	
	public void start()
	{
		mDeltaY = 100;
		mStop = false;
	}
	
	public float getY()
	{
		return mDeltaY/100.0f;
	}
}

