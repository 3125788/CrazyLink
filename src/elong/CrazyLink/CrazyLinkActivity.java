/**********************************************************
 * 项目名称：山寨“爱消除”游戏7日教程
 * 作          者：郑敏新
 * 腾讯微博：SuperCube3D
 * 日          期：2013年10月
 * 声          明：版权所有   侵权必究
 * 本源代码供网友研究学习OpenGL ES开发Android应用用，
 * 请勿全部或部分用于商业用途
 ********************************************************/


package elong.CrazyLink;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;

public class CrazyLinkActivity extends Activity {	
	CrazyLinkGLSurfaceView mGLSurfaceView;
	private MediaPlayer mp;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	//设置为直屏
              
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

			// R.raw.mmp是资源文件，MP3格式的
			mp = MediaPlayer.create(this, R.raw.s_background);
			mp.setLooping(true);
			mp.start();

			}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mGLSurfaceView.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mGLSurfaceView.onPause();
		mp.pause();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mp.stop();
	}
}