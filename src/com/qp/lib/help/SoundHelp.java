package com.qp.lib.help;

import java.util.HashMap;

import android.media.AudioManager;
import android.media.SoundPool;

import com.qp.lib.main.AppMain;
import com.qp.lib.utility.Util;

public class SoundHelp {

	@SuppressWarnings("unused")
	private static final String				TAG				= "SoundHelp";
	 
	private final SoundPool					soundPool		= new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	private final HashMap<Integer, Integer>	soundPoolMap	= new HashMap<Integer, Integer>();
 
	private int streamVolume=0;
 
 
	public SoundHelp( ) {
		streamVolume = 100;
	}

	public void loadSfx(int raw, int ID) {
		int value = soundPool.load(AppMain.getInstance(), raw, ID);
		if (value != -1)
			soundPoolMap.put(Integer.valueOf(ID), Integer.valueOf(value));
		else
			Util.e("SoundPool", "bad-sound");
	}
	public int getVolume(){
//		AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);   
//	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
//	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
//	    return streamVolumeCurrent/streamVolumeMax;   
		return streamVolume ;
	}
	public void play(int sound) {
	    int sd=soundPoolMap.get(sound);
		if(sd!=-1)     
			soundPool.play( sd  , streamVolume/ 100.f  , streamVolume/ 100.f , 1, 0, 1.0F);
		else
			Util.e("SoundPool", "bad-sound");
	}

	public void play(int sound, int loop) {
		int sd =  soundPoolMap.get( sound )  ;
		if (sd != -1)
			soundPool.play(sd,  streamVolume/ 100.f  ,  streamVolume/ 100.f  , 1, loop, 1.0F);
		else
			Util.e("SoundPool", "bad-sound");
	}

	public void setVolume(int volume) {
		 
		streamVolume = volume;// % 100;
		Util.v(TAG,"setVolume "+volume);
	}
 
}
