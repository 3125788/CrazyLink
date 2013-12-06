/**********************************************************
 * ��Ŀ���ƣ�ɽկ������������Ϸ7�ս̳�
 * ��          �ߣ�֣����
 * ��Ѷ΢����SuperCube3D
 * ��          �ڣ�2013��11��
 * ��          ������Ȩ����   ��Ȩ�ؾ�
 * ��Դ���빩�����о�ѧϰOpenGL ES����AndroidӦ���ã�
 * ����ȫ���򲿷�������ҵ��;
 ********************************************************/
package elong.CrazyLink.Core;

import android.os.Message;

public class Timer {
	
	long mMaxTime = 0;	//���ʱ��
	long mLeftTime = 0;	//ʣ�µ�ʱ��
	long mUsedTime = 0;	//�Ѿ�ʹ�õ�ʱ��
	long mStartTime;
	boolean mStop = true;
	
	
	public Timer(int maxTime)
	{
		mMaxTime = maxTime;
		mLeftTime = maxTime;
		mStop = true;
	}
	
	public void start()
	{
		if(mLeftTime > 0)
		{
			mStartTime = System.currentTimeMillis()/1000 - mUsedTime;
			mStop = false;
		}
	}
	
	public void pause()
	{
		if(mLeftTime > 0)
		{
			mUsedTime = System.currentTimeMillis()/1000 - mStartTime;
			mStop = true;
		}
	}
	
	public void resume()
	{
		if(mLeftTime > 0)
		{
			mStartTime = System.currentTimeMillis()/1000 - mUsedTime;
			mStop = false;
		}
	}
	
	public int getLeftTime()
	{
		if(!mStop)
		{
			mUsedTime = System.currentTimeMillis()/1000 - mStartTime;
			mLeftTime = mMaxTime - mUsedTime;
			if(0 == mLeftTime)
			{
				mStop = true;
				Message msg = new Message();
				msg.what = ControlCenter.GAME_OVER;
			    ControlCenter.mHandler.sendMessage(msg);
			}

		}
		return (int)mLeftTime;
	}

}
