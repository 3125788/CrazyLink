package elong.CrazyLink.Interface;

public interface IControl {
	void run();
	void start();
	void end();
	boolean isRun();
	void setToken(int token);
	int getToken();
	void sendMsg();
}
