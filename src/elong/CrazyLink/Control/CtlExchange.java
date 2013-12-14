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

public class CtlExchange extends CtlBase{
	int mCol1 = 0;					//坐标值
	int mCol2 = 0;
	int mRow1 = 0;
	int mRow2 = 0;
	
	int mDeltaX1 = 0;				//偏移量
	int mDeltaY1 = 0;
	int mDeltaX2 = 0;
	int mDeltaY2 = 0;
	
	int mStep = 10;				//偏移步长
	boolean mDirectionX = true;   //运动方向控制
	boolean mDirectionY = true;
	boolean mNeedMoveX = false;	//是否需要偏移
	boolean mNeedMoveY = false;	

	public void init(int token, int col1, int row1, int col2, int row2)
	{
		super.setToken(token);
		mNeedMoveX = false;
		mNeedMoveY = false;
		mCol1 = col1;
		mCol2 = col2;
		mRow1 = row1;
		mRow2 = row2;

		if(col1 == col2)
		{
			mDeltaX1 = 0;
			mDeltaX2 = 0;
		}
		else
		{
			//根据坐标位置来决定运动的初始方向
			mNeedMoveX = true;
			if(col1 >  col2)
			{
				mDirectionX = true;
				mDeltaX1 = 40;
			}
			else
			{
				mDirectionX = false;
				mDeltaX1 = -40;
			}
		}
		if(row1 == row2)
		{
			mDeltaY1 = 0;
			mDeltaY2 = 0;
		}
		else
		{
			//根据坐标位置来决定运动的初始方向			
			mNeedMoveY = true;
			if(row1 >  row2)
			{
				mDirectionY = true;
				mDeltaY1 = 40;
			}
			else
			{
				mDirectionY = false;
				mDeltaY1 = -40;
			}
		}
		mStop = false;		
	}
	
	public void run()
	{
		if(mStop) return;
		//将偏移范围限定在-0.5~0.5间，超出指定区间即掉头
		if(mNeedMoveX)
		{
			if(mDeltaX1 >= 50)
			{
				mDirectionX = true;
				mStop = true;
			}
			else if (mDeltaX1 <= -50)
			{
				mDirectionX = false;
				mStop = true;
			}
			if(mDirectionX)
			{
				mDeltaX1 -= mStep;
				mDeltaX2 = -mDeltaX1;
			}
			else
			{
				mDeltaX1 += mStep;
				mDeltaX2 = -mDeltaX1;
			}			
		}

		if(mNeedMoveY)
		{
			if(mDeltaY1 >= 50)
			{
				mDirectionY = true;
				mStop = true;
			}
			else if (mDeltaY1 <= -50)
			{
				mDirectionY = false;
				mStop = true;
			}
			if(mDirectionY)
			{
				mDeltaY1 -= mStep;
				mDeltaY2 = -mDeltaY1;
			}
			else
			{
				mDeltaY1 += mStep;
				mDeltaY2 = -mDeltaY1;
			}			
		}
		
		if(mStop)
		{
			sendMsg();
		}

	}
	
	public float getX1()
	{
		float delta = 0;
		//根据给定坐标设置初始偏移量，将运动范围控制在0~1或0~-1区间
		if(mCol1 > mCol2)
		{
			delta = -0.5f; 
		}
		else if(mCol1 < mCol2)
		{
			delta = 0.5f;
		}
		return delta + mDeltaX1/100.0f;
	}
	
	public float getX2()
	{
		float delta = 0;
		//根据给定坐标设置初始偏移量，将运动范围控制在0~1或0~-1区间
		if(mCol1 > mCol2)
		{
			delta = 0.5f; 
		}
		else if(mCol1 < mCol2)
		{
			delta = -0.5f;
		}
		return delta + mDeltaX2/100.0f;
	}

	public float getY1()
	{
		float delta = 0;
		//根据给定坐标设置初始偏移量，将运动范围控制在0~1或0~-1区间
		if(mRow1 > mRow2)
		{
			delta = -0.5f; 
		}
		else if(mRow1 < mRow2)
		{
			delta = 0.5f;
		}
		return delta + mDeltaY1/100.0f;
	}

	public float getY2()
	{
		float delta = 0;
		//根据给定坐标设置初始偏移量，将运动范围控制在0~1或0~-1区间
		if(mRow1 > mRow2)
		{
			delta = 0.5f; 
		}
		else if(mRow1 < mRow2)
		{
			delta = -0.5f;
		}
		return delta + mDeltaY2/100.0f;
	}
	
	public void sendMsg()
	{
		Bundle b = new Bundle();
		b.putInt("token", mToken);
		setToken(-1);
		b.putInt("col1", mCol1);
		b.putInt("row1", mRow1);
		b.putInt("col2", mCol2);
		b.putInt("row2", mRow2);
		Message msg = new Message();
	    msg.what = ControlCenter.EXCHANGE_END;
		msg.setData(b);
	    ControlCenter.mHandler.sendMessage(msg);
	}
	
}
