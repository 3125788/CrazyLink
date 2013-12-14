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
import android.os.Bundle;
import android.os.Message;
import elong.CrazyLink.Core.ControlCenter;

public class CtlDisappear extends CtlBase{

	int mCount = 0;
	int mTimeCnt = 0;

	public void run()
	{
		if (!mStop)
		{
			mTimeCnt++;
			if (1 == (mTimeCnt % 5)) return;		//降频
			mCount--;
			if(0 == mCount)
			{
				mStop = true;
				sendMsg();
			}
		}
	}
	
	public void start()
	{
		if(0 == mCount) mCount = 10;
		super.start();
	}
	
	public int getCount()
	{
		return mCount;
	}

	public void sendMsg()
	{
		Bundle b = new Bundle();
		b.putInt("token", mToken);
		setToken(-1);
		Message msg = new Message();
	    msg.what = ControlCenter.DISAPPEAR_END;
	    msg.setData(b);
	    ControlCenter.mHandler.sendMessage(msg);
	}
}

