/**********************************************************
 * ��Ŀ���ƣ�ɽկ������������Ϸ7�ս̳�
 * ��          �ߣ�֣����
 * ��Ѷ΢����SuperCube3D
 * ��          �ڣ�2013��10��
 * ��          ������Ȩ����   ��Ȩ�ؾ�
 * ��Դ���빩�����о�ѧϰOpenGL ES����AndroidӦ���ã�
 * ����ȫ���򲿷�������ҵ��;
 ********************************************************/

package elong.CrazyLink.Control;

import android.os.Message;
import elong.CrazyLink.Core.ControlCenter;

public class CtlLoading extends CtlBase{
	
	int mPicId = 0;
	int mTimeCnt = 0;

	public void run()
	{
		if(mStop) return;
		mTimeCnt++;
		if (1 == (mTimeCnt % 2)) return;		//��Ƶ
		mPicId++;
		if (mPicId >= 10) mPicId = 0;
		if(mTimeCnt > 60)
		{
			mTimeCnt = 0;
			mStop = true;
			sendMsg();
		}
	}
	
	public int getPicId()
	{
		return mPicId;
	}		
	
	public void sendMsg()
	{
		Message msg = new Message();
	    msg.what = ControlCenter.LOADING_END;
	    ControlCenter.mHandler.sendMessage(msg);
	}
	
}
