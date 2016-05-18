
package com.qp.lib.interface_ex.option;

import android.app.Activity;

public interface IOption
{

	public abstract void onInitConfig(Activity activity);

	public abstract void onSaveConfig(Activity activity);

	public abstract boolean isFirstStart();

	public abstract int getLight();

	public abstract int getMusicVoice();

	public abstract int getSoundVoice();

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract long getGameVersion();

	public abstract long getAppVersion();

	public abstract byte getDeviceType();

	public abstract boolean getMusic();

	public abstract boolean getSound();

	public abstract boolean getShake();

	public abstract boolean getBeginner();

	public abstract String getMachineID();

	public abstract String getPhoneNum();

	public abstract int getKindId();

	public abstract boolean getLookOnAllow();

	public abstract String getAppName();

	public abstract int getViewCount();
}
