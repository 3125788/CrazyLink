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
