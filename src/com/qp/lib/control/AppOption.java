package com.qp.lib.control;

import com.qp.lib.help.MusicHelp;
import com.qp.lib.help.SoundHelp;
import com.qp.lib.interface_ex.option.IOptionControl;
import com.qp.lib.main.AppMain;
import com.qp.lib.utility.Util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.util.Log;
import android.view.*;

public class AppOption implements IOptionControl {

	public static final String	TAG		= "AppOption";
	long						lGameVersion;
	long						lAppVersion;
	String						szMachineID;
	String						szPhoneNum;
	byte						cbDeviceType;
	int							nKindID;
	int							nMaxVoice;
	public int					nSceneWidth;
	public int					nSceneHeight;
	public String				szName;
	int							nViewCount;
	MusicHelp					bgm;
	SoundHelp					soundhelp;
	private boolean				bMusic	= true;
	private boolean				bSound	= true;

	int							m_nSystemMusic;
	int							m_nCurMusic;

	public AppOption() {
		szMachineID = "";
		szPhoneNum = "";
		szName = "";
	}

	public void onInitConfig(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		nSceneWidth = display.getWidth();
		nSceneHeight = display.getHeight();
		if (nSceneWidth >= 800 && nSceneHeight >= 480)
			cbDeviceType = 19;
		else if (nSceneWidth >= 480 && nSceneHeight >= 320)
			cbDeviceType = 18;
		else if (nSceneWidth >= 320 && nSceneHeight >= 240)
			cbDeviceType = 17;
		else
			cbDeviceType = 0;

		szMachineID = "szMachineID";//Util.getDeviceID(AppMain.getInstance());

		bgm = new MusicHelp();
		soundhelp = new SoundHelp();
		AudioManager am = (AudioManager) activity.getBaseContext().getSystemService("audio");
		nMaxVoice = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		m_nSystemMusic = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		m_nCurMusic = m_nSystemMusic;
		soundhelp.setVolume(m_nSystemMusic * 100 / nMaxVoice);
		bgm.setVolume(m_nSystemMusic * 100 / nMaxVoice);
		
		
		//get
		SharedPreferences sp=  activity.getBaseContext().getSharedPreferences("shared_file", 0);

		int sdvol=  sp.getInt( "sound", 10);
		soundhelp.setVolume(sdvol);
		int  bgmvol=  sp.getInt("bgm", 10);
		bgm.setVolume(bgmvol);
		bMusic= sp.getBoolean("bMusic", true);
		bSound= sp.getBoolean("bSound", true);
		
		android.provider.Settings.System.putInt(activity.getContentResolver(), "screen_brightness_mode", 0);
		Util.d(TAG, "[DeviceType:" + cbDeviceType + "][szMachineID:" + szMachineID + "][m_nCurMusic:" + m_nCurMusic+"]");
	}

	public void onSaveConfig(Activity activity) {
		//AudioManager am = (AudioManager) activity.getBaseContext().getSystemService("audio");
		//am.setStreamVolume(AudioManager.STREAM_MUSIC, m_nSystemMusic, 0);
		SharedPreferences    sp= activity.getBaseContext().getSharedPreferences("shared_file", 0);
		 if(soundhelp!=null && bgm!=null)
		 {
			 sp.edit().putInt("sound", soundhelp.getVolume()).commit();
			 sp.edit().putInt("bgm", bgm.getVolume() ).commit();
			 
			 sp.edit().putBoolean("bMusic", bMusic ).commit();
			 sp.edit().putBoolean("bSound", bSound ).commit();
			  
		 }
	}

	public boolean isFirstStart() {
		return false;
	}

	public int getWidth() {
		return nSceneWidth;
	}

	public int getHeight() {
		return nSceneHeight;
	}

	public byte getDeviceType() {
		return cbDeviceType;
	}

	public boolean setMusic(boolean boff) {
		bMusic = boff;
		if (bgm.hasmusic()) {
			if (bMusic)
				bgm.Play();
			else
				bgm.Pause();
		}
		Util.d(TAG, "setMusic-" + bMusic + "-" + bgm.getVolume());
		return true;
	}

	public boolean setSound(boolean boff) {
		bSound = boff;
		return true;
	}

	public boolean setShake(boolean boff) {

		return true;
	}

	public boolean setBeginner(boolean boff) {

		return true;
	}

