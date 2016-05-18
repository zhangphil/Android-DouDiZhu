
package com.qp.lib.interface_ex.option;

public interface IOptionControl
	extends IOption
{

	public abstract boolean setMusic(boolean flag);

	public abstract boolean setSound(boolean flag);

	public abstract boolean setShake(boolean flag);

	public abstract boolean setBeginner(boolean flag);

	public abstract boolean setLight(int i);

	public abstract boolean setMusicVoice(int i);

	public abstract boolean setSoundVoice(int i);

	public abstract void setLookonAllow(boolean flag);

	public abstract boolean setGameVersion(long l);

	public abstract boolean setAppVersion(long l);

	public abstract boolean setKindID(int i);

	public abstract void setMachineID(String s);

	public abstract void setPhoneNum(String s);

	public abstract void setAppName(String s);

	public abstract void setViewCount(int i);

	public abstract void PlayBackGroundMusic(int i, boolean flag);
	public abstract void PlayBackGroundMusic(String i, boolean flag);
	public abstract void PauseBackGroundMusic();

	public abstract void ContinueBackGroundMusic();

	public abstract void LoadGameSound(int raw, int id);

	public abstract void PlayGameSound(int id);
	
	public abstract void PlayGameSound(int id,int loop);

	public abstract void StopBackGroundMusic();
}
