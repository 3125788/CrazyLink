/**********************************************************
 * ��Ŀ���ƣ�ɽկ������������Ϸ7�ս̳�
 * ��          �ߣ�֣����
 * ��Ѷ΢����SuperCube3D
 * ��          �ڣ�2013��10��
 * ��          ������Ȩ����   ��Ȩ�ؾ�
 * ��Դ���빩�����о�ѧϰOpenGL ES����AndroidӦ���ã�
 * ����ȫ���򲿷�������ҵ��;
 ********************************************************/


package elong.CrazyLink;

import elong.CrazyLink.Core.ControlCenter;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class CrazyLinkActivity extends Activity {	
	CrazyLinkGLSurfaceView mGLSurfaceView;
	private MediaPlayer mp;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	//����Ϊֱ��
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mGLSurfaceView = new CrazyLinkGLSurfaceView(this);
        setContentView(mGLSurfaceView);
        mGLSurfaceView.requestFocus();
        mGLSurfaceView.setFocusableInTouchMode(true);
    }

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(mp==null){

			// R.raw.mmp����Դ�ļ���MP3��ʽ��
			mp = MediaPlayer.create(this, R.raw.s_background);
			mp.setLooping(true);
			mp.start();

			}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		ControlCenter.mTimer.pause();
		super.onStop();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mGLSurfaceView.onResume();
		ControlCenter.mTimer.resume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mGLSurfaceView.onPause();
		mp.pause();
		ControlCenter.mTimer.pause();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mp.stop();
	}
}