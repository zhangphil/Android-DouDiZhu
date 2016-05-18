package com.qp.lib.help;

import android.media.AudioManager;
import android.media.MediaPlayer;
import java.io.IOException;

import com.qp.lib.main.AppMain;

public class MusicHelp implements android.media.MediaPlayer.OnCompletionListener, android.media.MediaPlayer.OnErrorListener {

	MediaPlayer			m_MediaPlayer;
	int					state;
	static final int	NULL	= 0;
	static final int	PLAY	= 1;
	static final int	PAUSE	= 2;
	static final int	STOP	= 3;
	int					volume;

	int type; //1表示file加载   0表示res加载
	
	public MusicHelp() {
		m_MediaPlayer = null;
		state = 0;
		volume = 100;
	}

	public void Stop() {
		if (m_MediaPlayer != null) {
			m_MediaPlayer.stop();
			state = 3;
		}
	}

	public void Pause() {
		if (m_MediaPlayer != null && state == 1 && m_MediaPlayer.isPlaying()) {
			m_MediaPlayer.pause();
			state = 2;
		}
	}

	public void Play() {
		if (m_MediaPlayer != null)
			if (state == 2) {
				m_MediaPlayer.start();
				m_MediaPlayer.setVolume(volume / 100.f, volume / 100.f);
				state = 1;
			} else {
				try {
					//在播放之前先判断playerMusic是否被占用，这样就不会报错了

				       if (m_MediaPlayer != null)
				    	   m_MediaPlayer.stop();

				      //if(type==0)
				    	  m_MediaPlayer.prepare();
				       m_MediaPlayer.start();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				 
				m_MediaPlayer.setVolume(volume / 100.f, volume / 100.f);
				state = 1;
			}
	}

	public void LoadMusic(int resid, boolean loop) {
		type=0;
		Release();
		m_MediaPlayer = MediaPlayer.create(AppMain.getInstance(), resid);
		m_MediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		m_MediaPlayer.setLooping(loop);
		if (!loop)
			m_MediaPlayer.setOnCompletionListener(this);
		m_MediaPlayer.setOnErrorListener(this);
	}
	public void LoadMusic(String Path, boolean loop) {
		type=1;
		
		Release();
		 
		try {
			 
			m_MediaPlayer = new MediaPlayer( );
			m_MediaPlayer.reset();
			m_MediaPlayer.setDataSource(Path);
			m_MediaPlayer.prepare();
		}  catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m_MediaPlayer=null;
			return;
		}
		m_MediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		m_MediaPlayer.setLooping(loop);
		if (!loop)
			m_MediaPlayer.setOnCompletionListener(this);
		m_MediaPlayer.setOnErrorListener(this);
	}

	public void Release() {
		if (m_MediaPlayer != null) {
			
			
			m_MediaPlayer.release();
			
			m_MediaPlayer = null;
			state = 0;
		}
	}

	public void onCompletion(MediaPlayer mediaplayer) {
		if (mediaplayer != null)
			mediaplayer.release();
	}

	public boolean onError(MediaPlayer mediaplayer, int arg1, int arg2) {
		if (mediaplayer != null)
			mediaplayer.release();
		return false;
	}

	public boolean IsPlaying() {
		if (m_MediaPlayer != null)
			return m_MediaPlayer.isPlaying();
		else
			return false;
	}

	public boolean hasmusic() {
		return m_MediaPlayer != null;
	}

	public void setVolume(int voice) {
		volume = voice % 101;
		if (m_MediaPlayer != null)
			m_MediaPlayer.setVolume(volume / 100.f, volume / 100.f);
	}

	public int getVolume() {
		// TODO Auto-generated method stub
		return volume;
	}
}
