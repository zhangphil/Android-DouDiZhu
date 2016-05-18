package com.qp.ddz.scene.popupwindow;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.qp.ddz.R;
import com.qp.lib.interface_ex.option.IOptionControl;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class OptionMenu implements OnSeekBarChangeListener, OnClickListener {

	@SuppressWarnings("unused")
	private static final String	TAG	= "OptionMenu";

	PopupWindow					m_PopupWindow;

	SeekBar						musicbar;
	SeekBar						soundbar;

	Button						m_btMusic;

	IOptionControl				m_OptionControl;

	boolean						playSound;

	public OptionMenu(IOptionControl optioncontrol) {
		m_OptionControl = optioncontrol;
	}

	public boolean onShowOptionMenu(Activity activity, View view) {
		if (m_PopupWindow == null && view != null) {
			View popupview = activity.getLayoutInflater().inflate(R.layout.option_menu, null, false);
			m_PopupWindow = new PopupWindow(popupview, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
			m_PopupWindow.setBackgroundDrawable(new BitmapDrawable());
			m_PopupWindow.setOutsideTouchable(true);

			musicbar = (SeekBar) popupview.findViewById(R.id.option_music_seekbar);
			musicbar.setProgress(m_OptionControl.getMusicVoice());
			musicbar.setOnSeekBarChangeListener(this);

			soundbar = (SeekBar) popupview.findViewById(R.id.option_sound_seekbar);
			soundbar.setProgress(m_OptionControl.getSoundVoice());
			soundbar.setOnSeekBarChangeListener(this);

			m_btMusic = (Button) popupview.findViewById(R.id.option_bt_voice);
			m_btMusic.setOnClickListener(this);

			playSound = m_OptionControl.getMusic();

			if (playSound) {
				m_btMusic.setBackgroundResource(R.drawable.bt_on);
			} else {
				m_btMusic.setBackgroundResource(R.drawable.bt_off);
			}

		}

		m_PopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

		return true;
	}

	public void onDestroy() {

		if (m_PopupWindow != null) {
			m_PopupWindow.dismiss();
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
			case R.id.option_music_seekbar :
				m_OptionControl.setMusicVoice(progress);
				break;
			case R.id.option_sound_seekbar :
				m_OptionControl.setSoundVoice(progress);
				break;
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	public boolean isVisibility() {
		return m_PopupWindow != null && m_PopupWindow.isShowing();
	}

	public boolean onVoiceDown() {
		if (playSound) {
			int progress = 0;
			if (musicbar != null) {
				progress = Math.max(musicbar.getProgress() - 10, 0);
				musicbar.setProgress(progress);
			} else {
				progress = Math.max(m_OptionControl.getMusicVoice() - 10, 0);
			}
			m_OptionControl.setMusicVoice(progress);

			progress = 0;
			if (soundbar != null) {
				progress = Math.max(soundbar.getProgress() - 10, 0);
				soundbar.setProgress(progress);
			} else {
				progress = Math.max(m_OptionControl.getSoundVoice() - 10, 0);
			}
			m_OptionControl.setSoundVoice(progress);
		}
		return true;
	}
	public boolean onVoiceUp() {
		if (playSound) {
			int progress = 0;
			if (musicbar != null) {
				progress = Math.min(musicbar.getProgress() + 10, 100);
				musicbar.setProgress(progress);
			} else {
				progress = Math.max(m_OptionControl.getMusicVoice() + 10, 100);
			}
			m_OptionControl.setMusicVoice(progress);

			progress = 0;
			if (soundbar != null) {
				progress = Math.min(soundbar.getProgress() + 10, 100);
				soundbar.setProgress(progress);
			} else {
				progress = Math.max(m_OptionControl.getSoundVoice() + 10, 100);
			}
			m_OptionControl.setSoundVoice(progress);
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.option_bt_voice) {
			playSound = !playSound;
			m_OptionControl.setMusic(playSound);
			m_OptionControl.setSound(playSound);
			soundbar.setEnabled(playSound);
			musicbar.setEnabled(playSound);
			if (playSound) {
				m_btMusic.setBackgroundResource(R.drawable.bt_on);
			} else {
				m_btMusic.setBackgroundResource(R.drawable.bt_off);
			}
		}
	}

}