	public boolean setLight(int nlight) {
		android.view.WindowManager.LayoutParams lp = AppMain.getInstance().getWindow().getAttributes();
		lp.screenBrightness = Math.max(0.1F, (float) nlight / 100F);
		AppMain.getInstance().getWindow().setAttributes(lp);
		return true;
	}

	public boolean setMusicVoice(int voice) {
		if (m_nCurMusic != nMaxVoice) {
			m_nCurMusic = nMaxVoice;
		//	AudioManager am = (AudioManager) AppMain.getInstance().getBaseContext().getSystemService("audio");
		//	am.setStreamVolume(AudioManager.STREAM_MUSIC, m_nCurMusic, 0);
		}
		bgm.setVolume(voice);
		Util.d(TAG, "setMusicVoice-" + bMusic + "-" + bgm.getVolume());
		return true;
	}
	public boolean setSoundVoice(int voice) {
		if (m_nCurMusic != nMaxVoice) {
			m_nCurMusic = nMaxVoice;
		//	AudioManager am = (AudioManager) AppMain.getInstance().getBaseContext().getSystemService("audio");
		//	am.setStreamVolume(AudioManager.STREAM_MUSIC, m_nCurMusic, 0);
		}
		soundhelp.setVolume(voice);
		return true;
	}

	public boolean getMusic() {
		return bMusic;
	}

	public boolean getSound() {
		return bSound;
	}

	public boolean getShake() {
		return false;
	}

	public boolean getBeginner() {
		return false;
	}

	public int getLight() {
		int value = 0;
		android.view.WindowManager.LayoutParams lp = AppMain.getInstance().getWindow().getAttributes();
		if (lp.screenBrightness < 0.0F)
			value = 100;
		else if (lp.screenBrightness <= 0.1F)
			value = 0;
		else
			value = (int) Math.min(lp.screenBrightness * 100F, 100F);
		return value;
	}

	public int getMusicVoice() {
		return bgm.getVolume();
	}

	public int getSoundVoice() {
		return soundhelp.getVolume();
	}

	public long getGameVersion() {
		return lGameVersion;
	}

	public long getAppVersion() {
		return lAppVersion;
	}

	public boolean setGameVersion(long gameversion) {
		lGameVersion = gameversion;
		return true;
	}

	public boolean setAppVersion(long appversion) {
		lAppVersion = appversion;
		return true;
	}

	public String getMachineID() {
		return szMachineID;
	}

	public int getKindId() {
		return nKindID;
	}

	public boolean setKindID(int id) {
		nKindID = id;
		return true;
	}

	public String getPhoneNum() {
		return szPhoneNum;
	}

	public void setMachineID(String szmachine) {
		szMachineID = szmachine;
	}

	public void setPhoneNum(String szphone) {
		szPhoneNum = szphone;
	}

	public boolean getLookOnAllow() {
		return false;
	}

	public void setLookonAllow(boolean allow) {

	}

	public String getAppName() {
		return szName;
	}

	public void setAppName(String szname) {
		szName = szname;
	}

	public int getViewCount() {
		return nViewCount;
	}

	public void setViewCount(int count) {
		nViewCount = count;
	}

	public void PlayBackGroundMusic(int resid, boolean loop) {
		bgm.LoadMusic(resid, loop);
		if (bMusic)
			bgm.Play();
	}
	public void PlayBackGroundMusic(String path, boolean loop) {
		Util.v(TAG,"PlayBackGroundMusic LoadMusic");
		bgm.LoadMusic(path, loop);
		if (bMusic)
		{	
			bgm.Play();
			Util.v(TAG,"PlayBackGroundMusic Play");
		}
	}
	public void PauseBackGroundMusic() {
		bgm.Pause();
	}
	public void StopBackGroundMusic(){
		 bgm.Stop();
	}
	public void ContinueBackGroundMusic() {
		if (bMusic)
			bgm.Play();
	}

	public void LoadGameSound(int raw, int id) {
		soundhelp.loadSfx(raw, id);
	}

	public void PlayGameSound(int id) {
		if (bSound) {
			soundhelp.play(id);
		}
		Util.d(TAG, "PlayGameSound-" + bSound + "-" + soundhelp.getVolume());
	}

	public void PlayGameSound(int id, int loop) {
		if (bSound) {
			soundhelp.play(id, loop);
		}
	}
}
