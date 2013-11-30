
/**********************************************************
 * 项目名称：山寨“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年11月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/
package elong.CrazyLink.Control;

public class CtlTip2 extends CtlBase{
	
	int mDeltaW = 0;
	int mDeltaH = 0;
	int mDirection = 0;
	int mKeep = 0;
	int mPicId = 0;
	
	public void run()
	{
		int maxW = 128;
		int minW = 32;
		int deltaW = 8;
		int deltaH = 2;
		if(!mStop)
		{
			if(0 == mDirection)
			{
				mDeltaW += deltaW;
				mDeltaH += deltaH;
				if (mDeltaW >= maxW)
				{
					mDirection = 1;
				}
			}
			else if(1 == mDirection)
			{
				mKeep++;
				if(mKeep >= 10)
				{
					mKeep = 0;
					mDirection = 2;
				}				
			}
			else if(2 == mDirection)
			{
				mDeltaW -= deltaW;
				mDeltaH -= deltaH;
				if (mDeltaW <= minW)
				{
					mDirection = 3;
				}				
			}
			else if(3 == mDirection)
			{
				mStop = true;
			}
		}		
	}
	
	public int getW()
	{
		return mDeltaW;
	}
	
	public int getH()
	{
		return mDeltaH;
	}
	
	
	public void init(int pic)
	{
		if(pic > 3) return;
		if(!mStop) return;
		mDeltaW = 0;
		mDeltaH = 0;
		mDirection = 0;
		
		mPicId = pic;
		super.start();
	}
		
	public int getPicId()
	{
		return mPicId;
	}
}
