/**********************************************************
 * ��Ŀ���ƣ�ɽկ������������Ϸ7�ս̳�
 * ��          �ߣ�֣����
 * ��Ѷ΢����SuperCube3D
 * ��          �ڣ�2013��10��
 * ��          ������Ȩ����   ��Ȩ�ؾ�
 * ��Դ���빩�����о�ѧϰOpenGL ES����AndroidӦ���ã�
 * ����ȫ���򲿷�������ҵ��;
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
		INVALID		//������Ч
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
	
	//��������
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
			    float dy = y - mPreviousY;//���㴥�ر�Yλ��
			    float dx = x - mPreviousX;//���㴥�ر�Yλ��
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
		mPreviousY = y;//��¼���ر�λ��
		mPreviousX = x;//��¼���ر�λ��
		return true;
	}	
	
	//��ȡ�����ĸ���
	int getGridX()
	{
		return mGridX;
	}
	
	//��ȡ�����ĸ���
	int getGridY()
	{
		return mGridY;
	}

	TOUCH_DIRECTION getDirection()
	{
		return mMoveDirection;
	}
	
	//���ݷ�����Ϣ��ȡ��Ӧ���ھ�
	int getNeighborX()
	{
		int neighborX = mGridX;
		if (mMoveDirection == TOUCH_DIRECTION.LEFT) neighborX--;
		else if(mMoveDirection == TOUCH_DIRECTION.RIGHT) neighborX++;
		return neighborX;
	}

	//���ݷ�����Ϣ��ȡ��Ӧ���ھ�
	int getNeighborY()
	{
		int neighborY = mGridY;
		if (mMoveDirection == TOUCH_DIRECTION.DOWN) neighborY--;
		else if(mMoveDirection == TOUCH_DIRECTION.UP) neighborY++;
		return neighborY;
	}
	
	boolean isValidTouchMove()
	{		
		//У�鴥�������Ƿ���Ч
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
		ControlCenter.mTimer.reset();
		ControlCenter.mLife = CrazyLinkConstent.LIFE_NUM;
		ControlCenter.init();
		Message msg = new Message();
		msg.what = ControlCenter.LOADING_START;
	    ControlCenter.mHandler.sendMessage(msg);
	}

	void raiseTouchResultViewEvent()
	{
		ControlCenter.mScene = E_SCENARIO.MENU;
	}

	//������Ч�Ĵ����¼�������Ϣ��mHandlerͳһ����
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
		if(isValidTouchMove())	//У�鶯���Ƿ�Ϸ�
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
