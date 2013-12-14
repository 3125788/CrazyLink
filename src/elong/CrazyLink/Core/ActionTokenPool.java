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

public class ActionTokenPool {
	
	boolean[] mTokenInused;

	public ActionTokenPool()
	{
		mTokenInused = new boolean[CrazyLinkConstent.MAX_TOKEN];
		for(int i = 0; i < CrazyLinkConstent.MAX_TOKEN; i++)
		{
			mTokenInused[i] = false;
		}
	}
	
	synchronized int takeToken()
	{
		for(int i = 0; i < CrazyLinkConstent.MAX_TOKEN; i++)
		{
			if (!mTokenInused[i])
			{
				mTokenInused[i] = true;
				return i;
			}
		}
		return -1;
	}
	
	synchronized void freeToken(int token)
	{
		if(token >= 0 && token < CrazyLinkConstent.MAX_TOKEN) mTokenInused[token] = false;
	}
}
