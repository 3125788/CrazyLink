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

import elong.CrazyLink.Interface.IControl;


public class CtlBase implements IControl{
	public int mToken = 0;
	public boolean mStop = true;

	public boolean isRun() {
		return !mStop;
	}

	public void run() {
		
	}

	public void start() {
		mStop = false;
	}

	public void end() {
		mStop = true;
	}
	
	public void setToken(int token)
	{
		mToken = token;
	}
	
	public int getToken()
	{
		return mToken;
	}

	public void sendMsg() {
		
	}
}
