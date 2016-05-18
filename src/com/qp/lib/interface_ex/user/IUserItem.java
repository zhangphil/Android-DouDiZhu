package com.qp.lib.interface_ex.user;

import com.qp.lib.tag.UserInfo;
import com.qp.lib.tag.UserScore;
import com.qp.lib.tag.UserStatus;

public interface IUserItem
{

	public abstract UserInfo GetUserInfo();

	public abstract int GetFaceID();

	public abstract long GetCustomID();

	public abstract byte GetGender();

	public abstract long GetUserID();

	public abstract long GetGameID();

	public abstract String GetNickName();

	public abstract long GetUserExperience();

	public abstract int GetMemberOrder();

	public abstract int GetTableID();

	public abstract int GetChairID();

	public abstract int GetUserStatus();

	public abstract long GetUserScore();

	public abstract long GetUserInsure();

	public abstract long GetUserWinCount();

	public abstract long GetUserLostCount();

	public abstract long GetUserDrawCount();

	public abstract long GetUserFleeCount();

	public abstract long GetUserPlayCount();

	public abstract long GetUserWinRate();

	public abstract long GetUserLostRate();

	public abstract long GetUserDrawRate();

	public abstract long GetUserFleeRate();

	public abstract void SetUserItem(IUserItem iuseritem);

	public abstract boolean IsEmpty();

	public abstract void Empty();

	public abstract void SetUserStatus(UserStatus userstatus);

	public abstract void SetUserScore(UserScore userscore);
}
