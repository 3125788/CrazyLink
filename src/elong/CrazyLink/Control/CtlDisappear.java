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

public class CtlDisappear extends CtlBase{

	int mCount = 6;
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
				Message msg = new Message();
			    msg.what = ControlCenter.DISAPPEAR_END;
			    ControlCenter.mHandler.sendMessage(msg);
			}
		}
	}
	
	public void start()
	{
		mCount = 10;
		super.start();
	}
	
	public int getCount()
	{
		return mCount;
	}

}

