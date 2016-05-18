package com.qp.lib.interface_ex.user;

public interface IUserControl extends IUserEvent {

	public abstract void onInitUserControl();

	public abstract IUserItem getMeUserItem();

	public abstract IUserItem searchUserByUserID(long userid);

	public abstract IUserItem searchUserByGameID(long gameid);

	public abstract IUserItem searchUserByNickName(String s);

	public abstract IUserItem searchUserByTableInfo(int table, int chair);

	public abstract void onReleaseUserItem(boolean flag);
}
