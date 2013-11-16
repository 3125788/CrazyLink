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

public class CtlLoading implements IControl{
	
	int mPicId = 0;
	int mTimeCnt = 0;
	boolean mStop = true;

	public void run()
	{
		if(mStop) return;
		mTimeCnt++;
		if (1 == (mTimeCnt % 2)) return;		//降频
		mPicId++;
		if (mPicId >= 10) mPicId = 0;
		if(mTimeCnt > 60)
		{
			mStop = true;
			Message msg = new Message();
		    msg.what = ControlCenter.LOADING_END;
		    ControlCenter.mHandler.sendMessage(msg);
		}
	}
	
	public int getPicId()
	{
		return mPicId;
	}		
	
	public void start()
	{
		mStop = false;
	}
	
	public boolean isRun()
	{
		return !mStop;
	}
}
