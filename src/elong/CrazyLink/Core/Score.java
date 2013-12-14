/**********************************************************
 * 项目名称：山寨腾讯“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年12月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/

package elong.CrazyLink.Core;

import elong.CrazyLink.CrazyLinkConstent;
import elong.CrazyLink.CrazyLinkConstent.E_SOUND;
import android.os.Message;

public class Score {
	int mTotalScore = 0;	//总成绩
	int mAwardScore = 0;	//当次的奖励
	float mAwardRatio = 0;	//奖励倍数
	int mContinueCnt = 0;	//连续消除次数
	
	int mOver3 = 0;			//超过3个
	int mJust3 = 0;			//等于3个
	
	int mLife = CrazyLinkConstent.LIFE_NUM;
	
	public Score()
	{
		init();
	}
	
	public void init()
	{
		mTotalScore = 0;
		mAwardScore = 0;
		mAwardRatio = 0;
		mContinueCnt = 0;
	}
	
	//奖励规则，可以自己调整
	public void award(int clearNum)
	{
		int award = 0;
		switch (clearNum)
		{
		case 3:
			award = 100;
			break;
		case 4:
			award = 500;
			break;
		case 5:
			award = 1000;
			break;
		case 6:
			award = 5000;
			break;
		case 7:
			award = 10000;
			break;
		case 8:
			award = 50000;
			break;
		case 9:
			award = 100000;
			break;
		case 10:
			award = 500000;
			break;
		case 11:
			award = 1000000;
			break;
		default:
			if(clearNum > 11)
			{
				award = 500000 * clearNum;
			}
			break;
		}
		awardScore(mAwardRatio*award);
	}
	
	//本次奖励的分数
	public void awardScore(float score)
	{
		mAwardScore = (int)score;
		mTotalScore += (int)score;
	}
	
	//获取累计分数
	public int getScore()
	{
		return mTotalScore;
	}
	
	//获取本次得分
	public int getAward()
	{
		return mAwardScore;
	}
	
	//获取连续消除的次数
	public int getContinueCnt()
	{
		return mContinueCnt;
	}
	
	//复位分数系数
	public void reset()
	{
		mAwardRatio = 0;
		mContinueCnt = 0;
		lifeDelMsg();
	}
	
	//递增分数系数
	public void increase()
	{
		mAwardRatio++;
		mContinueCnt++;
	}
	
	//根据一次性消除的个数增加分数系数
	public void increase(int clearNum)
	{
		mAwardRatio += (float)clearNum / 5;
		if (clearNum > CrazyLinkConstent.LIFE_UP)
		{
			lifeAddMsg();	//增加生命
		}
	}
	
	public void calcTotal(int clearNum)
	{
		if(clearNum > 3)
		{
			mOver3++;
			if(1 == mOver3 % CrazyLinkConstent.MONSTER_APPEAR)
			{
				//生成一个MONSTER
				Message msg = new Message();
				msg.what = ControlCenter.GEN_SPECIALANIMAL;
			    ControlCenter.mHandler.sendMessage(msg);	
			}				
		}
		else
		{
			mJust3++;
			if((CrazyLinkConstent.MONSTER_APPEAR * 3 - 1) == mJust3 % (CrazyLinkConstent.MONSTER_APPEAR * 3))
			{
				//生成一个MONSTER
				Message msg = new Message();
				msg.what = ControlCenter.GEN_SPECIALANIMAL;
			    ControlCenter.mHandler.sendMessage(msg);	
			}							
		}

	}
	
	void increaseLife()
	{
		mLife++;
		ControlCenter.mSound.play(E_SOUND.LIFEADD);
	}
	
	void lifeAddMsg()
	{
		Message msg = new Message();
		msg.what = ControlCenter.LIFEADD_START;
	    ControlCenter.mHandler.sendMessage(msg);			
	}
	
	void decreaseLife()
	{
		mLife--;
		ControlCenter.mSound.play(E_SOUND.LIFEDEL);
		if(0 == mLife)
		{
			Message msg = new Message();
			msg.what = ControlCenter.GAME_OVER_START;
		    ControlCenter.mHandler.sendMessage(msg);				
		}

	}
	
	void lifeDelMsg()
	{
		Message msg = new Message();
		msg.what = ControlCenter.LIFEDEL_START;
	    ControlCenter.mHandler.sendMessage(msg);			
	}
	
	
	int getLife()
	{
		return mLife;
	}
	
	public void setLife(int life)
	{
		mLife = life;
	}

}
