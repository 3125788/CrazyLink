/**********************************************************
 * 项目名称：山寨腾讯“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年12月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/

package elong.CrazyLink.Control;

public class CtlSingleScore extends CtlBase{
	
	int mDeltaY = 0;
	int mDelta = 1;
	int mCount = 0;
	
	public void run()
	{
		if(!mStop)
		{
			mCount++;
			
			if (mCount > 30) mStop = true;
			if (mCount < 20) mDeltaY += mDelta; 
		}		
	}
	
	
	public int getY()
	{
		return mDeltaY;
	}
	
	public void start()
	{
		mDeltaY = 0;
		mCount = 0;
		super.start();
	}
		
}

