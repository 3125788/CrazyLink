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


//自动提示效果
public class CtlExplosion extends CtlBase{
	
	int mPicId = 1;
	int mTimeCnt = 0;

	public void run()
	{
		if(mStop) return;
		mTimeCnt++;
		if (1 == (mTimeCnt % 2)) return;		//降频
		mPicId++;
		if (mPicId > 10) mPicId = 1;
	}
	
	public int getPicId()
	{
		return mPicId;
	}		
	
	public void start()
	{
		mPicId = 1;
		mStop = false;
	}
	
}

