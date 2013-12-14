/**********************************************************
 * 项目名称：山寨腾讯“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年12月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/

package elong.CrazyLink.Interaction;

import elong.CrazyLink.CrazyLinkConstent;
import elong.CrazyLink.CrazyLinkConstent.E_SCENARIO;
import elong.CrazyLink.Core.ControlCenter;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;

public class ScreenTouch {
	
	Context mContext;
	
	float mPreviousX = 0.0f;
	float mPreviousY = 0.0f;
	
	int mWidth = 0;
	int mHeight = 0;
	int mGridX = 0;
	int mGridY = 0;
	int mStep = 0;
	int mYStart = 0;

	enum TOUCH_DIRECTION
	{
		UP,		
		DOWN,
		LEFT,
		RIGHT,
		INVALID		//方向无效
	}

	
	TOUCH_DIRECTION mMoveDirection = TOUCH_DIRECTION.INVALID;
	
	public ScreenTouch(Context context, int width, int height)
	{
		mContext = context;
		mWidth = width;
		mHeight = height;
		mStep = (int) (width / CrazyLinkConstent.GRID_NUM);
		mYStart = (mHeight - mWidth) / 2;
	}
	
	public boolean touchMenuView(MotionEvent e) {
		switch (e.getAction()) {		
			case MotionEvent.ACTION_UP:
				raiseTouchMenuViewEvent();
				break;
		}
		return true;
	}
	
	public boolean touchResultView(MotionEvent e) {
		switch (e.getAction()) {		
		case MotionEvent.ACTION_UP:
			raiseTouchResultViewEvent();
			break;
		}
		return true;		
	}
	
	//触摸动作
	public boolean touchGameView(MotionEvent e) {

		float y = e.getY();
		float x = e.getX();
		switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mGridX = -1;
				mGridY = -1;
				mMoveDirection = TOUCH_DIRECTION.INVALID;
				if (x >= 0 && x < mStep) mGridX = 0;
				else if(x >= mStep && x < 2*mStep) mGridX = 1;
				else if(x >= 2*mStep && x < 3*mStep) mGridX = 2;
				else if(x >= 3*mStep && x < 4*mStep) mGridX = 3;
				else if(x >= 4*mStep && x < 5*mStep) mGridX = 4;
				else if(x >= 5*mStep && x < 6*mStep) mGridX = 5;
				else if(x >= 6*mStep && x < 7*mStep) mGridX = 6;
				
				if(y >= mYStart && y < mYStart + mStep) mGridY = 6;
				else if(y >= mYStart + mStep && y < mYStart + 2*mStep) mGridY = 5;
				else if(y >= mYStart + 2*mStep && y < mYStart + 3*mStep) mGridY = 4;
				else if(y >= mYStart + 3*mStep && y < mYStart + 4*mStep) mGridY = 3;
				else if(y >= mYStart + 4*mStep && y < mYStart + 5*mStep) mGridY = 2;
				else if(y >= mYStart + 5*mStep && y < mYStart + 6*mStep) mGridY = 1;
				else if(y >= mYStart + 6*mStep && y < mYStart + 7*mStep) mGridY = 0;
				break;
			case MotionEvent.ACTION_UP:
				raiseTouchGameViewEvent();
				break;
			case MotionEvent.ACTION_MOVE:
			    float dy = y - mPreviousY;//计算触控笔Y位移
			    float dx = x - mPreviousX;//计算触控笔Y位移
			    if(Math.abs(dy) > Math.abs(dx))
			    {
			    	if(dy > CrazyLinkConstent.MOVE_THRESDHOLDER) mMoveDirection = TOUCH_DIRECTION.DOWN;
			    	else if(dy < -CrazyLinkConstent.MOVE_THRESDHOLDER) mMoveDirection = TOUCH_DIRECTION.UP;
			    }
			    else
			    {
			    	if(dx > CrazyLinkConstent.MOVE_THRESDHOLDER) mMoveDirection = TOUCH_DIRECTION.RIGHT;
			    	else if(dx < -CrazyLinkConstent.MOVE_THRESDHOLDER) mMoveDirection = TOUCH_DIRECTION.LEFT; 	
			    }
		}
		mPreviousY = y;//记录触控笔位置
		mPreviousX = x;//记录触控笔位置
		return true;
	}	
	
	//获取操作的格子
	int getGridX()
	{
		return mGridX;
	}
	
	//获取操作的格子
	int getGridY()
	{
		return mGridY;
	}

	TOUCH_DIRECTION getDirection()
	{
		return mMoveDirection;
	}
	
	//根据方向信息获取对应的邻居
	int getNeighborX()
	{
		int neighborX = mGridX;
		if (mMoveDirection == TOUCH_DIRECTION.LEFT) neighborX--;
		else if(mMoveDirection == TOUCH_DIRECTION.RIGHT) neighborX++;
		return neighborX;
	}

	//根据方向信息获取对应的邻居
	int getNeighborY()
	{
		int neighborY = mGridY;
		if (mMoveDirection == TOUCH_DIRECTION.DOWN) neighborY--;
		else if(mMoveDirection == TOUCH_DIRECTION.UP) neighborY++;
		return neighborY;
	}
	
	boolean isValidTouchMove()
	{		
		//校验触摸操作是否有效
		if(-1 == mGridX || -1 == mGridY) return false;			
		if((0 == mGridX && mMoveDirection == TOUCH_DIRECTION.LEFT)
				|| (6 == mGridX && mMoveDirection == TOUCH_DIRECTION.RIGHT)
				|| (0 == mGridY && mMoveDirection == TOUCH_DIRECTION.DOWN)
				|| (6 == mGridY && mMoveDirection == TOUCH_DIRECTION.UP)
				|| mMoveDirection == TOUCH_DIRECTION.INVALID)
		{
			return false;
		}
		return true;
	}
	
	void raiseTouchMenuViewEvent()
	{
		ControlCenter.mScore.init();
		ControlCenter.mScore.setLife(CrazyLinkConstent.LIFE_NUM);
		ControlCenter.mTimer.reset();
		ControlCenter.init();
		Message msg = new Message();
		msg.what = ControlCenter.LOADING_START;
	    ControlCenter.mHandler.sendMessage(msg);
	}

	void raiseTouchResultViewEvent()
	{
		ControlCenter.mScene = E_SCENARIO.MENU;
	}

	//产生有效的触摸事件，发消息给mHandler统一处理
	void raiseTouchGameViewEvent()
	{
		int token = ControlCenter.takeToken();
		if(-1 == token) return;
		Message msg = new Message();
		Bundle b = new Bundle();
		int x  = getGridX();
		int y = getGridY();
		if(!(x >= 0 && x < (int)CrazyLinkConstent.GRID_NUM)) return;
		if(!(y >= 0 && y < (int)CrazyLinkConstent.GRID_NUM)) return;
		b.putInt("token", token);
		b.putInt("col1", x);
		b.putInt("row1", y);
		if(isValidTouchMove())	//校验动作是否合法
		{		
			b.putInt("col2", getNeighborX());
			b.putInt("row2", getNeighborY());	
			msg.what = ControlCenter.EXCHANGE_START;
		}
		else
		{
			msg.what = ControlCenter.SCREEN_TOUCH;
		}
		msg.setData(b);
	    ControlCenter.mHandler.sendMessage(msg);
	    System.out.println("touch grid(" + x +"," + y +")");
	}
	
}
