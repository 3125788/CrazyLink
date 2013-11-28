package elong.CrazyLink.Core;


import java.util.HashMap;

import elong.CrazyLink.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound {
	
	public enum E_SOUND
	{
		SLIDE,
		FILL,
		DISAPPEAR3,
		DISAPPEAR4,
		DISAPPEAR5,
		READYGO,
		TIMEOVER,
		SUPER,
		COOL,
		BOMB,
		MONSTER,
	}

	
	//音效的音量   
	int streamVolume;   
	  
	//定义SoundPool 对象   
	public SoundPool mSoundPool;    
	  
	//定义HASH表   
	public HashMap<Integer, Integer> mSoundPoolMap;
	
	Context mContext;
	
	public Sound(Context c){

		mContext = c;
		InitSounds();
	}
	private void InitSounds()
	{
		System.out.println("InitSounds");
		initSounds();
	}
	

	private void initSounds() 
	{    
		//初始化soundPool 对象,第一个参数是允许有多少个声音流同时播放,第2个参数是声音类型,第三个参数是声音的品质   
		mSoundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 100);    
		  
		//初始化HASH表   
		mSoundPoolMap = new HashMap<Integer, Integer>();    
		      
		//获得声音设备和设备音量   
		AudioManager mgr = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);   
		streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		
		loadSfx(R.raw.s_readygo, E_SOUND.READYGO.ordinal());
		loadSfx(R.raw.s_timeover, E_SOUND.TIMEOVER.ordinal());
		loadSfx(R.raw.s_bomb, E_SOUND.BOMB.ordinal());
		loadSfx(R.raw.s_cool, E_SOUND.COOL.ordinal());
		loadSfx(R.raw.s_disappear3, E_SOUND.DISAPPEAR3.ordinal());
		loadSfx(R.raw.s_disappear4, E_SOUND.DISAPPEAR4.ordinal());
		loadSfx(R.raw.s_disappear5, E_SOUND.DISAPPEAR5.ordinal());
		loadSfx(R.raw.s_slide, E_SOUND.SLIDE.ordinal());
		loadSfx(R.raw.s_super, E_SOUND.SUPER.ordinal());
		loadSfx(R.raw.s_fill, E_SOUND.FILL.ordinal());
		loadSfx(R.raw.s_monster, E_SOUND.MONSTER.ordinal());
	}   
	     
	//加载音效资源  
	private void loadSfx(int raw, int id) {   
	   //把资源中的音效加载到指定的ID(播放的时候就对应到这个ID播放就行了)   
		mSoundPoolMap.put(id, mSoundPool.load(mContext, raw, id));    
	}       
	  
	//sound:要播放的音效的ID, loop:循环次数  
	private void play(E_SOUND sound, int loop) {
		int id = sound.ordinal();
		mSoundPool.play(mSoundPoolMap.get(id), streamVolume, streamVolume, 1, loop, 1f);    
	}
	
	void play(E_SOUND sound)
	{
		play(sound, 0);
	}


}

