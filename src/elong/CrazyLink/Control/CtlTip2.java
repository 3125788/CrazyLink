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

import android.os.Message;
import elong.CrazyLink.Core.ControlCenter;
import elong.CrazyLink.CrazyLinkConstent.E_TIP;

public class CtlTip2 extends CtlBase{
	
	int mDeltaW = 0;
	int mDeltaH = 0;
	int mStep = 0;
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
			if(0 == mStep)
			{
				mDeltaW += deltaW;
				mDeltaH += deltaH;
				if (mDeltaW >= maxW)
				{
					mStep = 1;
				}
			}
			else if(1 == mStep)
			{
				int keep = 10;
				if(E_TIP.GAMEOVER.ordinal() == mPicId)
				{
					keep = 40;
				}
				mKeep++;
				if(mKeep >= keep)
				{
					mKeep = 0;
					mStep = 2;
				}				
			}
			else if(2 == mStep)
			{
				mDeltaW -= deltaW;
				mDeltaH -= deltaH;
				if (mDeltaW <= minW)
				{
					mStep = 3;
				}				
			}
			else if(3 == mStep)
			{
				mStop = true;
				sendMsg();
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
		mStep = 0;
		
		mPicId = pic;
		super.start();
	}
		
	public int getPicId()
	{
		return mPicId;
	}
	
	public void sendMsg()
	{
		if(E_TIP.GAMEOVER.ordinal() == mPicId)
		{
			Message msg = new Message();
		    msg.what = ControlCenter.GAME_OVER_END;
		    ControlCenter.mHandler.sendMessage(msg);
		}
	}
}
