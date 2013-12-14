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

import elong.CrazyLink.CrazyLinkConstent.E_SOUND;
import elong.CrazyLink.Core.ControlCenter;

public class CtlTip1 extends CtlBase{
	
	int mDeltaW = 0;
	int mDeltaH = 0;
	int mDeltaX = 0;
	int mDeltaY = 0;
	int mStep = 0;
	int mKeep = 0;
	int mPicId = 0;
	int mGoodCnt = 0;
	
	public void run()
	{
		int maxW = 96;
		int minW = 16;
		int deltaW = 8;
		int deltaH = 2;
		if(!mStop)
		{
			if(0 == mStep)
			{
				mDeltaW += deltaW;
				mDeltaH += deltaH;
				mDeltaX ++;
				mDeltaY ++;
				if (mDeltaW >= maxW)
				{
					mStep = 1;
				}
			}
			else if(1 == mStep)
			{
				mKeep++;
				if(mKeep >= 10)
				{
					mKeep = 0;
					mStep = 2;
				}				
			}
			else if(2 == mStep)
			{
				mDeltaW -= deltaW;
				mDeltaH -= deltaH;
				mDeltaX --;
				mDeltaY --;
				if (mDeltaW <= minW)
				{
					mStep = 3;
				}				
			}
			else if(3 == mStep)
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
	
	public int getX()
	{
		return mDeltaX;
	}
	
	public int getY()
	{
		return mDeltaY;
	}
	
	public void init(int clearCnt)
	{
		if(clearCnt < 3) return;
		if(!mStop) return;
		mDeltaW = 0;
		mDeltaH = 0;
		mDeltaX = 0;
		mDeltaY = 0;
		mStep = 0;
		
		if(clearCnt >6) clearCnt = 6;
		mPicId = clearCnt - 3;
		if(3 == clearCnt)
		{
			mGoodCnt++;
			if(1 == mGoodCnt % 5)
			{
				super.start();      //连续消除5次3个动物才提示一次GOOD
				ControlCenter.mSound.play(E_SOUND.GOOD);
			}
		}
		else
		{
			//其他的每次都提示
			super.start();
		}
	}
		
	public int getPicId()
	{
		return mPicId;
	}
}
