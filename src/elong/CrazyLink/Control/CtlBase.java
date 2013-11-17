package elong.CrazyLink.Control;

import elong.CrazyLink.Interface.IControl;


public class CtlBase implements IControl{

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
	

}
